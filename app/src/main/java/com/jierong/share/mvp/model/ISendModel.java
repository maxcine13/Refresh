package com.jierong.share.mvp.model;

import com.jierong.share.okhttp.HttpStringCallBack;

/**
 * 发布广告的界面数据接口
 */
public interface ISendModel extends ModelListener {

    /**
     * 获取广告分类列表数据
     * @param uid       用户id
     * @param token     用户token
     * @param mCallBack 结果回调
     */
    public void getAdvType(String uid, String token, HttpStringCallBack mCallBack);

    /**
     * 发送广播的接口
     * @param uid       用户id
     * @param token     用户token
     * @param name      名称
     * @param tel       手机
     * @param type      广告分类
     * @param desc      广告需求描述
     * @param mCallBack 结果回调
     */
    public void sendAdv(String uid, String token, String name, String tel,
                        String type, String desc, HttpStringCallBack mCallBack);
}
