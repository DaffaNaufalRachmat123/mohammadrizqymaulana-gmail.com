package com.starbucks.id.controller.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.starbucks.id.R;
import com.starbucks.id.controller.activity.MainActivity;

public class GeneralWebViewFragment extends Fragment {

    static String Url;
    static String HeaderName;
    WebView webView;

    public GeneralWebViewFragment() {
        // Required empty public constructor
    }

    public static GeneralWebViewFragment newInstance(String url, String headerName) {
        GeneralWebViewFragment fragment = new GeneralWebViewFragment();
        Url = url;
        HeaderName = headerName;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity) getActivity()).setToolbarTitle(HeaderName);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_general_web_view, container, false);
        webView = view.findViewById(R.id.webView);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.loadUrl(Url);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                webView.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }
}
