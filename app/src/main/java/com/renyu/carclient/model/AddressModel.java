package com.renyu.carclient.model;

/**
 * Created by renyu on 16/1/4.
 */
public class AddressModel {

    /**
     * addr_id : 8
     * area : 北京市/昌平区:110100/110114
     * mobile : 18023212341
     * addr : yuxin
     * zip : 123456
     * user_id : 57
     * tel : null
     * def_addr : 0
     * name : LJ
     */

    private int addr_id;
    private String area;
    private String mobile;
    private String addr;
    private String zip;
    private int user_id;
    private Object tel;
    private int def_addr;
    private String name;

    public void setAddr_id(int addr_id) {
        this.addr_id = addr_id;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setTel(Object tel) {
        this.tel = tel;
    }

    public void setDef_addr(int def_addr) {
        this.def_addr = def_addr;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAddr_id() {
        return addr_id;
    }

    public String getArea() {
        return area;
    }

    public String getMobile() {
        return mobile;
    }

    public String getAddr() {
        return addr;
    }

    public String getZip() {
        return zip;
    }

    public int getUser_id() {
        return user_id;
    }

    public Object getTel() {
        return tel;
    }

    public int getDef_addr() {
        return def_addr;
    }

    public String getName() {
        return name;
    }
}
