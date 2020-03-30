package com.starbucks.id.controller.fragment.home_fragment.profile_tabs;

/**
 * Created by Angga N P on 3/13/2018.
 */

import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.TextView;

import com.starbucks.id.R;
import com.starbucks.id.controller.activity.MainActivity;
import com.starbucks.id.controller.extension.SpinnerAdapter;
import com.starbucks.id.controller.fragment.home_fragment.profile_tabs.profile_adapter.ActivityItemAdapter;
import com.starbucks.id.controller.fragment.pay_fragment.pay_adapter.CardAdapter;
import com.starbucks.id.helper.UserDefault;
import com.starbucks.id.helper.utils.DataUtil;
import com.starbucks.id.model.extension.EavModel;
import com.starbucks.id.model.trx.ActivityResponseModel;
import com.starbucks.id.model.trx.ActivityResultModel;
import com.starbucks.id.model.user.UserIdentifierModel;
import com.starbucks.id.model.user.UserResponseModel;
import com.starbucks.id.rest.ApiInterface;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentActivity extends Fragment implements CardAdapter.ItemClickListener,
        View.OnClickListener, SpinnerAdapter.ItemClickListener, ActivityItemAdapter.ItemClickListener {

    private ConstraintLayout CLContainer;
    private RecyclerView rv, rvCard, rvTS;
    private Button btTS, btCard;
    private TextView tvEmpty;
    private UserDefault userDefault;

    private UserIdentifierModel selectedCard;
    private CardAdapter cardAdapter;

    private EavModel time;
    private List<EavModel> timePick;
    private SpinnerAdapter timeAdapter;

    private ApiInterface apiService;
    private ActivityResponseModel activityResponseModel;
    private UserResponseModel userResponseModel;

    private List<ActivityResultModel> curTrx;
    private List<ActivityResultModel> lastTrx;

    private String cMonth, lMonth;

    private ActivityItemAdapter adapter;

    private MainActivity activity;

    public FragmentActivity() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = ((MainActivity)getActivity());
        if (activity != null) userDefault = activity.getUserDefault();
        timePick = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home_profile_activity, container, false);

        CLContainer = rootView.findViewById(R.id.CLContainer);


        rv = rootView.findViewById(R.id.rvHistory);
        rvCard = rootView.findViewById(R.id.rvCard);
        rvTS = rootView.findViewById(R.id.rvTS);

        btTS = rootView.findViewById(R.id.btTS);
        btCard = rootView.findViewById(R.id.btCard);

        btTS.setOnClickListener(this);
        btCard.setOnClickListener(this);

        tvEmpty = rootView.findViewById(R.id.tvEmpty);

        rv.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        rvCard.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        rvTS.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        setData();

        return rootView;
    }


    public void setData() {
        setMonth();

        userResponseModel = activity.getUser();
        selectedCard = userResponseModel.getUser().getIdentifiers().get(0);
        getTrx();
    }

    private void setBaseView() {
        activity.setToolbarTitle(
                userDefault.IDLanguage() ? activity.getString(R.string.id_activity_history_title) : activity.getString(R.string.en_activity_history_title));

        CLContainer.setVisibility(View.VISIBLE);
        time = timePick.get(0);
        if (userDefault.IDLanguage()) tvEmpty.setText(
                activity.getString(R.string.id_empty_activity));


        //Populate Card
        cardAdapter = new CardAdapter(userResponseModel.getUser().getIdentifiers(), R.layout.item_cards_list_activity, getActivity());
        rvCard.setAdapter(cardAdapter);
        cardAdapter.setClickListener(this);

        btCard.setText(DataUtil.maskCard(userResponseModel.getUser().getIdentifiers().get(0).getExternalId()));

        getFilter();
    }

    private void populateTime() {
        if (userDefault.IDLanguage()) btTS.setText(getString(R.string.id_cur_month));

        timePick.clear();

        EavModel ea1 = new EavModel();
        ea1.setAttribute("1");
        ea1.setValue(userDefault.IDLanguage() ? getString(R.string.id_cur_month) : getString(R.string.cur_month));

        EavModel ea2 = new EavModel();
        ea2.setAttribute("2");
        ea2.setValue(userDefault.IDLanguage() ? getString(R.string.id_last_month) : getString(R.string.last_month));

        timePick.add(ea1);
        timePick.add(ea2);

        timeAdapter = new SpinnerAdapter(timePick, R.layout.rv_spinner_list, getActivity());
        rvTS.setAdapter(timeAdapter);
        timeAdapter.setClickListener(this);

        setBaseView();
    }

    private void setMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM");
        Calendar cal = new GregorianCalendar();
        cMonth = sdf.format(cal.getTime());

        cal.add(Calendar.MONTH, -1);
        lMonth = sdf.format(cal.getTime());
    }

    private void mapAllData() {
        curTrx = new ArrayList<>();
        lastTrx = new ArrayList<>();

        for (ActivityResultModel row : activityResponseModel.getResult()) {
            if (row.getTimestamp() != null && row.getTimestamp().length() > 10) {
                if (genDate(row.getTimestamp().substring(0, 10)).contains(cMonth)) {
                    curTrx.add(row);
                } else if (genDate(row.getTimestamp().substring(0, 10)).contains(lMonth)) {
                    lastTrx.add(row);
                }
            }
        }

        populateTime();
    }

    private String genDate(String unix) {
        Date dt = new Date(Long.parseLong(unix) * 1000);

        SimpleDateFormat sdf2 = new SimpleDateFormat("dd MMM yyyy");

        return sdf2.format(dt);
    }


    /*REST Call*/
    private void getTrx() {
        if (!activity.checkConnection()) return;
        if (apiService == null) apiService = activity.getApiService();

        CLContainer.setVisibility(View.INVISIBLE);
        activityResponseModel = null;

        Call<ActivityResponseModel> callTrx = apiService.getTrx("get_activity_transaction",
                activity.genSBUX("email=" + userDefault.getEmail()
                        + "&card_number=" + userResponseModel.getUser().getUserProfile().getDefaultCard()));

        callTrx.enqueue(new Callback<ActivityResponseModel>() {
            @Override
            public void onResponse(Call<ActivityResponseModel> call, Response<ActivityResponseModel> response) {
                if (getActivity() != null) {
                    if (response.isSuccessful() && response.body() != null) {
                        activityResponseModel = response.body();
                        if (activity.successResponse(activityResponseModel.getStatus(), activityResponseModel.getProcessMsg())) {
                            mapAllData();
                        }
                    } else {
                        activity.checkServer(String.valueOf(response.code()),response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<ActivityResponseModel> call, Throwable t) {
                if (getActivity() != null && isAdded())
                    activity.restFailure(t);
            }
        });
    }


    /*Override Func*/
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btTS:
                rvCard.setVisibility(View.GONE);
                if (rvTS.getVisibility() == View.VISIBLE) {
                    rvTS.setVisibility(View.GONE);
                } else {
                    rvTS.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.btCard:
                rvTS.setVisibility(View.GONE);
                if (rvCard.getVisibility() == View.VISIBLE) {
                    rvCard.setVisibility(View.GONE);
                } else {
                    rvCard.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    @Override
    public void onClick(View view, int position) {
        switch (view.getId()) {
            case R.id.CLCusSpin:
                time = timeAdapter.getDetail(position);
                btTS.setText(timeAdapter.getDetail(position).getValue());
                rvTS.setVisibility(View.GONE);

                rv.setVisibility(View.INVISIBLE);
                tvEmpty.setVisibility(View.INVISIBLE);

                getFilter();
                break;

            case R.id.CLAct:
                selectedCard = cardAdapter.getCard(position);
                btCard.setText(DataUtil.maskCard(cardAdapter.getCard(position).getExternalId()));
                rvCard.setVisibility(View.INVISIBLE);

                getFilter();
                break;

            case R.id.RVCLActBase:
                if (rvCard.getVisibility() == View.VISIBLE) rvCard.setVisibility(View.GONE);
                if (rvTS.getVisibility() == View.VISIBLE) rvTS.setVisibility(View.GONE);
                break;
        }
    }

    private void getFilter() {
        if (time != null && selectedCard != null) {
            rv.setVisibility(View.INVISIBLE);
            tvEmpty.setVisibility(View.INVISIBLE);
            if (time.getAttribute().equals("1")) {
                if (curTrx.size() > 0) {
                    rv.setVisibility(View.INVISIBLE);

                    adapter = new ActivityItemAdapter(curTrx, R.layout.item_profile_activity_history, getContext());
                    rv.setAdapter(adapter);
                    adapter.setClickListener(this);

                    if (selectedCard != null) {
                        adapter.getFilter().filter(selectedCard.getExternalId(), new Filter.FilterListener() {
                            @Override
                            public void onFilterComplete(int count) {
                                if (adapter.getItemCount() == 0)
                                    tvEmpty.setVisibility(View.VISIBLE);
                            }
                        });
                    }

                    rv.setVisibility(View.VISIBLE);
                } else {
                    tvEmpty.setVisibility(View.VISIBLE);
                    rv.setVisibility(View.INVISIBLE);
                    rv.setAdapter(null);
                }
            } else {
                if (lastTrx.size() > 0) {
                    rv.setVisibility(View.INVISIBLE);

                    adapter = new ActivityItemAdapter(lastTrx, R.layout.item_profile_activity_history, getContext());
                    rv.setAdapter(adapter);
                    adapter.setClickListener(this);

                    if (selectedCard != null) {
                        adapter.getFilter().filter(selectedCard.getExternalId(), new Filter.FilterListener() {
                            @Override
                            public void onFilterComplete(int count) {
                                if (adapter.getItemCount() == 0)
                                    tvEmpty.setVisibility(View.VISIBLE);
                            }
                        });
                    }

                    rv.setVisibility(View.VISIBLE);
                } else {
                    tvEmpty.setVisibility(View.VISIBLE);
                    rv.setVisibility(View.INVISIBLE);
                    rv.setAdapter(null);
                }
            }
        }
    }
}
