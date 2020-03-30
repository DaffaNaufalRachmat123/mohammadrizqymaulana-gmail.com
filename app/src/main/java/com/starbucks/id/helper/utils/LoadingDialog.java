package com.starbucks.id.helper.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.widget.TextView;

import com.starbucks.id.R;

public class LoadingDialog extends Dialog {

    Context context;
    TextView text;

    public LoadingDialog(Context context) {
        super(context, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar);
        setContentView(R.layout.loading_view);
        text = (TextView)findViewById(R.id.title_dialog) ;
        this.context = context;
        setCanceledOnTouchOutside(false);
        this.text.setText("");
        init();
    }

    private void init() {
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

    }

    public void setTextLoading(String textTitle) {
        this.text.setText(textTitle);
    }
}