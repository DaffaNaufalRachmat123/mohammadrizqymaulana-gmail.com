package com.starbucks.id.controller.fragment.home_fragment.profile_tabs.reward_fragment;

import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.starbucks.id.R;
import com.starbucks.id.controller.activity.MainActivity;
import com.starbucks.id.controller.extension.ViewPagerAdapter;
import com.starbucks.id.controller.fragment.TabsWebViewFragment;
import com.starbucks.id.helper.UserDefault;
import com.starbucks.id.rest.ApiClient;

/**
 * Created by Angga N P on 3/13/2018.
 */

public class FragmentRewardBenefit extends Fragment {
    private static final String TAG = Fragment.class.getSimpleName();
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private TabLayout tabLayout;
    private UserDefault userDefault;

    public FragmentRewardBenefit() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userDefault = ((MainActivity) getActivity()).getUserDefault();
//        ((MainActivity) getActivity()).enableNavigationIcon();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_pay, container, false);
        viewPager = rootView.findViewById(R.id.viewpager);
        tabLayout = rootView.findViewById(R.id.tabs);

        setView();

        return rootView;
    }

    private void setView() {
        ((MainActivity) getActivity()).setToolbarTitle(
                getString(R.string.reward_benefit_title));
        viewPager.setVisibility(View.VISIBLE);
        tabLayout.setVisibility(View.VISIBLE);

        tabLayout.setupWithViewPager(viewPager);

        if (viewPager.getAdapter() == null) setupViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getChildFragmentManager());

        adapter.addFragment(TabsWebViewFragment.newInstance(
                userDefault.IDLanguage() ? ApiClient.INSTANCE.getHTML_FILE() + "green_level_id.html" : ApiClient.INSTANCE.getHTML_FILE() + "green_level.html"), "Green Level");
        adapter.addFragment(TabsWebViewFragment.newInstance(
                userDefault.IDLanguage() ? ApiClient.INSTANCE.getHTML_FILE() + "gold_level_id.html" : ApiClient.INSTANCE.getHTML_FILE() + "gold_level.html"), "Gold Level");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        setView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
