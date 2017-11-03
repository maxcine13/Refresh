package com.jierong.share.mvp.model.impl;

import com.jierong.share.Constants;
import com.jierong.share.mvp.model.ICommentModel;
import com.jierong.share.okhttp.BaseHttpUtil;
import com.jierong.share.okhttp.HttpStringCallBack;
import com.jierong.share.util.LogUtil;
import com.lzy.okgo.model.HttpParams;

/**
 * 达人评论列表接口实现
 */
public class CommentModel implements ICommentModel {
    private BaseHttpUtil mBaseHttpUtil;

    public CommentModel() {
        mBaseHttpUtil = new BaseHttpUtil();
    }

    @Override
    public void getMasterData(String uid, String token,String talent_id, final HttpStringCallBack mCallBack) {
        String url = Constants.Http_Api_CommentList;
        HttpParams httpParams = new HttpParams();
        httpParams.put("uid", uid);
        httpParams.put("token", token);
        httpParams.put("talent_id", talent_id);
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

    @Override
    public void sendComment(String uid, String token, String talent_id, String comment, final HttpStringCallBack mCallBack) {
        String url = Constants.Http_Api_Comment;
        HttpParams httpParams = new HttpParams();
        httpParams.put("uid", uid);
        httpParams.put("token", token);
        httpParams.put("talent_id", talent_id);
        httpParams.put("comment", comment);
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

    @Override
    public void closeHttp() {
        mBaseHttpUtil.closeHttp();
    }

}
