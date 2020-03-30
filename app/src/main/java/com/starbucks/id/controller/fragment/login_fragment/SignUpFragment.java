package com.starbucks.id.controller.fragment.login_fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.starbucks.id.R;
import com.starbucks.id.controller.activity.LoginActivity;
import com.starbucks.id.helper.UserDefault;
import com.starbucks.id.helper.utils.DataUtil;
import com.starbucks.id.helper.utils.DialogUtil;
import com.starbucks.id.helper.utils.PopUpUtil;
import com.starbucks.id.model.ResponseBase;
import com.starbucks.id.model.login.ResponseSignUp;
import com.starbucks.id.rest.ApiInterface;
//import com.stfalcon.smsverifycatcher.OnSmsCatchListener;
//import com.stfalcon.smsverifycatcher.SmsVerifyCatcher;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpFragment extends Fragment {
    private TextInputEditText txtSignUpCardNo, txtSignUpSecurityCode, txtSignUpEmail,
            txtSignUpPwd, txtSignUpRecPwd, txtSignUpName, txtSignUpLastName,
//            txtSignUpDOB,
//            txtSignUpMobile,
            txtEditFavBev,txtEditMobile,edOTP;
    private LinearLayout dobLy;
    private Button btnSignUp;
    private TextView tvHint, txtCardHeader, txtAccountHeader, txtProfileHeader,reqOTP,btnHave,btnDont;
    private ImageView ivAlert,icCheck;
    private CheckBox cbSub;

    private UserDefault userDefault;
    private String date;

    private ImageButton ibShowPass, ibShowPass2;
    private boolean show, show2 = false;
    private TextInputLayout tilSUCardNo, tilSUCardPin, tilSUEmail,
            tilSUPwd, tilSUPwd2, tilSUName, tilSULName,
//            tilSUDob,
//            tilSUMobile,
            tilSUBev;
    private EditText txtSignUpDOB;
    private Calendar myCalendar, myCalendarMin, myCalendarMax;

    private LoginActivity activity;

