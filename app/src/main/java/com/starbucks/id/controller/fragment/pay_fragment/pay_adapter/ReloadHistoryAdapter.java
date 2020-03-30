package com.starbucks.id.controller.fragment.pay_fragment.pay_adapter;

/**
 * Created by Angga N P on 3/27/2018.
 */

import android.content.Context;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.starbucks.id.R;
import com.starbucks.id.controller.activity.MainActivity;
import com.starbucks.id.controller.fragment.pay_fragment.FragmentReload;
import com.starbucks.id.helper.UserDefault;
import com.starbucks.id.helper.utils.DataUtil;
import com.starbucks.id.model.pay.CardReloadHistory;
import com.starbucks.id.rest.ApiClient;

import java.util.List;


public class ReloadHistoryAdapter extends RecyclerView.Adapter<ReloadHistoryAdapter.ContactVH> {

    private List<CardReloadHistory> trx;
    private Context context;
    private int rowLayout;
    private Picasso picasso;
    private UserDefault userDefault;

    public ReloadHistoryAdapter(List<CardReloadHistory> rmm, int rowLayout, Context context) {
        this.trx = rmm;
        this.context = context;
        this.rowLayout = rowLayout;
        this.picasso = Picasso.with(context);
        this.userDefault = ((MainActivity) context).getUserDefault();
    }

    @Override
    public ReloadHistoryAdapter.ContactVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ContactVH(view);
    }

    @Override
    public void onBindViewHolder(final ContactVH holder, final int position) {
        final CardReloadHistory tr = trx.get(position);

        holder.tvVa.setText(tr.getVa());
        holder.tvTs.setText(DataUtil.getDate(tr.getDate(), 0));

        if (userDefault.IDLanguage()){
            holder.stvVa.setText("Nomor Virtual Account");
        }

        switch (tr.getStatus().toLowerCase()){
            case "pending":
                holder.tvStatus.setText("Waiting Payment");
                break;
            case "expired":
                holder.tvStatus.setText("Expired");
                break;
            case "cancelled":
                holder.tvStatus.setText("Cancelled");
                break;
            default:
                holder.tvStatus.setText("Success");
                break;
        }

        picasso.load(ApiClient.INSTANCE.getBASE_URL_END() + tr.getBankImage()).into(holder.ivLogo, new Callback() {
            @Override
            public void onSuccess() {
               holder.ivLogo.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError() {
                holder.ivLogo.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return trx.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ContactVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView  tvTs, tvVa, stvVa, tvStatus;
        ImageView ivLogo;

        private ContactVH(View v) {
            super(v);
            tvVa = v.findViewById(R.id.tvVa);
            stvVa = v.findViewById(R.id.stvVa);
            tvTs = v.findViewById(R.id.btnStart);
            tvStatus = v.findViewById(R.id.tvStatus);
            ivLogo = v.findViewById(R.id.ivLogo);
            picasso = Picasso.with(context);

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Bundle argums = new Bundle();
            argums.putParcelable("data", trx.get(getAdapterPosition()));
            if (context instanceof MainActivity)((MainActivity)context).cFragmentWithBundle(new FragmentReload(), argums);
        }
    }
}


