package com.jierong.share.mvp.model.info;

import com.jierong.share.util.JSONUtil;
import java.io.Serializable;
import java.util.List;

/**
 * 用户模型
 */
public class RegistUserInfo implements Serializable {
    private String uId;         // 用户id
    private String uIc;         // 用户头像地址
    private String uName;       // 用户名称
    private String uTel;        // 用户电话
    private String uToken;      // 用户凭证
    //private int uType;          // 用户分类：1广告商、2普通用户

    private String uThree;      // 已绑定的第三方
    private String uAddr;       // 用户地址
    private String uShareNum;   // 当天分享数目
    private String uComeMoney;  // 签到收益
    private String uGiveMoney;  // 推荐收益
    private String uShareMoney; // 分享收益

    // get,set注入
    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getuIc() {
        return uIc;
    }

    public void setuIc(String uIc) {
        this.uIc = uIc;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuTel() {
        return uTel;
    }

    public void setuTel(String uTel) {
        this.uTel = uTel;
    }

    public String getuToken() {
        return uToken;
    }

    public void setuToken(String uToken) {
        this.uToken = uToken;
    }

    //    public int getuType() {
//        return uType;
//    }
//    public void setuType(int uType) {
//        this.uType = uType;
//    }
    public String getuThree() {
        return uThree;
    }

    public void setuThree(String uThree) {
        this.uThree = uThree;
    }

    public String getuAddr() {
        return uAddr;
    }

    public void setuAddr(String uAddr) {
        this.uAddr = uAddr;
    }

    public String getuShareNum() {
        return uShareNum;
    }

    public void setuShareNum(String uShareNum) {
        this.uShareNum = uShareNum;
    }

    public String getuComeMoney() {
        return uComeMoney;
    }

    public void setuComeMoney(String uComeMoney) {
        this.uComeMoney = uComeMoney;
    }

    public String getuGiveMoney() {
        return uGiveMoney;
    }

    public void setuGiveMoney(String uGiveMoney) {
        this.uGiveMoney = uGiveMoney;
    }

    public String getuShareMoney() {
        return uShareMoney;
    }

    public void setuShareMoney(String uShareMoney) {
        this.uShareMoney = uShareMoney;
    }

    // gson转换
    public static RegistUserInfo fromJSON(String JSONString) {
        if (JSONString == null)
            return null;
        return JSONUtil.fromJson(JSONString, RegistUserInfo.class);
    }

    public static List<RegistUserInfo> fromJSONS(String JSONSString) {
        if (JSONSString == null) return null;
        return JSONUtil.fromJsons(JSONSString, RegistUserInfo.class);
    }

    public String toJSON() {
        return JSONUtil.toJson(this);
    }

    public String toString() {
        return toJSON();
    }
}
