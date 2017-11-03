package com.jierong.share.mvp.model.impl;

import com.jierong.share.Constants;
import com.jierong.share.mvp.model.IMakePwModel;
import com.jierong.share.okhttp.BaseHttpUtil;
import com.jierong.share.okhttp.HttpStringCallBack;
import com.lzy.okgo.model.HttpParams;

/**
 * 设置密码界面数据模型实现
 */
public class MakePwModel implements IMakePwModel {
    private BaseHttpUtil mBaseHttpUtil;

    public MakePwModel() {
        mBaseHttpUtil = new BaseHttpUtil();
    }

    /**
     * 设置密码界面
     * @param uid           用户id
     * @param tel           用户手机号码
     * @param pw            用户密码
     * @param mCallBack     结果回调
     */
    @Override
    public void makePw(String uid, String tel, String pw, final HttpStringCallBack mCallBack) {
        String url = Constants.Http_Api_MakePw;
        HttpParams httpParams = new HttpParams();
        httpParams.put("uid", uid);
        httpParams.put("tel", tel);
        httpParams.put("pw", pw);
        mBaseHttpUtil.doPost(url, httpParams, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                mCallBack.onSuccess(null);
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