//    private SmsVerifyCatcher smsVerifyCatcher;
    private String code;
    private CountDownTimer cTimer = null;
    private ApiInterface apiService;
    private int registration = 0;
    private boolean isOTP = false;
    private LinearLayout cardInfo;
    private ImageView close;
    int counter = 0;
    boolean otpValid = false;
    TextView tvRegType,phoneNumber,tvTitle1,tvTitle2,tvTitle3,tvRequired;
    public SignUpFragment() {
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

        View view = inflater.inflate(R.layout.fragment_login_sign_up, container, false);

//        txtProfileHeader = view.findViewById(R.id.textView4);
        txtCardHeader = view.findViewById(R.id.cardInformation);
//        txtAccountHeader = view.findViewById(R.id.textView3);
        txtSignUpCardNo = view.findViewById(R.id.txtSignUpCardNo);
        txtSignUpSecurityCode = view.findViewById(R.id.txtSignUpSecurityCode);
        txtSignUpEmail = view.findViewById(R.id.txtSignUpEmail);
        txtSignUpPwd = view.findViewById(R.id.txtSignUpPwd);
        txtSignUpRecPwd = view.findViewById(R.id.txtSignUpRecPwd);
        txtSignUpName = view.findViewById(R.id.txtSignUpFName);
        txtSignUpLastName = view.findViewById(R.id.txtSignUpLastName);

        txtSignUpDOB = view.findViewById(R.id.txtSignUpDOB);
        txtEditFavBev = view.findViewById(R.id.txtEditFavBev);
//        txtSignUpMobile = view.findViewById(R.id.txtSignUpMobile);
        btnSignUp = view.findViewById(R.id.btnSignUp);
        ivAlert = view.findViewById(R.id.ivAlert);
        cbSub = view.findViewById(R.id.cbSub);
        tvHint = view.findViewById(R.id.tvHint);
        dobLy = view.findViewById(R.id.dobLy);
        tilSUCardNo = view.findViewById(R.id.tilSUCardNo);
        tilSUCardPin = view.findViewById(R.id.tilSUCardPin);
        tilSUEmail = view.findViewById(R.id.tilSUEmail);
        tilSUPwd = view.findViewById(R.id.tilSUPwd);
        tilSUPwd2 = view.findViewById(R.id.tilSUPwd2);
        tilSUName = view.findViewById(R.id.tilSUName);
        tilSULName = view.findViewById(R.id.tilSULName);
//        tilSUDob = view.findViewById(R.id.tilSUDob);
//        tilSUMobile = view.findViewById(R.id.tilSUMobile);
        tilSUBev = view.findViewById(R.id.tilSUBev);
        close = view.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.cFragmentWithBS(new LoginFragment());
            }
        });
        ibShowPass = view.findViewById(R.id.ibShowPass);
        ibShowPass2 = view.findViewById(R.id.ibShowPass2);

        reqOTP = view.findViewById(R.id.reqOTP);
        txtEditMobile = view.findViewById(R.id.txtEditMobile);
        edOTP = view.findViewById(R.id.edOTP);
        icCheck = view.findViewById(R.id.check);

        btnHave = view.findViewById(R.id.btnHave);
        btnDont = view.findViewById(R.id.btnDont);
        tvRegType = view.findViewById(R.id.tvRegType);
        phoneNumber = view.findViewById(R.id.phoneNumber);
        tvTitle1 = view.findViewById(R.id.tvTitle1);
        tvTitle2 = view.findViewById(R.id.tvTitle2);
        tvTitle3 = view.findViewById(R.id.tvTitle3);
        tvRequired = view.findViewById(R.id.tvRequired);
        cardInfo = view.findViewById(R.id.cardInfo);
        cardInfo.setVisibility(View.GONE);
        SetupBahasa();
        setOTP();
        setRegistration();
        closeField();
        return view;
    }



    private void SetupBahasa() {
        reqOTP.setText(userDefault.IDLanguage()?"KIRIM OTP":"SEND OTP");
        btnDont.setText(userDefault.IDLanguage()?"SAYA TIDAK MEMILIKI KARTU STARBUCKS":" I DO NOT HAVE A STARBUCKS CARD");
        btnHave.setText(userDefault.IDLanguage()?"SAYA MEMILIKI KARTU STARBUCKS":"I HAVE A STARBUCKS CARD");
        tvRegType.setText(userDefault.IDLanguage()?"Pilih cara pendaftaran":"Choose your registration type");
        phoneNumber.setText(userDefault.IDLanguage()?"Verifikasikan nomor ponsel Anda dengan OTP yang dikirimkan via SMS":"Verify your mobile number with OTP via SMS");
        tvTitle1.setText(userDefault.IDLanguage()?"Silakan memasukkan nomor ponsel Anda":"Please input your mobile number");
        tvTitle2.setText(userDefault.IDLanguage()?"Silakan masukkan informasi akun Anda":"Please input your account information");
        tvTitle3.setText(userDefault.IDLanguage()?"Silakan masukkan profil Anda":"Please input your profile");
        tvRequired.setText(userDefault.IDLanguage()?"*Dibutuhkan":"*Required");
        txtEditMobile.setHint(userDefault.IDLanguage()?"Nomor handphone":"Mobile phone*");
        edOTP.setHint(userDefault.IDLanguage()?"Masukkan nomor OTP":"Input OTP number*");
        txtCardHeader.setText(userDefault.IDLanguage()?"Masukkan informasi kartu Anda":"Please input your card information");
        if (userDefault.IDLanguage()) {
//            txtProfileHeader.setText(getString(R.string.id_signup_prof_header));
//            txtAccountHeader.setText(getString(R.string.id_signup_acc_header));
//            txtCardHeader.setText(getString(R.string.id_signup_card_header));

            tilSUCardNo.setHint(getString(R.string.id_hint_card_number));
            tilSUCardPin.setHint(getString(R.string.id_hint_security_code));
            tilSUEmail.setHint(getString(R.string.id_hint_username));
            tilSUPwd.setHint(getString(R.string.id_hint_pwd));
            tilSUPwd2.setHint(getString(R.string.id_hint_rec_pwd));

            tilSUName.setHint(getString(R.string.id_hint_first_name));
            tilSULName.setHint(getString(R.string.id_hint_last_name));

//            tilSUDob.setHint(getString(R.string.id_hint_dob));
//            tilSUMobile.setHint(getString(R.string.id_hint_mobile_phone));

            tilSUBev.setHint(getString(R.string.hint_beverage_id));
            btnSignUp.setText(getString(R.string.id_sign_up));

            cbSub.setText(R.string.id_val_cb_signup);
            tvHint.setText(R.string.id_val_hint_signup);


        }

        setCalendar();
    }

    private void setCalendar(){
        myCalendar = Calendar.getInstance();
        myCalendar.set(Calendar.YEAR, 2000);
        myCalendar.set(Calendar.DAY_OF_YEAR, 1);

        myCalendarMin = Calendar.getInstance();
        myCalendarMin.set(Calendar.YEAR, 2000);
        myCalendarMin.set(Calendar.DAY_OF_YEAR, 1);

        myCalendarMax = Calendar.getInstance();
        myCalendarMax.set(Calendar.YEAR, 2000);
        myCalendarMax.set(Calendar.MONTH, 11);
        myCalendarMax.set(Calendar.DAY_OF_MONTH, 31);

        setAct();
    }

    private void setAct() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFormValid()) signUp();
            }
        });

        txtSignUpDOB.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) sDialogCalendar();
