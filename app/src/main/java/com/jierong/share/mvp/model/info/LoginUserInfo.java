package com.jierong.share.mvp.model.info;

import com.jierong.share.util.JSONUtil;
import java.io.Serializable;
import java.util.List;

/**
 * 用户模型
 */
public class LoginUserInfo implements Serializable {
    private String uId;         // 用户id
    private String uIc;         // 用户头像地址
    private String uName;       // 用户名称
    private String uTel;        // 用户电话
    private String uToken;      // 用户凭证
    private String way;         // 用户登录方式
    //private int uType;          // 用户分类：1广告商、2普通用户

    private String uThree;      // 已绑定的第三方
    private String uAddr;       // 用户地址
    private String uShareNum;   // 当天分享数目
    private String uComeMoney;  // 签到收益
    private String uGiveMoney;  // 推荐收益
    private String uShareMoney; // 分享收益
    private String uMoneyAll;
    private String rebate;  //收益积分
    private String name; // 用户名称

    private String bank; // 银行卡
    private String idcard; // 身份证号码
    private String new_state; // 是否是新人

    private boolean bindYqm;     // 是否绑定过邀请码
    private String grade_mark;      // 用户等级图标

    // get,set注入
    public String getRebate() {
        return rebate;
    }

    public void setRebate(String rebate) {
        this.rebate = rebate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getNew_state() {
        return new_state;
    }

    public void setNew_state(String new_state) {
        this.new_state = new_state;
    }

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

    public boolean isBindYqm() {
        return bindYqm;
    }

    public void setBindYqm(boolean bindYqm) {
        this.bindYqm = bindYqm;
    }

    public String getGrade_mark() {
        return grade_mark;
    }

    public void setGrade_mark(String grade_mark) {
        this.grade_mark = grade_mark;
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }

    // gson转换
    public static LoginUserInfo fromJSON(String JSONString) {
        if (JSONString == null)
            return null;
        return JSONUtil.fromJson(JSONString, LoginUserInfo.class);
    }

    public static List<LoginUserInfo> fromJSONS(String JSONSString) {
        if (JSONSString == null) return null;
        return JSONUtil.fromJsons(JSONSString, LoginUserInfo.class);
    }

    public String toJSON() {
        return JSONUtil.toJson(this);
    }

    public String toString() {
        return toJSON();
    }

    public String getuMoneyAll() {
        return uMoneyAll;
    }

    public void setuMoneyAll(String uMoneyAll) {
        this.uMoneyAll = uMoneyAll;
    }
}
