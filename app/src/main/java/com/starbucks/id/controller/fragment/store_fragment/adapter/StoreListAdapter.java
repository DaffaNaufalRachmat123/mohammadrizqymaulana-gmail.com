package com.starbucks.id.controller.fragment.store_fragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.starbucks.id.R;
import com.starbucks.id.controller.activity.MainActivity;
import com.starbucks.id.helper.UserDefault;
import com.starbucks.id.model.store.StoresModel;

import java.util.ArrayList;
import java.util.List;

public class StoreListAdapter extends ArrayAdapter<String> implements Filterable {

    private Context context;
    private List<StoresModel> stores;
    private List<StoresModel> filStores;
    private UserDefault userDefault;
    private int items = 20;

    public StoreListAdapter(Context context, List<StoresModel> stores) {
        super(context, R.layout.rv_store_row_detail);
        this.context = context;
        this.stores = stores;
        this.userDefault = ((MainActivity) context).getUserDefault();
        this.filStores = stores;
    }

    @Override
    public int getCount() {
        if (filStores.size() == stores.size()) return items;
        else return filStores.size();
    }

    @Override
    public String getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getPosition(String item) {
        return super.getPosition(item);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_store_row_detail, parent, false);

        TextView txtStoreName = rowView.findViewById(R.id.txtStoreName);
        TextView txtStoreAddress = rowView.findViewById(R.id.txtStoreAddress);
        TextView txtStoreHour = rowView.findViewById(R.id.txtStoreHour);

        txtStoreName.setText(filStores.get(position).getName());
        txtStoreHour.setText((!userDefault.IDLanguage() ? context.getString(R.string.en_operational_hour) : context.getString(R.string.id_operational_hour)) + System.getProperty("line.separator")
                + filStores.get(position).getOperationHour().replace("\\n", System.getProperty("line.separator")));
        txtStoreAddress.setText(filStores.get(position).getAddr());

        return rowView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filStores = stores;
                } else {
                    List<StoresModel> filteredList = new ArrayList<>();
                    for (StoresModel row : stores) {

                        if (row.getName().trim().toLowerCase().contains(charString.toLowerCase())
                                || row.getAddr().trim().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    filStores = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filStores;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filStores = (ArrayList<StoresModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public StoresModel getStores(int pos) {
        return filStores.get(pos);
    }
}
