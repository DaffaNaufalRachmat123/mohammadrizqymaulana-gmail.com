package com.starbucks.id.controller.fragment.menu_fragment.adapter;

import android.app.Activity;
import android.database.DataSetObserver;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.starbucks.id.R;
import com.starbucks.id.controller.activity.MainActivity;
import com.starbucks.id.controller.fragment.menu_fragment.MenuContentFragment;
import com.starbucks.id.helper.UserDefault;
import com.starbucks.id.model.menu.MenuDetailModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Novian on 1/15/2016.
 */
public class MenuGvAdapter extends ArrayAdapter {
    private final Activity context;
    private List<MenuDetailModel> MenuDetail = new ArrayList<>();
    private Picasso picasso;
    private UserDefault userDefault;
    public MenuGvAdapter(Activity context, List<MenuDetailModel> details) {
        super(context, R.layout.menu_detail_gridview_item);
        this.context = context;
        this.MenuDetail = details;
        this.picasso = ((MainActivity) context).getPicasso();
        userDefault = new UserDefault(context);
    }

    @Override
    public int getCount() {
        return MenuDetail.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.menu_gridview_item, null, true);
        final ImageView imgMenu = rowView.findViewById(R.id.imgMenu);
        TextView txtMenuDetailTitle = rowView.findViewById(R.id.txtMenuTitle);

//        txtMenuDetailTitle.setText(MenuDetail.get(position).getDtlNameEn());

        txtMenuDetailTitle.setText(userDefault.IDLanguage()
                ? MenuDetail.get(position).getDtlNameId()
                : MenuDetail.get(position).getDtlNameEn());

        if (MenuDetail.get(position).getImageDtlContent() != null && !MenuDetail.get(position).getImageDtlContent().equals("")) {
            picasso.load(MenuDetail.get(position).getImageDtl())
                    .into(imgMenu, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            imgMenu.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onError() {
                            picasso.load(R.drawable.defaultpage_720_h).into(imgMenu);
                            imgMenu.setVisibility(View.VISIBLE);

                        }
                    });
        }

        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = MenuContentFragment.newInstance(
                        MenuDetail.get(position).getImageDtlContent(),
                        userDefault.IDLanguage() ? MenuDetail.get(position).getDtlNameId() : MenuDetail.get(position).getDtlNameEn(),
                        userDefault.IDLanguage() ? MenuDetail.get(position).getMenuContentId(): MenuDetail.get(position).getMenuContentEn());
                FragmentTransaction ft = ((MainActivity) context).getSupportFragmentManager().beginTransaction();
                ft.addToBackStack(null);
                ft.replace(R.id.FLC, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }
        });

        return rowView;
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        if (observer != null) {
            super.unregisterDataSetObserver(observer);
        }
    }
}
