package com.jierong.share.mvp.model;

import com.jierong.share.okhttp.HttpStringCallBack;

/**
 * 分享赚钱 广告分类列表数据接口
 */
public interface IAdvModel extends ModelListener {


    /**
     * 获取分享赚钱 广告分类列表数据
     * @param uid       用户id
     * @param token     用户token
     * @param mCallBack 结果回调
     */
    public void getAdvListData(String uid, String token, HttpStringCallBack mCallBack);


}
