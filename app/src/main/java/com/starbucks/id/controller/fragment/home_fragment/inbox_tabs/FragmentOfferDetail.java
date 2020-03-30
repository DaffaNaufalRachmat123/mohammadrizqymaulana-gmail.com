package com.starbucks.id.controller.fragment.home_fragment.inbox_tabs;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.squareup.picasso.Picasso;
import com.starbucks.id.R;
import com.starbucks.id.controller.activity.MainActivity;
import com.starbucks.id.controller.extension.extendedView.ProgressBarAnimation;
import com.starbucks.id.helper.UserDefault;
import com.starbucks.id.model.inbox.DetailOfferResponseModel;
import com.starbucks.id.model.user.UserResponseModel;
import com.starbucks.id.rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentOfferDetail extends Fragment {
    private TextView detailDescription, btnVM, TvVm, offerTitle, offerDesc,tvStatus,tvProgress,tvnickName;
    private ImageView iv,starsLogo,bookLogo,backgroundId;
    private LinearLayout ly;
    private ProgressBar pb;
    private UserDefault userDefault;
    private ApiInterface apiService;
    private MainActivity activity;
    private UserResponseModel userResponseModel;
    private DetailOfferResponseModel dataModel;
    private ProgressBar progressSA;
    private ConstraintLayout CLTC;
    private Picasso picasso;
    private SwipeRefreshLayout sw;
    private boolean isClicked;
    public FragmentOfferDetail( ){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userDefault = ((MainActivity)getActivity()).getUserDefault();
        activity = ((MainActivity)getActivity());
        if (activity != null) userDefault = activity.getUserDefault();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offers_detail, container, false);
        detailDescription = view.findViewById(R.id.tvHeader);
        btnVM = view.findViewById(R.id.btnVM);
        TvVm = view.findViewById(R.id.tvViewMore);
        tvStatus = view.findViewById(R.id.tvStatus);
        tvProgress = view.findViewById(R.id.tvProgress);
        offerTitle = view.findViewById(R.id.offerTitel);
        offerDesc = view.findViewById(R.id.offerDesc);
        tvnickName = view.findViewById(R.id.tvTitle);
        iv = view.findViewById(R.id.iv);
        starsLogo = view.findViewById(R.id.starsLogo);
        bookLogo = view.findViewById(R.id.bookLogo);
        pb = view.findViewById(R.id.pb);

        progressSA = view.findViewById(R.id.progressHorizontal);
        backgroundId = view.findViewById(R.id.backgroundId);
        CLTC = view.findViewById(R.id.CLTC);
        picasso = Picasso.with(getActivity());
        sw = view.findViewById(R.id.SWRoot);
        sw.setEnabled(false);
        setData();

        return view;
    }



    public void setData() {
        if (activity.getUser() != null) {
//            userResponseModel.getUser().getFirstName();
            userResponseModel = activity.getUser();
            if (dataModel == null) getDetailOffer();
            else setBaseView();

        } else activity.getUserData();
    }

    private void setBaseView(){
        sw.setRefreshing(false);
        ((MainActivity) (getActivity())).setToolbarPN(
                userDefault.IDLanguage()? "Program Tracker" : "Program Tracker"
        );


//        2 = Spend Ammount 1000:500
//        1 = Transaction Count 1000:200
        if(dataModel.getData()!=null){
            tvnickName.setText("Hi "+userResponseModel.getUser().getFirstName()+",");
            if(dataModel.getData().getCampaign_type().equals("2")){
                CLTC.setVisibility(View.VISIBLE);
                progressSA.setVisibility(View.VISIBLE);
                bookLogo.setVisibility(View.VISIBLE);
                btnVM.setVisibility(View.VISIBLE);
                ConstraintSet set = new ConstraintSet();
                set.clone(CLTC);
                set.setDimensionRatio(backgroundId.getId(), "1000:500");
                set.applyTo(CLTC);
                ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams)backgroundId.getLayoutParams();
                marginParams.setMargins(0,0,0,0);


                if(dataModel.getData()!= null){

                    detailDescription.setText(dataModel.getData().getDescription());
                    TvVm.setText(dataModel.getData().getTerm_and_con());


                    tvStatus.setText(dataModel.getData().getPrecentage()+"%");

                    offerTitle.setText(dataModel.getData().getProgress_title_1());
                    offerDesc.setText(dataModel.getData().getProgress_title_2());
                    tvProgress.setText(dataModel.getData().getProgress_detail());
                    progressSA.setScaleY(2f);
                    progressSA.setMax(100);
                    ProgressBarAnimation anim = new ProgressBarAnimation(
                            progressSA, 0, Float.parseFloat(dataModel.getData().getPrecentage()));
                    anim.setDuration(2000);
                    progressSA.startAnimation(anim);

                    btnVM.setText(userDefault.IDLanguage()? "Tampilkan":"View More");
                    btnVM.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            isClicked = !isClicked;
                            if(isClicked){
                                btnVM.setText(userDefault.IDLanguage()? "Tutup":"View Less");
                                TvVm.setVisibility(View.VISIBLE);

                            }else{
                                btnVM.setText(userDefault.IDLanguage()? "Tampilkan":"View More");
                                TvVm.setVisibility(View.GONE);
                            }



                        }
                    });




                    if (dataModel.getData().getImage_detail() != null && !dataModel.getData().getImage_detail().isEmpty()) {
                        picasso.with(getActivity()).load(dataModel.getData().getStars_image()).into(starsLogo);
                        picasso.with(getActivity()).load(dataModel.getData().getBook_image()).into(bookLogo);
                        picasso.with(getActivity()).load(dataModel.getData().getImage_progress()).into(backgroundId);
//                        backgroundId.setScaleType(ImageView.ScaleType.FIT_XY);
                        picasso.with(getActivity()).load(dataModel.getData().getImage_detail())
                                .into(iv, new com.squareup.picasso.Callback() {
                                    @Override
                                    public void onSuccess() {
                                        iv.setVisibility(View.VISIBLE);
                                        pb.setVisibility(View.GONE);
                                    }

                                    @Override
                                    public void onError() {
                                        iv.setVisibility(View.VISIBLE);
                                        picasso.load(R.drawable.logo_sbux).into(iv);
                                        pb.setVisibility(View.GONE);
                                    }
                                });
                    }else {
//                        iv.setVisibility(View.GONE);
                        pb.setVisibility(View.GONE);
                    }
                }else{
                    getActivity().onBackPressed();
                }

            }
            else if(dataModel.getData().getCampaign_type().equals("1")){
                CLTC.setVisibility(View.VISIBLE);
                progressSA.setVisibility(View.GONE);
                bookLogo.setVisibility(View.GONE);
                btnVM.setVisibility(View.VISIBLE);
                ConstraintSet set = new ConstraintSet();
                set.clone(CLTC);
                set.setDimensionRatio(backgroundId.getId(), "1000:200");
                set.setVerticalBias(backgroundId.getId(),Float.parseFloat("0.8"));
                set.applyTo(CLTC);
                ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams)backgroundId.getLayoutParams();
                marginParams.setMargins(15,0,15,0);
                if(dataModel.getData()!= null){
                    detailDescription.setText(dataModel.getData().getDescription());
                    TvVm.setText(dataModel.getData().getTerm_and_con());

                    String first = dataModel.getData().getPrecentage();
                    SpannableString data=  new SpannableString(first);
                    data.setSpan(new RelativeSizeSpan(2f),0,1,0);
                    data.setSpan(new StyleSpan(Typeface.BOLD), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tvStatus.setText(data);
//                    tvStatus.setText(dataModel.getData().getPrecentage());
                    offerTitle.setText(dataModel.getData().getProgress_title_1());
                    offerDesc.setText(dataModel.getData().getProgress_title_2());
                    tvProgress.setText(dataModel.getData().getProgress_detail());

                    btnVM.setText(userDefault.IDLanguage()? "Tampilkan":"View More");
                    btnVM.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(isClicked){
                                btnVM.setText(userDefault.IDLanguage()? "Tampilkan":"View More");
                                TvVm.setVisibility(View.GONE);
                            }else{
                                btnVM.setText(userDefault.IDLanguage()? "Tutup":"View Less");
                                TvVm.setVisibility(View.VISIBLE);
                            }

                            isClicked = !isClicked;

                        }
                    });
                    if (dataModel.getData().getImage_detail() != null && !dataModel.getData().getImage_detail().isEmpty()) {
                        Picasso.with(getActivity()).load(dataModel.getData().getStars_image()).into(starsLogo);
                        Picasso.with(getActivity()).load(dataModel.getData().getBook_image()).into(bookLogo);
                        Picasso.with(getActivity()).load(dataModel.getData().getImage_progress()).into(backgroundId);
                        Picasso.with(getActivity()).load(dataModel.getData().getImage_detail())
                                .into(iv, new com.squareup.picasso.Callback() {
                                    @Override
                                    public void onSuccess() {
                                        iv.setVisibility(View.VISIBLE);
                                        pb.setVisibility(View.GONE);
                                    }

                                    @Override
                                    public void onError() {
                                        iv.setVisibility(View.VISIBLE);
                                        picasso.load(R.drawable.logo_sbux).into(iv);
                                        pb.setVisibility(View.GONE);
                                    }
                                });
                    }else {
//                        iv.setVisibility(View.GONE);
                        pb.setVisibility(View.GONE);
                    }
                }else{
                    getActivity().onBackPressed();
                }
            }
        }else{
            getActivity().onBackPressed();
        }

        sw.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                CLTC.setVisibility(View.GONE);
