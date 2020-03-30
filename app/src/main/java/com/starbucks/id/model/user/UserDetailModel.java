package com.starbucks.id.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Angga N P on 6/25/2018.
 */
public class UserDetailModel {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("external_id")
    @Expose
    private String externalId;
    @SerializedName("identifiers")
    @Expose
    private List<UserIdentifierModel> identifiers = null;
    @SerializedName("available_points")
    @Expose
    private Integer availablePoints;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("registered_at")
    @Expose
    private String registeredAt;
    @SerializedName("next_tier_points")
    @Expose
    private Integer nextTierPoints;
    @SerializedName("tier_entered_at")
    @Expose
    private String tierEnteredAt;
    @SerializedName("user_profile")
    @Expose
    private UserProfileModel userProfile;
    @SerializedName("total_offer")
    @Expose
    private Integer totalOffer;
    @SerializedName("phone_numbers")
    @Expose
    private List<UserPhoneNumberModel> phoneNumbers = null;
    @SerializedName("tier")
    @Expose
    private String tier;

    @SerializedName("premium_status_code")
    @Expose
    private String premiumStatusCode;

    @SerializedName("premium_label")
    @Expose
    private String premiumLabel;

    @SerializedName("premium_description")
    @Expose
    private String premiumDescription;

    @SerializedName("status_phone_number_verify")
    @Expose
    private int phoneNumberVerify;

    @SerializedName("alternate_phone_number")
    @Expose
    private String altPhoneNumber;


    @SerializedName("status_user")
    @Expose
    private String statusUser;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExternalId() {
        return externalId;
    }

    public List<UserIdentifierModel> getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(List<UserIdentifierModel> identifiers) {
        this.identifiers = identifiers;
    }

    public Integer getAvailablePoints() {
        return availablePoints;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public List<UserPhoneNumberModel> getPhoneNumbers() {
        return phoneNumbers;
    }

    public String getTier() {
        return tier;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getRegisteredAt() {
        return registeredAt;
    }

    public Integer getNextTierPoints() {
        return nextTierPoints;
    }

    public String getTierEnteredAt() {
        return tierEnteredAt;
    }

    public UserProfileModel getUserProfile() {
        return userProfile;
    }

    public Integer getTotalOffer() {
        return totalOffer;
    }

    public String getPremiumStatusCode() {
        return premiumStatusCode;
    }

    public void setPremiumStatusCode(String premiumStatusCode) {
        this.premiumStatusCode = premiumStatusCode;
    }

    public String getPremiumLabel() {
        return premiumLabel;
    }

    public void setPremiumLabel(String premiumLabel) {
        this.premiumLabel = premiumLabel;
    }

    public String getPremiumDescription() {
        return premiumDescription;
    }

    public void setPremiumDescription(String premiumDescription) {
        this.premiumDescription = premiumDescription;
    }

    public int getPhoneNumberVerify() {
        return phoneNumberVerify;
    }

    public void setPhoneNumberVerify(int phoneNumberVerify) {
        this.phoneNumberVerify = phoneNumberVerify;
    }

    public String getAltPhoneNumber() {
        return altPhoneNumber;
    }

    public void setAltPhoneNumber(String altPhoneNumber) {
        this.altPhoneNumber = altPhoneNumber;
    }

    public String getStatusUser() {
        return statusUser;
    }

    public void setStatusUser(String statusUser) {
        this.statusUser = statusUser;
    }
}