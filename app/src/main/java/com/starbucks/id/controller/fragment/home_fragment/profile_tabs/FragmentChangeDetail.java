package com.starbucks.id.controller.fragment.home_fragment.profile_tabs;

/*
 * Created by Angga N P on 3/13/2018.
 */

import android.app.AlertDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.core.widget.NestedScrollView;

import android.os.CountDownTimer;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.starbucks.id.R;
import com.starbucks.id.controller.activity.MainActivity;
import com.starbucks.id.helper.UserDefault;
import com.starbucks.id.helper.utils.DataUtil;
import com.starbucks.id.helper.utils.DialogUtil;
import com.starbucks.id.helper.utils.PopUpUtil;
import com.starbucks.id.model.ResponseBase;
import com.starbucks.id.model.user.UserDetailModel;
import com.starbucks.id.model.user.UserResponseModel;
import com.starbucks.id.rest.ApiInterface;
//import com.stfalcon.smsverifycatcher.OnSmsCatchListener;
//import com.stfalcon.smsverifycatcher.SmsVerifyCatcher;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentChangeDetail extends Fragment {
    private static final String TAG = Fragment.class.getSimpleName();

    private UserDefault userDefault;
    private Button btnUpdate, btnCancel;
    private TextView tvHint, txtHeaderAccountInfo,phoneNumber,reqOTP,tvHintOtp;
    private TextInputLayout tilFirstName, tilLastName, tilBev, tilPhone,tilOTPS;
    private TextInputEditText txtEditName, txtEditLastName, txtEditMobile, txtEditFavBev,edOTP;
    private ImageView ivAlert,icCheck;
    private CheckBox cbSub;
    private ApiInterface apiService;
    private NestedScrollView scrollView;
    private LinearLayout tilOTP,lyOtp;
    CountDownTimer cTimer = null;
    private UserResponseModel userResponseModel;
