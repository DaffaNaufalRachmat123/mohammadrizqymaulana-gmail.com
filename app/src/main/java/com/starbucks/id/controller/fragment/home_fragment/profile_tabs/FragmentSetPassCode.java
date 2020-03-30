package com.starbucks.id.controller.fragment.home_fragment.profile_tabs;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
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
import android.widget.Toast;

import com.starbucks.id.R;
import com.starbucks.id.controller.activity.MainActivity;
import com.starbucks.id.controller.extension.extendedView.SingleEditText;
import com.starbucks.id.controller.fragment.home_fragment.FragmentHome;
import com.starbucks.id.helper.UserDefault;
import com.starbucks.id.helper.utils.DialogUtil;

import java.util.Timer;
import java.util.TimerTask;

public class FragmentSetPassCode extends Fragment {

    SingleEditText PC1;
    SingleEditText PC2;
    SingleEditText PC3;
    SingleEditText PC4;
    SingleEditText PC5;
    SingleEditText PC6;
    TextView txtSet;
    TextView txtDescPassCode;
    String pwd1;
    String Recpwd;
    boolean oldpass = false;
    boolean confirm = false;
    boolean issetoff = false;
    boolean isnew = false;

    Dialog dialogNotif;
    TextView txtNotifHeader;
    TextView txtNotifContent;
    Button btnNotif;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private UserDefault userDefault;

    private OnFragmentInteractionListener mListener;

    public FragmentSetPassCode() {
        // Required empty public constructor
    }

    public static FragmentSetPassCode newInstance(boolean setOff, boolean isnw) {
        FragmentSetPassCode fragment = new FragmentSetPassCode();
        Bundle args = new Bundle();
        args.putBoolean("SET_OFF_PASSCODE", setOff);
        args.putBoolean("IS_NEW_PASS_CODE", isnw);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            issetoff = getArguments().getBoolean("SET_OFF_PASSCODE");
            isnew = getArguments().getBoolean("IS_NEW_PASS_CODE");
        }
        userDefault = ((MainActivity) getActivity()).getUserDefault();
    }

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_set_pass_code, container, false);
        PC1 = view.findViewById(R.id.EPC1);
        PC2 = view.findViewById(R.id.EPC2);
        PC3 = view.findViewById(R.id.EPC3);
        PC4 = view.findViewById(R.id.EPC4);
        PC5 = view.findViewById(R.id.EPC5);
        PC6 = view.findViewById(R.id.EPC6);
        txtSet = view.findViewById(R.id.txtSet);
        txtDescPassCode = view.findViewById(R.id.txtDescPassCode);
        ((MainActivity) getActivity()).toolbar.setVisibility(View.GONE);
        ((MainActivity) getActivity()).tabLayout.setVisibility(View.GONE);
        ((MainActivity) getActivity()).setToolbarTitle(!userDefault.IDLanguage() ?
                getString(R.string.pc_title) : getString(R.string.pc_title_id));

        if (!userDefault.IDLanguage())
            txtSet.setText(getString(R.string.en_enter_old_passcode));
        else {
            txtSet.setText(getString(R.string.id_enter_old_passcode));
            txtDescPassCode.setText(getString(R.string.desc_passcode_id));
        }

        if (isnew) {
            if (!userDefault.IDLanguage())
                txtSet.setText(getString(R.string.en_enter_new_passcode));
            else
                txtSet.setText(getString(R.string.id_enter_new_passcode));
            oldpass = true;
        }

//        PC1.requestFocus();
//        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
//        PC2.setEnabled(false);
//        PC3.setEnabled(false);
//        PC4.setEnabled(false);
//        PC5.setEnabled(false);
//        PC6.setEnabled(false);
        ClearTxt();

        PC1.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_DEL) {
                    PC1.requestFocus();
                }
                return false;
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
//                if (s.toString().equals("")) {
//                    PC1.requestFocus();
//                } else {
                    PC2.requestFocus();
//                }
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
//                if (s.toString().equals("")) {
//                    PC1.requestFocus();
//                } else {
                    PC3.requestFocus();
//                }
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
//                if (s.toString().equals("")) {
//                    PC2.requestFocus();
//                } else {
                    PC4.requestFocus();
//                }
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
//                if (s.toString().equals("")) {
//                    PC3.requestFocus();
//                } else {
                    PC5.requestFocus();
