package com.starbucks.id.model.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Angga N P on 5/8/2018.
 */
public class DetailsSignUp {

    @SerializedName("card_number")
    @Expose
    private String cardNumber;
    @SerializedName("account")
    @Expose
    private String account;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
