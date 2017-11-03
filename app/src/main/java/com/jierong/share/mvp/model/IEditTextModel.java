package com.jierong.share.mvp.model;

import com.jierong.share.okhttp.HttpStringCallBack;

/**
 * 完善文本界面数据接口
 */
public interface IEditTextModel extends ModelListener {

    /**
     * 完善用户昵称信息
     * @param uid       用户id
     * @param token     用户token
     * @param name      用户名称
     * @param city      用户所在城市
     * @param mCallBack 结果回调
     */
    public void changeNameCity(String uid, String token, String name, String city, HttpStringCallBack mCallBack);

    /**
     * 获取标签列表数据
     * @param uid           用户id
     * @param token         用户token
     * @param mCallBack     结果回调
     */
    public void getTagList(String uid, String token, HttpStringCallBack mCallBack);

    /**
     * 完善用户信息
     * @param uid       用户id
     * @param token     用户token
     * @param name      用户名称
     * @param city      用户所在城市
     * @param tag       用户标签
     * @param mCallBack 结果回调
     */
    public void changeNameCityTag(String uid, String token, String name, String city, String tag, HttpStringCallBack mCallBack);

}
