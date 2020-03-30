package com.starbucks.id.model.menu;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Angga N P on 4/30/2018.
 */
public class MenuSubModel {

    //FOR SEARCHING PURPOSE
    public String Content;
    @SerializedName("menus_sub_id")
    @Expose
    private String menusSubId;
    @SerializedName("menus_hdr_id")
    @Expose
    private String menusHdrId;
    @SerializedName("name_id")
    @Expose
    private String nameId;
    @SerializedName("name_en")
    @Expose
    private String nameEn;
    @SerializedName("image_sub")
    @Expose
    private String imageSub;
    @SerializedName("is_delete")
    @Expose
    private String isDelete;
    @SerializedName("dtlmenu")
    @Expose
    private List<MenuDetailModel> dtlmenu = null;

    public String getMenusSubId() {
        return menusSubId;
    }

    public void setMenusSubId(String menusSubId) {
        this.menusSubId = menusSubId;
    }

    public String getMenusHdrId() {
        return menusHdrId;
    }

    public void setMenusHdrId(String menusHdrId) {
        this.menusHdrId = menusHdrId;
    }

    public String getNameId() {
        return nameId;
    }

    public void setNameId(String nameId) {
        this.nameId = nameId;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getImageSub() {
        return imageSub;
    }

    public void setImageSub(String imageSub) {
        this.imageSub = imageSub;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public List<MenuDetailModel> getDtlmenu() {
        return dtlmenu;
    }

    public void setDtlmenu(List<MenuDetailModel> dtlmenu) {
        this.dtlmenu = dtlmenu;
    }
}
