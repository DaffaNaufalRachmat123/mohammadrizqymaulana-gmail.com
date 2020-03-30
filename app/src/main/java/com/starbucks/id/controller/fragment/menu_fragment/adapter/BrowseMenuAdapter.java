package com.starbucks.id.controller.fragment.menu_fragment.adapter;

import android.app.Activity;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.starbucks.id.R;
import com.starbucks.id.model.menu.MenuModel;
import com.starbucks.id.model.menu.MenuSubModel;

import java.util.List;

/**
 * Created by Novian on 1/16/2016.
 */
public class BrowseMenuAdapter extends BaseExpandableListAdapter {
    Activity context;
    List<MenuModel> Menus;
    ImageView imgArrow;
    private LayoutInflater inflater;

    public BrowseMenuAdapter(Activity ctx, List<MenuModel> menus) {
        this.Menus = menus;
        this.context = ctx;
    }

    public void setInflater(LayoutInflater inflater, Activity activity) {
        this.inflater = inflater;
        this.context = activity;
    }


    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.menu_browse_row_parent, null, true);
        List<MenuSubModel> detail = Menus.get(groupPosition).getSubmenu();
        TextView txtDetailMenuText = rowView.findViewById(R.id.txtMenuParent);
        txtDetailMenuText.setText(detail.get(childPosition).getNameEn());
        return rowView;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.menu_browse_row_detail, null, true);
        TextView txtMenuParent = rowView.findViewById(R.id.txtDetailMenuText);
        imgArrow = rowView.findViewById(R.id.imgMenu);
        int imageResourceId = isExpanded ? R.drawable.arrow_down_white : R.drawable.arrow_right_white;
        imgArrow.setImageResource(imageResourceId);
        txtMenuParent.setText(Menus.get(groupPosition).getMenusNameEn());
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
        return Menus.get(groupPosition).getSubmenu().size();
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
        super.onGroupExpanded(groupPosition);
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
