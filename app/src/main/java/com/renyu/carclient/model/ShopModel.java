package com.renyu.carclient.model;

import java.util.ArrayList;

/**
 * Created by renyu on 15/10/19.
 */
public class ShopModel {

    int id=-1;
    String shopname="";
    boolean isEdit=false;
    ArrayList<ProductModel> models=null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public boolean isEdit() {
        return isEdit;
    }

    public void setIsEdit(boolean isEdit) {
        this.isEdit = isEdit;
    }

    public ArrayList<ProductModel> getModels() {
        return models;
    }

    public void setModels(ArrayList<ProductModel> models) {
        this.models = models;
    }
}
