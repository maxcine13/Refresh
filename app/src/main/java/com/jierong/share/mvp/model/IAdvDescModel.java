package com.jierong.share.mvp.model;

import com.jierong.share.okhttp.HttpStringCallBack;

/**
 * 广告详情数据接口
 */
public interface IAdvDescModel extends ModelListener {

    /**
     * 根据用户点选某条广告，查看该条广告详情
     * @param uid       用户id
     * @param token     用户token
     * @param aid       广告id
     * @param mCallBack 结果回调
     */
    public void getAdvDesc(String uid, String token, String aid, HttpStringCallBack mCallBack);

    /**
     * 获取用户分享收益
     * @param uid       用户id
     * @param token     用户token
     * @param aid       广告id
     * @param shareType 分享的渠道
     * @param mCallBack 结果回调
     */
    public void getShareMoney(String uid, String token, String aid,
                              String shareType, HttpStringCallBack mCallBack);

}
