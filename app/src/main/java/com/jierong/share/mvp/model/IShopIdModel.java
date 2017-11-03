package com.jierong.share.mvp.model;

import com.jierong.share.okhttp.HttpStringCallBack;

/**
 * 查询返利接口
 */
public interface IShopIdModel extends ModelListener {


    /**
     * 查询预计返利接口
     *
     * @param uid         用户id
     * @param token       用户唯一标识
     * @param ordernumber 用户姓名
     * @param mCallBack
     */
    void searchBudgetrebate(String uid, String token, String ordernumber, HttpStringCallBack mCallBack);

    /**
     * 查询最终返利接口
     *
     * @param uid         用户id
     * @param token       用户唯一标识
     * @param ordernumber 用户姓名
     * @param mCallBack
     */
    void searchRealRebate(String uid, String token, String ordernumber, HttpStringCallBack mCallBack);

}
