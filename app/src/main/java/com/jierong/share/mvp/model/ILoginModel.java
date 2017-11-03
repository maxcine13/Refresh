package com.jierong.share.mvp.model;

import com.jierong.share.okhttp.HttpStringCallBack;

/**
 * 登录界面数据接口
 */
public interface ILoginModel extends ModelListener {

    /**
     * 根据用户名密码登录
     * @param imei      imei
     * @param pid   推送分配的设备id
     * @param name      用户名
     * @param pass      密码
     * @param mCallBack 数据回调
     */
    public void loginByPass(String imei, String pid, String name, String pass, HttpStringCallBack mCallBack);

    /**
     * 根据QQ账号登录
     * @param imei      imei
     * @param pid   推送分配的设备id
     * @param id    QQ中的OpenId
     * @param token QQ中的AccessToken
     * @param mCallBack 结果回调
     */
    public void loginByQQ(String imei, String pid, String id, String token, HttpStringCallBack mCallBack);

    /**
     * 根据新浪微博账号登录
     * @param imei      imei
     * @param pid       推送分配的设备id
     * @param id        微博返回的uid
     * @param token     微博返回的token
     * @param mCallBack 结果回调
     */
    public void loginByWB(String imei, String pid, String id, String token, HttpStringCallBack mCallBack);

    /**
     * 根据微信账号登录
     * @param imei      imei
     * @param pid       推送分配的设备id
     * @param id        微信中的OpenId
     * @param token     微信中的AccessToken
     * @param mCallBack 结果回调
     */
    public void loginByWX(String imei, String pid, String id, String token, HttpStringCallBack mCallBack);

}
