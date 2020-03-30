package com.starbucks.id.controller.fragment.pay_fragment;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.fragment.app.Fragment;
import androidx.core.widget.NestedScrollView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.starbucks.id.R;
import com.starbucks.id.controller.activity.MainActivity;
import com.starbucks.id.helper.UserDefault;
import com.starbucks.id.helper.utils.DataUtil;
import com.starbucks.id.helper.utils.PopUpUtil;
import com.starbucks.id.model.pay.CardReloadHistory;
import com.starbucks.id.model.pay.ResponseCardBalance;
import com.starbucks.id.model.pay.ResponseOpenTrx;
import com.starbucks.id.model.pay.ResponseReloadCard;
import com.starbucks.id.rest.ApiClient;
import com.starbucks.id.rest.ApiInterface;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.CLIPBOARD_SERVICE;


/*
 * Created by Angga N P on 4/23/2018.
 */

public class FragmentReload extends Fragment implements View.OnClickListener {

    private TextView tvCardNo, tvBalance, tvTS, tvAmmount;
    private Button bt100, bt200, bt300, bt400, bt500, btOther,
            bt600, bt700, bt800, bt900, bt1000, btReload;

    private ImageButton ibRefresh;
    private ImageView ivCard;
    private ProgressBar pbBalance;
    private NestedScrollView NSV;

    private UserDefault userDefault;
    private int top = 0;

    private ResponseReloadCard responseReloadCard;
    private ResponseOpenTrx responseOpenTrx;

    private String card_number, balance, card_image;
    private ResponseCardBalance sBalance;
    private Call<ResponseReloadCard> callReload;
    private ApiInterface apiService;
    private WebView webPayment;

    //Open TRX View
    private TextView tvCard, tvRA, tvVA,
            tvET, tvTT, tvTT2, tvStatus, tvNotes,
            tvNotes1, tvNotes2, tvTimer, stvTimer, stvVA;
    private TextView stvRA;
    private ImageView ivLogo;
    private Button btCancel, btCopy;

    private NestedScrollView NSVOpenContainer;

    private Boolean his = false;
    private CardReloadHistory hisData;

    private ClipboardManager clipboard;

    private MainActivity activity;

    public FragmentReload() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = ((MainActivity)getActivity());
        if (activity != null) userDefault = activity.getUserDefault();

        Bundle args = getArguments();
        if (args != null) {
            if (args.getParcelable("data") != null){
                hisData = args.getParcelable("data");
                his = true;
            }else {
                card_number = args.getString("card_number", "");
                card_image = args.getString("card_image", "");
                balance = args.getString("balance", "0");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_pay_reload, container, false);

        tvCardNo = rootView.findViewById(R.id.tvCardNo);
        tvBalance = rootView.findViewById(R.id.tvBalance);
        tvTS = rootView.findViewById(R.id.tvTS);
        tvAmmount = rootView.findViewById(R.id.tvAmmount);

        NSV = rootView.findViewById(R.id.NSVRootContainer);
        ibRefresh = rootView.findViewById(R.id.ibRefresh);
        pbBalance = rootView.findViewById(R.id.pbBalance);
        ivCard = rootView.findViewById(R.id.ivCard);
        webPayment = rootView.findViewById(R.id.wv);

        bt100 = rootView.findViewById(R.id.bt100);
        bt200 = rootView.findViewById(R.id.bt200);
        bt300 = rootView.findViewById(R.id.bt300);
        bt400 = rootView.findViewById(R.id.bt400);
        bt500 = rootView.findViewById(R.id.bt500);
        bt600 = rootView.findViewById(R.id.bt600);
        bt700 = rootView.findViewById(R.id.bt700);
        bt800 = rootView.findViewById(R.id.bt800);
        bt900 = rootView.findViewById(R.id.bt900);
        bt1000 = rootView.findViewById(R.id.bt1000);
        btOther = rootView.findViewById(R.id.btOther);
        btReload = rootView.findViewById(R.id.btReload);

        ibRefresh.setOnClickListener(this);
        bt100.setOnClickListener(this);
        bt200.setOnClickListener(this);
        bt300.setOnClickListener(this);
        bt400.setOnClickListener(this);
        bt500.setOnClickListener(this);
        bt600.setOnClickListener(this);
        bt700.setOnClickListener(this);
        bt800.setOnClickListener(this);
        bt900.setOnClickListener(this);
        bt1000.setOnClickListener(this);
        btOther.setOnClickListener(this);
        btReload.setOnClickListener(this);

        //Open TRX View
        tvCard = rootView.findViewById(R.id.tvCard);
        tvRA = rootView.findViewById(R.id.tvRa);
        tvVA = rootView.findViewById(R.id.tvVA);
        tvET = rootView.findViewById(R.id.tvET);
        tvTimer = rootView.findViewById(R.id.tvTimer);
        stvTimer = rootView.findViewById(R.id.stvTimer);
        stvVA = rootView.findViewById(R.id.stvVA);
        tvTT = rootView.findViewById(R.id.tvTT);
        tvTT2 = rootView.findViewById(R.id.tvTT2);
        tvStatus = rootView.findViewById(R.id.tvStatus);
        tvNotes = rootView.findViewById(R.id.tvNotes);
        tvNotes1 = rootView.findViewById(R.id.tvNotes1);
        tvNotes2 = rootView.findViewById(R.id.tvNotes2);
        stvRA = rootView.findViewById(R.id.stvRa);
        ivLogo = rootView.findViewById(R.id.ivLogo);
        btCopy = rootView.findViewById(R.id.btCopy);
        btCancel = rootView.findViewById(R.id.btCancel);
        NSVOpenContainer = rootView.findViewById(R.id.NSVOpenContainer);
        btReload.setText(userDefault.IDLanguage() ? "ISI ULANG":"TOP UP");
        if (!his) {
            activity.
                    setToolbarTitle(userDefault.IDLanguage() ?
                            getString(R.string.f_pay_reload_id) : getString(R.string.f_pay_reload));
            checkReload();
        }
        else {
            activity.setToolbarTitle(userDefault.IDLanguage() ?
                    getString(R.string.reload_history_id) : getString(R.string.reload_history));
            setHisTrx();
        }

        activity.enableNavigationIcon();

        return rootView;
    }

