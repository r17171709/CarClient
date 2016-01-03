package com.renyu.carclient.model;

import java.io.Serializable;

/**
 * Created by renyu on 15/12/26.
 */
public class GoodsListModel implements Serializable {

    /**
     * violation : 0
     * delist_time : null
     * freez : null
     * bn : G5673F538423F1
     * is_selfshop : 1
     * image_default_id : http://www.kzmall.cn/images/99/d0/37/107df3c508a2b0fbf15a38035d3e50ddf5432e75.jpg
     * cost_price : 0.000
     * list_time : 1450693961
     * item_id : 2093
     * disabled : 0
     * nospec : 1
     * brand_id : 5
     * is_virtual : 0
     * is_offline : 0
     * shop_id : 1
     * mkt_price : 0.000
     * params : {}
     * list_image : http://www.kzmall.cn/images/99/d0/37/107df3c508a2b0fbf15a38035d3e50ddf5432e75.jpg
     * sub_stock : 0
     * cat_id : 110400
     * spec_desc : null
     * match_id : Dot-3
     * shop_cat_id : 1
     * price : 20.000
     * barcode : null
     * approve_status : onsale
     * rate_count : 0
     * rate_good_count : 0
     * modified_time : 1450693958
     * outer_id : null
     * sub_title :
     * order_sort : 1
     * use_platform : 0
     * props_name : null
     * sold_quantity : 0
     * view_count : 0
     * rate_neutral_count : 0
     * buy_count : 0
     * store : 999999
     * settle_price : 0.000
     * rate_bad_count : 0
     * title : 求是刹车油Dot-3/450g制动液
     * weight : 0.000
     * is_timing : 0
     * has_discount : 0
     */

    private int violation;
    private String delist_time;
    private String freez;
    private String bn;
    private int is_selfshop;
    private String image_default_id;
    private String cost_price;
    private int list_time;
    private int item_id;
    private int disabled;
    private int nospec;
    private int brand_id;
    private int is_virtual;
    private int is_offline;
    private int shop_id;
    private String mkt_price;
    private String list_image;
    private int sub_stock;
    private int cat_id;
    private String spec_desc;
    private String match_id;
    private String shop_cat_id;
    private String price;
    private String barcode;
    private String approve_status;
    private int rate_count;
    private int rate_good_count;
    private int modified_time;
    private String outer_id;
    private String sub_title;
    private int order_sort;
    private String use_platform;
    private String props_name;
    private int sold_quantity;
    private int view_count;
    private int rate_neutral_count;
    private int buy_count;
    private int store;
    private String settle_price;
    private int rate_bad_count;
    private String title;
    private String weight;
    private int is_timing;
    private int has_discount;
    private String params;
    private int quantity;
    private boolean isChecked;

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public void setViolation(int violation) {
        this.violation = violation;
    }

    public void setDelist_time(String delist_time) {
        this.delist_time = delist_time;
    }

    public void setFreez(String freez) {
        this.freez = freez;
    }

    public void setBn(String bn) {
        this.bn = bn;
    }

    public void setIs_selfshop(int is_selfshop) {
        this.is_selfshop = is_selfshop;
    }

    public void setImage_default_id(String image_default_id) {
        this.image_default_id = image_default_id;
    }

    public void setCost_price(String cost_price) {
        this.cost_price = cost_price;
    }

    public void setList_time(int list_time) {
        this.list_time = list_time;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public void setDisabled(int disabled) {
        this.disabled = disabled;
    }

    public void setNospec(int nospec) {
        this.nospec = nospec;
    }

    public void setBrand_id(int brand_id) {
        this.brand_id = brand_id;
    }

    public void setIs_virtual(int is_virtual) {
        this.is_virtual = is_virtual;
    }

    public void setIs_offline(int is_offline) {
        this.is_offline = is_offline;
    }

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
    }

    public void setMkt_price(String mkt_price) {
        this.mkt_price = mkt_price;
    }

    public void setList_image(String list_image) {
        this.list_image = list_image;
    }

    public void setSub_stock(int sub_stock) {
        this.sub_stock = sub_stock;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }

    public void setSpec_desc(String spec_desc) {
        this.spec_desc = spec_desc;
    }

    public void setMatch_id(String match_id) {
        this.match_id = match_id;
    }

