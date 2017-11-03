package com.jierong.share.mvp.model.impl;

import com.jierong.share.Constants;
import com.jierong.share.mvp.model.IMessageModel;
import com.jierong.share.mvp.model.info.MessageInfo;
import com.jierong.share.okhttp.BaseHttpUtil;
import com.jierong.share.okhttp.HttpStringCallBack;
import com.lzy.okgo.model.HttpParams;

import java.util.List;

public class MessageModel implements IMessageModel {
    private BaseHttpUtil mBaseHttpUtil;

    public MessageModel() {
        mBaseHttpUtil = new BaseHttpUtil();
    }

    /**
     * 获取消息列表
     * @param page      当前请求页码
     * @param uid       用户id
     * @param token     用户token
     * @param mCallBack 结果回调
     */
    @Override
    public void getMessageList(int page, String uid, String token, final HttpStringCallBack mCallBack) {
        String url = Constants.Http_Api_RedPacket_List;
        HttpParams httpParams = new HttpParams();
        httpParams.put("uid", uid);
        httpParams.put("token", token);
        httpParams.put("page", page);
        mBaseHttpUtil.doPost(url, httpParams, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                List<MessageInfo> data = MessageInfo.fromJSONS(String.valueOf(result));
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
