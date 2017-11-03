package com.jierong.share.mvp.model.info;

import com.jierong.share.util.JSONUtil;

import java.io.Serializable;
import java.util.List;

/**
 * 广告模型
 */
public class Budgetinfo implements Serializable {


    /**
     * goodname : 【捷融】搜客宝 销售助手电销神器 全国企业客户资源 月会员
     * from : 洛阳捷融网络科技有限公司
     * ordernumber : 28628511716258668
     * pay_money : 87.5
     * predictmoney : 8.75
     * ordertime : 2017-06-15 16:24:04
     */

    private String goodname;
    private String from;
    private String ordernumber;
    private String pay_money;
    private double predictmoney;
    private String ordertime;

    // gson转换
    public static Budgetinfo fromJSON(String JSONString) {
        if (JSONString == null)
            return null;
        return JSONUtil.fromJson(JSONString, Budgetinfo.class);
    }

    public static List<Budgetinfo> fromJSONS(String JSONSString) {
        if (JSONSString == null) return null;
        return JSONUtil.fromJsons(JSONSString, Budgetinfo.class);
    }

    public String toJSON() {
        return JSONUtil.toJson(this);
    }

    public String toString() {
        return toJSON();
    }


    public String getGoodname() {
        return goodname;
    }

    public void setGoodname(String goodname) {
        this.goodname = goodname;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getOrdernumber() {
        return ordernumber;
    }

    public void setOrdernumber(String ordernumber) {
        this.ordernumber = ordernumber;
    }

    public String getPay_money() {
        return pay_money;
    }

    public void setPay_money(String pay_money) {
        this.pay_money = pay_money;
    }

    public double getPredictmoney() {
        return predictmoney;
    }

    public void setPredictmoney(double predictmoney) {
        this.predictmoney = predictmoney;
    }

    public String getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(String ordertime) {
        this.ordertime = ordertime;
    }
}
