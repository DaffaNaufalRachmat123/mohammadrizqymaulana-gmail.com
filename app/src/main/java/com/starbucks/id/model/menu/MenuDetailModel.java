package com.starbucks.id.model.menu;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Angga N P on 4/30/2018.
 */
public class MenuDetailModel {

    @SerializedName("menus_dtl_id")
    @Expose
    private String menusDtlId;
    @SerializedName("menus_sub_id")
    @Expose
    private String menusSubId;
    @SerializedName("dtl_name_en")
    @Expose
    private String dtlNameEn;
    @SerializedName("menu_content_en")
    @Expose
    private String menuContentEn;
    @SerializedName("menu_content_id")
    @Expose
    private String menuContentId;
    @SerializedName("image_dtl")
    @Expose
    private String imageDtl;
    @SerializedName("image_dtl_content")
    @Expose
    private String imageDtlContent;
    @SerializedName("is_delete")
    @Expose
    private String isDelete;
    @SerializedName("dtl_name_id")
    @Expose
    private String dtlNameId;

    public String getMenusDtlId() {
        return menusDtlId;
    }

    public void setMenusDtlId(String menusDtlId) {
        this.menusDtlId = menusDtlId;
    }

    public String getMenusSubId() {
        return menusSubId;
    }

    public void setMenusSubId(String menusSubId) {
        this.menusSubId = menusSubId;
    }

    public String getDtlNameEn() {
        return dtlNameEn;
    }

    public void setDtlNameEn(String dtlNameEn) {
        this.dtlNameEn = dtlNameEn;
    }

    public String getMenuContentEn() {
        return menuContentEn;
    }

    public void setMenuContentEn(String menuContentEn) {
        this.menuContentEn = menuContentEn;
    }

    public String getImageDtl() {
        return imageDtl;
    }

    public void setImageDtl(String imageDtl) {
        this.imageDtl = imageDtl;
    }

    public String getImageDtlContent() {
        return imageDtlContent;
    }

    public void setImageDtlContent(String imageDtlContent) {
        this.imageDtlContent = imageDtlContent;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public String getDtlNameId() {
        return dtlNameId;
    }

    public void setDtlNameId(String dtlNameId) {
        this.dtlNameId = dtlNameId;
    }

    public String getMenuContentId() {
        return menuContentId;
    }

    public void setMenuContentId(String menuContentId) {
        this.menuContentId = menuContentId;
    }
}
