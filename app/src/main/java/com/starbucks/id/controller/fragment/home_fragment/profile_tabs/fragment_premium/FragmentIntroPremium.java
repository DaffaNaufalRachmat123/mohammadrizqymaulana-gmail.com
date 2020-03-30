package com.starbucks.id.controller.fragment.home_fragment.profile_tabs.fragment_premium;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.starbucks.id.R;
import com.starbucks.id.controller.activity.MainActivity;
import com.starbucks.id.controller.fragment.home_fragment.profile_tabs.FragmentPersonal;
import com.starbucks.id.helper.ConnectionDetector;
import com.starbucks.id.helper.StarbucksID;
import com.starbucks.id.helper.UserDefault;
import com.starbucks.id.helper.utils.DialogUtil;
import com.starbucks.id.model.ResponseBase;
import com.starbucks.id.model.user.UserResponseModel;
import com.starbucks.id.rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentIntroPremium extends Fragment {
    private TextView btStart;
    private MainActivity activity;
    private UserDefault userDefault;
    TextView tvHeader,tvStatus,tvStart;
    StarbucksID app;
    private UserResponseModel userResponseModel;
    public FragmentIntroPremium() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = ((MainActivity)getActivity());
        if (activity != null) userDefault = activity.getUserDefault();
        app = StarbucksID.Companion.getInstance();
        userResponseModel = activity.getUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_step_intro_premium, container, false);
        btStart = rootView.findViewById(R.id.btnStart);
        tvHeader = rootView.findViewById(R.id.tvHeader);
        tvStatus = rootView.findViewById(R.id.tvStatus);
        tvStart = rootView.findViewById(R.id.tvStart);
        activity.tabLayout.setVisibility(View.GONE);

        setData();
        return rootView;
    }

    public void setData(){
        getIntroPremium();
        backButton();

        activity.setToolbarTitle("Starbucks Premium");
        tvStart.setText(userDefault.IDLanguage()?"SIAPKAN KARTU IDENTITAS ANDA":"PREPARE YOUR ID CARD");

        btStart.setText(userDefault.IDLanguage()?"MULAI":"START");
        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.cFragmentPremium(new FragmentStep1Premium());
            }
        });
    }
    public void backButton() {
        activity.toolbar.setNavigationIcon(R.drawable.ic_action_back);
        activity.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.cFragmentPremium(new FragmentPersonal());
            }});
    }

    public boolean checkConnection() {
        if (!ConnectionDetector.isConnected()) {
            DialogUtil.sErrDialog(getActivity(), userDefault, 1, new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    getIntroPremium();
                }
            });
        }
        return ConnectionDetector.isConnected();
    }


    private ApiInterface apiService;


    public ApiInterface getApiService() {
        if (apiService == null) apiService = app.getApiService();
        return apiService;
    }

    /*REST Call*/
    public void getIntroPremium() {
        if (!checkConnection()) return;
        if (apiService == null) apiService = app.getApiService();


        Call<ResponseBase> call = apiService.getIntroPremium(
                "sbx_premium_description",
                activity.genSBUX(
                        "email=" + userResponseModel.getUser().getEmail()+
                                "&lang=" + userDefault.getLanguage()));
        call.enqueue(new Callback<ResponseBase>() {
            @Override
            public void onResponse(Call<ResponseBase> call, Response<ResponseBase> response) {
                if (getActivity() != null) {
                    if (activity.successResponse(response.body().getReturnCode(), response.body().getProcessMsg())) {
                        tvHeader.setText(response.body().getData().getLanding_title());
                        tvStatus.setText(response.body().getData().getDetail_content());
                    }else{
                        activity.checkServer(String.valueOf(response.code()), response.message());
                    }
                }


            }

            @Override
            public void onFailure(Call<ResponseBase> call, Throwable t) {
                if (getActivity() != null && isAdded())
                    activity.restFailure(t);
            }
        });

    }

}
