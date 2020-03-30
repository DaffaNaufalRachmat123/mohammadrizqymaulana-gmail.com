package com.starbucks.id.model.reward;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Angga N P on 6/29/2018.
 */
public class UserOfferModel {

    @SerializedName("offer_id")
    @Expose
    private String offerId;
    @SerializedName("offer_group_id")
    @Expose
    private String offerGroupId;
    @SerializedName("offer_type")
    @Expose
    private String offerType;
    @SerializedName("redemption_start_date")
    @Expose
    private String redemptionStartDate;
    @SerializedName("redemption_end_date")
    @Expose
    private String redemptionEndDate;
    @SerializedName("acquire_date")
    @Expose
    private String acquireDate;
    @SerializedName("is_redeemable")
    @Expose
    private Boolean isRedeemable;
    @SerializedName("check_level")
    @Expose
    private Boolean checkLevel;
    @SerializedName("pending_extended_data")
    @Expose
    private Boolean pendingExtendedData;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("expiration_date")
    @Expose
    private String expirationDate;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("terms")
    @Expose
    private String terms;
    @SerializedName("culture")
    @Expose
    private String culture;
    @SerializedName("media")
    @Expose
    private List<MediumModel> media = null;

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getOfferGroupId() {
        return offerGroupId;
    }

    public void setOfferGroupId(String offerGroupId) {
        this.offerGroupId = offerGroupId;
    }

    public String getOfferType() {
        return offerType;
    }

    public void setOfferType(String offerType) {
        this.offerType = offerType;
    }

    public String getRedemptionStartDate() {
        return redemptionStartDate;
    }

    public void setRedemptionStartDate(String redemptionStartDate) {
        this.redemptionStartDate = redemptionStartDate;
    }

    public String getRedemptionEndDate() {
        return redemptionEndDate;
    }

    public void setRedemptionEndDate(String redemptionEndDate) {
        this.redemptionEndDate = redemptionEndDate;
    }

    public String getAcquireDate() {
        return acquireDate;
    }

    public void setAcquireDate(String acquireDate) {
        this.acquireDate = acquireDate;
    }

    public Boolean getIsRedeemable() {
        return isRedeemable;
    }

    public void setIsRedeemable(Boolean isRedeemable) {
        this.isRedeemable = isRedeemable;
    }

    public Boolean getCheckLevel() {
        return checkLevel;
    }

    public void setCheckLevel(Boolean checkLevel) {
        this.checkLevel = checkLevel;
    }

    public Boolean getPendingExtendedData() {
        return pendingExtendedData;
    }

    public void setPendingExtendedData(Boolean pendingExtendedData) {
        this.pendingExtendedData = pendingExtendedData;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public String getCulture() {
        return culture;
    }

    public void setCulture(String culture) {
        this.culture = culture;
    }

    public List<MediumModel> getMedia() {
        return media;
    }

    public void setMedia(List<MediumModel> media) {
        this.media = media;
    }
}
