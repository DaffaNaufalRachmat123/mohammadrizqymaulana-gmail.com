package com.starbucks.id.controller.fragment.home_fragment.inbox_tabs;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.starbucks.id.R;
import com.starbucks.id.controller.activity.MainActivity;
import com.starbucks.id.controller.extension.ViewPagerAdapter;
import com.starbucks.id.helper.UserDefault;
import com.starbucks.id.model.inbox.ResponseMessageList;
import com.starbucks.id.model.user.UserResponseModel;

import java.util.Objects;

public class FragmentInbox extends Fragment {
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private TabLayout tabLayout;
    private UserDefault userDefault;
    Bundle args;
    int operation_id;
    public FragmentInbox (){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userDefault = ((MainActivity) getActivity()).getUserDefault();
        Bundle args = getArguments();
        if (args != null) operation_id = args.getInt("operation_id", 1);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_inbox, container, false);

        viewPager = rootView.findViewById(R.id.viewpager);
        tabLayout = rootView.findViewById(R.id.tabs);

        setData();
        return rootView;
    }

    public void setData() {

        if (getActivity() != null) {
            if (((MainActivity) getActivity()).userResponseModel != null) {
                viewPager.setVisibility(View.INVISIBLE);
                tabLayout.setVisibility(View.INVISIBLE);
                setupViewPager();
                ((MainActivity) getActivity()).setToolbarTitle(getString(R.string.f_Inbox_t));
            } else {
                ((MainActivity) getActivity()).getUserData();
            }
        }
    }

    public void setupViewPager() {
        adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new FragmentMessage(), !userDefault.IDLanguage() ? "Message" : "Pesan");
        adapter.addFragment(new FragmentOffer(), !userDefault.IDLanguage() ? "Program Tracker" : "Program Tracker");
        viewPager.setAdapter(adapter);
        if(operation_id == 2){
            viewPager.setCurrentItem(1);
        }else{
            viewPager.setCurrentItem(0);
        }
        setView();
    }

    private void setView() {
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View focus = Objects.requireNonNull(getActivity()).getCurrentFocus();
                if (focus != null) hiddenKeyboard(focus);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View focus = Objects.requireNonNull(getActivity()).getCurrentFocus();
                if (focus != null) hiddenKeyboard(focus);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                View focus = Objects.requireNonNull(getActivity()).getCurrentFocus();
                if (focus != null) hiddenKeyboard(focus);
            }
        });

        viewPager.setVisibility(View.VISIBLE);
        tabLayout.setVisibility(View.VISIBLE);
    }

    private void hiddenKeyboard(View v) {
        InputMethodManager keyboard = (InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
        if (keyboard != null) keyboard.hideSoftInputFromWindow(v.getWindowToken(), 0);

    }

}
