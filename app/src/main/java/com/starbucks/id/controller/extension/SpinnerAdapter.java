package com.starbucks.id.controller.extension;


/*
 * Created by Angga N P on 3/20/2018.
 */


import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.starbucks.id.R;
import com.starbucks.id.model.extension.EavModel;

import java.util.List;

public class SpinnerAdapter extends RecyclerView.Adapter<SpinnerAdapter.CVHolder> {

    private List<EavModel> filcards;
    private int rowLayout;
    private ItemClickListener clickListener;

    public SpinnerAdapter(List<EavModel> cards, int rowLayout, Context context) {
        this.filcards = cards;
        this.rowLayout = rowLayout;
    }

    @Override
    public SpinnerAdapter.CVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);

        return new SpinnerAdapter.CVHolder(view);
    }

    @Override
    public void onBindViewHolder(final SpinnerAdapter.CVHolder holder, final int position) {
        final EavModel cm = filcards.get(position);

        holder.tv.setText(cm.getValue());
    }

    @Override
    public int getItemCount() {
        return filcards.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public EavModel getDetail(int position) {
        return filcards.get(position);
    }

    public interface ItemClickListener {
        void onClick(View view, int position);
    }

    public class CVHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tv;

        CVHolder(View v) {
            super(v);
            tv = v.findViewById(R.id.tvTraFro);

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) clickListener.onClick(v, getAdapterPosition());
        }
    }
}
