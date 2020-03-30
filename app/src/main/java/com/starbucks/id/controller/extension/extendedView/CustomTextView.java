package com.starbucks.id.controller.extension.extendedView;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ScaleXSpan;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class CustomTextView extends androidx.appcompat.widget.AppCompatTextView {

    public static final String ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android";

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context, attrs);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context, attrs);
    }

    private void applyCustomFont(Context context, AttributeSet attrs) {
        int textStyle = attrs.getAttributeIntValue(ANDROID_SCHEMA, "textStyle", Typeface.NORMAL);

        Typeface customFont = selectTypeface(context, textStyle);
        setTypeface(customFont);
    }

    private Typeface selectTypeface(Context context, int textStyle) {
        /*
         * information about the TextView textStyle:
         * http://developer.android.com/reference/android/R.styleable.html#TextView_textStyle
         */
        switch (textStyle) {
            case Typeface.BOLD: // bold
                return FontCache.getTypeface(context,"sodosans_narrow_bold.otf");

            case Typeface.ITALIC: // italic
                return FontCache.getTypeface(context,"sodosans_narrow_italic.otf");

            case Typeface.NORMAL: // regular
            default:
                return FontCache.getTypeface(context,"sodosans_narrow_regular.otf");
        }
    }
}
