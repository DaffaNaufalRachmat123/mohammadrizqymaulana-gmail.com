package com.starbucks.id.controller.fragment.pay_fragment.pay_adapter;


/*
 * Created by Angga N P on 3/20/2018.
 */


import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.starbucks.id.R;
import com.starbucks.id.controller.activity.MainActivity;
import com.starbucks.id.model.user.UserIdentifierModel;
import com.starbucks.id.rest.ApiClient;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CVHolder> implements Filterable {

    private final Picasso picasso;
    private List<UserIdentifierModel> cards;
    private List<UserIdentifierModel> filcards;
    private int rowLayout;
    private ItemClickListener clickListener;

    public CardAdapter(List<UserIdentifierModel> cards, int rowLayout, Context context) {
        this.cards = cards;
        this.filcards = cards;
        this.rowLayout = rowLayout;
        this.picasso = ((MainActivity) context).getPicasso();
    }

    @Override
    public CardAdapter.CVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);

        return new CardAdapter.CVHolder(view);
    }

    @Override
    public void onBindViewHolder(final CardAdapter.CVHolder holder, final int position) {
        final UserIdentifierModel cm = filcards.get(position);

        String str = cm.getExternalId();
        if (str != null && str.length() > 4)
            holder.number.setText("(" + str.substring(str.length() - 4) + ")"
            );

        picasso.load(ApiClient.INSTANCE.getCARDS_IMG_URL() + cm.getCardImage())
                .transform(new RoundedCornersTransformation(10, 0))
                .into(holder.ivCard, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        holder.ivCard.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError() {
                        picasso.load(ApiClient.INSTANCE.getIMG_DEFAULT()).into(holder.ivCard);
                        holder.ivCard.setVisibility(View.VISIBLE);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return filcards.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filcards = cards;
                } else {
                    List<UserIdentifierModel> filteredList = new ArrayList<>();
                    for (UserIdentifierModel row : cards) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getExternalId().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    filcards = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filcards;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filcards = (List<UserIdentifierModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public UserIdentifierModel getCard(int position) {
        return cards.get(position);
    }

    public interface ItemClickListener {
        void onClick(View view, int position);
    }

    public class CVHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView balance, number;
        public ImageView ivCard;

        CVHolder(View v) {
            super(v);
            balance = v.findViewById(R.id.tvBalance);
            number = v.findViewById(R.id.tvNumber);
            ivCard = v.findViewById(R.id.ivCard);

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) clickListener.onClick(v, getAdapterPosition());
        }
    }
}
