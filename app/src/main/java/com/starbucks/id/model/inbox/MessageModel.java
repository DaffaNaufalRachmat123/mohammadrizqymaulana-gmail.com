package com.starbucks.id.model.inbox;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Angga N P on 5/18/2018.
 */
public class MessageModel {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("is_delete")
    @Expose
    private Integer isDelete;
    @SerializedName("msg_id")
    @Expose
    private String msgId;
    @SerializedName("msg_subject")
    @Expose
    private String msgSubject;
    @SerializedName("msg_image")
    @Expose
    private String msgImage;
    @SerializedName("msg_dtl")
    @Expose
    private String msgDtl;
    @SerializedName("msg_type")
    @Expose
    private String msgType;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("state")
    @Expose
    private Integer state;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getMsgSubject() {
        return msgSubject;
    }

    public void setMsgSubject(String msgSubject) {
        this.msgSubject = msgSubject;
    }

    public String getMsgImage() {
        return msgImage;
    }

    public void setMsgImage(String msgImage) {
        this.msgImage = msgImage;
    }

    public String getMsgDtl() {
        return msgDtl;
    }

    public void setMsgDtl(String msgDtl) {
        this.msgDtl = msgDtl;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
