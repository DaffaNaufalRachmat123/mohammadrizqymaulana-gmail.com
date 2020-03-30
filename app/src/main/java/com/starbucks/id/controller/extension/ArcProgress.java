package com.starbucks.id.controller.extension;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.starbucks.id.R;
import com.starbucks.id.helper.utils.DataUtil;

/**
 * Created by Angga N P on 12/19/2018.
 */

public class ArcProgress extends View {
    private Paint paint, bgPaint;
    protected Paint textPaint;

    private RectF rectF = new RectF();

    private float strokeWidth = 16;
    private float bgStrokeWidth = 20;

    private float bottomTextSize = 20;
    private String bottomText;
    private int bottomTextColor;

    private float progress = 0;
    private int max;

    private int fgColor, bgColor;

    private float arcAngle, arcBottomHeight;

    public ArcProgress(Context context) {
        this(context, null);
    }

    public ArcProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ArcProgress, defStyleAttr, 0);
        initByAttributes(attributes);
        attributes.recycle();

        initPainters();
    }

    protected void initByAttributes(TypedArray attributes) {
        fgColor = attributes.getColor(R.styleable.ArcProgress_arc_finished_color, getResources().getColor(R.color.greenAccent));
        bgColor = attributes.getColor(R.styleable.ArcProgress_arc_unfinished_color, getResources().getColor(R.color.gray_accent));
        bottomTextColor = attributes.getColor(R.styleable.ArcProgress_arc_text_color, getResources().getColor(R.color.gray_dop));
        bottomTextSize = attributes.getDimension(R.styleable.ArcProgress_arc_text_size, DataUtil.dp2px(getResources(), 20));
        setMax(attributes.getInt(R.styleable.ArcProgress_arc_max, 100));
        setProgress(attributes.getFloat(R.styleable.ArcProgress_arc_progress, 0));

        arcAngle = 360;
        strokeWidth = DataUtil.dp2px(getResources(), strokeWidth);
        bgStrokeWidth = DataUtil.dp2px(getResources(), bgStrokeWidth);
    }

    protected void initPainters() {
        textPaint = new TextPaint();
        textPaint.setColor(bottomTextColor);
        textPaint.setTextSize(bottomTextSize);
        textPaint.setAntiAlias(true);

        //FG
        paint = new Paint();
        paint.setColor(fgColor);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(strokeWidth);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);

        //BG
        bgPaint = new Paint();
        bgPaint.setColor(bgColor);
        bgPaint.setAntiAlias(true);
        bgPaint.setStrokeWidth(bgStrokeWidth);
        bgPaint.setStyle(Paint.Style.STROKE);
        bgPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    public void invalidate() {
        initPainters();
        super.invalidate();
    }

    public float getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
        this.invalidate();
    }

    public String getBottomText() {
        return bottomText;
    }

    public void setBottomText(String bottomText) {
        this.bottomText = bottomText;
        this.invalidate();
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;

        if (this.progress > getMax()) {
            this.progress %= getMax();
        }
        invalidate();
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        if (max > 0) {
            this.max = max;
            invalidate();
        }
    }

    public float getBottomTextSize() {
        return bottomTextSize;
    }

    public void setBottomTextSize(float bottomTextSize) {
        this.bottomTextSize = bottomTextSize;
        this.invalidate();
    }

    public int getFgColor() {
        return fgColor;
    }

    public void setFgColor(int fgColor) {
        this.fgColor = fgColor;
        this.invalidate();
    }

    public int getBgColor() {
        return bgColor;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
        this.invalidate();
    }

    public float getArcAngle() {
        return arcAngle;
    }

    public void setArcAngle(float arcAngle) {
        this.arcAngle = arcAngle;
        this.invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        rectF.set(bgStrokeWidth / 2f, bgStrokeWidth / 2f, width - bgStrokeWidth / 2f,
                MeasureSpec.getSize(heightMeasureSpec) - bgStrokeWidth / 2f);
        float radius = width / 2f;
        float angle = (360 - arcAngle) / 2f;
        arcBottomHeight = radius * (float) (1 - Math.cos(angle / 180 * Math.PI));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float startAngle = 270 - arcAngle / 2f;
        float finishedSweepAngle = progress / (float) getMax() * arcAngle;
        float finishedStartAngle = startAngle;
        if(progress == 0) finishedStartAngle = 0.01f;


        // Draw PB BG
        canvas.drawArc(rectF, startAngle, arcAngle, false, bgPaint);
        // Draw PB FG
        canvas.drawArc(rectF, finishedStartAngle, finishedSweepAngle, false, paint);


        if(arcBottomHeight == 0) {
            float radius = getWidth() / 2f;
            float angle = (360 - arcAngle) / 2f;
            arcBottomHeight = radius * (float) (1 - Math.cos(angle / 180 * Math.PI));
        }

        //Draw Bottom Text
        if (!TextUtils.isEmpty(bottomText)) {
            textPaint.setColor(bottomTextColor);
            textPaint.setTextSize(bottomTextSize);
            float textBaseline = getHeight() - arcBottomHeight - (textPaint.descent() + textPaint.ascent()) / 2;
            canvas.drawText(getBottomText(), (getWidth() - textPaint.measureText(getBottomText())) / 2.0f, textBaseline, textPaint);

        }
    }
}