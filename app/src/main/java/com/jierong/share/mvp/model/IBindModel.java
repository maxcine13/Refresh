package com.jierong.share.mvp.model;

import com.jierong.share.okhttp.HttpStringCallBack;

/**
 * 绑定手机界面数据接口
 */
public interface IBindModel extends ModelListener {

    /**
     * 根据手机号码获取验证码
     * @param tel       手机号码
     * @param mCallBack 结果回调
     */
    public void getKey(String tel, HttpStringCallBack mCallBack);

    /**
     * 根据手机号码、验证码，执行校验绑定
     * @param uid       用户id
     * @param tel       手机号码
     * @param key       验证码
     * @param mCallBack 结果回调
     */
    public void bind(String uid, String tel, String key, HttpStringCallBack mCallBack);

}
