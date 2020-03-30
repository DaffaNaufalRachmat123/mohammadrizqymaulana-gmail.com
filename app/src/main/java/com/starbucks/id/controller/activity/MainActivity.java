package com.starbucks.id.controller.activity;

/*
 * Created by Angga N P on 5/9/2018.
 */

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.squareup.picasso.Picasso;
import com.starbucks.id.R;
import com.starbucks.id.controller.extension.ShakeDetector;
import com.starbucks.id.controller.fragment.ErrorHandlerFragment;
import com.starbucks.id.controller.fragment.home_fragment.FragmentHome;
import com.starbucks.id.controller.fragment.home_fragment.inbox_tabs.FragmentInbox;
import com.starbucks.id.controller.fragment.home_fragment.inbox_tabs.FragmentMessage;
import com.starbucks.id.controller.fragment.home_fragment.inbox_tabs.FragmentMessageDetail;
import com.starbucks.id.controller.fragment.home_fragment.inbox_tabs.FragmentOffer;
import com.starbucks.id.controller.fragment.home_fragment.inbox_tabs.FragmentOfferDetail;
import com.starbucks.id.controller.fragment.home_fragment.profile_tabs.FragmentHistoryCardProfile;
import com.starbucks.id.controller.fragment.home_fragment.profile_tabs.FragmentChangeDetail;
import com.starbucks.id.controller.fragment.home_fragment.profile_tabs.FragmentPassCode;
import com.starbucks.id.controller.fragment.home_fragment.profile_tabs.FragmentPersonal;
import com.starbucks.id.controller.fragment.home_fragment.profile_tabs.FragmentSetPassCode;
import com.starbucks.id.controller.fragment.home_fragment.profile_tabs.fragment_premium.FragmentStepStatusPremium;
import com.starbucks.id.controller.fragment.home_fragment.profile_tabs.reward_fragment.FragmentRewardHistory;
import com.starbucks.id.controller.fragment.home_fragment.profile_tabs.reward_fragment.FragmentRewards;
import com.starbucks.id.controller.fragment.menu_fragment.MenusFragment;
import com.starbucks.id.controller.fragment.pay_fragment.FragmentPay;
import com.starbucks.id.controller.fragment.pay_fragment.FragmentReload;
import com.starbucks.id.controller.fragment.pay_fragment.tabs.FragmentAddTab;
import com.starbucks.id.controller.fragment.store_fragment.FragmentStoreDetail;
import com.starbucks.id.controller.fragment.store_fragment.FragmentStores;
import com.starbucks.id.helper.ConnectionDetector;
import com.starbucks.id.helper.StarbucksID;
import com.starbucks.id.helper.UserDefault;
import com.starbucks.id.helper.internet.InternetConnectionListener;
import com.starbucks.id.helper.utils.DataUtil;
import com.starbucks.id.helper.utils.DialogUtil;
import com.starbucks.id.helper.utils.PopUpUtil;
import com.starbucks.id.model.ResponseBase;
import com.starbucks.id.model.extension.WhatsNewModel;
import com.starbucks.id.model.inbox.MessageModel;
import com.starbucks.id.model.inbox.ResponseMessageCount;
import com.starbucks.id.model.login.LoginResponseModel;
import com.starbucks.id.model.menu.MenuModel;
import com.starbucks.id.model.user.ResponseRefreshToken;
import com.starbucks.id.model.user.UserResponseModel;
import com.starbucks.id.rest.ApiInterface;
import com.starbucks.id.rest.RestSecurityGen;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements InternetConnectionListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public static final int REQUEST_CHECK_SETTINGS = 19;

    //All Data
    public List<MenuModel> menuList;
    public UserResponseModel userResponseModel;
    public ResponseMessageCount msgCount;
    public List<WhatsNewModel> whatsNew;

    public Toolbar toolbar;
    public static TabLayout tabLayout;
    private TextView tvTier, tvTitle, tvSubtitle;
    private ConstraintLayout CLToolbar;
    private UserDefault userDefault;
    private Picasso picasso;
    private FrameLayout FLC;

    private Dialog dialogPay, dialogPC;
    private Snackbar snack;
    private SensorManager mSensorManager;
    private Sensor mAcc;
    private ShakeDetector mShakeDetector;
    private LocationManager locManager;
    private ApiInterface apiService;
    public int counter = 0;
    private LinearLayout pb;
    private StarbucksID app;

    public static Bitmap idCard;
    public static Bitmap selfieCard;

    private int[] defaultTabIcons = {
            R.drawable.ic_action_home,
            R.drawable.ic_action_pay,
            R.drawable.ic_action_reward,
            R.drawable.ic_action_menu,
            R.drawable.ic_action_store
    };
    private int[] selectedTabIcons = {
            R.drawable.ic_action_home_b,
            R.drawable.ic_action_pay_b,
            R.drawable.ic_action_reward_b,
            R.drawable.ic_action_menu_b,
            R.drawable.ic_action_store_b
    };

    public boolean doubleBackToExitPressedOnce = false;

    // PN
    private int pnAttempt = 0;
    private MessageModel msg;

    private Fragment curFragment;
    private Fragment curPosition;



    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        // All required changes were successfully made
                        counter = 0;
                        if (ContextCompat.checkSelfPermission(this,
                                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                                    // Do something after 5s = 5000ms
                                    if (getCurrentFragment() instanceof FragmentStores)
                                        ((FragmentStores) getCurrentFragment()).setData();
                                    if (getCurrentFragment() instanceof FragmentStoreDetail)
                                        ((FragmentStoreDetail) getCurrentFragment()).setData();

                        } else {
                            ActivityCompat.requestPermissions(this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    MY_PERMISSIONS_REQUEST_LOCATION);
                        }
                        break;

                    case Activity.RESULT_CANCELED:
                        // The user was asked to change settings, but chose not to
                        counter = 1;
                        if (getCurrentFragment() instanceof FragmentStores)
                            ((FragmentStores) getCurrentFragment()).setDefault();
                        if (getCurrentFragment() instanceof FragmentStoreDetail)
                            ((FragmentStoreDetail) getCurrentFragment()).setData();

                        showToast(userDefault.IDLanguage() ?
                                "Pengguna menolak untuk mengaktifkan lokasi perangkat, store default ditampilkan." :
                                "User deny to turn on device location, default store displayed.");
                        break;

                    default:
                        break;
                }
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                counter = 1;
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (getCurrentFragment() instanceof FragmentStores)
                        ((FragmentStores) getCurrentFragment()).setData();
                    if (getCurrentFragment() instanceof FragmentStoreDetail)
                        ((FragmentStoreDetail) getCurrentFragment()).setData();

                } else {
                    counter = 1;
                    if (getCurrentFragment() instanceof FragmentStores)
                        ((FragmentStores) getCurrentFragment()).setDefault();
                    if (getCurrentFragment() instanceof FragmentStoreDetail)
                        ((FragmentStoreDetail) getCurrentFragment()).setData();

                    showSnack(userDefault.IDLanguage() ?
                            getString(R.string.location_permission_detail_id) :
                            getString(R.string.location_permission_detail));
                }
            }
        }
    }

    // Check Location Setting
    public void checkSettingLocation() {
//            counter = 0;
            GoogleApiClient googleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                    .addApi(LocationServices.API).build();
            googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);
        builder.setNeedBle(true);

        Task<LocationSettingsResponse> task =
                LocationServices.getSettingsClient(getApplicationContext()).checkLocationSettings(builder.build());
        task.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(Task<LocationSettingsResponse> task) {
                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    // All location settings are satisfied. The client can initialize location
                    // requests here.
                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(MainActivity.this,
                                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                            counter = 0;
                            if (getCurrentFragment() instanceof FragmentStores)
                                ((FragmentStores) getCurrentFragment()).setData();
                            else if (getCurrentFragment() instanceof FragmentStoreDetail)
                                ((FragmentStoreDetail) getCurrentFragment()).setData();
                        } else {
                            counter = 0;
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    MY_PERMISSIONS_REQUEST_LOCATION);
                        }
                    }
                } catch (ApiException exception) {
                    switch (exception.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            // Location settings are not satisfied. But could be fixed by showing the
                            // user a dialog.
                            if(counter == 0){
                                try {

                                    // Cast to a resolvable exception.
                                    ResolvableApiException resolvable = (ResolvableApiException) exception;
                                    // Show the dialog by calling startResolutionForResult(),
                                    // and check the result in onActivityResult().
                                    resolvable.startResolutionForResult(MainActivity.this, REQUEST_CHECK_SETTINGS);
                                    counter+=1;
                                } catch (IntentSender.SendIntentException e) {
                                    // Ignore the error.
                                }
                            }

                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            // Location settings are not satisfied. However, we have no way to fix the
                            // settings so we won't show the dialog.
                            counter = 1;
                            showToast(userDefault.IDLanguage() ?
                                    "Lokasi tidak bisa di dapatkan karena masalah device" :
                                    "Location can't be found due to device error");
                            break;
                    }
                }
            }
        });

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if (intent.getExtras() != null) {
            for (String key : intent.getExtras().keySet()) {
                String value = intent.getExtras().getString(key);
                if (StarbucksID.Companion.getDebug()) Log.d("XXX New Intent XXX", "Key: " + key + " Value: " + value);
            }

            // Inbox Item
            if (intent.getStringExtra("action_type") != null &&
                    intent.getStringExtra("action_type").equals("landing_page")){

                msg = new MessageModel();
                msg.setMsgSubject(intent.getStringExtra("title"));
                msg.setMsgDtl(intent.getStringExtra("body"));
                msg.setMsgImage(intent.getStringExtra("image"));

                displayView(0);
                handlePN();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        app = StarbucksID.Companion.getInstance();
        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                String value = getIntent().getExtras().getString(key);
                if (StarbucksID.Companion.getDebug()) Log.d("XXX Intent XXX", "Key: " + key + " Value: " + value);
            }

            // Inbox Item
            if (getIntent().getStringExtra("action_type") != null &&
                    getIntent().getStringExtra("action_type").equals("landing_page")) {

                msg = new MessageModel();
                msg.setMsgSubject(getIntent().getStringExtra("title"));
                msg.setMsgDtl(getIntent().getStringExtra("body"));
                msg.setMsgImage(getIntent().getStringExtra("image"));
            }
        }



        if (userDefault == null) userDefault = UserDefault.getInstance(getApplicationContext());
        if (picasso == null) picasso = Picasso.with(this);
        if (locManager == null) locManager = (LocationManager) getApplication().getSystemService(Context.LOCATION_SERVICE);
        if (mShakeDetector == null) mShakeDetector = new ShakeDetector();
        if (apiService == null) apiService = app.getApiService();

        toolbar = findViewById(R.id.toolbar);
        FLC = findViewById(R.id.FLC);
        tabLayout = findViewById(R.id.tabs);

        CLToolbar = toolbar.findViewById(R.id.CLToolbar);
        tvTier = toolbar.findViewById(R.id.tvTier);
        tvTitle = toolbar.findViewById(R.id.detailDescription);
        tvSubtitle = toolbar.findViewById(R.id.tvSubtitle);
        pb = findViewById(R.id.pb);
        pb.setVisibility(View.GONE);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAcc = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if(userDefault.getOtpFlag()==1){
            userDefault.setOtpFlag(0);
            Fragment fragment = new FragmentSetPassCode();
            Bundle bundle = new Bundle();
            bundle.putBoolean("IS_NEW_PASS_CODE", true);
            fragment.setArguments(bundle);
            cFragmentNoBS(fragment);
        } else{
            String passcode = userDefault.getPasscode();
            if(passcode!=null) {
                if (passcode.length() < 5) {
                    showForceLogout();
                }if (passcode.length() == 6){
                    cFragmentNoBS(new FragmentPassCode());
                }
            } else {
                showForceLogout();
            }
        }

    }


    /*TEST DEBUG ONLY*/
    private void test() {
        String advertiser_id = Settings.Secure.getString(getApplicationContext().
                        getContentResolver(), Settings.Secure.ANDROID_ID);
        String uuid = UUID.randomUUID().toString();

        TelephonyManager tel = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String networkOperator = tel.getNetworkOperator();

        int mcc = 510;
        int mnc = 7;
        String counrty_code = "";
        String op_name = "Telkom";
        if (!TextUtils.isEmpty(networkOperator)) {
            mcc = Integer.parseInt(networkOperator.substring(0, 3));
            mnc = Integer.parseInt(networkOperator.substring(3));
            op_name = tel.getNetworkOperatorName();
            counrty_code = tel.getSimCountryIso();
        }



        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        String screen_size = height + "x" + width;

        Log.i(TAG, "Carrier Network Code: " + mnc);
        Log.i(TAG, "Country Code : " + counrty_code);
        Log.i(TAG, "Carrier Name: " + op_name);
        Log.i(TAG, "Density : " + getResources().getDisplayMetrics().density);
        Log.i(TAG, "Timezone : " + TimeZone.getDefault().getID());
        Log.i(TAG, "Screen : " + screen_size);
        Log.i(TAG, "Model : " + Build.MODEL);
        Log.i(TAG, "Locale : " + Locale.getDefault().getLanguage());
        Log.i(TAG, "Version : " + Build.VERSION_CODES.BASE);
        Log.i(TAG, "Brand : " + Build.BRAND);
        Log.i(TAG, "Manufacturer : " + Build.MANUFACTURER);
        Log.i(TAG, "Advertiser ID? : " + advertiser_id);
        Log.i(TAG, "UUID : " + uuid);
        Log.i(TAG, "Platform : " +"Android");
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(
                MainActivity.this,  new OnSuccessListener<InstanceIdResult>() {
                    @Override
                    public void onSuccess(InstanceIdResult instanceIdResult) {
                        Log.i(TAG, "Firebase Token : " + instanceIdResult.getToken());
                    }
                });
        Log.i(TAG, "Acc Token : " + userDefault.getAccToken());
    }

    ///**********************************************************/



    ///*******************Activity Func********************/
    //Set Base View
    public void setBaseView() {
        toolbar.setVisibility(View.VISIBLE);
        tabLayout.setVisibility(View.VISIBLE);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                displayView(tab.getPosition());
                tab.setIcon(selectedTabIcons[tab.getPosition()]);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        if (dialogPay == null) {
            dialogPay = new Dialog(this);
            dialogPay.setContentView(R.layout.dialog_pay);

            Window window = dialogPay.getWindow();
            if (window != null) {
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                window.setGravity(Gravity.CENTER);
                window.setBackgroundDrawableResource(android.R.color.transparent);
            }

        }

        //Display Home tabs and change icon
        displayView(0);
        Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(selectedTabIcons[0]);
        setShake();

    }

    //Set Shake Detector
    private void setShake() {
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {
            @Override
            public void onShake(int count) {
                if (userDefault.stp()) {
                    if (userResponseModel != null) {
                        if (!dialogPay.isShowing())
                            payPopUp(userResponseModel.getUser().getUserProfile().getDefaultCard());
                        else dialogPay.dismiss();
                    } else showToast(userDefault.IDLanguage() ?
                            getString(R.string.no_card_display_detail_id) :
                            getString(R.string.no_card_display_detail));
                }
            }
        });

        mSensorManager.registerListener(mShakeDetector, mAcc, SensorManager.SENSOR_DELAY_GAME);

        // Handle Push Notification if present
        handlePN();
    }

    //Push Notification Handler
    private void handlePN(){
        // Push Notification
        if (msg != null) {
            cFragmentWithBS(FragmentMessage.pushNotif(msg));
            msg = null;
        }
    }

    //Display View by Position
    private void displayView(int position) {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab ti = tabLayout.getTabAt(i);
            if (ti != null) ti.setIcon(defaultTabIcons[i]);
        }
        switch (position) {

            case 0:
                counter = 1;
                cFragmentNoBS(new FragmentHome());
                break;
            case 1:
                counter = 1;
                cFragmentNoBS(new FragmentPay());
                break;

            case 2:
                counter = 1;
                cFragmentNoBS(new FragmentRewards());
                break;

            case 3:
                counter = 1;
                cFragmentNoBS(new MenusFragment());
                break;

            case 4:
                counter = 0;
                cFragmentNoBS(new FragmentStores());
                break;
        }
    }
    /**********************************************************/



    /*******************Fragment Transaction********************/
    //BackStackPremium
    public void cFragmentPremium(Fragment fragment) {
        setToolbarTitle("");

        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FLC, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();

        curFragment = fragment;
        enableNavigationIcon();
    }
    public void cFragmentWithBundlePremium(Fragment fragment, Bundle bundle) {
        setToolbarTitle("");
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragment.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FLC, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();

        curFragment = fragment;
        enableNavigationIcon();
    }
    // Change Fragment Without Backstack
    public void cFragmentNoBS(Fragment fragment) {
//        counter = 0;
        setToolbarTitle("");

        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FLC, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commitAllowingStateLoss();
        curFragment = fragment;
        disableNavigationIcon();
    }



    String FragmentOfferDetailId;

    public String getFragmentOfferDetailId() {
        return FragmentOfferDetailId;
    }

    public void setFragmentOfferDetailId(String fragmentOfferDetailId) {
        FragmentOfferDetailId = fragmentOfferDetailId;
    }

    // Change Fragment With Backstack
    public void cFragmentWithBS(Fragment fragment) {
        counter = 0;
        setToolbarTitle("");

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.addToBackStack(null);
        ft.replace(R.id.FLC, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commitAllowingStateLoss();

        curFragment = fragment;
        enableNavigationIcon();
    }

    public void cFragmentWithBundle(Fragment fragment, Bundle bundle) {
        counter = 0;
        setToolbarTitle("");

        fragment.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.addToBackStack(null);
        ft.replace(R.id.FLC, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();

        curFragment = fragment;
        enableNavigationIcon();
    }


    // Get Current Visible Fragment
    public Fragment getCurrentFragment() {
        counter = 0;
        return getSupportFragmentManager().findFragmentById(R.id.FLC);
    }

    public void refFragment(){
        counter = 0;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.detach(curFragment);
        ft.attach(curFragment);
        ft.commit();
    }
    /**********************************************************/



    /******************Get Data & Val********************************/
    public UserResponseModel getUser() {
        return this.userResponseModel;
    }

    public UserDefault getUserDefault() {
        if (userDefault == null) userDefault = UserDefault.getInstance(getApplicationContext());
        return userDefault;
    }

    public Picasso getPicasso() {
        if (picasso == null) picasso = Picasso.with(this);
        return picasso;
    }

    public ApiInterface getApiService() {
        if (apiService == null) apiService = app.getApiService();
        return apiService;
    }

    ///**********************************************************/



    ///******************View Related********************************/
    // Display Toast
    public void showToast(String s) {
        PopUpUtil.sLongToast(this, s);
    }

    // Display Home Tab Toolbar
    public void setHomeToolbar(String title, String subtitle, String Tier) {
        CLToolbar.setVisibility(View.VISIBLE);
        tvTitle.setText(title);
        tvSubtitle.setText(subtitle);
        tvTier.setText(Html.fromHtml(Tier));
    }

    // Display Tab Toolbar Other Than Home Tab
    public void setToolbarTitle(String title) {
        CLToolbar.setVisibility(View.GONE);
        toolbar.setTitle(title);
        if (getSupportActionBar() == null) setSupportActionBar(toolbar);
    }

    // Display Tab Toolbar for Push Notification Only
    public void setToolbarPN(String title) {
        CLToolbar.setVisibility(View.GONE);
        toolbar.setTitle(title);

        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_action_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    // Enable Back Button
    public void enableNavigationIcon() {
        toolbar.setNavigationIcon(R.drawable.ic_action_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                    toolbar.setNavigationIcon(null);
                }
                onBackPressed();
            }
        });
    }

    // Disable Back Button
    public void disableNavigationIcon() {
        toolbar.setNavigationIcon(null);
    }
    ///**********************************************************/






    ///***********************REST Call**************************/


    //Get User Profile
    public void getUserData() {
        if (!checkConnection()) return;
        if (apiService == null) apiService = app.getApiService();

        Call<UserResponseModel> callUser = apiService.getUser("getprofile",
                genSBUX("email="+userDefault.getEmail()+
                        "&lang="+userDefault.getLanguage()));
        toolbar.setVisibility(View.GONE);
        tabLayout.setVisibility(View.GONE);
        callUser.enqueue(new Callback<UserResponseModel>() {
            @Override
            public void onResponse(Call<UserResponseModel> call, Response<UserResponseModel> response) {
                toolbar.setVisibility(View.VISIBLE);
                tabLayout.setVisibility(View.VISIBLE);
                if (response.isSuccessful() && response.body() != null) {
                    userResponseModel = response.body();
                    if (successResponse(userResponseModel.getStatus(), userResponseModel.getProcessMsg())) {
                        userResponseModel.getUser().setIdentifiers(DataUtil.mapCard(
                                userResponseModel.getUser().getIdentifiers(),
                                userResponseModel.getUser().getUserProfile().getDefaultCard()));

                        if (!userDefault.isPn()) {
                            FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(
                                    MainActivity.this,  new OnSuccessListener<InstanceIdResult>() {
                                        @Override
                                        public void onSuccess(InstanceIdResult instanceIdResult) {
                                            regPN(instanceIdResult.getToken());
                                        }
                                    });

                        }

                        if (getCurrentFragment() instanceof FragmentChangeDetail) onBackPressed();
                        else if (getCurrentFragment() instanceof FragmentPay)
                            ((FragmentPay) getCurrentFragment()).setData();
                        else if (getCurrentFragment() instanceof FragmentHome)
                            ((FragmentHome) getCurrentFragment()).setData();
                        else if (getCurrentFragment() instanceof FragmentRewards)
                            ((FragmentRewards) getCurrentFragment()).setData ();
                        else if (getCurrentFragment() instanceof FragmentAddTab)
                            ((FragmentAddTab) getCurrentFragment()).setBaseView ();
                        else if (getCurrentFragment() instanceof FragmentInbox)
                            ((FragmentInbox) getCurrentFragment()).setupViewPager();
                        else if (getCurrentFragment() instanceof FragmentOffer)
                            ((FragmentOffer) getCurrentFragment()).setData();
                        else if (getCurrentFragment() instanceof FragmentOfferDetail)
                            ((FragmentOfferDetail) getCurrentFragment()).setData();
                        else if (getCurrentFragment() instanceof FragmentMessage)
                            ((FragmentMessage) getCurrentFragment()).setData();
                        else if (getCurrentFragment() instanceof FragmentStepStatusPremium)
                            ((FragmentStepStatusPremium) getCurrentFragment()).refreshDataProfile();
                        else if (getCurrentFragment() instanceof FragmentMessageDetail)
                            return;
                        else getCurrentFragment().onResume();
                    }
                } else {
                    checkServer(String.valueOf(response.code()), response.message());
                }
            }
            @Override
            public void onFailure(Call<UserResponseModel> call, Throwable t) {
                restFailure(t);
            }
        });
    }



    //Refresh Acc Token
    public void getAccToken() {
        if (!checkConnection()) return;
        if (apiService == null) apiService = app.getApiService();
        Call<ResponseRefreshToken> callAcc = apiService.getAcc("refresh_token",
                genSBUX("email=" + userDefault.getEmail() +
                        "&sm_token=" + userDefault.getAccToken() +
                        "&sm_token_refresh=" + userDefault.getRefreshToken()));

        callAcc.enqueue(new Callback<ResponseRefreshToken>() {
            @Override
            public void onResponse(Call<ResponseRefreshToken> call, Response<ResponseRefreshToken> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ResponseRefreshToken res = response.body();

                    if (successResponse(res.getStatus(), res.getProcessMsg())) {
                        userDefault.setAccToken(res.getAccessToken());

                        /*Display*/
                        if (getCurrentFragment() instanceof FragmentPay)
                            ((FragmentPay) getCurrentFragment()).setData();
                        else if (getCurrentFragment() instanceof FragmentHome)
                            ((FragmentHome) getCurrentFragment()).setData();
                        else if (getCurrentFragment() instanceof FragmentRewards)
                            ((FragmentRewards) getCurrentFragment()).setData();
                        else if (getCurrentFragment() instanceof FragmentRewardHistory)
                            ((FragmentRewardHistory) getCurrentFragment()).setData();
                        else if (getCurrentFragment() instanceof FragmentHistoryCardProfile)
                            ((FragmentHistoryCardProfile) getCurrentFragment()).setData();
                        else if (getCurrentFragment() instanceof FragmentReload)
                            ((FragmentReload) getCurrentFragment()).setBaseView();

                            /*Action*/
                        else if (getCurrentFragment() instanceof FragmentChangeDetail)
                            ((FragmentChangeDetail) getCurrentFragment()).sendData();
                        else if (getCurrentFragment() instanceof FragmentPersonal)
                            ((FragmentPersonal) getCurrentFragment()).changePwd();
                        else if (getCurrentFragment() instanceof FragmentInbox)
                            ((FragmentInbox) getCurrentFragment()).setData();
                        else if (getCurrentFragment() instanceof FragmentMessage)
                            ((FragmentMessage) getCurrentFragment()).setData();
                        else if (getCurrentFragment() instanceof FragmentOffer)
                            ((FragmentOffer) getCurrentFragment()).setData();
                        else if (getCurrentFragment() instanceof FragmentOfferDetail)
                            ((FragmentOfferDetail) getCurrentFragment()).setData();
                        else if (getCurrentFragment() instanceof FragmentMessageDetail)
                            return;
                        else getCurrentFragment().onResume();
                    } else {
                        showForceLogout();
                    }

                } else {
                    checkServer(String.valueOf(response.code()), response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseRefreshToken> call, Throwable t) {
                restFailure(t);
            }
        });
    }

    //Register Push Notification
    private void regPN(final String tkn) {
        if (!checkConnection()) return;

        /*Get Device Properties*/
        @SuppressLint("HardwareIds")
        String advertiser_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

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
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        String screen_size = height + "x" + width;

        if (apiService == null) apiService = app.getApiService();

        userDefault.setPn(false);
        pnAttempt += 1;

        Call<ResponseBase> call = apiService.pnReg("reg_pn",
                genSBUX("email=" + userDefault.getEmail() +
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
                        "&push_token=" + tkn +
                        "&advertiser_id=" + advertiser_id
                ));

        call.enqueue(new Callback<ResponseBase>() {
            @Override
            public void onResponse(Call<ResponseBase> call, Response<ResponseBase> response) {
                if (response.isSuccessful()) {
                    userDefault.setPn(true);
                }else{
                    if (pnAttempt < 3) regPN(tkn);
                }
            }

            @Override
            public void onFailure(Call<ResponseBase> call, Throwable t) {
            }
        });
    }

    //Sign Out
    public void signOut(final int opid) {
        if (!checkConnection()) return;
        DialogUtil.sProDialog(this);

        if (apiService == null) apiService = app.getApiService();

        Call<LoginResponseModel> call = apiService.signOut("logout",
                DataUtil.genSBUX("email=" + userDefault.getEmail() +
                        "&mobid=" + getString(R.string.rest_mob_id) +
                        "&mobkey=" + getString(R.string.rest_mob_key)
                ));

        call.enqueue(new Callback<LoginResponseModel>() {
            @Override
            public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {
                if (response.isSuccessful()) {
                    if (response.body().getReturnCode().equals("00")) {
                        userDefault.logOut();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                    } else {
                        showToast(response.body().getProcessMsg());

                        switch (opid){
                            case 0: {
                                // Force Logout
                                showForceLogout();
                                break;
                            }

                            case 1: {
                                // Opt Logout from Passcode
                                showOptionLogout(opid);
                                break;
                            }

                            case 2: {
                                // Opt Logout from Passcode
                                showOptionLogout(opid);
                                break;
                            }

                            default: {
                                break;
                            }
                        }
                    }
                } else {
                    checkServer(String.valueOf(response.code()), response.message());


                    switch (opid){
                        case 0: {
                            // Force Logout
                            showForceLogout();
                            break;
                        }

                        case 1: {
                            // Opt Logout from Passcode
                            showOptionLogout(opid);
                            break;
                        }

                        case 2: {
                            // Opt Logout from Passcode
                            showOptionLogout(opid);
                            break;
                        }

                        default: {
                            break;
                        }
                    }
                }

                hideProgressDialog();
            }

            @Override
            public void onFailure(Call<LoginResponseModel> call, Throwable t) {
                restFailure(t);
                hideProgressDialog();
            }
        });

    }

    //Update Message Status
    public void updateMessage(final String message_id, final int attempt) {
        if (!checkConnection()) return;
        if (apiService == null) apiService = app.getApiService();

        Call<ResponseBase> call =  apiService.updateMessage("inboxUpdate",
                genSBUX("email=" + userDefault.getEmail() +
                        "&sm_token=" + userDefault.getAccToken() +
                        "&card_number=" + userResponseModel.getUser().getExternalId() +
                        "&message_id=" + message_id)
        );

        call.enqueue(new Callback<ResponseBase>() {
            @Override
            public void onResponse(Call<ResponseBase> call, Response<ResponseBase> response) {
                if (!response.isSuccessful() && !response.body().getReturnCode().equals("00")) {
                    if (attempt < 3) updateMessage(message_id, attempt+1);
                }
            }

            @Override
            public void onFailure(Call<ResponseBase> call, Throwable t) {
                if (attempt < 3) updateMessage(message_id, attempt+1);
            }
        });
    }

    ///**********************************************************/



    ///********************REST Error Handler********************/
    // Global API Response Code
    public boolean successResponse(String code, String msg) {
        switch (code.toLowerCase()) {
            case "00": return true;
            case "01":
                showForceLogout();
                return false;
            case "04":
                if (msg != null)
                    Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
                else errHandler(0);
                return false;
            case "09":
                getAccToken();
                return false;
            case "9028":
                if (msg != null)
                    Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
                else errHandler(0);
                return false;
            case "expired":
                getAccToken();
                return false;
            case "failure":
                if (msg != null)
                    Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
                else errHandler(0);
                return false;
            case "ok": return true;
            default: return true;
        }
    }

    // Method to manually check connection status
    public boolean checkConnection() {
        if (!ConnectionDetector.isConnected()) {
            DialogUtil.sErrDialog(MainActivity.this, userDefault, 1,
                    new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            if (curFragment != null) refFragment();
                        }
                    });
            return false;
        }
        return true;
    }

    // Check Server Availability
    public boolean checkServer(String code, String m) {
        if (!code.startsWith("2")) {
            errHandler(0);
            return false;
        }else{
            DialogUtil.sNotDialog(this, m);
            return true;
        }
    }

    // Retrofit error handler
    public void restFailure(Throwable t) {
        if (!(t instanceof IOException)) errHandler(0);
    }

    // Change Fragment error handler
    public void errHandler(Integer opid){
        if (!(getCurrentFragment() instanceof ErrorHandlerFragment)) {
            Bundle bundle = new Bundle();
            bundle.putInt("operation_id",opid);
            cFragmentWithBundle(new ErrorHandlerFragment(), bundle);
        }
    }

    ///**********************************************************/



    ///**********************Dialog & View***********************/
    // Shake Listener Dialog
    public void payPopUp(String cardNumber) {
        ImageView imgBarcode = dialogPay.findViewById(R.id.imgBarcode);
        TextView txtPayCarNumber = dialogPay.findViewById(R.id.txtPayDialogCardNumber);
        TextView txtPayBarista = dialogPay.findViewById(R.id.txtPayBarista);
        Button btnPayDone = dialogPay.findViewById(R.id.btnPayDone);

        txtPayBarista.setText(userDefault.IDLanguage()
                ? getString(R.string.pay_detail_id)
                : getString(R.string.pay_detail));

        txtPayCarNumber.setText(DataUtil.maskCard(cardNumber));

        Bitmap bitmap;
        try {
            bitmap = DataUtil.encodeAsBitmap(
                    cardNumber, BarcodeFormat.PDF_417, 1000, 300
            );
            imgBarcode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();

        }

        final Runnable opener = new Runnable() {
            public void run() {
                dialogPay.dismiss();
            }

        };
        final Handler handler = new Handler();
        handler.postDelayed(opener, 60000);
        btnPayDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeCallbacks(opener);
                dialogPay.dismiss();
            }
        });

        dialogPay.show();
    }

    // Snackbar for setting
    private void showSnack(String s){
        if (snack == null) {
            snack = Snackbar.make(FLC, s, Snackbar.LENGTH_LONG);

            View view = snack.getView();
            TextView tv = view.findViewById(R.id.snackbar_text);
            tv.setTextColor(getResources().getColor(R.color.white));

            snack.setAction("Setting", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivityForResult(intent, MY_PERMISSIONS_REQUEST_LOCATION);
                }
            });



            snack.setActionTextColor(getResources().getColor(R.color.error_state_red));
        }
        snack.show();
    }

    // Progress Dialog
    public void showProgressDialog() {
        DialogUtil.sProDialog(this);
    }

    // Progress Dialog
    public void hideProgressDialog() {
        DialogUtil.hProDialog();
    }

    public void showOptionLogout(final int opid) {
        final Dialog dialogLogOut = new Dialog(this);
        dialogLogOut.setCanceledOnTouchOutside(false);
        dialogLogOut.setCancelable(false);
        dialogLogOut.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogLogOut.setContentView(R.layout.dialog_notif_2_button);

        Window window = dialogLogOut.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawableResource(android.R.color.transparent);


        TextView tvHeader = dialogLogOut.findViewById(R.id.tvHeader);
        TextView tvContent = dialogLogOut.findViewById(R.id.tvNotif);
        Button btOk = dialogLogOut.findViewById(R.id.btOk);
        Button btCancel = dialogLogOut.findViewById(R.id.btCancel);

        if (userDefault.IDLanguage()) {
            btCancel.setText(getString(R.string.id_cancel));
            btOk.setText(getString(R.string.id_sign_out));
        }else{
            btCancel.setText(getString(R.string.cancel));
            btOk.setText(getString(R.string.sign_out));
        }


        tvHeader.setText(userDefault.IDLanguage()
                ? getString(R.string.warning_id)
                : getString(R.string.warning));

        // Passcode Logout
        if (opid == 1) tvContent.setText(userDefault.IDLanguage()
                ? getString(R.string.security_purpose_id)
                : getString(R.string.security_purpose));

        // Setting Logout
        else tvContent.setText(!userDefault.IDLanguage()
                ? getString(R.string.sure_logout)
                : getString(R.string.sure_logout_id));

        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut(opid);
                dialogLogOut.dismiss();
            }
        });

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogLogOut.dismiss();
            }
        });
        dialogLogOut.show();
    }

    public void showForceLogout() {
        final Dialog dialogLogOut = new Dialog(MainActivity.this);
        dialogLogOut.setCanceledOnTouchOutside(false);
        dialogLogOut.setCancelable(false);
        dialogLogOut.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogLogOut.setContentView(R.layout.dialog_notif_1_button);

        Window window = dialogLogOut.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawableResource(android.R.color.transparent);

        TextView txtNotifHeader = dialogLogOut.findViewById(R.id.tvHeader);
        TextView txtNotifContent = dialogLogOut.findViewById(R.id.tvNotif);
        Button btnNotif = dialogLogOut.findViewById(R.id.btOk);

        txtNotifHeader.setText(userDefault.IDLanguage()
                ? "Otentikasi Salah"
                : "Authentication Failed");
        txtNotifContent.setText(userDefault.IDLanguage()
                ? "Sesi Anda telah kedaluwarsa. " +
                        "Silakan masuk lagi."
                : "Your session has expired. " +
                        "Please sign in again.");

        btnNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//               signOut(0);
                userDefault.logOut();
                dialogLogOut.dismiss();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();

            }
        });

        dialogLogOut.show();
    }

    public String genSBUX(String s) {
        s = "sm_token=" + userDefault.getAccToken() + "&" + s;

        if (StarbucksID.Companion.getDebug()) {
            Log.i("TAG", "genSBUX: " + s);
            Log.i("TAG", "genSBUX: " + RestSecurityGen.encrypt(s));
        }

        return RestSecurityGen.encrypt(s);
    }