    /*UI Controller*/
    public void setBaseView() {
        top = 0;
        NSV.setVisibility(View.VISIBLE);

        if (userDefault.IDLanguage()) {
            tvAmmount.setText(R.string.reload_amm_id);
            btOther.setText(R.string.other_id);
        }

        setDefaultView();
    }

    private void setOpentTrxView(){
        tvCard.setText(DataUtil.maskCard(card_number));

        tvRA.setText(DataUtil.currencyFormat(
                responseOpenTrx.getAmount().substring(0, responseOpenTrx.getAmount().indexOf("."))));
        tvVA.setText(responseOpenTrx.getVirtualAccount());

        tvET.setText((userDefault.IDLanguage()? getString(R.string.Please_paid_before_id) :
                getString(R.string.Please_paid_before)) + " " +
                DataUtil.getDate(responseOpenTrx.getExpiredTime(), 2));
        tvTT.setText(DataUtil.getDate(responseOpenTrx.getTransactionTime(), 0));
        tvTT2.setText(DataUtil.getDate(responseOpenTrx.getTransactionTime(), 1));
        tvStatus.setText(R.string.status_waiting_payment);

        new CountDownTimer(DataUtil.getExpTime(responseOpenTrx.getServerTime(), responseOpenTrx.getExpiredTime()), 500) {
            public void onTick(long millisUntilFinished) {
                String text = String.format(Locale.getDefault(),
                        "%02d : %02d : %02d",
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished) % 60,
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60,
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60);
                tvTimer.setText(text);
            }

            public void onFinish() {
                if (getActivity() != null) {
                    tvTimer.setText(R.string.empty_time);
                    tvStatus.setText(R.string.expired);

                    tvET.setText((userDefault.IDLanguage()? getString(R.string.expired_on_id) :
                            getString(R.string.expired_on)) +
                            DataUtil.getDate(responseOpenTrx.getExpiredTime(), 2));
                }
            }

        }.start();

        tvTimer.setVisibility(View.VISIBLE);
        stvTimer.setVisibility(View.VISIBLE);

        Picasso.with(getActivity()).load(ApiClient.INSTANCE.getBASE_URL_END() + responseOpenTrx.getBankImage()).into(ivLogo, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {
                ivLogo.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError() {
                ivLogo.setVisibility(View.INVISIBLE);
            }
        });


