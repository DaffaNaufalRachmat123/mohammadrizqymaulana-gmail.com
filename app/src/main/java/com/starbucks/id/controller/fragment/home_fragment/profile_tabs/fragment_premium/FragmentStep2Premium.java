package com.starbucks.id.controller.fragment.home_fragment.profile_tabs.fragment_premium;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.starbucks.id.R;
import com.starbucks.id.controller.activity.MainActivity;
import com.starbucks.id.helper.UserDefault;

public class FragmentStep2Premium extends Fragment {
    private Button takePhoto;
    private static MainActivity activity;
    private UserDefault userDefault;
    TextView tvIdPhoto,selfieTv,submitTv,tv;
    public FragmentStep2Premium() {
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
        View rootView = inflater.inflate(R.layout.fragment_step_2_premium, container, false);
        takePhoto = rootView.findViewById(R.id.takePhoto);
        tvIdPhoto = rootView.findViewById(R.id.tvIdPhoto);
        selfieTv = rootView.findViewById(R.id.selfieTv);
        submitTv = rootView.findViewById(R.id.submitTv);
        tv = rootView.findViewById(R.id.tv);
        setData();
        return rootView;
    }

    public void setData(){
        activity.setToolbarTitle("Starbucks Premium");
        tvIdPhoto.setText(userDefault.IDLanguage()?"Foto ID":"ID Photo");
        selfieTv.setText(userDefault.IDLanguage()?"Selfie Dengan ID":"Selfie With ID");
        submitTv.setText(userDefault.IDLanguage()?"Review":"Review");
        tv.setText(userDefault.IDLanguage()?"Ambil swafoto sambil memegang kartu ID":"Take a selfie while holding your ID card");
        takePhoto.setText(userDefault.IDLanguage()?"Ambil Foto":"Take Selfie");
        backButton();
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.cFragmentPremium(new FragmentStep2Camera());
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