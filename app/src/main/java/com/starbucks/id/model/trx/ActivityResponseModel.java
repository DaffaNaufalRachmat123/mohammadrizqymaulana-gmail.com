package com.starbucks.id.model.trx;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Angga N P on 7/3/2018.
 */
public class ActivityResponseModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("result")
    @Expose
    private List<ActivityResultModel> result = null;
    @SerializedName("grouping_field")
    @Expose
    private Object groupingField;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ActivityResultModel> getResult() {
        return result;
    }

    public void setResult(List<ActivityResultModel> result) {
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
