package com.renyu.carclient.model;

/**
 * Created by renyu on 16/1/13.
 */
public class HotBrandModel {

    /**
     * brand_name : 美孚
     * brand_logo : http://120.26.128.24/b2b2c/public/images/f4/d7/c8/1e2ad8040a4670aeb014c383bab6ab1bb28f5486.jpg
     * brand_id : 3
     */

    private String brand_name;
    private String brand_logo;
    private String brand_id;

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public void setBrand_logo(String brand_logo) {
        this.brand_logo = brand_logo;
    }

    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public String getBrand_logo() {
        return brand_logo;
    }

    public String getBrand_id() {
        return brand_id;
    }
}