//    public void sDialogPassCode() {
//        dialogPC = new Dialog(this);
//        dialogPC.setCanceledOnTouchOutside(false);
//        dialogPC.setCancelable(false);
//        dialogPC.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialogPC.setContentView(R.layout.dialog_passcode);
//
//        Window window = dialogPC.getWindow();
//        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        window.setGravity(Gravity.CENTER);
//        window.setBackgroundDrawableResource(android.R.color.transparent);
//        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
//
//        final SingleEditText PC1 = dialogPC.findViewById(R.id.EPC1);
//        final SingleEditText PC2 = dialogPC.findViewById(R.id.EPC2);
//        final SingleEditText PC3 = dialogPC.findViewById(R.id.EPC3);
//        final SingleEditText PC4 = dialogPC.findViewById(R.id.EPC4);
//        final SingleEditText PC5 = dialogPC.findViewById(R.id.EPC5);
//        final SingleEditText PC6 = dialogPC.findViewById(R.id.EPC6);
//        final TextView txtEnter = dialogPC.findViewById(R.id.txtEnter);
//        final Button btForgot = dialogPC.findViewById(R.id.btForgot);
//
//        btForgot.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showOptionLogout(1);
//            }
//        });
//
//        PC1.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (s.toString().equals("")) {
//                    PC1.requestFocus();
//                } else {
//                    PC2.requestFocus();
//                }
//            }
//        });
//        PC1.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View view, int i, KeyEvent keyEvent) {
//                if (i == KeyEvent.KEYCODE_DEL) {
//                    PC1.requestFocus();
//                }
//                return false;
//            }
//        });
//
//
//        PC2.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (s.toString().equals("")) {
//                    PC1.requestFocus();
//                } else {
//                    PC3.requestFocus();
//                }
//            }
//        });
//
//        PC2.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View view, int i, KeyEvent keyEvent) {
//
//                if (i == KeyEvent.KEYCODE_DEL) {
//                    if (PC2.getText().toString().equals("")) {
//                        PC1.setText("");
//                        PC1.requestFocus();
//                    } else
//                        PC1.requestFocus();
//                }
//
//                return false;
//            }
//        });
//
//        PC3.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (s.toString().equals("")) {
//                    PC2.requestFocus();
//                } else {
//                    PC4.requestFocus();
//                }
//            }
//        });
//
//        PC3.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View view, int i, KeyEvent keyEvent) {
//
//                if (i == KeyEvent.KEYCODE_DEL) {
//                    if (PC3.getText().toString().equals("")) {
//                        PC2.setText("");
//                        PC1.requestFocus();
//                    } else
//                        PC2.requestFocus();
//                }
//
//                return false;
//            }
//        });
//
//        PC4.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (s.toString().equals("")) {
//                    PC3.requestFocus();
//                } else {
//                    PC5.requestFocus();
//                }
//            }
//        });
//
//        PC4.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View view, int i, KeyEvent keyEvent) {
//
//                if (i == KeyEvent.KEYCODE_DEL) {
//                    if (PC4.getText().toString().equals("")) {
//                        PC3.setText("");
//                        PC2.requestFocus();
//                    } else
//                        PC3.requestFocus();
//                }
//
//                return false;
//            }
//        });
//
//        PC5.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (s.toString().equals("")) {
//                    PC4.requestFocus();
//                } else {
//                    PC6.requestFocus();
//                }
//            }
//        });
//
//        PC5.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View view, int i, KeyEvent keyEvent) {
//
//                if (i == KeyEvent.KEYCODE_DEL) {
//                    if (PC5.getText().toString().equals("")) {
//                        PC4.setText("");
//                        PC3.requestFocus();
//                    } else
//                        PC4.requestFocus();
//                }
//
//                return false;
//            }
//        });
//
//        PC6.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (s.toString().equals("")) {
//                    PC5.requestFocus();
//                } else {
//                    String curPasscode = PC1.getText().toString() + PC2.getText().toString() + PC3.getText().toString() + PC4.getText().toString()
//                            + PC5.getText().toString()+ PC6.getText().toString();
//
//                    if (curPasscode.equals(userDefault.getPasscode())) {
//                        dialogPC.dismiss();
//                        setBaseView();
//                    } else {
//                        dialogPC.dismiss();
//                        DialogUtil.sNotDialog1Btn(MainActivity.this,
//                                !userDefault.IDLanguage() ? getString(R.string.en_failure) : getString(R.string.id_failure),
//                                !userDefault.IDLanguage() ? getString(R.string.passcode_not_match) : getString(R.string.passcode_not_match_id),
//                                new DialogInterface.OnDismissListener() {
//                                    @Override
//                                    public void onDismiss(DialogInterface dialogInterface) {
//                                        sDialogPassCode();
//                                    }
//                                }
//                        );
//                        PC1.setText("");
//                        PC2.setText("");
//                        PC3.setText("");
//                        PC4.setText("");
//                        PC5.setText("");
//                        PC6.setText("");
//                        PC1.requestFocus();
//                    }
//
//                }
//            }
//        });
//
//        PC6.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View view, int i, KeyEvent keyEvent) {
//                if (i == KeyEvent.KEYCODE_DEL) {
//                    if (PC6.getText().toString().equals("")) {
//                        PC5.setText("");
//                    }
//                    PC5.requestFocus();
//                }
//                return false;
//            }
//        });
//
//        if (userDefault.IDLanguage()) {
//            btForgot.setText("Lupa kode sandi?");
//            txtEnter.setText("Masukkan kode sandi");
//        }
//
//        dialogPC.setOnKeyListener(new DialogInterface.OnKeyListener() {
//            @Override
//            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
//                if (i == android.view.KeyEvent.KEYCODE_BACK) {
//                    finish();
//                    return true;
//                }
//                return false;
//            }
//        });
//
//        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
//        inputMethodManager.showSoftInput(PC1, InputMethodManager.SHOW_IMPLICIT);
//
//        dialogPC.show();
//    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            setToolbarTitle("");
            getSupportFragmentManager().popBackStack();
            if (getSupportFragmentManager().getBackStackEntryCount() == 1)
                disableNavigationIcon();
        } else {
            if (tabLayout.getSelectedTabPosition() != 0 && tabLayout != null) {
                setToolbarTitle("");
                TabLayout.Tab tab = tabLayout.getTabAt(0);
                if (tab != null) tab.select();
            } else {
                if (doubleBackToExitPressedOnce) {
                    super.onBackPressed();
                    finish();
                } else {
                    this.doubleBackToExitPressedOnce = true;
                    Toast.makeText(this, userDefault.IDLanguage()
                            ? this.getString(R.string.id_double_click_to_exit)
                            : this.getString(R.string.en_double_click_to_exit),
                            Toast.LENGTH_SHORT).show();
                }

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, 2000);
            }
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        app.removeInternetConnectionListener();
//        cFragmentNoBS(new FragmentPassCode());

    }

    @Override
    public void onResume(){
        super.onResume();
        app.setInternetConnectionListener(this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (dialogPay != null && dialogPay.isShowing()) dialogPay.dismiss();
        if (dialogPC != null && dialogPC.isShowing()) dialogPC.dismiss();
    }

    @Override
    public void onInternetUnavailable() {
        new Thread() {
            public void run() {
                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DialogUtil.sErrDialog(MainActivity.this, userDefault, 1,
                                    new DialogInterface.OnDismissListener() {
                                        @Override
                                        public void onDismiss(DialogInterface dialogInterface) {
                                            if (curFragment != null) refFragment();
                                        }
                                    });
                        }
                    });
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public static Bitmap getIdCard() {
        return idCard;
    }

    public static void setIdCard(Bitmap idCard) {
        MainActivity.idCard = idCard;
    }

    public static Bitmap getSelfieCard() {
        return selfieCard;
    }

    public static void setSelfieCard(Bitmap selfieCard) {
        MainActivity.selfieCard = selfieCard;
    }
}
