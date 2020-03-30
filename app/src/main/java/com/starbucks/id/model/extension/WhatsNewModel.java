package com.starbucks.id.model.extension;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Angga N P on 5/18/2018.
 */
public class WhatsNewModel {


    @SerializedName("wn_id")
    @Expose
    private String wnId;
    @SerializedName("landing_title_en")
    @Expose
    private String landingTitleEn;
    @SerializedName("landing_title_id")
    @Expose
    private String landingTitleId;
    @SerializedName("detail_content_en")
    @Expose
    private String detailContentEn;
    @SerializedName("detail_content_id")
    @Expose
    private String detailContentId;
    @SerializedName("image_landing_url")
    @Expose
    private String imageLandingUrl;
    @SerializedName("image_content_url")
    @Expose
    private String imageContentUrl;
    @SerializedName("begin_date")
    @Expose
    private String beginDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("is_delete")
    @Expose
    private String isDelete;

    public String getWnId() {
        return wnId;
    }

    public void setWnId(String wnId) {
        this.wnId = wnId;
    }

    public String getLandingTitleEn() {
        return landingTitleEn;
    }

    public void setLandingTitleEn(String landingTitleEn) {
        this.landingTitleEn = landingTitleEn;
    }

    public String getLandingTitleId() {
        return landingTitleId;
    }

    public void setLandingTitleId(String landingTitleId) {
        this.landingTitleId = landingTitleId;
    }

    public String getDetailContentEn() {
        return detailContentEn;
    }

    public void setDetailContentEn(String detailContentEn) {
        this.detailContentEn = detailContentEn;
    }

    public String getDetailContentId() {
        return detailContentId;
    }

    public void setDetailContentId(String detailContentId) {
        this.detailContentId = detailContentId;
    }

    public String getImageLandingUrl() {
        return imageLandingUrl;
    }

    public void setImageLandingUrl(String imageLandingUrl) {
        this.imageLandingUrl = imageLandingUrl;
    }

    public String getImageContentUrl() {
        return imageContentUrl;
    }

    public void setImageContentUrl(String imageContentUrl) {
        this.imageContentUrl = imageContentUrl;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

}
