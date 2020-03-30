package com.starbucks.id.controller.fragment.menu_fragment.adapter;

import android.app.Activity;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.starbucks.id.R;
import com.starbucks.id.controller.extension.extendedView.LetterSpacingTextView;

/**
 * Created by Novian on 12/11/2015.
 */
public class MenuList extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] menus;
    private final Boolean[] isSelected;

    public MenuList(Activity context, String[] menus, Boolean[] isselected) {
        super(context, R.layout.menu_row_list, menus);
        this.context = context;
        this.menus = menus;
        this.isSelected = isselected;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.menu_row_list, null, true);
        LetterSpacingTextView txtTitle = rowView.findViewById(R.id.txt);
        View cell = rowView.findViewById(R.id.selectedOnly);
        txtTitle.setSpacing(10);
        txtTitle.setText(menus[position]);

        if (!isSelected[position])
            cell.setBackgroundDrawable(null);

        return rowView;
    }

    public void SetSelected(int position) {
        for (Integer i = 0; i < isSelected.length; i++) {
            isSelected[i] = i == position - 1;

        }
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        if (observer != null) {
            super.unregisterDataSetObserver(observer);
        }
    }
}
