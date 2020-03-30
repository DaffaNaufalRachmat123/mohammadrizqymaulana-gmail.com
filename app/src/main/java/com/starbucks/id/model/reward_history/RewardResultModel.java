package com.starbucks.id.model.reward_history;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Angga N P on 7/12/2018.
 */
public class RewardResultModel {

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
    private RewardEventStreamPayloadModel eventStreamPayload;
    @SerializedName("contexts")
    @Expose
    private List<RewardContextModel> contexts = null;

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

    public RewardEventStreamPayloadModel getEventStreamPayload() {
        return eventStreamPayload;
    }

    public void setEventStreamPayload(RewardEventStreamPayloadModel eventStreamPayload) {
        this.eventStreamPayload = eventStreamPayload;
    }

    public List<RewardContextModel> getContexts() {
        return contexts;
    }

    public void setContexts(List<RewardContextModel> contexts) {
        this.contexts = contexts;
    }
}
