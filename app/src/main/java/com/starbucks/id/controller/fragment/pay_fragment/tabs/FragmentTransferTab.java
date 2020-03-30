package com.starbucks.id.controller.fragment.pay_fragment.tabs;

import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.starbucks.id.R;
import com.starbucks.id.controller.activity.MainActivity;
import com.starbucks.id.controller.fragment.pay_fragment.pay_adapter.CardAdapter;
import com.starbucks.id.helper.UserDefault;
import com.starbucks.id.helper.utils.DataUtil;
import com.starbucks.id.model.pay.ResponseCardBalance;
import com.starbucks.id.model.pay.ResponseTransferBalance;
import com.starbucks.id.model.user.UserIdentifierModel;
import com.starbucks.id.model.user.UserResponseModel;
import com.starbucks.id.rest.ApiClient;
import com.starbucks.id.rest.ApiInterface;

import java.io.IOException;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Angga N P on 4/23/2018.
 */
public class FragmentTransferTab extends Fragment implements View.OnClickListener, CardAdapter.ItemClickListener {
    private ImageView ivCard, ivCard2, ivFrom, ivTo;
    private TextView tvCardNo, tvBalance, tvTS,
            tvCardTo, tvBalanceTo, tvTsTo,
            tvTraTo, tvTraFro;
    private ImageButton ibRefresh, ibRefreshTo, ibStatFro, ibStatTo;
    private Button btCardFrom, btCardTo, btConfirm;
    private ConstraintLayout CLTraNSVBase;

    private NestedScrollView NSV;
    private RoundedCornersTransformation roundedCornersTransformation;
    private Picasso picasso;
    private UserDefault userDefault;
    private RecyclerView rvFrom, rvTo;
    private CardAdapter adapterFrom;
    private CardAdapter adapterTo;
    private UserIdentifierModel cardFrom, cardTo;
    private ProgressBar pbBalance, pbBalanceTo;
    private ApiInterface apiService;
    private ResponseCardBalance balance;
    private ResponseCardBalance balanceTo;

    private UserResponseModel userResponseModel;
    private Call<ResponseCardBalance> callBalance;
    private Call<ResponseCardBalance> callBalanceTo;
    private Call<ResponseTransferBalance> callTransfer;

    private MainActivity activity;

    public FragmentTransferTab() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = ((MainActivity)getActivity());
        if (activity != null) userDefault = activity.getUserDefault();
        if (activity != null) picasso = activity.getPicasso();

