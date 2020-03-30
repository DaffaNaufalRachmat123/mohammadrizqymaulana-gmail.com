package com.starbucks.id.controller.fragment.home_fragment.profile_tabs.profile_adapter;

/**
 * Created by Angga N P on 3/27/2018.
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

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.starbucks.id.R;
import com.starbucks.id.controller.activity.MainActivity;
import com.starbucks.id.helper.UserDefault;
import com.starbucks.id.helper.utils.DataUtil;
import com.starbucks.id.model.trx.ActivityResultModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class ActivityItemAdapter extends RecyclerView.Adapter<ActivityItemAdapter.ContactVH> implements Filterable {

    private List<ActivityResultModel> trx;
    private List<ActivityResultModel> filTrx;
    private Context context;
    private int rowLayout;
    private Picasso picasso;
    private ActivityItemAdapter.ItemClickListener clickListener;
    private Calendar cal;
    private UserDefault userDefault;
    private Gson gson;

    public ActivityItemAdapter(List<ActivityResultModel> rmm, int rowLayout, Context context) {
        this.trx = rmm;
        this.filTrx = rmm;
        this.context = context;
        this.rowLayout = rowLayout;
        this.cal = Calendar.getInstance();
        this.userDefault = ((MainActivity) context).getUserDefault();
        this.gson = new Gson();
    }

    @Override
    public ActivityItemAdapter.ContactVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ContactVH(view);
    }

    @Override
    public void onBindViewHolder(final ContactVH holder, final int position) {
        final ActivityResultModel tr = filTrx.get(position);
        holder.tvTitle.setText(userDefault.IDLanguage() ? tr.getTransactionNameId() : tr.getTransactionNameEn());
        if(tr.getStarsEarn().equals("")){
            holder.starsEarned.setVisibility(View.INVISIBLE);
        }else{
            float stars = Float.parseFloat(tr.getStarsEarn());
//            String starsEarmed = String.format("%.2f",stars);
            holder.starsEarned.setVisibility(View.VISIBLE);
            holder.starsEarned.setText(userDefault.IDLanguage()? "Stars "
                    +tr.getStarsEarn():"Stars Earned "+tr.getStarsEarn());
        }

        if(tr.getBonusStars().equals("")){
            holder.bonusStars.setVisibility(View.INVISIBLE);
        }else{
            float bonus = Float.parseFloat(tr.getBonusStars());
//            String bonusStars = String.format("%.2f", bonus);
            holder.bonusStars.setVisibility(View.VISIBLE);
            holder.bonusStars.setText(userDefault.IDLanguage()? "Bonus Stars "
                    +tr.getBonusStars():"Bonus Stars "+tr.getBonusStars());
        }




        holder.tvAmmount.setText(DataUtil.currencyFormat(tr.getAmount().toString()));

        if (tr.getTimestamp() != null && tr.getTimestamp().length() > 10) {
            holder.tvOpt.setText(genTime(tr.getTimestamp().substring(0, 10)));
            holder.tvTs.setText(genDate(tr.getTimestamp().substring(0, 10)));
        }

    }

    @Override
    public int getItemCount() {
        return filTrx.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setClickListener(ActivityItemAdapter.ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();

                List<ActivityResultModel> filteredList = new ArrayList<>();

                for (ActivityResultModel row : trx) {
                    // name match condition. this might differ depending on your requirement
                    // here we are looking for name or phone number match
                    if ((row.getCardNumber()).equals(charString.toLowerCase())) {
                        filteredList.add(row);
                    }else{
                    }
                }

                filTrx = filteredList;

                FilterResults filterResults = new FilterResults();
                filterResults.values = filTrx;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                notifyDataSetChanged();
            }
        };
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

    public interface ItemClickListener {
        void onClick(View view, int position);
    }

    public class ContactVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvTitle, tvOpt, tvTs, tvAmmount,starsEarned,bonusStars;
        public ImageView iv;

        private ContactVH(View v) {
            super(v);
            tvTitle = v.findViewById(R.id.detailDescription);
            tvOpt = v.findViewById(R.id.tvOpt);
            tvTs = v.findViewById(R.id.btnStart);
            tvAmmount = v.findViewById(R.id.tvAmmount);
            iv = v.findViewById(R.id.iv);
            starsEarned = v.findViewById(R.id.starsEarned);
            bonusStars = v.findViewById(R.id.bonusStars);
            picasso = Picasso.with(context);

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) clickListener.onClick(v, getAdapterPosition());
        }
    }
}


