package com.starbucks.id.controller.fragment.home_fragment;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.starbucks.id.R;
import com.starbucks.id.helper.UserDefault;


/**
 * Created by Angga N P on 6/11/2018.
 */

public class FragmentPromo extends Fragment {

    private UserDefault userDefault;

    private String notif, bonus;


    public FragmentPromo newInstance(String not, String bon) {
        FragmentPromo fragment = new FragmentPromo();
        notif = not;
        bonus = bon;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userDefault = UserDefault.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.dialog_notif_pc, container, false);
        setHasOptionsMenu(true);

        TextView tvNotif = rootView.findViewById(R.id.tvNotif);
        TextView tvBonus = rootView.findViewById(R.id.tvBonus);
        ImageView ibClose = rootView.findViewById(R.id.ibClose);

        tvNotif.setText(notif);
        tvBonus.setText(bonus);

        ibClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        return rootView;
    }
}
