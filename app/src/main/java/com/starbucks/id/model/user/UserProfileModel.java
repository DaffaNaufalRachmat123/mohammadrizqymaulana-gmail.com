package com.starbucks.id.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Angga N P on 6/25/2018.
 */
public class UserProfileModel {

    @SerializedName("_version")
    @Expose
    private Integer version;
    @SerializedName("fav_beverage")
    @Expose
    private String favBeverage;
    @SerializedName("default_card")
    @Expose
    private String defaultCard;
    @SerializedName("direct_marcomm")
    @Expose
    private Boolean directMarcomm;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getFavBeverage() {
        return favBeverage;
    }

    public void setFavBeverage(String favBeverage) {
        this.favBeverage = favBeverage;
    }

    public String getDefaultCard() {
        return defaultCard;
    }

    public void setDefaultCard(String defaultCard) {
        this.defaultCard = defaultCard;
    }

    public Boolean getDirectMarcomm() {
        return directMarcomm;
    }

    public void setDirectMarcomm(Boolean directMarcomm) {
        this.directMarcomm = directMarcomm;
    }


}
