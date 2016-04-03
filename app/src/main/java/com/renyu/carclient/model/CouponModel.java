package com.renyu.carclient.model;

/**
 * Created by renyu on 16/3/28.
 */
public class CouponModel {
    /**
     * coupon_code : B29Q75930600001
     * user_id : 24
     * shop_id : 1
     * coupon_id : 3
     * obtain_desc : 免费领取
     * obtain_time : 1458624433
     * tid : null
     * is_valid : 1
     * used_platform : 0
     * canuse_start_time : 1458662400
     * canuse_end_time : 1459439999
     * limit_money : 100.000
     * deduct_money : 10.000
     * coupon_name : 123456
     * coupon_desc : 312312
     */

    private String coupon_code;
    private int user_id;
    private int shop_id;
    private int coupon_id;
    private String obtain_desc;
    private int obtain_time;
    private Object tid;
    private String is_valid;
    private String used_platform;
    private int canuse_start_time;
    private int canuse_end_time;
    private String limit_money;
    private String deduct_money;
    private String coupon_name;
    private String coupon_desc;

    public String getCoupon_code() {
        return coupon_code;
    }

    public void setCoupon_code(String coupon_code) {
        this.coupon_code = coupon_code;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getShop_id() {
        return shop_id;
    }

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
    }

    public int getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(int coupon_id) {
        this.coupon_id = coupon_id;
    }

    public String getObtain_desc() {
        return obtain_desc;
    }

    public void setObtain_desc(String obtain_desc) {
        this.obtain_desc = obtain_desc;
    }

    public int getObtain_time() {
        return obtain_time;
    }

    public void setObtain_time(int obtain_time) {
        this.obtain_time = obtain_time;
    }

    public Object getTid() {
        return tid;
    }

    public void setTid(Object tid) {
        this.tid = tid;
    }

    public String getIs_valid() {
        return is_valid;
    }

    public void setIs_valid(String is_valid) {
        this.is_valid = is_valid;
    }

    public String getUsed_platform() {
        return used_platform;
    }

    public void setUsed_platform(String used_platform) {
        this.used_platform = used_platform;
    }

    public int getCanuse_start_time() {
        return canuse_start_time;
    }

    public void setCanuse_start_time(int canuse_start_time) {
        this.canuse_start_time = canuse_start_time;
    }

    public int getCanuse_end_time() {
        return canuse_end_time;
    }

    public void setCanuse_end_time(int canuse_end_time) {
        this.canuse_end_time = canuse_end_time;
    }

    public String getLimit_money() {
        return limit_money;
    }

    public void setLimit_money(String limit_money) {
        this.limit_money = limit_money;
    }

    public String getDeduct_money() {
        return deduct_money;
    }

    public void setDeduct_money(String deduct_money) {
        this.deduct_money = deduct_money;
    }

    public String getCoupon_name() {
        return coupon_name;
    }

    public void setCoupon_name(String coupon_name) {
        this.coupon_name = coupon_name;
    }

    public String getCoupon_desc() {
        return coupon_desc;
    }

    public void setCoupon_desc(String coupon_desc) {
        this.coupon_desc = coupon_desc;
    }
}
