package com.jierong.share.mvp.model.impl;

import com.jierong.share.Constants;
import com.jierong.share.mvp.model.IAdvListModel;
import com.jierong.share.mvp.model.info.AdvInfo;
import com.jierong.share.okhttp.BaseHttpUtil;
import com.jierong.share.okhttp.HttpStringCallBack;
import com.lzy.okgo.model.HttpParams;

import java.util.List;

/**
 * 广告列表数据模型实现
 */
public class AdvListModel implements IAdvListModel {
    private BaseHttpUtil mBaseHttpUtil;

    public AdvListModel() {
        mBaseHttpUtil = new BaseHttpUtil();
    }

    /**
     * 根据用户点选的广告分类，查看广告列表
     * @param uid       用户id
     * @param token     用户token
     * @param tid       广告分类id
     * @param mCallBack 结果回调
     */
    @Override
    public void getAdvList(String uid, String token, String tid, final HttpStringCallBack mCallBack) {
        String url = Constants.Http_Api_GetAdvList;
        HttpParams httpParams = new HttpParams();
        httpParams.put("uid", uid);
        httpParams.put("token", token);
        httpParams.put("tid", tid);
        mBaseHttpUtil.doPost(url, httpParams, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                // 这里返回的是剥离出来的data
                List<AdvInfo> info = AdvInfo.fromJSONS(String.valueOf(result));
                mCallBack.onSuccess(info);
            }

            @Override
            public void onFailure(int code, String msg) {
                mCallBack.onFailure(code, msg);
            }
        });
    }

    /**
     * 点赞
     * @param uid       用户id
     * @param token     用户token
     * @param aid       广告id
     * @param mCallBack 结果回调
     */
    @Override
    public void doZan(String uid, String token, String aid, final HttpStringCallBack mCallBack) {
        String url = Constants.Http_Api_DoZan;
        HttpParams httpParams = new HttpParams();
        httpParams.put("uid", uid);
        httpParams.put("token", token);
        httpParams.put("aid", aid);
        mBaseHttpUtil.doPost(url, httpParams, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                // 这里返回的是剥离出来的data
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