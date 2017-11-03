package com.jierong.share.mvp.model;

import com.jierong.share.okhttp.HttpStringCallBack;

/**
 * 商品购物列表数据接口
 */
public interface IShopListModel extends ModelListener {

    /**
     * 查看用户商品购物列表
     * @param page      当前请求页码
     * @param uid       用户id
     * @param token     用户token
     * @param mCallBack 结果回调
     */
    public void getBuyList(int page, String uid, String token, HttpStringCallBack mCallBack);

    /**
     * 查看用户商品购物返利列表
     * @param page      当前请求页码
     * @param uid       用户id
     * @param token     用户token
     * @param mCallBack 结果回调
     */
    public void getFanList(int page, String uid, String token, HttpStringCallBack mCallBack);

    /**
     * 查看白赚-签到列表
     * @param page      当前请求页码
     * @param uid       用户id
     * @param token     用户token
     * @param mCallBack 结果回调
     */
    public void getBzQdList(int page, String uid, String token, HttpStringCallBack mCallBack);

    /**
     * 查看白赚-推荐列表
     * @param page      当前请求页码
     * @param uid       用户id
     * @param token     用户token
     * @param mCallBack 结果回调
     */
    public void getBzTjList(int page, String uid, String token, HttpStringCallBack mCallBack);

    /**
     * 查看白赚-分享列表
     * @param page      当前请求页码
     * @param uid       用户id
     * @param token     用户token
     * @param mCallBack 结果回调
     */
    public void getBzFxList(int page, String uid, String token, HttpStringCallBack mCallBack);

    /**
     * 查看白赚-成交列表
     * @param page      当前请求页码
     * @param uid       用户id
     * @param token     用户token
     * @param mCallBack 结果回调
     */
    public void getBzCjList(int page, String uid, String token, HttpStringCallBack mCallBack);

}
