package com.renyu.carclient.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by renyu on 15/12/28.
 */
public class OrderModel implements Serializable {

    /**
     * examine_reason : null
     * is_price_approve : 0
     * trade_from : pc
     * user_id : 57
     * adjust_fee : 0.000
     * payed_fee : 0.000
     * post_fee : null
     * message : 不开票
     * tid : 1512251009420057
     * created_time : 1451009364
     * dlytmpl_id : null
     * title : 订单明细介绍
     * need_invoice : 0
     * invoice_name : unit
     * is_clearing : 0
     * real_point_fee : null
     * pay_type : online
     * receiver_zip : 123456
     * service_id : 16
     * status : WAIT_GOODS
     * modified_time : 1451009545
     * shop_memo : null
     * send_time : null
     * receiver_mobile : 18023212341
     * buyer_area : 110100/110114
     * area_id : null
     * invoice_main : 
     * approve_time : null
     * anony : 0
     * cancel_reason : null
     * shipping_type : null
     * buyer_rate : 0
     * payment : 1.000
     * consign_time : 1451268997
     * receiver_phone : null
     * end_time : null
     * receiver_address : yuxin
     * step_paid_fee : null
     * ip : 49.77.178.69
     * obtain_point_fee : 100
     * receiver_time : null
     * shop_flag : null
     * is_part_consign : 0
     * order : [{"spec_nature_info":null,"sku_properties_name":null,"bn":"1701010001","end_time":null,"refund_fee":"0.000","buyer_rate":0,"outer_iid":null,"refund_id":null,"item_id":7,"num":1,"invoice_no":null,"divide_order_fee":"0.000","bind_oid":null,"shop_id":1,"old_price":"100.000","discount_fee":"0.000","sub_stock":0,"payment":"1.000","timeout_action_time":null,"oid":1512251009430057,"shipping_type":null,"status":"WAIT_GOODS","tid":1512251009420057,"price":"1.000","approve_time":null,"part_mjz_discount":"0.000","pay_time":null,"logistics_company":null,"consign_time":null,"modified_time":1451009545,"cat_service_rate":"0.000","user_id":57,"seller_rate":0,"aftersales_status":null,"anony":0,"order_from":"pc","sku_id":"1701010001","sendnum":0,"total_fee":"1.000","adjust_fee":"0.000","complaints_status":"NOT_COMPLAINTS","is_oversold":0,"outer_sku_id":null,"title":"测试皮带","service_id":16,"pic_path":"http://120.26.139.82/b2b2c/public/images/d8/d7/7d/c1b95ca6b032e76eec48226c42142989d11f0fe3.png","settle_price":"0.000"}]
     * buyer_message : null
     * receiver_city : 昌平区
     * seller_rate : 0
     * pay_time : null
     * consume_point_fee : 0
     * user_memo : null
     * total_fee : 1.000
     * disabled : 0
     * discount_fee : 0.000
     * step_trade_status : null
     * receiver_state : 北京市
     * invoice_type : dedicated
     * receiver_name : LJ
     * receiver_district : null
     * ziti_addr : null
     * itemnum : 1
     * invoice_fee : 0.000
     * trade_memo : 
     * shop_id : 1
     * timeout_action_time : null
     * user_name : 10120001
     * needpaytime : null
     */

