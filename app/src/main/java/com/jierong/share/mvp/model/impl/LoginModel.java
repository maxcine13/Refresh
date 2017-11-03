package com.jierong.share.mvp.model.impl;

import com.jierong.share.Constants;
import com.jierong.share.mvp.model.ILoginModel;
import com.jierong.share.mvp.model.info.LoginUserInfo;
import com.jierong.share.okhttp.BaseHttpUtil;
import com.jierong.share.okhttp.HttpStringCallBack;
import com.lzy.okgo.model.HttpParams;

/**
 * 登录数据模型实现
 */
public class LoginModel implements ILoginModel {
    private BaseHttpUtil mBaseHttpUtil;

    public LoginModel() {
        mBaseHttpUtil = new BaseHttpUtil();
    }

    /**
     * 使用用户名、密码登录
     * @param imei      imei
     * @param pid   推送分配的设备id
     * @param name      用户名
     * @param pass      密码
     * @param mCallBack 数据回调
     */
    @Override
    public void loginByPass(String imei, String pid, String name, String pass, final HttpStringCallBack mCallBack) {
        String url = Constants.Http_Api_UserLogin;
        HttpParams httpParams = new HttpParams();
        httpParams.put("deviceImei", imei);
        httpParams.put("pid", pid);
        httpParams.put("openid", name);
        httpParams.put("accesstoken", pass);
        mBaseHttpUtil.doPost(url, httpParams, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                LoginUserInfo user = LoginUserInfo.fromJSON(String.valueOf(result));

                mCallBack.onSuccess(user);
            }

            @Override
            public void onFailure(int code, String msg) {
                mCallBack.onFailure(code, msg);
            }
        });
    }

    /**
     * 使用QQ登录
     * @param imei      imei
     * @param pid   推送分配的设备id
     * @param id    QQ中的OpenId
     * @param token QQ中的AccessToken
     * @param mCallBack 结果回调
     */
    @Override
    public void loginByQQ(String imei, String pid, String id, String token, final HttpStringCallBack mCallBack) {
        String url = Constants.Http_Api_QQLogin;
        HttpParams httpParams = new HttpParams();
        httpParams.put("deviceImei", imei);
        httpParams.put("pid", pid);
        httpParams.put("openid", id);
        httpParams.put("accesstoken", token);
        mBaseHttpUtil.doPost(url, httpParams, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                LoginUserInfo user = LoginUserInfo.fromJSON(String.valueOf(result));
                mCallBack.onSuccess(user);
            }

            @Override
            public void onFailure(int code, String msg) {
                mCallBack.onFailure(code, msg);
            }
        });
    }

    /**
     * 使用新浪微博登录
     * @param imei      imei
     * @param pid       推送分配的设备id
     * @param id        微博返回的uid
     * @param token     微博返回的token
     * @param mCallBack 结果回调
     */
    @Override
    public void loginByWB(String imei, String pid, String id, String token, final HttpStringCallBack mCallBack) {
        String url = Constants.Http_Api_WBLogin;
        HttpParams httpParams = new HttpParams();
        httpParams.put("deviceImei", imei);
        httpParams.put("pid", pid);
        httpParams.put("openid", id);
        httpParams.put("accesstoken", token);
        mBaseHttpUtil.doPost(url, httpParams, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                LoginUserInfo user = LoginUserInfo.fromJSON(String.valueOf(result));
                mCallBack.onSuccess(user);
            }

            @Override
            public void onFailure(int code, String msg) {
                mCallBack.onFailure(code, msg);
            }
        });
    }

    /**
     * 使用微信登录
     * @param imei      imei
     * @param pid       推送分配的设备id
     * @param id        微信中的OpenId
     * @param token     微信中的AccessToken
     * @param mCallBack 结果回调
     */
    @Override
    public void loginByWX(String imei, String pid, String id, String token, final HttpStringCallBack mCallBack) {
        String url = Constants.Http_Api_WXLogin;
        HttpParams httpParams = new HttpParams();
        httpParams.put("deviceImei", imei);
        httpParams.put("pid", pid);
        httpParams.put("openid", id);
        httpParams.put("accesstoken", token);
        mBaseHttpUtil.doPost(url, httpParams, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                LoginUserInfo user = LoginUserInfo.fromJSON(String.valueOf(result));
                mCallBack.onSuccess(user);
            }

            @Override
            public void onFailure(int code, String msg) {
                mCallBack.onFailure(code, msg);
            }
        });
    }

    @Override
    public void closeHttp() {
        mBaseHttpUtil.closeHttp();
    }

}
