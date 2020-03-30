package com.starbucks.id.controller.extension;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.ViewGroup;

public class ViewPortFace extends ViewGroup {

    public ViewPortFace(Context context) {
        super(context);
    }

    public ViewPortFace(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewPortFace(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
    }

    @Override
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        int viewportMargin = 80;
        int viewportCornerRadius = 8;
        Paint eraser = new Paint();
        eraser.setAntiAlias(true);
        eraser.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        float width = (float) getWidth() - viewportMargin;
        float height = (float) getHeight() - viewportMargin;
        RectF rect = new RectF((float)viewportMargin, (float)viewportMargin, width, height);
        RectF frame = new RectF((float)viewportMargin, (float)viewportMargin, width, height);
        Path path = new Path();
        Paint stroke = new Paint();
        stroke.setAntiAlias(true);
        stroke.setStrokeWidth(4);
        stroke.setColor(Color.WHITE);
        stroke.setStyle(Paint.Style.STROKE);
        path.addRoundRect(frame, (float) viewportCornerRadius, (float) viewportCornerRadius, Path.Direction.CW);
        canvas.drawPath(path, stroke);
        canvas.drawRoundRect(rect, (float) viewportCornerRadius, (float) viewportCornerRadius, eraser);
    }
}