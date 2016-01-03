package com.renyu.carclient.model;

/**
 * Created by renyu on 15/10/19.
 */
public class ProductModel {

    boolean isChecked=false;
    boolean isEdit=false;

    /**
     * sku_id : 1101000003
     * quantity : 1
     * image_default_id : http://www.kzmall.cn/images/74/50/0b/e6e3b9fa7db8f234f93316c03259df0b30624f6e.jpg
     * title : Mobil/美孚机油黑霸王15W-40矿物质油CH-4级柴机油4L（4升）
     * price : 120.000
     * cart_id : 94
     * item_id : 49
     */

    private int sku_id;
    private int quantity;
    private String image_default_id;
    private String title;
    private String price;
    private int cart_id;
    private int item_id;

    public void setSku_id(int sku_id) {
        this.sku_id = sku_id;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setImage_default_id(String image_default_id) {
        this.image_default_id = image_default_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setCart_id(int cart_id) {
        this.cart_id = cart_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public int getSku_id() {
        return sku_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getImage_default_id() {
        return image_default_id;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public int getCart_id() {
        return cart_id;
    }

    public int getItem_id() {
        return item_id;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }
}
