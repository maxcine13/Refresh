package com.jierong.share.mvp.model.impl;

import com.jierong.share.Constants;
import com.jierong.share.mvp.model.IChangePwModel;
import com.jierong.share.okhttp.BaseHttpUtil;
import com.jierong.share.okhttp.HttpStringCallBack;
import com.jierong.share.util.LogUtil;
import com.lzy.okgo.model.HttpParams;

/**
 * 绑定手机界面数据模型实现
 */
public class ChangePwModel implements IChangePwModel {
    private BaseHttpUtil mBaseHttpUtil;

    public ChangePwModel() {
        mBaseHttpUtil = new BaseHttpUtil();
    }

    /**
     * 根据uid修改新的手机登录密码
     * @param uid       用户id
     * @param token     用户token
     * @param old       用户输入的旧密码
     * @param pass      用户设置的新密码
     * @param mCallBack 结果回调
     */
    @Override
    public void change(String uid, String token, String old, String pass, final HttpStringCallBack mCallBack) {
        String url = Constants.Http_Api_ChangePw;
        HttpParams httpParams=new HttpParams();
        httpParams.put("uid",uid);
        httpParams.put("token",token);
        httpParams.put("oldpw",old);
        httpParams.put("newpw",pass);
        mBaseHttpUtil.doPost(url,httpParams, new HttpStringCallBack() {
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

    @Override
    public void closeHttp() {
        mBaseHttpUtil.closeHttp();
    }

}