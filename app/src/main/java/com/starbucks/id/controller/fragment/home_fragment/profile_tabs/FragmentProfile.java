package com.starbucks.id.controller.fragment.home_fragment.profile_tabs;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.starbucks.id.R;
import com.starbucks.id.controller.activity.MainActivity;
import com.starbucks.id.controller.extension.CustomCamera;
import com.starbucks.id.controller.extension.CustomCamera2;
import com.starbucks.id.controller.fragment.home_fragment.FragmentHome;
import com.starbucks.id.controller.fragment.home_fragment.profile_tabs.reward_fragment.FragmentRewards;
import com.starbucks.id.helper.UserDefault;
import com.starbucks.id.helper.utils.PopUpUtil;

/**
 * Created by Angga N P on 3/14/2018.
 */

public class FragmentProfile extends Fragment implements View.OnClickListener {
    private Button btActivity, btReward, btPersonal, btSetting, btTest,btFoto;
    private UserDefault userDefault;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 1;
    MainActivity activity;
    public FragmentProfile() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = ((MainActivity)getActivity());
        userDefault = ((MainActivity) getActivity()).getUserDefault();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home_profile, container, false);

        setHasOptionsMenu(true);

        btActivity = rootView.findViewById(R.id.btActivity);
        btReward = rootView.findViewById(R.id.btReward);
        btPersonal = rootView.findViewById(R.id.btPersonal);
        btSetting = rootView.findViewById(R.id.btSetting);
        btTest = rootView.findViewById(R.id.btTest);
        btFoto = rootView.findViewById(R.id.btProgramTracker);

        btActivity.setOnClickListener(this);
        btReward.setOnClickListener(this);
        btPersonal.setOnClickListener(this);
        btSetting.setOnClickListener(this);
        btTest.setOnClickListener(this);
        btFoto.setOnClickListener(this);

        setBaseView();

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btActivity:
                ((MainActivity) getActivity()).cFragmentWithBS(new FragmentHistoryCardProfile());
                break;
            case R.id.btReward:
                Bundle bundle = new Bundle();
                bundle.putInt("operation_id", 1);
                ((MainActivity) getActivity()).cFragmentWithBundle(new FragmentRewards(), bundle);
                break;
            case R.id.btPersonal:
                ((MainActivity) getActivity()).cFragmentWithBS(new FragmentPersonal());
                break;

            case R.id.btSetting:
                ((MainActivity) getActivity()).cFragmentWithBS(new FragmentSetting());
                break;
            case R.id.btTest:
                Intent i = new Intent(getActivity(), CustomCamera2.class);
                startActivity(i);
                break;
        }
    }

    private void setDefaultView() {
        ((MainActivity) getActivity()).setToolbarTitle(
                getString(R.string.profile_title));
        activity.toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.cFragmentNoBS(new FragmentHome());
            }
        });
    }

    private void setBaseView() {
        backButton();
        if (userDefault.IDLanguage()) {
            btActivity.setText("Riwayat Transaksi Kartu");
            btPersonal.setText("Kontak Info");
            btSetting.setText("Pengaturan");
        }
        setDefaultView();
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        // All required changes were successfully made
                        if (ContextCompat.checkSelfPermission(getActivity(),
                                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

                            Intent i = new Intent(getActivity(), CustomCamera.class);
                            startActivity(i);
                        } else {
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[]{Manifest.permission.CAMERA},
                                    MY_PERMISSIONS_REQUEST_CAMERA);
                        }
                        break;

                    case Activity.RESULT_CANCELED:
                        // The user was asked to change settings, but chose not to

                        showToast(userDefault.IDLanguage() ?
                                "Pengguna menolak untuk mengaktifkan camera perangkat." :
                                "User deny to turn on device camera");
                        break;

                    default:
                        break;
                }
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

                        Intent i = new Intent(getActivity(), CustomCamera.class);
                        startActivity(i);
                    } else {
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{Manifest.permission.CAMERA},
                                MY_PERMISSIONS_REQUEST_CAMERA);
                    }

                    showToast(userDefault.IDLanguage() ?
                            "Pengguna menolak untuk mengaktifkan camera perangkat." :
                            "User deny to turn on device camera");
                }
            }
        }
    }
    public void showToast(String s) {
        PopUpUtil.sLongToast(getActivity(), s);
    }


    public void backButton() {
        activity.toolbar.setNavigationIcon(R.drawable.ic_action_back);
        activity.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.cFragmentNoBS(new FragmentHome());

            }});
    }
}
