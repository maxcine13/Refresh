package com.jierong.share.mvp.model.impl;

import com.jierong.share.Constants;
import com.jierong.share.mvp.model.ISendModel;
import com.jierong.share.okhttp.BaseHttpUtil;
import com.jierong.share.okhttp.HttpStringCallBack;
import com.lzy.okgo.model.HttpParams;

/**
 * 广告发送界面的接口实现
 */
public class SendModel implements ISendModel {
    private BaseHttpUtil mBaseHttpUtil;

    public SendModel() {
        mBaseHttpUtil = new BaseHttpUtil();
    }

    @Override
    public void getAdvType(String uid, String token, final HttpStringCallBack mCallBack) {
        String url = Constants.Http_Api_GetSendType;
        HttpParams httpParams = new HttpParams();
        httpParams.put("uid", uid);
        httpParams.put("token", token);
        mBaseHttpUtil.doPost(url, httpParams, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                // 这里返回的是剥离出来的data
                //List<AdvTypeInfo> advTypeInfos = AdvTypeInfo.fromJSONS(String.valueOf(result));
                mCallBack.onSuccess(result);
            }

            @Override
            public void onFailure(int code, String msg) {
                mCallBack.onFailure(code, msg);
            }
        });
    }

    @Override
    public void sendAdv(String uid, String token, String name, String tel, String type, String desc,
                        final HttpStringCallBack mCallBack) {
        String url = Constants.Http_Api_SendAdv;
        HttpParams httpParams = new HttpParams();
        httpParams.put("uid", uid);
        httpParams.put("token", token);
        httpParams.put("name", name);
        httpParams.put("tel", tel);
        httpParams.put("category_id", type);
        httpParams.put("content", desc);
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
