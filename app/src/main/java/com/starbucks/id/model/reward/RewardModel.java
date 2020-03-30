package com.starbucks.id.model.reward;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Angga N P on 6/29/2018.
 */
public class RewardModel {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("payload")
    @Expose
    private PayloadModel payload;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PayloadModel getPayload() {
        return payload;
    }

    public void setPayload(PayloadModel payload) {
        this.payload = payload;
    }

    @SerializedName("processMsg")
    @Expose
    private String processMsg;

    public String getProcessMsg() {
        return processMsg;
    }

    public void setProcessMsg(String processMsg) {
        this.processMsg = processMsg;
    }
}
