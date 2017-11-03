package com.jierong.share.mvp.model.info;

import com.jierong.share.util.JSONUtil;
import java.io.Serializable;
import java.util.List;

/**
 * 淘宝商品模型
 */
public class TaoBaoInfo implements Serializable {
    private String pict_url;            // 商品的图片
    private String title;               // 商品标题
    private String zk_final_price;       // 商品的价格
    private String tk_rate;             // 预计返利
    private String day;                  // 活动时间
    private String volume;               // 月销量
    private String coupon_info;          // 优惠券描述信息
    private String coupon_remain_count;     // 优惠券剩余个数

    private String coupon_click_url;         // 优惠券领取链接
    private String click_url;               // 商品购买链接

    // get,set注入
    public String getPict_url() {
        return pict_url;
    }
    public void setPict_url(String pict_url) {
        this.pict_url = pict_url;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getZk_final_price() {
        return zk_final_price;
    }

    public void setZk_final_price(String zk_final_price) {
        this.zk_final_price = zk_final_price;
    }
    public String getTk_rate() {
        return tk_rate;
    }
    public void setTk_rate(String tk_rate) {
        this.tk_rate = tk_rate;
    }
    public String getDay() {
        return day;
    }
    public void setDay(String day) {
        this.day = day;
    }
    public String getVolume() {
        return volume;
    }
    public void setVolume(String volume) {
        this.volume = volume;
    }
    public String getCoupon_info() {
        return coupon_info;
    }
    public void setCoupon_info(String coupon_info) {
        this.coupon_info = coupon_info;
    }
    public String getCoupon_remain_count() {
        return coupon_remain_count;
    }
    public void setCoupon_remain_count(String coupon_remain_count) {
        this.coupon_remain_count = coupon_remain_count;
    }
    public String getCoupon_click_url() {
        return coupon_click_url;
    }
    public void setCoupon_click_url(String coupon_click_url) {
        this.coupon_click_url = coupon_click_url;
    }
    public String getClick_url() {
        return click_url;
    }
    public void setClick_url(String click_url) {
        this.click_url = click_url;
    }

    // gson转换
    public static TaoBaoInfo fromJSON(String JSONString) {
        if (JSONString == null)
            return null;
        return JSONUtil.fromJson(JSONString, TaoBaoInfo.class);
    }
    public static List<TaoBaoInfo> fromJSONS(String JSONSString) {
        if (JSONSString == null) return null;
        return JSONUtil.fromJsons(JSONSString, TaoBaoInfo.class);
    }
    public String toJSON() {
        return JSONUtil.toJson(this);
    }
    public String toString() {
        return toJSON();
    }
}
