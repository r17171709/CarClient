package com.renyu.carclient.model;

/**
 * Created by renyu on 15/12/7.
 */
public class SearchCarTypeChildModel {
    boolean isOpen=false;

    /**
     * brand : ASDF
     * models : 广汽本田 奥德赛
     */

    private String brand;
    private String models;
    private String flag;

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModels(String models) {
        this.models = models;
    }

    public String getBrand() {
        return brand;
    }

    public String getModels() {
        return models;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
