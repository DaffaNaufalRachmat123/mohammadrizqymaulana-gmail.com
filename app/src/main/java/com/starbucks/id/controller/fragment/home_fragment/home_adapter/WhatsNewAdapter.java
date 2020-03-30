package com.starbucks.id.controller.fragment.home_fragment.home_adapter;

/**
 * Created by Angga N P on 3/27/2018.
 */

import android.content.Context;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.starbucks.id.R;
import com.starbucks.id.controller.activity.MainActivity;
import com.starbucks.id.controller.fragment.home_fragment.FragmentWhatsNewDetail;
import com.starbucks.id.helper.UserDefault;
import com.starbucks.id.model.extension.WhatsNewModel;
import com.starbucks.id.rest.ApiClient;

import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;


public class WhatsNewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<WhatsNewModel> rm;
    private Context context;
    private Picasso picasso;
    private UserDefault userDefault;
    private int operation_id = 0;

    public WhatsNewAdapter(List<WhatsNewModel> rmm, Context context, int op) {
        this.rm = rmm;
        this.context = context;
        this.userDefault = ((MainActivity) context).getUserDefault();
        this.operation_id = op;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return 1;
        else return (position % 2) + 2;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_inbox, parent, false);
        View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_inbox2, parent, false);
        View view3 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_inbox3, parent, false);
        switch (viewType) {
            case 1:
                return new VH1(view);
            case 2:
                return new VH2(view2);
            case 3:
                return new VH3(view3);

            default:
                return new VH1(view);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final WhatsNewModel rmm = rm.get(position);

        switch (holder.getItemViewType()) {
            case 1:
                final VH1 vh1 = (VH1) holder;

                if (operation_id == 0) {
                    if (rmm.getImageLandingUrl() != null) {
                        picasso.load(rmm.getImageLandingUrl()).into(vh1.iv, new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {
                                vh1.iv.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onError() {
                                vh1.iv.setVisibility(View.VISIBLE);
                                picasso.load(R.drawable.defaultpage_720_h).into(vh1.iv);
                            }
                        });
                    }
                    break;
                } else {
                    picasso.load(ApiClient.INSTANCE.getIMG_DEFAULT_WN()).into(vh1.iv, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            vh1.iv.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onError() {
                            vh1.iv.setVisibility(View.VISIBLE);
                            picasso.load(R.drawable.defaultpage_720_h)
                                    .into(vh1.iv);

                        }
                    });
                    break;
                }

            case 2:
                final VH2 vh2 = (VH2) holder;
                vh2.tvTitle.setText(userDefault.IDLanguage() ? rmm.getLandingTitleId() : rmm.getLandingTitleEn());
                vh2.tvSub.setText(userDefault.IDLanguage() ? rmm.getDetailContentId() : rmm.getDetailContentEn());

                picasso.load(rmm.getImageLandingUrl())
                        .transform(new CropCircleTransformation())
                        .into(vh2.iv, new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {
                                vh2.pb.setVisibility(View.GONE);
                                vh2.CLContent.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onError() {
                                vh2.pb.setVisibility(View.GONE);
                                vh2.CLContent.setVisibility(View.VISIBLE);
                                picasso.load(R.drawable.defaultpage_720_h)
                                        .transform(new CropCircleTransformation())
                                        .into(vh2.iv);
                            }
                        });
                break;

            case 3:
                final VH3 vh3 = (VH3) holder;
                vh3.tvTitle.setText(userDefault.IDLanguage() ? rmm.getLandingTitleId() : rmm.getLandingTitleEn());
                vh3.tvSub.setText(userDefault.IDLanguage() ? rmm.getDetailContentId() : rmm.getDetailContentEn());

                picasso.load(rmm.getImageLandingUrl())
                        .transform(new CropCircleTransformation())
                        .into(vh3.iv, new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {
                                vh3.pb.setVisibility(View.GONE);
                                vh3.CLContent.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onError() {
                                vh3.pb.setVisibility(View.GONE);
                                vh3.CLContent.setVisibility(View.VISIBLE);
                                picasso.load(R.drawable.defaultpage_720_h)
                                        .transform(new CropCircleTransformation())
                                        .into(vh3.iv);
                            }
                        });
                break;

        }

    }

    @Override
    public int getItemCount() {
        if (operation_id == 1) return 1;
        else {
            if (rm.size() > 5) return 5;
            else return rm.size();
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setClickListener(WhatsNewAdapter.ItemClickListener itemClickListener) {
        ItemClickListener clickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onClick(View view, int position);
    }

    public class VH1 extends RecyclerView.ViewHolder {
        public ImageView iv;
        public ProgressBar pb;

        public VH1(View v) {
            super(v);
            picasso = ((MainActivity) context).getPicasso();

            iv = v.findViewById(R.id.iv);
            pb = v.findViewById(R.id.pb);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment newFragment = FragmentWhatsNewDetail.newInstance(rm.get(getAdapterPosition()));
                    ((MainActivity) context).cFragmentWithBS(newFragment);
                }
            });
        }
    }

    public class VH2 extends RecyclerView.ViewHolder {
        public TextView tvTitle, tvSub;
        public ImageView iv;
        public ProgressBar pb;
        public ConstraintLayout CLContent;
        public Button bt;

        public VH2(View v) {
            super(v);
            picasso = ((MainActivity) context).getPicasso();

            tvTitle = v.findViewById(R.id.detailDescription);
            tvSub = v.findViewById(R.id.tvTraFro);
            iv = v.findViewById(R.id.iv);
            pb = v.findViewById(R.id.pb);
            CLContent = v.findViewById(R.id.CLContent);

            bt = v.findViewById(R.id.bt);
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment newFragment = FragmentWhatsNewDetail.newInstance(rm.get(getAdapterPosition()));
                    ((MainActivity) context).cFragmentWithBS(newFragment);
                }
            });
        }
    }

    public class VH3 extends RecyclerView.ViewHolder {
        public TextView tvTitle, tvSub;
        public ImageView iv;
        public ProgressBar pb;
        public ConstraintLayout CLContent;
        public Button bt;

        public VH3(View v) {
            super(v);

            picasso = ((MainActivity) context).getPicasso();

            tvTitle = v.findViewById(R.id.detailDescription);
            tvSub = v.findViewById(R.id.tvTraFro);
            iv = v.findViewById(R.id.iv);
            pb = v.findViewById(R.id.pb);
            CLContent = v.findViewById(R.id.CLContent);

            bt = v.findViewById(R.id.bt);
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment newFragment = FragmentWhatsNewDetail.newInstance(rm.get(getAdapterPosition()));
                    ((MainActivity) context).cFragmentWithBS(newFragment);
                }
            });
        }
    }
}


