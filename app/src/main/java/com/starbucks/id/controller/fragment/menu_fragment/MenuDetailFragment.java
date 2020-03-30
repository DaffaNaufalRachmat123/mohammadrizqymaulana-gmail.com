package com.starbucks.id.controller.fragment.menu_fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.starbucks.id.R;
import com.starbucks.id.controller.activity.MainActivity;
import com.starbucks.id.controller.fragment.menu_fragment.adapter.MenuGvAdapter;
import com.starbucks.id.model.menu.MenuDetailModel;

import java.util.List;

public class MenuDetailFragment extends Fragment {
    private static final String TAG = Fragment.class.getSimpleName();
    static List<MenuDetailModel> MenuDetail;
    static String name;
    GridView gvMenuDetail;

    public MenuDetailFragment() {
    }

    public static MenuDetailFragment newInstance(List<MenuDetailModel> Detail, String HeaderName) {
        MenuDetailFragment fragment = new MenuDetailFragment();
        MenuDetail = Detail;
        name = HeaderName;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu_detail, container, false);

        gvMenuDetail = view.findViewById(R.id.gvMenuDetail);
        gvMenuDetail.setAdapter(new MenuGvAdapter(getActivity(), MenuDetail));

        setBaseView();

        return view;
    }

    private void setBaseView() {
//        ((MainActivity) getActivity()).enableNavigationIcon();
        ((MainActivity) getActivity()).setToolbarTitle(name);
    }

}
