package com.jierong.share.mvp.model.impl;

import com.jierong.share.Constants;
import com.jierong.share.mvp.model.IActivationModel;
import com.jierong.share.mvp.model.IAuthenModel;
import com.jierong.share.okhttp.BaseHttpUtil;
import com.jierong.share.okhttp.HttpStringCallBack;
import com.lzy.okgo.model.HttpParams;

/**
 * 白条额度
 */

public class ActivationModel implements IActivationModel {
    BaseHttpUtil baseHttpUtil;

    public ActivationModel() {
        baseHttpUtil = new BaseHttpUtil();
    }


    /**
     * 激活额度
     *
     * @param uid       用户id
     * @param token     用户唯一标识
     * @param name      用户姓名
     * @param idcard    用户身份证号
     * @param bankcard  用户银行卡号
     * @param tel       用户银行卡号所绑定的手机号码
     * @param mCallBack
     */
    @Override
    public void activationMethod(String uid, String token, String name, String idcard, String bankcard, String tel, final HttpStringCallBack mCallBack) {
        String url = Constants.Http_Api_WhiteHandel;
        HttpParams httpParams = new HttpParams();
        httpParams.put("uid", uid);
        httpParams.put("token", token);
        httpParams.put("name", name);
        httpParams.put("idcard", idcard);
        httpParams.put("bankcard", bankcard);
        httpParams.put("tel", tel);
        baseHttpUtil.doPost(url, httpParams, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
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
        baseHttpUtil.closeHttp();
    }
}
