package com.jierong.share.mvp.model.info;

import com.jierong.share.util.JSONUtil;
import java.io.Serializable;
import java.util.List;

/**
 * 购物记录模型
 */
public class ShopInfo implements Serializable {
    private String id;      // 商品id
    private String name;    // 商品名称
    private String time;    // 购买时间
    private String price;   // 购买价格

    private int flag;         // 1是已返利，0是预计返利
    private String fanli;     // 预计返利

    private String tel;         // 推荐的手机号
    private String store;       // 商店名称（广告名称）

    // get,set注入
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public int getFlag() {
        return flag;
    }
    public void setFlag(int flag) {
        this.flag = flag;
    }
    public String getFanli() {
        return fanli;
    }
    public void setFanli(String fanli) {
        this.fanli = fanli;
    }
    public String getTel() {
        return tel;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }
    public String getStore() {
        return store;
    }
    public void setStore(String store) {
        this.store = store;
    }

    // gson转换
    public static ShopInfo fromJSON(String JSONString) {
        if (JSONString == null)
            return null;
        return JSONUtil.fromJson(JSONString, ShopInfo.class);
    }
    public static List<ShopInfo> fromJSONS(String JSONSString) {
        if (JSONSString == null) return null;
        return JSONUtil.fromJsons(JSONSString, ShopInfo.class);
    }
    public String toJSON() {
        return JSONUtil.toJson(this);
    }
    public String toString() {
        return toJSON();
    }

}
