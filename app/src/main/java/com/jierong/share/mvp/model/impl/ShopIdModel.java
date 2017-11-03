package com.jierong.share.mvp.model.impl;

import com.jierong.share.Constants;
import com.jierong.share.mvp.model.IShopIdModel;
import com.jierong.share.okhttp.BaseHttpUtil;
import com.jierong.share.okhttp.HttpStringCallBack;
import com.lzy.okgo.model.HttpParams;

/**
 * Created by wht on 2017/6/19.
 */

public class ShopIdModel implements IShopIdModel {
    BaseHttpUtil mBaseHttpUtil;

    public ShopIdModel() {
        mBaseHttpUtil = new BaseHttpUtil();
    }


    /**
     * 申请返利列表接
     *
     * @param uid         用户id
     * @param token       用户唯一标识
     * @param ordernumber 申请订单号
     * @param mCallBack
     */
    @Override
    public void searchBudgetrebate(String uid, String token, String ordernumber, final HttpStringCallBack mCallBack) {
        String url = Constants.Http_Api_TaoUpdatem;
        HttpParams httpParams = new HttpParams();
        httpParams.put("uid", uid);
        httpParams.put("token", token);
        httpParams.put("ordernumber", ordernumber);
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
     * 申请返利接口
     *
     * @param uid         用户id
     * @param token       用户唯一标识
     * @param ordernumber 申请订单号
     * @param mCallBack
     */
    @Override
    public void searchRealRebate(String uid, String token, String ordernumber, final HttpStringCallBack mCallBack) {
        String url = Constants.Http_Api_TaoUpdatem;
        HttpParams httpParams = new HttpParams();
        httpParams.put("uid", uid);
        httpParams.put("token", token);
        httpParams.put("ordernumber", ordernumber);
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
