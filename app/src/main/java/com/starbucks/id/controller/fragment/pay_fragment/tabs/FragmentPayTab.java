package com.starbucks.id.controller.fragment.pay_fragment.tabs;

/*
 * Created by Angga N P on 3/13/2018.
 */

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.starbucks.id.R;
import com.starbucks.id.controller.activity.MainActivity;
import com.starbucks.id.controller.fragment.pay_fragment.FragmentReload;
import com.starbucks.id.controller.fragment.pay_fragment.pay_adapter.CardAdapter;
import com.starbucks.id.helper.UserDefault;
import com.starbucks.id.helper.utils.DataUtil;
import com.starbucks.id.model.ResponseBase;
import com.starbucks.id.model.pay.ResponseCardBalance;
import com.starbucks.id.model.user.UserIdentifierModel;
import com.starbucks.id.model.user.UserResponseModel;
import com.starbucks.id.rest.ApiClient;
import com.starbucks.id.rest.ApiInterface;

import java.io.IOException;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentPayTab extends Fragment implements CardAdapter.ItemClickListener {

    private TextView tvCard, tvBalance, tvTs, tvEmpty, tvActive, tvMyCards;
    private Button btPay, btReload;
    private ImageView ivCard, ivRefresh;
    private RecyclerView rvCard;
    private RoundedCornersTransformation roundedCornersTransformation;
    private Picasso picasso;
    private CardAdapter adapter;
    private NestedScrollView NSV;
    private ProgressBar pbBalance;

    private UserDefault userDefault;
    private UserIdentifierModel selectedCard;
    private ApiInterface apiService;
    private CheckBox cbDefault;

    private Call<ResponseCardBalance> callBalance;
    private Call<ResponseBase> callDefault;
    private ResponseCardBalance balance;

    private UserResponseModel userResponseModel;

    private MainActivity activity;

    public FragmentPayTab() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = ((MainActivity)getActivity());
        if (activity != null) userDefault = activity.getUserDefault();

        picasso = activity.getPicasso();
        roundedCornersTransformation = new RoundedCornersTransformation(10, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_pay_tab, container, false);

        tvCard = rootView.findViewById(R.id.tvCard);
        tvBalance = rootView.findViewById(R.id.tvBalance);
        tvTs = rootView.findViewById(R.id.tvTS);
        tvEmpty = rootView.findViewById(R.id.tvEmpty);
        tvActive = rootView.findViewById(R.id.tvActive);
        tvMyCards = rootView.findViewById(R.id.tvMyCards);

        ivRefresh = rootView.findViewById(R.id.ivRefresh);
        btPay = rootView.findViewById(R.id.btPay);
        btReload = rootView.findViewById(R.id.btReload);
        ivCard = rootView.findViewById(R.id.ivCard);
        rvCard = rootView.findViewById(R.id.rvCard);
        cbDefault = rootView.findViewById(R.id.cbDefault);
        NSV = rootView.findViewById(R.id.NSV);
        pbBalance = rootView.findViewById(R.id.pbBalance);

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
                    setBaseView();
            }
        }else{
         ivCard.setVisibility(View.INVISIBLE);
         NSV.setVisibility(View.INVISIBLE);
         activity.getUserData();
        }
    }

    private void setBaseView() {
        ivCard.setVisibility(View.INVISIBLE);
        NSV.setVisibility(View.VISIBLE);

        selectedCard = userResponseModel.getUser().getIdentifiers().get(0);

        rvCard.setLayoutManager(new LinearLayoutManager((getActivity().getApplicationContext()), LinearLayoutManager.HORIZONTAL, false));

        adapter = new CardAdapter(userResponseModel.getUser().getIdentifiers(), R.layout.item_cards_grid, getActivity());
        rvCard.setAdapter(adapter);
        adapter.setClickListener(this);

        if (userResponseModel.getUser().getIdentifiers().get(0) != null) {
            tvEmpty.setVisibility(View.GONE);
            rvCard.setVisibility(View.VISIBLE);
        } else {
            tvEmpty.setVisibility(View.VISIBLE);
            rvCard.setVisibility(View.GONE);
        }

        tvTs.setText((userDefault.IDLanguage() ? "Terakhir diperbarui " : "As of ") +
                DataUtil.getCurTS());

        ivRefresh.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getCardBalance(selectedCard.getExternalId());
            }
        });

        btPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.payPopUp(selectedCard.getExternalId());
            }
        });

        btReload.setText(userDefault.IDLanguage() ? "ISI ULANG" : "TOP UP");


        btReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (balance.getDetails().getCardStatus().toLowerCase().equals("a")) {
                    Bundle argums = new Bundle();
                    argums.putString("card_number", selectedCard.getExternalId());
                    argums.putString("card_image", balance.getDetails().getCardImg());
                    if (balance != null) if (balance.getDetails() != null)
                        if (balance.getDetails().getCardBalance() != null)
                            argums.putString("balance", balance.getDetails().getCardBalance());

                    activity.cFragmentWithBundle(new FragmentReload(), argums);
                } else {
                    activity.showToast(userDefault.IDLanguage() ?
                            "Kartu ini tidak aktif" : "This card is inactive");
                }
            }
        });

        cbDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!cbDefault.isChecked()) cbDefault.setChecked(true);
                else setDefCard();
            }
        });

        if (userDefault.IDLanguage()) {
            tvMyCards.setText("KARTU SAYA");
            cbDefault.setText("Digunakan sebagai kartu utama");
            btPay.setText("Bayar");
        }

        setCardView();
    }

    private void setCardView() {
        ivCard.setVisibility(View.INVISIBLE);
        String str = selectedCard.getExternalId();

        if (selectedCard.getExternalId().equals(userResponseModel.getUser().getUserProfile().getDefaultCard()))
            cbDefault.setChecked(true);
        else cbDefault.setChecked(false);

        if (str != null && str.length() > 4)
//            tvCard.setText((userDefault.IDLanguage() ?
//                    "Kartu Starbucks (" : "Starbucks Card (") + str.substring(str.length() - 4)
//                    + ")"
//            );
            tvCard.setText(userDefault.IDLanguage()?"Saldo akun Anda":"Your account balance");

        picasso.load(ApiClient.INSTANCE.getCARDS_IMG_URL() + selectedCard.getCardImage())
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

        getCardBalance(selectedCard.getExternalId());
    }

    private void showBalance() {
        if (balance != null &&
                balance.getDetails() != null &&
                balance.getDetails().getCardBalance() != null) {

            tvBalance.setText(DataUtil.currencyFormat(balance.getDetails().getCardBalance()));

            if (balance.getDetails().getCardStatus().toLowerCase().equals("a")) {
                tvActive.setText(userDefault.IDLanguage() ? R.string.active_id : R.string.active);
                tvActive.setTextColor(getResources().getColor(R.color.greenPrimary));
            } else {
                tvActive.setText(userDefault.IDLanguage() ? R.string.deactive_id : R.string.deactive);
                tvActive.setTextColor(getResources().getColor(R.color.error_state_red));
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
        }else{
            tvBalance.setText(R.string.balance_error);

            tvActive.setText(userDefault.IDLanguage() ? R.string.deactive_id : R.string.deactive);
            tvActive.setTextColor(getResources().getColor(R.color.error_state_red));

            picasso.load(ApiClient.INSTANCE.getIMG_DEFAULT()).into(ivCard);
            ivCard.setVisibility(View.VISIBLE);
        }

        tvActive.setVisibility(View.VISIBLE);
        tvBalance.setVisibility(View.VISIBLE);
        ivRefresh.setVisibility(View.VISIBLE);
        pbBalance.setVisibility(View.INVISIBLE);
        btReload.setVisibility(View.VISIBLE);
        tvTs.setVisibility(View.VISIBLE);
    }

    private void setDefCard() {
        if (!activity.checkConnection()) return;
        if (apiService == null) apiService = activity.getApiService();

        NSV.setVisibility(View.INVISIBLE);
        btReload.setVisibility(View.INVISIBLE);

        callDefault = apiService.setDefaultCard("set_default_card",
                activity.genSBUX(
                        "card_number=" + userResponseModel.getUser().getExternalId() +
                                "&email=" + userDefault.getEmail() +
                                "&default_card=" + selectedCard.getExternalId()));

        callDefault.enqueue(new Callback<ResponseBase>() {
            @Override
            public void onResponse(Call<ResponseBase> call, Response<ResponseBase> response) {
                if (getActivity() != null) {
                    if (response.isSuccessful() && response.body() != null) {
                        if (activity.successResponse(response.body().getReturnCode(), response.body().getProcessMsg())) {
                            activity.showToast(response.body().getProcessMsg());
                            activity.getUserData();
                        } else {
                            NSV.setVisibility(View.VISIBLE);
                            setBaseView();
                        }
                    } else {
                        NSV.setVisibility(View.INVISIBLE);
                        activity.
                                checkServer(String.valueOf(response.code()), response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBase> call, Throwable t) {
                if (getActivity() != null && isAdded() && t != null)
                    activity.restFailure(t);
            }
        });
    }


    /*REST CALL*/
    // Get Card Balance from Server
    private void getCardBalance(String card) {
        if (!activity.checkConnection()) return;
        if (apiService == null) apiService = activity.getApiService();

        if (callBalance != null && callBalance.isExecuted()) callBalance.cancel();

        balance = null;

        btReload.setVisibility(View.INVISIBLE);
        tvTs.setVisibility(View.INVISIBLE);
        tvActive.setVisibility(View.INVISIBLE);
        tvBalance.setVisibility(View.INVISIBLE);
        ivRefresh.setVisibility(View.INVISIBLE);
        pbBalance.setVisibility(View.VISIBLE);

        callBalance = apiService.getCardBalance("balance",
                activity.genSBUX("uid=" + userDefault.getEmail() +
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
                    if (!(t instanceof IOException)) showBalance();
                }
            }
        });
    }


    /*Override Func*/
    @Override
    public void onClick(View view, int position) {
        selectedCard = userResponseModel.getUser().getIdentifiers().get(position);
        setCardView();
    }

    @Override
    public void onDestroy() {
        if (callBalance != null && !callBalance.isExecuted()) callBalance.cancel();
        if (callDefault != null && !callDefault.isExecuted()) callDefault.cancel();
        super.onDestroy();
    }
}
