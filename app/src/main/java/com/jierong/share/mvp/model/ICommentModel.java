package com.jierong.share.mvp.model;

import com.jierong.share.okhttp.HttpStringCallBack;

/**
 * 发布广告的界面数据接口
 */
public interface ICommentModel extends ModelListener {

    /**
     * 获取分享达人数据的接口
     *
     * @param uid         用户id
     * @param token       用户token
     * @param talent_id   达人id
     * @param mCallBack   结果回调
     */
    public void getMasterData(String uid, String token, String talent_id, HttpStringCallBack mCallBack);
    /**
     * 获取分享达人数据的接口
     *
     * @param uid         用户id
     * @param token       用户token
     * @param talent_id   达人id
     * @param comment     用户评论的内容
     * @param mCallBack   结果回调
     */
    public void sendComment(String uid, String token, String talent_id, String comment, HttpStringCallBack mCallBack);
}