//    private SmsVerifyCatcher smsVerifyCatcher;
    private MainActivity activity;
    private String code;
    int counter = 0;
    int otpSuccess = 0;
    public FragmentChangeDetail() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = ((MainActivity)getActivity());
        if (activity != null) userDefault = activity.getUserDefault();
        userResponseModel = activity.getUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home_profile_edit, container, false);

        setHasOptionsMenu(true);

        txtEditName = rootView.findViewById(R.id.txtEditFirstName);
        txtEditLastName = rootView.findViewById(R.id.txtEditLastName);
        txtEditMobile = rootView.findViewById(R.id.txtEditMobile);
        txtEditFavBev = rootView.findViewById(R.id.txtEditFavBev);
        btnUpdate = rootView.findViewById(R.id.btnUpdate);
        btnCancel = rootView.findViewById(R.id.btnEditCancel);
        txtHeaderAccountInfo = rootView.findViewById(R.id.textView4);
        scrollView = rootView.findViewById(R.id.NSVContainer);
        ivAlert = rootView.findViewById(R.id.ivAlert);
        cbSub = rootView.findViewById(R.id.cbSub);
        tvHint = rootView.findViewById(R.id.tvHint);
        phoneNumber = rootView.findViewById(R.id.phoneNumber);
        tilFirstName = rootView.findViewById(R.id.tilFirstName);
        tilLastName = rootView.findViewById(R.id.tilLastName);
        tilBev = rootView.findViewById(R.id.tilBev);
        tilPhone = rootView.findViewById(R.id.tilPhones);

        reqOTP = rootView.findViewById(R.id.reqOTP);
        tilOTP = rootView.findViewById(R.id.tilOTP);
        tvHintOtp = rootView.findViewById(R.id.tvHintOtp);
        edOTP = rootView.findViewById(R.id.edOTP);
        icCheck = rootView.findViewById(R.id.check);
        lyOtp = rootView.findViewById(R.id.lyOtp);

        tilOTPS=rootView.findViewById(R.id.tilOTPS);
        SetupBahasa();
        setVerifyPhone();
        setStatusKYC();
        return rootView;
    }

    private void setVerifyPhone(){
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
        if(userResponseModel.getUser().getPhoneNumberVerify()==0){
            if(userResponseModel.getUser().getAltPhoneNumber().equals("")){
                phoneNumber.setVisibility(View.GONE);
            }else{
                phoneNumber.setTextColor(getResources().getColor(R.color.gray_accent));
                phoneNumber.setTypeface(phoneNumber.getTypeface(), Typeface.ITALIC);
                phoneNumber.setText(userDefault.IDLanguage()?"Nomor ponsel terdaftar: "+"+62" + userResponseModel.getUser().getAltPhoneNumber():"Mobile Number Listed: "+"+62" + userResponseModel.getUser().getAltPhoneNumber());

            }
//            txtEditMobile.setText("+62" +userResponseModel.getUser().getPhoneNumbers().get(0).getPhoneNumber());
            txtEditMobile.setFocusable(true);
            txtEditMobile.setEnabled(true);
            tilOTP.setVisibility(View.GONE);
            tvHintOtp.setVisibility(View.GONE);
            lyOtp.setVisibility(View.VISIBLE);
            tilPhone.setVisibility(View.VISIBLE);

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

        }else{
            txtEditMobile.setText("+62" +userResponseModel.getUser().getPhoneNumbers().get(0).getPhoneNumber());

            txtEditMobile.setFocusable(false);
            txtEditMobile.setEnabled(false);
            tilOTP.setVisibility(View.GONE);
            phoneNumber.setVisibility(View.GONE);
            lyOtp.setVisibility(View.GONE);
            tvHintOtp.setVisibility(View.GONE);
        }

    }

    private void setStatusKYC(){
        if(userResponseModel.getUser().getPremiumStatusCode().equals("3") ||
                userResponseModel.getUser().getPremiumStatusCode().equals("1")){
            txtEditName.setFocusable(false);
            txtEditName.setEnabled(false);
            txtEditLastName.setFocusable(false);
            txtEditLastName.setEnabled(false);

        }else{
            txtEditName.setFocusable(true);
            txtEditName.setEnabled(true);
            txtEditLastName.setFocusable(true);
            txtEditLastName.setEnabled(true);
        }




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


    /*UI Controller*/

    private boolean isFormValidName() {
        boolean isBahasa = (userDefault.IDLanguage());

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setPositiveButton("OK", null);
        if (txtEditName.getText().toString().isEmpty()) {
            DialogUtil.sNotDialog(getActivity(),
                    isBahasa ? getString(R.string.id_val_f_name) : getString(R.string.en_val_f_name));
            return false;
        } else if (txtEditLastName.getText().toString().isEmpty()) {
            DialogUtil.sNotDialog(getActivity(),
                    isBahasa ? getString(R.string.id_val_l_name) : getString(R.string.en_val_l_name));
            return false;
        }
        return true;
    }


    private boolean isFormValid() {
        boolean isBahasa = (userDefault.IDLanguage());

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setPositiveButton("OK", null);

        if (txtEditName.getText().toString().isEmpty()) {
            DialogUtil.sNotDialog(getActivity(),
                    isBahasa ? getString(R.string.id_val_f_name) : getString(R.string.en_val_f_name));
            return false;
        } else if (txtEditLastName.getText().toString().isEmpty()) {
            DialogUtil.sNotDialog(getActivity(),
                    isBahasa ? getString(R.string.id_val_l_name) : getString(R.string.en_val_l_name));
            return false;
        } else if (txtEditMobile.getText().toString().isEmpty()) {
            DialogUtil.sNotDialog(getActivity(),
                    isBahasa ? getString(R.string.id_val_mobile) : getString(R.string.en_val_m_phone));
            return false;
        } else if (txtEditMobile.getText().toString().length() < 11) {
            DialogUtil.sNotDialog(getActivity(), isBahasa
                    ? activity.getString(R.string.id_invalid_phone)
                    : activity.getString(R.string.en_invalid_phone));
            return false;
        } else if (!txtEditMobile.getText().toString().substring(3).startsWith("8")) {
            DialogUtil.sNotDialog(getActivity(), isBahasa
                    ? activity.getString(R.string.id_invalid_phone2)
                    : activity.getString(R.string.en_invalid_phone2));
            return false;
        }else if (edOTP.getText().toString().isEmpty()) {
            DialogUtil.sNotDialog(getActivity(), isBahasa
                    ? "Masukkan nomor OTP"
                    : "Please input OTP Number");
            return false;
        }
        if (otpSuccess==0){
            DialogUtil.sNotDialog(getActivity(), isBahasa
                    ? "Verivikasi OTP terlebih dahulu"
                    : "Please verified OTP first");
            return false;
        }

        else if (edOTP.getText().toString().length() < 6) {
            DialogUtil.sNotDialog(getActivity(), isBahasa
                    ? "Nomor OTP salah"
                    : "Invalid OTP Number");
            return false;
        }

        return true;
    }

    private void SetupBahasa() {
        if (userDefault.IDLanguage()) {
            activity.setToolbarTitle(getString(R.string.change_detail_title_id));
            txtHeaderAccountInfo.setText(getString(R.string.header_personal_id));
            btnUpdate.setText(getString(R.string.update_id));
            btnCancel.setText(getString(R.string.id_cancel));
            cbSub.setText("Saya tidak ingin menerima komunikasi pemasaran langsung dari Starbucks Indonesia");
            tvHint.setText("Harap diperhatikan bahwa jika Anda anggota Starbucks dan Anda memilih untuk tidak " +
                    "menerima komunikasi pemasaran langsung dari kami, kami akan tetap mengirimkan komunikasi " +
                    "keanggotaan (termasuk namun tidak terbatas pada informasi tentang program keanggotaan, " +
                    "hadiah Starbucks, dan pembaruan keanggotaan).");

            tilFirstName.setHint(userDefault.IDLanguage()?"Nama Depan":"Firstname");
            tilLastName.setHint(userDefault.IDLanguage()?"Nama Belakang":"Lastname");
            tilPhone.setHint(userDefault.IDLanguage()?"Nomor Handphone*":"Mobile Phone*");


            tilBev.setHint(userDefault.IDLanguage()?"Minuman Favorit Anda":"My Favorite Beverage");
            tvHintOtp.setText(userDefault.IDLanguage()?"Verifikasikan nomor ponsel dengan OTP yang dikirimkan via SMS":"Verify your mobile number with OTP via SMS");
            tilOTPS.setHint(userDefault.IDLanguage()?"Masukkan nomor OTP":"Input OTP number*");
        } else {
            activity.setToolbarTitle(
                    getString(R.string.change_detail_title));
        }

        setData();

    }

    private void setData() {

        activity.setToolbarTitle(userDefault.IDLanguage() ?
                getString(R.string.change_profile_title_id) : getString(R.string.change_profile_title));

        userResponseModel = activity.getUser();
        displayData(userResponseModel);
    }

    private void displayData(final UserResponseModel userResponseModel) {
        scrollView.setVisibility(View.VISIBLE);
        UserDetailModel user = userResponseModel.getUser();

        txtEditName.setText(user.getFirstName());
        txtEditLastName.setText(user.getLastName());
        if (userResponseModel.getUser().getUserProfile().getDirectMarcomm() != null)
            if (userResponseModel.getUser().getUserProfile().getDirectMarcomm())
                cbSub.setChecked(false);
            else cbSub.setChecked(true);

//        if (user.getPhoneNumbers() != null) {
//            if (user.getPhoneNumbers().size() > 0)
//                phoneNumber.setText("Mobile Number Listed: "+"+62" + user.getPhoneNumbers().get(0).getPhoneNumber());
//        }
        if (user.getUserProfile() != null)
            txtEditFavBev.setText(user.getUserProfile().getFavBeverage());


//        txtEditMobile.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                String mob = txtEditMobile.getText().toString();
//                if (mob.length() < 3) {
//                    txtEditMobile.setText(activity.getString(R.string.phone_number_start));
//                    Selection.setSelection(txtEditMobile.getText(), txtEditMobile.getText().length());
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userResponseModel.getUser().getPremiumStatusCode().equals("0") ||
                        userResponseModel.getUser().getPremiumStatusCode().equals("2")){
                    if(userResponseModel.getUser().getPhoneNumberVerify()==0){
                        if (isFormValid())
                            sendData();
                    }else{
                        if(isFormValidName())
                        sendData();
                    }
                }else{
                        sendData();
                }

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        ivAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvHint.getVisibility() == View.VISIBLE) tvHint.setVisibility(View.GONE);
                else tvHint.setVisibility(View.VISIBLE);
            }
        });
    }

