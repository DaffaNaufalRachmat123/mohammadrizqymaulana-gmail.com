package com.starbucks.id.controller.activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import androidx.viewpager.widget.PagerAdapter;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.starbucks.id.R;
import com.starbucks.id.controller.activity.LoginActivity;
import com.starbucks.id.controller.activity.MainActivity;
import com.starbucks.id.helper.UserDefault;

import java.util.ArrayList;

/**
 * Created by Novian on 1/19/2016.
 */
public class TutorialsPagerAdapter extends PagerAdapter {
    private AppCompatActivity context;
    private ArrayList<Integer> imgUrls;
    private LayoutInflater inflater;
    private Boolean isFragment;
    private UserDefault userDefault;

    public TutorialsPagerAdapter(AppCompatActivity ctx, ArrayList<Integer> files, Boolean _isFragment) {
        this.imgUrls = files;
        this.context = ctx;
        this.isFragment = _isFragment;
        this.userDefault = UserDefault.getInstance(ctx);
    }

    @Override
    public int getCount() {
        return imgUrls.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView imgTutorial;
        Button btnGotIt;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.tutorial_pager_item, container, false);
        imgTutorial = itemView.findViewById(R.id.imgTutorial);
        btnGotIt = itemView.findViewById(R.id.btnGotIt);
        if (position != (imgUrls.size() - 1)) {
            btnGotIt.setVisibility(View.INVISIBLE);
        } else {
            btnGotIt.setVisibility(View.VISIBLE);
            btnGotIt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isFragment) {
                        context.onBackPressed();
                    } else {
                        if (userDefault.isLoggedIn()) {
                            Intent home = new Intent(TutorialsPagerAdapter.this.context, MainActivity.class);
                            context.startActivity(home);
                            context.finish();
                        } else {
                            Intent login = new Intent(TutorialsPagerAdapter.this.context, LoginActivity.class);
                            context.startActivity(login);
                            context.finish();
                        }
                    }
                }
            });
        }
        imgTutorial.setImageDrawable(context.getResources().getDrawable(imgUrls.get(position)));

        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        if (observer != null) {
            super.unregisterDataSetObserver(observer);
        }
    }

}
