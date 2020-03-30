package com.starbucks.id.model.reward;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Angga N P on 6/29/2018.
 */
public class PayloadModel {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("user_offers")
    @Expose
    private List<UserOfferModel> userOffers = null;
    @SerializedName("total_records")
    @Expose
    private Integer totalRecords;
    @SerializedName("offer_groups")
    @Expose
    private List<OfferGroupModel> offerGroups = null;
    @SerializedName("offer_categories")
    @Expose
    private List<Object> offerCategories = null;
    @SerializedName("total_points")
    @Expose
    private Integer totalPoints;
    @SerializedName("available_points")
    @Expose
    private Integer availablePoints;
    @SerializedName("totaloffer")
    @Expose
    private Integer totaloffer;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<UserOfferModel> getUserOffers() {
        return userOffers;
    }

    public void setUserOffers(List<UserOfferModel> userOffers) {
        this.userOffers = userOffers;
    }

    public Integer getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(Integer totalRecords) {
        this.totalRecords = totalRecords;
    }

    public List<OfferGroupModel> getOfferGroups() {
        return offerGroups;
    }

    public void setOfferGroups(List<OfferGroupModel> offerGroups) {
        this.offerGroups = offerGroups;
    }

    public List<Object> getOfferCategories() {
        return offerCategories;
    }

    public void setOfferCategories(List<Object> offerCategories) {
        this.offerCategories = offerCategories;
    }

    public Integer getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
    }

    public Integer getAvailablePoints() {
        return availablePoints;
    }

    public void setAvailablePoints(Integer availablePoints) {
        this.availablePoints = availablePoints;
    }

    public Integer getTotaloffer() {
        return totaloffer;
    }

    public void setTotaloffer(Integer totaloffer) {
        this.totaloffer = totaloffer;
    }
}
