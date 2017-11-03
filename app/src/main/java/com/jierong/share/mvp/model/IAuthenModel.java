package com.jierong.share.mvp.model;

import com.jierong.share.okhttp.HttpStringCallBack;

/**
 * 绑定手机界面数据接口
 */
public interface IAuthenModel extends ModelListener {

    /**
     * 检测是否达人认证
     * @param uid       用户id
     * @param token     用户token
     * @param mCallBack 结果回调
     */
    public void check(String uid, String token, HttpStringCallBack mCallBack);

    /**
     * @param uid       用户id
     * @param token     用户唯一标识
     * @param name      用户姓名
     * @param flag      新增和编辑的标识
     * @param ID        用户身份证号
     * @param mCallBack
     */
    public void examine(String uid, String token, String flag, String name, String ID, HttpStringCallBack mCallBack);

}
