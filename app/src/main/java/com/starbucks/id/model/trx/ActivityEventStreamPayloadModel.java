package com.starbucks.id.model.trx;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Angga N P on 7/3/2018.
 */
public class ActivityEventStreamPayloadModel {

    @SerializedName("parent_name")
    @Expose
    private String parentName;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("event_type_slug")
    @Expose
    private String eventTypeSlug;
    @SerializedName("timezone")
    @Expose
    private String timezone;
    @SerializedName("channel")
    @Expose
    private String channel;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("external_id")
    @Expose
    private ActivityExternalIdModel externalId;
    @SerializedName("rewards_system_id")
    @Expose
    private String rewardsSystemId;
    @SerializedName("player_id")
    @Expose
    private ActivityPlayerIdModel playerId;
    @SerializedName("model_type_name")
    @Expose
    private String modelTypeName;
    @SerializedName("event_category_name")
    @Expose
    private String eventCategoryName;
    @SerializedName("transaction_time")
    @Expose
    private ActivityTransactionTimeModel transactionTime;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("developer_id")
    @Expose
    private String developerId;
    @SerializedName("sub_channel")
    @Expose
    private String subChannel;
    @SerializedName("model_type_id")
    @Expose
    private String modelTypeId;
    @SerializedName("transaction_id")
    @Expose
    private String transactionId;
    @SerializedName("used_msr_reward")
    @Expose
    private String usedMsrReward;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("awarded_merchant_points")
    @Expose
    private String awardedMerchantPoints;
    @SerializedName("award_limit_reached")
    @Expose
    private String awardLimitReached;
    @SerializedName("card_number")
    @Expose
    private String cardNumber;
    @SerializedName("retailer")
    @Expose
    private String retailer;
    @SerializedName("price_amount")
    @Expose
    private String priceAmount;
    @SerializedName("store")
    @Expose
    private String store;
    @SerializedName("model_id")
    @Expose
    private String modelId;
    @SerializedName("transaction_type")
    @Expose
    private String transactionType;
    @SerializedName("application_id")
    @Expose
    private String applicationId;
    @SerializedName("event_type_name")
    @Expose
    private String eventTypeName;
    @SerializedName("override_price_amount")
    @Expose
    private String overridePriceAmount;
    @SerializedName("event_category_slug")
    @Expose
    private String eventCategorySlug;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("subtotal")
    @Expose
    private String subtotal;
    @SerializedName("qty")
    @Expose
    private String qty;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("discount_price_amount")
    @Expose
    private String discountPriceAmount;
    @SerializedName("postal_code")
    @Expose
    private String postalCode;
    @SerializedName("request_id")
    @Expose
    private String requestId;

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEventTypeSlug() {
        return eventTypeSlug;
    }

    public void setEventTypeSlug(String eventTypeSlug) {
        this.eventTypeSlug = eventTypeSlug;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ActivityExternalIdModel getExternalId() {
        return externalId;
    }

    public void setExternalId(ActivityExternalIdModel externalId) {
        this.externalId = externalId;
    }

    public String getRewardsSystemId() {
        return rewardsSystemId;
    }

    public void setRewardsSystemId(String rewardsSystemId) {
        this.rewardsSystemId = rewardsSystemId;
    }

    public ActivityPlayerIdModel getPlayerId() {
        return playerId;
    }

    public void setPlayerId(ActivityPlayerIdModel playerId) {
        this.playerId = playerId;
    }

    public String getModelTypeName() {
        return modelTypeName;
    }

    public void setModelTypeName(String modelTypeName) {
        this.modelTypeName = modelTypeName;
    }

    public String getEventCategoryName() {
        return eventCategoryName;
    }

    public void setEventCategoryName(String eventCategoryName) {
        this.eventCategoryName = eventCategoryName;
    }

    public ActivityTransactionTimeModel getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(ActivityTransactionTimeModel transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDeveloperId() {
        return developerId;
    }

    public void setDeveloperId(String developerId) {
        this.developerId = developerId;
    }

    public String getSubChannel() {
        return subChannel;
    }

    public void setSubChannel(String subChannel) {
        this.subChannel = subChannel;
    }

    public String getModelTypeId() {
        return modelTypeId;
    }

    public void setModelTypeId(String modelTypeId) {
        this.modelTypeId = modelTypeId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getUsedMsrReward() {
        return usedMsrReward;
    }

    public void setUsedMsrReward(String usedMsrReward) {
        this.usedMsrReward = usedMsrReward;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getAwardedMerchantPoints() {
        return awardedMerchantPoints;
    }

    public void setAwardedMerchantPoints(String awardedMerchantPoints) {
        this.awardedMerchantPoints = awardedMerchantPoints;
    }

    public String getAwardLimitReached() {
        return awardLimitReached;
    }

    public void setAwardLimitReached(String awardLimitReached) {
        this.awardLimitReached = awardLimitReached;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getRetailer() {
        return retailer;
    }

    public void setRetailer(String retailer) {
        this.retailer = retailer;
    }

    public String getPriceAmount() {
        return priceAmount;
    }

    public void setPriceAmount(String priceAmount) {
        this.priceAmount = priceAmount;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getEventTypeName() {
        return eventTypeName;
    }

    public void setEventTypeName(String eventTypeName) {
        this.eventTypeName = eventTypeName;
    }

    public String getOverridePriceAmount() {
        return overridePriceAmount;
    }

    public void setOverridePriceAmount(String overridePriceAmount) {
        this.overridePriceAmount = overridePriceAmount;
    }

    public String getEventCategorySlug() {
        return eventCategorySlug;
    }

    public void setEventCategorySlug(String eventCategorySlug) {
        this.eventCategorySlug = eventCategorySlug;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiscountPriceAmount() {
        return discountPriceAmount;
    }

    public void setDiscountPriceAmount(String discountPriceAmount) {
        this.discountPriceAmount = discountPriceAmount;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
