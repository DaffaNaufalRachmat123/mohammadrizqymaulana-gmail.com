package com.starbucks.id.controller.fragment.home_fragment.inbox_tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.starbucks.id.R;
import com.starbucks.id.controller.activity.MainActivity;
import com.starbucks.id.controller.fragment.home_fragment.inbox_tabs.inbox_adapter.OfferAdapter;
import com.starbucks.id.controller.fragment.home_fragment.profile_tabs.FragmentProfile;
import com.starbucks.id.helper.UserDefault;
import com.starbucks.id.helper.utils.DataUtil;
import com.starbucks.id.model.inbox.MessageModel;
import com.starbucks.id.model.inbox.ResponseOfferModel;
import com.starbucks.id.model.user.UserResponseModel;
import com.starbucks.id.rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentOffer extends Fragment {

    private RecyclerView rv;
    private SwipeRefreshLayout sw;
    private TextView tvEmpty;
    private ProgressBar pbLoading;

    private ResponseOfferModel offerList;

    private UserDefault userDefault;
    private OfferAdapter adapter;
    private UserResponseModel userResponseModel;
    private ApiInterface apiService;
    private int retry_count = 3;
    private static MessageModel msg;
    private MainActivity activity;


    public FragmentOffer() {
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
        View rootview = inflater.inflate(R.layout.fragment_message, container, false);

        rv = rootview.findViewById(R.id.rv);
        sw = rootview.findViewById(R.id.SWRoot);
        tvEmpty = rootview.findViewById(R.id.tvEmpty);
        pbLoading = rootview.findViewById(R.id.pbLoading);

//        if (msg != null) {
//            Fragment newFragment = FragmentMessageDetail.newInstance(msg);
//            activity.cFragmentWithBS(newFragment);
//            msg = null;
//        }else setData();
        if (getActivity() != null) setData();
        return rootview;
    }
    public void setData() {
        userResponseModel = activity.getUser();
        if(userResponseModel.getUser()!=null){
            if (activity.getUser()!= null) {
                getListOfferData();
            }
        }else{
            activity.getUserData();
        }
//        if (activity.getUser() != null) {
//            userResponseModel = activity.getUser();
//            if (offerList == null) getListOfferData();
//            else setBaseView();
//        } else activity.getUserData();
    }

    private void setBaseView() {
        activity.setToolbarTitle("Inbox");

        sw.setRefreshing(false);
        pbLoading.setVisibility(View.GONE);
            if (offerList.getCampaignList().size() > 0) {
                rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                adapter = new OfferAdapter(offerList.getCampaignList(), R.layout.item_offers, getActivity());

                rv.setAdapter(adapter);
                adapter.setClickListener(new OfferAdapter.ItemClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        activity.setFragmentOfferDetailId(offerList.getCampaignList().get(position).getCampaign_id_mcs());
                        activity.cFragmentWithBS(new FragmentOfferDetail());


//                        Toast.makeText(getActivity(),offerList.getCampaignList().get(position).getCampaign_id_mcs(),Toast.LENGTH_LONG).show();
                    }
                });

                rv.setVisibility(View.VISIBLE);
            } else{
                tvEmpty.setText(offerList.getProcessMsg());
                tvEmpty.setVisibility(View.VISIBLE);
            }


        sw.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                rv.setVisibility(View.INVISIBLE);
                getListOfferData();
                sw.setRefreshing(false);
            }
        });
    }

    /*REST CALL*/
    // Get Offer List Data
    public void getListOfferData() {
        if (!activity.checkConnection()) return;
        if (apiService == null) apiService = activity.getApiService();

        pbLoading.setVisibility(View.VISIBLE);
        tvEmpty.setVisibility(View.GONE);
        rv.setVisibility(View.GONE);

        Call<ResponseOfferModel> call = apiService.getOfferList(  "campaign_list",
                activity.genSBUX( "email=" + userResponseModel.getUser().getEmail()+
                        "&card_number="+userResponseModel.getUser().getExternalId()+
                        "&lang=" + userDefault.getLanguage()+
                        "&sm_token=" + userDefault.getAccToken()));

        call.enqueue(new Callback<ResponseOfferModel>() {
            @Override
            public void onResponse(Call<ResponseOfferModel> call, Response<ResponseOfferModel> response) {
                if (getActivity() != null) {
                    if (response.isSuccessful() && response.body() != null) {
                        offerList = response.body();
                        if(offerList.getCampaignList()!=null){
                            setBaseView();
                        }else{

                        }
//                        if (activity.successResponse(offerList.getStatus(),offerList.getProcessMsg())) {

//                        }
                    } else {
                        activity.checkServer(String.valueOf(response.code()), response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseOfferModel> call, Throwable t) {
                if (getActivity() != null && isAdded())
                    activity.restFailure(t);
            }
        });

    }
}