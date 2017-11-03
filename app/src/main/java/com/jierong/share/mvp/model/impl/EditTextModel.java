package com.jierong.share.mvp.model.impl;

import com.jierong.share.Constants;
import com.jierong.share.mvp.model.IEditTextModel;
import com.jierong.share.mvp.model.info.TagInfo;
import com.jierong.share.okhttp.BaseHttpUtil;
import com.jierong.share.okhttp.HttpStringCallBack;
import com.lzy.okgo.model.HttpParams;

import java.util.List;

/**
 * 完善用户文本数据模型实现
 */
public class EditTextModel implements IEditTextModel {
    private BaseHttpUtil mBaseHttpUtil;

    public EditTextModel() {
        mBaseHttpUtil = new BaseHttpUtil();
    }

    /**
     * 完善用户文本资料信息
     * @param uid       用户id
     * @param token     用户token
     * @param name      用户名称
     * @param city      用户所在城市
     * @param mCallBack 结果回调
     */
    @Override
    public void changeNameCity(String uid, String token, String name, String city, final HttpStringCallBack mCallBack) {
        String url = Constants.Http_Api_Update_UName_City;
        HttpParams httpParams = new HttpParams();
        httpParams.put("uid", uid);
        httpParams.put("token", token);
        httpParams.put("name", name);
        httpParams.put("city", city);
        mBaseHttpUtil.doPost(url, httpParams, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                // 这里返回的是剥离出来的data
                mCallBack.onSuccess(null);
            }

            @Override
            public void onFailure(int code, String msg) {
                mCallBack.onFailure(code, msg);
            }
        });
    }

    /**
     * 获取标签列表数据
     * @param uid           用户id
     * @param token         用户token
     * @param mCallBack     结果回调
     */
    @Override
    public void getTagList(String uid, String token, final HttpStringCallBack mCallBack) {
        String url = Constants.Http_Api_GetTagList;
        HttpParams httpParams = new HttpParams();
        httpParams.put("uid", uid);
        httpParams.put("token", token);
        mBaseHttpUtil.doPost(url, httpParams, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                List<TagInfo> info = TagInfo.fromJSONS(String.valueOf(result));
                mCallBack.onSuccess(info);
            }

            @Override
            public void onFailure(int code, String msg) {
                mCallBack.onFailure(code, msg);
            }
        });
    }

    /**
     * 完善用户信息
     * @param uid       用户id
     * @param token     用户token
     * @param name      用户名称
     * @param city      用户所在城市
     * @param tag       用户标签
     * @param mCallBack 结果回调
     */
    @Override
    public void changeNameCityTag(String uid, String token, String name, String city, String tag, final HttpStringCallBack mCallBack) {
        String url = Constants.Http_Api_Update_UName_City_Tag;
        HttpParams httpParams = new HttpParams();
        httpParams.put("uid", uid);
        httpParams.put("token", token);
        httpParams.put("name", name);
        httpParams.put("city", city);
        httpParams.put("tagString", tag);
        mBaseHttpUtil.doPost(url, httpParams, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                mCallBack.onSuccess(null);
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