    public void setShop_cat_id(String shop_cat_id) {
        this.shop_cat_id = shop_cat_id;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public void setApprove_status(String approve_status) {
        this.approve_status = approve_status;
    }

    public void setRate_count(int rate_count) {
        this.rate_count = rate_count;
    }

    public void setRate_good_count(int rate_good_count) {
        this.rate_good_count = rate_good_count;
    }

    public void setModified_time(int modified_time) {
        this.modified_time = modified_time;
    }

    public void setOuter_id(String outer_id) {
        this.outer_id = outer_id;
    }

    public void setSub_title(String sub_title) {
        this.sub_title = sub_title;
    }

    public void setOrder_sort(int order_sort) {
        this.order_sort = order_sort;
    }

    public void setUse_platform(String use_platform) {
        this.use_platform = use_platform;
    }

    public void setProps_name(String props_name) {
        this.props_name = props_name;
    }

    public void setSold_quantity(int sold_quantity) {
        this.sold_quantity = sold_quantity;
    }

    public void setView_count(int view_count) {
        this.view_count = view_count;
    }

    public void setRate_neutral_count(int rate_neutral_count) {
        this.rate_neutral_count = rate_neutral_count;
    }

    public void setBuy_count(int buy_count) {
        this.buy_count = buy_count;
    }

    public void setStore(int store) {
        this.store = store;
    }

    public void setSettle_price(String settle_price) {
        this.settle_price = settle_price;
    }

    public void setRate_bad_count(int rate_bad_count) {
        this.rate_bad_count = rate_bad_count;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setIs_timing(int is_timing) {
        this.is_timing = is_timing;
    }

    public void setHas_discount(int has_discount) {
        this.has_discount = has_discount;
    }

    public int getViolation() {
        return violation;
    }

    public String getDelist_time() {
        return delist_time;
    }

    public String getFreez() {
        return freez;
    }

    public String getBn() {
        return bn;
    }

    public int getIs_selfshop() {
        return is_selfshop;
    }

    public String getImage_default_id() {
        return image_default_id;
    }

    public String getCost_price() {
        return cost_price;
    }

    public int getList_time() {
        return list_time;
    }

    public int getItem_id() {
        return item_id;
    }

    public int getDisabled() {
        return disabled;
    }

    public int getNospec() {
        return nospec;
    }

    public int getBrand_id() {
        return brand_id;
    }

    public int getIs_virtual() {
        return is_virtual;
    }

    public int getIs_offline() {
        return is_offline;
    }

    public int getShop_id() {
        return shop_id;
    }

    public String getMkt_price() {
        return mkt_price;
    }

    public String getList_image() {
        return list_image;
    }

    public int getSub_stock() {
        return sub_stock;
    }

    public int getCat_id() {
        return cat_id;
    }

    public String getSpec_desc() {
        return spec_desc;
    }

    public String getMatch_id() {
        return match_id;
    }

    public String getShop_cat_id() {
        return shop_cat_id;
    }

    public String getPrice() {
        return price;
    }

    public String getBarcode() {
        return barcode;
    }

    public String getApprove_status() {
        return approve_status;
    }

    public int getRate_count() {
        return rate_count;
    }

    public int getRate_good_count() {
        return rate_good_count;
    }

    public int getModified_time() {
        return modified_time;
    }

    public String getOuter_id() {
        return outer_id;
    }

    public String getSub_title() {
        return sub_title;
    }

    public int getOrder_sort() {
        return order_sort;
    }

    public String getUse_platform() {
        return use_platform;
    }

    public String getProps_name() {
        return props_name;
    }

    public int getSold_quantity() {
        return sold_quantity;
    }

    public int getView_count() {
        return view_count;
    }

    public int getRate_neutral_count() {
        return rate_neutral_count;
    }

    public int getBuy_count() {
        return buy_count;
    }

    public int getStore() {
        return store;
    }

    public String getSettle_price() {
        return settle_price;
    }

    public int getRate_bad_count() {
        return rate_bad_count;
    }

    public String getTitle() {
        return title;
    }

    public String getWeight() {
        return weight;
    }

    public int getIs_timing() {
        return is_timing;
    }

    public int getHas_discount() {
        return has_discount;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
