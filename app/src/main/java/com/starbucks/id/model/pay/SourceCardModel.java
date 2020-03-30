package com.starbucks.id.model.pay;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Angga N P on 5/9/2018.
 */
public class SourceCardModel {

    @SerializedName("cashCardNumber")
    @Expose
    private String cashCardNumber;
    @SerializedName("cardBalance")
    @Expose
    private String cardBalance;

    public String getCashCardNumber() {
        return cashCardNumber;
    }

    public void setCashCardNumber(String cashCardNumber) {
        this.cashCardNumber = cashCardNumber;
    }

    public String getCardBalance() {
        return cardBalance;
    }

    public void setCardBalance(String cardBalance) {
        this.cardBalance = cardBalance;
    }
}
