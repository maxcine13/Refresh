package com.jierong.share.mvp.model;

import com.jierong.share.okhttp.HttpStringCallBack;

/**
 * 注册界面数据接口
 */
public interface IRegisterModel extends ModelListener {

    /**
     * 根据手机号码获取验证码
     * @param tel       手机号码
     * @param mCallBack 结果回调
     */
    public void getKey(String tel, HttpStringCallBack mCallBack);

    /**
     * 执行注册操作
     * @param tel       手机号码
     * @param key       验证码
     * @param pass      密码
     * @param mCallBack 结果回调
     */
    public void register(String tel, String key, String pass, HttpStringCallBack mCallBack);

    /**
     * 同步用户信息[自动登录]
     * @param uid       用户id
     * @param token     用户凭证
     * @param pid       第三方推送id
     * @param way       登录方式[手机号、QQ、微信、微博]
     * @param mCallBack 数据回调
     */
    public void enterByToken(String uid, String token, String pid, String way, HttpStringCallBack mCallBack);

}
