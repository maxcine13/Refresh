package com.jierong.share.mvp.model.impl;

import com.jierong.share.Constants;
import com.jierong.share.mvp.model.IShopListModel;
import com.jierong.share.okhttp.BaseHttpUtil;
import com.jierong.share.okhttp.HttpStringCallBack;
import com.lzy.okgo.model.HttpParams;

public class ShopListModel implements IShopListModel {
    private BaseHttpUtil mBaseHttpUtil;

    public ShopListModel() {
        mBaseHttpUtil = new BaseHttpUtil();
    }

    /**
     * 查看用户商品购物列表
     * @param page      当前请求页码
     * @param uid       用户id
     * @param token     用户token
     * @param mCallBack 结果回调
     */
    @Override
    public void getBuyList(int page, String uid, String token, final HttpStringCallBack mCallBack) {
        String url = Constants.Http_Api_BuyList;
        HttpParams httpParams = new HttpParams();
        httpParams.put("uid", uid);
        httpParams.put("token", token);
        httpParams.put("page", page);
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
     * 查看用户商品购物返利列表
     * @param page      当前请求页码
     * @param uid       用户id
     * @param token     用户token
     * @param mCallBack 结果回调
     */
    @Override
    public void getFanList(int page, String uid, String token, final HttpStringCallBack mCallBack) {
        String url = Constants.Http_Api_FanList;
        HttpParams httpParams = new HttpParams();
        httpParams.put("uid", uid);
        httpParams.put("token", token);
        httpParams.put("page", page);
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
     * 查看白赚-签到列表
     * @param page      当前请求页码
     * @param uid       用户id
     * @param token     用户token
     * @param mCallBack 结果回调
     */
    @Override
    public void getBzQdList(int page, String uid, String token, final HttpStringCallBack mCallBack) {
        String url = Constants.Http_Api_BzList;
        HttpParams httpParams = new HttpParams();
        httpParams.put("uid", uid);
        httpParams.put("token", token);
        httpParams.put("page", page);
        httpParams.put("flag", 1);
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
     * 查看推荐-签到列表
     * @param page      当前请求页码
     * @param uid       用户id
     * @param token     用户token
     * @param mCallBack 结果回调
     */
    @Override
    public void getBzTjList(int page, String uid, String token, final HttpStringCallBack mCallBack) {
        String url = Constants.Http_Api_BzList;
        HttpParams httpParams = new HttpParams();
        httpParams.put("uid", uid);
        httpParams.put("token", token);
        httpParams.put("page", page);
        httpParams.put("flag", 2);
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
     * 查看白赚-分享列表
     * @param page      当前请求页码
     * @param uid       用户id
     * @param token     用户token
     * @param mCallBack 结果回调
     */
    @Override
    public void getBzFxList(int page, String uid, String token, final HttpStringCallBack mCallBack) {
        String url = Constants.Http_Api_BzList;
        HttpParams httpParams = new HttpParams();
        httpParams.put("uid", uid);
        httpParams.put("token", token);
        httpParams.put("page", page);
        httpParams.put("flag", 3);
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
     * 查看白赚-成交列表
     * @param page      当前请求页码
     * @param uid       用户id
     * @param token     用户token
     * @param mCallBack 结果回调
     */
    @Override
    public void getBzCjList(int page, String uid, String token, final HttpStringCallBack mCallBack) {
        String url = Constants.Http_Api_BzList;
        HttpParams httpParams = new HttpParams();
        httpParams.put("uid", uid);
        httpParams.put("token", token);
        httpParams.put("page", page);
        httpParams.put("flag", 4);
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
