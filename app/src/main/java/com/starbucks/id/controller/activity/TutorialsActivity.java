package com.starbucks.id.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.starbucks.id.R;
import com.starbucks.id.controller.activity.adapter.TutorialsPagerAdapter;
import com.starbucks.id.helper.UserDefault;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

/**
 * Created by Angga N P on 5/19/2017.
 */
public class TutorialsActivity extends AppCompatActivity {
    private ViewPager TutorialPager;
    private CirclePageIndicator TutorialPagerIndicator;
    private UserDefault userDefault;
    private int operation_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if (intent != null) operation_id = intent.getExtras().getInt("operation_id");

        setContentView(R.layout.activity_tutorials);

        ArrayList<Integer> arrImages = new ArrayList<>();

        userDefault = UserDefault.getInstance(getApplicationContext());
        userDefault.setFirst_launch(false);

        arrImages.add(R.drawable.tutorial1);
        arrImages.add(R.drawable.tutorial2);
        arrImages.add(R.drawable.tutorial3);
        arrImages.add(R.drawable.tutorial4);

        TutorialPager = findViewById(R.id.TutorialPager);
        TutorialPagerIndicator = findViewById(R.id.TutorialPagerIndicator);

        if (operation_id == 0) {
            TutorialPager.setAdapter(new TutorialsPagerAdapter(this, arrImages, false));
        } else {
            TutorialPager.setAdapter(new TutorialsPagerAdapter(this, arrImages, true));
        }

        TutorialPagerIndicator.setViewPager(TutorialPager);
    }
}
