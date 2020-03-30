package com.starbucks.id.controller.fragment.home_fragment.profile_tabs.reward_fragment.adapter;

/**
 * Created by Angga N P on 3/27/2018.
 */

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.starbucks.id.R;
import com.starbucks.id.controller.activity.MainActivity;
import com.starbucks.id.helper.UserDefault;
import com.starbucks.id.model.reward.UserOfferModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class RewardAdapter extends RecyclerView.Adapter<RewardAdapter.ContactVH> {

    private final Picasso picasso;
    private List<UserOfferModel> rm;
    private Context context;
    private int rowLayout, left;
    private RewardAdapter.ItemClickListener clickListener;
    private UserDefault userDefault;
    private String tier;

    public RewardAdapter(List<UserOfferModel> rmm, String tier, int rowLayout, Context context) {
        this.tier = tier;
        this.rm = rmm;
        this.context = context;
        this.rowLayout = rowLayout;
        this.picasso = ((MainActivity) context).getPicasso();
        this.userDefault = ((MainActivity) context).getUserDefault();
    }

    @Override
    public ContactVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ContactVH(view);
    }

    @Override
    public void onBindViewHolder(final ContactVH holder, final int position) {
        final UserOfferModel us = rm.get(position);

        if (tier.equals("green")) {
            holder.iv.setBackgroundResource(R.drawable.vgr_opt_transparent);
        } else {
            holder.iv.setBackgroundResource(R.drawable.vgo_opt_transparent);
        }

        holder.tvTitle.setText(us.getName());
        holder.tvDetail.setText(us.getDescription());

        try {
            if (us.getRedemptionStartDate() != null && us.getRedemptionStartDate().length() > 19)
                holder.tvTSS.setText((userDefault.IDLanguage() ?
                        "Hadiah diterima " : "Reward earned " ) +
                        getDate(us.getRedemptionStartDate().substring(0, 19)));

            if (us.getRedemptionEndDate() != null && us.getRedemptionEndDate().length() > 19)
                holder.tvTS.setText((userDefault.IDLanguage() ?
                        "Hadiah berakhir " : "Reward expires " ) +
                        getDate(us.getRedemptionEndDate().substring(0, 19)));
        }catch (Exception e){
            e.printStackTrace();
            holder.tvTSS.setText(userDefault.IDLanguage() ? "Tanggal tidak benar" : "Date is not valid");
            holder.tvTS.setText(userDefault.IDLanguage() ? "Tanggal tidak benar" : "Date is not valid");
        }

    }

    @Override
    public int getItemCount() {
        return rm.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setClickListener(RewardAdapter.ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    private String getDate(String s) {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        s = s.replace("T", " ");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date newDate = null;
        try {
            newDate = format.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        format = new SimpleDateFormat("dd MMM yyyy hh:mm a");
        if (newDate != null) return format.format(newDate);
        else return userDefault.IDLanguage() ? "Tanggal tidak benar" : "Date is not valid";
    }

    public interface ItemClickListener {
        void onClick(View view, int position);
    }

    public class ContactVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvTitle, tvDetail, tvTS, tvTSS;
        public ImageView iv;

        public ContactVH(View v) {
            super(v);
            tvTitle = v.findViewById(R.id.detailDescription);
            tvDetail = v.findViewById(R.id.tvTraFro);
            tvTS = v.findViewById(R.id.tvTS);
            tvTSS = v.findViewById(R.id.tvTSS);
            iv = v.findViewById(R.id.iv);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) clickListener.onClick(v, getAdapterPosition());
        }
    }

}


