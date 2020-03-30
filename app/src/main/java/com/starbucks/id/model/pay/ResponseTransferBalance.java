package com.starbucks.id.model.pay;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Angga N P on 5/9/2018.
 */

public class ResponseTransferBalance {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("returnCode")
    @Expose
    private String returnCode;
    @SerializedName("processMsg")
    @Expose
    private String processMsg;
    @SerializedName("errMsg")
    @Expose
    private String errMsg;
    @SerializedName("trace")
    @Expose
    private String trace;
    @SerializedName("transaction_time")
    @Expose
    private String transactionTime;
    @SerializedName("details")
    @Expose
    private DetailsTransferBalance details;

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

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
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

    public DetailsTransferBalance getDetails() {
        return details;
    }

    public void setDetails(DetailsTransferBalance details) {
        this.details = details;
    }

}
