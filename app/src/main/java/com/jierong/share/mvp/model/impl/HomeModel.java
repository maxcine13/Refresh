package com.jierong.share.mvp.model.impl;

import com.jierong.share.Constants;
import com.jierong.share.mvp.model.IHomeModel;
import com.jierong.share.mvp.model.info.BkInfo;
import com.jierong.share.okhttp.BaseHttpUtil;
import com.jierong.share.okhttp.HttpFileCallBack;
import com.jierong.share.okhttp.HttpStringCallBack;
import com.lzy.okgo.model.HttpParams;
import java.io.File;
import java.util.List;

/**
 * 首页界面数据接口实现
 */
public class HomeModel implements IHomeModel {
    private BaseHttpUtil mBaseHttpUtil;

    public HomeModel() {
        mBaseHttpUtil = new BaseHttpUtil();
    }

    /**
     * 告知服务端当天状态
     * @param uid       用户id
     * @param token     用户token
     * @param mCallBack 结果回调
     */
    @Override
    public void telServer(String uid, String token, final HttpStringCallBack mCallBack) {
        String url = Constants.Http_Api_TelServer;
        HttpParams httpParams = new HttpParams();
        httpParams.put("uid", uid);
        httpParams.put("token", token);
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
     * 上传用户定位信息
     *
     * @param uid       用户id
     * @param token     用户token
     * @param city      用户城市定位
     * @param mCallBack 结果回调
     */
    @Override
    public void uploadCity(String uid, String token, String city, final HttpStringCallBack mCallBack) {
        String url = Constants.Http_Api_UploadCity;
        HttpParams httpParams = new HttpParams();
        httpParams.put("uid", uid);
        httpParams.put("token", token);
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
     * 获取首页 顶端的轮播广告数据 和 广告分类列表数据
     *
     * @param uid       用户id
     * @param token     用户token
     * @param mCallBack 结果回调
     */
    @Override
    public void getHomeData(String uid, String token, final HttpStringCallBack mCallBack) {
        String url = Constants.Http_Api_GetHomeData;
        HttpParams httpParams = new HttpParams();
        httpParams.put("uid", uid);
        httpParams.put("token", token);
        mBaseHttpUtil.doPost(url, httpParams, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                // 这里返回的是剥离出来的data
                mCallBack.onSuccess(result);
            }

            @Override
            public void onFailure(int code, String msg) {
                mCallBack.onFailure(code, msg);
            }
        });
    }

    /**
     * 获取 爆款 列表数据
     * @param uid       用户id
     * @param token     用户token
     * @param mCallBack 结果回调
     */
    @Override
    public void getBkData(String uid, String token, final HttpStringCallBack mCallBack) {
        String url = Constants.Http_Api_GetBkData;
        HttpParams httpParams = new HttpParams();
        httpParams.put("uid", uid);
        httpParams.put("token", token);
        mBaseHttpUtil.doPost(url, httpParams, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                List<BkInfo> bkInfos = BkInfo.fromJSONS(String.valueOf(result));
                mCallBack.onSuccess(bkInfos);
            }

            @Override
            public void onFailure(int code, String msg) {
                mCallBack.onFailure(code, msg);
            }
        });
    }

    /**
     * 查看公告信息
     * @param uid       用户id
     * @param token     用户token
     * @param mCallBack 结果回调
     */
    @Override
    public void getGg(String uid, String token, final HttpStringCallBack mCallBack) {
        String url = Constants.Http_Api_GetGg_Home;
        HttpParams httpParams = new HttpParams();
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
     * 展示新人大礼包
     * @param uid       用户id
     * @param token     用户token
     * @param mCallBack 结果回调
     */
    @Override
    public void getShowGift(String uid, String token, final HttpStringCallBack mCallBack) {
        String url = Constants.Http_Api_GetGigt;
        HttpParams httpParams = new HttpParams();
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

    @Override
    public void getSign(String uid, String token, final HttpStringCallBack mCallBack) {
        String url = Constants.Http_Api_Sign;
        HttpParams httpParams = new HttpParams();
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
     * @param uid       用户id
     * @param token     用户token
     * @param path
     * @param name
     * @param url
     * @param mCallBack 结果回调
     */
    @Override
    public void downNewApk(String uid, String token, String path, String name, String url, final HttpFileCallBack mCallBack) {
        mBaseHttpUtil.doDownloadFile(path, name, url, new HttpFileCallBack() {
            @Override
            public void inProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                mCallBack.inProgress(currentSize, totalSize, progress, networkSpeed);
            }

            @Override
            public void onSuccess(File file) {
                mCallBack.onSuccess(file);
            }

            @Override
            public void onError(Exception e) {
                mCallBack.onError(e);
            }
        });
    }

    /**
     * 检查更新
     * @param uid       用户id
     * @param token     用户token
     * @param version   当前APP的Version
     * @param mCallBack 结果回调
     */
    @Override
    public void checkUpdate(String uid, String token, int version, final HttpStringCallBack mCallBack) {
        String url = Constants.Http_Api_GetVersion;
        HttpParams httpParams = new HttpParams();
        httpParams.put("version", version);
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
     * 关闭网络请求
     */
    @Override
    public void closeHttp() {
        mBaseHttpUtil.closeHttp();
    }

}
