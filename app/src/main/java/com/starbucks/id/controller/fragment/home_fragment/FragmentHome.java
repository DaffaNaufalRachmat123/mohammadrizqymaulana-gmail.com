package com.starbucks.id.controller.fragment.home_fragment;

/**
 * Created by Angga N P on 3/13/2018.
 */

import android.app.Dialog;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.starbucks.id.R;
import com.starbucks.id.controller.activity.MainActivity;
import com.starbucks.id.controller.extension.extendedView.PreventCopas;
import com.starbucks.id.controller.extension.extendedView.ProgressBarAnimation;
import com.starbucks.id.controller.fragment.home_fragment.home_adapter.WhatsNewAdapter;
import com.starbucks.id.controller.fragment.home_fragment.inbox_tabs.FragmentInbox;
import com.starbucks.id.controller.fragment.home_fragment.profile_tabs.FragmentProfile;
import com.starbucks.id.controller.fragment.home_fragment.profile_tabs.FragmentSetPassCode;
import com.starbucks.id.controller.fragment.home_fragment.profile_tabs.reward_fragment.FragmentRewards;
import com.starbucks.id.helper.UserDefault;
import com.starbucks.id.helper.utils.DataUtil;
import com.starbucks.id.helper.utils.DialogUtil;
import com.starbucks.id.helper.utils.PopUpUtil;
import com.starbucks.id.model.extension.WhatsNewModel;
import com.starbucks.id.model.inbox.ResponseMessageCount;
import com.starbucks.id.model.login.LoginResponseModel;
import com.starbucks.id.model.user.UserResponseModel;
import com.starbucks.id.rest.ApiInterface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentHome extends Fragment {
    private ImageView ivTier;
    private TextView tvPoint, tvTier, tvLeft, tvRewardLeft, tvReward, tvCounter;
    private ProgressBar pb;
    private int reward_max_point, reward_size, tierNext, reward_cur_point;
    private RecyclerView rv;
    private Button btProfile, btInbox, btPay, btPromo;

    private UserDefault userDefault;
    private List<WhatsNewModel> whatsNew;
    private ApiInterface apiService;
    private UserResponseModel userResponseModel;
    private ConstraintLayout CLBase;
    private SwipeRefreshLayout srRoot;

    private Dialog pDialog;
    private EditText etPromo;
    private TextView stvPromo;

    public ResponseMessageCount msgCount;

    private MainActivity activity;
    private FirebaseAnalytics mFirebaseAnalytics;

    public FragmentHome() {
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
        final View rootView = inflater.inflate(R.layout.fragment_home_base, container, false);
        btProfile = rootView.findViewById(R.id.btProfile);
        btInbox = rootView.findViewById(R.id.btInbox);
        btPromo = rootView.findViewById(R.id.btPromo);
        btPay = rootView.findViewById(R.id.btPayHome);
        ivTier = rootView.findViewById(R.id.ivTier);
        pb = rootView.findViewById(R.id.pb);

        tvPoint = rootView.findViewById(R.id.detailDescription);
        tvReward = rootView.findViewById(R.id.tvRewards);
        tvRewardLeft = rootView.findViewById(R.id.tvRewardLeft);
        tvTier = rootView.findViewById(R.id.tvTier);
        tvLeft = rootView.findViewById(R.id.tvLeft);
        tvCounter = rootView.findViewById(R.id.tvCount);
        rv = rootView.findViewById(R.id.rvHistory);
        CLBase = rootView.findViewById(R.id.CLBase);
        srRoot = rootView.findViewById(R.id.srRoot);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());
        Bundle params = new Bundle();
        params.putString("FragmentHome", "Fragment Home");
        mFirebaseAnalytics.logEvent("AOS", params);
        activity.toolbar.setVisibility(View.VISIBLE);
        activity.tabLayout.setVisibility(View.VISIBLE);
        setData();
        return rootView;
    }


    /*UI Controller*/
    public void setData() {
        srRoot.setRefreshing(false);
        if (activity.getUser() != null) {
            userResponseModel = activity.getUser();
            setDefaultView();
        } else {
            CLBase.setVisibility(View.INVISIBLE);
            btPay.setVisibility(View.INVISIBLE);
            activity.getUserData();
        }
    }


    private void setDefaultView() {
        if(userResponseModel.getUser()==null){
            CLBase.setVisibility(View.INVISIBLE);
            btPay.setVisibility(View.INVISIBLE);
            activity.getUserData();
        }else{
            CLBase.setVisibility(View.VISIBLE);
            btPay.setVisibility(View.VISIBLE);

            String level = (userResponseModel.getUser().getTier().toLowerCase().equals("gold")) ?
                    "<font color='#c2a661'>Gold Level</font>" :
                    "<font color='#00653e'>Green Level</font>";

            btPay.setText(userDefault.IDLanguage() ? "Bayar" : "PAY");

            tierNext = userResponseModel.getUser().getNextTierPoints();

            reward_max_point = 100;
            reward_size = userResponseModel.getUser().getTotalOffer();
            reward_cur_point = userResponseModel.getUser().getAvailablePoints();


            activity.setHomeToolbar(getTime() + ",",
                    userResponseModel.getUser().getFirstName(), level);

            btProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.cFragmentWithBS(new FragmentProfile());
                }
            });

            btInbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                activity.cFragmentWithBS(new FragmentInbox());
