package com.jierong.share.mvp.model.info;

import com.jierong.share.util.JSONUtil;
import java.io.Serializable;
import java.util.List;

/**
 * 广告模型
 */
public class AdvInfo implements Serializable {
    private String id;      // 广告唯一标示
    private String img;    // 广告图片路径
    private String link;    // 广告点击链接路径

    private String contentUrl;  // 广告内容网页地址
    private String manIc;       // 广告主头像
    private String manName;     // 广告主别名
    private String oneMoney;    // 单次分享的收益
    private String allMoney;    // 总收益
    private String haveNum;     // 剩余条数
    private String title;       // 广告标题
    private String desc;        // 广告文字描述

    private String shareDesc;   // 分享时候的描述
    private String shareImg;    // 分享时候的图片

//    private boolean isQQShare;          // 是否已经进行QQ聊天分享
//    private boolean isQQZoneShare;      // 是否已经进行QQ动态分享
//    private boolean isWXShare;          // 是否已经进行微信聊天分享
//    private boolean isWXCircleShare;    // 是否已经进行微信朋友圈分享
//    private boolean isWBShare;          // 是否已经进行微博分享

    private boolean isLove;     // 是否点赞
    private String pic_path;    // 广告图片路径
    private int type;        // 广告分类

    // 用于限制分享的滥用
    private boolean qq;
    private boolean qqZone;
    private boolean wx;
    private boolean wxCircle;
    private boolean wb;
    private boolean Dingding;
    private boolean AlipayMoments;
    private boolean Allpaylifecircle;


    // get,set 注入
    public String getPic_path() {
        return pic_path;
    }
    public void setPic_path(String pic_path) {
        this.pic_path = pic_path;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getImg() {
        return img;
    }
    public void setImg(String img) {
        this.img = img;
    }
    public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }
    public String getManIc() {
        return manIc;
    }
    public void setManIc(String manIc) {
        this.manIc = manIc;
    }
    public String getManName() {
        return manName;
    }
    public void setManName(String manName) {
        this.manName = manName;
    }
    public String getOneMoney() {
        return oneMoney;
    }
    public void setOneMoney(String oneMoney) {
        this.oneMoney = oneMoney;
    }
    public String getAllMoney() {
        return allMoney;
    }
    public void setAllMoney(String allMoney) {
        this.allMoney = allMoney;
    }
    public String getHaveNum() {
        return haveNum;
    }
    public void setHaveNum(String haveNum) {
        this.haveNum = haveNum;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getContentUrl() {
        return contentUrl;
    }
    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }
//    public boolean isQQShare() {
//        return isQQShare;
//    }
//    public void setQQShare(boolean QQShare) {
//        isQQShare = QQShare;
//    }
//    public boolean isQQZoneShare() {
//        return isQQZoneShare;
//    }
//    public void setQQZoneShare(boolean QQZoneShare) {
//        isQQZoneShare = QQZoneShare;
//    }
//    public boolean isWXShare() {
//        return isWXShare;
//    }
//    public void setWXShare(boolean WXShare) {
//        isWXShare = WXShare;
//    }
//    public boolean isWXCircleShare() {
//        return isWXCircleShare;
//    }
//    public void setWXCircleShare(boolean WXCircleShare) {
//        isWXCircleShare = WXCircleShare;
//    }
//    public boolean isWBShare() {
//        return isWBShare;
//    }
//    public void setWBShare(boolean WBShare) {
//        isWBShare = WBShare;
//    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getShareDesc() {
        return shareDesc;
    }
    public void setShareDesc(String shareDesc) {
        this.shareDesc = shareDesc;
    }
    public String getShareImg() {
        return shareImg;
    }
    public void setShareImg(String shareImg) {
        this.shareImg = shareImg;
    }
    public boolean isLove() {
        return isLove;
    }
    public void setLove(boolean love) {
        isLove = love;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public boolean isQq() {
        return qq;
    }
    public void setQq(boolean qq) {
        this.qq = qq;
    }
    public boolean isQqZone() {
        return qqZone;
    }
    public void setQqZone(boolean qqZone) {
        this.qqZone = qqZone;
    }
    public boolean isWx() {
        return wx;
    }
    public void setWx(boolean wx) {
        this.wx = wx;
    }
    public boolean isWxCircle() {
        return wxCircle;
    }
    public void setWxCircle(boolean wxCircle) {
        this.wxCircle = wxCircle;
    }
    public boolean isWb() {
        return wb;
    }
    public void setWb(boolean wb) {
        this.wb = wb;
    }
    public boolean isDingding() {
        return Dingding;
    }
    public void setDingding(boolean dingding) {
        Dingding = dingding;
    }
    public boolean isAlipayMoments() {
        return AlipayMoments;
    }
    public void setAlipayMoments(boolean alipayMoments) {
        AlipayMoments = alipayMoments;
    }
    public boolean isAllpaylifecircle() {
        return Allpaylifecircle;
    }
    public void setAllpaylifecircle(boolean allpaylifecircle) {
        Allpaylifecircle = allpaylifecircle;
    }

    // gson转换
    public static AdvInfo fromJSON(String JSONString) {
        if (JSONString == null)
            return null;
        return JSONUtil.fromJson(JSONString, AdvInfo.class);
    }
    public static List<AdvInfo> fromJSONS(String JSONSString) {
        if (JSONSString == null) return null;
        return JSONUtil.fromJsons(JSONSString, AdvInfo.class);
    }
    public String toJSON() {
        return JSONUtil.toJson(this);
    }
    public String toString() {
        return toJSON();
    }

}
