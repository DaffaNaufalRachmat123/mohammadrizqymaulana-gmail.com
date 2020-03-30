package com.starbucks.id.model.pay;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Angga N P on 1/7/2019.
 */
public class ResponseOpenTrx {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("status_desc")
    @Expose
    private String statusDesc;
    @SerializedName("returnCode")
    @Expose
    private String returnCode;
    @SerializedName("processMsg")
    @Expose
    private String processMsg;
    @SerializedName("trace")
    @Expose
    private String trace;
    @SerializedName("transaction_time")
    @Expose
    private String transactionTime;
    @SerializedName("bank")
    @Expose
    private String bank;
    @SerializedName("virtualAccount")
    @Expose
    private String virtualAccount;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("orderId")
    @Expose
    private String orderId;
    @SerializedName("expired_time")
    @Expose
    private String expiredTime;
    @SerializedName("bank_image")
    @Expose
    private String bankImage;
    @SerializedName("server_time")
    @Expose
    private String serverTime;

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

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getProcessMsg() {
        return processMsg;
    }

    public void setProcessMsg(String processMsg) {
        this.processMsg = processMsg;
    }

    public String getTrace() {
        return trace;
    }

    public void setTrace(String trace) {
        this.trace = trace;
    }

    public String getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getVirtualAccount() {
        return virtualAccount;
    }

    public void setVirtualAccount(String virtualAccount) {
        this.virtualAccount = virtualAccount;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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


    public String getServerTime() {
        return serverTime;
    }

    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }
}
