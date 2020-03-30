package com.starbucks.id.model.pay;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Angga N P on 6/30/2018.
 */
public class ReportLostDetails {

    @SerializedName("cardAuthCode")
    @Expose
    private String cardAuthCode;
    @SerializedName("custEmailID")
    @Expose
    private String custEmailID;
    @SerializedName("cashCardNumber")
    @Expose
    private String cashCardNumber;
    @SerializedName("cardBalance")
    @Expose
    private String cardBalance;
    @SerializedName("cardStatus")
    @Expose
    private String cardStatus;

    public String getCardAuthCode() {
        return cardAuthCode;
    }

    public void setCardAuthCode(String cardAuthCode) {
        this.cardAuthCode = cardAuthCode;
    }

    public String getCustEmailID() {
        return custEmailID;
    }

    public void setCustEmailID(String custEmailID) {
        this.custEmailID = custEmailID;
    }

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

    public String getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }
}
