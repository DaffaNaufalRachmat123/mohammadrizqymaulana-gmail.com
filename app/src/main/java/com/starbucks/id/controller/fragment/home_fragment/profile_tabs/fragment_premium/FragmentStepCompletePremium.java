package com.starbucks.id.controller.fragment.home_fragment.profile_tabs.fragment_premium;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.starbucks.id.R;
import com.starbucks.id.controller.activity.MainActivity;
import com.starbucks.id.helper.ConnectionDetector;
import com.starbucks.id.helper.StarbucksID;
import com.starbucks.id.helper.UserDefault;
import com.starbucks.id.helper.utils.DataUtil;
import com.starbucks.id.helper.utils.DialogUtil;
import com.starbucks.id.helper.utils.PopUpUtil;
import com.starbucks.id.model.ResponseBase;
import com.starbucks.id.model.user.UserResponseModel;
import com.starbucks.id.rest.ApiInterface;
//import com.stfalcon.smsverifycatcher.OnSmsCatchListener;
//import com.stfalcon.smsverifycatcher.SmsVerifyCatcher;

import java.io.ByteArrayOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentStepCompletePremium extends Fragment {
    private static MainActivity activity;
    private UserDefault userDefault;
    //    Bitmap bitmapIdCard,bitmapSelfie;
    ImageView idCard, selfie, icCheck;
    TextView retake, reqOTP,tvPhone;
    EditText firstName, lastname, idNumber, dob, address,
            occupation, txtEditMobile, edOTP,village,district,city,region,zipCode;

    private TextView fsNameLabel,lsNameLabel,idCardLabel,
            idNumberLabel,genderLabel,addressLabel,cityLabel,
            regionLabel,districtLabel,villageLabel,
            zipcodeLabel,countryLabel,occupationLabel,dobLabel,
            tvDetails;

    Spinner spinnerIdCard, spinnerGender, spinnerCountry;
    Button submit;
    String choosedItemIdCard, choosedItemGender, choosedItemCountry;
    StarbucksID app;
    LinearLayout ly1, ly2,tilPhone,tilOTP;
    String[] itemSpinnerIdCardEn = {"KTP", "KITAS"};
    String[] itemSpinnerIdCardId = {"KTP", "KITAS"};
    String[] itemSpinnerGenderEn = {"Male", "Female"};
    String[] itemSpinnerGenderId = {"Pria", "Wanita"};
    String[] itemSpinnerCountryEn = {"Indonesia","Others"};
    String[] itemSpinnerCountryId = {"Indonesia","Lainnya"};
    private String code;
    private CountDownTimer cTimer = null;
    ProgressBar pb;
    int counter = 0;
    private UserResponseModel userResponseModel;
    TextView tvIdPhoto,selfieTv,submitTv,phoneNumber;

//    private SmsVerifyCatcher smsVerifyCatcher;


    public FragmentStepCompletePremium() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = ((MainActivity) getActivity());
        if (activity != null) userDefault = activity.getUserDefault();
        app = StarbucksID.Companion.getInstance();
        userResponseModel = activity.getUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_step_complete_premiums, container, false);
        pb = rootView.findViewById(R.id.progressBar);
        idCard = rootView.findViewById(R.id.idCard);
        selfie = rootView.findViewById(R.id.selfie);
        retake = rootView.findViewById(R.id.retake);
        firstName = rootView.findViewById(R.id.firstName);
        lastname = rootView.findViewById(R.id.lastName);
        dob = rootView.findViewById(R.id.dob);
        village = rootView.findViewById(R.id.village);
        district = rootView.findViewById(R.id.district);
        city = rootView.findViewById(R.id.city);
        address = rootView.findViewById(R.id.address);

        region = rootView.findViewById(R.id.region);
        zipCode = rootView.findViewById(R.id.zipCode);
        occupation = rootView.findViewById(R.id.occupation);

        spinnerCountry = rootView.findViewById(R.id.country);
        spinnerIdCard = rootView.findViewById(R.id.spinnerIdCard);
        spinnerGender = rootView.findViewById(R.id.spinnerGender);

        idNumber = rootView.findViewById(R.id.idNumber);
        submit = rootView.findViewById(R.id.submit);
        ly1 = rootView.findViewById(R.id.ly1);
        ly2 = rootView.findViewById(R.id.ly2);
        reqOTP = rootView.findViewById(R.id.reqOTP);
        icCheck = rootView.findViewById(R.id.check);
        txtEditMobile = rootView.findViewById(R.id.txtEditMobile);
        edOTP = rootView.findViewById(R.id.edOTP);
        tvIdPhoto = rootView.findViewById(R.id.tvIdPhoto);
        selfieTv = rootView.findViewById(R.id.selfieTv);
        submitTv = rootView.findViewById(R.id.submitTv);
        tilPhone = rootView.findViewById(R.id.tilPhone);
        tilOTP = rootView.findViewById(R.id.tilOTP);
        phoneNumber = rootView.findViewById(R.id.phoneNumber);
        tvPhone = rootView.findViewById(R.id.tvPhone);

        fsNameLabel= rootView.findViewById(R.id.fsNameLabel);
        lsNameLabel= rootView.findViewById(R.id.lsNameLabel);
        idCardLabel= rootView.findViewById(R.id.idCardLabel);
        idNumberLabel= rootView.findViewById(R.id.idNumberLabel);
        genderLabel= rootView.findViewById(R.id.genderLabel);
        addressLabel= rootView.findViewById(R.id.addressLabel);
        cityLabel= rootView.findViewById(R.id.cityLabel);
        regionLabel= rootView.findViewById(R.id.regionLabel);
        districtLabel= rootView.findViewById(R.id.districtLabel);
        villageLabel= rootView.findViewById(R.id.villageLabel);
        zipcodeLabel= rootView.findViewById(R.id.zipcodeLabel);
        countryLabel= rootView.findViewById(R.id.countryLabel);
        occupationLabel= rootView.findViewById(R.id.occupationLabel);
        dobLabel = rootView.findViewById(R.id.dobLabel);
        tvDetails = rootView.findViewById(R.id.tvDetails);



        setOTP();
        setData();
        setLabelLanguage();

        spinnerIdCard();
        spinnerGender();
        spinnerNationality();

        return rootView;
    }

    public void setLabelLanguage(){
        tvPhone.setText(userDefault.IDLanguage()?"Silakan masukkan nomor ponsel Anda":"Please input your mobile number");
        txtEditMobile.setHint(userDefault.IDLanguage()?"Nomor Handphone":"Mobile Number");
        phoneNumber.setText(userDefault.IDLanguage()?"Verifikasi nomor ponsel Anda dengan OTP melalui SMS":"Verify your mobile number with OTP via SMS");
        edOTP.setHint(userDefault.IDLanguage()?"Nomor OTP":"Input OTP number");
        tvDetails.setText(userDefault.IDLanguage()?"Silakan masukkan data pribadi Anda":"Please Input Your Personal details");
        fsNameLabel.setText(userDefault.IDLanguage()?"Nama Depan :":"First Name :");
        lsNameLabel.setText(userDefault.IDLanguage()?"Nama Belakang :":"Last Name :");
        idCardLabel.setText(userDefault.IDLanguage()?"Kartu ID :":"ID Card :");
        idNumberLabel.setText(userDefault.IDLanguage()?"Nomor ID :":"ID Number :");
        genderLabel.setText(userDefault.IDLanguage()?"Jenis Kelamin :":"Gender :");
        dobLabel.setText(userDefault.IDLanguage()?"Tanggal Lahir :":"Date of birth :");
        addressLabel.setText(userDefault.IDLanguage()?"Alamat :":"Address :");
        cityLabel.setText(userDefault.IDLanguage()?"Kota :":"City :");
        regionLabel.setText(userDefault.IDLanguage()?"Provinsi :":"Region :");
        districtLabel.setText(userDefault.IDLanguage()?"Kecamatan :":"District :");
        villageLabel.setText(userDefault.IDLanguage()?"Kelurahan :":"Village :");
        zipcodeLabel.setText(userDefault.IDLanguage()?"Kode Pos :":"Zipcode :");
        countryLabel.setText(userDefault.IDLanguage()?"Kewarganegaraan :":"Nationality :");
        occupationLabel.setText(userDefault.IDLanguage()?"Pekerjaan :":"Occupation :");
        reqOTP.setText(userDefault.IDLanguage()?"Kirim OTP":"Send OTP");

    }

    private void setOTP(){
        if(userResponseModel.getUser().getPhoneNumberVerify()==1){
            openField();
            tvPhone.setVisibility(View.GONE);
            tilPhone.setVisibility(View.GONE);
            tilOTP.setVisibility(View.GONE);
            phoneNumber.setVisibility(View.GONE);
        }else{
            closeField();
            tvPhone.setVisibility(View.VISIBLE);
            tilPhone.setVisibility(View.VISIBLE);
            tilOTP.setVisibility(View.VISIBLE);
            phoneNumber.setVisibility(View.VISIBLE);
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
        }


//        smsVerifyCatcher = new SmsVerifyCatcher(getActivity(), new OnSmsCatchListener<String>() {
//            @Override
//            public void onSmsCatch(String message) {
//                code = parseCode(message);//Parse verification code
//                edOTP.setText(code);//set code in edit text
//                if(counter == 0){
//                    counter = 1;
//                    activity.showProgressDialog();
//                    sendSMSOTPValidation(code);
//
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


    public void setData() {
        activity.setToolbarTitle("Starbucks Premium");
        tvIdPhoto.setText(userDefault.IDLanguage()?"Foto ID":"ID Photo");
        selfieTv.setText(userDefault.IDLanguage()?"Selfie Dengan ID":"Selfie With ID");
        submitTv.setText(userDefault.IDLanguage()?"Review":"Review");
        backButton();
        idCard.setImageBitmap(activity.getIdCard());
        selfie.setImageBitmap(activity.getSelfieCard());
        ly1.bringToFront();
        ly2.bringToFront();



        retake.setText(userDefault.IDLanguage()?"Ulangi":"Retake");
        retake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kycDialog(getActivity(), userDefault.IDLanguage() ? "Peringatan!":"Warning!",
                        userDefault.IDLanguage() ? "Apakah Anda yakin ingin mengulang dari awal?":"Are you sure you want to re-do the process from the start?");
            }
        });
        submit.setText(userDefault.IDLanguage()?"Selesai!":"Submit");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFormValid()){
                    sendKYC();
                }
            }
        });
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopUpUtil.getCalendar(getActivity(), dob, false);
            }
        });
        dob.setHint("DD-MM-YYYY");
    }

    public void spinnerIdCard(){
        ArrayAdapter aaIdCard = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item,
                userDefault.IDLanguage() ? itemSpinnerIdCardId : itemSpinnerIdCardEn);
        aaIdCard.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIdCard.setAdapter(aaIdCard);

        spinnerIdCard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choosedItemIdCard = userDefault.IDLanguage() ? itemSpinnerIdCardId[position] : itemSpinnerIdCardEn[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                choosedItemIdCard = userDefault.IDLanguage() ? itemSpinnerIdCardId[0] : itemSpinnerIdCardEn[0];
            }
        });

    }

    public void spinnerGender(){
        ArrayAdapter aaGender = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item,
                userDefault.IDLanguage() ? itemSpinnerGenderId : itemSpinnerGenderEn);
        aaGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(aaGender);

        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choosedItemGender = userDefault.IDLanguage() ? itemSpinnerGenderEn[position] : itemSpinnerGenderEn[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                choosedItemGender = userDefault.IDLanguage() ? itemSpinnerGenderEn[0] : itemSpinnerGenderEn[0];
            }
        });
    }

    public void spinnerNationality(){
        ArrayAdapter aaCountry = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item,
                userDefault.IDLanguage() ? itemSpinnerCountryId : itemSpinnerCountryEn);
        aaCountry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCountry.setAdapter(aaCountry);

        spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choosedItemCountry = userDefault.IDLanguage() ? itemSpinnerCountryEn[position] : itemSpinnerCountryEn[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                choosedItemCountry = userDefault.IDLanguage() ? itemSpinnerCountryEn[0] : itemSpinnerCountryEn[0];
            }
        });
    }

    public boolean checkConnection() {
        if (!ConnectionDetector.isConnected()) {
            DialogUtil.sErrDialog(getActivity(), userDefault, 1, new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
//                    sendKYC();
                    Toast.makeText(getActivity(),userDefault.IDLanguage()?"Mohon cek koneksi anda":"PLease check your internet connection",Toast.LENGTH_LONG).show();
                }
            });
        }
        return ConnectionDetector.isConnected();
    }


    private ApiInterface apiService;


    public ApiInterface getApiService() {
        if (apiService == null) apiService = app.getApiService();
        return apiService;
    }

    /*REST Call*/
    private void sendKYC() {
        if (!checkConnection()) return;
        if (apiService == null) apiService = app.getApiService();
        String bitmapIdCard = null;
        String bitmapSelfie = null;
        if(getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).showProgressDialog();
            if(MainActivity.getIdCard()!=null || MainActivity.getSelfieCard()!=null){
                ByteArrayOutputStream byteArrayOutputStream1 = new ByteArrayOutputStream();
                MainActivity.getIdCard().compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream1);
                byte[] bitmapIdCards = byteArrayOutputStream1.toByteArray();
                bitmapIdCard = Base64.encodeToString(bitmapIdCards, Base64.DEFAULT);
                ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
                MainActivity.getSelfieCard().compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream2);
                byte[] bitmapSelfies = byteArrayOutputStream2.toByteArray();
                bitmapSelfie = Base64.encodeToString(bitmapSelfies, Base64.DEFAULT);
            }
        }


        String mobile = txtEditMobile.getText().toString();
        if (mobile.startsWith("+62")) mobile = mobile.substring(3);
        Call<ResponseBase> call = null;
        if(getActivity() instanceof MainActivity) {
            if (userResponseModel.getUser().getPhoneNumberVerify() == 1) {
                call = apiService.postPremiumData("submit_kyc",
                        ((MainActivity) getActivity()).genSBUX("email=" + userResponseModel.getUser().getEmail() +
                                "&card_number=" + userResponseModel.getUser().getExternalId() +
                                "&phone_number=" + userResponseModel.getUser().getPhoneNumbers().get(0).getPhoneNumber() +
                                "&first_name=" + firstName.getText().toString() +
                                "&last_name=" + lastname.getText().toString() +
                                "&id_card=" + choosedItemIdCard +
                                "&id_number=" + idNumber.getText().toString() +
                                "&dob=" + dob.getText().toString() +
                                "&gender=" + choosedItemGender +
                                "&address=" + address.getText().toString() +
                                "&city=" + city.getText().toString() +
                                "&region=" + region.getText().toString() +
                                "&district=" + district.getText().toString() +
                                "&village=" + village.getText().toString() +
                                "&zipcode=" + zipCode.getText().toString() +
                                "&country=" + choosedItemCountry +
                                "&occupation=" + occupation.getText().toString() +
                                "&id_card_file=" + bitmapIdCard +
                                "&selfie_file=" + bitmapSelfie));
            } else {
                call = apiService.postPremiumData("submit_kyc",
                        ((MainActivity) getActivity()).genSBUX("email=" + userResponseModel.getUser().getEmail() +
                                "&card_number=" + userResponseModel.getUser().getExternalId() +
                                "&phone_number=" + mobile +
                                "&first_name=" + firstName.getText().toString() +
                                "&last_name=" + lastname.getText().toString() +
                                "&id_card=" + choosedItemIdCard +
                                "&id_number=" + idNumber.getText().toString() +
                                "&dob=" + dob.getText().toString() +
                                "&gender=" + choosedItemGender +
                                "&address=" + address.getText().toString() +
                                "&city=" + city.getText().toString() +
                                "&region=" + region.getText().toString() +
                                "&district=" + district.getText().toString() +
                                "&village=" + village.getText().toString() +
                                "&zipcode=" + zipCode.getText().toString() +
                                "&country=" + choosedItemCountry +
                                "&occupation=" + occupation.getText().toString() +
                                "&id_card_file=" + bitmapIdCard +
                                "&selfie_file=" + bitmapSelfie));
            }
        }
        if(call != null) {
            call.enqueue(new Callback<ResponseBase>() {
                @Override
                public void onResponse(Call<ResponseBase> call, Response<ResponseBase> response) {
                    if (getActivity() instanceof MainActivity) {
                        ((MainActivity) getActivity()).hideProgressDialog();
                        if (response.isSuccessful() && response.body() != null) {
                            if (((MainActivity) getActivity()).successResponse(response.body().getReturnCode(), response.body().getProcessMsg())) {
                                Toast.makeText(getActivity(), response.body().getProcessMsg(), Toast.LENGTH_LONG).show();
                                ((MainActivity) getActivity()).getUserData();
                                Bundle bundle = new Bundle();
                                bundle.putString("description", response.body().getStatus_user());
                                ((MainActivity) getActivity()).cFragmentWithBundlePremium(new FragmentStepStatusPremium(), bundle);
                            }
                        } else {
                            openField();
                            ((MainActivity) getActivity()).checkServer(String.valueOf(response.code()), response.message());
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBase> call, Throwable t) {
                    if (getActivity() != null && isAdded() && getActivity() instanceof MainActivity) {
                        ((MainActivity) getActivity()).hideProgressDialog();
                        ((MainActivity) getActivity()).restFailure(t);
                    }
                }
            });
        }
    }
    public void backButton() {
        activity.toolbar.setNavigationIcon(R.drawable.ic_action_back);
        activity.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kycDialog(getActivity(), userDefault.IDLanguage() ? "Peringatan!":"Warning!",
                        userDefault.IDLanguage() ? "Anda yakin ingin kembali melakukan proses dari awal?":"Are you sure you want to re-do the process from the start?");

            }
        });
    }

    public static void kycDialog(final Activity act, String header, String message) {
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

    //Get SMS OTP
    public void getSmsOtp() {
        if (!activity.checkConnection()) return;
        if (apiService == null) apiService = activity.getApiService();

        String mobile = txtEditMobile.getText().toString();
        if (mobile.startsWith("+62")) mobile = mobile.substring(3);

        Call<ResponseBase> callSmsOtp = apiService.getSMSOTP("smsotp",
                DataUtil.genSBUX(
                        "mobid=" + getString(R.string.rest_mob_id) +
                                "&mobkey=" + getString(R.string.rest_mob_key) +
                                "&phone=" + mobile));
        Log.e("Mobile", mobile);

        callSmsOtp.enqueue(new Callback<ResponseBase>() {
            @Override
            public void onResponse(Call<ResponseBase> call, Response<ResponseBase> response) {
                activity.hideProgressDialog();
                if (getActivity() != null) {
                    if (response.isSuccessful() && response.body() != null) {
                        if(response.body().getStatus().equals("Success")){
                            startTimer();
                            Toast.makeText(getActivity(),response.body().getProcessMsg(),Toast.LENGTH_LONG).show();
                        }else{
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
        String mobile = txtEditMobile.getText().toString();
        if (mobile.startsWith("+62")) mobile = mobile.substring(3);

        Call<ResponseBase> sendSMSOTPValidation = apiService.sendSMSOTPValidation("smsotp_validation",
                DataUtil.genSBUX(
                        "mobid=" + getString(R.string.rest_mob_id) +
                                "&mobkey=" + getString(R.string.rest_mob_key) +
                                "&phone=" + mobile +
                                "&sms_otp=" + OTP));
        Log.e("OTP", OTP);
        sendSMSOTPValidation.enqueue(new Callback<ResponseBase>() {
            @Override
            public void onResponse(Call<ResponseBase> call, Response<ResponseBase> response) {
                activity.hideProgressDialog();
                if (getActivity() != null) {
                    if (response.isSuccessful() && response.body() != null) {
                        PopUpUtil.sLongToast(getActivity(),response.body().getProcessMsg());
                        if(response.body().getStatus().equals("Success")){
                            counter = 1;
                            openField();
                            icCheck.setVisibility(View.VISIBLE);
                            icCheck.setBackgroundResource(R.drawable.ic_ok);
                            txtEditMobile.setFocusable(false);
                            edOTP.setFocusable(false);
                            reqOTP.setVisibility(View.GONE);
                        }else{
                            icCheck.setVisibility(View.VISIBLE);
                            icCheck.setBackgroundResource(R.drawable.ic_denied);
                            counter = 0;
                            closeField();
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

    public void openField() {
        firstName.setEnabled(true);
        firstName.setBackground(getResources().getDrawable(R.drawable.border_kyc_confirmation));
        firstName.setPadding(18,5,5,5);
        lastname.setEnabled(true);
        lastname.setBackground(getResources().getDrawable(R.drawable.border_kyc_confirmation));
        lastname.setPadding(18,5,5,5);
        spinnerIdCard.setEnabled(true);
        spinnerIdCard.setBackground(getResources().getDrawable(R.drawable.border_kyc_confirmation));
        spinnerGender.setEnabled(true);
        spinnerGender.setBackground(getResources().getDrawable(R.drawable.border_kyc_confirmation));
        idNumber.setEnabled(true);
        idNumber.setBackground(getResources().getDrawable(R.drawable.border_kyc_confirmation));
        idNumber.setPadding(18,5,5,5);
        dob.setEnabled(true);
        dob.setBackground(getResources().getDrawable(R.drawable.border_kyc_confirmation));
        dob.setPadding(18,5,5,5);
        address.setEnabled(true);
        address.setBackground(getResources().getDrawable(R.drawable.border_kyc_confirmation));
        address.setPadding(18,5,5,5);
        village.setEnabled(true);
        village.setBackground(getResources().getDrawable(R.drawable.border_kyc_confirmation));
        village.setPadding(18,5,5,5);
        district.setEnabled(true);
        district.setBackground(getResources().getDrawable(R.drawable.border_kyc_confirmation));
        district.setPadding(18,5,5,5);
        city.setEnabled(true);
        city.setBackground(getResources().getDrawable(R.drawable.border_kyc_confirmation));
        city.setPadding(18,5,5,5);
        region.setEnabled(true);
        region.setBackground(getResources().getDrawable(R.drawable.border_kyc_confirmation));
        region.setPadding(18,5,5,5);
        spinnerCountry.setEnabled(true);
        spinnerCountry.setBackground(getResources().getDrawable(R.drawable.border_kyc_confirmation));
        occupation.setEnabled(true);
        occupation.setBackground(getResources().getDrawable(R.drawable.border_kyc_confirmation));
        occupation.setPadding(18,5,5,5);
        zipCode.setEnabled(true);
        zipCode.setBackground(getResources().getDrawable(R.drawable.border_kyc_confirmation));
        zipCode.setPadding(18,5,5,5);
    }
    public void closeField() {
        firstName.setEnabled(false);
        firstName.setBackground(getResources().getDrawable(R.drawable.border_kyc_confirmation_disable));
        lastname.setEnabled(false);
        lastname.setBackground(getResources().getDrawable(R.drawable.border_kyc_confirmation_disable));
        spinnerIdCard.setEnabled(false);
        spinnerIdCard.setBackground(getResources().getDrawable(R.drawable.border_kyc_confirmation_disable));
        spinnerGender.setEnabled(false);
        spinnerGender.setBackground(getResources().getDrawable(R.drawable.border_kyc_confirmation_disable));
        idNumber.setEnabled(false);
        idNumber.setBackground(getResources().getDrawable(R.drawable.border_kyc_confirmation_disable));
        dob.setEnabled(false);
        dob.setBackground(getResources().getDrawable(R.drawable.border_kyc_confirmation_disable));
        address.setEnabled(false);
        address.setBackground(getResources().getDrawable(R.drawable.border_kyc_confirmation_disable));
        village.setEnabled(false);
        village.setBackground(getResources().getDrawable(R.drawable.border_kyc_confirmation_disable));
        district.setEnabled(false);
        district.setBackground(getResources().getDrawable(R.drawable.border_kyc_confirmation_disable));
        city.setEnabled(false);
        city.setBackground(getResources().getDrawable(R.drawable.border_kyc_confirmation_disable));
        region.setEnabled(false);
        region.setBackground(getResources().getDrawable(R.drawable.border_kyc_confirmation_disable));
        spinnerCountry.setEnabled(false);
        spinnerCountry.setBackground(getResources().getDrawable(R.drawable.border_kyc_confirmation_disable));
        occupation.setEnabled(false);
        occupation.setBackground(getResources().getDrawable(R.drawable.border_kyc_confirmation_disable));
        zipCode.setEnabled(false);
        zipCode.setBackground(getResources().getDrawable(R.drawable.border_kyc_confirmation_disable));
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
        if (cTimer != null)
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



    private boolean isFormValid() {
        boolean isBahasa = (userDefault.IDLanguage());
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setPositiveButton("OK", null);
        if(userResponseModel.getUser().getPhoneNumberVerify()==1){

            tvPhone.setText(userDefault.IDLanguage()?"Semua data harus diisi":"All fields are mandatory");

            if (firstName.getText().toString().isEmpty()) {
                DialogUtil.sNotDialog(getActivity(),
                        isBahasa ? "Semua data harus diisi":"All fields are mandatory");
                return false;
            } else if (lastname.getText().toString().isEmpty()) {
                DialogUtil.sNotDialog(getActivity(),
                        isBahasa ? "Semua data harus diisi":"All fields are mandatory");
                return false;
            } else if (idNumber.getText().toString().isEmpty()) {
                DialogUtil.sNotDialog(getActivity(),
                        isBahasa ? "Semua data harus diisi":"All fields are mandatory");
                return false;
            } else if (dob.getText().toString().isEmpty()) {
                DialogUtil.sNotDialog(getActivity(),
                        isBahasa ?"Semua data harus diisi":"All fields are mandatory");
                return false;
            } else if (address.getText().toString().isEmpty()) {
                DialogUtil.sNotDialog(getActivity(),
                        isBahasa ? "Semua data harus diisi":"All fields are mandatory");
                return false;
            } else if (village.getText().toString().isEmpty()) {
                DialogUtil.sNotDialog(getActivity(),
                        isBahasa ? "Semua data harus diisi":"All fields are mandatory");
                return false;
            } else if (district.getText().toString().isEmpty()) {
                DialogUtil.sNotDialog(getActivity(),
                        isBahasa ? "Semua data harus diisi":"All fields are mandatory");
                return false;
            } else if (city.getText().toString().isEmpty()) {
                DialogUtil.sNotDialog(getActivity(),
                        isBahasa ? "Semua data harus diisi":"All fields are mandatory");
                return false;
            } else if (region.getText().toString().isEmpty()) {
                DialogUtil.sNotDialog(getActivity(),
                        isBahasa ? "Semua data harus diisi":"All fields are mandatory");
                return false;
            }  else if (occupation.getText().toString().isEmpty()) {
                DialogUtil.sNotDialog(getActivity(),
                        isBahasa ? "Semua data harus diisi":"All fields are mandatory");
                return false;
            }else if (zipCode.getText().toString().isEmpty()) {
                DialogUtil.sNotDialog(getActivity(),
                        isBahasa ? "Semua data harus diisi":"All fields are mandatory");
                return false;
            }

        }else{
            if (txtEditMobile.getText().toString().isEmpty()) {
                DialogUtil.sNotDialog(getActivity(),
                        isBahasa ? "Semua data harus diisi":"All fields are mandatory");
                return false;
            }else if (txtEditMobile.getText().toString().length() < 11) {
                DialogUtil.sNotDialog(getActivity(),
                        isBahasa ? "Semua data harus diisi":"All fields are mandatory");
                return false;
            } else if (!txtEditMobile.getText().toString().substring(3).startsWith("8")) {
                DialogUtil.sNotDialog(getActivity(),
                        isBahasa ? "Semua data harus diisi":"All fields are mandatory");
                return false;
            } else if (edOTP.getText().toString().isEmpty()) {
                DialogUtil.sNotDialog(getActivity(),
                        isBahasa ? "Semua data harus diisi":"All fields are mandatory");
                return false;
            }if (firstName.getText().toString().isEmpty()) {
                DialogUtil.sNotDialog(getActivity(),
                        isBahasa ? "Semua data harus diisi":"All fields are mandatory");
                return false;
            } else if (lastname.getText().toString().isEmpty()) {
                DialogUtil.sNotDialog(getActivity(),
                        isBahasa ?  "Semua data harus diisi":"All fields are mandatory");
                return false;
            } else if (idNumber.getText().toString().isEmpty()) {
                DialogUtil.sNotDialog(getActivity(),
                        isBahasa ?  "Semua data harus diisi":"All fields are mandatory");
                return false;
            } else if (dob.getText().toString().isEmpty()) {
                DialogUtil.sNotDialog(getActivity(),
                        isBahasa ?  "Semua data harus diisi":"All fields are mandatory");
                return false;
            } else if (address.getText().toString().isEmpty()) {
                DialogUtil.sNotDialog(getActivity(),
                        isBahasa ?  "Semua data harus diisi":"All fields are mandatory");
                return false;
            } else if (village.getText().toString().isEmpty()) {
                DialogUtil.sNotDialog(getActivity(),
                        isBahasa ?  "Semua data harus diisi":"All fields are mandatory");
                return false;
            } else if (district.getText().toString().isEmpty()) {
                DialogUtil.sNotDialog(getActivity(),
                        isBahasa ?  "Semua data harus diisi":"All fields are mandatory");
                return false;
            } else if (city.getText().toString().isEmpty()) {
                DialogUtil.sNotDialog(getActivity(),
                        isBahasa ?  "Semua data harus diisi":"All fields are mandatory");
                return false;
            } else if (region.getText().toString().isEmpty()) {
                DialogUtil.sNotDialog(getActivity(),
                        isBahasa ?  "Semua data harus diisi":"All fields are mandatory");
                return false;
            }  else if (occupation.getText().toString().isEmpty()) {
                DialogUtil.sNotDialog(getActivity(),
                        isBahasa ?  "Semua data harus diisi":"All fields are mandatory");
                return false;
            }else if (zipCode.getText().toString().isEmpty()) {
                DialogUtil.sNotDialog(getActivity(),
                        isBahasa ?  "Semua data harus diisi":"All fields are mandatory");
                return false;
            }



        }
        return true;
    }

//    private boolean isFormValid() {
//        boolean isBahasa = (userDefault.IDLanguage());
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
//        alertDialogBuilder.setPositiveButton("OK", null);
//        if(userResponseModel.getUser().getPhoneNumberVerify()==1){
//
//            tvPhone.setText(userDefault.IDLanguage()?"Semua data harus diisi":"All fields are mandatory");
//
//            if (firstName.getText().toString().isEmpty()) {
//                DialogUtil.sNotDialog(getActivity(),
//                        isBahasa ? getString(R.string.id_val_f_name) : getString(R.string.val_f_name));
//                return false;
//            } else if (lastname.getText().toString().isEmpty()) {
//                DialogUtil.sNotDialog(getActivity(),
//                        isBahasa ? getString(R.string.id_val_l_name) : getString(R.string.val_l_name));
//                return false;
//            } else if (idNumber.getText().toString().isEmpty()) {
//                DialogUtil.sNotDialog(getActivity(),
//                        isBahasa ? "Nomor ID Tidak Boleh Kosong" : "ID Number Cannot Be Empty");
//                return false;
//            } else if (dob.getText().toString().isEmpty()) {
//                DialogUtil.sNotDialog(getActivity(),
//                        isBahasa ? getString(R.string.id_val_dob) : getString(R.string.val_dob));
//                return false;
//            } else if (address.getText().toString().isEmpty()) {
//                DialogUtil.sNotDialog(getActivity(),
//                        isBahasa ? "Alamat Tidak Boleh Kosong" : "Address Cannot be Empty");
//                return false;
//            } else if (village.getText().toString().isEmpty()) {
//                DialogUtil.sNotDialog(getActivity(),
//                        isBahasa ? "Kelurahan Tidak Boleh Kosong" : "Village Cannot Be Empty");
//                return false;
//            } else if (district.getText().toString().isEmpty()) {
//                DialogUtil.sNotDialog(getActivity(),
//                        isBahasa ? "Kecamatan Tidak Boleh Kosong" : "District Cannot Be Empty");
//                return false;
//            } else if (city.getText().toString().isEmpty()) {
//                DialogUtil.sNotDialog(getActivity(),
//                        isBahasa ? "Kota Tidak Boleh Kosong" :"City Cannot Be Empty");
//                return false;
//            } else if (region.getText().toString().isEmpty()) {
//                DialogUtil.sNotDialog(getActivity(),
//                        isBahasa ? "Kotamadya Tidak Boleh Kosong" : "Region Cannot Be Empty");
//                return false;
//            } else if (country.getText().toString().isEmpty()) {
//                DialogUtil.sNotDialog(getActivity(),
//                        isBahasa ? "Negara Tidak Boleh Kosong" : "Country Cannot Be Empty");
//                return false;
//            } else if (occupation.getText().toString().isEmpty()) {
//                DialogUtil.sNotDialog(getActivity(),
//                        isBahasa ? "Domisili Tidak Boleh Kosong" : "Occupation Cannot Be Empty");
//                return false;
//            }else if (zipCode.getText().toString().isEmpty()) {
//                DialogUtil.sNotDialog(getActivity(),
//                        isBahasa ? "Kodepos Tidak Boleh Kosong" : "Zipcode Cannot Be Empty");
//                return false;
//            }
//
//        }else{
//            if (txtEditMobile.getText().toString().isEmpty()) {
//                DialogUtil.sNotDialog(getActivity(),
//                        isBahasa ? getString(R.string.id_val_mobile) : getString(R.string.val_mobile));
//                return false;
//            }else if (txtEditMobile.getText().toString().length() < 11) {
//                DialogUtil.sNotDialog(getActivity(),
//                        isBahasa ? getString(R.string.id_val_wrong_mobile) : getString(R.string.val_wrong_mobile));
//                return false;
//            } else if (!txtEditMobile.getText().toString().substring(3).startsWith("8")) {
//                DialogUtil.sNotDialog(getActivity(),
//                        isBahasa ? getString(R.string.id_val_wrong_mobile2) : getString(R.string.val_wrong_mobile2));
//                return false;
//            } else if (edOTP.getText().toString().isEmpty()) {
//                DialogUtil.sNotDialog(getActivity(),
//                        isBahasa ? getString(R.string.id_val_otp) : getString(R.string.val_otp));
//                return false;
//            }if (firstName.getText().toString().isEmpty()) {
//                DialogUtil.sNotDialog(getActivity(),
//                        isBahasa ? getString(R.string.id_val_f_name) : getString(R.string.val_f_name));
//                return false;
//            } else if (lastname.getText().toString().isEmpty()) {
//                DialogUtil.sNotDialog(getActivity(),
//                        isBahasa ? getString(R.string.id_val_l_name) : getString(R.string.val_l_name));
//                return false;
//            } else if (idNumber.getText().toString().isEmpty()) {
//                DialogUtil.sNotDialog(getActivity(),
//                        isBahasa ? "Nomor ID Tidak Boleh Kosong" : "ID Number Cannot Be Empty");
//                return false;
//            } else if (dob.getText().toString().isEmpty()) {
//                DialogUtil.sNotDialog(getActivity(),
//                        isBahasa ? getString(R.string.id_val_dob) : getString(R.string.val_dob));
//                return false;
//            } else if (address.getText().toString().isEmpty()) {
//                DialogUtil.sNotDialog(getActivity(),
//                        isBahasa ? "Alamat Tidak Boleh Kosong" : "Address Cannot be Empty");
//                return false;
//            } else if (village.getText().toString().isEmpty()) {
//                DialogUtil.sNotDialog(getActivity(),
//                        isBahasa ? "Kelurahan Tidak Boleh Kosong" : "Village Cannot Be Empty");
//                return false;
//            } else if (district.getText().toString().isEmpty()) {
//                DialogUtil.sNotDialog(getActivity(),
//                        isBahasa ? "Kecamatan Tidak Boleh Kosong" : "District Cannot Be Empty");
//                return false;
//            } else if (city.getText().toString().isEmpty()) {
//                DialogUtil.sNotDialog(getActivity(),
//                        isBahasa ? "Kota Tidak Boleh Kosong" :"City Cannot Be Empty");
//                return false;
//            } else if (region.getText().toString().isEmpty()) {
//                DialogUtil.sNotDialog(getActivity(),
//                        isBahasa ? "Kotamadya Tidak Boleh Kosong" : "Region Cannot Be Empty");
//                return false;
//            } else if (country.getText().toString().isEmpty()) {
//                DialogUtil.sNotDialog(getActivity(),
//                        isBahasa ? "Negara Tidak Boleh Kosong" : "Country Cannot Be Empty");
//                return false;
//            } else if (occupation.getText().toString().isEmpty()) {
//                DialogUtil.sNotDialog(getActivity(),
//                        isBahasa ? "Domisili Tidak Boleh Kosong" : "Occupation Cannot Be Empty");
//                return false;
//            }else if (zipCode.getText().toString().isEmpty()) {
//                DialogUtil.sNotDialog(getActivity(),
//                        isBahasa ? "Kodepos Tidak Boleh Kosong" : "Zipcode Cannot Be Empty");
//                return false;
//            }
//
//
//
//        }
//        return true;
//    }
}
