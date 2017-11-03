package com.jierong.share.mvp.model;

import com.jierong.share.okhttp.HttpStringCallBack;

/**
 * 广告列表数据接口
 */
public interface IAdvListModel extends ModelListener {

    /**
     * 根据用户点选的广告分类，查看广告列表
     * @param uid       用户id
     * @param token     用户token
     * @param tid       广告分类id
     * @param mCallBack 结果回调
     */
    public void getAdvList(String uid, String token, String tid, HttpStringCallBack mCallBack);


    /**
     * 点赞
     * @param uid       用户id
     * @param token     用户token
     * @param aid       广告id
     * @param mCallBack 结果回调
     */
    public void doZan(String uid, String token, String aid, HttpStringCallBack mCallBack);

}
