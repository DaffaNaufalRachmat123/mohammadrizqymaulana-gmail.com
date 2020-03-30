package com.starbucks.id.controller.fragment.home_fragment.profile_tabs.reward_fragment;

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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.starbucks.id.R;
import com.starbucks.id.controller.activity.MainActivity;
import com.starbucks.id.controller.extension.extendedView.ProgressBarAnimation;
import com.starbucks.id.controller.fragment.home_fragment.profile_tabs.reward_fragment.adapter.RewardAdapter;
import com.starbucks.id.helper.UserDefault;
import com.starbucks.id.model.reward.RewardModel;
import com.starbucks.id.model.user.UserResponseModel;
import com.starbucks.id.rest.ApiInterface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentRewards extends Fragment {

    private static final String TAG = Fragment.class.getSimpleName();

    private ProgressBar pb;
    private TextView tv, tvTitle, tvTier, tvCollect,
            tvRewards, tvMember, tvSub;
    private RecyclerView rvReward;

    private int operation_id = 0;
    private ImageView ivTier, iv;
    private Button btMore, btHistory;
    private NestedScrollView NSV;

    private RewardAdapter rewardAdapter;
    private UserDefault userDefault;
    private UserResponseModel userResponseModel;
    private RewardModel rewardModel;
    private ApiInterface apiService;
    private Call<RewardModel> callReward;

    private int reward_max_point, reward_size, reward_cur_point, reward_left,
            tierNext, cur_point = 0;

    private MainActivity activity;
    private FirebaseAnalytics mFirebaseAnalytics;
    public FragmentRewards() {
        // Required empty public constructor
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
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home_rewards, container, false);

        setHasOptionsMenu(true);

        tvRewards = rootView.findViewById(R.id.tvRewards);
        pb = rootView.findViewById(R.id.pb);
        tv = rootView.findViewById(R.id.tvTraFro);
        tvTitle = rootView.findViewById(R.id.detailDescription);
//        tvCollect = (TextView) rootView.findViewById(R.id.tvCollect);
        tvTier = rootView.findViewById(R.id.tvTier);
        tvMember = rootView.findViewById(R.id.tvMember);
        tvSub = rootView.findViewById(R.id.tvSub);
        rvReward = rootView.findViewById(R.id.rvHistory);
        ivTier = rootView.findViewById(R.id.ivTier);
        iv = rootView.findViewById(R.id.iv);
        btMore = rootView.findViewById(R.id.btMore);
        btHistory = rootView.findViewById(R.id.btHistory);
        NSV = rootView.findViewById(R.id.NSVContainer);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());
        Bundle params = new Bundle();
        params.putString("FragmentRewards", "Fragment Rewards");
        mFirebaseAnalytics.logEvent("AOS", params);

        setData();

        return rootView;
    }

    /*UI Controller*/
    public void setData() {
        userResponseModel = activity.getUser();
        if(userResponseModel.getUser()!=null){
            if (activity.getUser()!= null) {
                if (userResponseModel != null)
                    getReward();
            }
        }else{
            NSV.setVisibility(View.INVISIBLE);
            activity.getUserData();
        }
    }

    private void setDefaultView() {
        activity.setToolbarTitle(
                getString(R.string.reward_title));

        if (userDefault.IDLanguage()) {
            btMore.setText("Informasi Tentang Keuntungan");
            btHistory.setText("Lihat Riwayat");
            tvSub.setText("Reward dan Keuntungan");
        }

        NSV.setVisibility(View.VISIBLE);

        tierNext = userResponseModel.getUser().getNextTierPoints();

        reward_max_point = 100;
        reward_size = userResponseModel.getUser().getTotalOffer();

        if (!userResponseModel.getUser().getAvailablePoints()
                .toString().toLowerCase().equals("n/a"))
            reward_cur_point = userResponseModel.getUser().getAvailablePoints();
        else reward_cur_point = 0;

        reward_left = reward_max_point - reward_cur_point;

        switch (userResponseModel.getUser().getTier().toLowerCase()) {
            case "green":
                pb.setProgressDrawable(getResources().getDrawable(R.drawable.pb_green));
                tvTier.setText("Green Level");
                tvTier.setTextColor(getResources().getColor(R.color.greenPrimary));
                ivTier.setImageDrawable(getResources().getDrawable(R.drawable.star_green));

                break;

            case "gold":
                pb.setProgressDrawable(getResources().getDrawable(R.drawable.pb_gold));
                tvTier.setText("Gold Level");
                tvTier.setTextColor(getResources().getColor(R.color.goldAccent));
                ivTier.setImageDrawable(getResources().getDrawable(R.drawable.star_gold));

                tv.setTextColor(getResources().getColor(R.color.black_dop));
                iv.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_black_star));

                break;

            default:
                pb.setProgressDrawable(getResources().getDrawable(R.drawable.pb_green));
                tvTier.setText("Green Level");
                tvTier.setTextColor(getResources().getColor(R.color.greenPrimary));
                ivTier.setImageDrawable(getResources().getDrawable(R.drawable.star_green));
                break;
        }

        rvReward.setLayoutManager(new LinearLayoutManager(getContext()));
        rewardAdapter = new RewardAdapter(rewardModel.getPayload().getUserOffers(),
                userResponseModel.getUser().getTier().toLowerCase(),
                R.layout.item_reward_display, getContext());
        rvReward.setAdapter(rewardAdapter);

        btMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.cFragmentWithBS(new FragmentRewardBenefit());
            }
        });

        btHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.cFragmentWithBS(new FragmentRewardHistory());
            }
        });


        setProgress();
    }


    private void setProgress() {

        pb.setMax(reward_max_point);
        tvRewards.setText(String.valueOf(reward_size));
        tvMember.setText((userDefault.IDLanguage() ? "Terdaftar sejak - " :
                "Member since - ") + getDate(userResponseModel.getUser().getRegisteredAt()));

        tv.setText(String.valueOf(reward_cur_point));
        tvTitle.setText(userDefault.IDLanguage() ?
                String.valueOf(reward_max_point - reward_cur_point) + " Bintang sampai Reward selanjutnya" :
                String.valueOf(reward_max_point - reward_cur_point) + " Stars to next Reward");

        pb.setMax(reward_max_point);
        ProgressBarAnimation anim = new ProgressBarAnimation(
                pb, 0, Float.parseFloat(String.valueOf(reward_cur_point)));
        anim.setDuration(2000);
        pb.startAnimation(anim);
    }

    private String getDate(String s) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date newDate = null;
        try {
            newDate = format.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        format = new SimpleDateFormat("dd MMM yyyy");
        if (newDate != null) return format.format(newDate);
        else return userDefault.IDLanguage() ?
                "Tanggal tidak valid" : "Date not valid";
    }

    /*REST Call*/
    private void getReward() {
        if (!activity.checkConnection()) return;
        if (apiService == null) apiService = activity.getApiService();

        callReward = apiService.getCardReward("get_rewards",
                activity.genSBUX("email=" + userDefault.getEmail()));

        callReward.enqueue(new Callback<RewardModel>() {
            @Override
            public void onResponse(Call<RewardModel> call, Response<RewardModel> response) {

                if (getActivity() != null) {
                    if (response.isSuccessful() && response.body() != null) {
                        rewardModel = response.body();
                        if (activity.successResponse(rewardModel.getStatus(), rewardModel.getProcessMsg())) {
                            setDefaultView();
                        }
                    } else {
                        activity.checkServer(String.valueOf(response.code()), response.message());
                    }
                }


            }

            @Override
            public void onFailure(Call<RewardModel> call, Throwable t) {
                if (getActivity() != null && isAdded())
                    activity.restFailure(t);
            }
        });
    }
}
