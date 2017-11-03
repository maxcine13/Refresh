package com.jierong.share.mvp.model.impl;

import com.jierong.share.Constants;
import com.jierong.share.mvp.model.IRegisterModel;
import com.jierong.share.mvp.model.info.LoginUserInfo;
import com.jierong.share.okhttp.BaseHttpUtil;
import com.jierong.share.okhttp.HttpStringCallBack;
import com.lzy.okgo.model.HttpParams;

/**
 * 注册界面数据接口实现
 */
public class RegisterModel implements IRegisterModel {
    private BaseHttpUtil mBaseHttpUtil;

    public RegisterModel() {
        mBaseHttpUtil = new BaseHttpUtil();
    }

    /**
     * 获取手机验证码
     * @param tel       手机号码
     * @param mCallBack 结果回调
     */
    @Override
    public void getKey(String tel, final HttpStringCallBack mCallBack) {
        String url = Constants.Http_Api_GetKey;
        HttpParams httpParams = new HttpParams();
        httpParams.put("mobile", tel);
        mBaseHttpUtil.doPost(url, httpParams, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                // 这里返回的是剥离出来的data
                mCallBack.onSuccess(result);
            }

            @Override
            public void onFailure(int code, String msg) {
                mCallBack.onFailure(code, msg);
            }
        });
    }

    /**
     * 注册账号
     * @param tel       手机号码
     * @param key       验证码
     * @param pass      密码
     * @param mCallBack 结果回调
     */
    @Override
    public void register(String tel, String key, String pass, final HttpStringCallBack mCallBack) {
        String url = Constants.Http_Api_Register;
        HttpParams httpParams = new HttpParams();
        httpParams.put("tel", tel);
        httpParams.put("key", key);
        httpParams.put("pw", pass);
        mBaseHttpUtil.doPost(url, httpParams, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                // 这里返回的是剥离出来的data
                mCallBack.onSuccess(result);
            }

            @Override
            public void onFailure(int code, String msg) {
                mCallBack.onFailure(code, msg);
            }
        });
    }

    /**
     * 同步用户信息[自动登录]
     * @param uid       用户id
     * @param token     用户凭证
     * @param pid       第三方推送id
     * @param way       登录方式[手机号、QQ、微信、微博]
     * @param mCallBack 数据回调
     */
    @Override
    public void enterByToken(String uid, String token, String pid, String way,
                             final HttpStringCallBack mCallBack) {
        String url = Constants.Http_Api_InitUser;
        HttpParams httpParams = new HttpParams();
        httpParams.put("uid", uid);
        httpParams.put("token", token);
        httpParams.put("pid", pid);
        httpParams.put("way", way);
        mBaseHttpUtil.doPost(url, httpParams, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                LoginUserInfo info = LoginUserInfo.fromJSON(String.valueOf(result));
                mCallBack.onSuccess(info);
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
