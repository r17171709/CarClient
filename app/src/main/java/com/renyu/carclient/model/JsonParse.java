package com.renyu.carclient.model;

import com.renyu.carclient.commons.ParamUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
                    model.setAutoreceive_parameter(object1.getString("autoreceive_parameter"));
                    model.setNeedpaytime_parameter(object1.getString("needpaytime_parameter"));
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
}
