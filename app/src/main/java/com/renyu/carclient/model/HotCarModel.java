package com.renyu.carclient.model;

/**
 * Created by renyu on 16/1/13.
 */
public class HotCarModel {

    /**
     * car_logo :
     * car_name : 宝马
     * car_id : 1
     */

    private String car_logo;
    private String car_name;
    private String car_id;

    public void setCar_logo(String car_logo) {
        this.car_logo = car_logo;
    }

    public void setCar_name(String car_name) {
        this.car_name = car_name;
    }

    public void setCar_id(String car_id) {
        this.car_id = car_id;
    }

    public String getCar_logo() {
        return car_logo;
    }

    public String getCar_name() {
        return car_name;
    }

    public String getCar_id() {
        return car_id;
    }
}