//                    activity.cFragmentWithBS(new FragmentMes sage());
                }
            });

            btPromo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sDialogPromo();
//                sPlanner(getActivity());
//                sProgressHorizontal(getActivity());
//                    Crashlytics.getInstance().crash();
//                    activity.cFragmentWithBS(new FragmentOfferDetail());
//                    activity.cFragmentWithBS(new FragmentTestSMS());
                }
            });

            btPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.payPopUp(userResponseModel.getUser().getUserProfile().getDefaultCard());
                }
            });

            srRoot.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    CLBase.setVisibility(View.INVISIBLE);
                    activity.getUserData();
                    srRoot.setRefreshing(false);
                }
            });

            setBanner();
        }


    }


    private void setBanner() {
        tvPoint.setText(String.valueOf(reward_cur_point));
        pb.setMax(reward_max_point);

        tvPoint.setText(String.valueOf(reward_cur_point));

        tvRewardLeft.setText(userDefault.IDLanguage() ?
                (reward_max_point - reward_cur_point) + " Stars sampai Reward selanjutnya" :
                (reward_max_point - reward_cur_point) + " Stars to next Reward");

        if (tierNext > 0) {
            if (userResponseModel.getUser().getTier().toLowerCase().equals("green")) {
                tvLeft.setText(userDefault.IDLanguage() ?
                        tierNext + " Stars sampai Gold Tier" :
                        tierNext + " Stars to Gold Tier");
            } else {
                tvLeft.setText(userDefault.IDLanguage() ?
                        "Kumpulkan kembali " + tierNext +
                                " Stars s/d \n" + getDate(userResponseModel.getUser().getTierEnteredAt()) +
                                "\n untuk tetap di status Gold di tahun berikutnya" :
                        "Earn " + tierNext +
                                " Stars by \n" + getDate(userResponseModel.getUser().getTierEnteredAt()) +
                                "\n for another year of Gold");
            }
        } else {
            tvLeft.setText(userDefault.IDLanguage() ?
                    "Anda telah mendapatkan satu tahun Gold lagi" :
                    "You have earned another year of Gold "
            );
        }

        ProgressBarAnimation anim = new ProgressBarAnimation(
                pb, 0, Float.parseFloat(String.valueOf(reward_cur_point)));
        anim.setDuration(2000);
        pb.startAnimation(anim);

        tvReward.setText(String.valueOf(reward_size));
        tvTier.setText(String.valueOf(reward_max_point));

        switch (userResponseModel.getUser().getTier().toLowerCase()) {
            case "green":
                pb.setProgressDrawable(getResources().getDrawable(R.drawable.pb_green));
                tvTier.setTextColor(getResources().getColor(R.color.greenPrimary));
                ivTier.setImageDrawable(getResources().getDrawable(R.drawable.star_green));
                break;

            case "gold":
                pb.setProgressDrawable(getResources().getDrawable(R.drawable.pb_gold));
                tvTier.setTextColor(getResources().getColor(R.color.goldAccent));
                ivTier.setImageDrawable(getResources().getDrawable(R.drawable.star_gold));
                break;

            default:
                pb.setProgressDrawable(getResources().getDrawable(R.drawable.pb_green));
                tvTier.setTextColor(getResources().getColor(R.color.greenPrimary));
                ivTier.setImageDrawable(getResources().getDrawable(R.drawable.star_green));
                break;
        }

        ivTier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("operation_id", 1);
                activity.cFragmentWithBundle(new FragmentRewards(), bundle);
            }
        });

        setWhatsNew();
    }

    private void setWhatsNew() {
        if (activity.whatsNew != null) {
            whatsNew = activity.whatsNew;
            setMessage();
        } else {
            if (getActivity() != null) getWhatsNewData();
        }
    }

    private void setMessage() {
        rv.setVisibility(View.VISIBLE);
        btPay.setVisibility(View.VISIBLE);
        rv.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        if (whatsNew.size() > 0) {
            WhatsNewAdapter adapter = new WhatsNewAdapter(whatsNew, getActivity(), 0);
            rv.setAdapter(adapter);
        } else {
            WhatsNewAdapter adapter = new WhatsNewAdapter(whatsNew, getActivity(), 1);
            rv.setAdapter(adapter);
        }

        setCounter();
    }

    private void setCounter() {
        if (activity.msgCount != null) {
            msgCount = activity.msgCount;
            setCounterView();
        } else {
            if (getActivity() != null) getMsgCount();
        }
    }

    private void setCounterView() {
        btInbox.setEnabled(true);
        if (msgCount != null &&
                msgCount.getTotalNew() != null &&
                msgCount.getTotalNew()>0) {
            tvCounter.setText(String.valueOf(msgCount.getTotalNew()));
            tvCounter.setVisibility(View.VISIBLE);
        }else{
            tvCounter.setVisibility(View.GONE);
        }
    }

    private String getTime() {
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay >= 0 && timeOfDay < 12) {
            return !userDefault.IDLanguage()
                    ? activity.getString(R.string.en_morning)
                    : activity.getString(R.string.id_morning);
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            return !userDefault.IDLanguage()
                    ? activity.getString(R.string.en_afternoon)
                    : activity.getString(R.string.id_afternoon);
        } else if (timeOfDay >= 16 && timeOfDay < 20) {
            return !userDefault.IDLanguage()
                    ? activity.getString(R.string.en_evening)
                    : activity.getString(R.string.id_evening);
        } else if (timeOfDay >= 20 && timeOfDay < 24) {
            return !userDefault.IDLanguage()
                    ? activity.getString(R.string.en_night)
                    : activity.getString(R.string.id_night);
        } else {
            return !userDefault.IDLanguage()
                    ? activity.getString(R.string.en_morning)
                    : activity.getString(R.string.id_morning);
        }
    }

    private String getDate(String s) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date newDate = null;
        Calendar cal = Calendar.getInstance();
        try {
            newDate = format.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (newDate != null) {
            cal.setTime(newDate);
            cal.add(Calendar.DAY_OF_MONTH, 364);
            newDate = cal.getTime();

            format = new SimpleDateFormat("dd-MMM-yyyy");
            return format.format(newDate);
        } else {
            return userDefault.IDLanguage()
                    ? activity.getString(R.string.id_date_not_valid)
                    : activity.getString(R.string.en_date_not_valid);
        }
    }

    /******************DIALOG VIEW******************/
    private void sDialogPromo(){
        if (pDialog == null) {
            pDialog = new Dialog(getActivity());
            pDialog.setCancelable(false);
            pDialog.setCanceledOnTouchOutside(false);
            pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            pDialog.setContentView(R.layout.dialog_promo_code);

            Window window = pDialog.getWindow();
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setGravity(Gravity.CENTER);
            window.setBackgroundDrawableResource(android.R.color.transparent);
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

            stvPromo = pDialog.findViewById(R.id.stvPromo);
            etPromo = pDialog.findViewById(R.id.etPromo);
            etPromo.setCustomSelectionActionModeCallback(new PreventCopas());

            Button btApply = pDialog.findViewById(R.id.btApply);
            ImageButton ibClose = pDialog.findViewById(R.id.ibClose);

            ibClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (pDialog != null && pDialog.isShowing()) pDialog.dismiss();
                }
            });

            btApply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (etPromo.getText().toString().isEmpty()) {
                        PopUpUtil.sLongToast(getActivity(), userDefault.IDLanguage()
                                ? activity.getString(R.string.id_warning_fill_promotion_code)
                                : activity.getString(R.string.en_warning_fill_promotion_code));
                    } else {
                        sendCode(etPromo.getText().toString());
                    }
                }
            });


        }

        if (userDefault.IDLanguage()) {
            stvPromo.setText(activity.getString(R.string.id_promo_code_hint));
            etPromo.setHint(activity.getString(R.string.id_ecode));
        } else {
            stvPromo.setText(activity.getString(R.string.en_promo_code_hint));
            etPromo.setHint(activity.getString(R.string.en_ecode));
        }

        pDialog.show();
    }

    private void sNotDialog(String header, String message) {
        if (pDialog != null && pDialog.isShowing()) pDialog.dismiss();

        final Dialog dialogNotif = new Dialog(getActivity());
        dialogNotif.setCanceledOnTouchOutside(false);
        dialogNotif.setCancelable(false);
        dialogNotif.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogNotif.setContentView(R.layout.dialog_notif_pc);

        Window window = dialogNotif.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawableResource(android.R.color.transparent);

        TextView stvNotif = dialogNotif.findViewById(R.id.stvNotif);
        TextView tvNotif = dialogNotif.findViewById(R.id.tvNotif);
        TextView tvBonus = dialogNotif.findViewById(R.id.tvBonus);
        ImageView ibClose = dialogNotif.findViewById(R.id.ibClose);

        tvNotif.setText(header);
        tvBonus.setText(message);

        if (userDefault.IDLanguage()) stvNotif.setText(activity.getString(R.string.id_congratulation));

        ibClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogNotif.dismiss();
            }
        });

        dialogNotif.show();
    }


    /******************REST Call******************/
    // Get Whats New Data
    private void getWhatsNewData() {
        if (!activity.checkConnection()) return;
        if (apiService == null) apiService = activity.getApiService();

        Call<List<WhatsNewModel>> callWhatsNew = apiService.getWhatsNew(
                DataUtil.encrypt(getString(R.string.rest_content_auth)));

        callWhatsNew.enqueue(new Callback<List<WhatsNewModel>>() {
            @Override
            public void onResponse(Call<List<WhatsNewModel>> call, Response<List<WhatsNewModel>> response) {
                if (getActivity() != null) {
                    if (response.isSuccessful() && response.body() != null) {
                        whatsNew = response.body();
                        activity.whatsNew = whatsNew;
                        setMessage();
                    } else {
                        activity.checkServer(String.valueOf(response.code()), response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<WhatsNewModel>> call, Throwable t) {
                if (getActivity() != null && isAdded()) activity.restFailure(t);
            }
        });
    }

    // Get Message Counter
    // Note this is not an important event to block UI / UX when Error / Failed Happens
    private void getMsgCount() {
        if (!activity.checkConnection()) return;
        if (apiService == null) apiService = activity.getApiService();

        btInbox.setEnabled(false);

        Call<ResponseMessageCount> call =  apiService.getMessageCount("totalInbox",
                activity.genSBUX("email=" + userDefault.getEmail() +
                        "&sm_token=" + userDefault.getAccToken() +
                        "&card_number=" + userResponseModel.getUser().getExternalId())
        );

        call.enqueue(new Callback<ResponseMessageCount>() {
            @Override
            public void onResponse(Call<ResponseMessageCount> call, Response<ResponseMessageCount> response) {
                if (getActivity() != null) {
                    if (response.isSuccessful() && response.body().getReturnCode().equals("00")) {
                        msgCount = response.body();
                        activity.msgCount = response.body();
                        setCounterView();
                    } else btInbox.setEnabled(true);
                }
            }

            @Override
            public void onFailure(Call<ResponseMessageCount> call, Throwable t) {
                btInbox.setEnabled(true);
            }
        });
    }

    // Send Promo Code
    public void sendCode(String code) {
        if (!activity.checkConnection()) return;
        if (apiService == null) apiService = activity.getApiService();

        if (pDialog != null && pDialog.isShowing()) pDialog.dismiss();

        DialogUtil.sProDialog(getActivity());

        UserResponseModel userResponseModel = activity.getUser();

        Call<LoginResponseModel> call = apiService.promo("promoCodeSM",
                activity.genSBUX(
                        "card_number=" + userResponseModel.getUser().getUserProfile().getDefaultCard() +
                                "&email=" + userDefault.getEmail() +
                                "&promocode=" + code)
        );

        call.enqueue(new Callback<LoginResponseModel>() {
            @Override
            public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {
                if (getActivity() != null) {
                    DialogUtil.hProDialog();
                    if (response.isSuccessful()) {
                        if (response.body().getReturnCode().equals("00")) {
                            if (response.body().getProcessMsg() != null){
                                etPromo.setText("");
                                sNotDialog(response.body().getProcessMsg(),
                                        response.body().getBonus());
                            }
                        } else {
                            if (response.body().getProcessMsg() != null) PopUpUtil
                                    .sLongToast(getActivity(), response.body().getProcessMsg());
                            sDialogPromo();
                        }
                    }else{
                        if (response.body().getProcessMsg() != null) PopUpUtil
                                .sLongToast(getActivity(), response.body().getProcessMsg());
                        sDialogPromo();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponseModel> call, Throwable t) {
                if (getActivity() != null && isAdded()) {
                    DialogUtil.hProDialog();
                    activity.restFailure(t);
                }

            }
        });

    }

    /******************Override Func*****************************/
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (pDialog != null && pDialog.isShowing()) pDialog.dismiss();
    }
}
