package com.starbucks.id.model.inbox;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Angga N P on 2/22/2019.
 */
public class ResponseMessageCount {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("returnCode")
    @Expose
    private String returnCode;
    @SerializedName("processMsg")
    @Expose
    private String processMsg;
    @SerializedName("transaction_time")
    @Expose
    private String transactionTime;
    @SerializedName("total_inbox")
    @Expose
    private Integer totalInbox;
    @SerializedName("total_new")
    @Expose
    private Integer totalNew;
    @SerializedName("total_read")
    @Expose
    private Integer totalRead;

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

    public String getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }

    public Integer getTotalInbox() {
        return totalInbox;
    }

    public void setTotalInbox(Integer totalInbox) {
        this.totalInbox = totalInbox;
    }

    public Integer getTotalNew() {
        return totalNew;
    }

    public void setTotalNew(Integer totalNew) {
        this.totalNew = totalNew;
    }

    public Integer getTotalRead() {
        return totalRead;
    }

    public void setTotalRead(Integer totalRead) {
        this.totalRead = totalRead;
    }
}
