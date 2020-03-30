package com.starbucks.id.model.pay;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Angga N P on 1/11/2019.
 */

public class CardReloadHistory implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("card")
    @Expose
    private String card;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("status_desc")
    @Expose
    private String statusDesc;
    @SerializedName("va")
    @Expose
    private String va;
    @SerializedName("bank")
    @Expose
    private String bank;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("expired_time")
    @Expose
    private String expiredTime;
    @SerializedName("bank_image")
    @Expose
    private String bankImage;
    @SerializedName("transaction_time")
    @Expose
    private String transactionTime;
    @SerializedName("server_time")
    @Expose
    private String serverTime;

    protected CardReloadHistory(Parcel in) {
        id = in.readString();
        orderId = in.readString();
        card = in.readString();
        amount = in.readString();
        status = in.readString();
        statusDesc = in.readString();
        va = in.readString();
        bank = in.readString();
        date = in.readString();
        expiredTime = in.readString();
        bankImage = in.readString();
        transactionTime = in.readString();
        serverTime = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(orderId);
        dest.writeString(card);
        dest.writeString(amount);
        dest.writeString(status);
        dest.writeString(statusDesc);
        dest.writeString(va);
        dest.writeString(bank);
        dest.writeString(date);
        dest.writeString(expiredTime);
        dest.writeString(bankImage);
        dest.writeString(transactionTime);
        dest.writeString(serverTime);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CardReloadHistory> CREATOR = new Creator<CardReloadHistory>() {
        @Override
        public CardReloadHistory createFromParcel(Parcel in) {
            return new CardReloadHistory(in);
        }

        @Override
        public CardReloadHistory[] newArray(int size) {
            return new CardReloadHistory[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getVa() {
        return va;
    }

    public void setVa(String va) {
        this.va = va;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(String expiredTime) {
        this.expiredTime = expiredTime;
    }

    public String getBankImage() {
        return bankImage;
    }

    public void setBankImage(String bankImage) {
        this.bankImage = bankImage;
    }

    public String getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getServerTime() {
        return serverTime;
    }

    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }
}
