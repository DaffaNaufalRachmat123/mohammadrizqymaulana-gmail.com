package com.starbucks.id.controller.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import android.provider.Settings;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.starbucks.id.BuildConfig;
import com.starbucks.id.R;
import com.starbucks.id.helper.ConnectionDetector;
import com.starbucks.id.helper.StarbucksID;
import com.starbucks.id.helper.UserDefault;
import com.starbucks.id.helper.utils.DataUtil;
import com.starbucks.id.helper.utils.DialogUtil;
import com.starbucks.id.helper.utils.PopUpUtil;
import com.starbucks.id.helper.utils.RootUtil;
import com.starbucks.id.model.ResponseBase;
import com.starbucks.id.rest.ApiInterface;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SplashActivity extends AppCompatActivity {

    private int time = 1000;
    private UserDefault userDefault;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        /*SET DEBUG FLAG*/
        StarbucksID.Companion.setDebug(BuildConfig.DEBUG);

        userDefault = UserDefault.getInstance(getApplicationContext());
        userDefault.setLanguage(userDefault.IDLanguage()?"id":"en");

        if (StarbucksID.Companion.getDebug()) checkVersion();
//        checkVersion();
        else if (deviceValidation()) checkVersion();

    }

    private void goNext(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!userDefault.firstLaunch()) {
                    Intent home = new Intent(SplashActivity.this, LoginActivity.class);
                    if (userDefault.isLoggedIn()) {
                        home = new Intent(SplashActivity.this, MainActivity.class);
                    }
                    startActivity(home);
                    finish();
                } else {
                    userDefault.clean();
                    Intent tuts = new Intent(SplashActivity.this, TutorialsActivity.class);
                    tuts.putExtra("operation_id", 0);

                    startActivity(tuts);
                    finish();
                }

            }
        }, time);
    }

    private void startTimer(){
        if (time > 0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    time = time - 200;
                    startTimer();
                }
            }, 200);
        }
    }

    /*REST Call*/
    public void checkVersion() {
        if (!checkConnection()) return;


        StarbucksID app = StarbucksID.Companion.getInstance();
        ApiInterface apiService = app.getApiService();

        if (apiService == null) apiService = app.getApiService();

        Call<ResponseBase> call = apiService.checkVersion("version",
                DataUtil.genSBUX(
                        "os= android" +
                                "&version=" + getVersion() +
                                "&mobid=" + getString(R.string.rest_mob_id) +
                                "&mobkey=" + getString(R.string.rest_mob_key)
                ));

        startTimer();

        call.enqueue(new Callback<ResponseBase>() {
            @Override
            public void onResponse(Call<ResponseBase> call, Response<ResponseBase> response) {
                DialogUtil.hProDialog();
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getReturnCode().equals("00")) goNext();
                    else showDialog2(response.body().getProcessMsg());
                }else PopUpUtil.sLongToast(SplashActivity.this, response.message());
            }

            @Override
            public void onFailure(Call<ResponseBase> call, Throwable t) {
                restFailure(t);
            }
        });

    }



    private boolean deviceValidation() {
        String androidID = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        SensorManager manager = (SensorManager) getApplicationContext().getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> lsensors = manager.getSensorList(Sensor.TYPE_ALL);

        if (Build.FINGERPRINT.contains("generic") ||
                Build.PRODUCT.contains("sdk") ||
                Build.PRODUCT.contains("SDK") ||
                Build.HARDWARE.contains("golfdish") ||
                androidID == null ||
                Build.FINGERPRINT.startsWith("unknown") ||
                Build.MODEL.contains("google_sdk")||
                Build.MODEL.contains("Emulator")||
                Build.MODEL.contains("Android SDK built for x86") ||
                Build.MANUFACTURER.contains("Genymotion")) {

            // Emulator detected
            showDialog1(userDefault.IDLanguage() ? getString(R.string.warning_id) : getString(R.string.warning),
                    userDefault.IDLanguage() ? getString(R.string.emulator_id) : getString(R.string.emulator));

            return false;
        }else if(new RootUtil().isDeviceRooted()) {

            // Rooted Device detected
            showDialog1(userDefault.IDLanguage() ? getString(R.string.warning_id) : getString(R.string.warning),
                    userDefault.IDLanguage() ? getString(R.string.root_id) : getString(R.string.root));
            return false;
        }
        else return !lsensors.isEmpty();
    }

    private void showDialog2(String message){
        final Dialog dialogSignOut = new Dialog(SplashActivity.this);
        dialogSignOut.setCanceledOnTouchOutside(false);
        dialogSignOut.setCancelable(false);
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

        tvHeader.setText(userDefault.IDLanguage() ? getString(R.string.warning_id) : getString(R.string.warning));
        tvContent.setText(message);
        btOk.setText("OK");
        btCancel.setText("Exit");

        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSignOut.dismiss();

                final String appPackageName = getPackageName();
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
                finish();
            }
        });

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSignOut.dismiss();
                finish();
            }
        });
        dialogSignOut.show();
    }

    public void showDialog1(String header, String message) {
        final Dialog dialogNotif = new Dialog(this);
        dialogNotif.setCanceledOnTouchOutside(false);
        dialogNotif.setCancelable(false);
        dialogNotif.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogNotif.setContentView(R.layout.dialog_notif_1_button);

        Window window = dialogNotif.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawableResource(android.R.color.transparent);

        TextView txtNotifHeader = dialogNotif.findViewById(R.id.tvHeader);
        TextView txtNotifContent = dialogNotif.findViewById(R.id.tvNotif);
        Button btnNotif = dialogNotif.findViewById(R.id.btOk);

        txtNotifHeader.setText(header);
        txtNotifContent.setText(message);

        btnNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogNotif.dismiss();
                finish();
            }
        });

        dialogNotif.show();
    }

    private String getVersion() {
        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(this.getPackageName(), 0);
            return String.valueOf(pInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*Error Handler*/
    // Method to manually check connection status
    public boolean checkConnection() {
        if (!ConnectionDetector.isConnected()) {
            DialogUtil.sErrDialog(this, userDefault, 1, new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    checkVersion();
                }
            });
        }
        return ConnectionDetector.isConnected();
    }

    public boolean checkServer(String code, String m) {
        if (!code.startsWith("2")) {
            DialogUtil.sErrDialog(this, userDefault, 0, new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    checkVersion();
                }
            });
            return false;
        }else{
            DialogUtil.sNotDialog(this, m);
            return true;
        }
    }

    public void restFailure(Throwable t) {
        if (t instanceof IOException) {
            DialogUtil.sErrDialog(this, userDefault, 0, new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    checkVersion();
                }
            });
        }
    }
}
