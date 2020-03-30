package com.starbucks.id.model.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Angga N P on 5/8/2018.
 */
public class DetailsSignIn {

    @SerializedName("mobile_id")
    @Expose
    private String mobileId;
    @SerializedName("device")
    @Expose
    private String device;
    @SerializedName("activation_date")
    @Expose
    private String activationDate;

    public String getMobileId() {
        return mobileId;
    }

    public void setMobileId(String mobileId) {
        this.mobileId = mobileId;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(String activationDate) {
        this.activationDate = activationDate;
    }

}
