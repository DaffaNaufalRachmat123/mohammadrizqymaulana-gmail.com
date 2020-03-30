package com.starbucks.id.helper.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.starbucks.id.R;
import com.starbucks.id.controller.extension.ArcProgress;
import com.starbucks.id.controller.extension.extendedView.ArcProgressAnimation;
import com.starbucks.id.helper.UserDefault;

import java.util.Random;

public class DialogUtil {

    private static Dialog mProgressDialog, dialogNotif;

    static LoadingDialog loadingDialog;

    public static void sProDialog(final Activity act) {
        mProgressDialog = new Dialog(act);

        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setCancelable(false);
        mProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mProgressDialog.setContentView(R.layout.dialog_loading);

        Window window = mProgressDialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawableResource(android.R.color.transparent);

        mProgressDialog.show();
    }

    public static void hProDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) mProgressDialog.dismiss();
    }

    public static void sNotDialog(final Activity act, String Message) {
        dialogNotif = new Dialog(act);
        dialogNotif.setCancelable(false);
        dialogNotif.setCanceledOnTouchOutside(false);
        dialogNotif.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogNotif.setContentView(R.layout.dialog_notif);

        Window window = dialogNotif.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawableResource(android.R.color.transparent);

        TextView tvNotif = dialogNotif.findViewById(R.id.tvNotif);
        Button btOk = dialogNotif.findViewById(R.id.btOk);

        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogNotif != null && dialogNotif.isShowing()) dialogNotif.dismiss();
            }
        });

        tvNotif.setText(Message);

        dialogNotif.show();
    }

    public static void sNotDialog1Btn(final Activity act, String header,
                                      String message, DialogInterface.OnDismissListener dc) {
        final Dialog dialogNotif = new Dialog(act);
        dialogNotif.setCanceledOnTouchOutside(false);
        dialogNotif.setCancelable(false);
        dialogNotif.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogNotif.setContentView(R.layout.dialog_notif_1_button);

        Window window = dialogNotif.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);




        TextView txtNotifHeader = dialogNotif.findViewById(R.id.tvHeader);
        TextView txtNotifContent = dialogNotif.findViewById(R.id.tvNotif);
        Button btnNotif = dialogNotif.findViewById(R.id.btOk);

        txtNotifHeader.setText(header);
        txtNotifContent.setText(message);

        btnNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogNotif.dismiss();
            }
        });

        dialogNotif.setOnDismissListener(dc);

        dialogNotif.show();
    }

    public static void sErrDialog(final Activity act, UserDefault userDefault, Integer opid, Dialog.OnDismissListener dm){
        final Dialog dialogError = new Dialog(act);

        dialogError.setCanceledOnTouchOutside(false);
        dialogError.setCancelable(false);
        dialogError.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogError.setContentView(R.layout.fragment_error);

        Window window = dialogError.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawableResource(android.R.color.transparent);

        ConstraintLayout CLMt = dialogError.findViewById(R.id.CLMt);
        ConstraintLayout CLCon = dialogError.findViewById(R.id.CLCon);
        TextView tvCon = dialogError.findViewById(R.id.tvCon);
        TextView tvMt = dialogError.findViewById(R.id.tvMt);
        Button btRetry = dialogError.findViewById(R.id.btRetry);
        Button btExit = dialogError.findViewById(R.id.btExit);

        if (userDefault.IDLanguage()) {
            tvCon.setText(act.getResources().getString(R.string.it_notif_id));
            tvMt.setText(act.getResources().getString(R.string.mt_notif_id));
        }

        if (opid == 0) {
            CLMt.setVisibility(View.VISIBLE);
            CLCon.setVisibility(View.GONE);
        } else {
            CLMt.setVisibility(View.GONE);
            CLCon.setVisibility(View.VISIBLE);
        }

        btRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogError.dismiss();
            }
        });

        btExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                act.finish();
                dialogError.dismiss();
            }
        });

        dialogError.setOnDismissListener(dm);

        dialogError.show();
    }

    // Arc Progress Bar Planner
    public static void sPlanner(final Activity act){
        final Dialog dialogPlanner = new Dialog(act);

        dialogPlanner.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogPlanner.setContentView(R.layout.item_planner_list);

        Window window = dialogPlanner.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawableResource(android.R.color.transparent);

        TextView tv = dialogPlanner.findViewById(R.id.tv);
        ArcProgress pb = dialogPlanner.findViewById(R.id.pb);
        ImageView iv = dialogPlanner.findViewById(R.id.iv);

//        pb.setProgress(getRandom(0, 100));
        int data = 80;
        ArcProgressAnimation anim = new ArcProgressAnimation(pb,0,data);
        anim.setDuration(2000);
        pb.startAnimation(anim);
//        pb.setProgress(data);

        iv.setImageDrawable(act.getDrawable(R.drawable.def_card));
        tv.setText(" ");

        dialogPlanner.show();
    }

    private static Integer getRandom(int min, int max){
        return  new Random().nextInt((max - min) + 1) + min;
    }

    //Horizontal progress Bar
    public static void sProgressHorizontal(final Activity act){
        final Dialog dialogPlanner = new Dialog(act);

        dialogPlanner.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogPlanner.setContentView(R.layout.progress_bar_3_in_1);

        Window window = dialogPlanner.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawableResource(android.R.color.white);
        int pbInt = 70;
        ProgressBar pb = dialogPlanner.findViewById(R.id.pb);
        pb.setProgress(pbInt);
        ImageView im1 = dialogPlanner.findViewById(R.id.imageView1);
        ImageView im2 = dialogPlanner.findViewById(R.id.imageView2);
        ImageView im3 = dialogPlanner.findViewById(R.id.imageView3);
        ImageView im4 = dialogPlanner.findViewById(R.id.imageView4);

        if(pbInt < 25){
            im1.setBackgroundResource(R.drawable.digi);
            im2.setBackgroundResource(R.drawable.digi);
            im3.setBackgroundResource(R.drawable.digi);
            im4.setBackgroundResource(R.drawable.digi);
        }
        if(pbInt > 24 && pbInt < 49){
            im1.setBackgroundResource(R.drawable.def_card);
            im2.setBackgroundResource(R.drawable.digi);
            im3.setBackgroundResource(R.drawable.digi);
            im4.setBackgroundResource(R.drawable.digi);
        }
        if(pbInt > 49 && pbInt < 74){
            im1.setBackgroundResource(R.drawable.def_card);
            im2.setBackgroundResource(R.drawable.def_card);
            im3.setBackgroundResource(R.drawable.digi);
            im4.setBackgroundResource(R.drawable.digi);
        }
        if(pbInt > 74 && pbInt < 99){
            im1.setBackgroundResource(R.drawable.def_card);
            im2.setBackgroundResource(R.drawable.def_card);
            im3.setBackgroundResource(R.drawable.def_card);
            im4.setBackgroundResource(R.drawable.digi);
        }
        if(pbInt == 100){
            im1.setBackgroundResource(R.drawable.def_card);
            im2.setBackgroundResource(R.drawable.def_card);
            im3.setBackgroundResource(R.drawable.def_card);
            im4.setBackgroundResource(R.drawable.def_card);
        }

        dialogPlanner.show();
    }

    public static void showLoading(final Context context, String text) {
        showLoading(context, "", true);
    }

    public static void showLoading(final Context context, String text, boolean b) {
        loadingDialog = new LoadingDialog(context);
        loadingDialog.setTextLoading(text);
        loadingDialog.setCancelable(b);
        loadingDialog.show();
    }

    public static void hideLoading() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }



}
