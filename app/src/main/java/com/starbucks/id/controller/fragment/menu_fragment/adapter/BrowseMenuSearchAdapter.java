package com.starbucks.id.controller.fragment.menu_fragment.adapter;

import android.app.Activity;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.starbucks.id.R;
import com.starbucks.id.controller.activity.MainActivity;
import com.starbucks.id.model.menu.MenuModel;
import com.starbucks.id.model.menu.MenuSubModel;

import java.util.List;

public class BrowseMenuSearchAdapter extends BaseExpandableListAdapter {
    Activity context;
    List<MenuModel> Menus;
    ImageView imgArrow;
    private LayoutInflater inflater;
    private Picasso picasso;

    public BrowseMenuSearchAdapter(Activity ctx, List<MenuModel> menus) {
        this.Menus = menus;
        this.context = ctx;
        picasso = ((MainActivity) context).getPicasso();
    }

    public void setInflater(LayoutInflater inflater, Activity activity) {
        this.inflater = inflater;
        this.context = activity;
    }


    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.menu_browse_search, null, true);
        List<MenuSubModel> detail = Menus.get(groupPosition).getSubmenu();
        TextView txtDetailMenuText = rowView.findViewById(R.id.txtMenuText);
        final ImageView img = rowView.findViewById(R.id.imgMenu);
        txtDetailMenuText.setText(detail.get(childPosition).getNameEn());


        if (Menus.get(groupPosition).getImageHdr() != null && !Menus.get(groupPosition).getImageHdr().toString().equals("")) {


            picasso.load(Menus.get(groupPosition).getImageHdr())
                    .into(img, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            img.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onError() {
                            picasso.load(R.drawable.defaultpage_720_h).into(img);
                            img.setVisibility(View.VISIBLE);

                        }
                    });
        }


        return rowView;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View rowView = inflater.inflate(R.layout.menu_browse_search, null, true);
        TextView txtDetailMenuText = rowView.findViewById(R.id.txtMenuText);
        final ImageView img = rowView.findViewById(R.id.imgMenu);
        txtDetailMenuText.setText(Menus.get(groupPosition).getMenusNameEn());

        picasso.load(Menus.get(groupPosition).getImageHdr())
                .into(img, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        img.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError() {
                        picasso.load(R.drawable.defaultpage_720_h).into(img);
                        img.setVisibility(View.VISIBLE);

                    }
                });

        return rowView;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public int getGroupCount() {
        return Menus.size();
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        imgArrow.setImageResource(R.drawable.arrow_down_white);
        //super.onGroupExpanded(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        if (observer != null) {
            super.unregisterDataSetObserver(observer);
        }
    }
}
