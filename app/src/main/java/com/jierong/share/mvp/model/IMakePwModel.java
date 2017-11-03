package com.jierong.share.mvp.model;

import com.jierong.share.okhttp.HttpStringCallBack;

/**
 * 设置密码界面数据接口
 */
public interface IMakePwModel extends ModelListener {

    /**
     * 设置密码界面
     * @param uid           用户id
     * @param tel           用户手机号码
     * @param pw            用户密码
     * @param mCallBack     结果回调
     */
    public void makePw(String uid, String tel, String pw, HttpStringCallBack mCallBack);

}
