package com.renyu.carclient.model;

/**
 * Created by renyu on 15/10/18.
 */
public class SearchBrandModel {

    private String brand_logo;
    private int brand_id;
    private String brand_name;

    private String cat_id;
    private String cat_name;

    private boolean isSelect=false;

    public void setBrand_logo(String brand_logo) {
        this.brand_logo = brand_logo;
    }

    public void setBrand_id(int brand_id) {
        this.brand_id = brand_id;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getBrand_logo() {
        return brand_logo;
    }

    public int getBrand_id() {
        return brand_id;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
