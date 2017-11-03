package com.jierong.share.mvp.model;

import com.jierong.share.okhttp.HttpStringCallBack;

/**
 * 消息列表数据接口
 */
public interface IMessageModel extends ModelListener {

    /**
     * 获取消息列表
     * @param page      当前请求页码
     * @param uid       用户id
     * @param token     用户token
     * @param mCallBack 结果回调
     */
    public void getMessageList(int page, String uid, String token, HttpStringCallBack mCallBack);

}
