package com.starbucks.id.controller.fragment.home_fragment.profile_tabs.fragment_premium;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.os.Bundle;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.otaliastudios.cameraview.PictureResult;
import com.starbucks.id.R;
import com.starbucks.id.controller.activity.MainActivity;
import com.starbucks.id.helper.UserDefault;
import com.starbucks.id.helper.utils.DialogUtil;

import java.io.IOException;

public class FragmentStep1Confirmation extends Fragment {
    private static MainActivity activity;
    private UserDefault userDefault;
    ImageView idCard;
//    Bitmap bitmapIdCard;
    private Button retake,submit;
    TextView tvIdPhoto,selfieTv,submitTv,tv1,tv2;
    private static PictureResult picture;

    public static void setPictureResult(@Nullable PictureResult pictureResult) {
        picture = pictureResult;
    }

    public FragmentStep1Confirmation() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = ((MainActivity)getActivity());
        if (activity != null) userDefault = activity.getUserDefault();

//        Bundle args = getArguments();
//        if (args != null) bitmapIdCard = args.getParcelable("idCard");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_step_1_confirmation, container, false);
        DialogUtil.hideLoading();
        idCard = rootView.findViewById(R.id.idCard);
        retake = rootView.findViewById(R.id.retake);
        submit = rootView.findViewById(R.id.submit);
        selfieTv = rootView.findViewById(R.id.selfieTv);
        submitTv = rootView.findViewById(R.id.submitTv);
        tvIdPhoto = rootView.findViewById(R.id.tvIdPhoto);
        tv1 = rootView.findViewById(R.id.tv1);
        tv2 = rootView.findViewById(R.id.tv2);
        setData();
        return rootView;
    }

    public void setData(){
        activity.setToolbarTitle("Starbucks Premium");
        selfieTv.setText(userDefault.IDLanguage()?"Selfie Dengan ID":"Selfie With ID");
        tvIdPhoto.setText(userDefault.IDLanguage()?"Foto ID":"ID Photo");
        submitTv.setText(userDefault.IDLanguage()?"Review":"Review");
        tv1.setText(userDefault.IDLanguage()?"Anda telah berhasil mengambil foto ID":"You've successfully taken your ID photo");
        tv2.setText(userDefault.IDLanguage()?"Pastikan foto yang diambil jelas, tidak buram atau terpotong":"Make sure your photo is clear, not blurry or cropped");
        retake.setText(userDefault.IDLanguage()?"Foto Ulang":"Retake");
        submit.setText(userDefault.IDLanguage()?"Gunakan Foto Ini":"Use this photo");
        backButton();
        idCard.setImageBitmap(activity.getIdCard());
//        Drawable d = new BitmapDrawable(getResources(), bitmapIdCard);
//        idCard.setBackground(d);
//        Bitmap rotatedBitmap;
//        Matrix matrix = new Matrix();
//        matrix.postRotate(0);
//
////        rotatedBitmap = Bitmap.createBitmap(bitmapIdCard, 0, 0, bitmapIdCard.getWidth(), bitmapIdCard.getHeight(), matrix, true);
//
//        idCard.setImageBitmap(Bitmap.createScaledBitmap(bitmapIdCard,500,500,true));

//        final Bundle bundle = new Bundle();
//        bundle.putParcelable("idCard", bitmapIdCard);
//        submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                activity.cFragmentWithBundle(new FragmentStep2Premium(),bundle);
//            }
//        });

        retake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.cFragmentPremium(new FragmentStep1Camera());
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.cFragmentPremium(new FragmentStep2Premium());
            }
        });
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