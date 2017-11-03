package com.jierong.share.mvp.model.impl;

import com.jierong.share.Constants;
import com.jierong.share.mvp.model.IPhbWeekModel;
import com.jierong.share.okhttp.BaseHttpUtil;
import com.jierong.share.okhttp.HttpStringCallBack;
import com.lzy.okgo.model.HttpParams;

public class PhbWeekModel implements IPhbWeekModel {
    private BaseHttpUtil mBaseHttpUtil;

    public PhbWeekModel() {
        mBaseHttpUtil = new BaseHttpUtil();
    }

    /**
     * 查询周排行班数据
     * @param uid           用户id
     * @param token         用户token
     * @param mCallBack     结果回调
     */
    @Override
    public void loadData(String uid, String token, final HttpStringCallBack mCallBack) {
        String url = Constants.Http_Api_Zphb;
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
