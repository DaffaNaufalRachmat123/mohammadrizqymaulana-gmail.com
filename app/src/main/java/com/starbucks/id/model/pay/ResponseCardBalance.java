package com.starbucks.id.model.pay;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Angga N P on 5/8/2018.
 */
public class ResponseCardBalance {

    @SerializedName("status")
    @Expose
    private String status;
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
    @SerializedName("details")
    @Expose
    private DetailsCardBalance details;
    @SerializedName("sessionm")
    @Expose
    private List<Object> sessionm = null;

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

    public DetailsCardBalance getDetails() {
        return details;
    }

    public void setDetails(DetailsCardBalance details) {
        this.details = details;
    }

    public List<Object> getSessionm() {
        return sessionm;
    }

    public void setSessionm(List<Object> sessionm) {
        this.sessionm = sessionm;
    }


}
