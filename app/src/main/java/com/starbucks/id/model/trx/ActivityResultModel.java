package com.starbucks.id.model.trx;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Angga N P on 7/3/2018.
 */
public class ActivityResultModel {

    @SerializedName("target_id")
    @Expose
    private Integer targetId;
    @SerializedName("event_stream_stream_id")
    @Expose
    private String eventStreamStreamId;
    @SerializedName("event_stream_event_type_id")
    @Expose
    private Integer eventStreamEventTypeId;
    @SerializedName("event_stream_event_category_id")
    @Expose
    private Integer eventStreamEventCategoryId;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("event_stream_payload")
    @Expose
    private ActivityEventStreamPayloadModel eventStreamPayload;
    @SerializedName("contexts")
    @Expose
    private List<ActivityContextModel> contexts = null;
    @SerializedName("transaction_name_id")
    @Expose
    private String transactionNameId;
    @SerializedName("transaction_name_en")
    @Expose
    private String transactionNameEn;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("cardnumber")
    @Expose
    private String cardNumber;
    @SerializedName("base_stars")
    @Expose
    private String starsEarn;
    @SerializedName("bonus_stars")
    @Expose
    private String bonusStars;



    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    public String getEventStreamStreamId() {
        return eventStreamStreamId;
    }

    public void setEventStreamStreamId(String eventStreamStreamId) {
        this.eventStreamStreamId = eventStreamStreamId;
    }

    public Integer getEventStreamEventTypeId() {
        return eventStreamEventTypeId;
    }

    public void setEventStreamEventTypeId(Integer eventStreamEventTypeId) {
        this.eventStreamEventTypeId = eventStreamEventTypeId;
    }

    public Integer getEventStreamEventCategoryId() {
        return eventStreamEventCategoryId;
    }

    public void setEventStreamEventCategoryId(Integer eventStreamEventCategoryId) {
        this.eventStreamEventCategoryId = eventStreamEventCategoryId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public ActivityEventStreamPayloadModel getEventStreamPayload() {
        return eventStreamPayload;
    }

    public void setEventStreamPayload(ActivityEventStreamPayloadModel eventStreamPayload) {
        this.eventStreamPayload = eventStreamPayload;
    }

    public List<ActivityContextModel> getContexts() {
        return contexts;
    }

    public void setContexts(List<ActivityContextModel> contexts) {
        this.contexts = contexts;
    }


    public String getTransactionNameId() {
        return transactionNameId;
    }

    public void setTransactionNameId(String transactionNameId) {
        this.transactionNameId = transactionNameId;
    }

    public String getTransactionNameEn() {
        return transactionNameEn;
    }

    public void setTransactionNameEn(String transactionNameEn) {
        this.transactionNameEn = transactionNameEn;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getStarsEarn() {
        return starsEarn;
    }

    public void setStarsEarn(String starsEarn) {
        this.starsEarn = starsEarn;
    }

    public String getBonusStars() {
        return bonusStars;
    }

    public void setBonusStars(String bonusStars) {
        this.bonusStars = bonusStars;
    }
}
