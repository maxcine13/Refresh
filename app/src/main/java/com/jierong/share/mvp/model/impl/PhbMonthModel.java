package com.jierong.share.mvp.model.impl;

import com.jierong.share.Constants;
import com.jierong.share.mvp.model.IPhbMonthModel;
import com.jierong.share.okhttp.BaseHttpUtil;
import com.jierong.share.okhttp.HttpStringCallBack;
import com.lzy.okgo.model.HttpParams;

public class PhbMonthModel implements IPhbMonthModel {
    private BaseHttpUtil mBaseHttpUtil;

    public PhbMonthModel() {
        mBaseHttpUtil = new BaseHttpUtil();
    }

    /**
     * 查询月排行班数据
     * @param uid           用户id
     * @param token         用户token
     * @param mCallBack     结果回调
     */
    @Override
    public void loadData(String uid, String token, final HttpStringCallBack mCallBack) {
        String url = Constants.Http_Api_Yphb;
        HttpParams httpParams = new HttpParams();
        httpParams.put("uid", uid);
        httpParams.put("token", token);
        mBaseHttpUtil.doPost(url, httpParams, new HttpStringCallBack() {
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
        mBaseHttpUtil.closeHttp();
    }
}
