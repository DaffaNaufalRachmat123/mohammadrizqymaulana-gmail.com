package com.starbucks.id.model.pay;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Angga N P on 5/9/2018.
 */
public class DetailsTransferBalance {

    @SerializedName("cardAuthCode")
    @Expose
    private String cardAuthCode;
    @SerializedName("custEmailID")
    @Expose
    private String custEmailID;
    @SerializedName("txnLifeCycleId")
    @Expose
    private Object txnLifeCycleId;
    @SerializedName("sourceCard")
    @Expose
    private SourceCardModel sourceCard;
    @SerializedName("xferCard")
    @Expose
    private XferCard xferCard;

    public String getCardAuthCode() {
        return cardAuthCode;
    }

    public void setCardAuthCode(String cardAuthCode) {
        this.cardAuthCode = cardAuthCode;
    }

    public String getCustEmailID() {
        return custEmailID;
    }

    public void setCustEmailID(String custEmailID) {
        this.custEmailID = custEmailID;
    }

    public Object getTxnLifeCycleId() {
        return txnLifeCycleId;
    }

    public void setTxnLifeCycleId(Object txnLifeCycleId) {
        this.txnLifeCycleId = txnLifeCycleId;
    }

    public SourceCardModel getSourceCard() {
        return sourceCard;
    }

    public void setSourceCard(SourceCardModel sourceCard) {
        this.sourceCard = sourceCard;
    }

    public XferCard getXferCard() {
        return xferCard;
    }

    public void setXferCard(XferCard xferCard) {
        this.xferCard = xferCard;
    }
}