//                if(hasFocus)PopUpUtil.getCalendar(getActivity(), txtSignUpDOB, true);
            }
        });

        txtSignUpDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                sDialogCalendar();
//                PopUpUtil.getCalendar(getActivity(), txtSignUpDOB, true);
            }
        });

//
//        txtSignUpMobile.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                String mob = txtSignUpMobile.getText().toString();
//                if (mob.length() < 3) {
//                    txtSignUpMobile.setText(R.string.id_phone_code);
//                    Selection.setSelection(txtSignUpMobile.getText(), txtSignUpMobile.getText().length());
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//            }
//        });

        ivAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvHint.getVisibility() == View.VISIBLE) tvHint.setVisibility(View.GONE);
                else tvHint.setVisibility(View.VISIBLE);
            }
        });

        ibShowPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!show) {
                    ibShowPass.setImageResource(R.drawable.ic_action_show);
                    txtSignUpPwd.setTransformationMethod(null);
                    txtSignUpPwd.setSelection(txtSignUpPwd.getText().length());
                } else {
                    ibShowPass.setImageResource(R.drawable.ic_action_hide);
                    txtSignUpPwd.setTransformationMethod(new PasswordTransformationMethod());
                    txtSignUpPwd.setSelection(txtSignUpPwd.getText().length());
                }
                show = !show;
            }
        });

        ibShowPass2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!show2) {
                    ibShowPass2.setImageResource(R.drawable.ic_action_show);
                    txtSignUpRecPwd.setTransformationMethod(null);
                    txtSignUpRecPwd.setSelection(txtSignUpRecPwd.getText().length());
                } else {
                    ibShowPass2.setImageResource(R.drawable.ic_action_hide);
                    txtSignUpRecPwd.setTransformationMethod(new PasswordTransformationMethod());
                    txtSignUpRecPwd.setSelection(txtSignUpRecPwd.getText().length());
                }
                show2 = !show2;
            }
        });
    }

    private void updateLabel() {
        String myFormat = "dd MMM"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        String myFormat2 = "MM/dd"; //data to send
        SimpleDateFormat sdf2 = new SimpleDateFormat(myFormat2, Locale.US);

        txtSignUpDOB.setText(sdf.format(myCalendar.getTime()));
        date = sdf2.format(myCalendar.getTime());
    }

    private boolean isFormValid() {
        boolean isBahasa = (userDefault.IDLanguage());

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setPositiveButton("OK", null);

        if(registration == 1){
            if (txtEditMobile.getText().toString().isEmpty()) {
                DialogUtil.sNotDialog(getActivity(),
                        isBahasa ? getString(R.string.id_val_mobile) : getString(R.string.val_mobile));
                return false;
            } else if (txtEditMobile.getText().toString().length() < 9) {
                DialogUtil.sNotDialog(getActivity(),
                        isBahasa ?getString(R.string.id_val_wrong_mobile) : getString(R.string.val_wrong_mobile));
                return false;
            }else  if (!txtEditMobile.getText().toString().substring(3).startsWith("8")) {
                DialogUtil.sNotDialog(getActivity(),
                        isBahasa ?getString(R.string.id_val_wrong_mobile) : getString(R.string.val_wrong_mobile));
                return false;
            } else if (edOTP.getText().toString().isEmpty()) {
                DialogUtil.sNotDialog(getActivity(),
                        isBahasa ? getString(R.string.id_val_otp) : getString(R.string.val_otp));
                return false;
            } else if (otpValid =false) {
                DialogUtil.sNotDialog(getActivity(),
                        isBahasa ? "Mohon validasi nomor handphone anda" : "Please validate your phone number");
                return false;
            }else if (txtSignUpEmail.getText().toString().isEmpty()) {
                DialogUtil.sNotDialog(getActivity(),
                        isBahasa ? getString(R.string.id_val_username) : getString(R.string.val_username));
                return false;

            } else if (!DataUtil.isValidEmail(txtSignUpEmail.getText().toString())) {
                DialogUtil.sNotDialog(getActivity(),
                        isBahasa ? getString(R.string.id_val_email_valid) : getString(R.string.val_email_valid));
                return false;

            } else if (txtSignUpPwd.getText().toString().isEmpty()) {
                DialogUtil.sNotDialog(getActivity(),
                        isBahasa ? getString(R.string.id_val_pwd) : getString(R.string.val_pwd));
                return false;

            } else if (txtSignUpPwd.getText().toString().length() < 8) {
                DialogUtil.sNotDialog(getActivity(),
                        isBahasa ? getString(R.string.id_val_pwd_8) : getString(R.string.en_val_pwd_8));
                return false;
            } else if (!isValidPassword(txtSignUpPwd.getText().toString())) {
                DialogUtil.sNotDialog(getActivity(),
                        isBahasa ? getString(R.string.id_val_pwd_comb) : getString(R.string.en_val_pwd_comb));
                return false;
            } else if (txtSignUpRecPwd.getText().toString().isEmpty()) {
                DialogUtil.sNotDialog(getActivity(),
                        isBahasa ? getString(R.string.id_val_r_pwd) : getString(R.string.val_r_pwd));
                return false;
            } else if (!txtSignUpPwd.getText().toString().equals(txtSignUpRecPwd.getText().toString())) {
                DialogUtil.sNotDialog(getActivity(),
                        isBahasa ? getString(R.string.id_val_pwd_not_match) : getString(R.string.val_pwd_not_match));
                return false;

            } else if (txtSignUpName.getText().toString().isEmpty()) {
                DialogUtil.sNotDialog(getActivity(),
                        isBahasa ? getString(R.string.id_val_f_name) : getString(R.string.val_f_name));
                return false;
            } else if (txtSignUpLastName.getText().toString().isEmpty()) {
                DialogUtil.sNotDialog(getActivity(),
                        isBahasa ? getString(R.string.id_val_l_name) : getString(R.string.val_l_name));
                return false;
            } else if (txtSignUpDOB.getText().toString().isEmpty()) {
                DialogUtil.sNotDialog(getActivity(),
                        isBahasa ? getString(R.string.id_val_dob) : getString(R.string.val_dob));
                return false;
            }

            else if (txtSignUpCardNo.getText().toString().isEmpty()) {
                DialogUtil.sNotDialog(getActivity(),
                        isBahasa ? getString(R.string.id_val_card_num) : getString(R.string.val_card_num));
                return false;

            } else if (txtSignUpCardNo.getText().toString().length() < 16) {
                DialogUtil.sNotDialog(getActivity(),
                        isBahasa ? getString(R.string.id_sbux_card_16_digit) : getString(R.string.sbux_card_16_digit));
                return false;

            } else if (txtSignUpSecurityCode.getText().toString().isEmpty()) {
                DialogUtil.sNotDialog(getActivity(),
                        isBahasa ? getString(R.string.id_val_sec_code) : getString(R.string.val_sec_code));
                return false;

            } else if (!txtSignUpSecurityCode.getText().toString().isEmpty() && txtSignUpSecurityCode.getText().toString().length() < 8) {
                DialogUtil.sNotDialog(getActivity(),
                        isBahasa ? getString(R.string.id_val_sc_8) : getString(R.string.val_sc_8));
                return false;

            }


        }
        else{
            if (txtEditMobile.getText().toString().isEmpty()) {
                DialogUtil.sNotDialog(getActivity(),
                        isBahasa ? getString(R.string.id_val_mobile) : getString(R.string.val_mobile));
                return false;
            } else if (txtEditMobile.getText().toString().length() < 9) {
                DialogUtil.sNotDialog(getActivity(),
                        isBahasa ?getString(R.string.id_val_wrong_mobile) : getString(R.string.val_wrong_mobile));
                return false;
            }else  if (!txtEditMobile.getText().toString().substring(3).startsWith("8")) {
                DialogUtil.sNotDialog(getActivity(),
                        isBahasa ?getString(R.string.id_val_wrong_mobile) : getString(R.string.val_wrong_mobile));
                return false;
            }  else if (edOTP.getText().toString().isEmpty()) {
                DialogUtil.sNotDialog(getActivity(),
                        isBahasa ? getString(R.string.id_val_otp) : getString(R.string.val_otp));
                return false;
            } else if (otpValid =false) {
                DialogUtil.sNotDialog(getActivity(),
                        isBahasa ? "Mohon validasi nomor handphone anda" : "Please validate your phone number");
                return false;
            } else if (txtSignUpEmail.getText().toString().isEmpty()) {
                DialogUtil.sNotDialog(getActivity(),
                        isBahasa ? getString(R.string.id_val_username) : getString(R.string.val_username));
                return false;
            } else if (!DataUtil.isValidEmail(txtSignUpEmail.getText().toString())) {
                DialogUtil.sNotDialog(getActivity(),
                        isBahasa ? getString(R.string.id_val_email_valid) : getString(R.string.val_email_valid));
                return false;
            } else if (txtSignUpPwd.getText().toString().isEmpty()) {
                DialogUtil.sNotDialog(getActivity(),
                        isBahasa ? getString(R.string.id_val_pwd) : getString(R.string.val_pwd));
                return false;
            } else if (txtSignUpPwd.getText().toString().length() < 8) {
                DialogUtil.sNotDialog(getActivity(),
                        isBahasa ? getString(R.string.id_val_pwd_8) : getString(R.string.en_val_pwd_8));
                return false;
            } else if (!isValidPassword(txtSignUpPwd.getText().toString())) {
                DialogUtil.sNotDialog(getActivity(),
                        isBahasa ? getString(R.string.id_val_pwd_comb) : getString(R.string.en_val_pwd_comb));
                return false;
            } else if (txtSignUpRecPwd.getText().toString().isEmpty()) {
                DialogUtil.sNotDialog(getActivity(),
                        isBahasa ? getString(R.string.id_val_r_pwd) : getString(R.string.val_r_pwd));
                return false;
            } else if (!txtSignUpPwd.getText().toString().equals(txtSignUpRecPwd.getText().toString())) {
                DialogUtil.sNotDialog(getActivity(),
                        isBahasa ? getString(R.string.id_val_pwd_not_match) : getString(R.string.val_pwd_not_match));
                return false;

            } else if (txtSignUpName.getText().toString().isEmpty()) {
                DialogUtil.sNotDialog(getActivity(),
                        isBahasa ? getString(R.string.id_val_f_name) : getString(R.string.val_f_name));
                return false;
            } else if (txtSignUpLastName.getText().toString().isEmpty()) {
                DialogUtil.sNotDialog(getActivity(),
                        isBahasa ? getString(R.string.id_val_l_name) : getString(R.string.val_l_name));
                return false;
            }
            else if (txtSignUpDOB.getText().toString().isEmpty()) {
                DialogUtil.sNotDialog(getActivity(),
                        isBahasa ? getString(R.string.id_val_dob) : getString(R.string.val_dob));
                return false;
            }
        }
        return true;
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


    /*REST Call*/
    private void signUp() {
        if (!activity.checkConnection()) return;

        DialogUtil.sProDialog(getActivity());

        String[] arrDOB = date.split("/");

        String dob = arrDOB[0] + "-" + arrDOB[1];

        String mobile = txtEditMobile.getText().toString();
        if (mobile.startsWith("+62")) mobile = mobile.substring(3);

        apiService = ((LoginActivity)getActivity()).apiService;

        Call<ResponseSignUp> call;

        if(registration==1){
            call = apiService.signUp("registration",
                    DataUtil.genSBUX("mobid=" + getString(R.string.rest_mob_id) +
                            "&mobkey=" + getString(R.string.rest_mob_key) +
                            "&card_number=" + txtSignUpCardNo.getText().toString() +
                            "&card_pin=" + txtSignUpSecurityCode.getText().toString() +
                            "&email=" + txtSignUpEmail.getText().toString() +
                            "&password=" + txtSignUpPwd.getText().toString() +
                            "&firstname=" + txtSignUpName.getText().toString() +
                            "&lastname=" + txtSignUpLastName.getText().toString() +
                            "&phone=" + mobile +
                            "&dob=" + dob +
                            "&direct_marcomm=" + (cbSub.isChecked() ? "false" : "true") +
                            "&beverage=" + txtEditFavBev.getText().toString()
                    ));
        }else{
            call = apiService.signUp("registration",
                    DataUtil.genSBUX("mobid=" + getString(R.string.rest_mob_id) +
                            "&mobkey=" + getString(R.string.rest_mob_key) +
                            "&card_number=" + " " +
                            "&card_pin=" + " " +
                            "&email=" + txtSignUpEmail.getText().toString() +
                            "&password=" + txtSignUpPwd.getText().toString() +
                            "&firstname=" + txtSignUpName.getText().toString() +
                            "&lastname=" + txtSignUpLastName.getText().toString() +
                            "&phone=" + mobile +
                            "&dob=" + dob +
                            "&direct_marcomm=" + (cbSub.isChecked() ? "false" : "true") +
                            "&beverage=" + txtEditFavBev.getText().toString()
                    ));
        }




        call.enqueue(new Callback<ResponseSignUp>() {
            @Override
            public void onResponse(Call<ResponseSignUp> call, Response<ResponseSignUp> response) {
                if (getActivity() != null) {
                    if (response.isSuccessful() && response.body() != null) {
                        ResponseSignUp rsu = response.body();
                        if (rsu.getReturnCode().equals("00")) {
                            PopUpUtil.sLongToast(getActivity(), userDefault.IDLanguage() ?
                                    "Pendaftaran Berhasil, Silahkan Sign in" :
                                    "Registration Successfull, Please Sign In");
                            activity.cFragmentNoBs(new LoginFragment());
                        } else PopUpUtil.sLongToast(getActivity(), rsu.getProcessMsg());
                    } else {
                        openField();
                        activity.checkServer(String.valueOf(response.code()), response.message());
                    }

                    DialogUtil.hProDialog();
                }
            }

            @Override
            public void onFailure(Call<ResponseSignUp> call, Throwable t) {
                if (getActivity() != null && isAdded()) {
                    openField();
                    activity.restFailure(t);
                    DialogUtil.hProDialog();
                }
            }
        });
    }

    private void sDialogCalendar() {
        DatePickerDialog dp = new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateLabel();
                        }
                    },
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
        );

        dp.getDatePicker().setMinDate(myCalendarMin.getTimeInMillis());
        dp.getDatePicker().setMaxDate(myCalendarMax.getTimeInMillis());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_title, null);
        TextView tv = dialogView.findViewById(R.id.detailDescription);
        tv.setText(userDefault.IDLanguage() ? getString(R.string.id_dob) : getString(R.string.dob));

        dp.setCustomTitle(dialogView);
            dp.getDatePicker().findViewById(getResources().
                    getIdentifier("year", "id", "android")).setVisibility(View.GONE);
            dp.setCanceledOnTouchOutside(false);
            dp.show();

    }



    //OTP
    private void setRegistration(){
        btnDont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnDont.setBackgroundResource(R.drawable.border_sign_up);
                btnDont.setTextColor(getResources().getColor(R.color.greenPrimary));
                btnHave.setBackgroundResource(0);
                btnHave.setTextColor(getResources().getColor(R.color.black_dop));
                registration = 0;
                cardInfo.setVisibility(View.GONE);

            }
        });
        btnHave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnDont.setBackgroundResource(0);
                btnDont.setTextColor(getResources().getColor(R.color.black_dop));
                btnHave.setBackgroundResource(R.drawable.border_sign_up);
                btnHave.setTextColor(getResources().getColor(R.color.greenPrimary));
                registration = 1;
                cardInfo.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setOTP(){
        txtEditMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String mob = txtEditMobile.getText().toString();
                if (mob.length() < 3) {
                    txtEditMobile.setText(activity.getString(R.string.phone_number_start));
                    Selection.setSelection(txtEditMobile.getText(), txtEditMobile.getText().length());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edOTP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String mob = edOTP.getText().toString();
                if (mob.length() == 6) {
                    counter = 1;
                    sendSMSOTPValidation(mob);
                }
            }
        });

        icCheck.setVisibility(View.INVISIBLE);

        reqOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtEditMobile.getText().toString().length() < 9) {
                    Toast.makeText(getActivity(),userDefault.IDLanguage()?getString(R.string.id_val_wrong_mobile) : getString(R.string.val_wrong_mobile),Toast.LENGTH_LONG).show();
                }else if(txtEditMobile.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(),userDefault.IDLanguage()?getString(R.string.id_val_mobile) : getString(R.string.val_mobile),Toast.LENGTH_LONG).show();
                }else if(!txtEditMobile.getText().toString().substring(3).startsWith("8")){
                    Toast.makeText(getActivity(),userDefault.IDLanguage()?getString(R.string.id_val_wrong_mobile) : getString(R.string.val_wrong_mobile),Toast.LENGTH_LONG).show();
                }else{
                    activity.showProgressDialog();
                    getSmsOtp();
                }
            }
        });
