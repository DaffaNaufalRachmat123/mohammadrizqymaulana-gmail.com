package com.starbucks.id.controller.fragment.menu_fragment.adapter;

import android.app.Activity;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.starbucks.id.R;

/**
 * Created by Novian on 1/15/2016.
 */

public class MenuContentListAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String HtmlTitle;
    private final String HtmlContent;

    public MenuContentListAdapter(Activity context, String HtmlTitle, String HtmlContent) {
        super(context, R.layout.whats_new_row_detail );
        this.context = context;
        this.HtmlTitle = HtmlTitle;
        this.HtmlContent = HtmlContent;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        final int _position = position;
        View rowView = inflater.inflate(R.layout.whats_new_row_detail, null, true);
        TextView txtWhatsNewDetailTitle = rowView.findViewById(R.id.txtWhatsNewDetailTitle);
        TextView txtWhatsNewDetailContent = rowView.findViewById(R.id.txtWhatsNewDetailContent);
        txtWhatsNewDetailTitle.setText(HtmlTitle);
        txtWhatsNewDetailContent.setText(HtmlContent);

        return rowView;
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        if (observer != null) {
            super.unregisterDataSetObserver(observer);
        }
    }
}
