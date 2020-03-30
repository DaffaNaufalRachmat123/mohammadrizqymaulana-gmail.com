package com.starbucks.id.controller.fragment.menu_fragment.adapter;

import android.app.Activity;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.starbucks.id.R;
import com.starbucks.id.controller.activity.MainActivity;
import com.starbucks.id.model.menu.MenuModel;

import java.util.ArrayList;
import java.util.List;

public class MenuParentGvAdapter extends ArrayAdapter {
    private final Activity context;
    private List<MenuModel> Menus = new ArrayList<>();
    private Picasso picasso;

    public MenuParentGvAdapter(Activity context, List<MenuModel> menus) {
        super(context, R.layout.menu_detail_gridview_item);
        this.context = context;
        this.Menus = menus;
        picasso = ((MainActivity) context).getPicasso();
    }

    @Override
    public int getCount() {
        return Menus.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final int _position = position;
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.menu_detail_gridview_item, null, true);
        final ImageView imgMenuDetail = rowView.findViewById(R.id.imgMenuDetail);
        if (Menus.get(position).getImageHdr() != null && !Menus.get(position).getImageHdr().equals("")) {
            picasso.load(Menus.get(position).getImageHdr())
                    .into(imgMenuDetail, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            imgMenuDetail.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onError() {
                            picasso.load(R.drawable.defaultpage_720_h).into(imgMenuDetail);
                            imgMenuDetail.setVisibility(View.VISIBLE);
                        }
                    });
        }

        return rowView;
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        if (observer != null) {
            super.unregisterDataSetObserver(observer);
        }
    }
}
