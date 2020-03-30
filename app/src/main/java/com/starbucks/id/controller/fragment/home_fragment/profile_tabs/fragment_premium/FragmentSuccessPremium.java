package com.starbucks.id.controller.fragment.home_fragment.profile_tabs.fragment_premium;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;

import com.starbucks.id.R;
import com.starbucks.id.controller.activity.MainActivity;
import com.starbucks.id.helper.UserDefault;
import com.starbucks.id.rest.ApiClient;

public class FragmentSuccessPremium extends Fragment {
    private static MainActivity activity;
    private UserDefault userDefault;
    private WebView webTNC;
    public FragmentSuccessPremium() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = ((MainActivity)getActivity());
        if (activity != null) userDefault = activity.getUserDefault();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_success_premium, container, false);
        webTNC = rootView.findViewById(R.id.webPremium);
        setData();
        return rootView;
    }

    private void setData(){
        activity.setToolbarTitle("Starbucks Premium");
        String url = !userDefault.IDLanguage()
                ? ApiClient.INSTANCE.getHTML_FILE() + "sbux-premium.html"
                : ApiClient.INSTANCE.getHTML_FILE() + "sbux-premium-id.html";

        webTNC.getSettings().setJavaScriptEnabled(true);
        webTNC.getSettings().setLoadWithOverviewMode(true);
        webTNC.getSettings().setUseWideViewPort(true);
        webTNC.loadUrl(url);
        webTNC.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                webTNC.setVisibility(View.VISIBLE);
                activity.tabLayout.setVisibility(View.GONE);
            }
        });
    }
}
