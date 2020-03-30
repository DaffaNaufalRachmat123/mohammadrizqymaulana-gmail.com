package com.starbucks.id.controller.extension.extendedView;

import android.content.Context;
import android.graphics.Color;
import androidx.appcompat.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;

import java.util.Random;

/**
 * Created by Angga N P on 6/9/2016.
 *
 */
public class SingleEditText extends AppCompatEditText {
    private Random r = new Random();

    public SingleEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SingleEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SingleEditText(Context context) {
        super(context);
    }

    public void setRandomBackgroundColor() {
        setBackgroundColor(Color.rgb(r.nextInt(256), r.nextInt(256), r
                .nextInt(256)));
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        return new ZanyInputConnection(super.onCreateInputConnection(outAttrs),
                true);
    }

    private class ZanyInputConnection extends InputConnectionWrapper {

        public ZanyInputConnection(InputConnection target, boolean mutable) {
            super(target, mutable);
        }

        @Override
        public boolean sendKeyEvent(KeyEvent event) {
            if (event.getAction() == KeyEvent.ACTION_DOWN
                    && event.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                //ZanyEditText.this.setRandomBackgroundColor();
                // Un-comment if you wish to cancel the backspace:
                // return false;
            }
            return super.sendKeyEvent(event);
        }

    }

}
