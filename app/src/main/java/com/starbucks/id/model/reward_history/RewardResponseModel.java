package com.starbucks.id.model.reward_history;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Angga N P on 7/12/2018.
 */
public class RewardResponseModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("result")
    @Expose
    private List<RewardResultModel> result = null;
    @SerializedName("grouping_field")
    @Expose
    private Object groupingField;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<RewardResultModel> getResult() {
        return result;
    }

    public void setResult(List<RewardResultModel> result) {
        this.result = result;
    }

    public Object getGroupingField() {
        return groupingField;
    }

    public void setGroupingField(Object groupingField) {
        this.groupingField = groupingField;
    }

    @SerializedName("processMsg")
    @Expose
    private String processMsg;

    public String getProcessMsg() {
        return processMsg;
    }

    public void setProcessMsg(String processMsg) {
        this.processMsg = processMsg;
    }
}
