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
import com.starbucks.id.controller.fragment.menu_fragment.MenuDetailFragment;
import com.starbucks.id.helper.UserDefault;
import com.starbucks.id.model.menu.MenuDetailModel;
import com.starbucks.id.model.menu.MenuSubModel;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Novian on 1/28/2016.
 */
public class SubMenuGvAdapter extends ArrayAdapter {
    private final Activity context;
    private List<MenuSubModel> SubMenu;
    private Picasso picasso;
    private UserDefault userDefault;

    public SubMenuGvAdapter(Activity context, List<MenuSubModel> sub) {
        super(context, R.layout.menu_detail_gridview_item);
        this.context = context;
        this.SubMenu = sub;
        picasso = ((MainActivity) context).getPicasso();
        userDefault = new UserDefault(context);
    }

    @Override
    public int getCount() {
        return SubMenu.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.menu_gridview_item, null, true);
        final ImageView imgMenu = rowView.findViewById(R.id.imgMenu);
        TextView txtMenuDetailTitle = rowView.findViewById(R.id.txtMenuTitle);

//        txtMenuDetailTitle.setText(SubMenu.get(position).getNameEn());
        txtMenuDetailTitle.setText(userDefault.IDLanguage() ? SubMenu.get(position).getNameId() : SubMenu.get(position).getNameEn());

        Collections.sort(SubMenu, new Comparator<MenuSubModel>() {
            @Override
            public int compare(MenuSubModel left, MenuSubModel right) {
                return userDefault.IDLanguage() ? left.getNameId().compareTo(right.getNameId())  : left.getNameEn().compareTo(right.getNameEn());
            }
        });
        Collections.sort(SubMenu.get(position).getDtlmenu(), new Comparator<MenuDetailModel>() {
            @Override
            public int compare(MenuDetailModel left, MenuDetailModel right) {
                return userDefault.IDLanguage()
                        ? left.getDtlNameId().compareTo(right.getDtlNameId())
                        : left.getDtlNameEn().compareTo(right.getDtlNameEn());
            }
        });


        if (SubMenu.get(position).getImageSub() != null && !SubMenu.get(position).getImageSub().equals("")) {
            picasso.load(SubMenu.get(position).getImageSub())
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

                Fragment fragment = MenuDetailFragment.newInstance(SubMenu.get(position).getDtlmenu(), SubMenu.get(position).getNameEn());
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
