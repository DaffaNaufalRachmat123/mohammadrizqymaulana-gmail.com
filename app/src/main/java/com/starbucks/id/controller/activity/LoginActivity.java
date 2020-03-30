package com.starbucks.id.controller.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

import com.starbucks.id.R;
import com.starbucks.id.controller.fragment.login_fragment.LoginFragment;
import com.starbucks.id.helper.ConnectionDetector;
import com.starbucks.id.helper.StarbucksID;
import com.starbucks.id.helper.UserDefault;
import com.starbucks.id.helper.utils.DialogUtil;
import com.starbucks.id.rest.ApiInterface;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {

    public UserDefault userDefault;
    public Fragment curFragment;
    public ApiInterface apiService;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userDefault = UserDefault.getInstance(getApplicationContext());

        StarbucksID app = StarbucksID.Companion.getInstance();
        apiService = app.getApiService();

        cFragmentNoBs(new LoginFragment());
    }

    public void cFragmentNoBs(Fragment fragment) {
        curFragment = fragment;
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.replace(R.id.FLC, fragment);
        ft.commit();
    }

    // Change Fragment With Backstack
    public void cFragmentWithBS(Fragment fragment) {
        curFragment = fragment;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.addToBackStack(null);
        ft.replace(R.id.FLC, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }

    public void refFragment(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.detach(curFragment);
        ft.attach(curFragment);
        ft.commit();
    }

    /*Error Handler*/
    // Method to manually check connection status
    public boolean checkConnection() {
        if (!ConnectionDetector.isConnected()) {
            DialogUtil.sErrDialog(this, userDefault, 1, new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    refFragment();
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
                    refFragment();
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
                    refFragment();
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    // Progress Dialog
    public void showProgressDialog() {
        DialogUtil.sProDialog(this);
    }

    // Progress Dialog
    public void hideProgressDialog() {
        DialogUtil.hProDialog();
    }

    public boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
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
