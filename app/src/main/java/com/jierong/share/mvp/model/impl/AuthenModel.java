package com.jierong.share.mvp.model.impl;

import com.jierong.share.Constants;
import com.jierong.share.mvp.model.IAuthenModel;
import com.jierong.share.mvp.model.info.MaInfo;
import com.jierong.share.okhttp.BaseHttpUtil;
import com.jierong.share.okhttp.HttpStringCallBack;
import com.lzy.okgo.model.HttpParams;

/**
 * 达人认证接口模块
 */
public class AuthenModel implements IAuthenModel {
    BaseHttpUtil baseHttpUtil;

    public AuthenModel() {
        baseHttpUtil = new BaseHttpUtil();
    }

    /**
     * 检测是否达人认证
     * @param uid       用户id
     * @param token     用户token
     * @param mCallBack 结果回调
     */
    @Override
    public void check(String uid, String token, final HttpStringCallBack mCallBack) {
        String url = Constants.Http_Api_Master_Check;
        HttpParams httpParams = new HttpParams();
        httpParams.put("uid", uid);
        httpParams.put("token", token);
        baseHttpUtil.doPost(url, httpParams, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                MaInfo info = MaInfo.fromJSON(String.valueOf(result));
                mCallBack.onSuccess(info);
            }

            @Override
            public void onFailure(int code, String msg) {
                mCallBack.onFailure(code, msg);
            }
        });
    }

    /**
     * @param uid       用户id
     * @param token     用户唯一标识
     * @param flag      新增和编辑的标识
     * @param name      用户姓名
     * @param ID        用户身份证号
     * @param mCallBack
     */
    @Override
    public void examine(String uid, String token, String flag, String name, String ID, final HttpStringCallBack mCallBack) {
        String url = Constants.Http_Api_Master;
        HttpParams httpParams = new HttpParams();
        httpParams.put("uid", uid);
        httpParams.put("token", token);
        httpParams.put("flag", flag);
        httpParams.put("name", name);
        httpParams.put("idcard", ID);
        // httpParams.put("bank", bankcard);
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
