package com.starbucks.id.controller.fragment.home_fragment.profile_tabs;

/*
 * Created by Angga N P on 3/13/2018.
 */

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.starbucks.id.R;
import com.starbucks.id.controller.activity.MainActivity;
import com.starbucks.id.controller.activity.TutorialsActivity;
import com.starbucks.id.controller.fragment.GeneralWebViewFragment;
import com.starbucks.id.helper.UserDefault;
import com.starbucks.id.rest.ApiClient;

public class FragmentSetting extends Fragment implements View.OnClickListener {
    private Button btLogOut, btPassCode, btFaq, btTerms,
            btPri, btCont, btShare, btTutorial, btLang;
    private TextView tvVer, tvEmail;
    private UserDefault userDefault;
    private Switch swSTP;
    private Snackbar snackbar;
    private CoordinatorLayout CoLBase;
    private Dialog dialogLanguage;
    private Dialog dialogPasscode;

    public FragmentSetting() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userDefault = ((MainActivity) getActivity()).getUserDefault();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home_profile_setting, container, false);

        setHasOptionsMenu(true);

        btLogOut = rootView.findViewById(R.id.btLogOut);
        btPassCode = rootView.findViewById(R.id.btPassCode);
        btFaq = rootView.findViewById(R.id.btFaq);
        btTerms = rootView.findViewById(R.id.btTerms);
        btPri = rootView.findViewById(R.id.btPri);
        btCont = rootView.findViewById(R.id.btCont);
        btShare = rootView.findViewById(R.id.btShare);
        btTutorial = rootView.findViewById(R.id.btTutorial);
        btLang = rootView.findViewById(R.id.btLanguage);
        swSTP = rootView.findViewById(R.id.swSTP);
        CoLBase = rootView.findViewById(R.id.CoLFRoot);
        tvVer = rootView.findViewById(R.id.tvVer);
        tvEmail = rootView.findViewById(R.id.tvId);

        btLogOut.setOnClickListener(this);
        btPassCode.setOnClickListener(this);
        btFaq.setOnClickListener(this);
        btTerms.setOnClickListener(this);
        btPri.setOnClickListener(this);
        btCont.setOnClickListener(this);
        btShare.setOnClickListener(this);
        btTutorial.setOnClickListener(this);
        btLang.setOnClickListener(this);

        setBaseView();

        return rootView;
    }


    private void setBaseView() {
        ((MainActivity) getActivity()).setToolbarTitle(
                userDefault.IDLanguage() ? "Pengaturan" : "Settings");
//        ((MainActivity) getActivity()).enableNavigationIcon();

        swSTP.setChecked(userDefault.stp());
        tvEmail.setText(userDefault.getEmail());
        if (getVersion() != null) tvVer.setText("App Version " + getVersion());

        if (userDefault.IDLanguage()) {
            btLogOut.setText("Keluar");
            btPassCode.setText("Kode Sandi");
            btTerms.setText("Syarat & Ketentuan");
            btPri.setText("Kebijakan Privasi");
            btCont.setText("Hubungi Kami");
            btShare.setText("Bagikan");
            btLang.setText("Pengaturan Bahasa");
            swSTP.setText("Gerakkan HP Anda Untuk Membayar");
            tvVer.setText("Versi Aplikasi " + getVersion());
        } else {
            btLogOut.setText("Sign Out");
            btPassCode.setText("Passcode Lock");
            btTerms.setText("Terms and Conditions");
            btPri.setText("Privacy Policy");
            btCont.setText("Contact Us");
            btShare.setText("Share");
            btLang.setText("Language Setting");
            swSTP.setText("Shake to Pay");
        }


        swSTP.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (swSTP.isChecked()) {
                    userDefault.setStp(true);
                    displaySnack(!userDefault.IDLanguage() ? "Shake to pay turned on" : "Gerakkan untuk membayar dihidupkan");
                } else {
                    swSTP.setChecked(false);
                    userDefault.setStp(false);
                    displaySnack(!userDefault.IDLanguage() ? "Shake to pay turned off" : "Gerakkan untuk membayar dimatikan");
                }
            }
        });

    }


    /*Dialog Builder*/
    private void ShowDialogLanguage() {
        dialogLanguage = new Dialog(getActivity());
        dialogLanguage.setContentView(R.layout.dialog_change_language);

        Window window = dialogLanguage.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawableResource(android.R.color.transparent);

        TextView txt = dialogLanguage.findViewById(R.id.txtHeaderLanguage);
        Button btnEng = dialogLanguage.findViewById(R.id.btnEnglish);
        Button btnBhs = dialogLanguage.findViewById(R.id.btnBahasa);

        if (userDefault.IDLanguage()) {
            btnEng.setText("Inggris");
        }

        btnEng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userDefault.IDLanguage()) {
                    userDefault.setIDLanguage(false);
                    userDefault.setLanguage("en");
                    displaySnack("Default language has been changed");
                    dialogLanguage.dismiss();
                }
                dialogLanguage.dismiss();
                setBaseView();
            }
        });

        btnBhs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!userDefault.IDLanguage()) {
                    userDefault.setIDLanguage(true);
                    userDefault.setLanguage("in");
                    displaySnack("Bahasa telah diubah");
                    dialogLanguage.dismiss();
                }
                dialogLanguage.dismiss();
                setBaseView();
            }
        });
        if (userDefault.IDLanguage()) {
            txt.setText(getString(R.string.id_setting_language));
        }

        dialogLanguage.show();
    }

    private void ShowDialogPasscode() {
        dialogPasscode = new Dialog(getActivity());
        dialogPasscode.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogPasscode.setContentView(R.layout.dialog_off_passcode);

        Window window = dialogPasscode.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawableResource(android.R.color.transparent);

        TextView txt = dialogPasscode.findViewById(R.id.txtHeaderPass);
        Button btnTurnOff = dialogPasscode.findViewById(R.id.btnTurnOffPass);
        Button btnChange = dialogPasscode.findViewById(R.id.btnChangePass);

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment newFragment = new FragmentSetPassCode();
                Bundle bundle = new Bundle();
                bundle.putBoolean("SET_OFF_PASSCODE", false);
                newFragment.setArguments(bundle);
                ((MainActivity) getActivity()).cFragmentWithBS(newFragment);
                dialogPasscode.dismiss();
            }
        });

        btnTurnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment newFragment = new FragmentSetPassCode();
                Bundle bundle = new Bundle();
                bundle.putBoolean("SET_OFF_PASSCODE", true);
                newFragment.setArguments(bundle);
                ((MainActivity) getActivity()).cFragmentWithBS(newFragment);
                dialogPasscode.dismiss();
            }
        });

        if (userDefault.IDLanguage()) {
            txt.setText(getString(R.string.id_setting_passcode));
            btnTurnOff.setText(getString(R.string.id_setting_passcode_off));
            btnChange.setText(getString(R.string.id_setting_passcode_change));
        }

        dialogPasscode.show();
    }

    private void displaySnack(String s) {
        if (snackbar != null) snackbar.dismiss();

        snackbar = Snackbar.make(CoLBase, s, Snackbar.LENGTH_SHORT);
        View sbView = snackbar.getView();

        TextView textView = sbView.findViewById(R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }

    private String getVersion() {
        try {
            PackageInfo pInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
            return String.valueOf(pInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onClick(View view) {
        String url;
        Fragment fragment;

        switch (view.getId()) {
            case R.id.btLogOut:
                ((MainActivity)getActivity()).showOptionLogout(2);
                break;

            case R.id.btLanguage:
                ShowDialogLanguage();
                break;

            case R.id.btPassCode:
                if (!userDefault.isPasscodeB()) {
                    fragment = new FragmentSetPassCode();
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("SET_OFF_PASSCODE", false);
                    bundle.putBoolean("IS_NEW_PASS_CODE", true);
                    fragment.setArguments(bundle);
                    ((MainActivity) getActivity()).cFragmentWithBS(fragment);
                } else
                    ShowDialogPasscode();
                break;

            case R.id.btFaq:
                url = !userDefault.IDLanguage() ? ApiClient.INSTANCE.getHTML_FILE() + "faq-eng.html" : ApiClient.INSTANCE.getHTML_FILE() + "faq.html";
                fragment = GeneralWebViewFragment.newInstance(url, "FAQ");
                ((MainActivity) getActivity()).cFragmentWithBS(fragment);
                break;

            case R.id.btTerms:
                url = !userDefault.IDLanguage() ? ApiClient.INSTANCE.getHTML_FILE() + "tnc-eng.html" : ApiClient.INSTANCE.getHTML_FILE() + "tnc.html";
                fragment = GeneralWebViewFragment.newInstance(url, userDefault.IDLanguage() ? "Syarat Penggunaan" : "Terms and Conditions");
                ((MainActivity) getActivity()).cFragmentWithBS(fragment);
                break;

            case R.id.btPri:
                url = !userDefault.IDLanguage() ? ApiClient.INSTANCE.getHTML_FILE() + "pp-eng.html" : ApiClient.INSTANCE.getHTML_FILE() + "pp.html";
                fragment = GeneralWebViewFragment.newInstance(url, userDefault.IDLanguage() ? "Kebijakan Privasi" : "Privacy Policy");
                ((MainActivity) getActivity()).cFragmentWithBS(fragment);
                break;

            case R.id.btCont:
                url = !userDefault.IDLanguage() ? ApiClient.INSTANCE.getHTML_FILE() + "contactus-eng.html" : ApiClient.INSTANCE.getHTML_FILE() + "contactus.html";
                fragment = GeneralWebViewFragment.newInstance(url, userDefault.IDLanguage() ? "Hubungi Kami" : "Contact Us");
                ((MainActivity) getActivity()).cFragmentWithBS(fragment);
                break;

            case R.id.btTutorial:
                Intent intent = new Intent(getActivity(), TutorialsActivity.class);
                intent.putExtra("operation_id", 1);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setBaseView();
    }

    @Override
    public void onDestroy() {
        if (dialogLanguage != null) if (dialogLanguage.isShowing()) dialogLanguage.dismiss();
        if (dialogPasscode != null) if (dialogPasscode.isShowing()) dialogPasscode.dismiss();
        super.onDestroy();
    }
}
