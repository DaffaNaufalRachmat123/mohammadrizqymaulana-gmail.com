package com.starbucks.id.controller.fragment.home_fragment.inbox_tabs.inbox_adapter;

import android.content.Context;
import android.graphics.Typeface;
import androidx.recyclerview.widget.RecyclerView;
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

import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by Angga on 23/1/2019.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ContactVH> {

    public List<MessageModel> data;
    private Context context;
    private int rowLayout;
    private Picasso picasso;
    private ItemClickListener clickListener;
    private UserDefault userDefault;

    public MessageAdapter(List<MessageModel> rmm, int rowLayout, Context context) {
        this.data = rmm;
        this.context = context;
        this.rowLayout = rowLayout;
        this.userDefault = ((MainActivity) context).getUserDefault();
        this.picasso = Picasso.with(context);
    }


    public class ContactVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTitle, tvBody, tvTS;
        public ImageView iv, ic;
        public ProgressBar pb;

        private ContactVH(View v) {
            super(v);
            tvBody = v.findViewById(R.id.tvBody);
            tvTitle = v.findViewById(R.id.detailDescription);
            tvTS = v.findViewById(R.id.tvTS);
            iv = v.findViewById(R.id.iv);
            ic = v.findViewById(R.id.ic);
            pb = v.findViewById(R.id.pb);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) clickListener.onClick(v, getAdapterPosition());
        }
    }

    @Override
    public MessageAdapter.ContactVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MessageAdapter.ContactVH(view);
    }

    @Override
    public void onBindViewHolder(final MessageAdapter.ContactVH holder, final int position) {
        final MessageModel item = data.get(position);

        holder.tvTitle.setText(item.getMsgSubject());
        String message =item.getMsgDtl().replace("\\n","\n");
        holder.tvBody.setText(message);

        if (item.getCreatedAt() != null && !item.getCreatedAt().isEmpty()) {
            String time = item.getCreatedAt().
                    replace("T", " ").
                    replace("Z", "");
            holder.tvTS.setText(DataUtil.getDate(time, 0));
        } else holder.tvTS.setVisibility(View.INVISIBLE);

        if (item.getMsgImage() != null && !item.getMsgImage().isEmpty()) {
            picasso.load(item.getMsgImage()).
                    resize(200, 200).
                    centerCrop().
                    transform(new RoundedCornersTransformation(0, 2)).
                    into(holder.iv, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            holder.iv.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onError() {
                            picasso.load(R.drawable.logo_sbux).
                                    resize(300, 300).
                                    centerCrop().
                                    transform(new RoundedCornersTransformation(10, 2)).
                                    into(holder.iv);
                            holder.iv.setVisibility(View.VISIBLE);
                        }
                    });
        }else {
            picasso.load(R.drawable.logo_sbux).
                    resize(300, 300).
                    centerCrop().
                    transform(new RoundedCornersTransformation(10, 2)).
                    into(holder.iv);
            holder.iv.setVisibility(View.VISIBLE);
        }

        if (item.getState() == 1) {
            holder.tvTitle.setTypeface(null, Typeface.NORMAL);
            holder.tvBody.setTypeface(null, Typeface.NORMAL);
            holder.ic.setVisibility(View.INVISIBLE);
        } else {
            holder.tvTitle.setTypeface(null, Typeface.BOLD);
            holder.tvBody.setTypeface(null, Typeface.BOLD);
            holder.ic.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setClickListener(MessageAdapter.ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public MessageModel getdata(int pos){
        return data.get(pos);
    }

    public interface ItemClickListener {
        void onClick(View view, int position);
    }

}


