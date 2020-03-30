package com.starbucks.id.controller.fragment.menu_fragment;

import android.graphics.Rect;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.squareup.picasso.Picasso;
import com.starbucks.id.R;
import com.starbucks.id.controller.activity.MainActivity;
import com.starbucks.id.controller.fragment.menu_fragment.adapter.MenuContentListAdapter;

public class MenuContentFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    static String ImageUrl;
    static String Title;
    static String Content;
    ListView lstMenuContent;
    private String mParam1;
    private String mParam2;
    private ImageView backgroundImage;
    private ArrayAdapter adapter;
    private int lastTopValue = 0;
    private Picasso picasso;


    public MenuContentFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MenuContentFragment newInstance(String imageUrl, String title, String content) {
        MenuContentFragment fragment = new MenuContentFragment();
        ImageUrl = imageUrl;
        Title = title;
        Content = content;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        picasso = ((MainActivity) getActivity()).getPicasso();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_menu_content, container, false);
        lstMenuContent = view.findViewById(R.id.lstMenuContent);
        adapter = new MenuContentListAdapter(getActivity(), Title, Content);

        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.menu_content_header_detail, lstMenuContent, false);
        lstMenuContent.addHeaderView(header, null, false);
        lstMenuContent.setAdapter(adapter);
        backgroundImage = header.findViewById(R.id.imgMenuContentHeaderImage);

        picasso.load(ImageUrl)
                .into(backgroundImage, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        backgroundImage.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError() {
                        picasso.load(R.drawable.defaultpage_720_h).into(backgroundImage);
                        backgroundImage.setVisibility(View.VISIBLE);

                    }
                });

        lstMenuContent.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Rect rect = new Rect();
                backgroundImage.getLocalVisibleRect(rect);
                if (lastTopValue != rect.top) {
                    lastTopValue = rect.top;
                    backgroundImage.setY((float) (rect.top / 2.0));
                }
            }
        });

        setBaseView();

        return view;
    }

    private void setBaseView() {
//        ((MainActivity) getActivity()).enableNavigationIcon();
        ((MainActivity) getActivity()).setToolbarTitle(Title);
    }

}