//    private void validateName(){
//        if(userResponseModel.getUser().getPremiumStatusCode().equals("3") ||
//                userResponseModel.getUser().getPremiumStatusCode().equals("1")){
//            txtEditName.setFocusable(false);
//            txtEditLastName.setFocusable(false);
//            reqOTP.setVisibility(View.GONE);
//            tilOTP.setVisibility(View.GONE);
//            tilPhone.setVisibility(View.GONE);
//            phoneNumber.setVisibility(View.GONE);
//            tvHintOtp.setVisibility(View.GONE);
//            lyOtp.setVisibility(View.GONE);
//
//        }else{
//            txtEditName.setFocusable(true);
//            txtEditLastName.setFocusable(true);
//            txtEditMobile.setFocusable(true);
//            tilOTP.setVisibility(View.GONE);
//            tilPhone.setVisibility(View.VISIBLE);
//            phoneNumber.setVisibility(View.VISIBLE);
//            tvHintOtp.setVisibility(View.VISIBLE);
//            lyOtp.setVisibility(View.VISIBLE);
//            reqOTP.setVisibility(View.VISIBLE);
//            reqOTP.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    startTimer();
//                    getSmsOtp();
//                }
//            });
//            tilOTP.setVisibility(View.VISIBLE);
//        }
//    }

    /*REST CALL*/
    public void sendData() {
        if (!activity.checkConnection()) return;
        if (apiService == null) apiService = activity.getApiService();

        scrollView.setVisibility(View.INVISIBLE);

        String mobile = txtEditMobile.getText().toString();
        if (mobile.startsWith("+62")) mobile = mobile.substring(3);
        Call<ResponseBase> callUpdate;

        if(userResponseModel.getUser().getPremiumStatusCode().equals("3") ||
                userResponseModel.getUser().getPremiumStatusCode().equals("1")){
            callUpdate = apiService.updateProfile("update_profile",
                    activity.genSBUX(
                            "card_number=" + userResponseModel.getUser().getExternalId() +
                                    "&email=" + userDefault.getEmail() +
                                    "&firstname=" + userResponseModel.getUser().getFirstName() +
                                    "&lastname=" + userResponseModel.getUser().getLastName() +
                                    "&address=Jakarta" +
                                    "&city=Jakarta" +
                                    "&zip=63111" +
                                    "&phone=" + mobile +
                                    "&direct_marcomm=" + (cbSub.isChecked() ? "false" : "true") +
                                    "&beverage=" + txtEditFavBev.getText().toString()));

        }else{
            callUpdate = apiService.updateProfile("update_profile",
                    activity.genSBUX(
                            "card_number=" + userResponseModel.getUser().getExternalId() +
                                    "&email=" + userDefault.getEmail() +
                                    "&firstname=" + txtEditName.getText().toString() +
                                    "&lastname=" + txtEditLastName.getText().toString() +
                                    "&address=Jakarta" +
                                    "&city=Jakarta" +
                                    "&zip=63111" +
                                    "&phone=" + mobile +
                                    "&direct_marcomm=" + (cbSub.isChecked() ? "false" : "true") +
                                    "&beverage=" + txtEditFavBev.getText().toString()));

        }


        callUpdate.enqueue(new Callback<ResponseBase>() {
            @Override
            public void onResponse(Call<ResponseBase> call, Response<ResponseBase> response) {
                if (getActivity() != null) {
                    if (response.isSuccessful() && response.body() != null) {
                        if (activity.successResponse(response.body().getReturnCode(), response.body().getProcessMsg())) {
                            activity.hideProgressDialog();
                            activity.showToast(response.body().getProcessMsg());
                            scrollView.setVisibility(View.VISIBLE);
                            activity.getUserData();
                        }else if (response.body().getReturnCode().equals("04")){
                            activity.hideProgressDialog();
                            activity.showToast(response.body().getProcessMsg());
                            scrollView.setVisibility(View.VISIBLE);
                            activity.getUserData();
                        }
                        else {
                            activity.showToast(response.body().getProcessMsg());
                            scrollView.setVisibility(View.INVISIBLE);
                        }
                    } else {
                        activity.checkServer(String.valueOf(response.code()), response.message());
                        scrollView.setVisibility(View.INVISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBase> call, Throwable t) {
                if (getActivity() != null && isAdded()) {
                    scrollView.setVisibility(View.INVISIBLE);
                    activity.hideProgressDialog();
                    activity.restFailure(t);
                }
            }
        });
    }

    //Get SMS OTP
    public void getSmsOtp() {
        if (!activity.checkConnection()) return;
        if (apiService == null) apiService = activity.getApiService();

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
                    if (response.isSuccessful() && response.body() != null) {
                        if(response.body().getStatus().equals("Success")){
                            otpSuccess = 1;
                            startTimer();
                            Toast.makeText(getActivity(),response.body().getProcessMsg(),Toast.LENGTH_LONG).show();
                        }else{
                            otpSuccess = 0;
                            cancelTimer();
                            Toast.makeText(getActivity(),response.body().getProcessMsg(),Toast.LENGTH_LONG).show();
                        }

                    } else {
                        activity.checkServer(String.valueOf(response.code()), response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBase> call, Throwable t) {
                if (getActivity() != null && isAdded()) {
                    activity.restFailure(t);
                }
            }
        });
    }

    public void sendSMSOTPValidation(String OTP) {
        if (!activity.checkConnection()) return;
        if (apiService == null) apiService = activity.getApiService();

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
        sendSMSOTPValidation.enqueue(new Callback<ResponseBase>() {
            @Override
            public void onResponse(Call<ResponseBase> call, Response<ResponseBase> response) {
                if (getActivity() != null) {
                    if (response.isSuccessful() && response.body() != null) {
                        PopUpUtil.sLongToast(getActivity(),response.body().getProcessMsg());
                        if(response.body().getStatus().equals("Success")){
                            counter = 1;
                            icCheck.setVisibility(View.VISIBLE);
                            icCheck.setBackgroundResource(R.drawable.ic_ok);
                            reqOTP.setVisibility(View.GONE);
                            txtEditMobile.setFocusable(false);
                            edOTP.setFocusable(false);
                            otpSuccess = 1;
                        }else{
                            icCheck.setVisibility(View.VISIBLE);
                            icCheck.setBackgroundResource(R.drawable.ic_denied);
                            counter = 0;
                            otpSuccess = 0;
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

    //start timer function
    void startTimer() {
        cTimer = new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                //Input API
                reqOTP.setClickable(false);
                int seconds = (int) (millisUntilFinished / 1000);
                reqOTP.setText(userDefault.IDLanguage()?"Memverifikasi dalam "+Integer.toString(seconds)+" detik":"Verifying in "+Integer.toString(seconds)+" seconds...");
                reqOTP.setTextColor(getResources().getColor(R.color.black_dop));
                tilOTP.setVisibility(View.VISIBLE);
                tvHintOtp.setVisibility(View.VISIBLE);
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
