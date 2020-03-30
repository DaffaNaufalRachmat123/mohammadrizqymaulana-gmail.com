package com.starbucks.id.controller.extension.extendedView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;

/**
 * Created by Novian on 1/12/2016.
 */
public class StoreMapView extends MapView {

    public StoreMapView(Context context) {
        super(context);
    }

    public StoreMapView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StoreMapView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public StoreMapView(Context context, GoogleMapOptions options) {
        super(context, options);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                this.getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                this.getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
