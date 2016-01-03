package com.renyu.carclient.model;

/**
 * Created by renyu on 15/12/12.
 */
public class CollectionModel {
    /**
     * goods_name : Shell/壳牌机油劲霸R2 20W-50矿物质油CF-4级柴机油4L（4升）
     * cellphone : null
     * cat_id : 110100
     * send_time : null
     * item_id : 47
     * disabled : 0
     * user_id : 57
     * sku_id : null
     * image_default_id : http://www.kzmall.cn/images/89/1c/63/a53ff4c121bee87c880eacbcfeb15aeeeb6fe83d.jpg
     * String_type : goods
     * remark : null
     * gnotify_id : 4
     * goods_price : 90.000
     * create_time : 1451270210
     * email : null
     */

    private String goods_name;
    private String cellphone;
    private int cat_id;
    private String send_time;
    private int item_id;
    private int disabled;
    private int user_id;
    private String sku_id;
    private String image_default_id;
    private String String_type;
    private String remark;
    private int gnotify_id;
    private String goods_price;
    private int create_time;
    private String email;

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }

    public void setSend_time(String send_time) {
        this.send_time = send_time;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public void setDisabled(int disabled) {
        this.disabled = disabled;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setSku_id(String sku_id) {
        this.sku_id = sku_id;
    }

    public void setImage_default_id(String image_default_id) {
        this.image_default_id = image_default_id;
    }

    public void setString_type(String String_type) {
        this.String_type = String_type;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setGnotify_id(int gnotify_id) {
        this.gnotify_id = gnotify_id;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public String getCellphone() {
        return cellphone;
    }

    public int getCat_id() {
        return cat_id;
    }

    public String getSend_time() {
        return send_time;
    }

    public int getItem_id() {
        return item_id;
    }

    public int getDisabled() {
        return disabled;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getSku_id() {
        return sku_id;
    }

    public String getImage_default_id() {
        return image_default_id;
    }

    public String getString_type() {
        return String_type;
    }

    public String getRemark() {
        return remark;
    }

    public int getGnotify_id() {
        return gnotify_id;
    }

    public String getGoods_price() {
        return goods_price;
    }

    public int getCreate_time() {
        return create_time;
    }

    public String getEmail() {
        return email;
    }

    boolean flag;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
