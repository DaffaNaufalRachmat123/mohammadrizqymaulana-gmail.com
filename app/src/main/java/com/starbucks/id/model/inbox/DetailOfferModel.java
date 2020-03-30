package com.starbucks.id.model.inbox;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailOfferModel {
    @SerializedName("campaign_type")
    @Expose
    private String campaign_type;

    @SerializedName("progress_detail")
    @Expose
    private String progress_detail;

    @SerializedName("stars_image")
    @Expose
    private String stars_image;

    @SerializedName("progress_title_1")
    @Expose
    private String progress_title_1;

    @SerializedName("progress_title_2")
    @Expose
    private String progress_title_2;

    @SerializedName("book_image")
    @Expose
    private String book_image;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("term_and_con")
    @Expose
    private String term_and_con;

    @SerializedName("precentage")
    @Expose
    private String precentage;

    @SerializedName("image_detail")
    @Expose
    private String image_detail;

    @SerializedName("image_progress")
    @Expose
    private String image_progress;


    public String getCampaign_type() {
        return campaign_type;
    }

    public void setCampaign_type(String campaign_type) {
        this.campaign_type = campaign_type;
    }

    public String getProgress_detail() {
        return progress_detail;
    }

    public void setProgress_detail(String progress_detail) {
        this.progress_detail = progress_detail;
    }

    public String getStars_image() {
        return stars_image;
    }

    public void setStars_image(String stars_image) {
        this.stars_image = stars_image;
    }

    public String getProgress_title_1() {
        return progress_title_1;
    }

    public void setProgress_title_1(String progress_title_1) {
        this.progress_title_1 = progress_title_1;
    }

    public String getProgress_title_2() {
        return progress_title_2;
    }

    public void setProgress_title_2(String progress_title_2) {
        this.progress_title_2 = progress_title_2;
    }

    public String getBook_image() {
        return book_image;
    }

    public void setBook_image(String book_image) {
        this.book_image = book_image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTerm_and_con() {
        return term_and_con;
    }

    public void setTerm_and_con(String term_and_con) {
        this.term_and_con = term_and_con;
    }

    public String getPrecentage() {
        return precentage;
    }

    public void setPrecentage(String precentage) {
        this.precentage = precentage;
    }

    public String getImage_detail() {
        return image_detail;
    }

    public void setImage_detail(String image_detail) {
        this.image_detail = image_detail;
    }

    public String getImage_progress() {
        return image_progress;
    }

    public void setImage_progress(String image_progress) {
        this.image_progress = image_progress;
    }
}