//        smsVerifyCatcher = new SmsVerifyCatcher(getActivity(), new OnSmsCatchListener<String>() {
//            @Override
//            public void onSmsCatch(String message) {
//                code = parseCode(message);//Parse verification code
//                edOTP.setText(code);//set code in edit text
//                if(counter == 0){
//                    sendSMSOTPValidation(code);
//                    counter = 1;
//                }
//
//                //then you can send verification code to server
//            }
//        });
//        smsVerifyCatcher.setPhoneNumberFilter("StarbucksID");
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        smsVerifyCatcher.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }

    private String parseCode(String message) {
        Pattern p = Pattern.compile("\\b\\d{6}\\b");
        Matcher m = p.matcher(message);
        String code = "";
        while (m.find()) {
            code = m.group(0);
        }
        return code;
    }

    public void getSmsOtp() {
        if (!activity.checkConnection()) return;
        if (apiService == null) apiService = ((LoginActivity)getActivity()).apiService;

//        scrollView.setVisibility(View.INVISIBLE);

//        activity.showProgressDialog();

        String mobile = txtEditMobile.getText().toString();
        if (mobile.startsWith("+62")) mobile = mobile.substring(3);

        Call<ResponseBase> callSmsOtp = apiService.getSMSOTP("smsotp",
                DataUtil.genSBUX(
                        "mobid=" + getString(R.string.rest_mob_id) +
                                "&mobkey=" +getString(R.string.rest_mob_key) +
                                "&phone=" + mobile));
        Log.e("Mobile",mobile);

        callSmsOtp.enqueue(new Callback<ResponseBase>() {
            @Override
            public void onResponse(Call<ResponseBase> call, Response<ResponseBase> response) {
                activity.hideProgressDialog();
                if (getActivity() != null) {
                    if(response.body().getStatus().equals("Success")){
                        startTimer();
                        Toast.makeText(getActivity(),response.body().getProcessMsg(),Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getActivity(),response.body().getProcessMsg(),Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBase> call, Throwable t) {
                if (getActivity() != null && isAdded()) {
//                    scrollView.setVisibility(View.INVISIBLE);
//                    activity.hideProgressDialog();
                    activity.restFailure(t);
                }
            }
        });
    }

    public void sendSMSOTPValidation(String OTP) {
        if (!activity.checkConnection()) return;
        if (apiService == null) apiService = ((LoginActivity)getActivity()).apiService;

//        scrollView.setVisibility(View.INVISIBLE);

//        activity.showProgressDialog();

        String mobile = txtEditMobile.getText().toString();
        if (mobile.startsWith("+62")) mobile = mobile.substring(3);

        Call<ResponseBase> sendSMSOTPValidation = apiService.sendSMSOTPValidation("smsotp_validation",
                DataUtil.genSBUX(
                        "mobid=" + getString(R.string.rest_mob_id) +
                                "&mobkey=" +getString(R.string.rest_mob_key) +
                                "&phone=" + mobile +
                                "&sms_otp=" + OTP));
        Log.e("OTP",OTP);
        activity.showProgressDialog();
        sendSMSOTPValidation.enqueue(new Callback<ResponseBase>() {
            @Override
            public void onResponse(Call<ResponseBase> call, Response<ResponseBase> response) {
                if (getActivity() != null) {
                    if (response.isSuccessful() && response.body() != null) {
                        activity.hideProgressDialog();
                        PopUpUtil.sLongToast(getActivity(),response.body().getProcessMsg());
                        if(response.body().getStatus().equals("Success")){
                            counter = 1;
                            openField();
                            otpValid = true;
                            icCheck.setVisibility(View.VISIBLE);
                            icCheck.setBackgroundResource(R.drawable.ic_ok);
                            reqOTP.setVisibility(View.GONE);
                            txtEditMobile.setFocusable(false);
                            edOTP.setFocusable(false);
                        }else{
                            icCheck.setVisibility(View.VISIBLE);
                            icCheck.setBackgroundResource(R.drawable.ic_denied);
                            counter = 0;
                            closeField();
                            otpValid = false;
                        }

                    } else {
                        activity.checkServer(String.valueOf(response.code()), response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBase> call, Throwable t) {
                if (getActivity() != null && isAdded()) {
//                    scrollView.setVisibility(View.INVISIBLE);
//                    activity.hideProgressDialog();
                    activity.restFailure(t);
                }
            }
        });
    }


    public void openField(){
        tilSUEmail.setBackgroundColor(getResources().getColor(R.color.white));
        txtSignUpEmail.setEnabled(true);

        tilSUPwd.setBackgroundColor(getResources().getColor(R.color.white));
        txtSignUpPwd.setEnabled(true);

        tilSUPwd2.setBackgroundColor(getResources().getColor(R.color.white));
        txtSignUpRecPwd.setEnabled(true);

        tilSUName.setBackgroundColor(getResources().getColor(R.color.white));
        txtSignUpName.setEnabled(true);

        tilSULName.setBackgroundColor(getResources().getColor(R.color.white));
        txtSignUpLastName.setEnabled(true);

//        tilSUDob.setBackgroundColor(getResources().getColor(R.color.white));
//        txtSignUpDOB.setEnabled(true);
//
//        tilSUDob.setBackgroundColor(getResources().getColor(R.color.white));
//        txtSignUpDOB.setEnabled(true);

        dobLy.setBackgroundColor(getResources().getColor(R.color.white));
        txtSignUpDOB.setEnabled(true);

        tilSUBev.setBackgroundColor(getResources().getColor(R.color.white));
        txtEditFavBev.setEnabled(true);

        tilSUCardNo.setBackgroundColor(getResources().getColor(R.color.white));
        txtSignUpCardNo.setEnabled(true);

        tilSUCardPin.setBackgroundColor(getResources().getColor(R.color.white));
        txtSignUpSecurityCode.setEnabled(true);
    }

    public void closeField(){
        tilSUEmail.setBackgroundColor(getResources().getColor(R.color.gray_dop));
        txtSignUpEmail.setEnabled(false);

        tilSUPwd.setBackgroundColor(getResources().getColor(R.color.gray_dop));
        txtSignUpPwd.setEnabled(false);

        tilSUPwd2.setBackgroundColor(getResources().getColor(R.color.gray_dop));
        txtSignUpRecPwd.setEnabled(false);

        tilSUName.setBackgroundColor(getResources().getColor(R.color.gray_dop));
        txtSignUpName.setEnabled(false);

        tilSULName.setBackgroundColor(getResources().getColor(R.color.gray_dop));
        txtSignUpLastName.setEnabled(false);

//        tilSUDob.setBackgroundColor(getResources().getColor(R.color.gray_dop));
//        txtSignUpDOB.setEnabled(false);
//
//        tilSUDob.setBackgroundColor(getResources().getColor(R.color.gray_dop));
//        txtSignUpDOB.setEnabled(false);
        dobLy.setBackgroundColor(getResources().getColor(R.color.gray_dop));
        txtSignUpDOB.setEnabled(false);

        tilSUBev.setBackgroundColor(getResources().getColor(R.color.gray_dop));
        txtEditFavBev.setEnabled(false);

        tilSUCardNo.setBackgroundColor(getResources().getColor(R.color.gray_dop));
        txtSignUpCardNo.setEnabled(false);

        tilSUCardPin.setBackgroundColor(getResources().getColor(R.color.gray_dop));
        txtSignUpSecurityCode.setEnabled(false);
    }

    //start timer function
    void startTimer() {
        cTimer = new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                //Input API
                reqOTP.setClickable(false);
                int seconds = (int) (millisUntilFinished / 1000);
                reqOTP.setText(userDefault.IDLanguage()?"Memverifikasi dalam "+Integer.toString(seconds)+" detik":"Verifying in "+Integer.toString(seconds)+" seconds...");
                reqOTP.setTextColor(getResources().getColor(R.color.black_dop));
            }
            public void onFinish() {
                cancelTimer();
                reqOTP.setClickable(true);
                reqOTP.setText(userDefault.IDLanguage()?"Kirim OTP":"Send OTP");
                reqOTP.setTextColor(getResources().getColor(R.color.greenPrimary));
            }
        };
        cTimer.start();
    }

    //cancel timer
    void cancelTimer() {
        if(cTimer!=null)
            cTimer.cancel();
    }

    @Override
    public void onStart() {
        super.onStart();
        cancelTimer();
//        smsVerifyCatcher.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        cancelTimer();
//        smsVerifyCatcher.onStop();
    }
    @Override
    public void onPause() {
        super.onPause();
        cancelTimer();
    }
}
