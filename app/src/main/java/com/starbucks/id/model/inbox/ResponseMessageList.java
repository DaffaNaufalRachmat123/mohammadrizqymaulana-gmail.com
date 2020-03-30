package com.starbucks.id.model.inbox;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Angga N P on 2/22/2019.
 */
public class ResponseMessageList {
    @SerializedName("messages")
    @Expose
    private List<MessageModel> messages = null;
    @SerializedName("returnCode")
    @Expose
    private String returnCode;

    @SerializedName("status")
    @Expose
    public String status;

    @SerializedName("processMsg")
    @Expose
    private String processMsg;
    @SerializedName("transaction_time")
    @Expose
    private String transactionTime;

    public List<MessageModel> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageModel> messages) {
        this.messages = messages;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
}
