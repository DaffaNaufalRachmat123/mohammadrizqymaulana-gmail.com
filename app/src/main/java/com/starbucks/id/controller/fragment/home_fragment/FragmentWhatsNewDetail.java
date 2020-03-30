package com.starbucks.id.controller.fragment.home_fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.starbucks.id.R;
import com.starbucks.id.controller.activity.MainActivity;
import com.starbucks.id.helper.UserDefault;
import com.starbucks.id.model.extension.WhatsNewModel;


public class FragmentWhatsNewDetail extends Fragment {
    private final static String TAG = Fragment.class.getSimpleName();
    private static WhatsNewModel whatsNewModel;
    private ImageView iv;
    private TextView tvTitle, tvDetail;
    private UserDefault userDefault;
    private Picasso picasso;

    public FragmentWhatsNewDetail() {
        // Required empty public constructor
    }

    public static FragmentWhatsNewDetail newInstance(WhatsNewModel wm) {

        FragmentWhatsNewDetail fragment = new FragmentWhatsNewDetail();
        whatsNewModel = wm;

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userDefault = ((MainActivity) getActivity()).getUserDefault();
        picasso = ((MainActivity) getActivity()).getPicasso();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_whats_new_detail, container, false);

        iv = view.findViewById(R.id.iv);
        tvTitle = view.findViewById(R.id.detailDescription);
        tvDetail = view.findViewById(R.id.tvTS);

        setView();

        return view;
    }


    private void setView() {
        ((MainActivity) getActivity()).setToolbarTitle(getString(R.string.whats_new_title));
//        ((MainActivity) getActivity()).enableNavigationIcon();

        tvTitle.setText(userDefault.IDLanguage() ?
                whatsNewModel.getLandingTitleId() :
                whatsNewModel.getLandingTitleEn());

        tvDetail.setText(userDefault.IDLanguage() ?
                whatsNewModel.getDetailContentId() :
                whatsNewModel.getDetailContentEn());

        picasso.load(whatsNewModel.getImageContentUrl())
                .into(iv, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        iv.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError() {
                        picasso.load(R.drawable.defaultpage_720_h).into(iv);
                        iv.setVisibility(View.VISIBLE);
                    }
                });
    }
}
