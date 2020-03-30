package com.starbucks.id.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Angga N P on 6/25/2018.
 */
public class UserResponseModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("user")
    @Expose
    private UserDetailModel user;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserDetailModel getUser() {
        return user;
    }

    public void setUser(UserDetailModel user) {
        this.user = user;
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
