package com.starbucks.id.controller.fragment.home_fragment.inbox_tabs;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.starbucks.id.R;
import com.starbucks.id.controller.activity.MainActivity;
import com.starbucks.id.controller.fragment.home_fragment.inbox_tabs.inbox_adapter.MessageAdapter;
import com.starbucks.id.helper.UserDefault;
import com.starbucks.id.model.inbox.MessageModel;
import com.starbucks.id.model.inbox.ResponseMessageList;
import com.starbucks.id.model.user.UserResponseModel;
import com.starbucks.id.rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Angga on 23/1/2019.
 */


public class FragmentMessage extends Fragment {

    private RecyclerView rv;
    private SwipeRefreshLayout sw;
    private TextView tvEmpty;
    private ProgressBar pbLoading;
    private ResponseMessageList messageList;
    private UserDefault userDefault;
    private MessageAdapter adapter;
    private UserResponseModel userResponseModel;
    private ApiInterface apiService;
    private int operation_id = 0;
    private int retry_count = 3;

    private static MessageModel msg;

    private MainActivity activity;

    public FragmentMessage() {
        // Required empty public constructor
    }

    public static FragmentMessage pushNotif(MessageModel m) {
        FragmentMessage fragment = new FragmentMessage();
        msg = m;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = ((MainActivity)getActivity());
        if (activity != null) userDefault = activity.getUserDefault();

        Bundle args = getArguments();
        if (args != null) operation_id = args.getInt("operation_id", 1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_message, container, false);

        rv = rootview.findViewById(R.id.rv);
        sw = rootview.findViewById(R.id.SWRoot);
        tvEmpty = rootview.findViewById(R.id.tvEmpty);
        pbLoading = rootview.findViewById(R.id.pbLoading);

        if (msg != null) {
            Fragment newFragment = FragmentMessageDetail.newInstance(msg);
            activity.cFragmentWithBS(newFragment);
            msg = null;
        }else setData();

        return rootview;
    }

    public void setData() {
        userResponseModel = activity.getUser();
        if(userResponseModel.getUser()!=null){
            if (activity.getUser()!= null) {
                getMessageData();
            }
        }else{
            activity.getUserData();
        }
//        if (activity.getUser() != null) {
//            if (messageList == null) getMessageData();
//            else setBaseView();
//        } else activity.getUserData();
    }

    private void setBaseView() {
        activity.setToolbarTitle("Inbox");

        sw.setRefreshing(false);
        pbLoading.setVisibility(View.GONE);

        if (messageList.getMessages().size() > 0) {
            rv.setLayoutManager(new LinearLayoutManager(getActivity()));
            adapter = new MessageAdapter(messageList.getMessages(), R.layout.item_message_list, getActivity());

            rv.setAdapter(adapter);
            adapter.setClickListener(new MessageAdapter.ItemClickListener() {
                @Override
                public void onClick(View view, int position) {
                    if(adapter.getdata(position).getState() == 0){
                        activity.updateMessage(adapter.getdata(position).getMsgId(), 0);
                        activity.msgCount.setTotalNew(
                                activity.msgCount.getTotalNew()-1
                        );

                        adapter.data.get(position).setState(1);
                        adapter.notifyDataSetChanged();
                    }

                    Fragment newFragment = FragmentMessageDetail.newInstance(
                           adapter.getdata(position));
                    activity.cFragmentWithBS(newFragment);
                }
            });

            rv.setVisibility(View.VISIBLE);
        }else{
            tvEmpty.setText(messageList.getProcessMsg());
            tvEmpty.setVisibility(View.VISIBLE);
        }

        sw.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                rv.setVisibility(View.INVISIBLE);
                getMessageData();
                sw.setRefreshing(false);
            }
        });
    }

    /*REST CALL*/
    // Get Message List Data
    public void getMessageData() {
        if (!activity.checkConnection()) return;
        if (apiService == null) apiService = activity.getApiService();

        pbLoading.setVisibility(View.VISIBLE);
        tvEmpty.setVisibility(View.GONE);
        rv.setVisibility(View.GONE);

        Call<ResponseMessageList> call = apiService.getMessageList("inboxRetrieve",
                activity.genSBUX("email=" + userDefault.getEmail() +
                        "&sm_token=" + userDefault.getAccToken() +
                        "&card_number=" + userResponseModel.getUser().getExternalId()+
                        "&lang=" + userDefault.getLanguage())
        );

        call.enqueue(new Callback<ResponseMessageList>() {
            @Override
            public void onResponse(Call<ResponseMessageList> call, Response<ResponseMessageList> response) {
                if (getActivity() != null) {
                    if (response.isSuccessful() && response.body() != null) {
                        if (activity.successResponse(response.body().getStatus(),response.body().getProcessMsg())) {
                            messageList = response.body();
                        if(messageList.getMessages()!=null){
                            setBaseView();
                        }else{

                        }
                        }
                    } else {
                        activity.checkServer(String.valueOf(response.code()), response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseMessageList> call, Throwable t) {
                if (getActivity() != null && isAdded())
                    activity.restFailure(t);
            }
        });

    }
}
