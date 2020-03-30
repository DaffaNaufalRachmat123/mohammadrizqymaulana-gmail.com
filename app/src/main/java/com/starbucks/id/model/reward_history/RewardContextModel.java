package com.starbucks.id.model.reward_history;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Angga N P on 7/12/2018.
 */
public class RewardContextModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("value_details")
    @Expose
    private String valueDetails;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValueDetails() {
        return valueDetails;
    }

    public void setValueDetails(String valueDetails) {
        this.valueDetails = valueDetails;
    }

}
