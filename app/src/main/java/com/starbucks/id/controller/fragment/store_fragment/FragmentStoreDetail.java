package com.starbucks.id.controller.fragment.store_fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.starbucks.id.R;
import com.starbucks.id.controller.activity.MainActivity;
import com.starbucks.id.helper.UserDefault;
import com.starbucks.id.model.store.StoresModel;

public class FragmentStoreDetail extends Fragment implements OnMapReadyCallback {
    private static final int REQUEST_MAP = 0;
    private static StoresModel Store;
    private MapView StoreMapview;
    private GoogleMap map;

    private TextView txtAmenDriveThru, txtAmenOvenWarmed, txtAmenLB, txtAmenCloverBrewed, txtAmenEvMenu,
            txtAmenWifi, txtAmenDigi, txtAmen24, txtAmenReserve, txtAmenSbuxCard, txtAmenities,
            txtStoreNameDetail, txtGetDirHeader, txtGetDir, txtOpenUntil, txtCallNowHeader,
            txtPhoneNumber, txtOpenHourHeader, txtOpenHour;
    private TableRow trDriveThru, trOven, trLB, trClover, trEvMenu, trWifi, trDigi, tr24, trReserve, trSbuxCard;
    private ImageView imgDriveThru, imgOvenWarmed, imgLB, imgCloverBrewed, imgEvMenu, imgWifi, imgDigi,
            img24h, imgReserve, imgSbuxCard, imgDriveThruExpanded, imgOvenWarmedExpanded,
            imgLBExpanded, imgCloverBrewedExpanded, imgEvMenuExpanded, imgWifiExpanded,
            imgDigiExpanded, img24Expanded, imgReserveExpanded, imgSbuxCardExpanded;
    private ExpandableRelativeLayout ExpLayout;
    private RelativeLayout ImageHolder;
    private ImageButton btnExpand;
    private Button btnCall, btngetDir;

    private Location mLastLocation;
    private UserDefault userDefault;
    private MainActivity activity;

