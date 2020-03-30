package com.starbucks.id.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Angga N P on 6/25/2018.
 */
public class UserPhoneNumberModel {

    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("preference_flags")
    @Expose
    private List<Object> preferenceFlags = null;
    @SerializedName("verified_ownership")
    @Expose
    private Boolean verifiedOwnership;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Object> getPreferenceFlags() {
        return preferenceFlags;
    }

    public void setPreferenceFlags(List<Object> preferenceFlags) {
        this.preferenceFlags = preferenceFlags;
    }

    public Boolean getVerifiedOwnership() {
        return verifiedOwnership;
    }

    public void setVerifiedOwnership(Boolean verifiedOwnership) {
        this.verifiedOwnership = verifiedOwnership;
    }

}
