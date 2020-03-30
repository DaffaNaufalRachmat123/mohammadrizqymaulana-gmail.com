package com.starbucks.id.controller.fragment.menu_fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.starbucks.id.R;
import com.starbucks.id.controller.activity.MainActivity;
import com.starbucks.id.controller.fragment.menu_fragment.adapter.SubMenuGvAdapter;
import com.starbucks.id.model.menu.MenuSubModel;

import java.util.List;

public class MenuSubFragment extends Fragment {
    static List<MenuSubModel> SubMenu;
    static String name;
    GridView gvMenuSub;

    public MenuSubFragment() {
        // Required empty public constructor
    }

    public static MenuSubFragment newInstance(List<MenuSubModel> subMenu, String HeaderName) {
        MenuSubFragment fragment = new MenuSubFragment();
        SubMenu = subMenu;
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
        View view = inflater.inflate(R.layout.fragment_menu_sub, container, false);

        gvMenuSub = view.findViewById(R.id.gvMenuSub);
        gvMenuSub.setAdapter(new SubMenuGvAdapter(getActivity(), SubMenu));
        setBaseView();
        return view;
    }

    private void setBaseView() {
//        ((MainActivity) getActivity()).enableNavigationIcon();
        ((MainActivity) getActivity()).setToolbarTitle(name);
    }

}
