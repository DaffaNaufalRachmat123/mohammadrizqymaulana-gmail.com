package com.starbucks.id.controller.fragment.home_fragment.profile_tabs;

/**
 * Created by Angga N P on 3/13/2018.
 */

import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.textfield.TextInputLayout;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.starbucks.id.R;
import com.starbucks.id.controller.activity.MainActivity;
import com.starbucks.id.controller.fragment.home_fragment.profile_tabs.fragment_premium.FragmentIntroPremium;
import com.starbucks.id.controller.fragment.home_fragment.profile_tabs.fragment_premium.FragmentStepStatusPremium;
import com.starbucks.id.controller.fragment.home_fragment.profile_tabs.fragment_premium.FragmentStepStatusPremiumProfile;
import com.starbucks.id.controller.fragment.home_fragment.profile_tabs.fragment_premium.FragmentSuccessPremium;
import com.starbucks.id.helper.UserDefault;
import com.starbucks.id.model.ResponseBase;
import com.starbucks.id.model.user.UserResponseModel;
import com.starbucks.id.rest.ApiInterface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentPersonal extends Fragment {
    private static final String TAG = Fragment.class.getSimpleName();
    private UserDefault userDefault;
    private TextView tvName, tvEmail, tvDate;
    private Button btCD, btCP,btGP;
    private Dialog dialog;
    private ConstraintLayout CLContainer;
    private ApiInterface apiService;
    private UserResponseModel userResponseModel;
    private Call<ResponseBase> callPwd;
    private ImageView stars;
    TextView premiumLabel;
    LinearLayout premiumLY;
    TextView checkStatus;

    private ImageButton ibOld, ibNew, ibNew2;

    private boolean showOld, show, show2 = false;

    private EditText etOld, etNew, etNew2;
    private MainActivity activity;

    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {
            android.Manifest.permission.CAMERA,
//            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
//            android.Manifest.permission.READ_EXTERNAL_STORAGE,
    };

    public FragmentPersonal() {
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
        View rootView = inflater.inflate(R.layout.fragment_home_profile_personal, container, false);
        activity.tabLayout.setVisibility(View.VISIBLE);
        tvName = rootView.findViewById(R.id.tvName);
        tvEmail = rootView.findViewById(R.id.tvId);
        tvDate = rootView.findViewById(R.id.tvDate);
        btCP = rootView.findViewById(R.id.btCP);
        btCD = rootView.findViewById(R.id.btCD);
        btGP = rootView.findViewById(R.id.btGP);
        premiumLY = rootView.findViewById(R.id.btAP);
        premiumLabel = rootView.findViewById(R.id.btAPtv);
        checkStatus = rootView.findViewById(R.id.tvCheckStatus);
        CLContainer = rootView.findViewById(R.id.CLContainer);
        stars = rootView.findViewById(R.id.stars);
        setData();
        setBahasa();
        return rootView;
    }


private void setBahasa(){
    btCD.setText(userDefault.IDLanguage()?"Ubah Detail":"Change Detail");
    btCP.setText(userDefault.IDLanguage()?"Ganti Password":"Change Password");
    btGP.setText(userDefault.IDLanguage()?"Jadi Premium":"Go Premium");
}


    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    /*UI Controller*/
    private void setData() {
        userResponseModel = activity.getUser();
//        Log.e("Data", String.valueOf(userResponseModel.getUser().getPremiumStatusCode()));
        setDefaultView();
        setStatusPremium();
        backButton();
    }

    public void setStatusPremium(){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(10,10,10,10);
        if(userResponseModel.getUser().getPremiumStatusCode().equals("0")){
             premiumLY.setVisibility(View.GONE);
             premiumLabel.setVisibility(View.GONE);
             checkStatus.setVisibility(View.GONE);
             stars.setVisibility(View.GONE);
             btGP.setVisibility(View.VISIBLE);
        } else if(userResponseModel.getUser().getPremiumStatusCode().equals("1")){
            premiumLY.setVisibility(View.VISIBLE);
            premiumLabel.setVisibility(View.VISIBLE);
            premiumLY.setBackground(getResources().getDrawable(R.drawable.pending_premium_logo));
            premiumLabel.setTextColor(getResources().getColor(R.color.greenPrimary));
            premiumLabel.setText(userResponseModel.getUser().getPremiumLabel());
            premiumLabel.setLayoutParams(params);
            premiumLabel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    activity.cFragmentWithBundle(new FragmentStepStatusPremiumProfile(),bundle);
                }
            });
            checkStatus.setVisibility(View.GONE);
            btGP.setVisibility(View.GONE);
            stars.setVisibility(View.GONE);
        }else if(userResponseModel.getUser().getPremiumStatusCode().equals("2")){
            premiumLabel.setVisibility(View.VISIBLE);
            premiumLY.setVisibility(View.VISIBLE);
            premiumLY.setBackground(getResources().getDrawable(R.drawable.decline_premium_logo));
            premiumLabel.setTextColor(getResources().getColor(R.color.error_state_red));
            premiumLabel.setText(userResponseModel.getUser().getPremiumLabel());
            premiumLabel.setLayoutParams(params);
            checkStatus.setVisibility(View.GONE);
            stars.setVisibility(View.GONE);
            premiumLabel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    activity.cFragmentWithBundle(new FragmentStepStatusPremiumProfile(),bundle);
                }
            });
            btGP.setVisibility(View.VISIBLE);
        }else if(userResponseModel.getUser().getPremiumStatusCode().equals("3")){
            premiumLabel.setVisibility(View.VISIBLE);
            premiumLY.setVisibility(View.VISIBLE);
            premiumLabel.setText(userResponseModel.getUser().getPremiumLabel());
            stars.setVisibility(View.VISIBLE);
            premiumLY.setBackground(getResources().getDrawable(R.drawable.approve_premium_logo));
            premiumLabel.setTextColor(getResources().getColor(R.color.goldPrimary));
            premiumLabel.setLayoutParams(params);
            premiumLabel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.cFragmentWithBS(new FragmentSuccessPremium());
                }
            });
            checkStatus.setVisibility(View.GONE);
            btGP.setVisibility(View.GONE);
        }
    }

    private void setDefaultView() {
        CLContainer.setVisibility(View.VISIBLE);

        activity.setToolbarTitle(
                userDefault.IDLanguage() ? getString(R.string.personal_title_id) :
                        getString(R.string.personal_title));
        activity.toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.cFragmentWithBS(new FragmentProfile());
            }
        });

        tvName.setText(userResponseModel.getUser().getFirstName() +
                " " + userResponseModel.getUser().getLastName());
        tvEmail.setText(userDefault.getEmail());

        if (userResponseModel.getUser().getDob().equals("1901-01-01")) tvDate.setVisibility(View.INVISIBLE);

        tvDate.setText(userDefault.IDLanguage() ?
                "Tanggal lahir : " + getDate(userResponseModel.getUser().getDob()) :
                "Birth date : " + getDate(userResponseModel.getUser().getDob()));

        btCP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        btCD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.cFragmentWithBS(new FragmentChangeDetail());
            }
        });
        btGP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(hasPermissions(getActivity(), PERMISSIONS)){
                    activity.cFragmentPremium(new FragmentIntroPremium());
                } else {
                    ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, PERMISSION_ALL);
                }

            }
        });
    }

    private void showDialog() {
        if (dialog == null) {
            dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.dialog_change_pwd);

            Window window = dialog.getWindow();
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setGravity(Gravity.CENTER);
            window.setBackgroundDrawableResource(android.R.color.transparent);

            TextInputLayout tilCPOld = dialog.findViewById(R.id.tilCPOld);
            TextInputLayout tilCPNew = dialog.findViewById(R.id.tilCPNew);
            TextInputLayout tilCPNew2 = dialog.findViewById(R.id.tilCPNew2);
            TextView tvId = dialog.findViewById(R.id.tvId);
            TextView tvTitle = dialog.findViewById(R.id.detailDescription);
            Button btSend = dialog.findViewById(R.id.btSend);
            Button btCancel = dialog.findViewById(R.id.btCancel);


            etOld = dialog.findViewById(R.id.etOld);
            etNew = dialog.findViewById(R.id.etNew);
            etNew2 = dialog.findViewById(R.id.etNew2);

            ibOld = dialog.findViewById(R.id.ibOld);
            ibNew = dialog.findViewById(R.id.ibNew);
            ibNew2 = dialog.findViewById(R.id.ibNew2);

            tvTitle.setText(userDefault.IDLanguage() ? "GANTI PASSWORD" : "CHANGE PASSWORD");
            tilCPOld.setHint(userDefault.IDLanguage() ? "Password Lama" : "Old Password");
            tilCPNew.setHint(userDefault.IDLanguage() ? "Password Baru" : "New Password");
            tilCPNew2.setHint(userDefault.IDLanguage() ? "Konfirmasi Password Baru" : "Confirm New Password");

            tvId.setText(userDefault.getEmail());
            btSend.setText(!userDefault.IDLanguage() ? getString(R.string.submit) : getString(R.string.submit_id));
            btCancel.setText(!userDefault.IDLanguage() ? getString(R.string.btn_cancel) : getString(R.string.btn_cancel_id));

            btSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (validInput(etNew.getText().toString(),
                            etNew2.getText().toString(), etOld.getText().toString()))
                        changePwd();
                }
            });

            btCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            ibOld.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!showOld) {
                        ibOld.setImageResource(R.drawable.ic_action_show);
                        etOld.setTransformationMethod(null);
                        etOld.setSelection(etOld.getText().length());
                    } else {
                        ibOld.setImageResource(R.drawable.ic_action_hide);
                        etOld.setTransformationMethod(new PasswordTransformationMethod());
                        etOld.setSelection(etOld.getText().length());
                    }
                    showOld = !showOld;
                }
            });

            ibNew.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!show) {
                        ibNew.setImageResource(R.drawable.ic_action_show);
                        etNew.setTransformationMethod(null);
                        etNew.setSelection(etNew.getText().length());
                    } else {
                        ibNew.setImageResource(R.drawable.ic_action_hide);
                        etNew.setTransformationMethod(new PasswordTransformationMethod());
                        etNew.setSelection(etNew.getText().length());
                    }
                    show = !show;
                }
            });

            ibNew2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!show2) {
                        ibNew2.setImageResource(R.drawable.ic_action_show);
                        etNew2.setTransformationMethod(null);
                        etNew2.setSelection(etNew2.getText().length());
                    } else {
                        ibNew2.setImageResource(R.drawable.ic_action_hide);
                        etNew2.setTransformationMethod(new PasswordTransformationMethod());
                        etNew2.setSelection(etNew2.getText().length());
                    }
                    show2 = !show2;
                }
            });

            dialog.show();
        } else {
            etOld.setText("");
            etNew.setText("");
            etNew2.setText("");

            showOld = false;
            ibOld.setImageResource(R.drawable.ic_action_hide);
            etNew.setTransformationMethod(new PasswordTransformationMethod());

            show = false;
            ibNew.setImageResource(R.drawable.ic_action_hide);
            etNew.setTransformationMethod(new PasswordTransformationMethod());

            show2 = false;
            ibNew2.setImageResource(R.drawable.ic_action_hide);
            etNew2.setTransformationMethod(new PasswordTransformationMethod());

            dialog.show();
        }
    }

    private boolean validInput(String etNew, String etNew2, String Old) {
        if (!isValidPassword(etNew.toString())) {
            activity.showToast(userDefault.IDLanguage() ?
                    getString(R.string.id_val_pwd_comb) : getString(R.string.en_val_pwd_comb));
            return false;
        } else if (etNew.length() < 8) {
            activity.showToast(
                    userDefault.IDLanguage() ?
                            getString(R.string.id_val_pwd_8) : getString(R.string.en_val_pwd_8));
            return false;
        } else if (etNew2.isEmpty() || etNew2.equals("")) {
            activity.showToast(
                    userDefault.IDLanguage() ? "Tolong Masukkan Password Konfirmasi Anda" :
                            "Please Input Your Confirm New Password");
            return false;
        } else if (!etNew.equals(etNew2.toString())) {
            activity.showToast(
                    userDefault.IDLanguage() ? "Konfirmasi Pasword Baru Tidak Cocok" :
                            "Confirm New Password is invalid");
            return false;
        } else if (Old.isEmpty()) {
            activity.showToast(
                    userDefault.IDLanguage() ?
                            "Password lama anda masih kosong" : "Your old password is empty");
        }
        return true;
    }

    private String getDate(String s) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate = null;
        try {
            newDate = format.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        format = new SimpleDateFormat("dd MMM");
        if (newDate != null) return format.format(newDate);
        else return userDefault.IDLanguage() ?
                "Tanggal tidak valid" : "Date not valid";
    }


    protected boolean isValidPassword(String pwd) {
        boolean isValid = true;

        int counter = 0;

        String lowercasePattern = "(?=.*[a-z])";
        String uppercasePattern = "(?=.*[A-Z])";
        String numericPattern = "(?=.*[0-9])";
        String specialPattern = "(?=.*[!@#$&*])";


        Pattern LowercasePattern = Pattern.compile(lowercasePattern);
        Pattern UppercasePattern = Pattern.compile(uppercasePattern);
        Pattern NumericPattern = Pattern.compile(numericPattern);
        Pattern SpecialPattern = Pattern.compile(specialPattern);


        if (UppercasePattern.matcher(pwd).find()) {
            counter++;
        }

        if (LowercasePattern.matcher(pwd).find()) {
            counter++;
        }

        if (NumericPattern.matcher(pwd).find()) {
            counter++;
        }

        if (SpecialPattern.matcher(pwd).find()) {
            counter++;
        }

        if (counter < 4) {
            isValid = false;
        }

        return isValid;
    }


    //    REST Call
    public void changePwd() {
        if (!activity.checkConnection()) return;
        if (apiService == null) apiService = activity.getApiService();

        activity.showProgressDialog();

        callPwd = apiService.changePwd("change_password",
                activity.genSBUX(
                        "card_number=" + userResponseModel.getUser().getExternalId() +
                                "&email=" + userDefault.getEmail() +
                                "&oldpassword=" + etOld.getText().toString() +
                                "&newpassword=" + etNew.getText().toString()));

        callPwd.enqueue(new Callback<ResponseBase>() {
            @Override
            public void onResponse(Call<ResponseBase> call, Response<ResponseBase> response) {
                if (getActivity() != null) {
                    activity.hideProgressDialog();
                    if (response.isSuccessful() && response.body() != null) {
                        if (activity.successResponse(response.body().getReturnCode(), response.body().getProcessMsg())) {
                            if (dialog.isShowing()) dialog.dismiss();
                            activity.showToast(response.body().getProcessMsg());
                        } else {
                            if (dialog.isShowing()) dialog.dismiss();
                            activity.showToast(response.body().getProcessMsg());
                        }
                    } else {
                        if (dialog.isShowing()) dialog.dismiss();
                        activity.checkServer(String.valueOf(response.code()), response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBase> call, Throwable t) {
                if (getActivity() != null && isAdded()) {
                    if (dialog.isShowing()) dialog.dismiss();
                    activity.restFailure(t);
                    activity.hideProgressDialog();
                }
            }
        });
    }

    public void backButton() {
        activity.toolbar.setNavigationIcon(R.drawable.ic_action_back);
        activity.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.cFragmentPremium(new FragmentProfile());

            }});
    }

}
