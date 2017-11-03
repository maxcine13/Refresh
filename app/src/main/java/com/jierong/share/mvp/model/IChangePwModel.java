package com.jierong.share.mvp.model;

import com.jierong.share.okhttp.HttpStringCallBack;

/**
 * 修改密码数据接口
 */
public interface IChangePwModel extends ModelListener {

    /**
     * 根据uid修改新的手机登录密码
     * @param uid       用户id
     * @param token     用户token
     * @param old       用户输入的旧密码
     * @param pass      用户设置的新密码
     * @param mCallBack 结果回调
     */
    public void change(String uid, String token, String old, String pass, HttpStringCallBack mCallBack);

}