    public FragmentStoreDetail() {
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentStoreDetail newInstance(StoresModel store) {
        FragmentStoreDetail fragment = new FragmentStoreDetail();
        Store = store;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = ((MainActivity)getActivity());
        if (activity != null) userDefault = activity.getUserDefault();
        activity.counter = 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_store_detail, container, false);

        ExpLayout = view.findViewById(R.id.storeAmenities);
        ImageHolder = view.findViewById(R.id.imageHolder);
        StoreMapview = view.findViewById(R.id.StoreDetailMapview);
        StoreMapview.onCreate(savedInstanceState);

        txtAmenities = view.findViewById(R.id.txtAmenities);
        txtStoreNameDetail = view.findViewById(R.id.txtStoreNameDetail);
        txtGetDirHeader = view.findViewById(R.id.txtGetDirHeader);
        txtGetDir = view.findViewById(R.id.txtGetDir);
        txtOpenUntil = view.findViewById(R.id.txtOpenUntil);
        txtCallNowHeader = view.findViewById(R.id.txtCallNowHeader);
        txtPhoneNumber = view.findViewById(R.id.txtPhoneNumber);
        txtOpenHourHeader = view.findViewById(R.id.txtOpenHourHeader);
        txtOpenHour = view.findViewById(R.id.txtOpenHour);


        imgDriveThru = view.findViewById(R.id.imgDriveThru);
        imgOvenWarmed = view.findViewById(R.id.imgOvenWarmed);
        imgLB = view.findViewById(R.id.imgLB);
        imgCloverBrewed = view.findViewById(R.id.imgCloverBrewed);
        imgEvMenu = view.findViewById(R.id.imgEvMenu);
        imgWifi = view.findViewById(R.id.imgWifi);
        imgDigi = view.findViewById(R.id.imgDigi);
        img24h = view.findViewById(R.id.img24h);
        imgReserve = view.findViewById(R.id.imgReserve);
        imgSbuxCard = view.findViewById(R.id.imgSbuxCard);

        imgDriveThruExpanded = view.findViewById(R.id.imgDriveThruExpanded);
        imgOvenWarmedExpanded = view.findViewById(R.id.imgOvenWarmedExpanded);
        imgLBExpanded = view.findViewById(R.id.imgLBExpanded);
        imgCloverBrewedExpanded = view.findViewById(R.id.imgCloverBrewedExpanded);
        imgEvMenuExpanded = view.findViewById(R.id.imgEvMenuExpanded);
        imgWifiExpanded = view.findViewById(R.id.imgWifiExpanded);
        imgDigiExpanded = view.findViewById(R.id.imgDigiExpanded);
        img24Expanded = view.findViewById(R.id.img24Expanded);
        imgReserveExpanded = view.findViewById(R.id.imgReserveExpanded);
        imgSbuxCardExpanded = view.findViewById(R.id.imgSbuxCardExpanded);

        btnExpand = view.findViewById(R.id.btnExpand);

        txtAmenDriveThru = view.findViewById(R.id.txtAmenDriveThru);
        txtAmenOvenWarmed = view.findViewById(R.id.txtAmenOvenWarmed);
        txtAmenLB = view.findViewById(R.id.txtAmenLB);
        txtAmenCloverBrewed = view.findViewById(R.id.txtAmenCloverBrewed);
        txtAmenEvMenu = view.findViewById(R.id.txtAmenEvMenu);
        txtAmenWifi = view.findViewById(R.id.txtAmenWifi);
        txtAmenDigi = view.findViewById(R.id.txtAmenDigi);
        txtAmen24 = view.findViewById(R.id.txtAmen24);
        txtAmenReserve = view.findViewById(R.id.txtAmenReserve);
        txtAmenSbuxCard = view.findViewById(R.id.txtAmenSbuxCard);

        trDriveThru = view.findViewById(R.id.trDriveThru);
        trOven = view.findViewById(R.id.trOven);
        trLB = view.findViewById(R.id.trLB);
        trClover = view.findViewById(R.id.trClover);
        trEvMenu = view.findViewById(R.id.trEvMenu);
        trWifi = view.findViewById(R.id.trWifi);
        trDigi = view.findViewById(R.id.trDigi);
        tr24 = view.findViewById(R.id.tr24);
        trReserve = view.findViewById(R.id.trReserve);
        trSbuxCard = view.findViewById(R.id.trSbuxCard);
        btnCall = view.findViewById(R.id.btnCall);
        btngetDir = view.findViewById(R.id.btnGetDir);

        btnExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHideAmenities();
            }
        });

        //TEMPORARY NO DATA
        imgLB.setVisibility(View.GONE);
        trLB.setVisibility(View.GONE);
        imgEvMenu.setVisibility(View.GONE);
        trEvMenu.setVisibility(View.GONE);


        if (!Store.getIsDriveThru().equals("1")) {
            imgDriveThru.setVisibility(View.GONE);
            trDriveThru.setVisibility(View.GONE);
        }
        if (!Store.getIsOvenWarmedFood().equals("1")) {
            imgOvenWarmed.setVisibility(View.GONE);
            trOven.setVisibility(View.GONE);
        }
        if (!Store.getIsWifiReady().equals("1")) {
            imgWifi.setVisibility(View.GONE);
            trWifi.setVisibility(View.GONE);
        }
        if (!Store.getIs24hours().equals("1")) {
            img24h.setVisibility(View.GONE);
            tr24.setVisibility(View.GONE);
        }

        if (!Store.getIsReverseAble().equals("1")) {
            imgReserve.setVisibility(View.GONE);
            trReserve.setVisibility(View.GONE);
        }
        if (!Store.getIsEsspresoChoice().equals("1")) {
            imgDigi.setVisibility(View.GONE);
            trDigi.setVisibility(View.GONE);
        }
        if (!Store.getIsSparkling().equals("1")) {
            imgCloverBrewed.setVisibility(View.GONE);
            trClover.setVisibility(View.GONE);
        }
        if (!Store.getIsSbuxCard().equals("1")) {
            imgSbuxCard.setVisibility(View.GONE);
            trSbuxCard.setVisibility(View.GONE);
        }


        txtGetDir.setText(Store.getAddr());
        txtPhoneNumber.setText(Store.getPhone());
        txtOpenHour.setText(Store.getOperationHour().replace("\\n", System.getProperty("line.separator")));//(OpenHour + " - " + CloseHour);
        txtStoreNameDetail.setText(Store.getName());
        if (ContextCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_MAP);
        }

        StoreMapview.getMapAsync(this);

        SetupBahasa();

        return view;
    }

    public void SetupBahasa() {
        if (userDefault.IDLanguage()) {
            txtAmenities.setText(getString(R.string.id_amenities));
            txtGetDirHeader.setText(getString(R.string.id_get_dir));
            txtCallNowHeader.setText(getString(R.string.id_call_now));
            txtOpenHourHeader.setText(getString(R.string.id_open_hour));
        }
    }

    private void showHideAmenities() {
        if (ExpLayout.isExpanded()) {
            ImageHolder.setVisibility(View.VISIBLE);
            ExpLayout.collapse();
            btnExpand.animate().rotation(0);
        } else {
            ImageHolder.setVisibility(View.GONE);
            ExpLayout.expand();
            btnExpand.animate().rotation(180);
        }
    }

    public void setData() {
        if(activity.counter==0){
            map.getUiSettings().setMyLocationButtonEnabled(true);
        }else{
            map.getUiSettings().setMyLocationButtonEnabled(false);
        }
        map.getUiSettings().setZoomControlsEnabled(false);
        LocationManager locManager;
        double def_lat = -6.175392999999987;
        double def_long = 106.82702100000006;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {

                map.setMyLocationEnabled(true);
                MapsInitializer.initialize(this.getActivity());

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
            } else {
                mLastLocation = new Location("DEFAULT");
                mLastLocation.setLatitude(def_lat);
                mLastLocation.setLongitude(def_long);
            }
        } else {
            locManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            MapsInitializer.initialize(this.getActivity());

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
        }

        map.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(Store.getLat()),
                Double.parseDouble(Store.getLon()))).title(Store.getName()));

        double aLong = FragmentStores.round(Double.parseDouble(Store.getLon()), 4);
        double lat = FragmentStores.round(Double.parseDouble(Store.getLat()), 4);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(lat, aLong), 15);
        map.animateCamera(cameraUpdate);

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phNum = Store.getPhone();
                if (phNum.contains("/")) {
                    phNum = phNum.split("/")[0];
                }
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + phNum));
                startActivity(callIntent);
            }
        });

        btngetDir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?saddr=" +
                                Double.toString(mLastLocation.getLatitude()) + "," +
                                Double.toString(mLastLocation.getLongitude()) +
                                "&daddr=" + (Store.getLat()) + "," + (Store.getLon())));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        StoreMapview.onResume();
        super.onResume();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        activity.checkSettingLocation();
    }
}
