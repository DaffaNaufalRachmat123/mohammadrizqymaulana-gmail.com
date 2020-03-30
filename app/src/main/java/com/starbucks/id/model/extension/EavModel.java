package com.starbucks.id.model.extension;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Angga N P on 5/24/2018.
 */

public class EavModel {

    @SerializedName("entity")
    @Expose
    private String entity;
    @SerializedName("attribute")
    @Expose
    private String attribute;
    @SerializedName("value")
    @Expose
    private String value;

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}