package com.jierong.share.mvp.model;

import com.jierong.share.okhttp.HttpStringCallBack;

/**
 * 淘宝商品数据模型接口
 */
public interface ITaoBaoModel extends ModelListener {

    /**
     * 查询 “品质家电” 淘宝商品列表数据
     * @param page          当前页码
     * @param sort          排序规则
     * @param uid           用户id
     * @param token         用户token
     * @param mCallBack     结果回调
     */
    public void loadPzjdData(int page, int sort, String uid, String token, HttpStringCallBack mCallBack);

    /**
     * 查询 “今日特惠” 淘宝商品列表数据
     * @param page          当前页码
     * @param sort          排序规则
     * @param uid           用户id
     * @param token         用户token
     * @param mCallBack     结果回调
     */
    public void loadJrthData(int page, int sort, String uid, String token, HttpStringCallBack mCallBack);

    /**
     * 查询 “超值返利” 淘宝商品列表数据
     * @param page          当前页码
     * @param sort          排序规则
     * @param uid           用户id
     * @param token         用户token
     * @param mCallBack     结果回调
     */
    public void loadCzflData(int page, int sort, String uid, String token, HttpStringCallBack mCallBack);

    /**
     * 查询 “爆款返利” 淘宝商品列表数据
     * @param category      商品分类
     * @param page          当前页码
     * @param sort          排序规则
     * @param uid           用户id
     * @param token         用户token
     * @param mCallBack     结果回调
     */
    public void loadBkflData(String category, int page, int sort, String uid, String token, HttpStringCallBack mCallBack);

}
