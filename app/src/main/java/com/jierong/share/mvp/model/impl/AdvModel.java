package com.jierong.share.mvp.model.impl;

import com.jierong.share.Constants;
import com.jierong.share.mvp.model.IAdvModel;
import com.jierong.share.mvp.model.info.AdvTypeInfo;
import com.jierong.share.okhttp.BaseHttpUtil;
import com.jierong.share.okhttp.HttpStringCallBack;
import com.lzy.okgo.model.HttpParams;

import java.util.List;

/**
 * 分享赚钱 广告分类列表接口实现
 */
public class AdvModel implements IAdvModel {
    private BaseHttpUtil mBaseHttpUtil;

    public AdvModel() {
        mBaseHttpUtil = new BaseHttpUtil();
    }

    /**
     * 获取分享赚钱 广告分类列表数据
     * @param uid       用户id
     * @param token     用户token
     * @param mCallBack 结果回调
     */
    @Override
    public void getAdvListData(String uid, String token, final HttpStringCallBack mCallBack) {
        String url = Constants.Http_Api_GetAdvData;
        HttpParams httpParams = new HttpParams();
        httpParams.put("uid", uid);
        httpParams.put("token", token);
        mBaseHttpUtil.doPost(url, httpParams, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                List<AdvTypeInfo> info = AdvTypeInfo.fromJSONS(String.valueOf(result));
                mCallBack.onSuccess(info);
            }

            @Override
            public void onFailure(int code, String msg) {
                mCallBack.onFailure(code, msg);
            }
        });
    }

    /**
     * 关闭网络请求
     */
    @Override
    public void closeHttp() {
        mBaseHttpUtil.closeHttp();
    }

}
