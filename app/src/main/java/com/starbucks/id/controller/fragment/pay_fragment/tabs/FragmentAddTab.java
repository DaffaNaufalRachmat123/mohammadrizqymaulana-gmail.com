package com.starbucks.id.controller.fragment.pay_fragment.tabs;

/*
 * Created by Angga N P on 3/13/2018.
 */

import android.content.Context;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.starbucks.id.R;
import com.starbucks.id.controller.activity.MainActivity;
import com.starbucks.id.helper.UserDefault;
import com.starbucks.id.helper.utils.DialogUtil;
import com.starbucks.id.model.ResponseBase;
import com.starbucks.id.model.user.UserResponseModel;
import com.starbucks.id.rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentAddTab extends Fragment {
    private TextView tvTitle, tvSub;
    private TextInputEditText etCard, etSecurity;
    private Button btAdd;
    private UserDefault userDefault;
    private ApiInterface apiService;
    private ConstraintLayout CLBase;
    private UserResponseModel userResponseModel;
    private TextInputLayout tilPACCNumber;

    private MainActivity activity;

    public FragmentAddTab() {
        // Required empty public constructor
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
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_pay_add_tab, container, false);

        setHasOptionsMenu(true);

        tvTitle = rootView.findViewById(R.id.detailDescription);
        tvSub = rootView.findViewById(R.id.tvTraFro);
        etCard = rootView.findViewById(R.id.etCard);
        etSecurity = rootView.findViewById(R.id.etSecurity);
        btAdd = rootView.findViewById(R.id.btAdd);
        tilPACCNumber = rootView.findViewById(R.id.tilPACCNumber);
        CLBase = rootView.findViewById(R.id.CLBase);
        if (getActivity() != null) setBaseView();

        return rootView;
    }

    /*UI Controller*/
    public void setBaseView() {
        userResponseModel = activity.getUser();
        if(userResponseModel.getUser()!=null){
            CLBase.setVisibility(View.VISIBLE);
            if (activity.getUser() != null)
                userResponseModel = activity.getUser();

            if (userDefault.IDLanguage()) {
                tvTitle.setText(R.string.title_add_card_id);
                tvSub.setText(R.string.subtitle_add_card_id);
                tilPACCNumber.setHint(getString(R.string.hint_card_number_id));
                btAdd.setText(R.string.submit_id);
            }
        }else{
            CLBase.setVisibility(View.INVISIBLE);
            activity.getUserData();
        }
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValid()) addCard();
            }


        });
    }

//    /*UI Controller*/
//    private void setBaseView() {
//        if (activity.getUser() != null)
//            userResponseModel = activity.getUser();
//
//        if (userDefault.IDLanguage()) {
//            tvTitle.setText(R.string.title_add_card_id);
//            tvSub.setText(R.string.subtitle_add_card_id);
//            tilPACCNumber.setHint(getString(R.string.hint_card_number_id));
//            btAdd.setText(R.string.submit_id);
//        }
//
//        btAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (isValid()) addCard();
//            }
//
//
//        });
//    }

    private boolean isValid() {
        String card_no = etCard.getText().toString();
        String pin = etSecurity.getText().toString();

        if (card_no.isEmpty()) {
            Toast.makeText(getActivity(),userDefault.IDLanguage() ?
                    "Masukan Nomor Kartu Anda" : "Please fill Your card number",Toast.LENGTH_LONG).show();
            return false;
        } else if (card_no.length() < 16) {
            Toast.makeText(getActivity(),userDefault.IDLanguage() ?
                    "Masukan setidaknya 16 Angka Nomor Kartu" : "Please enter at least 16 number",Toast.LENGTH_LONG).show();
            return false;
        } else if (pin.isEmpty()) {
            Toast.makeText(getActivity(),userDefault.IDLanguage() ?
                    "Masukan nomor pin Anda" : "Please fill Your pin number",Toast.LENGTH_LONG).show();
            return false;
        }else if (pin.length() < 8) {
            Toast.makeText(getActivity(),userDefault.IDLanguage() ?
                    "Masukan setidaknya 8 Angka PIN" : "Please enter at least 8 PIN number",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void hiddenKeyboard(View v) {
        InputMethodManager keyboard = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        keyboard.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    private void clearET() {
        etCard.setText("");
        etSecurity.setText("");
    }


    /*REST CALL*/
    // Add Card to Server
    private void addCard() {
        if (!activity.checkConnection()) return;
        if (apiService == null) apiService = activity.getApiService();

        View focus = getActivity().getCurrentFocus();
        if (focus != null) hiddenKeyboard(focus);

        DialogUtil.sProDialog(getActivity());

        Call<ResponseBase> call = apiService.addCard("add_card",
                activity.genSBUX(
                        "email=" + userDefault.getEmail() +
                                "&card_number=" + userResponseModel.getUser().getExternalId() +
                                "&addcard=" + etCard.getText() +
                                "&pin=" + etSecurity.getText()
                ));

        call.enqueue(new Callback<ResponseBase>() {
            @Override
            public void onResponse(Call<ResponseBase> call, Response<ResponseBase> response) {
                if (getActivity() != null) {
                    DialogUtil.hProDialog();

                    if (response.isSuccessful() && response.body() != null) {
                        if (activity.successResponse(response.body().getReturnCode(), response.body().getProcessMsg())) {
                            if(response.body().getStatus().equals("Failure")){
                                clearET();
                                Toast.makeText(getActivity(),response.body().getProcessMsg(),Toast.LENGTH_LONG).show();
                            }else{
                                clearET();
                                Toast.makeText(getActivity(),response.body().getProcessMsg(),Toast.LENGTH_LONG).show();
                                activity.getUserData();
                            }
                        } else {
                            clearET();
                            activity.showToast(response.body().getProcessMsg());
                        }
                    } else {
                        clearET();
                        activity.checkServer(String.valueOf(response.code()), response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBase> call, Throwable t) {
                if (getActivity() != null && isAdded() && t != null) {
                    activity.restFailure(t);
                    clearET();
                    DialogUtil.hProDialog();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        clearET();
    }
}