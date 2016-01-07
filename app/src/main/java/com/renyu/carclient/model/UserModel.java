package com.renyu.carclient.model;

import java.io.Serializable;

/**
 * Created by renyu on 16/1/4.
 */
public class UserModel implements Serializable {

    /**
     * init_amount : 0
     * amount : 0
     * user_id : 57
     * repairdepot_name : qiyemin
     */

    private int init_amount;
    private int amount;
    private int user_id;
    private String repairdepot_name;
    private String head_photo;

    public void setInit_amount(int init_amount) {
        this.init_amount = init_amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setRepairdepot_name(String repairdepot_name) {
        this.repairdepot_name = repairdepot_name;
    }

    public int getInit_amount() {
        return init_amount;
    }

    public int getAmount() {
        return amount;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getRepairdepot_name() {
        return repairdepot_name;
    }

    public String getHead_photo() {
        return head_photo;
    }

    public void setHead_photo(String head_photo) {
        this.head_photo = head_photo;
    }
}
