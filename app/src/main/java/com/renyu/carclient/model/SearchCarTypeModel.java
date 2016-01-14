package com.renyu.carclient.model;

/**
 * Created by renyu on 15/12/7.
 */
public class SearchCarTypeModel {
    /**
     * brand : 1234
     * brand_img : null
     */

    private String brand;
    private String brand_img;

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setBrand_img(String brand_img) {
        this.brand_img = brand_img;
    }

    public String getBrand() {
        return brand;
    }

    public String getBrand_img() {
        return brand_img;
    }
}
