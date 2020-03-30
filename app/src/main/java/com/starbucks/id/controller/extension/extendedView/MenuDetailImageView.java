package com.starbucks.id.controller.extension.extendedView;

import android.content.Context;
import androidx.appcompat.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * Created by Novian on 1/15/2016.
 */
public class MenuDetailImageView extends AppCompatImageView {

    public MenuDetailImageView(Context context) {
        super(context);
    }

    public MenuDetailImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MenuDetailImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth()); //Snap to width
    }

}
