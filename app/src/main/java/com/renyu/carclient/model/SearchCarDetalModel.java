package com.renyu.carclient.model;

/**
 * Created by renyu on 16/1/13.
 */
public class SearchCarDetalModel {
    /**
     * model_year : 2006
     * sales_name : 1.8T 无级 基本型
     * nlevelid : CFA0418C0019
     */

    private String model_year;
    private String sales_name;
    private String nlevelid;

    public void setModel_year(String model_year) {
        this.model_year = model_year;
    }

    public void setSales_name(String sales_name) {
        this.sales_name = sales_name;
    }

    public void setNlevelid(String nlevelid) {
        this.nlevelid = nlevelid;
    }

    public String getModel_year() {
        return model_year;
    }

    public String getSales_name() {
        return sales_name;
    }

    public String getNlevelid() {
        return nlevelid;
    }
}
