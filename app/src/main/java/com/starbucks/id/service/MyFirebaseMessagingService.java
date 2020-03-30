package com.starbucks.id.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.starbucks.id.R;
import com.starbucks.id.controller.activity.MainActivity;
import com.starbucks.id.helper.StarbucksID;
import com.starbucks.id.helper.UserDefault;
import com.starbucks.id.helper.utils.DataUtil;
import com.starbucks.id.model.ResponseBase;
import com.starbucks.id.rest.ApiInterface;

import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.NotificationManager.IMPORTANCE_DEFAULT;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private UserDefault userDefault;

    @Override
    public void onNewToken(String s) {
        // Get updated InstanceID token.
        userDefault = UserDefault.getInstance(getApplicationContext());
        userDefault.setPn(false);
        if (userDefault.isLoggedIn()) sendRegistrationToServer(s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        if (remoteMessage.getData().entrySet().size() > 0) {
            for (Map.Entry<String, String> entry : remoteMessage.getData().entrySet()) {
                if (StarbucksID.Companion.getDebug()) Log.i("SBUXID", "key, " + entry.getKey() + " value " + entry.getValue());
                intent.putExtra(entry.getKey(), entry.getValue());
            }
        }

        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.logo_sbux);
        Notification.Builder mBuilder;
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel("Sbux-ID", "Sbux-ID", IMPORTANCE_DEFAULT);
            if (notificationManager != null) notificationManager.createNotificationChannel(mChannel);
            mBuilder = new Notification.Builder(this, "Sbux-ID");
        }else{
            mBuilder = new Notification.Builder(this);
        }

        mBuilder.setSmallIcon(R.drawable.logo_sbux)
                .setLargeIcon(largeIcon)
                .setAutoCancel(true)
                .setContentTitle(remoteMessage.getData().get("title"))
                .setContentText(remoteMessage.getData().get("body"))
                .setStyle(new Notification.BigTextStyle()
                        .bigText(remoteMessage.getData().get("body")))
                .setPriority(Notification.PRIORITY_HIGH)
                .setDefaults(Notification.FLAG_AUTO_CANCEL | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND);

        if (!StarbucksID.Companion.getFg()) {
            // Foreground state
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            mBuilder.setContentIntent(pendingIntent);
        }else{
            // Background state
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            mBuilder.setContentIntent(pendingIntent);
        }


        if (notificationManager != null)
            notificationManager.notify(0 /* ID of notification */, mBuilder.build());
    }

    private void sendRegistrationToServer(String token) {
        try {
            /*Get Device Properties*/
            @SuppressLint("HardwareIds")
            String advertiser_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                    Settings.Secure.ANDROID_ID);

            Log.e("Push Token", token);
            Log.e("Advertiser Id", advertiser_id);

            String uuid = UUID.randomUUID().toString().toLowerCase();

            TelephonyManager tel = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            String networkOperator = tel != null ? tel.getNetworkOperator() : null;

            int mnc = 11;
            String counrty_code = "id";
            String op_name = "Telkomsel";
            if (!TextUtils.isEmpty(networkOperator)) {
                mnc = Integer.parseInt(networkOperator.substring(3));
                op_name = tel.getNetworkOperatorName();
                counrty_code = tel.getSimCountryIso();
            }

            DisplayMetrics displayMetrics = new DisplayMetrics();

            int height = displayMetrics.heightPixels;
            int width = displayMetrics.widthPixels;
            String screen_size = height + "x" + width;


            ApiInterface apiService = StarbucksID.Companion.getInstance().getApiService();

            Call<ResponseBase> call = apiService.pnReg("reg_pn",
                    DataUtil.genSBUX("email=" + userDefault.getEmail() +
                            "&network_code=" + mnc +
                            "&country_code=" + counrty_code +
                            "&name=" + op_name +
                            "&density=" + getResources().getDisplayMetrics().density +
                            "&timezone=" + TimeZone.getDefault().getID() +
                            "&screen=" + screen_size +
                            "&model=" + Build.MODEL +
                            "&locale=" + Locale.getDefault().getLanguage() +
                            "&version=" + Build.VERSION_CODES.BASE +
                            "&brand=" + Build.BRAND +
                            "&manufacturer=" + Build.MANUFACTURER +
                            "&id=" + uuid +
                            "&platform=android" +
                            "&push_token=" + token +
                            "&advertiser_id=" + advertiser_id
                    ));

            call.enqueue(new Callback<ResponseBase>() {
                @Override
                public void onResponse(Call<ResponseBase> call, Response<ResponseBase> response) {
                    if (response.isSuccessful()) userDefault.setPn(true);
                    else userDefault.setPn(false);
                }

                @Override
                public void onFailure(Call<ResponseBase> call, Throwable t) {
                }
            });
        }catch (Exception e){
            userDefault.setPn(false);
        }
    }
}
