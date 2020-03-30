package com.starbucks.id.controller.fragment.home_fragment.profile_tabs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.starbucks.id.R;
import com.starbucks.id.controller.activity.MainActivity;
import com.starbucks.id.controller.extension.extendedView.SingleEditText;
import com.starbucks.id.controller.fragment.home_fragment.FragmentHome;
import com.starbucks.id.helper.UserDefault;
import com.starbucks.id.helper.utils.DialogUtil;
import com.starbucks.id.model.user.UserResponseModel;
import com.starbucks.id.rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.starbucks.id.helper.utils.DataUtil.genSBUX;

public class FragmentPassCode extends Fragment {
    private MainActivity activity;
    private UserDefault userDefault;
    SingleEditText PC1;
     SingleEditText PC2;
     SingleEditText PC3;
     SingleEditText PC4;
     SingleEditText PC5;
     SingleEditText PC6;
    TextView txtEnter;
    Button btForgot;
    public FragmentPassCode() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = ((MainActivity) getActivity());
        userDefault = UserDefault.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_passcode, container, false);
        PC1 = view.findViewById(R.id.EPC1);
        PC2 = view.findViewById(R.id.EPC2);
        PC3 = view.findViewById(R.id.EPC3);
        PC4 = view.findViewById(R.id.EPC4);
        PC5 = view.findViewById(R.id.EPC5);
        PC6 = view.findViewById(R.id.EPC6);
        txtEnter = view.findViewById(R.id.txtEnter);
        btForgot = view.findViewById(R.id.btForgot);
        activity.toolbar.setVisibility(View.GONE);
        activity.tabLayout.setVisibility(View.GONE);
        setView();
        return view;
    }

    private void ClearTxt() {
        PC1.setText("");
        PC2.setText("");
        PC3.setText("");
        PC4.setText("");
        PC5.setText("");
        PC6.setText("");
        PC1.setEnabled(true);
        PC1.requestFocus();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        PC2.setEnabled(false);
        PC3.setEnabled(false);
        PC4.setEnabled(false);
        PC5.setEnabled(false);
        PC6.setEnabled(false);


//        PC1.setText("");
//        PC2.setText("");
//        PC3.setText("");
//        PC4.setText("");
//        PC5.setText("");
//        PC6.setText("");
//        PC1.requestFocus();
//        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
//        inputMethodManager.showSoftInput(PC1, InputMethodManager.SHOW_IMPLICIT);
    }
    public void setView() {
        txtEnter.setText(userDefault.IDLanguage()?"Masukkan passcode anda":"Enter your passcode");
        btForgot.setText(userDefault.IDLanguage()?"Lupa passcode?":"Forgot passcode?");
        btForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.showOptionLogout(1);
            }
        });

//        PC1.requestFocus();
//        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
//
//        PC2.setEnabled(false);
//        PC3.setEnabled(false);
//        PC4.setEnabled(false);
//        PC5.setEnabled(false);
//        PC6.setEnabled(false);
        ClearTxt();
        PC1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                PC2.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {
                PC2.requestFocus();
            }
        });
        PC1.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_DEL) {
                    PC1.requestFocus();
                }
                return false;
            }
        });


        PC2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               PC1.setEnabled(false);
                PC3.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {
                PC3.requestFocus();
            }
        });

        PC2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_DEL) {
//                    if (PC2.getText().toString().equals("")) {
//                        PC1.setText("");
//                        PC1.requestFocus();
//                        PC2.setEnabled(false);
//                    }
                    ClearTxt();
                }

                return false;

            }
        });

        PC3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               PC2.setEnabled(false);
                PC4.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {
                PC4.requestFocus();
            }
        });

        PC3.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_DEL) {
//                    if (PC3.getText().toString().equals("")) {
//                        PC2.setText("");
//                        PC2.requestFocus();
//                        PC3.setEnabled(false);
//                    }
                    ClearTxt();
                }

                return false;

            }
        });

        PC4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                PC3.setEnabled(false);
                PC5.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {
                PC5.requestFocus();
            }
        });

        PC4.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_DEL) {
//                    if (PC4.getText().toString().equals("")) {
//                        PC3.setText("");
//                        PC3.requestFocus();
//                        PC4.setEnabled(false);
//                    }
                    ClearTxt();
                }

                return false;

            }
        });

        PC5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                PC6.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {
                PC6.requestFocus();
            }
        });

        PC5.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_DEL) {
//                    if (PC5.getText().toString().equals("")) {
//                        PC4.setText("");
//                        PC4.requestFocus();
//                        PC5.setEnabled(false);
//                    }
                    ClearTxt();
                }

                return false;

            }
        });

        PC6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("")) {
                    PC5.requestFocus();
                } else {
                    String curPasscode = PC1.getText().toString() + PC2.getText().toString() + PC3.getText().toString() + PC4.getText().toString()
                            + PC5.getText().toString()+ PC6.getText().toString();

                    if (curPasscode.equals(userDefault.getPasscode())) {
                        ((MainActivity) getActivity()).setBaseView();
                        ((MainActivity) getActivity()).cFragmentNoBS(new FragmentHome());
                    } else {
//                        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//                        DialogUtil.sNotDialog1Btn(getActivity(),
//                                !userDefault.IDLanguage() ?  getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//                            String Message = !userDefault.IDLanguage() ? getString(R.string.en_old_passcode) : getString(R.string.id_old_passcode);
//                            String Header = !userDefault.IDLanguage() ? getString(R.string.en_failure) : getString(R.string.id_failure);
//                                !userDefault.IDLanguage() ? getString(R.string.passcode_not_match) :
//                                                            getString(R.string.passcode_not_match_id),
//                                new DialogInterface.OnDismissListener() {
//                                    @Override
//                                    public void onDismiss(DialogInterface dialogInterface) {
//                                        ClearTxt();
//                                        DialogUtil.hProDialog();
//                                    }
//                                }
//                        );
                        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                        String Message = !userDefault.IDLanguage() ? getString(R.string.passcode_not_match) : getString(R.string.passcode_not_match_id);
                        String Header = !userDefault.IDLanguage() ?getString(R.string.en_failure) : getString(R.string.id_failure);
                        showNotif(Message, Header);
//                        PC1.setText("");
//                        PC2.setText("");
//                        PC3.setText("");
//                        PC4.setText("");
//                        PC5.setText("");
//                        PC6.setText("");
//                        PC1.requestFocus();
                    }

                }
            }
        });

        PC6.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_DEL) {
//                    PC5.setText("");
//                    PC5.requestFocus();
//                    PC6.setEnabled(false);
                    ClearTxt();
                }
                return false;
            }
        });

    }
    Dialog dialogNotif;
    TextView txtNotifHeader;
    TextView txtNotifContent;
    Button btnNotif;
    protected void showNotif(String Message, String Header) {
        dialogNotif = new Dialog(getActivity());
        dialogNotif.setCanceledOnTouchOutside(false);
        dialogNotif.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogNotif.setContentView(R.layout.dialog_notif_1_button);

        Window window = dialogNotif.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawableResource(android.R.color.transparent);

        txtNotifHeader = dialogNotif.findViewById(R.id.tvHeader);
        txtNotifContent = dialogNotif.findViewById(R.id.tvNotif);
        btnNotif = dialogNotif.findViewById(R.id.btOk);

        txtNotifHeader.setText(Header);
        txtNotifContent.setText(Message);
        btnNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogNotif.dismiss();
            }
        });
        dialogNotif.show();

        dialogNotif.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                ClearTxt();
            }
        });

    }
}
