package com.starbucks.id.controller.fragment.store_fragment;

/*
 * Created by Angga N P on 5/9/2018.
 */

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.starbucks.id.R;
import com.starbucks.id.controller.activity.MainActivity;
import com.starbucks.id.controller.fragment.store_fragment.adapter.StoreListAdapter;
import com.starbucks.id.helper.UserDefault;
import com.starbucks.id.helper.utils.DataUtil;
import com.starbucks.id.model.store.StoresModel;
import com.starbucks.id.rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.SENSOR_SERVICE;


public class FragmentStores extends Fragment implements
        OnMapReadyCallback {

    private double Long, Lat;
    private TextView txtMapTitle;
    private EditText txtStoreSearch;
    private GoogleMap map;
    private MapView StoreMapview;
    private LocationManager locManager;
    private double def_lat = -6.175392999999987;
    private double def_long = 106.82702100000006;
    private ListView lstStore;
    private ViewGroup header;
    private Location mLastLocation;

    private StoreListAdapter adapter;
    private List<StoresModel> storeList;
    private UserDefault userDefault;
    private Call<List<StoresModel>> call;

    private MainActivity activity;
    private FirebaseAnalytics mFirebaseAnalytics;
    public FragmentStores() {
        // Required empty public constructor
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = ((MainActivity)getActivity());
        if (activity != null) userDefault = activity.getUserDefault();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_stores, container, false);
        activity.setToolbarTitle(getString(R.string.f_store_t));

        lstStore = view.findViewById(R.id.lstStore);

        header = (ViewGroup) inflater.inflate(R.layout.rv_store_header, lstStore, false);
        lstStore.addHeaderView(header, null, false);
        StoreMapview = header.findViewById(R.id.StoreMapview);
        txtMapTitle = header.findViewById(R.id.txtMapTitle);
        txtStoreSearch = header.findViewById(R.id.txtStoreSearch);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());
        Bundle params = new Bundle();
        params.putString("FragmentStores", "Fragment Stores");
        mFirebaseAnalytics.logEvent("AOS", params);

        if (userDefault.IDLanguage()) {
            txtStoreSearch.setHint(getString(R.string.id_search_store));
            txtMapTitle.setText(getString(R.string.id_near_by));
        }

        StoreMapview.getMapAsync(this);
        StoreMapview.onCreate(savedInstanceState);

        return view;
    }

    public void setData() {
        if (getActivity() != null) {
            initLoc();
        }
    }

    public void setDefault() {
        if (getActivity() != null) {
            getStoreData(String.valueOf(def_lat), String.valueOf(def_long));
        }
    }

    private void setMap() {
        map.clear();
        activity.setToolbarTitle(getString(R.string.f_store_t));

        for (int i = 0; i < storeList.size(); i++) {
            LatLng pos = new LatLng(
                    Double.parseDouble(storeList.get(i).getLat()),
                    Double.parseDouble(storeList.get(i).getLon()));
            map.addMarker(new MarkerOptions().position(pos)
                    .title(storeList.get(i).getName())
                    .snippet(storeList.get(i).getAddr()));

            map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    StoresModel Store = getStoreByName(marker.getTitle());
                    Fragment newFragment = FragmentStoreDetail.newInstance(Store);
                    activity.cFragmentWithBS(newFragment);
                }
            });
        }

        txtStoreSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        adapter = new StoreListAdapter(getActivity(), storeList);
        lstStore.setAdapter(adapter);
        lstStore.setVisibility(View.VISIBLE);

        if (storeList.size() == 0) ShowNoData();
        else HideNoData();

        lstStore.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StoresModel Store = adapter.getStores(position - 1);
                Fragment newFragment = FragmentStoreDetail.newInstance(Store);
                activity.cFragmentWithBS(newFragment);
            }
        });

                if (mLastLocation != null) {
                    Long = round(mLastLocation.getLongitude(), 4);
                    Lat = round(mLastLocation.getLatitude(), 4);
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(Lat, Long), 15);
                    map.animateCamera(cameraUpdate);
                } else {
                    Long = round(def_long, 4);
                    Lat = round(def_lat, 4);
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(Lat, Long), 15);
                    map.animateCamera(cameraUpdate);
                }



    }


    public void initLoc() {
                map.getUiSettings().setMyLocationButtonEnabled(true);
                map.getUiSettings().setZoomControlsEnabled(false);
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                        map.setMyLocationEnabled(true);
                        MapsInitializer.initialize(getActivity());

                        locManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

                        mLastLocation = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (mLastLocation == null) {
                            mLastLocation = locManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                            if (mLastLocation == null) {
                                mLastLocation = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                                if (mLastLocation == null) {
                                    mLastLocation = new Location("DEFAULT");
                                    mLastLocation.setLatitude(def_lat);
                                    mLastLocation.setLongitude(def_long);
                                }
                            }
                        }

                        getStoreData(String.valueOf(mLastLocation.getLatitude()), String.valueOf(mLastLocation.getLongitude()));
                    } else {
                        activity.checkSettingLocation();
                    }
                }

    }

    private StoresModel getStoreByName(String StoreName) {
        StoresModel store = null;
        for (int i = 0; i < storeList.size(); i++) {
            if (storeList.get(i).getName().equals(StoreName)) {
                store = storeList.get(i);
                break;
            }
        }
        return store;
    }

    /* REST Call */
    // Get Store List Data
    public void getStoreData(final String lat, final String lon) {
        if (!activity.checkConnection()) return;
        ApiInterface apiService = activity.getApiService();

        lstStore.setVisibility(View.INVISIBLE);
        call = apiService.getStoreList(lat, lon, DataUtil.encrypt(getString(R.string.rest_content_auth)));

        call.enqueue(new Callback<List<StoresModel>>() {
            @Override
            public void onResponse(Call<List<StoresModel>> call, Response<List<StoresModel>> response) {
                if (getActivity() != null) {
                    if (response.isSuccessful()) {
                        storeList = response.body();
                        setMap();
                    } else {
                        activity.
                                checkServer(String.valueOf(response.code()), response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<StoresModel>> call, Throwable t) {
                if (getActivity() != null && isAdded())
                    activity.restFailure(t);
            }
        });

    }

    protected void ShowNoData() {
        TextView txtMapTitle = header.findViewById(R.id.txtMapTitle);
        TextView txtNoData = header.findViewById(R.id.txtNoData);

        txtMapTitle.setVisibility(View.INVISIBLE);
        txtNoData.setVisibility(View.VISIBLE);

        if (userDefault.IDLanguage())
            txtNoData.setText(userDefault.IDLanguage() ?
                    "Tidak ada data ditemukan" : "No data found");
    }

    protected void HideNoData() {
        TextView txtMapTitle = header.findViewById(R.id.txtMapTitle);
        TextView txtNoData = header.findViewById(R.id.txtNoData);

        txtMapTitle.setVisibility(View.VISIBLE);
        txtNoData.setVisibility(View.GONE);

        if (userDefault.IDLanguage())
            txtNoData.setText(userDefault.IDLanguage() ?
                    "Tidak ada data ditemukan" : "No data found");
    }

    @Override
    public void onMapReady(GoogleMap gmap) {
        map = gmap;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        activity.checkSettingLocation();

    }

    @Override
    public void onResume() {
        StoreMapview.onResume();
        super.onResume();

    }


}
