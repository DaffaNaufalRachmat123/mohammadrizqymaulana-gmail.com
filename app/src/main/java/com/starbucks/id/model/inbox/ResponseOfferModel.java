package com.starbucks.id.model.inbox;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseOfferModel {

    @SerializedName("campaignList")
    @Expose
    public List<OfferModel> campaignList = null;

    @SerializedName("status")
    @Expose
    public String status;

    @SerializedName("processMsg")
    @Expose
    public String processMsg;


    public List<OfferModel> getCampaignList() {
        return campaignList;
    }

    public void setCampaignList(List<OfferModel> campaignList) {
        this.campaignList = campaignList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProcessMsg() {
        return processMsg;
    }

    public void setProcessMsg(String processMsg) {
        this.processMsg = processMsg;
    }
}
