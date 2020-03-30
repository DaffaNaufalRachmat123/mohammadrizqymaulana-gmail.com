package com.starbucks.id.controller.fragment.home_fragment.profile_tabs.fragment_premium;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.otaliastudios.cameraview.CameraException;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraLogger;
import com.otaliastudios.cameraview.CameraOptions;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.PictureResult;
import com.otaliastudios.cameraview.controls.Facing;
import com.otaliastudios.cameraview.controls.Preview;
import com.starbucks.id.R;
import com.starbucks.id.controller.activity.MainActivity;
import com.starbucks.id.helper.UserDefault;

public class FragmentStep1Camera extends Fragment {

    private static MainActivity activity;
    CameraView camera;
    LinearLayout capturePictureSnapshot;
    private UserDefault userDefault;
    TextView tvIdPhoto,selfieTv,submitTv,tv;
    public FragmentStep1Camera() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = ((MainActivity)getActivity());
        if (activity != null) userDefault = activity.getUserDefault();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_step_1_camera, container, false);

        camera = rootView.findViewById(R.id.camera);

        camera.setLifecycleOwner(getViewLifecycleOwner());
        camera.setFacing(Facing.BACK);
        camera.addCameraListener(new Listener());
        capturePictureSnapshot = rootView.findViewById(R.id.capturePictureSnapshot);
        capturePictureSnapshot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capturePictureSnapshot();
            }
        });
        selfieTv = rootView.findViewById(R.id.selfieTv);
        submitTv = rootView.findViewById(R.id.submitTv);
        tv = rootView.findViewById(R.id.tv);
        tvIdPhoto = rootView.findViewById(R.id.tvIdPhoto);
        tvIdPhoto.setText(userDefault.IDLanguage()?"Foto ID":"ID Photo");
        selfieTv.setText(userDefault.IDLanguage()?"Selfie Dengan ID":"Selfie With ID");
        submitTv.setText(userDefault.IDLanguage()?"Review":"Review");
        tv.setText(userDefault.IDLanguage()?"Tempatkan kartu identitas Anda di dalam kotak":"Place your ID card within the frame");
        backButton();
        activity.setToolbarTitle("Starbucks Premium");
        return rootView;
    }
    private long mCaptureTime;

    private void capturePictureSnapshot() {
        if (camera.isTakingPicture()) return;
        if (camera.getPreview() != Preview.GL_SURFACE) {
            message("Picture snapshots are only allowed with the GL_SURFACE preview.", true);
            return;
        }
        mCaptureTime = System.currentTimeMillis();
        message("Capturing picture snapshot...", false);
        camera.takePictureSnapshot();
    }


    private final static CameraLogger LOG = CameraLogger.create("Apss");

    private void message(@NonNull String content, boolean important) {
        if (important) {
            LOG.w(content);
            Toast.makeText(activity, content, Toast.LENGTH_LONG).show();
        } else {
            LOG.i(content);
            Toast.makeText(activity, content, Toast.LENGTH_SHORT).show();
        }
    }
    private class Listener extends CameraListener {

        @Override
        public void onCameraOpened(@NonNull CameraOptions options) {
//            ViewGroup group = (ViewGroup) controlPanel.getChildAt(0);
//            for (int i = 0; i < group.getChildCount(); i++) {
//                OptionView view = (OptionView) group.getChildAt(i);
//                view.onCameraOpened(camera, options);
//            }
        }

        @Override
        public void onCameraError(@NonNull CameraException exception) {
            super.onCameraError(exception);
            message("Got CameraException #" + exception.getReason(), true);
        }

        @Override
        public void onPictureTaken(@NonNull PictureResult result) {
            super.onPictureTaken(result);
            stopPreviewAndFreeCamera();
            // This can happen if picture was taken with a gesture.
            long callbackTime = System.currentTimeMillis();
            if (mCaptureTime == 0) mCaptureTime = callbackTime - 300;
            LOG.w("onPictureTaken called! Launching activity. Delay:", callbackTime - mCaptureTime);

            FragmentStep1Confirmation.setPictureResult(result);

            result.getData();
            Bitmap bitmap = BitmapFactory.decodeByteArray( result.getData(), 0,  result.getData().length);

//            Bundle bundle = new Bundle();
//            bundle.putParcelable("idCard", bitmap);
//            activity.cFragmentWithBundle(new FragmentStep1Confirmation(),bundle);

            activity.setIdCard(bitmap);
            activity.cFragmentPremium(new FragmentStep1Confirmation());

            mCaptureTime = 0;
            LOG.w("onPictureTaken called! Launched activity.");
        }


        @Override
        public void onExposureCorrectionChanged(float newValue, @NonNull float[] bounds, @Nullable PointF[] fingers) {
            super.onExposureCorrectionChanged(newValue, bounds, fingers);
            message("Exposure correction:" + newValue, false);
        }

        @Override
        public void onZoomChanged(float newValue, @NonNull float[] bounds, @Nullable PointF[] fingers) {
            super.onZoomChanged(newValue, bounds, fingers);
            message("Zoom:" + newValue, false);
        }

    }

    private void stopPreviewAndFreeCamera() {

        if (camera != null) {
            // Call stopPreview() to stop updating the preview surface.
            camera.close();

            // Important: Call release() to release the camera for use by other
            // applications. Applications should release the camera immediately
            // during onPause() and re-open() it during onResume()).
            camera.open();

            camera = null;
        }
    }
    public void backButton() {
        activity.toolbar.setNavigationIcon(R.drawable.ic_action_back);
        activity.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kycDialog(getActivity(), userDefault.IDLanguage() ? "Peringatan!":"Warning!",
                        userDefault.IDLanguage() ? "Apakah Anda yakin ingin mengulang dari awal?":"Are you sure you want to re-do the process from the start?");

            }});
    }

    public static void kycDialog(final Activity act, String header,
                                 String message) {
        final Dialog dialogNotif = new Dialog(act);
        dialogNotif.setCanceledOnTouchOutside(false);
        dialogNotif.setCancelable(false);
        dialogNotif.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogNotif.setContentView(R.layout.kyc_dialog);

        Window window = dialogNotif.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        TextView txtNotifHeader = dialogNotif.findViewById(R.id.tvHeader);
        TextView txtNotifContent = dialogNotif.findViewById(R.id.tvNotif);
        Button btnOk = dialogNotif.findViewById(R.id.btOk);
        Button btnCancel = dialogNotif.findViewById(R.id.btNo);


        txtNotifHeader.setText(header);
        txtNotifContent.setText(message);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.cFragmentPremium(new FragmentIntroPremium());
                dialogNotif.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogNotif.dismiss();
            }
        });

        dialogNotif.show();
    }
}
