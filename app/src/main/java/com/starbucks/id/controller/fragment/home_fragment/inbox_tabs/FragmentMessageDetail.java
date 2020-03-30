package com.starbucks.id.controller.fragment.home_fragment.inbox_tabs;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.starbucks.id.R;
import com.starbucks.id.controller.activity.MainActivity;
import com.starbucks.id.helper.UserDefault;
import com.starbucks.id.helper.utils.DataUtil;
import com.starbucks.id.model.inbox.MessageModel;

public class FragmentMessageDetail extends Fragment {

    private static MessageModel msg;

    private TextView tvTitle, tvTs, tvBody;
    private ImageView iv;
    private ProgressBar pb;
    private UserDefault userDefault;

    public FragmentMessageDetail() {
        // Required empty public constructor
    }

    public static FragmentMessageDetail newInstance(MessageModel message) {
        msg = message;

        return new FragmentMessageDetail();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userDefault = ((MainActivity)getActivity()).getUserDefault();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.message_item_detail, container, false);

        tvTitle = view.findViewById(R.id.detailDescription);
        tvTs = view.findViewById(R.id.btnStart);
        tvBody = view.findViewById(R.id.tvBody);
        iv = view.findViewById(R.id.iv);
        pb = view.findViewById(R.id.pb);

        setBaseView();

        return view;
    }

    private void setBaseView(){
        ((MainActivity) (getActivity())).setToolbarPN(
                userDefault.IDLanguage()? "Detail Pesan" : "Message Detail"
        );

        if (msg != null){
            tvTitle.setText(msg.getMsgSubject());
            String message = msg.getMsgDtl().replace("\\n","\n");
            tvBody.setText(message);

            if (msg.getCreatedAt() != null && !msg.getCreatedAt().isEmpty()) {
                String time = msg.getCreatedAt().
                        replace("T", " ").
                        replace("Z", "");
                tvTs.setText(DataUtil.getDate(time, 0));
            } else tvTs.setVisibility(View.INVISIBLE);


            if (msg.getMsgImage() != null && !msg.getMsgImage().isEmpty()) {
                Picasso.with(getActivity()).load(msg.getMsgImage())
                        .into(iv, new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {
                                iv.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onError() {
                                iv.setVisibility(View.GONE);
                                pb.setVisibility(View.GONE);
                            }
                        });
            }else {
                iv.setVisibility(View.GONE);
                pb.setVisibility(View.GONE);
            }
        }else{
            getActivity().onBackPressed();
        }
    }

}
