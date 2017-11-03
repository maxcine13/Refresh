package com.jierong.share.mvp.model;

import com.jierong.share.okhttp.HttpFileCallBack;
import com.jierong.share.okhttp.HttpStringCallBack;

/**
 * 首页界面数据接口
 */
public interface IHomeModel extends ModelListener {

    /**
     * 告知服务端当天状态
     * @param uid       用户id
     * @param token     用户token
     * @param mCallBack 结果回调
     */
    public void telServer(String uid, String token, HttpStringCallBack mCallBack);

    /**
     * 上传用户所在城市数据
     * @param uid       用户id
     * @param token     用户token
     * @param city      用户城市定位
     * @param mCallBack 结果回调
     */
    public void uploadCity(String uid, String token, String city, HttpStringCallBack mCallBack);

    /**
     * 获取首页 顶端的轮播广告数据 和 广告分类列表数据
     * @param uid       用户id
     * @param token     用户token
     * @param mCallBack 结果回调
     */
    public void getHomeData(String uid, String token, HttpStringCallBack mCallBack);

    /**
     * 获取 爆款 列表数据
     * @param uid       用户id
     * @param token     用户token
     * @param mCallBack 结果回调
     */
    public void getBkData(String uid, String token, HttpStringCallBack mCallBack);

    /**
     * 查看公告信息
     * @param uid       用户id
     * @param token     用户token
     * @param mCallBack 结果回调
     */
    public void getGg(String uid, String token, HttpStringCallBack mCallBack);

    /**
     * 显示新人大礼包
     * @param uid       用户id
     * @param token     用户token
     * @param mCallBack 结果回调
     */
    public void getShowGift(String uid, String token, HttpStringCallBack mCallBack);

    /**
     * 签到收益接口
     * @param uid       用户id
     * @param token     用户token
     * @param mCallBack 结果回调
     */
    public void getSign(String uid, String token, HttpStringCallBack mCallBack);

    /**
     * 下载最新的apk
     * @param uid       用户ID
     * @param token     用户token
     * @param path      文件储存路径
     * @param name      文件名
     * @param url       下载路径
     * @param mCallBack 结果回调
     */
    public void downNewApk(String uid, String token, String path, String name, String url, HttpFileCallBack mCallBack);

    /**
     * 检查更新APP版本
     * @param uid       用户id
     * @param token     用户token
     * @param version   当前APP的Version
     * @param mCallBack 结果回调
     */
    public void checkUpdate(String uid, String token, int version, HttpStringCallBack mCallBack);

}
