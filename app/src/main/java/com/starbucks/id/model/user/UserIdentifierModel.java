package com.starbucks.id.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Angga N P on 6/25/2018.
 */
public class UserIdentifierModel {

    @SerializedName("external_id")
    @Expose
    private String externalId;
    @SerializedName("external_id_type")
    @Expose
    private Object externalIdType;
    @SerializedName("card_image")
    @Expose
    private String cardImage;

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public Object getExternalIdType() {
        return externalIdType;
    }

    public void setExternalIdType(Object externalIdType) {
        this.externalIdType = externalIdType;
    }

    public String getCardImage() {
        return cardImage;
    }

    public void setCardImage(String cardImage) {
        this.cardImage = cardImage;
    }
}
