package com.jierong.share.mvp.model.info;

import com.jierong.share.util.JSONUtil;

import java.util.List;

public class MasterInfo {


    /**
     * id : 119
     * avatar : ./Public/Avatar/2017-05-18/591cf67749fa4.jpg
     * name : 乌托邦
     * share_number : 3
     * allpay : 1.09
     * uIc : http://toshare.91jierong.com./Public/Avatar/2017-05-18/591cf67749fa4.jpg
     * uLevel : 高级认证
     * uScore : 8.8
     * uClicks : 2
     * sharefee : 0.87
     * uIncome : 100
     * uVolume : 100单
     * uTotalIncome : 101.09
     * uLink : http://toshare.91jierong.com/index.php/home/talent/talent?id=119&allincome=101.09
     */

    private String id;
    private String avatar;
    private String name;
    private String share_number;
    private String allpay;
    private String uIc;
    private String uLevel;
    private double uScore;
    private int uClicks;
    private double sharefee;
    private int uIncome;
    private String uVolume;
    private double uTotalIncome;
    private String uLink;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShare_number() {
        return share_number;
    }

    public void setShare_number(String share_number) {
        this.share_number = share_number;
    }

    public String getAllpay() {
        return allpay;
    }

    public void setAllpay(String allpay) {
        this.allpay = allpay;
    }

    public String getUIc() {
        return uIc;
    }

    public void setUIc(String uIc) {
        this.uIc = uIc;
    }

    public String getULevel() {
        return uLevel;
    }

    public void setULevel(String uLevel) {
        this.uLevel = uLevel;
    }

    public double getUScore() {
        return uScore;
    }

    public void setUScore(double uScore) {
        this.uScore = uScore;
    }

    public int getUClicks() {
        return uClicks;
    }

    public void setUClicks(int uClicks) {
        this.uClicks = uClicks;
    }

    public double getSharefee() {
        return sharefee;
    }

    public void setSharefee(double sharefee) {
        this.sharefee = sharefee;
    }

    public int getUIncome() {
        return uIncome;
    }

    public void setUIncome(int uIncome) {
        this.uIncome = uIncome;
    }

    public String getUVolume() {
        return uVolume;
    }

    public void setUVolume(String uVolume) {
        this.uVolume = uVolume;
    }

    public double getUTotalIncome() {
        return uTotalIncome;
    }

    public void setUTotalIncome(double uTotalIncome) {
        this.uTotalIncome = uTotalIncome;
    }

    public String getULink() {
        return uLink;
    }

    public void setULink(String uLink) {
        this.uLink = uLink;
    }


    // gson转换
    public static MasterInfo fromJSON(String JSONString) {
        if (JSONString == null)
            return null;
        return JSONUtil.fromJson(JSONString, MasterInfo.class);
    }

    public static List<MasterInfo> fromJSONS(String JSONSString) {
        if (JSONSString == null) return null;
        return JSONUtil.fromJsons(JSONSString, MasterInfo.class);
    }
}