package com.starbucks.id.controller.fragment.pay_fragment.tabs;

import android.app.Dialog;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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
import com.starbucks.id.model.pay.ReportLostResponse;
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

/**
 * Created by Angga N P on 4/23/2018.
 */
public class FragmentReportTab extends Fragment implements CardAdapter.ItemClickListener {
    private TextView tvCardNo, tvBalance, tvTS, tvHint;
    private Button btReason, btConfirm, btCard;
    private ImageButton ibRefresh, ibStatFro;
    private ImageView ivCard, ivBtCard;
    private RoundedCornersTransformation roundedCornersTransformation;
    private Picasso picasso;
    private ProgressBar pbBalance;
    private RecyclerView rv;
    private ConstraintLayout CLRepNSVBase;
    private UserDefault userDefault;
    private UserResponseModel userResponseModel;
    private NestedScrollView NSV;
    private Dialog dialog;
    private ApiInterface apiService;

    private int operation_id = 0;

    private ResponseCardBalance balance;
    private CardAdapter adapter;
    private UserIdentifierModel cardSelected;

    private Call<ReportLostResponse> callReport;
    private Call<ResponseCardBalance> callBalance;

    private MainActivity activity;

    public FragmentReportTab() {
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
        View rootView = inflater.inflate(R.layout.fragment_pay_report, container, false);

        NSV = rootView.findViewById(R.id.NSV);
        tvCardNo = rootView.findViewById(R.id.tvCardNo);
        tvBalance = rootView.findViewById(R.id.tvBalance);
        tvTS = rootView.findViewById(R.id.tvTS);
        tvHint = rootView.findViewById(R.id.tvHint);
        pbBalance = rootView.findViewById(R.id.pbBalance);
        CLRepNSVBase = rootView.findViewById(R.id.CLRepNSVBase);
        btReason = rootView.findViewById(R.id.btReason);
        btConfirm = rootView.findViewById(R.id.btConfirm);
        btCard = rootView.findViewById(R.id.btCard);

        ibRefresh = rootView.findViewById(R.id.ibRefresh);
        ibStatFro = rootView.findViewById(R.id.ibStatFro);

        ivCard = rootView.findViewById(R.id.ivCard);
        ivBtCard = rootView.findViewById(R.id.ivBtCard);
        rv = rootView.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));

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
        NSV.setVisibility(View.VISIBLE);
        ivCard.setVisibility(View.INVISIBLE);
        btReason.setText(!userDefault.IDLanguage() ? getString(R.string.bt_reason) : getString(R.string.bt_reason_id));

        tvTS.setText(userDefault.IDLanguage() ? getString(R.string.timestamp_id) + " " + DataUtil.getCurTS()
                : getString(R.string.timestamp) + " " + DataUtil.getCurTS());
        tvHint.setText(userDefault.IDLanguage() ? getString(R.string.hint_lost_id) : getString(R.string.hint_lost));

        ibRefresh.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                hideBalance();

                getCardBalance(cardSelected.getExternalId());
            }
        });

        btReason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (balance.getDetails() != null) {
                    if (operation_id == 0) activity
                            .showToast(userDefault.IDLanguage() ? "Tolong pilih alasan anda" : "Please pick your reason");
                    else if (!balance.getDetails().getCardStatus().toLowerCase().equals("a"))
                        activity
                                .showToast(userDefault.IDLanguage() ? "Kartu ini sudah tidak aktif" : "This card is already deactivated.");
                    else showConfirm();
                } else {
                    activity
                            .showToast(userDefault.IDLanguage() ? "Kartu ini tidak aktif" : "This card is already deactivated.");
                }
            }
        });

        btCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rv.getVisibility() != View.VISIBLE) {
                    ivBtCard.animate().rotation(180).start();
                    rv.setVisibility(View.VISIBLE);
                } else {
                    ivBtCard.animate().rotation(0).start();
                    rv.setVisibility(View.GONE);
                }
            }
        });

        CLRepNSVBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rv.getVisibility() == View.VISIBLE) rv.setVisibility(View.GONE);
            }
        });


        //Populate Card
        adapter = new CardAdapter(userResponseModel.getUser().getIdentifiers(), R.layout.item_cards_list_from, getActivity());
        rv.setAdapter(adapter);
        adapter.setClickListener(this);

        setCardView(userResponseModel.getUser().getIdentifiers().get(0));
    }

    private void setCardView(final UserIdentifierModel cm) {
        cardSelected = cm;
        hideBalance();
        getCardBalance(cm.getExternalId());

        btCard.setText(DataUtil.maskCard(cm.getExternalId()));
        tvCardNo.setText(DataUtil.maskCard(cm.getExternalId()));
    }

    private void showDialog() {
        if (dialog == null) {
            dialog = new Dialog(getActivity());

            dialog.setContentView(R.layout.dialog_card_reason);

            Window window = dialog.getWindow();
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setGravity(Gravity.CENTER);
            window.setBackgroundDrawableResource(android.R.color.transparent);

            TextView tvTitle = dialog.findViewById(R.id.detailDescription);
            Button btLost = dialog.findViewById(R.id.bt);
            Button btDamage = dialog.findViewById(R.id.btDamage);

            tvTitle.setText(!userDefault.IDLanguage() ? getString(R.string.bt_reason) : getString(R.string.bt_reason_id));
            btLost.setText(!userDefault.IDLanguage() ? getString(R.string.hint_lost_card) : getString(R.string.hint_lost_card_id));
            btDamage.setText(!userDefault.IDLanguage() ? getString(R.string.hint_damaged_card) : getString(R.string.hint_damaged_card_id));

            btDamage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btReason.setText(!userDefault.IDLanguage() ? getString(R.string.hint_damaged_card) : getString(R.string.hint_damaged_card_id));
                    dialog.dismiss();
                    operation_id = 1;
                }
            });
            btLost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btReason.setText(!userDefault.IDLanguage() ? getString(R.string.hint_lost_card) : getString(R.string.hint_lost_card_id));
                    dialog.dismiss();
                    operation_id = 2;
                }
            });
        }

        dialog.show();
    }

    private void hideBalance() {
        tvBalance.setVisibility(View.INVISIBLE);
        ibRefresh.setVisibility(View.INVISIBLE);
        ibStatFro.setVisibility(View.INVISIBLE);
        pbBalance.setVisibility(View.GONE);
//        pbBalance.setVisibility(View.VISIBLE);
    }

    private void showBalance() {
        if (balance != null &&
                balance.getDetails() != null &&
                balance.getDetails().getCardBalance() != null) {

//            tvBalance.setText(DataUtil.currencyFormat(balance.getDetails().getCardBalance()));
            if (balance.getDetails().getCardStatus().toLowerCase().equals("a"))
                ibStatFro.setImageResource(R.drawable.def_card);
            else ibStatFro.setImageResource(R.drawable.inactive_card);

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

            btConfirm.setVisibility(View.VISIBLE);
        } else {
//            tvBalance.setText("Balance Error");
            ibStatFro.setImageResource(R.drawable.inactive_card);
            picasso.load(ApiClient.INSTANCE.getIMG_DEFAULT()).into(ivCard);
            ivCard.setVisibility(View.VISIBLE);

            btConfirm.setVisibility(View.GONE);
        }

//        tvBalance.setVisibility(View.VISIBLE);
//        pbBalance.setVisibility(View.INVISIBLE);
//        ibRefresh.setVisibility(View.VISIBLE);
        tvBalance.setVisibility(View.GONE);
        pbBalance.setVisibility(View.GONE);
        ibRefresh.setVisibility(View.GONE);
        ibStatFro.setVisibility(View.VISIBLE);

    }

    private void showConfirm() {
        final Dialog dialogSignOut = new Dialog(getActivity());
        dialogSignOut.setCanceledOnTouchOutside(false);
        dialogSignOut.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSignOut.setContentView(R.layout.dialog_notif_2_button);

        Window window = dialogSignOut.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawableResource(android.R.color.transparent);


        TextView tvHeader = dialogSignOut.findViewById(R.id.tvHeader);
        TextView tvContent = dialogSignOut.findViewById(R.id.tvNotif);
        Button btOk = dialogSignOut.findViewById(R.id.btOk);
        Button btCancel = dialogSignOut.findViewById(R.id.btCancel);

        if (userDefault.IDLanguage()) {
            btCancel.setText(getString(R.string.id_cancel));
            btOk.setText(getString(R.string.id_sign_out));
        }

        tvHeader.setText(userDefault.IDLanguage() ? "Peringatan" : "Warning");
        tvContent.setText(!userDefault.IDLanguage() ?
                "Are you sure want to Deactivate the card?" : "Apakah anda yakin ingin menjadikan kartu ini tidak aktif?");


        if (userDefault.IDLanguage()) {
            btOk.setText(getString(R.string.id_confirm));
            btCancel.setText(getString(R.string.id_cancel));
        } else {
            btOk.setText("OK");
            btCancel.setText("CANCEL");
        }

        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSignOut.dismiss();
        sendReport(String.valueOf(operation_id));
            }
        });

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSignOut.dismiss();
            }
        });
        dialogSignOut.show();
    }


    /*REST CALL*/
    // Send Report Card to Server
    private void sendReport(final String code) {
        if (!activity.checkConnection()) return;
        if (apiService == null) apiService = activity.getApiService();

        NSV.setVisibility(View.INVISIBLE);

        callReport = apiService.reportCard("report_card",
                activity.genSBUX(
                        "uid=" + userDefault.getEmail() +
                                "&card=" + cardSelected.getExternalId() +
                                "&reason=" + code
                ));

        callReport.enqueue(new Callback<ReportLostResponse>() {
            @Override
            public void onResponse(Call<ReportLostResponse> call, Response<ReportLostResponse> response) {
                if (getActivity() != null) {
                    NSV.setVisibility(View.VISIBLE);
                    if (response.isSuccessful()) {

                        if (activity.successResponse(response.body().getReturnCode(), response.body().getProcessMsg())) {
                            activity.showToast(userDefault.IDLanguage() ?
                                    "Pelaporan kartu berhasil" : "Report card success");
                            activity.getUserData();
                        } else if (!response.body().getReturnCode().equals("01") ||
                                !response.body().getReturnCode().equals("09")) {
                            activity.showToast(response.body().getProcessMsg());
                            NSV.setVisibility(View.VISIBLE);
                        }
                    } else {
                        activity.
                                checkServer(String.valueOf(response.code()), response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<ReportLostResponse> call, Throwable t) {
                if (getActivity() != null && isAdded() && t != null) {
                    activity.restFailure(t);
                    NSV.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    // Get Card Balance from Server
    private void getCardBalance(final String card) {
        if (!activity.checkConnection()) return;
        if (apiService == null) apiService = activity.getApiService();

        balance = null;
        btConfirm.setVisibility(View.GONE);

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
                    if (!(t instanceof IOException)) showBalance();
                }
            }
        });
    }


    /*Override Func*/
    @Override
    public void onClick(View view, int position) {
        setCardView(adapter.getCard(position));

        ivBtCard.animate().rotation(0).start();
        ivCard.setVisibility(View.INVISIBLE);
        rv.setVisibility(View.GONE);
    }
}
