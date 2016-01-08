package com.renyu.carclient.model;

import com.renyu.carclient.commons.ParamUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by renyu on 15/12/25.
 */
public class JsonParse {

    public static int getResultInt(String string) {
        try {
            JSONObject object=new JSONObject(string);
            return object.getInt("errorcode");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public static String getErrorValue(String string) {
        try {
            JSONObject object=new JSONObject(string);
            JSONObject error=object.getJSONObject("error");
            return error.getString("message");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "未知错误";
    }

    public static String getResultValue(String string) {
        if (getResultInt(string)==0) {
            try {
                JSONObject object=new JSONObject(string);
                JSONObject result=object.getJSONObject("result");
                return result.getString("result_info");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        else {
            return getErrorValue(string);
        }
    }

    /**
     * 获取查询一级列表
     * @param string
     * @return
     */
    public static ArrayList<CategoryModel> getCategoryListModel(String string) {
        if (getResultInt(string)==0) {
            try {
                JSONObject object=new JSONObject(string);
                JSONObject result=object.getJSONObject("result");
                JSONObject data1=result.getJSONObject("data");
                JSONArray data2=data1.getJSONArray("data");
                ArrayList<CategoryModel> models=new ArrayList<>();
                for (int i=0;i<data2.length();i++) {
                    JSONObject object1=data2.getJSONObject(i);
                    CategoryModel model=new CategoryModel();
                    model.setCat_id(object1.getInt("cat_id"));
                    model.setCat_logo(object1.getString("cat_logo"));
                    model.setCat_name(object1.getString("cat_name"));
                    model.setParent_id(object1.getInt("parent_id"));
                    models.add(model);
                }
                return models;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取查询二级列表
     * @param string
     * @return
     */
    public static ArrayList<CategoryModel> getSecondCategoryListModel(String string) {
        if (getResultInt(string)==0) {
            try {
                JSONObject object=new JSONObject(string);
                JSONObject result=object.getJSONObject("result");
                JSONObject data1=result.getJSONObject("data");
                JSONArray data2=data1.getJSONArray("data");
                ArrayList<CategoryModel> models=new ArrayList<>();
                for (int i=0;i<data2.length();i++) {
                    JSONObject object1=data2.getJSONObject(i);
                    CategoryModel model=new CategoryModel();
                    model.setCat_id(object1.getInt("cat_id"));
                    model.setCat_name(object1.getString("cat_name"));
                    model.setParent_id(object1.getInt("parent_id"));
                    model.setOpen(true);
                    model.setParent_id(-1);
                    ArrayList<CategoryModel> listModels=new ArrayList<>();
                    JSONArray sub_cat=object1.getJSONArray("sub_cat");
                    for (int j=0;j<sub_cat.length();j++) {
                        JSONObject temp=sub_cat.getJSONObject(j);
                        CategoryModel childModel=new CategoryModel();
                        childModel.setCat_id(temp.getInt("cat_id"));
                        childModel.setCat_name(temp.getString("cat_name"));
                        childModel.setParent_id(temp.getInt("parent_id"));
                        childModel.setOpen(true);
                        listModels.add(childModel);
                    }
                    model.setLists(listModels);
                    models.add(model);
                }
                return models;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取品牌一级列表
     * @param string
     * @return
     */
    public static ArrayList<SearchBrandModel> getSearchBrandListModel(String string) {
        if (getResultInt(string)==0) {
            try {
                JSONObject object=new JSONObject(string);
                JSONObject result=object.getJSONObject("result");
                JSONObject data1=result.getJSONObject("data");
                JSONArray data2=data1.getJSONArray("data");
                ArrayList<SearchBrandModel> models=new ArrayList<>();
                for (int i=0;i<data2.length();i++) {
                    JSONObject object1=data2.getJSONObject(i);
                    SearchBrandModel model=new SearchBrandModel();
                    model.setBrand_id(object1.getInt("brand_id"));
                    model.setBrand_logo(object1.getString("brand_logo"));
                    model.setBrand_name(object1.getString("brand_name"));
                    models.add(model);
                }
                return models;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取品牌二级列表
     * @param string
     * @return
     */
    public static ArrayList<SearchBrandModel> getSecondSearchBrandModel(String string) {
        if (getResultInt(string)==0) {
            try {
                JSONObject object=new JSONObject(string);
                JSONObject result=object.getJSONObject("result");
                JSONObject data1=result.getJSONObject("data");
                JSONArray data2=data1.getJSONArray("data");
                ArrayList<SearchBrandModel> models=new ArrayList<>();
                for (int i=0;i<data2.length();i++) {
                    JSONObject object1=data2.getJSONObject(i);
                    SearchBrandModel model=new SearchBrandModel();
                    model.setCat_id(object1.getString("cat_id"));
                    model.setCat_name(object1.getString("cat_name"));
                    models.add(model);
                }
                return models;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取商品列表
     * @param string
     * @return
     */
    public static ArrayList<GoodsListModel> getGoodsListModel(String string) {
        if (getResultInt(string)==0) {
            try {
                JSONObject object=new JSONObject(string);
                JSONObject result=object.getJSONObject("result");
                JSONObject data1=result.getJSONObject("data");
                JSONArray data2=data1.getJSONArray("data");
                ArrayList<GoodsListModel> models=new ArrayList<>();
                for (int i=0;i<data2.length();i++) {
                    JSONObject object1=data2.getJSONObject(i);
                    GoodsListModel model=new GoodsListModel();
                    model.setTitle(object1.getString("title"));
                    model.setImage_default_id(object1.getString("image_default_id"));
                    model.setPrice(object1.getString("price"));
                    model.setItem_id(object1.getInt("item_id"));
                    models.add(model);
                }
                return models;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 商品详情
     * @param string
     * @return
     */
    public static GoodsListModel getGoodsDetailModel(String string) {
        try {
            JSONObject object=new JSONObject(string);
            JSONObject result=object.getJSONObject("result");
            JSONObject data1=result.getJSONObject("data");
            GoodsListModel model=new GoodsListModel();
            model.setParams(data1.getString("params"));
            model.setList_image(data1.getString("list_image"));
            model.setImage_default_id(data1.getString("image_default_id"));
            model.setTitle(data1.getString("title"));
            model.setBrand_id(data1.getInt("brand_id"));
            model.setItem_id(data1.getInt("item_id"));
            model.setStore(data1.getInt("store"));
            model.setPrice(data1.getString("price"));
            model.setReal_price(data1.getString("real_price"));
            model.setChoucang(data1.getInt("isShoucang")==1?true:false);
            return model;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取订单列表
     * @param string
     * @return
     */
    public static ArrayList<OrderModel> getOrderListModel(String string) {
        if (getResultInt(string)==0) {
            try {
                JSONObject object=new JSONObject(string);
                JSONObject result=object.getJSONObject("result");
                JSONObject data1=result.getJSONObject("data");
                JSONArray data2=data1.getJSONArray("data");
                ArrayList<OrderModel> models=new ArrayList<>();
                for (int i=0;i<data2.length();i++) {
                    JSONObject object1=data2.getJSONObject(i);
                    OrderModel model=new OrderModel();
                    model.setTid(object1.getLong("tid"));
                    model.setTitle(object1.getString("title"));
                    model.setStatus(object1.getString("status"));
                    model.setItemnum(object1.getInt("itemnum"));
                    model.setTotal_fee(object1.getString("total_fee"));
                    model.setUser_memo(object1.getString("user_memo"));
                    try {
                        model.setAutoreceive_parameter(object1.getString("autoreceive_parameter"));
                    } catch (Exception e) {
                        model.setAutoreceive_parameter("");
                    }
                    try {
                        model.setNeedpaytime_parameter(object1.getString("needpaytime_parameter"));
                    } catch (Exception e) {
                        model.setNeedpaytime_parameter("");
                    }
                    model.setBuyer_area(object1.getString("buyer_area"));
                    model.setCreated_time(object1.getInt("created_time"));
                    model.setConsign_time(ParamUtils.converInt(object1.getString("consign_time")));
                    model.setReceiver_time(ParamUtils.converInt(object1.getString("receiver_time")));
                    model.setPay_time(ParamUtils.converInt(object1.getString("pay_time")));
                    model.setReceiver_address(object1.getString("receiver_address"));
                    model.setReceiver_name(object1.getString("receiver_name"));
                    model.setReceiver_mobile(object1.getString("receiver_mobile"));
                    ArrayList<OrderModel.OrderEntity> orders=new ArrayList<>();
                    for (int j=0;j<object1.getJSONArray("order").length();j++) {
                        JSONObject object2=object1.getJSONArray("order").getJSONObject(j);
                        OrderModel.OrderEntity entity=new OrderModel.OrderEntity();
                        entity.setOid(object2.getLong("oid"));
                        entity.setTid(object2.getLong("tid"));
                        entity.setTitle(object2.getString("title"));
                        entity.setNum(object2.getInt("num"));
                        entity.setOld_price(object2.getString("old_price"));
                        entity.setPrice(object2.getString("price"));
                        entity.setSpec_nature_info(ParamUtils.converNull(object2.getString("spec_nature_info")));
                        entity.setPic_path(object2.getString("pic_path"));
                        entity.setAftersales_status(object2.getString("aftersales_status"));
                        orders.add(entity);
                    }
                    model.setOrder(orders);
                    models.add(model);
                }
                return models;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 收藏列表
     * @param string
     * @return
     */
    public static ArrayList<CollectionModel> getCollectionModels(String string) {
        if (getResultInt(string)==0) {
            try {
                JSONObject object=new JSONObject(string);
                JSONObject result=object.getJSONObject("result");
                JSONObject data1=result.getJSONObject("data");
                JSONArray data2=data1.getJSONArray("data");
                ArrayList<CollectionModel> models=new ArrayList<>();
                for (int i=0;i<data2.length();i++) {
                    JSONObject object1=data2.getJSONObject(i);
                    CollectionModel model=new CollectionModel();
                    model.setGoods_name(object1.getString("goods_name"));
                    model.setCat_id(object1.getInt("cat_id"));
                    model.setItem_id(object1.getInt("item_id"));
                    model.setUser_id(object1.getInt("user_id"));
                    model.setImage_default_id(object1.getString("image_default_id"));
                    model.setGnotify_id(object1.getInt("gnotify_id"));
                    model.setGoods_price(object1.getString("goods_price"));
                    models.add(model);
                }
                return models;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取购物车列表
     * @param string
     * @return
     */
    public static ArrayList<ProductModel> getCartList(String string) {
        if (getResultInt(string)==0) {
            try {
                JSONObject object=new JSONObject(string);
                JSONObject result=object.getJSONObject("result");
                JSONObject data1=result.getJSONObject("data");
                JSONArray data2=data1.getJSONArray("data");
                ArrayList<ProductModel> models=new ArrayList<>();
                for (int i=0;i<data2.length();i++) {
                    JSONObject object1=data2.getJSONObject(i);
                    ProductModel model=new ProductModel();
                    model.setCart_id(object1.getInt("cart_id"));
                    model.setImage_default_id(object1.getString("image_default_id"));
                    model.setTitle(object1.getString("title"));
                    model.setItem_id(object1.getInt("item_id"));
                    model.setPrice(object1.getString("price"));
                    model.setQuantity(object1.getInt("quantity"));
                    model.setSku_id(object1.getInt("sku_id"));
                    model.setReal_price(object1.getString("real_price"));
                    models.add(model);
                }
                return models;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取登录信息
     * @param string
     * @return
     */
    public static UserModel getLoginModel(String string) {
        try {
            JSONObject object=new JSONObject(string);
            JSONObject result=object.getJSONObject("result");
            JSONObject data=result.getJSONObject("data");
            UserModel model=new UserModel();
            model.setInit_amount(data.getInt("init_amount"));
            model.setAmount(data.getInt("amount"));
            model.setUser_id(data.getInt("user_id"));
            model.setRepairdepot_name(data.getString("repairdepot_name"));
            model.setHead_photo(ParamUtils.converNull(data.getString("head_photo")));
            return model;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取收货地址
     * @param string
     * @return
     */
    public static ArrayList<AddressModel> getAddressModels(String string) {
        try {
            JSONObject object=new JSONObject(string);
            JSONObject result=object.getJSONObject("result");
            JSONObject data1=result.getJSONObject("data");
            JSONArray data2=data1.getJSONArray("data");
            ArrayList<AddressModel> models=new ArrayList<>();
            for (int i=0;i<data2.length();i++) {
                JSONObject object1=data2.getJSONObject(i);
                AddressModel model=new AddressModel();
                model.setAddr(object1.getString("addr"));
                model.setAddr_id(object1.getInt("addr_id"));
                model.setArea(object1.getString("area"));
                model.setDef_addr(object1.getInt("def_addr"));
                model.setMobile(object1.getString("mobile"));
                model.setName(object1.getString("name"));
                model.setTel(ParamUtils.converNull(object1.getString("tel")));
                model.setUser_id(object1.getInt("user_id"));
                model.setZip(object1.getString("zip"));
                models.add(model);
            }
            return models;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取基本信息
     * @param string
     * @return
     */
    public static MyInfoModel getMyInfo(String string) {
        try {
            JSONObject object=new JSONObject(string);
            JSONObject result=object.getJSONObject("result");
            JSONObject data1=result.getJSONObject("data");
            MyInfoModel model=new MyInfoModel();
            model.setAccount_name(data1.getString("account_name"));
            model.setBank_account(data1.getString("bank_account"));
            model.setBank_name(data1.getString("bank_name"));
            model.setBusiness_encoding(data1.getString("business_encoding"));
            model.setContact_person(data1.getString("contact_person"));
            model.setContact_phone(data1.getString("contact_phone"));
            model.setContact_tel(data1.getString("contact_tel"));
            model.setCorporation(data1.getString("corporation"));
            model.setEmail(data1.getString("email"));
            model.setLogin_account(data1.getString("login_account"));
            model.setReg_address(data1.getString("reg_address"));
            model.setRepairdepot_name(data1.getString("repairdepot_name"));
            model.setRevenues_code(data1.getString("revenues_code"));
            return model;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取头像
     * @param string
     * @return
     */
    public static String getAvatar(String string) {
        try {
            JSONObject object=new JSONObject(string);
            JSONObject result=object.getJSONObject("result");
            JSONObject data1=result.getJSONObject("data");
            return data1.getString("head_photo");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static HashMap<String, String> getNum(String string) {
        HashMap<String, String> map=null;
        try {
            map=new HashMap<>();
            JSONObject object=new JSONObject(string);
            JSONObject result=object.getJSONObject("result");
            JSONObject data1=result.getJSONObject("data");
            JSONObject data2=data1.getJSONObject("data");
            map.put("TRADE_FINISHED", data2.getString("TRADE_FINISHED"));
            map.put("TRADE_CANCEL", data2.getString("TRADE_CANCEL"));
            map.put("WAIT_APPROVE", data2.getString("WAIT_APPROVE"));
            map.put("AFTERSALES", data2.getString("AFTERSALES"));
            map.put("DELIVER_GOODS", data2.getString("DELIVER_GOODS"));
            map.put("WAIT_GOODS", data2.getString("WAIT_GOODS"));
            map.put("TRADE_CLOSED", data2.getString("TRADE_CLOSED"));
            map.put("WAIT_CONFRIM", data2.getString("WAIT_CONFRIM"));
            map.put("RECEIVE_GOODS", data2.getString("RECEIVE_GOODS"));
            return map;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