    private String examine_reason;
    private int is_price_approve;
    private String trade_from;
    private int user_id;
    private String adjust_fee;
    private String payed_fee;
    private String post_fee;
    private String message;
    private long tid;
    private int created_time;
    private String dlytmpl_id;
    private String title;
    private int need_invoice;
    private String invoice_name;
    private int is_clearing;
    private String real_point_fee;
    private String pay_type;
    private String receiver_zip;
    private int service_id;
    private String status;
    private int modified_time;
    private String shop_memo;
    private String send_time;
    private String receiver_mobile;
    private String buyer_area;
    private String area_id;
    private String invoice_main;
    private String approve_time;
    private int anony;
    private String cancel_reason;
    private String shipping_type;
    private int buyer_rate;
    private String payment;
    private int consign_time;
    private String receiver_phone;
    private String end_time;
    private String receiver_address;
    private String step_paid_fee;
    private String ip;
    private int obtain_point_fee;
    private int receiver_time;
    private String shop_flag;
    private int is_part_consign;
    private String buyer_message;
    private String receiver_city;
    private int seller_rate;
    private int pay_time;
    private int consume_point_fee;
    private String user_memo;
    private String total_fee;
    private int disabled;
    private String discount_fee;
    private String step_trade_status;
    private String receiver_state;
    private String invoice_type;
    private String receiver_name;
    private String receiver_district;
    private String ziti_addr;
    private int itemnum;
    private String invoice_fee;
    private String trade_memo;
    private int shop_id;
    private String timeout_action_time;
    private String user_name;
    private String needpaytime;
    private String autoreceive_parameter;
    private String needpaytime_parameter;

    /**
     * spec_nature_info : null
     * sku_properties_name : null
     * bn : 1701010001
     * end_time : null
     * refund_fee : 0.000
     * buyer_rate : 0
     * outer_iid : null
     * refund_id : null
     * item_id : 7
     * num : 1
     * invoice_no : null
     * divide_order_fee : 0.000
     * bind_oid : null
     * shop_id : 1
     * old_price : 100.000
     * discount_fee : 0.000
     * sub_stock : 0
     * payment : 1.000
     * timeout_action_time : null
     * oid : 1512251009430057
     * shipping_type : null
     * status : WAIT_GOODS
     * tid : 1512251009420057
     * price : 1.000
     * approve_time : null
     * part_mjz_discount : 0.000
     * pay_time : null
     * logistics_company : null
     * consign_time : null
     * modified_time : 1451009545
     * cat_service_rate : 0.000
     * user_id : 57
     * seller_rate : 0
     * aftersales_status : null
     * anony : 0
     * order_from : pc
     * sku_id : 1701010001
     * sendnum : 0
     * total_fee : 1.000
     * adjust_fee : 0.000
     * complaints_status : NOT_COMPLAINTS
     * is_oversold : 0
     * outer_sku_id : null
     * title : 测试皮带
     * service_id : 16
     * pic_path : http://120.26.139.82/b2b2c/public/images/d8/d7/7d/c1b95ca6b032e76eec48226c42142989d11f0fe3.png
     * settle_price : 0.000
     */

    private List<OrderEntity> order;

    public void setExamine_reason(String examine_reason) {
        this.examine_reason = examine_reason;
    }

    public void setIs_price_approve(int is_price_approve) {
        this.is_price_approve = is_price_approve;
    }

