package com.starbucks.id.model.store;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Novian on 1/11/2016.
 */
public class StoresModel {
    @SerializedName("store_id")
    @Expose
    private String storeId;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lon")
    @Expose
    private String lon;
    @SerializedName("addr")
    @Expose
    private String addr;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("is_24hours")
    @Expose
    private String is24hours;
    @SerializedName("is_oven_warmed_food")
    @Expose
    private String isOvenWarmedFood;
    @SerializedName("is_drive_thru")
    @Expose
    private String isDriveThru;
    @SerializedName("is_wifi_ready")
    @Expose
    private String isWifiReady;
    @SerializedName("is_sparkling")
    @Expose
    private String isSparkling;
    @SerializedName("is_mini_frap")
    @Expose
    private String isMiniFrap;
    @SerializedName("is_esspreso_choice")
    @Expose
    private String isEsspresoChoice;
    @SerializedName("is_reverse_able")
    @Expose
    private String isReverseAble;
    @SerializedName("operation_hour")
    @Expose
    private String operationHour;
    @SerializedName("is_sbux_card")
    @Expose
    private String isSbuxCard;

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIs24hours() {
        return is24hours;
    }

    public void setIs24hours(String is24hours) {
        this.is24hours = is24hours;
    }

    public String getIsOvenWarmedFood() {
        return isOvenWarmedFood;
    }

    public void setIsOvenWarmedFood(String isOvenWarmedFood) {
        this.isOvenWarmedFood = isOvenWarmedFood;
    }

    public String getIsDriveThru() {
        return isDriveThru;
    }

    public void setIsDriveThru(String isDriveThru) {
        this.isDriveThru = isDriveThru;
    }

    public String getIsWifiReady() {
        return isWifiReady;
    }

    public void setIsWifiReady(String isWifiReady) {
        this.isWifiReady = isWifiReady;
    }

    public String getIsSparkling() {
        return isSparkling;
    }

    public void setIsSparkling(String isSparkling) {
        this.isSparkling = isSparkling;
    }

    public String getIsMiniFrap() {
        return isMiniFrap;
    }

    public void setIsMiniFrap(String isMiniFrap) {
        this.isMiniFrap = isMiniFrap;
    }

    public String getIsEsspresoChoice() {
        return isEsspresoChoice;
    }

    public void setIsEsspresoChoice(String isEsspresoChoice) {
        this.isEsspresoChoice = isEsspresoChoice;
    }

    public String getIsReverseAble() {
        return isReverseAble;
    }

    public void setIsReverseAble(String isReverseAble) {
        this.isReverseAble = isReverseAble;
    }

    public String getOperationHour() {
        return operationHour;
    }

    public void setOperationHour(String operationHour) {
        this.operationHour = operationHour;
    }

    public String getIsSbuxCard() {
        return isSbuxCard;
    }

    public void setIsSbuxCard(String isSbuxCard) {
        this.isSbuxCard = isSbuxCard;
    }

}
