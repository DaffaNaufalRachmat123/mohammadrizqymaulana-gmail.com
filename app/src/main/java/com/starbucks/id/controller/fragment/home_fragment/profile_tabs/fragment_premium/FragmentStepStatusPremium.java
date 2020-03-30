package com.starbucks.id.controller.fragment.home_fragment.profile_tabs.fragment_premium;

import android.app.Activity;
import android.app.Dialog;
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
import com.starbucks.id.controller.fragment.home_fragment.profile_tabs.FragmentPersonal;
import com.starbucks.id.helper.UserDefault;
import com.starbucks.id.model.user.UserResponseModel;

public class FragmentStepStatusPremium extends Fragment {
    private static MainActivity activity;
    private static String detail;
    private UserDefault userDefault;
    TextView title,header,status,desc;
    private UserResponseModel userResponseModel;
    public FragmentStepStatusPremium() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = ((MainActivity)getActivity());
        if (activity != null) userDefault = activity.getUserDefault();
        Bundle args = getArguments();
        if (args != null) {
            detail = args.getString("description");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_step_status_premium, container, false);
        title = rootView.findViewById(R.id.tvTitle);
        header = rootView.findViewById(R.id.tvHeader);
        status = rootView.findViewById(R.id.tvStatus);
        desc = rootView.findViewById(R.id.tvDescription);
        activity.hideProgressDialog();
        activity.tabLayout.setVisibility(View.GONE);
        refreshDataProfile();
        backButton();


        return rootView;
    }

    public void refreshDataProfile(){
        activity.tabLayout.setVisibility(View.GONE);
        userResponseModel = activity.getUser();
//        ((MainActivity) getActivity()).getUserData();
        setData();
    }

    private void setData(){
        activity.setToolbarTitle("Personal");
        if(!detail.equals("")){
            header.setText(userDefault.IDLanguage()?"Status keanggotaan":"Membership status");
            title.setText(userDefault.IDLanguage()?
                    "Terima kasih telah melengkapi proses pendaftaran akun Starbucks Premium Anda"
                    :"Thank you for completing the submission process for your Starbucks Premium account");
            status.setTextColor(getResources().getColor(R.color.greenAccent));
            status.setText(detail);
            desc.setVisibility(View.GONE);
        }else{
            if(((MainActivity) getActivity()).getUser().getUser().getPremiumStatusCode().equals("1")){
                title.setText(userDefault.IDLanguage()?
                        "Terima kasih telah melengkapi proses pendaftaran akun Starbucks Premium Anda"
                        :"Thank you for completing the submission process for your Starbucks Premium account");
                header.setText(userDefault.IDLanguage()?"Status keanggotaan":"Membership status");
                    status.setTextColor(getResources().getColor(R.color.greenAccent));
                    status.setText(userResponseModel.getUser().getPremiumLabel());
                    desc.setVisibility(View.GONE);

            }else if (((MainActivity) getActivity()).getUser().getUser().getPremiumStatusCode().equals("2")) {
                title.setText(userDefault.IDLanguage()?
                        "Terima kasih telah melengkapi proses pendaftaran akun Starbucks Premium Anda"
                        :"Thank you for completing the submission process for your Starbucks Premium account");
                header.setText(userDefault.IDLanguage()?"Status keanggotaan":"Membership status");
                status.setTextColor(getResources().getColor(R.color.error_state_red));
                status.setText(userResponseModel.getUser().getPremiumLabel());
                desc.setText(userResponseModel.getUser().getPremiumDescription());

            }
        }

    }

    public void backButton() {
        activity.toolbar.setNavigationIcon(R.drawable.ic_action_back);
        activity.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                kycDialog(getActivity(), userDefault.IDLanguage() ? "Peringatan!":"Warning!",
//                        userDefault.IDLanguage() ? "Anda yakin ingin kembali melakukan proses dari awal?":"Are you sure you want to re-do the process from the start?");
                activity.cFragmentPremium(new FragmentPersonal());

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