    public void setTrade_from(String trade_from) {
        this.trade_from = trade_from;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setAdjust_fee(String adjust_fee) {
        this.adjust_fee = adjust_fee;
    }

    public void setPayed_fee(String payed_fee) {
        this.payed_fee = payed_fee;
    }

    public void setPost_fee(String post_fee) {
        this.post_fee = post_fee;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTid(long tid) {
        this.tid = tid;
    }

    public void setCreated_time(int created_time) {
        this.created_time = created_time;
    }

    public void setDlytmpl_id(String dlytmpl_id) {
        this.dlytmpl_id = dlytmpl_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setNeed_invoice(int need_invoice) {
        this.need_invoice = need_invoice;
    }

    public void setInvoice_name(String invoice_name) {
        this.invoice_name = invoice_name;
    }

    public void setIs_clearing(int is_clearing) {
        this.is_clearing = is_clearing;
    }

    public void setReal_point_fee(String real_point_fee) {
        this.real_point_fee = real_point_fee;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public void setReceiver_zip(String receiver_zip) {
        this.receiver_zip = receiver_zip;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setModified_time(int modified_time) {
        this.modified_time = modified_time;
    }

    public void setShop_memo(String shop_memo) {
        this.shop_memo = shop_memo;
    }

    public void setSend_time(String send_time) {
        this.send_time = send_time;
    }

    public void setReceiver_mobile(String receiver_mobile) {
        this.receiver_mobile = receiver_mobile;
    }

    public void setBuyer_area(String buyer_area) {
        this.buyer_area = buyer_area;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public void setInvoice_main(String invoice_main) {
        this.invoice_main = invoice_main;
    }

    public void setApprove_time(String approve_time) {
        this.approve_time = approve_time;
    }

    public void setAnony(int anony) {
        this.anony = anony;
    }

    public void setCancel_reason(String cancel_reason) {
        this.cancel_reason = cancel_reason;
    }

    public void setShipping_type(String shipping_type) {
        this.shipping_type = shipping_type;
    }

    public void setBuyer_rate(int buyer_rate) {
        this.buyer_rate = buyer_rate;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public void setConsign_time(int consign_time) {
        this.consign_time = consign_time;
    }

    public void setReceiver_phone(String receiver_phone) {
        this.receiver_phone = receiver_phone;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public void setReceiver_address(String receiver_address) {
        this.receiver_address = receiver_address;
    }

    public void setStep_paid_fee(String step_paid_fee) {
        this.step_paid_fee = step_paid_fee;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setObtain_point_fee(int obtain_point_fee) {
        this.obtain_point_fee = obtain_point_fee;
    }

    public void setReceiver_time(int receiver_time) {
        this.receiver_time = receiver_time;
    }

    public void setShop_flag(String shop_flag) {
        this.shop_flag = shop_flag;
    }

    public void setIs_part_consign(int is_part_consign) {
        this.is_part_consign = is_part_consign;
    }

    public void setBuyer_message(String buyer_message) {
        this.buyer_message = buyer_message;
    }

    public void setReceiver_city(String receiver_city) {
        this.receiver_city = receiver_city;
    }

    public void setSeller_rate(int seller_rate) {
        this.seller_rate = seller_rate;
    }

    public void setPay_time(int pay_time) {
        this.pay_time = pay_time;
    }

    public void setConsume_point_fee(int consume_point_fee) {
        this.consume_point_fee = consume_point_fee;
    }

    public void setUser_memo(String user_memo) {
        this.user_memo = user_memo;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public void setDisabled(int disabled) {
        this.disabled = disabled;
    }

    public void setDiscount_fee(String discount_fee) {
        this.discount_fee = discount_fee;
    }

    public void setStep_trade_status(String step_trade_status) {
        this.step_trade_status = step_trade_status;
    }

    public void setReceiver_state(String receiver_state) {
        this.receiver_state = receiver_state;
    }

    public void setInvoice_type(String invoice_type) {
        this.invoice_type = invoice_type;
    }

    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
    }

    public void setReceiver_district(String receiver_district) {
        this.receiver_district = receiver_district;
    }

    public void setZiti_addr(String ziti_addr) {
        this.ziti_addr = ziti_addr;
    }

    public void setItemnum(int itemnum) {
        this.itemnum = itemnum;
    }

    public void setInvoice_fee(String invoice_fee) {
        this.invoice_fee = invoice_fee;
    }

    public void setTrade_memo(String trade_memo) {
        this.trade_memo = trade_memo;
    }

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
    }

    public void setTimeout_action_time(String timeout_action_time) {
        this.timeout_action_time = timeout_action_time;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setNeedpaytime(String needpaytime) {
        this.needpaytime = needpaytime;
    }

    public void setOrder(List<OrderEntity> order) {
        this.order = order;
    }

    public String getExamine_reason() {
        return examine_reason;
    }

    public int getIs_price_approve() {
        return is_price_approve;
    }

    public String getTrade_from() {
        return trade_from;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getAdjust_fee() {
        return adjust_fee;
    }

    public String getPayed_fee() {
        return payed_fee;
    }

    public String getPost_fee() {
        return post_fee;
    }

    public String getMessage() {
        return message;
    }

    public long getTid() {
        return tid;
    }

    public int getCreated_time() {
        return created_time;
    }

    public String getDlytmpl_id() {
        return dlytmpl_id;
    }

    public String getTitle() {
        return title;
    }

    public int getNeed_invoice() {
        return need_invoice;
    }

    public String getInvoice_name() {
        return invoice_name;
    }

    public int getIs_clearing() {
        return is_clearing;
    }

    public String getReal_point_fee() {
        return real_point_fee;
    }

    public String getPay_type() {
        return pay_type;
    }

    public String getReceiver_zip() {
        return receiver_zip;
    }

    public int getService_id() {
        return service_id;
    }

    public String getStatus() {
        return status;
    }

    public int getModified_time() {
        return modified_time;
    }

    public String getShop_memo() {
        return shop_memo;
    }

    public String getSend_time() {
        return send_time;
    }

    public String getReceiver_mobile() {
        return receiver_mobile;
    }

    public String getBuyer_area() {
        return buyer_area;
    }

    public String getArea_id() {
        return area_id;
    }

    public String getInvoice_main() {
        return invoice_main;
    }

    public String getApprove_time() {
        return approve_time;
    }

    public int getAnony() {
        return anony;
    }

    public String getCancel_reason() {
        return cancel_reason;
    }

    public String getShipping_type() {
        return shipping_type;
    }

    public int getBuyer_rate() {
        return buyer_rate;
    }

    public String getPayment() {
        return payment;
    }

    public int getConsign_time() {
        return consign_time;
    }

    public String getReceiver_phone() {
        return receiver_phone;
    }

    public String getEnd_time() {
        return end_time;
    }

    public String getReceiver_address() {
        return receiver_address;
    }

    public String getStep_paid_fee() {
        return step_paid_fee;
    }

    public String getIp() {
        return ip;
    }

    public int getObtain_point_fee() {
        return obtain_point_fee;
    }

    public int getReceiver_time() {
        return receiver_time;
    }

    public String getShop_flag() {
        return shop_flag;
    }

    public int getIs_part_consign() {
        return is_part_consign;
    }

    public String getBuyer_message() {
        return buyer_message;
    }

    public String getReceiver_city() {
        return receiver_city;
    }

    public int getSeller_rate() {
        return seller_rate;
    }

    public int getPay_time() {
        return pay_time;
    }

    public int getConsume_point_fee() {
        return consume_point_fee;
    }

    public String getUser_memo() {
        return user_memo;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public int getDisabled() {
        return disabled;
    }

    public String getDiscount_fee() {
        return discount_fee;
    }

    public String getStep_trade_status() {
        return step_trade_status;
    }

    public String getReceiver_state() {
        return receiver_state;
    }

    public String getInvoice_type() {
        return invoice_type;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public String getReceiver_district() {
        return receiver_district;
    }

    public String getZiti_addr() {
        return ziti_addr;
    }

    public int getItemnum() {
        return itemnum;
    }

    public String getInvoice_fee() {
        return invoice_fee;
    }

    public String getTrade_memo() {
        return trade_memo;
    }

    public int getShop_id() {
        return shop_id;
    }

    public String getTimeout_action_time() {
        return timeout_action_time;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getNeedpaytime() {
        return needpaytime;
    }

    public String getAutoreceive_parameter() {
        return autoreceive_parameter;
    }

    public void setAutoreceive_parameter(String autoreceive_parameter) {
        this.autoreceive_parameter = autoreceive_parameter;
    }

    public String getNeedpaytime_parameter() {
        return needpaytime_parameter;
    }

    public void setNeedpaytime_parameter(String needpaytime_parameter) {
        this.needpaytime_parameter = needpaytime_parameter;
    }

    public List<OrderEntity> getOrder() {
        return order;
    }

    public static class OrderEntity implements Serializable {
        private String spec_nature_info;
        private String sku_properties_name;
        private String bn;
        private String end_time;
        private String refund_fee;
        private int buyer_rate;
        private String outer_iid;
        private String refund_id;
        private int item_id;
        private int num;
        private String invoice_no;
        private String divide_order_fee;
        private String bind_oid;
        private int shop_id;
        private String old_price;
        private String discount_fee;
        private int sub_stock;
        private String payment;
        private String timeout_action_time;
        private long oid;
        private String shipping_type;
        private String status;
        private long tid;
        private String price;
        private String approve_time;
        private String part_mjz_discount;
        private String pay_time;
        private String logistics_company;
        private String consign_time;
        private int modified_time;
        private String cat_service_rate;
        private int user_id;
        private int seller_rate;
        private String aftersales_status;
        private int anony;
        private String order_from;
        private String sku_id;
        private int sendnum;
        private String total_fee;
        private String adjust_fee;
        private String complaints_status;
        private int is_oversold;
        private String outer_sku_id;
        private String title;
        private int service_id;
        private String pic_path;
        private String settle_price;

        public void setSpec_nature_info(String spec_nature_info) {
            this.spec_nature_info = spec_nature_info;
        }

        public void setSku_properties_name(String sku_properties_name) {
            this.sku_properties_name = sku_properties_name;
        }

        public void setBn(String bn) {
            this.bn = bn;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public void setRefund_fee(String refund_fee) {
            this.refund_fee = refund_fee;
        }

        public void setBuyer_rate(int buyer_rate) {
            this.buyer_rate = buyer_rate;
        }

        public void setOuter_iid(String outer_iid) {
            this.outer_iid = outer_iid;
        }

        public void setRefund_id(String refund_id) {
            this.refund_id = refund_id;
        }

        public void setItem_id(int item_id) {
            this.item_id = item_id;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public void setInvoice_no(String invoice_no) {
            this.invoice_no = invoice_no;
        }

        public void setDivide_order_fee(String divide_order_fee) {
            this.divide_order_fee = divide_order_fee;
        }

        public void setBind_oid(String bind_oid) {
            this.bind_oid = bind_oid;
        }

        public void setShop_id(int shop_id) {
            this.shop_id = shop_id;
        }

        public void setOld_price(String old_price) {
            this.old_price = old_price;
        }

        public void setDiscount_fee(String discount_fee) {
            this.discount_fee = discount_fee;
        }

        public void setSub_stock(int sub_stock) {
            this.sub_stock = sub_stock;
        }

        public void setPayment(String payment) {
            this.payment = payment;
        }

        public void setTimeout_action_time(String timeout_action_time) {
            this.timeout_action_time = timeout_action_time;
        }

        public void setOid(long oid) {
            this.oid = oid;
        }

        public void setShipping_type(String shipping_type) {
            this.shipping_type = shipping_type;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setTid(long tid) {
            this.tid = tid;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public void setApprove_time(String approve_time) {
            this.approve_time = approve_time;
        }

        public void setPart_mjz_discount(String part_mjz_discount) {
            this.part_mjz_discount = part_mjz_discount;
        }

        public void setPay_time(String pay_time) {
            this.pay_time = pay_time;
        }

        public void setLogistics_company(String logistics_company) {
            this.logistics_company = logistics_company;
        }

        public void setConsign_time(String consign_time) {
            this.consign_time = consign_time;
        }

        public void setModified_time(int modified_time) {
            this.modified_time = modified_time;
        }

        public void setCat_service_rate(String cat_service_rate) {
            this.cat_service_rate = cat_service_rate;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public void setSeller_rate(int seller_rate) {
            this.seller_rate = seller_rate;
        }

        public void setAftersales_status(String aftersales_status) {
            this.aftersales_status = aftersales_status;
        }

        public void setAnony(int anony) {
            this.anony = anony;
        }

        public void setOrder_from(String order_from) {
            this.order_from = order_from;
        }

        public void setSku_id(String sku_id) {
            this.sku_id = sku_id;
        }

        public void setSendnum(int sendnum) {
            this.sendnum = sendnum;
        }

        public void setTotal_fee(String total_fee) {
            this.total_fee = total_fee;
        }

        public void setAdjust_fee(String adjust_fee) {
            this.adjust_fee = adjust_fee;
        }

        public void setComplaints_status(String complaints_status) {
            this.complaints_status = complaints_status;
        }

        public void setIs_oversold(int is_oversold) {
            this.is_oversold = is_oversold;
        }

        public void setOuter_sku_id(String outer_sku_id) {
            this.outer_sku_id = outer_sku_id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setService_id(int service_id) {
            this.service_id = service_id;
        }

        public void setPic_path(String pic_path) {
            this.pic_path = pic_path;
        }

        public void setSettle_price(String settle_price) {
            this.settle_price = settle_price;
        }

        public String getSpec_nature_info() {
            return spec_nature_info;
        }

        public String getSku_properties_name() {
            return sku_properties_name;
        }

        public String getBn() {
            return bn;
        }

        public String getEnd_time() {
            return end_time;
        }

        public String getRefund_fee() {
            return refund_fee;
        }

        public int getBuyer_rate() {
            return buyer_rate;
        }

        public String getOuter_iid() {
            return outer_iid;
        }

        public String getRefund_id() {
            return refund_id;
        }

        public int getItem_id() {
            return item_id;
        }

        public int getNum() {
            return num;
        }

        public String getInvoice_no() {
            return invoice_no;
        }

        public String getDivide_order_fee() {
            return divide_order_fee;
        }

        public String getBind_oid() {
            return bind_oid;
        }

        public int getShop_id() {
            return shop_id;
        }

        public String getOld_price() {
            return old_price;
        }

        public String getDiscount_fee() {
            return discount_fee;
        }

        public int getSub_stock() {
            return sub_stock;
        }

        public String getPayment() {
            return payment;
        }

        public String getTimeout_action_time() {
            return timeout_action_time;
        }

        public long getOid() {
            return oid;
        }

        public String getShipping_type() {
            return shipping_type;
        }

        public String getStatus() {
            return status;
        }

        public long getTid() {
            return tid;
        }

        public String getPrice() {
            return price;
        }

        public String getApprove_time() {
            return approve_time;
        }

        public String getPart_mjz_discount() {
            return part_mjz_discount;
        }

        public String getPay_time() {
            return pay_time;
        }

        public String getLogistics_company() {
            return logistics_company;
        }

        public String getConsign_time() {
            return consign_time;
        }

        public int getModified_time() {
            return modified_time;
        }

        public String getCat_service_rate() {
            return cat_service_rate;
        }

        public int getUser_id() {
            return user_id;
        }

        public int getSeller_rate() {
            return seller_rate;
        }

        public String getAftersales_status() {
            return aftersales_status;
        }

        public int getAnony() {
            return anony;
        }

        public String getOrder_from() {
            return order_from;
        }

        public String getSku_id() {
            return sku_id;
        }

        public int getSendnum() {
            return sendnum;
        }

        public String getTotal_fee() {
            return total_fee;
        }

        public String getAdjust_fee() {
            return adjust_fee;
        }

        public String getComplaints_status() {
            return complaints_status;
        }

        public int getIs_oversold() {
            return is_oversold;
        }

        public String getOuter_sku_id() {
            return outer_sku_id;
        }

        public String getTitle() {
            return title;
        }

        public int getService_id() {
            return service_id;
        }

        public String getPic_path() {
            return pic_path;
        }

        public String getSettle_price() {
            return settle_price;
        }
    }
}
