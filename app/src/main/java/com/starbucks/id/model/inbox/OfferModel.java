package com.starbucks.id.model.inbox;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OfferModel {
    @SerializedName("campaign_id_mcs")
    @Expose
    private String campaign_id_mcs;
    @SerializedName("campaign_id")
    @Expose
    private String campaign_id;
    @SerializedName("campaign_type")
    @Expose
    private String campaign_type;
    @SerializedName("campaign_title_id")
    @Expose
    private String campaign_title_id;
    @SerializedName("campaign_title")
    @Expose
    private String campaign_title;
    @SerializedName("campaign_desc_id")
    @Expose
    private String campaign_desc_id;
    @SerializedName("campaign_desc")
    @Expose
    private String campaign_desc;
    @SerializedName("campaign_term_cond_id")
    @Expose
    private String campaign_term_cond_id;
    @SerializedName("campaign_term_cond")
    @Expose
    private String campaign_term_cond;
    @SerializedName("campaign_img")
    @Expose
    private String campaign_img;

    public String getCampaign_id_mcs() {
        return campaign_id_mcs;
    }

    public void setCampaign_id_mcs(String campaign_id_mcs) {
        this.campaign_id_mcs = campaign_id_mcs;
    }

    public String getCampaign_id() {
        return campaign_id;
    }

    public void setCampaign_id(String campaign_id) {
        this.campaign_id = campaign_id;
    }

    public String getCampaign_type() {
        return campaign_type;
    }

    public void setCampaign_type(String campaign_type) {
        this.campaign_type = campaign_type;
    }

    public String getCampaign_title_id() {
        return campaign_title_id;
    }

    public void setCampaign_title_id(String campaign_title_id) {
        this.campaign_title_id = campaign_title_id;
    }

    public String getCampaign_title() {
        return campaign_title;
    }

    public void setCampaign_title(String campaign_title) {
        this.campaign_title = campaign_title;
    }

    public String getCampaign_desc_id() {
        return campaign_desc_id;
    }

    public void setCampaign_desc_id(String campaign_desc_id) {
        this.campaign_desc_id = campaign_desc_id;
    }

    public String getCampaign_desc() {
        return campaign_desc;
    }

    public void setCampaign_desc(String campaign_desc) {
        this.campaign_desc = campaign_desc;
    }

    public String getCampaign_term_cond_id() {
        return campaign_term_cond_id;
    }

    public void setCampaign_term_cond_id(String campaign_term_cond_id) {
        this.campaign_term_cond_id = campaign_term_cond_id;
    }

    public String getCampaign_term_cond() {
        return campaign_term_cond;
    }

    public void setCampaign_term_cond(String campaign_term_cond) {
        this.campaign_term_cond = campaign_term_cond;
    }

    public String getCampaign_img() {
        return campaign_img;
    }

    public void setCampaign_img(String campaign_img) {
        this.campaign_img = campaign_img;
    }
}
