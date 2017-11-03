package com.jierong.share.mvp.model.impl;

import com.jierong.share.Constants;
import com.jierong.share.mvp.model.IWelcomeModel;
import com.jierong.share.mvp.model.info.AdvInfo;
import com.jierong.share.mvp.model.info.LoginUserInfo;
import com.jierong.share.okhttp.BaseHttpUtil;
import com.jierong.share.okhttp.HttpStringCallBack;
import com.lzy.okgo.model.HttpParams;

/**
 * 欢迎界面数据接口实现
 */
public class WelcomeModel implements IWelcomeModel {
    private BaseHttpUtil mBaseHttpUtil;

    public WelcomeModel() {
        mBaseHttpUtil = new BaseHttpUtil();
    }

    /**
     * 获取欢迎界面广告数据
     * @param mCallBack 广告结果
     * 定义2秒请求时间
     */
    @Override
    public void getTopAdv(final HttpStringCallBack mCallBack) {
        mBaseHttpUtil.doGet(2000, Constants.Http_Api_Wel_Adv, new HttpStringCallBack() {
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
     * 同步用户信息[自动登录]
     * @param imei      imei
     * @param uid       用户id
     * @param token     用户凭证
     * @param pid       第三方推送id
     * @param way       登录方式[手机号、QQ、微信、微博]
     * @param mCallBack 数据回调
     */
    @Override
    public void enterByToken(String imei, String uid, String token, String pid, String way,
                             final HttpStringCallBack mCallBack) {
        String url = Constants.Http_Api_InitUser;
        HttpParams httpParams = new HttpParams();
        httpParams.put("deviceImei", imei);
        httpParams.put("uid", uid);
        httpParams.put("token", token);
        httpParams.put("pid", pid);
        httpParams.put("way", way);
        mBaseHttpUtil.doPost(url, httpParams, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                LoginUserInfo info = LoginUserInfo.fromJSON(String.valueOf(result));
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
