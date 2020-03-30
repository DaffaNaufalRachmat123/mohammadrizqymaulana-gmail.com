package com.starbucks.id.controller.fragment;


import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.starbucks.id.R;
import com.starbucks.id.controller.activity.MainActivity;
import com.starbucks.id.helper.UserDefault;


/**
 * Created by Angga N P on 6/11/2018.
 */

public class ErrorHandlerFragment extends Fragment {

    private UserDefault userDefault;
    private TextView tvCon, tvMt;
    private Button btRetry, btExit;
    private ConstraintLayout CLMt, CLCon;
    private int operation_id = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userDefault = UserDefault.getInstance(getActivity());

        Bundle args = getArguments();
        if (args != null) operation_id = args.getInt("operation_id", 0);

        if(getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).setToolbarTitle("");
            ((MainActivity) getActivity()).disableNavigationIcon();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_error, container, false);

        CLMt = rootView.findViewById(R.id.CLMt);
        CLCon = rootView.findViewById(R.id.CLCon);
        tvCon = rootView.findViewById(R.id.tvCon);
        tvMt = rootView.findViewById(R.id.tvMt);
        btRetry = rootView.findViewById(R.id.btRetry);
        btExit = rootView.findViewById(R.id.btExit);

        setView();

        return rootView;
    }


    private void setView() {
        if (userDefault.IDLanguage()) {
            tvCon.setText(getResources().getString(R.string.it_notif_id));
            tvMt.setText(getResources().getString(R.string.mt_notif_id));
        }

        if (operation_id == 0) {
            CLMt.setVisibility(View.VISIBLE);
            CLCon.setVisibility(View.GONE);
        } else {
            CLMt.setVisibility(View.GONE);
            CLCon.setVisibility(View.VISIBLE);
        }

        btRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        btExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
    }

}
