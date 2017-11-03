package com.jierong.share.mvp.model.impl;

import com.jierong.share.Constants;
import com.jierong.share.mvp.model.ICollectionModel;
import com.jierong.share.okhttp.BaseHttpUtil;
import com.jierong.share.okhttp.HttpStringCallBack;
import com.lzy.okgo.model.HttpParams;

/**
 * 广告发送界面的接口实现
 */
public class CollectionModel implements ICollectionModel {
    private BaseHttpUtil mBaseHttpUtil;

    public CollectionModel() {
        mBaseHttpUtil = new BaseHttpUtil();
    }

    /**
     * 获取用户收藏的接口
     * @param uid       用户id
     * @param token     用户token
     * @param mCallBack 结果回调
     */
    @Override
    public void getCollectionData(String uid, String token, final HttpStringCallBack mCallBack) {
        String url = Constants.Http_Api_MyCollect;
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

    /**
     * 取消收藏
     * @param uid       用户id
     * @param token     用户token
     * @param tid       收藏item id
     * @param mCallBack 结果回调
     */
    @Override
    public void doCancel(String uid, String token, String tid, final HttpStringCallBack mCallBack) {
        String url = Constants.Http_Api_Cancel;
        HttpParams httpParams = new HttpParams();
        httpParams.put("uid", uid);
        httpParams.put("token", token);
        httpParams.put("talent_id", tid);
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
