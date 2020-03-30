package com.starbucks.id.controller.fragment.login_fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.starbucks.id.R;
import com.starbucks.id.controller.activity.LoginActivity;
import com.starbucks.id.controller.activity.MainActivity;
import com.starbucks.id.controller.extension.extendedView.PreventCopas;
import com.starbucks.id.helper.UserDefault;
import com.starbucks.id.helper.utils.DataUtil;
import com.starbucks.id.helper.utils.DialogUtil;
import com.starbucks.id.helper.utils.PopUpUtil;
import com.starbucks.id.model.ResponseBase;
import com.starbucks.id.model.login.LoginResponseModel;
import com.starbucks.id.rest.ApiClient;
import com.starbucks.id.rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {
    private Button btnPaySignUp, btnPaySignIn;

    private ApiInterface apiService;
    private UserDefault userDefault;

    private Dialog forgotDialog, signInDialog, dialogOTP;

    //View Dialog Login
    private Button btnCancel, btnSignIn, btnForgotPwd;
    private ImageButton btnShowHidePwd;
    private TextInputLayout tilLOEmail, tilLOPwd;
    private TextInputEditText txtSignInUsername, txtSignInPwd;

    //View Dialog Forgot Password
    private EditText etEmail;
    private String email = "";
    private boolean show = false;

    //View Dialog OTP
    private EditText otp, otp2, otp3, otp4, otp5, otp6;

    private LoginActivity activity;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = ((LoginActivity)getActivity());
        if (activity != null) userDefault = activity.userDefault;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        if (userDefault.isLoggedIn()) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
            activity.finish();
        }


        btnPaySignUp = view.findViewById(R.id.btnPaySignUp);
        btnPaySignIn = view.findViewById(R.id.btnPaySignIn);

        setBaseView();
        return view;
    }

    private void setBaseView() {
        if (userDefault.IDLanguage()) {
            btnPaySignUp.setText(getString(R.string.id_sign_up));
            btnPaySignIn.setText(getString(R.string.id_sign_in));
        }

        btnPaySignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sDialogLogin();
            }
        });

        btnPaySignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sDialogTNC();
            }
        });
    }

    private boolean isValid(String email, String pwd) {
        if (email.equals("") || pwd.equals("")) {
            String MessageContent = !userDefault.IDLanguage() ? "Please enter your Email and Password" : "Mohon isi Email dan Kata Sandi";
            DialogUtil.sNotDialog(getActivity(), MessageContent);
            return false;
        } else if (!DataUtil.isValidEmail(email)) {
            String MessageContent = !userDefault.IDLanguage() ? "Email format is invalid" : "Format email salah";
            DialogUtil.sNotDialog(getActivity(), MessageContent);
            return false;
        } else if (pwd.length() < 8) {
            String MessageContent = !userDefault.IDLanguage() ? getString(R.string.en_val_pwd_8) : getString(R.string.id_val_pwd_8);
            DialogUtil.sNotDialog(getActivity(), MessageContent);
            return false;
        }
        return true;
    }

    /*DIALOG VIEW*/
    private void sDialogLogin() {
        if (signInDialog == null) {
            signInDialog = new Dialog(getActivity());

            signInDialog.setCanceledOnTouchOutside(false);
            signInDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            signInDialog.setContentView(R.layout.dialog_signin);

            Window window = signInDialog.getWindow();
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setGravity(Gravity.CENTER);
            window.setBackgroundDrawableResource(android.R.color.transparent);
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

            txtSignInUsername = signInDialog.findViewById(R.id.txtSignInUsername);
            txtSignInPwd = signInDialog.findViewById(R.id.txtSignInPwd);
            btnCancel = signInDialog.findViewById(R.id.btCancel);
            btnSignIn = signInDialog.findViewById(R.id.btOk);
            btnForgotPwd = signInDialog.findViewById(R.id.btForgot);
            btnShowHidePwd = signInDialog.findViewById(R.id.btnShowHidePwd);

            tilLOEmail = signInDialog.findViewById(R.id.tilLOEmail);
            tilLOPwd = signInDialog.findViewById(R.id.tilLOPwd);

            txtSignInUsername.setCustomSelectionActionModeCallback(new PreventCopas());
            txtSignInPwd.setCustomSelectionActionModeCallback(new PreventCopas());

            if (userDefault.IDLanguage()) {
                btnSignIn.setText(getString(R.string.id_sign_in));
                btnCancel.setText(getString(R.string.id_cancel));

                tilLOEmail.setHint(getString(R.string.id_hint_username));
                tilLOPwd.setHint(getString(R.string.id_hint_pwd));

                btnForgotPwd.setText(activity.getString(R.string.id_forgot_password));
            }

            btnSignIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isValid(txtSignInUsername.getText().toString(),
                            txtSignInPwd.getText().toString())) {
                        callLogin(txtSignInUsername.getText().toString(),
                                txtSignInPwd.getText().toString());
                    }
//                    checkData();
                }
            });

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    signInDialog.dismiss();
                }
            });


            btnForgotPwd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    signInDialog.dismiss();
                    sDialogForgot();
                }
            });

            btnForgotPwd.setTextColor(getResources().getColor(R.color.gray_dop));

            btnShowHidePwd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!show) {
                        btnShowHidePwd.setImageResource(R.drawable.ic_action_show);
                        txtSignInPwd.setTransformationMethod(null);
                        txtSignInPwd.setSelection(txtSignInPwd.getText().length());
                    } else {
                        btnShowHidePwd.setImageResource(R.drawable.ic_action_hide);
                        txtSignInPwd.setTransformationMethod(new PasswordTransformationMethod());
                        txtSignInPwd.setSelection(txtSignInPwd.getText().length());
                    }
                    show = !show;
                }
            });
        } else {
            show = false;
            btnShowHidePwd.setImageResource(R.drawable.ic_action_hide);
            txtSignInPwd.setTransformationMethod(new PasswordTransformationMethod());
//            txtSignInUsername.setText("angganova.recruitment@gmail.com");
//            txtSignInPwd.setText("Password01!");

            txtSignInUsername.requestFocus();
        }


        signInDialog.show();
    }

    private void sDialogOTP() {
        if (dialogOTP == null) {
            dialogOTP = new Dialog(getActivity());

            dialogOTP.setCanceledOnTouchOutside(false);
            dialogOTP.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogOTP.setContentView(R.layout.dialog_otp);

            Window window = dialogOTP.getWindow();
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setGravity(Gravity.CENTER);
            window.setBackgroundDrawableResource(android.R.color.transparent);
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);


            TextView tvDetail = dialogOTP.findViewById(R.id.tvDetail);
            TextView btRetry = dialogOTP.findViewById(R.id.btRetry);
            ImageButton ibClose = dialogOTP.findViewById(R.id.ibClose);

            tvDetail.setText(userDefault.IDLanguage() ?
                    "Pesan dengan kode verifikasi telah dikirim ke email Anda. Masukkan kode untuk melanjutkan" :
                    "A message with a verification code has been sent to your email. Enter the code to continue"
            );


            if (userDefault.IDLanguage()) {
                String text = "Tidak mendapatkan kode verifikasi? <font color='#00653e'><b>Coba lagi</b></font>.";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    btRetry.setText(Html.fromHtml(text,  Html.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE);
                } else {
                    btRetry.setText(Html.fromHtml(text), TextView.BufferType.SPANNABLE);
                }
            }else{
                String text = "Didn't get verification code? <font color='#00653e'><b>Retry</b></font>.";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    btRetry.setText(Html.fromHtml(text,  Html.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE);
                } else {
                    btRetry.setText(Html.fromHtml(text), TextView.BufferType.SPANNABLE);
                }
            }

            otp = dialogOTP.findViewById(R.id.otp);
            otp2 = dialogOTP.findViewById(R.id.otp2);
            otp3 = dialogOTP.findViewById(R.id.otp3);
            otp4 = dialogOTP.findViewById(R.id.otp4);
            otp5 = dialogOTP.findViewById(R.id.otp5);
            otp6 = dialogOTP.findViewById(R.id.otp6);

            otp.addTextChangedListener(new GenericTextWatcher(otp));
            otp2.addTextChangedListener(new GenericTextWatcher(otp2));
            otp3.addTextChangedListener(new GenericTextWatcher(otp3));
            otp4.addTextChangedListener(new GenericTextWatcher(otp4));
            otp5.addTextChangedListener(new GenericTextWatcher(otp5));
            otp6.addTextChangedListener(new GenericTextWatcher(otp6));

            otp.setOnKeyListener(new GenericKeyListener(otp));
            otp2.setOnKeyListener(new GenericKeyListener(otp2));
            otp3.setOnKeyListener(new GenericKeyListener(otp3));
            otp4.setOnKeyListener(new GenericKeyListener(otp4));
            otp5.setOnKeyListener(new GenericKeyListener(otp5));
            otp6.setOnKeyListener(new GenericKeyListener(otp6));

            btRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    otp.getText().clear();
                    otp2.getText().clear();
                    otp3.getText().clear();
                    otp4.getText().clear();
                    otp5.getText().clear();
                    otp6.getText().clear();


                    otp2.setEnabled(false);
                    otp3.setEnabled(false);
                    otp4.setEnabled(false);
                    otp5.setEnabled(false);
                    otp6.setEnabled(false);

                    otp.setEnabled(true);
                    otp.requestFocus();
                    callResendOTP();
                }
            });

            ibClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogOTP.dismiss();
                    sDialogLogin();
                }
            });

            otp.requestFocus();
        }
        dialogOTP.show();
    }

    public class GenericTextWatcher implements TextWatcher {
        private View view;
        private GenericTextWatcher(View view)
        {
            this.view = view;
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // TODO Auto-generated method stub
            String text = editable.toString();
            switch(view.getId()) {
                case R.id.otp:
                    if(text.length()==1) {
                        otp2.setEnabled(true);
                        otp2.requestFocus();

                        otp.setEnabled(false);
                    }
                    break;

                case R.id.otp2:
                    if(text.length()==1) {
                        otp3.setEnabled(true);
                        otp3.requestFocus();

                        otp2.setEnabled(false);
                    }
                    break;

                case R.id.otp3:
                    if(text.length()==1){
                        otp4.setEnabled(true);
                        otp4.requestFocus();

                        otp3.setEnabled(false);
                    }
                    break;
                case R.id.otp4:
                    if(text.length()==1) {
                        otp5.setEnabled(true);
                        otp5.requestFocus();

                        otp4.setEnabled(false);
                    }
                    break;

                case R.id.otp5:
                    if(text.length()==1) {
                        otp6.setEnabled(true);
                        otp6.requestFocus();

                        otp5.setEnabled(false);
                    }
                    break;
                case R.id.otp6:
                    if(text.length()==1) {
                        callOTP(otp.getText().toString() +
                                otp2.getText().toString() +
                                otp3.getText().toString() +
                                otp4.getText().toString() +
                                otp5.getText().toString() +
                                otp6.getText().toString());

                        otp.getText().clear();
                        otp.setEnabled(true);
                        otp.requestFocus();

                        otp2.getText().clear();
                        otp2.setEnabled(false);

                        otp3.getText().clear();
                        otp3.setEnabled(false);

                        otp4.getText().clear();
                        otp4.setEnabled(false);

                        otp5.getText().clear();
                        otp5.setEnabled(false);

                        otp6.getText().clear();
                        otp6.setEnabled(false);
                    }
                    break;
            }
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
        }
    }

    public class GenericKeyListener implements View.OnKeyListener{
        private View v;
        private GenericKeyListener(View view) { this.v = view; }

        @Override
        public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
            if(keyCode == KeyEvent.KEYCODE_DEL) {
                switch(v.getId()) {
                    case R.id.otp:
                        break;

                    case R.id.otp2:{
                        otp.setEnabled(true);
                        otp.setText("");
                        otp.requestFocus();

                        otp2.setText("");
                        otp2.setEnabled(false);
                    }
                    break;

                    case R.id.otp3:{
                        otp2.setEnabled(true);
                        otp2.setText("");
                        otp2.requestFocus();

                        otp3.setText("");
                        otp3.setEnabled(false);
                    }
                    break;

                    case R.id.otp4:{
                        otp3.setEnabled(true);
                        otp3.setText("");
                        otp3.requestFocus();

                        otp4.setText("");
                        otp4.setEnabled(false);
                    }
                    break;
                    case R.id.otp5:{
                        otp4.setEnabled(true);
                        otp4.setText("");
                        otp4.requestFocus();

                        otp5.setText("");
                        otp5.setEnabled(false);
                    }
                    break;

                    case R.id.otp6:{
                        otp5.setEnabled(true);
                        otp5.setText("");
                        otp5.requestFocus();

                        otp6.setText("");
                        otp6.setEnabled(false);
                    }
                    break;
                }
            }
            return false;
        }

    }

    private void sDialogTNC() {
        final Dialog dialogTNC = new Dialog(getActivity());

        dialogTNC.setContentView(R.layout.dialog_tnc);
        final WebView webTNC = dialogTNC.findViewById(R.id.webTNC);

        Window window = dialogTNC.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawableResource(android.R.color.transparent);

        String url = !userDefault.IDLanguage()
                ? ApiClient.INSTANCE.getHTML_FILE() + "tnc-eng-initial.html"
                : ApiClient.INSTANCE.getHTML_FILE() + "tnc-initial.html";

        webTNC.getSettings().setJavaScriptEnabled(true);
        webTNC.getSettings().setLoadWithOverviewMode(true);
        webTNC.getSettings().setUseWideViewPort(true);
        webTNC.loadUrl(url);

        webTNC.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                webTNC.setVisibility(View.VISIBLE);
                if (url.contains("closeTnC")) {
                    dialogTNC.dismiss();
                    activity.cFragmentWithBS(new SignUpFragment());
                }
            }
        });

        dialogTNC.show();
    }

    private void sDialogForgot() {
        if (forgotDialog == null) {
            forgotDialog = new Dialog(getActivity());
            forgotDialog.setContentView(R.layout.dialog_forgot_pwd);

            forgotDialog.setCanceledOnTouchOutside(false);
            Window window = forgotDialog.getWindow();
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setGravity(Gravity.CENTER);
            window.setBackgroundDrawableResource(android.R.color.transparent);
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

            etEmail = forgotDialog.findViewById(R.id.tvId);
            final TextView FPDtvTitle = forgotDialog.findViewById(R.id.FPDtvTitle);
            final TextView tvDesc = forgotDialog.findViewById(R.id.tvDesc);
            Button btCancel = forgotDialog.findViewById(R.id.btCancel);
            Button btSend = forgotDialog.findViewById(R.id.btSend);

            if (userDefault.IDLanguage()) {
                FPDtvTitle.setText("LUPA KATA SANDI");
                tvDesc.setText("Silakan masukan alamat email yang Anda gunakan untuk " +
                        "mendaftarkan Kartu Starbucks Anda dan kami akan mengirimkan Anda kata sandi baru");
                btCancel.setText("Batal");
                btSend.setText("Kirim");
            }

            btCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    forgotDialog.dismiss();

                    sDialogLogin();
                }
            });

            btSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (etEmail.getText().toString().isEmpty())
                        PopUpUtil.sLongToast(getActivity(),
                                userDefault.IDLanguage() ? "Tolong masukan email anda" :
                                        "Please input your email"
                        );
                    else callForgot(etEmail.getText().toString());
                }
            });

            forgotDialog.show();
        } else {
            etEmail.setText("");
            forgotDialog.show();
        }
    }

    private void sDialogLogout() {
        final Dialog dialogSignOut = new Dialog(getActivity());
        dialogSignOut.setCanceledOnTouchOutside(false);
        dialogSignOut.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSignOut.setContentView(R.layout.dialog_notif_2_button);

        Window window = dialogSignOut.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawableResource(android.R.color.transparent);


        TextView tvHeader = dialogSignOut.findViewById(R.id.tvHeader);
        TextView tvContent = dialogSignOut.findViewById(R.id.tvNotif);
        Button btOk = dialogSignOut.findViewById(R.id.btOk);
        Button btCancel = dialogSignOut.findViewById(R.id.btCancel);

        if (userDefault.IDLanguage()) {
            btCancel.setText(getString(R.string.id_cancel));
            btOk.setText(getString(R.string.id_sign_out));
        }

        tvHeader.setText(userDefault.IDLanguage() ? "Peringatan" : "Warning");
        tvContent.setText(!userDefault.IDLanguage() ? "Are you sure want to sign out?" : "Apakah anda yakin ingin keluar?");

        if (userDefault.IDLanguage()) {
            btOk.setText(getString(R.string.id_sign_in));
            btCancel.setText(getString(R.string.id_cancel));
        }

        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSignOut.dismiss();
                callLogout(txtSignInUsername.getText().toString());
            }
        });

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSignOut.dismiss();
            }
        });
        dialogSignOut.show();
    }

    private void clearDialog(){
        if (forgotDialog != null && forgotDialog.isShowing()) forgotDialog.dismiss();
        if (signInDialog != null && signInDialog.isShowing()) signInDialog.dismiss();
        if (dialogOTP != null && dialogOTP.isShowing()) dialogOTP.dismiss();
    }

    //Call REST
    //Sign in
    private void callLogin(String email, String password) {
        if (!activity.checkConnection()) {
            clearDialog();
            return;
        }

        this.email = email;
        if (signInDialog != null && signInDialog.isShowing()) signInDialog.dismiss();

        DialogUtil.sProDialog(getActivity());

        if (apiService == null) apiService = activity.apiService;
        Call<LoginResponseModel> call = apiService.signIn("loginOTP" +
                        "",
                DataUtil.genSBUX("email=" + email +
                        "&password=" + password +
                        "&mobid=" + getString(R.string.rest_mob_id) +
                        "&mobkey=" + getString(R.string.rest_mob_key)
                ));

        call.enqueue(new Callback<LoginResponseModel>() {
            @Override
            public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {
                if (getActivity() != null) {
                    DialogUtil.hProDialog();
                    if (response.isSuccessful()) {
                        switch (response.body().getReturnCode()) {
                            case "00":
                                sDialogOTP();
                                break;

                            case "01":
//                                txtSignInUsername.setText("");
//                                txtSignInPwd.setText("");
                                sDialogLogout();
                                break;

                            default:
                                txtSignInUsername.setText("");
                                txtSignInPwd.setText("");
                                String MessageContent = response.body().getProcessMsg();
                                DialogUtil.sNotDialog(getActivity(), MessageContent);
                                break;
                        }
                    } else {
                        activity.
                                checkServer(String.valueOf(response.code()), response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponseModel> call, Throwable t) {
                if (getActivity() != null && isAdded())
                    activity.restFailure(t);
                DialogUtil.hProDialog();
            }
        });

    }
    private void checkData(){
        Intent intent = new Intent(getActivity(), MainActivity.class);
        userDefault.setLogin(true);
        userDefault.setEmail("revavenu@gmail.com");
        userDefault.setAccToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpYXQiOiIyMDE5LTA5LTA0IDAyOjA0OjQwICswMDAwIiwiZXhwIjoiMjAxOS0wOS0xOCAwMjowNDo0MCArMDAwMCIsImRhdGEiOnsiaWQiOiI1YWI4MGU0ZS1jZWI4LTExZTktOGVkYi1lYTEzMjBlMTIwZGMifX0.sQSlO90zV3Q_qgK1x70_ObPu2h1mVeerQMInut5hDbHFWDubnU6O4K3p7FWDg2awJsatQF-yNj24lbW4IOUEaPerofWtx2hAiHECVQOgO2mUtBWf36sxMsv80NFGNK5HmN2PEEcgLlzoYAu0lEZkXWnuhWf0sjvp_dXwkr8aKJpouDkByIDFEZWAAOv6BSbLoq0pEVC4QXRsxALRg1l1x1OmUqJO7qyYr3pgM80sKWhIGeQIkxiKrmbZ2SFgIN7vKALVqz4gzh-K_jAzKMaKCuA7ZEhehU1dQORJVrRcRgUT_M7TzYdyV8SmQLrmJs4BAR2SFYx11L6MbCJc2Gq3iw");
        userDefault.setRefreshToken("dc56b01eb7bb47c5a935f99704b1bc06921fee4e9990d24145c21817b220115b");

        startActivity(intent);
        getActivity().finish();

    }


    //OTP
    private void callOTP(String otp) {
        if (!activity.checkConnection()) {
            clearDialog();
            return;
        }

        if (dialogOTP != null && dialogOTP.isShowing()) dialogOTP.dismiss();
        DialogUtil.sProDialog(getActivity());

        if (apiService == null) apiService = activity.apiService;
        Call<LoginResponseModel> call = apiService.otp("OTPSecret",
                DataUtil.genSBUX("email=" + email +
                        "&token_otp=" + otp +
                        "&mobid=" + getString(R.string.rest_mob_id) +
                        "&mobkey=" + getString(R.string.rest_mob_key)
                ));

        call.enqueue(new Callback<LoginResponseModel>() {
            @Override
            public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {
                if (getActivity() != null) {
                    DialogUtil.hProDialog();

                    if (response.isSuccessful()) {
                        LoginResponseModel responseModel = response.body();

                        switch (response.body().getReturnCode()) {
                            case "00":
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                userDefault.setLogin(true);
                                userDefault.setEmail(txtSignInUsername.getText().toString());
                                userDefault.setAccToken(responseModel.getAccessToken());
                                userDefault.setRefreshToken(responseModel.getRefreshToken());


                                PopUpUtil.sLongToast(getActivity(), userDefault.IDLanguage() ?
                                        "Selamat Datang " + responseModel.getFirstName() : "Welcome " + responseModel.getFirstName());

                                userDefault.setOtpFlag(1);
                                startActivity(intent);
                                getActivity().finish();
                                break;

                            default:
                                PopUpUtil.sLongToast(getActivity(), response.body().getProcessMsg());
                                sDialogOTP();
                                break;
                        }
                    } else {
                        activity.
                                checkServer(String.valueOf(response.code()), response.message());
                        sDialogOTP();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponseModel> call, Throwable t) {
                if (getActivity() != null && isAdded()) {
                    activity.restFailure(t);
                    sDialogOTP();
                    DialogUtil.hProDialog();
                }
            }
        });
    }

    //Resend OTP
    private void callResendOTP() {
        if (!activity.checkConnection()) {
            clearDialog();
            return;
        }

        if (dialogOTP != null && dialogOTP.isShowing()) dialogOTP.dismiss();
        DialogUtil.sProDialog(getActivity());

        if (apiService == null) apiService = activity.apiService;

        Call<LoginResponseModel> call = apiService.otpResend("resendOTPSecret",
                DataUtil.genSBUX("email=" + email +
                        "&mobid=" + getString(R.string.rest_mob_id) +
                        "&mobkey=" + getString(R.string.rest_mob_key)
                ));

        call.enqueue(new Callback<LoginResponseModel>() {
            @Override
            public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {
                if (getActivity() != null) {
                    if (response.isSuccessful()) {
                        DialogUtil.hProDialog();
                        switch (response.body().getReturnCode()) {
                            case "00":
                                PopUpUtil.sLongToast(getActivity(), response.body().getProcessMsg());
                                break;

                            default:
                                PopUpUtil.sLongToast(getActivity(), response.body().getProcessMsg());
                                break;
                        }
                    } else {
                        DialogUtil.hProDialog();
                        activity.
                                checkServer(String.valueOf(response.code()), response.message());
                    }

                    sDialogOTP();
                }
            }

            @Override
            public void onFailure(Call<LoginResponseModel> call, Throwable t) {
                if (getActivity() != null && isAdded())
                    activity.restFailure(t);

                DialogUtil.hProDialog();
                sDialogOTP();
            }
        });
    }

    // Forgot Password
    private void callForgot(final String email) {
        if (!activity.checkConnection()) {
            clearDialog();
            return;
        }

        if (forgotDialog.isShowing()) forgotDialog.dismiss();

        DialogUtil.sProDialog(getActivity());

        if (apiService == null) apiService = activity.apiService;

        Call<ResponseBase> callForgot = apiService.forgotPwd("forgot_password",
                DataUtil.genSBUX("email=" + email +
                        "&mobid=" + getString(R.string.rest_mob_id) +
                        "&mobkey=" + getString(R.string.rest_mob_key)
                ));

        callForgot.enqueue(new Callback<ResponseBase>() {
            @Override
            public void onResponse(Call<ResponseBase> call, Response<ResponseBase> response) {
                if (getActivity() != null) {
                    if (response.isSuccessful()) {
                        DialogUtil.hProDialog();
                        PopUpUtil.sLongToast(getActivity(), response.body().getProcessMsg());
                    } else {
                        DialogUtil.hProDialog();
                        activity.
                                checkServer(String.valueOf(response.code()), response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBase> call, Throwable t) {
                if (getActivity() != null && isAdded())
                    activity.restFailure(t);

                DialogUtil.hProDialog();
            }
        });
    }

    //Sign Out
    private void callLogout(String email) {
        if (!activity.checkConnection()) {
            clearDialog();
            return;
        }

        DialogUtil.sProDialog(getActivity());

        if (apiService == null) apiService = activity.apiService;

        Call<LoginResponseModel> call = apiService.signOut("logout",
                DataUtil.genSBUX("email=" + email +
                        "&mobid=" + getString(R.string.rest_mob_id) +
                        "&mobkey=" + getString(R.string.rest_mob_key)
                ));

        call.enqueue(new Callback<LoginResponseModel>() {
            @Override
            public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {
                if (getActivity() != null) {
                    if (response.isSuccessful()) {
                        DialogUtil.hProDialog();
                        if (response.body().getReturnCode().equals("00")) {
                            Toast.makeText(getActivity(), response.body().getProcessMsg(), Toast.LENGTH_SHORT).show();
                            callLogin(txtSignInUsername.getText().toString(),
                                    txtSignInPwd.getText().toString());
                        } else {
                            PopUpUtil.sLongToast(getActivity(), response.body().getProcessMsg());
                        }
                    } else {
                        DialogUtil.hProDialog();
                        activity.checkServer(String.valueOf(response.code()), response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponseModel> call, Throwable t) {
                if (getActivity() != null && isAdded())
                    activity.restFailure(t);

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        clearDialog();
        DialogUtil.hProDialog();
    }

}
