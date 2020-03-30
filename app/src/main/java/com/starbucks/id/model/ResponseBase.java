package com.starbucks.id.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Angga N P on 5/8/2018.
 */
public class ResponseBase {


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
    private String details;

    @SerializedName("data")
    @Expose
    private DetailsPremium data;

    @SerializedName("status_user")
    @Expose
    private String status_user;

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

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public DetailsPremium getData() {
        return data;
    }

    public void setData(DetailsPremium data) {
        this.data = data;
    }

    public String getStatus_user() {
        return status_user;
    }

    public void setStatus_user(String status_user) {
        this.status_user = status_user;
    }

    public class DetailsPremium {

        @SerializedName("landing_title")
        @Expose
        private String landing_title;

        @SerializedName("detail_content")
        @Expose
        private String detail_content;

        public String getLanding_title() {
            return landing_title;
        }

        public void setLanding_title(String landing_title) {
            this.landing_title = landing_title;
        }

        public String getDetail_content() {
            return detail_content;
        }

        public void setDetail_content(String detail_content) {
            this.detail_content = detail_content;
        }
    }

}
