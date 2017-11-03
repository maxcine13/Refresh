package com.jierong.share.mvp.model.impl;

import com.jierong.share.Constants;
import com.jierong.share.mvp.model.IBindModel;
import com.jierong.share.okhttp.BaseHttpUtil;
import com.jierong.share.okhttp.HttpStringCallBack;
import com.lzy.okgo.model.HttpParams;

/**
 * 绑定手机界面数据模型实现
 */
public class BindModel implements IBindModel {
    private BaseHttpUtil mBaseHttpUtil;

    public BindModel() {
        mBaseHttpUtil = new BaseHttpUtil();
    }

    /**
     * 获取手机验证码
     * @param tel       手机号码
     * @param mCallBack 结果回调
     */
    @Override
    public void getKey(String tel, final HttpStringCallBack mCallBack) {
        String url = Constants.Http_Api_BindKey;
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
     * 用户绑定手机
     * @param uid       用户id
     * @param tel       手机号码
     * @param key       验证码
     * @param mCallBack 结果回调
     */
    @Override
    public void bind(String uid, String tel, String key, final HttpStringCallBack mCallBack) {
        String url = Constants.Http_Api_BindTel;
        HttpParams httpParams = new HttpParams();
        httpParams.put("uid", uid);
        httpParams.put("tel", tel);
        httpParams.put("key", key);
        mBaseHttpUtil.doPost(url, httpParams, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                // 这里返回的是剥离出来的data
                mCallBack.onSuccess(result.toString());
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