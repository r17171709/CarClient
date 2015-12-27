package com.renyu.carclient.model;

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
                    model.setMkt_price(object1.getString("mkt_price"));
                    model.setParams(object1.getString("params"));
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
}
