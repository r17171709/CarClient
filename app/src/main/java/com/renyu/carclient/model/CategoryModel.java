package com.renyu.carclient.model;

import java.util.ArrayList;

/**
 * Created by renyu on 15/12/25.
 */
public class CategoryModel {

    /**
     * parent_id : 1802
     * cat_name : 刹车片
     * cat_logo : null
     * cat_id : 180200
     */

    private int parent_id;
    private String cat_name;
    private String cat_logo;
    private int cat_id;
    private boolean isSelect=false;
    private boolean isOpen=true;
    ArrayList<CategoryModel> lists=null;

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public void setCat_logo(String cat_logo) {
        this.cat_logo = cat_logo;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }

    public int getParent_id() {
        return parent_id;
    }

    public String getCat_name() {
        return cat_name;
    }

    public String getCat_logo() {
        return cat_logo;
    }

    public int getCat_id() {
        return cat_id;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public ArrayList<CategoryModel> getLists() {
        return lists;
    }

    public void setLists(ArrayList<CategoryModel> lists) {
        this.lists = lists;
    }
}
