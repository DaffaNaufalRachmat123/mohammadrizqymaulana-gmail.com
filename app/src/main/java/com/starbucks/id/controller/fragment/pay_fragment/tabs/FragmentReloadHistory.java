package com.starbucks.id.controller.fragment.pay_fragment.tabs;

/*
 * Created by Angga N P on 3/13/2018.
 */

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.starbucks.id.R;
import com.starbucks.id.controller.activity.MainActivity;
import com.starbucks.id.controller.fragment.pay_fragment.pay_adapter.ReloadHistoryAdapter;
import com.starbucks.id.helper.UserDefault;
import com.starbucks.id.model.pay.CardReloadHistoryResponse;
import com.starbucks.id.model.user.UserResponseModel;
import com.starbucks.id.rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentReloadHistory extends Fragment  {

    private RecyclerView rv;

    private TextView tvEmpty;
    private ProgressBar pb;

    private UserDefault userDefault;

    private CardReloadHistoryResponse data;

    private MainActivity activity;
    private UserResponseModel userResponseModel;
    public FragmentReloadHistory() {
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
        View rootView = inflater.inflate(R.layout.fragment_pay_reload_history, container, false);

        rv = rootView.findViewById(R.id.rv);
        tvEmpty = rootView.findViewById(R.id.tvEmpty);
        pb = rootView.findViewById(R.id.pb);

        setData();

        return rootView;
    }

//    public void setData() {
//        getTrx();
//    }


    /*UI Controller*/
    public void setData() {
        userResponseModel = activity.getUser();
        if(userResponseModel.getUser()!=null){
            if (activity.getUser()!= null) {
                getTrx();
            }
        }else{
            activity.getUserData();
        }
    }

    private void setBaseView() {
        rv.setVisibility(View.VISIBLE);
        rv.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        ReloadHistoryAdapter adapter = new ReloadHistoryAdapter(data.getHistory(), R.layout.item_pay_reload_history, getContext());
        rv.setAdapter(adapter);
    }

    private void setEmptyView() {
        pb.setVisibility(View.GONE);
//        if (userDefault.IDLanguage()) tvEmpty.setText(activity.getString(R.string.id_empty_va));
        tvEmpty.setText(userDefault.IDLanguage() ? "Silakan klik \"Isi ulang\" Pada halaman Pay untuk melakukan Transksi pertama Anda." : "Please click \"Top Up\" in the Pay Tab to create your first Virtual Account.");
        tvEmpty.setVisibility(View.VISIBLE);
    }


    /*REST Call*/
    private void getTrx() {
        if (!activity.checkConnection()) return;
        ApiInterface apiService = activity.getApiService();

        Call<CardReloadHistoryResponse> callTrx = apiService.getRH("historyVA",
                activity.genSBUX(
                        "uid=" + userDefault.getEmail()));

        callTrx.enqueue(new Callback<CardReloadHistoryResponse>() {
            @Override
            public void onResponse(Call<CardReloadHistoryResponse> call, Response<CardReloadHistoryResponse> response) {
                if (getActivity() != null) {
                    if (response.isSuccessful() && response.body() != null) {
                        data = response.body();
                        if (activity.successResponse(data.getReturnCode(), data.getProcessMsg())) {
                            if (data.getHistory().size()>0) setBaseView();
                            else setEmptyView();
                        }
                    } else {
                        activity.
                                checkServer(String.valueOf(response.code()), response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<CardReloadHistoryResponse> call, Throwable t) {
                if (getActivity() != null && isAdded())
                    activity.restFailure(t);
            }
        });
    }
}
