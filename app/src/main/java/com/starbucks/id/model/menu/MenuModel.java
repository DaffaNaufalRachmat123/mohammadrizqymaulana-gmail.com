package com.starbucks.id.model.menu;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Angga N P on 4/30/2018.
 */
public class MenuModel {

    @SerializedName("menus_hdr_id")
    @Expose
    private String menusHdrId;
    @SerializedName("menus_name_id")
    @Expose
    private String menusNameId;
    @SerializedName("menus_name_en")
    @Expose
    private String menusNameEn;
    @SerializedName("image_hdr")
    @Expose
    private String imageHdr;
    @SerializedName("submenu")
    @Expose
    private List<MenuSubModel> submenu = null;

    public String getMenusHdrId() {
        return menusHdrId;
    }

    public void setMenusHdrId(String menusHdrId) {
        this.menusHdrId = menusHdrId;
    }

    public String getMenusNameId() {
        return menusNameId;
    }

    public void setMenusNameId(String menusNameId) {
        this.menusNameId = menusNameId;
    }

    public String getMenusNameEn() {
        return menusNameEn;
    }

    public void setMenusNameEn(String menusNameEn) {
        this.menusNameEn = menusNameEn;
    }

    public String getImageHdr() {
        return imageHdr;
    }

    public void setImageHdr(String imageHdr) {
        this.imageHdr = imageHdr;
    }

    public List<MenuSubModel> getSubmenu() {
        return submenu;
    }

    public void setSubmenu(List<MenuSubModel> submenu) {
        this.submenu = submenu;
    }
}
