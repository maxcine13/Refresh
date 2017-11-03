package com.jierong.share.mvp.model.impl;

import com.jierong.share.Constants;
import com.jierong.share.mvp.model.ITaoBaoModel;
import com.jierong.share.mvp.model.info.TaoBaoInfo;
import com.jierong.share.okhttp.BaseHttpUtil;
import com.jierong.share.okhttp.HttpStringCallBack;
import com.lzy.okgo.model.HttpParams;
import java.util.List;

/**
 * 淘宝商品数据模型接口实现
 */
public class TaoBaoModel implements ITaoBaoModel {
    private BaseHttpUtil mBaseHttpUtil;

    public TaoBaoModel() {
        mBaseHttpUtil = new BaseHttpUtil();
    }

    /**
     * 查询 “品质家电” 淘宝商品列表数据
     * @param page          当前页码
     * @param sort          排序规则
     * @param uid           用户id
     * @param token         用户token
     * @param mCallBack     结果回调
     */
    @Override
    public void loadPzjdData(int page, int sort, String uid, String token, final HttpStringCallBack mCallBack) {
        String url = Constants.Http_Api_Pzjd_List;
        HttpParams httpParams = new HttpParams();
        httpParams.put("page", page);
        httpParams.put("sort", sort);
        httpParams.put("uid", uid);
        httpParams.put("token", token);
        mBaseHttpUtil.doPost(url, httpParams, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                List<TaoBaoInfo> data = TaoBaoInfo.fromJSONS(String.valueOf(result));
                mCallBack.onSuccess(data);
            }

            @Override
            public void onFailure(int code, String msg) {
                mCallBack.onFailure(code, msg);
            }
        });
    }

    /**
     * 查询 “今日特惠” 淘宝商品列表数据
     * @param page          当前页码
     * @param sort          排序规则
     * @param uid           用户id
     * @param token         用户token
     * @param mCallBack     结果回调
     */
    @Override
    public void loadJrthData(int page, int sort, String uid, String token, final HttpStringCallBack mCallBack) {
        String url = Constants.Http_Api_Jrth_List;
        HttpParams httpParams = new HttpParams();
        httpParams.put("page", page);
        httpParams.put("sort", sort);
        httpParams.put("uid", uid);
        httpParams.put("token", token);
        mBaseHttpUtil.doPost(url, httpParams, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                mCallBack.onSuccess(result);
            }

            @Override
            public void onFailure(int code, String msg) {
                mCallBack.onFailure(code, msg);
            }
        });
    }

    /**
     * 查询 “超值返利” 淘宝商品列表数据
     * @param page          当前页码
     * @param sort          排序规则
     * @param uid           用户id
     * @param token         用户token
     * @param mCallBack     结果回调
     */
    @Override
    public void loadCzflData(int page, int sort, String uid, String token, final HttpStringCallBack mCallBack) {
        String url = Constants.Http_Api_Czfl_List;
        HttpParams httpParams = new HttpParams();
        httpParams.put("page", page);
        httpParams.put("sort", sort);
        httpParams.put("uid", uid);
        httpParams.put("token", token);
        mBaseHttpUtil.doPost(url, httpParams, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                mCallBack.onSuccess(result);
            }

            @Override
            public void onFailure(int code, String msg) {
                mCallBack.onFailure(code, msg);
            }
        });
    }

    /**
     * 查询 “爆款返利” 淘宝商品列表数据
     * @param category      商品分类
     * @param page          当前页码
     * @param sort          排序规则
     * @param uid           用户id
     * @param token         用户token
     * @param mCallBack     结果回调
     */
    @Override
    public void loadBkflData(String category, int page, int sort, String uid, String token, final HttpStringCallBack mCallBack) {
        String url = Constants.Http_Api_Bkfl_List;
        HttpParams httpParams = new HttpParams();
        httpParams.put("category", category);
        httpParams.put("page", page);
        httpParams.put("sort", sort);
        httpParams.put("uid", uid);
        httpParams.put("token", token);
        mBaseHttpUtil.doPost(url, httpParams, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                List<TaoBaoInfo> data = TaoBaoInfo.fromJSONS(String.valueOf(result));
                mCallBack.onSuccess(data);
            }

            @Override
            public void onFailure(int code, String msg) {
                mCallBack.onFailure(code, msg);
            }
        });
    }

    @Override
    public void closeHttp() {
        mBaseHttpUtil.closeHttp();
    }
}