//                }
            }
        });

        PC5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                PC4.setEnabled(false);
                PC6.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {
//                if (s.toString().equals("")) {
//                    PC4.requestFocus();
//                } else {
                    PC6.requestFocus();
//                }
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
                    if (!oldpass) {
                        pwd1 = PC1.getText().toString() + PC2.getText().toString() + PC3.getText().toString()
                                + PC4.getText().toString()+ PC5.getText().toString() + PC6.getText().toString();
                        if (userDefault.getPasscode().equals(pwd1)) {
                            if (issetoff) {
                                userDefault.setPasscodeB(false);
                                userDefault.setPasscode("");

                                Toast.makeText(getActivity(), !userDefault.IDLanguage() ? getString(R.string.passcode_off) : getString(R.string.passcode_off_id),
                                        Toast.LENGTH_LONG).show();

                                getActivity().onBackPressed();
                            } else {
                                ClearTxt();
//                                PC1.requestFocus();
//                                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
//                                PC2.setEnabled(false);
//                                PC3.setEnabled(false);
//                                PC4.setEnabled(false);
//                                PC5.setEnabled(false);
//                                PC6.setEnabled(false);
                                if (!userDefault.IDLanguage())
                                    txtSet.setText(getString(R.string.en_enter_new_passcode));
                                else
                                    txtSet.setText(getString(R.string.id_enter_new_passcode));

                                oldpass = true;
                            }
                        } else {
//                            PC5.requestFocus();
//                            PC6.requestFocus();
                            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                            String Message = !userDefault.IDLanguage() ? getString(R.string.en_old_passcode) : getString(R.string.id_old_passcode);
                            String Header = !userDefault.IDLanguage() ? getString(R.string.en_failure) : getString(R.string.id_failure);
                            showNotif(Message, Header);
                        }
                    } else if (oldpass && !confirm) {
                        ProgressDialog dialog = new ProgressDialog(getActivity());
                        dialog.setMessage("Please wait..");
                        dialog.show();

                        pwd1 = PC1.getText().toString() + PC2.getText().toString() + PC3.getText().toString()
                                + PC4.getText().toString()+ PC5.getText().toString()+ PC6.getText().toString();
                        ClearTxt();
                        if (!userDefault.IDLanguage())
                            txtSet.setText(getString(R.string.confirm_passcode));
                        else
                            txtSet.setText(getString(R.string.confirm_passcode_id));
                        txtDescPassCode.setVisibility(View.INVISIBLE);
                        confirm = true;

                        dialog.dismiss();
                    } else {
                        Recpwd = PC1.getText().toString() + PC2.getText().toString() + PC3.getText().toString()
                                + PC4.getText().toString()+ PC5.getText().toString()  + PC6.getText().toString();

                        if (pwd1.equals(Recpwd)) {

                            userDefault.setPasscodeB(true);
                            userDefault.setPasscode(Recpwd);

                            Toast.makeText(getActivity(), !userDefault.IDLanguage() ? getString(R.string.passcode_success) : getString(R.string.passcode_success_id),
                                    Toast.LENGTH_LONG).show();
                                //New Passcode
//                            getActivity().onBackPressed();
                            ((MainActivity) getActivity()).setBaseView();
                            ((MainActivity) getActivity()).cFragmentNoBS(new FragmentHome());
                        } else {
//                            PC5.requestFocus();
//                            PC6.requestFocus();
                            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                            String Message = userDefault.IDLanguage() ? getString(R.string.passcode_not_match_id) : getString(R.string.passcode_not_match);
                            String Header = userDefault.IDLanguage() ?
                                    getResources().getString(R.string.id_failure) :
                                    getResources().getString(R.string.en_failure);
//                            ClearTxt();
                            showNotif(Message, Header);
//                            DialogUtil.sNotDialog1Btn(getActivity(),
//                                    !userDefault.IDLanguage() ? getString(R.string.en_failure) : getString(R.string.id_failure),
//                                    !userDefault.IDLanguage() ? getString(R.string.passcode_not_match) : getString(R.string.passcode_not_match_id),
//                                    new DialogInterface.OnDismissListener() {
//                                        @Override
//                                        public void onDismiss(DialogInterface dialogInterface) {
//                                            ClearTxt();
//                                            DialogUtil.hProDialog();
//                                        }
//                                    }
//                            );
//                            PC1.setText("");
//                            PC2.setText("");
//                            PC3.setText("");
//                            PC4.setText("");
//                            PC5.setText("");
//                            PC6.setText("");
//                            PC1.requestFocus();

                        }
                    }

                }
            }
        });

        ClearTxt();
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
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

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

    @Override
    public void onResume() {
        super.onResume();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(PC1, InputMethodManager.SHOW_IMPLICIT);
            }
        };
        final Timer timer = new Timer();
        timer.schedule(tt, 200);
    }

    private void InitBahasa() {
        if (userDefault.IDLanguage()) {
            txtSet.setText("Atur kode sandi Anda");
            txtDescPassCode.setText("Jika Anda mengatur kode sandi, Anda harus memasukkannya setiap kali mengakses aplikasi Starbucks");
        }

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