//                progressSA.setVisibility(View.GONE);
//                bookLogo.setVisibility(View.GONE);
//                btnVM.setVisibility(View.GONE);
                getDetailOffer();
                sw.setRefreshing(false);
            }
        });

    }



    /*REST CALL*/
    // Get Message List Data
    public void getDetailOffer() {
        if (!activity.checkConnection()) return;
        if (apiService == null) apiService = activity.getApiService();

        pb.setVisibility(View.VISIBLE);

        Call<DetailOfferResponseModel> call = apiService.getOfferDetail(
                "campaign",
                activity.genSBUX( "email=" + userResponseModel.getUser().getEmail()+
                        "&sm_token=" + userDefault.getAccToken()+
                        "&lang=" + userDefault.getLanguage()+
                        "&card_number=" + userResponseModel.getUser().getExternalId()+
                        "&campaign_id_mcs=" +activity.getFragmentOfferDetailId()));

        call.enqueue(new Callback<DetailOfferResponseModel>() {
            @Override
            public void onResponse(Call<DetailOfferResponseModel> call, Response<DetailOfferResponseModel> response) {
                if (getActivity() != null) {
                    if (response.isSuccessful() && response.body() != null) {
                        if (activity.successResponse(response.body().getStatus(),response.body().getProcessMsg())) {
                            dataModel = response.body();
                            setBaseView();
                        }
                    } else {
                        activity.checkServer(String.valueOf(response.code()), response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<DetailOfferResponseModel> call, Throwable t) {
                if (getActivity() != null && isAdded())
                    activity.restFailure(t);
            }
        });

    }

}
