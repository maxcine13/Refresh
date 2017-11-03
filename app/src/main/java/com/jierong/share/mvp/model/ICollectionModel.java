package com.jierong.share.mvp.model;

import com.jierong.share.okhttp.HttpStringCallBack;

/**
 * 我的收藏数据接口
 */
public interface ICollectionModel extends ModelListener {

    /**
     * 获取用户收藏的接口
     * @param uid       用户id
     * @param token     用户token
     * @param mCallBack 结果回调
     */
    public void getCollectionData(String uid, String token, HttpStringCallBack mCallBack);

    /**
     * 取消收藏
     * @param uid       用户id
     * @param token     用户token
     * @param tid       收藏item id
     * @param mCallBack 结果回调
     */
    public void doCancel(String uid, String token, String tid, HttpStringCallBack mCallBack);

}
