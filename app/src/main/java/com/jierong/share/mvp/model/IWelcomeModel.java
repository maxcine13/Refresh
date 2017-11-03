package com.jierong.share.mvp.model;

import com.jierong.share.okhttp.HttpStringCallBack;

/**
 * 欢迎界面数据接口
 */
public interface IWelcomeModel extends ModelListener {

    /**
     * 获取欢迎界面广告数据
     * @param mCallBack 数据回调
     */
    public void getTopAdv(HttpStringCallBack mCallBack);

    /**
     * 同步用户信息[自动登录]
     * @param imei      imei
     * @param uid       用户id
     * @param token     用户凭证
     * @param pid       第三方推送id
     * @param way       登录方式[手机号、QQ、微信、微博]
     * @param mCallBack 数据回调
     */
    public void enterByToken(String imei, String uid, String token, String pid, String way, HttpStringCallBack mCallBack);


}
