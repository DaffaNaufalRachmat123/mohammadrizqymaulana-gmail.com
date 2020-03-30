package com.starbucks.id.controller.fragment.pay_fragment;

import android.content.Context;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.starbucks.id.R;
import com.starbucks.id.controller.activity.MainActivity;
import com.starbucks.id.controller.extension.ViewPagerAdapter;
import com.starbucks.id.controller.fragment.pay_fragment.tabs.FragmentAddTab;
import com.starbucks.id.controller.fragment.pay_fragment.tabs.FragmentPayTab;
import com.starbucks.id.controller.fragment.pay_fragment.tabs.FragmentReloadHistory;
import com.starbucks.id.controller.fragment.pay_fragment.tabs.FragmentReportTab;
import com.starbucks.id.controller.fragment.pay_fragment.tabs.FragmentTransferTab;
import com.starbucks.id.helper.UserDefault;

import java.util.Objects;

/**
 * Created by Angga N P on 3/13/2018.
 */

public class FragmentPay extends Fragment {
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private TabLayout tabLayout;
    private UserDefault userDefault;
    private FirebaseAnalytics mFirebaseAnalytics;
    public FragmentPay() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userDefault = ((MainActivity) getActivity()).getUserDefault();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_pay, container, false);

        viewPager = rootView.findViewById(R.id.viewpager);
        tabLayout = rootView.findViewById(R.id.tabs);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());
        Bundle params = new Bundle();
        params.putString("FragmentPay", "Fragment Pay");
        mFirebaseAnalytics.logEvent("AOS", params);
        setData();

        return rootView;
    }

    public void setData() {
        if (getActivity() != null) {
            if (((MainActivity) getActivity()).userResponseModel != null) {
//                viewPager.setVisibility(View.INVISIBLE);
//                tabLayout.setVisibility(View.INVISIBLE);
                setupViewPager();
                ((MainActivity) getActivity()).setToolbarTitle(getString(R.string.f_pay_t));
            } else {
                ((MainActivity) getActivity()).getUserData();
            }
        }
    }

    private void setupViewPager() {
        adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new FragmentPayTab(), !userDefault.IDLanguage() ? "Pay" : "Bayar");
        adapter.addFragment(new FragmentAddTab(), !userDefault.IDLanguage() ? "Add" : "Tambah");
//        adapter.addFragment(new FragmentTransferTab(), "Transfer");
        adapter.addFragment(new FragmentReloadHistory(), !userDefault.IDLanguage() ? "Virtual Account" : "Akun Virtual");
        adapter.addFragment(new FragmentReportTab(), !userDefault.IDLanguage() ? "Report Lost Card" : "Laporan Kartu Hilang");
        viewPager.setAdapter(adapter);
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
