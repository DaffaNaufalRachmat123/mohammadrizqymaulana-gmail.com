package com.starbucks.id.model.reward_history;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Angga N P on 7/12/2018.
 */
public class RewardEventStreamPayloadModel {
    @SerializedName("event_type_slug")
    @Expose
    private String eventTypeSlug;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("event_type_name")
    @Expose
    private String eventTypeName;
    @SerializedName("offer_id")
    @Expose
    private Integer offerId;
    @SerializedName("redemption_date")
    @Expose
    private String redemptionDate;
    @SerializedName("rewards_system_id")
    @Expose
    private String rewardsSystemId;
    @SerializedName("event_category_slug")
    @Expose
    private String eventCategorySlug;
    @SerializedName("player_id")
    @Expose
    private Integer playerId;
    @SerializedName("event_category_name")
    @Expose
    private String eventCategoryName;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("reward_name")
    @Expose
    private String rewardName;
    @SerializedName("request_id")
    @Expose
    private String requestId;

    public String getEventTypeSlug() {
        return eventTypeSlug;
    }

    public void setEventTypeSlug(String eventTypeSlug) {
        this.eventTypeSlug = eventTypeSlug;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getEventTypeName() {
        return eventTypeName;
    }

    public void setEventTypeName(String eventTypeName) {
        this.eventTypeName = eventTypeName;
    }

    public Integer getOfferId() {
        return offerId;
    }

    public void setOfferId(Integer offerId) {
        this.offerId = offerId;
    }

    public String getRedemptionDate() {
        return redemptionDate;
    }

    public void setRedemptionDate(String redemptionDate) {
        this.redemptionDate = redemptionDate;
    }

    public String getRewardsSystemId() {
        return rewardsSystemId;
    }

    public void setRewardsSystemId(String rewardsSystemId) {
        this.rewardsSystemId = rewardsSystemId;
    }

    public String getEventCategorySlug() {
        return eventCategorySlug;
    }

    public void setEventCategorySlug(String eventCategorySlug) {
        this.eventCategorySlug = eventCategorySlug;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public String getEventCategoryName() {
        return eventCategoryName;
    }

    public void setEventCategoryName(String eventCategoryName) {
        this.eventCategoryName = eventCategoryName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRewardName() {
        return rewardName;
    }

    public void setRewardName(String rewardName) {
        this.rewardName = rewardName;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
