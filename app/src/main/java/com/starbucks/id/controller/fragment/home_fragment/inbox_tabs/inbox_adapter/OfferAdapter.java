package com.starbucks.id.controller.fragment.home_fragment.inbox_tabs.inbox_adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.starbucks.id.R;
import com.starbucks.id.controller.activity.MainActivity;
import com.starbucks.id.helper.UserDefault;
import com.starbucks.id.helper.utils.DataUtil;
import com.starbucks.id.model.inbox.MessageModel;
import com.starbucks.id.model.inbox.OfferModel;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.ContactVH> {

    public List<OfferModel> offerList;
    private Context context;
    private int rowLayout;
    private Picasso picasso;
    private OfferAdapter.ItemClickListener clickListener;
    private UserDefault userDefault;

    public OfferAdapter(List<OfferModel> offerList, int rowLayout, Context context) {
        this.offerList = offerList;
        this.context = context;
        this.rowLayout = rowLayout;
        this.userDefault = ((MainActivity) context).getUserDefault();
        this.picasso = Picasso.with(context);
    }


    public class ContactVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView iv;
        public ProgressBar pb;

        private ContactVH(View v) {
            super(v);
            iv = v.findViewById(R.id.iv);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) clickListener.onClick(v, getAdapterPosition());
        }
    }

    @Override
    public OfferAdapter.ContactVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new OfferAdapter.ContactVH(view);
    }

    @Override
    public void onBindViewHolder(final OfferAdapter.ContactVH holder, final int position) {
        final OfferModel item = offerList.get(position);if (item.getCampaign_img() != null && !item.getCampaign_img().isEmpty()) {
            picasso.load(item.getCampaign_img()).
                    into(holder.iv, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            holder.iv.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onError() {
                            picasso.load(R.drawable.logo_sbux).
                                    into(holder.iv);
                            holder.iv.setVisibility(View.VISIBLE);
                        }
                    });
        }
        else {
            picasso.load(R.drawable.logo_sbux).
                    into(holder.iv);
            holder.iv.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public int getItemCount() {
        return offerList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setClickListener(OfferAdapter.ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public OfferModel getdata(int pos){
        return offerList.get(pos);
    }

    public interface ItemClickListener {
        void onClick(View view, int position);
    }

}