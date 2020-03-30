package com.starbucks.id.controller.fragment.home_fragment.profile_tabs.reward_fragment.adapter;

/**
 * Created by Angga N P on 3/27/2018.
 */

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.starbucks.id.R;
import com.starbucks.id.model.reward_history.RewardResultModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class RewardHistoryAdapter extends RecyclerView.Adapter<RewardHistoryAdapter.ContactVH> {

    private List<RewardResultModel> rm;
    private int rowLayout;

    public RewardHistoryAdapter(List<RewardResultModel> rmm, int rowLayout) {
        this.rm = rmm;
        this.rowLayout = rowLayout;
    }

    @Override
    public ContactVH onCreateViewHolder(ViewGroup parent, int viewType) {
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_list_item, parent, false);
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ContactVH(view);
    }

    @Override
    public void onBindViewHolder(final ContactVH holder, final int position) {
        final RewardResultModel rmm = rm.get(position);
        holder.tvTitle.setText(rmm.getEventStreamPayload().getEventTypeName());

        if (rmm.getTimestamp() != null && rmm.getTimestamp().length() > 10) {
            holder.tvOpt.setText(genTime(rmm.getTimestamp().substring(0, 10)));
            holder.tvTs.setText(genDate(rmm.getTimestamp().substring(0, 10)));
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

    private String genDate(String unix) {
        Date dt = new Date(Long.parseLong(unix) * 1000);
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd MMM yyyy");

        return sdf2.format(dt);
    }

    private String genTime(String unix) {
        Date dt = new Date(Long.parseLong(unix) * 1000);
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");

        return sdf2.format(dt);
    }

    public class ContactVH extends RecyclerView.ViewHolder {
        public TextView tvTitle, tvOpt, tvTs, tvAmmount;
        public ImageView iv;

        public ContactVH(View v) {
            super(v);
            tvTitle = v.findViewById(R.id.detailDescription);
            tvOpt = v.findViewById(R.id.tvOpt);
            tvTs = v.findViewById(R.id.btnStart);
            tvAmmount = v.findViewById(R.id.tvAmmount);
            iv = v.findViewById(R.id.iv);
        }

    }
}