        btCopy.setVisibility(View.VISIBLE);
        btCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clipboard == null ) clipboard = (ClipboardManager) getActivity().getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText(getString(R.string.label), tvVA.getText());
                clipboard.setPrimaryClip(clip);
                PopUpUtil.sShortToast(getActivity(),
                        userDefault.IDLanguage()? getString(R.string.text_copy_id) : getString(R.string.text_copy));
            }
        });

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sDialog(responseOpenTrx.getOrderId());
            }
        });
        btCancel.setVisibility(View.VISIBLE);

        if (userDefault.IDLanguage()) {
            btCopy.setText(getString(R.string.copy));
            stvRA.setText(getString(R.string.total));
            stvVA.setText(getString(R.string.va_id));
            tvNotes.setText(getString(R.string.note_id));
            tvNotes1.setText(getString(R.string.note_id_1));
            tvNotes2.setText(getString(R.string.note_id_2));
            btCancel.setText(R.string.cancel_trx_btn);
        }

        NSVOpenContainer.setVisibility(View.VISIBLE);
    }

    private void setHisTrx(){
        tvCard.setText(DataUtil.maskCard(hisData.getCard()));

        tvRA.setText(DataUtil.currencyFormat(hisData.getAmount()));
        tvVA.setText(hisData.getVa());

        tvTT.setText(DataUtil.getDate(hisData.getDate(), 0));
        tvTT2.setText(DataUtil.getDate(hisData.getDate(), 1));

        switch (hisData.getStatus().toLowerCase()){
            case "pending":
                tvET.setText((userDefault.IDLanguage()?
                        getString(R.string.Please_paid_before_id) : getString(R.string.Please_paid_before)) +
                        DataUtil.getDate(hisData.getExpiredTime(), 2));

                tvStatus.setText(getString(R.string.status_waiting_payment));

                tvTimer.setVisibility(View.VISIBLE);
                stvTimer.setVisibility(View.VISIBLE);

                new CountDownTimer(DataUtil.getExpTime(hisData.getServerTime(), hisData.getExpiredTime()), 500) {
                    public void onTick(long millisUntilFinished) {
                        String text = String.format(Locale.getDefault(),
                                "%02d : %02d : %02d",
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished) % 60,
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60,
                                TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60);
                        tvTimer.setText(text);
                    }

                    public void onFinish() {
                        if (getActivity() != null) {
                            tvTimer.setText(getString(R.string.empty_time));
                            tvStatus.setText(getString(R.string.expired));

                            tvET.setText((userDefault.IDLanguage()?
                                    getString(R.string.expired_on_id) : getString(R.string.expired_on)) +
                                    " " + DataUtil.getDate(hisData.getExpiredTime(), 2));
                        }
                    }

                }.start();

                btCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sDialog(hisData.getOrderId());
                    }
                });
                btCancel.setVisibility(View.VISIBLE);

                break;
            case "expired":
                tvET.setText((userDefault.IDLanguage()?
                        getString(R.string.expired_on_id) : getString(R.string.expired_on)) +
                        " " + DataUtil.getDate(hisData.getExpiredTime(), 2));

                tvTimer.setText("00:00:00");
                tvStatus.setText(R.string.status_expired);

                tvTimer.setVisibility(View.VISIBLE);
                stvTimer.setVisibility(View.VISIBLE);
                break;

            case "cancelled":
                tvET.setText((userDefault.IDLanguage()?
                        getString(R.string.transaction_cancelled_id) : getString(R.string.transaction_cancelled)));

                tvStatus.setText(R.string.status_canceled);

                tvTimer.setVisibility(View.GONE);
                stvTimer.setVisibility(View.GONE);
                break;

            default:
                tvET.setText(userDefault.IDLanguage()?
                        getString(R.string.payment_complete_id) : getString(R.string.payment_complete));
                tvStatus.setText(R.string.status_success);
                tvTimer.setVisibility(View.GONE);
                stvTimer.setVisibility(View.GONE);
                break;
        }


        btCopy.setVisibility(View.VISIBLE);
        btCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clipboard == null ) clipboard = (ClipboardManager) getActivity().getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText(getString(R.string.label), tvVA.getText());
                clipboard.setPrimaryClip(clip);
                PopUpUtil.sShortToast(getActivity(),
                        userDefault.IDLanguage()? getString(R.string.text_copy_id) : getString(R.string.text_copy));
            }
        });

        if (userDefault.IDLanguage()) {
            btCopy.setText(R.string.copy);
            stvRA.setText(R.string.total);
            stvVA.setText(R.string.va_id);
            tvNotes.setText(R.string.note_id);
            tvNotes1.setText(R.string.note_id_1);
            tvNotes2.setText(R.string.note_id_2);
        }

        NSVOpenContainer.setVisibility(View.VISIBLE);

        Picasso.with(getActivity()).load(ApiClient.INSTANCE.getBASE_URL_END() + hisData.getBankImage()).into(ivLogo, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {
                ivLogo.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError() {
                ivLogo.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void setDefaultView() {
        ivCard.setVisibility(View.INVISIBLE);

        tvCardNo.setText(DataUtil.maskCard(card_number));
        tvBalance.setText(DataUtil.currencyFormat(balance));
        Picasso.with(getActivity()).load(ApiClient.INSTANCE.getCARDS_IMG_URL() + card_image)
                .transform(new RoundedCornersTransformation(10, 0))
                .into(ivCard, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        ivCard.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError() {
                        Picasso.with(getActivity()).load(ApiClient.INSTANCE.getIMG_DEFAULT()).into(ivCard);
                        Picasso.with(getActivity()).load(ApiClient.INSTANCE.getIMG_DEFAULT()).into(ivCard);
                        ivCard.setVisibility(View.VISIBLE);
                    }
                });

        tvTS.setText((!userDefault.IDLanguage() ?
                getString(R.string.as_of) : getString(R.string.as_of_id)) +
                " " + DataUtil.getCurTS());

    }

    private void setWebView(String url){
        if (getActivity() != null && isAdded()) {
            webPayment.setVisibility(View.VISIBLE);

            webPayment.getSettings().setJavaScriptEnabled(true);
            webPayment.getSettings().setLoadWithOverviewMode(true);
            webPayment.getSettings().setUseWideViewPort(true);

            webPayment.loadUrl(url);
            webPayment.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    if (url.toLowerCase().contains("/closesnap")) {
                        webPayment.setVisibility(View.GONE);
                        checkReload();
                    }

                    if (userDefault.IDLanguage()) {
                        // Force ID Language
                        webPayment.loadUrl("javascript:(function() {" +
                                "if(!document.getElementById('switch').checked){document.getElementById('switch').click();}" +
                                "})()");
                    }else{
                        // Force EN Language
                        webPayment.loadUrl("javascript:(function() {" +
                                "if(document.getElementById('switch').checked){document.getElementById('switch').click();}" +
                                "})()");
                    }
                }
            });
        }
    }

    private void setDefaultBt() {
        bt100.setBackgroundColor(getResources().getColor(R.color.black_dop));
        bt200.setBackgroundColor(getResources().getColor(R.color.black_dop));
        bt300.setBackgroundColor(getResources().getColor(R.color.black_dop));
        bt400.setBackgroundColor(getResources().getColor(R.color.black_dop));
        bt500.setBackgroundColor(getResources().getColor(R.color.black_dop));

        bt600.setBackgroundColor(getResources().getColor(R.color.black_dop));
        bt700.setBackgroundColor(getResources().getColor(R.color.black_dop));
        bt800.setBackgroundColor(getResources().getColor(R.color.black_dop));
        bt900.setBackgroundColor(getResources().getColor(R.color.black_dop));
        bt1000.setBackgroundColor(getResources().getColor(R.color.black_dop));

        btOther.setBackgroundColor(getResources().getColor(R.color.black_dop));

    }

    private void displayBt() {
        btOther.setVisibility(View.VISIBLE);
        bt400.setVisibility(View.VISIBLE);
        bt500.setVisibility(View.VISIBLE);
        bt600.setVisibility(View.VISIBLE);
        bt700.setVisibility(View.VISIBLE);
        bt800.setVisibility(View.VISIBLE);
        bt900.setVisibility(View.VISIBLE);
        bt1000.setVisibility(View.VISIBLE);
    }

    private void hideBalance() {
        tvBalance.setVisibility(View.INVISIBLE);
        ibRefresh.setVisibility(View.INVISIBLE);
        pbBalance.setVisibility(View.VISIBLE);
    }

    private void showBalance() {
        tvBalance.setVisibility(View.VISIBLE);
        ibRefresh.setVisibility(View.VISIBLE);
        pbBalance.setVisibility(View.INVISIBLE);
    }

    private Boolean isValid() {
        if (top == 0) {
            activity.showToast(userDefault.IDLanguage() ?
                    getString(R.string.reload_ammount_id) : getString(R.string.reload_ammount));
            return false;
        }

        return true;
    }

    private void sDialog(final String orderID){
        final Dialog dialog = new Dialog(getActivity());
        dialog.setCanceledOnTouchOutside(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_notif_2_button);

        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawableResource(android.R.color.transparent);


        TextView tvHeader = dialog.findViewById(R.id.tvHeader);
        TextView tvContent = dialog.findViewById(R.id.tvNotif);
        Button btOk = dialog.findViewById(R.id.btOk);
        Button btCancel = dialog.findViewById(R.id.btCancel);

        tvHeader.setText(!userDefault.IDLanguage()?
                getString(R.string.warning) :
                getString(R.string.warning_id));
        tvContent.setText(!userDefault.IDLanguage()?
                getString(R.string.sure_cancel) :
                getString(R.string.sure_cancel_id));
        btCancel.setText(
                !userDefault.IDLanguage()?getString(R.string.no) : getString(R.string.no_id));
        btOk.setText(
                !userDefault.IDLanguage()?getString(R.string.yes) : getString(R.string.yes_id));

        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelReload(orderID);
                dialog.dismiss();
            }
        });
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    /*REST CALL*/
    //Check Open Trx
    private void checkReload() {
        if (!activity.checkConnection()) return;
        if (apiService == null) apiService = activity.getApiService();

        Call<ResponseOpenTrx> callCheckTrx = apiService.checkOpenReload("checkreloadVA",
                activity.genSBUX(
                        "uid=" + userDefault.getEmail() +
                                "&card_number=" + card_number));

        callCheckTrx.enqueue(new Callback<ResponseOpenTrx>() {
            @Override
            public void onResponse(Call<ResponseOpenTrx> call, Response<ResponseOpenTrx> response) {
                if (getActivity() != null) {
                    if (response.isSuccessful() && response.body() != null) {
                        responseOpenTrx = response.body();

                        if (responseOpenTrx.getReturnCode().equals("00")){
                            // NO OPEN TRX EXISTED
                            setBaseView();
                        }else {
                            // OPEN TRX EXISTED
                            setOpentTrxView();
                        }

                    } else {
                        activity.
                                checkServer(String.valueOf(response.code()), response.message());
                        NSV.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseOpenTrx> call, Throwable t) {
                if (getActivity() != null && isAdded() && t != null) {
                    activity.restFailure(t);
                    NSV.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    //Request Reload
    private void newReload() {
        if (!activity.checkConnection()) return;
        if (apiService == null) apiService = activity.getApiService();

        NSV.setVisibility(View.GONE);

        callReload = apiService.newReload("reloadVA",
                activity.genSBUX(
                        "uid=" + userDefault.getEmail() +
                                "&card_current_balance=" + (balance) +
                                "&card_number=" + card_number +
                                "&amount=" + (String.valueOf(top) + "000") +
                                "&device= M" +
                                "&first_name=" + activity.userResponseModel.getUser().getFirstName())
                );

        callReload.enqueue(new Callback<ResponseReloadCard>() {
            @Override
            public void onResponse(Call<ResponseReloadCard> call, Response<ResponseReloadCard> response) {
                if (getActivity() != null) {
                    if (response.isSuccessful() && response.body() != null) {
                        responseReloadCard = response.body();

                        if (responseReloadCard.getReturnCode().equals("00"))
                            setWebView(responseReloadCard.getRedirectUrl());
                        else {
                            setBaseView();
                            PopUpUtil.sLongToast(getActivity(), responseReloadCard.getProcessMsg());
                        }
                    } else {
                        activity.
                                checkServer(String.valueOf(response.code()), response.message());
                        PopUpUtil.sLongToast(getActivity(), response.message());
                        NSV.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseReloadCard> call, Throwable t) {
                if (getActivity() != null && isAdded() && t != null) {
                    activity.restFailure(t);
                    NSV.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    //Cancel Reload
    private void cancelReload(String orderID) {
        if (!activity.checkConnection()) return;
        if (apiService == null) apiService = activity.getApiService();
        NSVOpenContainer.setVisibility(View.GONE);

        callReload = apiService.cancelReload("cancelVA",
                activity.genSBUX(
                        "uid=" + userDefault.getEmail() +
                                "&order_id=" + orderID +
                                "&sm_token=" + userDefault.getAccToken()
                ));


        callReload.enqueue(new Callback<ResponseReloadCard>() {
            @Override
            public void onResponse(Call<ResponseReloadCard> call, Response<ResponseReloadCard> response) {
                if (getActivity() != null) {
                    if (response.isSuccessful() && response.body() != null) {
                        if (response.body().getReturnCode().equals("00")) {
                            getActivity().onBackPressed();
                            PopUpUtil.sLongToast(getActivity(), response.body().getProcessMsg());
                        } else {
                            PopUpUtil.sLongToast(getActivity(), response.body().getProcessMsg());
                            NSVOpenContainer.setVisibility(View.VISIBLE);
                        }
                    } else {
                        activity.
                                checkServer(String.valueOf(response.code()), response.message());
                        PopUpUtil.sLongToast(getActivity(), response.message());
                        NSVOpenContainer.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseReloadCard> call, Throwable t) {
                if (getActivity() != null && isAdded() && t != null) {
                    activity.restFailure(t);
                    NSVOpenContainer.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    // Get Card Balance
    private void getCardBalance(final String card) {
        if (!activity.checkConnection()) return;
        if (apiService == null) apiService = activity.getApiService();

        hideBalance();
        Call<ResponseCardBalance> callBalance = apiService.getCardBalance("balance",
                activity.genSBUX(
                        "uid=" + userDefault.getEmail() +
                                "&card=" + card
                ));

        callBalance.enqueue(new Callback<ResponseCardBalance>() {
            @Override
            public void onResponse(Call<ResponseCardBalance> call, Response<ResponseCardBalance> response) {
                if (getActivity() != null) {
                    if (response.isSuccessful() && response.body() != null) {
                        sBalance = response.body();

                        if (activity.successResponse(sBalance.getReturnCode(), response.body().getProcessMsg())) {
                            if (sBalance.getDetails() != null) {
                                if (sBalance.getDetails().getCardBalance() != null) {
                                    tvBalance.setText(DataUtil.currencyFormat(sBalance.getDetails().getCardBalance()));
                                } else tvBalance.setText(R.string.balance_error);
                            } else tvBalance.setText(R.string.balance_error);

                            showBalance();
                        }
                    } else {
                        activity.checkServer(String.valueOf(response.code()), response.message());
                        tvBalance.setText(R.string.balance_error);
                        showBalance();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseCardBalance> call, Throwable t) {
                if (getActivity() != null && isAdded() && t != null) {
                    activity.restFailure(t);
                    tvBalance.setText(R.string.balance_error);
                    showBalance();
                }
            }
        });
    }

    /*Override Func*/
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibRefresh:
                getCardBalance(card_number);
                break;

            case R.id.bt100:
                setDefaultBt();
                bt100.setBackgroundColor(getResources().getColor(R.color.greenPrimary));
                top = 100;
                break;
            case R.id.bt200:
                setDefaultBt();
                bt200.setBackgroundColor(getResources().getColor(R.color.greenPrimary));
                top = 200;
                break;
            case R.id.bt300:
                setDefaultBt();
                bt300.setBackgroundColor(getResources().getColor(R.color.greenPrimary));
                top = 300;
                break;
            case R.id.bt400:
                setDefaultBt();
                bt400.setBackgroundColor(getResources().getColor(R.color.greenPrimary));
                top = 400;
                break;
            case R.id.bt500:
                setDefaultBt();
                bt500.setBackgroundColor(getResources().getColor(R.color.greenPrimary));
                top = 500;
                break;

            case R.id.bt600:
                setDefaultBt();
                bt600.setBackgroundColor(getResources().getColor(R.color.greenPrimary));
                top = 600;
                break;
            case R.id.bt700:
                setDefaultBt();
                bt700.setBackgroundColor(getResources().getColor(R.color.greenPrimary));
                top = 700;
                break;
            case R.id.bt800:
                setDefaultBt();
                bt800.setBackgroundColor(getResources().getColor(R.color.greenPrimary));
                top = 800;
                break;
            case R.id.bt900:
                setDefaultBt();
                bt900.setBackgroundColor(getResources().getColor(R.color.greenPrimary));
                top = 900;
                break;
            case R.id.bt1000:
                setDefaultBt();
                bt1000.setBackgroundColor(getResources().getColor(R.color.greenPrimary));
                top = 1000;
                break;

            case R.id.btOther:
                setDefaultBt();
                displayBt();
                break;

            case R.id.btReload:
                if (isValid()) {
//                    int amount = Integer.parseInt(String.valueOf(top) + getString(R.string.triple_zero));
//                    if (Double.parseDouble(balance) + amount > 2000000) {
//                        String message = !userDefault.IDLanguage()
//                                ? getString(R.string.en_cant_reload_exc_bal)
//                                : getString(R.string.id_cant_reload_exc_bal);
//                        activity.showToast(message);
//                    } else {
                        newReload();
//                    }
                }

                break;
        }
    }
}
