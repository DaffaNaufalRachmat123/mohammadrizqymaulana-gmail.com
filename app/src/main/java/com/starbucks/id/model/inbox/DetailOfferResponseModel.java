package com.starbucks.id.model.inbox;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DetailOfferResponseModel {

    @SerializedName("status")
    @Expose
    public String status;

    @SerializedName("returnCode")
    @Expose
    public String returnCode;

    @SerializedName("processMsg")
    @Expose
    public String processMsg;

    @SerializedName("data")
    @Expose
    public DetailOfferModel data = null;

    @SerializedName("transaction_time")
    @Expose
    public String transaction_time;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public DetailOfferModel getData() {
        return data;
    }

    public void setData(DetailOfferModel data) {
        this.data = data;
    }

    public String getTransaction_time() {
        return transaction_time;
    }

    public void setTransaction_time(String transaction_time) {
        this.transaction_time = transaction_time;
    }
}


