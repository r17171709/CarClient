package com.renyu.carclient.model;

/**
 * Created by renyu on 16/1/13.
 */
public class HotCatModel {

    /**
     * cat_logo :
     * cat_id : 2101
     * cat_name : 雨刮片
     */

    private String cat_logo;
    private String cat_id;
    private String cat_name;

    public void setCat_logo(String cat_logo) {
        this.cat_logo = cat_logo;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getCat_logo() {
        return cat_logo;
    }

    public String getCat_id() {
        return cat_id;
    }

    public String getCat_name() {
        return cat_name;
    }
}