        roundedCornersTransformation = new RoundedCornersTransformation(10, 0);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_pay_transfer, container, false);

        NSV = rootView.findViewById(R.id.NSV);
        ivCard = rootView.findViewById(R.id.ivCard);
        ivCard2 = rootView.findViewById(R.id.ivCard2);
        ivFrom = rootView.findViewById(R.id.ivFrom);
        ivTo = rootView.findViewById(R.id.ivTo);
        tvCardNo = rootView.findViewById(R.id.tvCardNo);
        tvBalance = rootView.findViewById(R.id.tvBalance);
        tvTS = rootView.findViewById(R.id.tvTS);
        tvTraFro = rootView.findViewById(R.id.tvTraFro);
        tvTraTo = rootView.findViewById(R.id.tvTraTo);

        CLTraNSVBase = rootView.findViewById(R.id.CLTraNSVBase);

        rvFrom = rootView.findViewById(R.id.rvFrom);
        rvTo = rootView.findViewById(R.id.rvTo);
        pbBalance = rootView.findViewById(R.id.pbBalance);

        tvCardTo = rootView.findViewById(R.id.tvCardTo);
        tvBalanceTo = rootView.findViewById(R.id.tvBalanceTo);
        tvTsTo = rootView.findViewById(R.id.tvTsTo);
        pbBalanceTo = rootView.findViewById(R.id.pbBalanceTo);
        ibRefreshTo = rootView.findViewById(R.id.ibRefreshTo);

        ibStatFro = rootView.findViewById(R.id.ibStatFro);
        ibStatTo = rootView.findViewById(R.id.ibStatTo);

        rvFrom.setLayoutManager(new LinearLayoutManager((getActivity().getApplicationContext()), RecyclerView.VERTICAL, false));
        rvTo.setLayoutManager(new LinearLayoutManager((getActivity().getApplicationContext()), RecyclerView.VERTICAL, false));

        ibRefresh = rootView.findViewById(R.id.ibRefresh);
        btCardFrom = rootView.findViewById(R.id.btCardFrom);
        btCardTo = rootView.findViewById(R.id.btCardTo);
        btConfirm = rootView.findViewById(R.id.btConfirm);

        ibRefreshTo.setOnClickListener(this);
        ibRefresh.setOnClickListener(this);
        btCardFrom.setOnClickListener(this);
        btCardTo.setOnClickListener(this);
        btConfirm.setOnClickListener(this);

        if (getActivity() != null) setData();

        return rootView;
    }
    /*UI Controller*/
    public void setData() {
        userResponseModel = activity.getUser();
        if(userResponseModel.getUser()!=null){
            if (activity.getUser()!= null) {
                if (userResponseModel.getUser().getIdentifiers() != null &&
                        userResponseModel.getUser().getIdentifiers().size() > 0)
                    setDefaultView();
            }
        }else{
            ivCard.setVisibility(View.INVISIBLE);
            NSV.setVisibility(View.INVISIBLE);
            activity.getUserData();
        }
    }

    private void setDefaultView() {
        NSV.setVisibility(View.VISIBLE);

        CLTraNSVBase.setOnClickListener(this);

        if (userDefault.IDLanguage()) {
            tvTraFro.setText("Transfer Dari");
            tvTraTo.setText("Transfer ke");
        }

        //      Card From
        setViewFrom(userResponseModel.getUser().getIdentifiers().get(0));

        //      Card To
        setViewTo(userResponseModel.getUser().getIdentifiers().get(0));

        //        Populate Card From
        adapterFrom = new CardAdapter(userResponseModel.getUser().getIdentifiers(), R.layout.item_cards_list_from, getActivity());
        rvFrom.setAdapter(adapterFrom);
        adapterFrom.setClickListener(this);

        //        Populate Card To
        adapterTo = new CardAdapter(userResponseModel.getUser().getIdentifiers(), R.layout.item_cards_list_to, getActivity());
        rvTo.setAdapter(adapterTo);
        adapterTo.setClickListener(this);
    }

    private void setViewFrom(final UserIdentifierModel cm) {
        cardFrom = cm;

        getCardBalance(cm.getExternalId());

        btCardFrom.setText(DataUtil.maskCard(cm.getExternalId()));
        tvCardNo.setText(DataUtil.maskCard(cm.getExternalId()));
        tvTS.setText(userDefault.IDLanguage() ? getString(R.string.timestamp_id) + " " + DataUtil.getCurTS() :
                getString(R.string.timestamp) + " " + DataUtil.getCurTS());
    }

    private void setViewTo(final UserIdentifierModel cm) {
        cardTo = cm;

        getCardBalanceTo(cm.getExternalId());

        btCardTo.setText(DataUtil.maskCard(cm.getExternalId()));
        tvCardTo.setText(DataUtil.maskCard(cm.getExternalId()));
        tvTsTo.setText(userDefault.IDLanguage() ? getString(R.string.timestamp_id) + " " + DataUtil.getCurTS() :
                getString(R.string.timestamp) + " " + DataUtil.getCurTS());
    }

    private void showBalance() {
        if (balance != null &&
                balance.getDetails() != null &&
                balance.getDetails().getCardBalance() != null) {

            tvBalance.setText(DataUtil.currencyFormat(balance.getDetails().getCardBalance()));
            if (balance.getDetails().getCardStatus().toLowerCase().equals("a"))
                ibStatFro.setImageResource(R.drawable.def_card);
            else ibStatFro.setImageResource(R.drawable.inactive_card);

            if (balanceTo == null) {
                balanceTo = balance;
                showBalanceTo();
            }

            if (balance.getDetails().getCardImg() != null) {
                picasso.load(ApiClient.INSTANCE.getCARDS_IMG_URL() + balance.getDetails().getCardImg())
                        .transform(roundedCornersTransformation)
                        .into(ivCard, new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {
                                ivCard.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onError() {
                                picasso.load(ApiClient.INSTANCE.getIMG_DEFAULT()).into(ivCard);
                                ivCard.setVisibility(View.VISIBLE);
                            }
                        });
            }else{
                picasso.load(ApiClient.INSTANCE.getIMG_DEFAULT()).into(ivCard);
                ivCard.setVisibility(View.VISIBLE);
            }
        } else {
            tvBalance.setText("Balance Error");
            ibStatFro.setImageResource(R.drawable.inactive_card);
            picasso.load(ApiClient.INSTANCE.getIMG_DEFAULT()).into(ivCard);
            ivCard.setVisibility(View.VISIBLE);

            if (balanceTo == null) showBalanceTo();
        }

        tvBalance.setVisibility(View.VISIBLE);
        ibRefresh.setVisibility(View.VISIBLE);
        ibStatFro.setVisibility(View.VISIBLE);
        pbBalance.setVisibility(View.INVISIBLE);
    }

    private void hideBalance() {
        ibStatFro.setVisibility(View.INVISIBLE);
        tvBalance.setVisibility(View.INVISIBLE);
        ibRefresh.setVisibility(View.INVISIBLE);
        pbBalance.setVisibility(View.VISIBLE);
        ivCard.setVisibility(View.INVISIBLE);

    }

    private void showBalanceTo() {
        if (balanceTo != null &&
                balanceTo.getDetails() != null &&
                balanceTo.getDetails().getCardBalance() != null){

            tvBalanceTo.setText(DataUtil.currencyFormat(balanceTo.getDetails().getCardBalance()));

            if (balanceTo.getDetails().getCardStatus().toLowerCase().equals("a"))
                ibStatTo.setImageResource(R.drawable.def_card);
            else ibStatTo.setImageResource(R.drawable.inactive_card);

            if (balanceTo.getDetails().getCardImg() != null) {
                picasso.load(ApiClient.INSTANCE.getCARDS_IMG_URL() + balanceTo.getDetails().getCardImg())
                        .transform(roundedCornersTransformation)
                        .into(ivCard2, new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {
                                ivCard2.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onError() {
                                picasso.load(ApiClient.INSTANCE.getIMG_DEFAULT()).into(ivCard2);
                                ivCard2.setVisibility(View.VISIBLE);
                            }
                        });
            }else{
                picasso.load(ApiClient.INSTANCE.getIMG_DEFAULT()).into(ivCard2);
                ivCard2.setVisibility(View.VISIBLE);
            }
        }else {
            tvBalanceTo.setText("Balance Error");
            ibStatTo.setImageResource(R.drawable.inactive_card);
            picasso.load(ApiClient.INSTANCE.getIMG_DEFAULT()).into(ivCard2);
            ivCard2.setVisibility(View.VISIBLE);
        }

        tvBalanceTo.setVisibility(View.VISIBLE);
        ibRefreshTo.setVisibility(View.VISIBLE);
        ibStatTo.setVisibility(View.VISIBLE);
        pbBalanceTo.setVisibility(View.INVISIBLE);
    }

    private void hideBalanceTo() {
        ibStatTo.setVisibility(View.INVISIBLE);
        tvBalanceTo.setVisibility(View.INVISIBLE);
        ibRefreshTo.setVisibility(View.INVISIBLE);
        pbBalanceTo.setVisibility(View.VISIBLE);
        ivCard2.setVisibility(View.INVISIBLE);

    }

    private void getCardBalance(String card) {
        if (!activity.checkConnection()) return;
        if (apiService == null) apiService = activity.getApiService();

        balance = null;
        if (callBalance != null && callBalance.isExecuted()) callBalance.cancel();

        callBalance = apiService.getCardBalance("balance",
                activity.genSBUX(
                        "uid=" + userDefault.getEmail() +
                                "&card=" + card
                ));

        callBalance.enqueue(new Callback<ResponseCardBalance>() {
            @Override
            public void onResponse(Call<ResponseCardBalance> call, Response<ResponseCardBalance> response) {
                if (getActivity() != null) {
                    if (response.isSuccessful() && response.body() != null) {
                        balance = response.body();
                    } else {
                        activity.checkServer(String.valueOf(response.code()), response.message());
                    }
                    showBalance();
                }
            }

            @Override
            public void onFailure(Call<ResponseCardBalance> call, Throwable t) {
                if (getActivity() != null && isAdded()) {
                    activity.restFailure(t);
                    if (!(t instanceof IOException)) {
                        showBalance();
                        if (balanceTo == null) showBalanceTo();
                    }


                }
            }
        });
    }


    /*REST CALL*/
    // Get Card Balance from Server
    private void getCardBalanceTo(String card) {
        if (!activity.checkConnection()) return;
        if (apiService == null) apiService = activity.getApiService();

        balanceTo = null;
        if (callBalanceTo != null && callBalanceTo.isExecuted()) callBalanceTo.cancel();

        callBalanceTo = apiService.getCardBalance("balance",
                activity.genSBUX(
                        "uid=" + userDefault.getEmail() +
                                "&card=" + card
                ));

        callBalanceTo.enqueue(new Callback<ResponseCardBalance>() {
            @Override
            public void onResponse(Call<ResponseCardBalance> call, Response<ResponseCardBalance> response) {
                if (getActivity() != null) {
                    if (response.isSuccessful() && response.body() != null) {
                        balanceTo = response.body();
                    } else {
                        activity.checkServer(String.valueOf(response.code()), response.message());
                    }
                    showBalanceTo();
                }
            }

            @Override
            public void onFailure(Call<ResponseCardBalance> call, Throwable t) {
                if (getActivity() != null && isAdded()) {
                    activity.restFailure(t);
                    if (!(t instanceof IOException)) showBalanceTo();
                }
            }
        });
    }

    // Set Default Card to Server
    private void transferData() {
        if (!activity.checkConnection()) return;
        if (apiService == null) apiService = activity.getApiService();

        NSV.setVisibility(View.INVISIBLE);

        callTransfer = apiService.transferCard("transfer_balance",
                activity.genSBUX(
                        "uid=" + userDefault.getEmail() +
                                "&source_card=" + cardFrom.getExternalId() +
                                "&dest_card=" + cardTo.getExternalId()
                ));

        callTransfer.enqueue(new Callback<ResponseTransferBalance>() {
            @Override
            public void onResponse(Call<ResponseTransferBalance> call, Response<ResponseTransferBalance> response) {
                if (getActivity() != null) {
                    if (response.isSuccessful() && response.body() != null) {

                        if (activity.successResponse(response.body().getReturnCode(), response.body().getProcessMsg())) {
                            activity.showToast("Transfer Success.");
                            activity.getUserData();
                        } else if (!response.body().getReturnCode().equals("01") ||
                                !response.body().getReturnCode().equals("09")) {
                            activity.showToast(response.body().getProcessMsg());
                            NSV.setVisibility(View.VISIBLE);
                        }
                    } else {
                        activity.
                                checkServer(String.valueOf(response.code()), response.message());
                        NSV.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseTransferBalance> call, Throwable t) {
                if (getActivity() != null && isAdded()) {
                    activity.restFailure(t);
                    NSV.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    /*Override Func*/
    @Override
    public void onClick(View view, int position) {
        switch (view.getId()) {
            case R.id.CLFrom:
                setViewFrom(adapterFrom.getCard(position));

                ivFrom.animate().rotation(0).start();
                rvFrom.setVisibility(View.GONE);
                hideBalance();
                break;

            case R.id.CLTo:
                setViewTo(adapterTo.getCard(position));

                ivTo.animate().rotation(0).start();
                rvTo.setVisibility(View.GONE);
                hideBalanceTo();
                break;
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibRefresh:
                hideBalance();
                getCardBalance(cardFrom.getExternalId());
                break;
            case R.id.ibRefreshTo:
                hideBalanceTo();
                getCardBalanceTo(cardTo.getExternalId());
                break;
            case R.id.btCardFrom:
                if (rvFrom.getVisibility() != View.VISIBLE) {
                    ivFrom.animate().rotation(180).start();
                    rvFrom.setVisibility(View.VISIBLE);

                    ivTo.animate().rotation(0).start();
                    rvTo.setVisibility(View.GONE);
                } else {
                    ivFrom.animate().rotation(0).start();
                    rvFrom.setVisibility(View.GONE);
                }
                break;
            case R.id.btCardTo:
                if (rvTo.getVisibility() != View.VISIBLE) {
                    ivTo.animate().rotation(180).start();
                    rvTo.setVisibility(View.VISIBLE);


                    ivFrom.animate().rotation(0).start();
                    rvFrom.setVisibility(View.GONE);
                } else {
                    ivTo.animate().rotation(0).start();
                    rvTo.setVisibility(View.GONE);
                }
                break;
            case R.id.btConfirm:
                if (cardFrom == cardTo)
                    activity.showToast(userDefault.IDLanguage() ?
                            getString(R.string.msg_same_card_id) :
                            getString(R.string.msg_same_card));

                else if (balance == null || balanceTo == null)
                    activity
                            .showToast(userDefault.IDLanguage() ? "Harap tunggu Saldo Kartu anda"
                                    : "Please wait for your Card Balance");
                else if (balance.getDetails() != null) {
                    if (balance.getDetails().getCardStatus().toLowerCase().equals("d"))
                        activity
                                .showToast(userDefault.IDLanguage() ? "Kartu sumber anda tidak aktif"
                                        : "Your source card is inactive");
                    else if ((Integer.parseInt(balance.getDetails().getCardBalance()) == 0))
                        activity
                                .showToast(userDefault.IDLanguage() ? "Saldo kartu sumber anda adalah 0"
                                        : "Your source card balance is 0");
                    else if (balanceTo.getDetails() != null) {
                        if (balanceTo.getDetails().getCardStatus().toLowerCase().equals("d"))
                            activity
                                    .showToast(userDefault.IDLanguage() ? "Kartu tujuan anda tidak aktif"
                                            : "Your destination card is inactive");
                        else if ((Integer.parseInt(balance.getDetails().getCardBalance()) +
                                Integer.parseInt(balanceTo.getDetails().getCardBalance())) > 2000000)
                            activity
                                    .showToast(!userDefault.IDLanguage() ? getString(R.string.en_cant_reload_exc_bal)
                                            : getString(R.string.id_cant_reload_exc_bal));
                        else transferData();
                    }
                }

                break;

            case R.id.CLTraNSVBase:
                if (rvFrom.getVisibility() == View.VISIBLE) rvFrom.setVisibility(View.GONE);
                if (rvTo.getVisibility() == View.VISIBLE) rvTo.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onDestroy() {
        if (callBalance != null && !callBalance.isExecuted()) callBalance.cancel();
        if (callBalanceTo != null && !callBalanceTo.isExecuted()) callBalanceTo.cancel();
        super.onDestroy();
    }
}
