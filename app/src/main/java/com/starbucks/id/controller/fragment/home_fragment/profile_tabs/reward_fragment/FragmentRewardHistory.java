package com.starbucks.id.controller.fragment.home_fragment.profile_tabs.reward_fragment;

/*
 * Created by Angga N P on 3/13/2018.
 */

import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.starbucks.id.R;
import com.starbucks.id.controller.activity.MainActivity;
import com.starbucks.id.controller.fragment.home_fragment.profile_tabs.reward_fragment.adapter.RewardHistoryAdapter;
import com.starbucks.id.helper.UserDefault;
import com.starbucks.id.model.reward_history.RewardResponseModel;
import com.starbucks.id.model.user.UserResponseModel;
import com.starbucks.id.rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentRewardHistory extends Fragment {

    private static final String TAG = Fragment.class.getSimpleName();
    private RecyclerView rv;
    private UserDefault userDefault;
    private ConstraintLayout CLEmpty;
    private ApiInterface apiService;
    private Call<RewardResponseModel> callReward;
    private Call<UserResponseModel> callUser;
    private RewardResponseModel rewardModel;
    private UserResponseModel userResponseModel;
    private TextView tvTitle, tvSub;

    private MainActivity activity;

    public FragmentRewardHistory() {
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
        View rootView = inflater.inflate(R.layout.fragment_home_reward_history, container, false);

        setHasOptionsMenu(true);

        rv = rootView.findViewById(R.id.rvHistory);
        CLEmpty = rootView.findViewById(R.id.CLEmpty);
        tvTitle = rootView.findViewById(R.id.detailDescription);
        tvSub = rootView.findViewById(R.id.tvSub);


        rv.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        setData();

        return rootView;
    }

    public void setData() {
        userResponseModel = activity.getUser();
        if (getActivity() != null) getTrx(userResponseModel
                .getUser().getUserProfile().getDefaultCard());

    }


    /*REST CALL*/
    private void getTrx(String card_number) {
        if (!activity.checkConnection()) return;
        if (apiService == null) apiService = activity.getApiService();

        callReward = apiService.getRewardHistory("get_activity_offers",
                activity.genSBUX("email=" + userDefault.getEmail()
                        + "&card_number=" + card_number));

        callReward.enqueue(new Callback<RewardResponseModel>() {
            @Override
            public void onResponse(Call<RewardResponseModel> call, Response<RewardResponseModel> response) {
                if (getActivity() != null) {
                    if (response.isSuccessful() && response.body() != null) {
                        rewardModel = response.body();
                        if (activity.successResponse(rewardModel.getStatus(), rewardModel.getProcessMsg())) {
                            setDefaultView();
                        }
                    } else {
                        activity.
                                checkServer(String.valueOf(response.code()), response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<RewardResponseModel> call, Throwable t) {
                if (getActivity() != null && isAdded())
                    activity.restFailure(t);
            }
        });
    }

    private void setDefaultView() {
        activity.setToolbarTitle(
                !userDefault.IDLanguage() ?
                        "Rewards History" : "Riwayat Reward");

        if (userDefault.IDLanguage()) {
            tvTitle.setText("Anda belum mempunyai riwayat reward");
            tvSub.setText("Saat anda mendapatkan reward, daftar riwayat anda akan keluar disini");
        }

        if (rewardModel.getResult().size() > 0) {
            RewardHistoryAdapter adapter = new RewardHistoryAdapter(rewardModel.getResult(), R.layout.rv_profile_activity_item);
            rv.setAdapter(adapter);
            rv.setVisibility(View.VISIBLE);
        } else CLEmpty.setVisibility(View.VISIBLE);
    }

    /*Override Func*/
    @Override
    public void onResume() {
        super.onResume();
    }

}
