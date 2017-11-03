package com.jierong.share.mvp.model.impl;

import com.jierong.share.Constants;
import com.jierong.share.mvp.model.IAdvDescModel;
import com.jierong.share.mvp.model.info.AdvInfo;
import com.jierong.share.mvp.model.info.MoneyInfo;
import com.jierong.share.okhttp.BaseHttpUtil;
import com.jierong.share.okhttp.HttpStringCallBack;
import com.lzy.okgo.model.HttpParams;

/**
 * 广告列表数据模型实现
 */
public class AdvDescModel implements IAdvDescModel {
    private BaseHttpUtil mBaseHttpUtil;

    public AdvDescModel() {
        mBaseHttpUtil = new BaseHttpUtil();
    }

    /**
     * 根据用户点选某条广告，查看该条广告详情
     * @param uid       用户id
     * @param token     用户token
     * @param aid       广告id
     * @param mCallBack 结果回调
     */
    @Override
    public void getAdvDesc(String uid, String token, String aid, final HttpStringCallBack mCallBack) {
        String url = Constants.Http_Api_GetAdvDesc;
        HttpParams httpParams = new HttpParams();
        httpParams.put("uid", uid);
        httpParams.put("token", token);
        httpParams.put("aid", aid);
        mBaseHttpUtil.doPost(url, httpParams, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                // 这里返回的是剥离出来的data
                AdvInfo info = AdvInfo.fromJSON(String.valueOf(result));
                mCallBack.onSuccess(info);
            }

            @Override
            public void onFailure(int code, String msg) {
                mCallBack.onFailure(code, msg);
            }
        });
    }

    /**
     * 获取用户分享收益
     * @param uid       用户id
     * @param token     用户token
     * @param aid       广告id
     * @param shareType 分享的渠道
     * @param mCallBack 结果回调
     */
    @Override
    public void getShareMoney(String uid, String token, String aid, String shareType, final HttpStringCallBack mCallBack) {
        String url = Constants.Http_Api_GetShareMoney;
        HttpParams httpParams = new HttpParams();
        httpParams.put("uid", uid);
        httpParams.put("token", token);
        httpParams.put("aid", aid);
        httpParams.put("shareType", shareType);
        mBaseHttpUtil.doPost(url, httpParams, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                // 这里返回的是剥离出来的data
                MoneyInfo info = MoneyInfo.fromJSON(String.valueOf(result));
                mCallBack.onSuccess(info);
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