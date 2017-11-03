package com.jierong.share.mvp.model.impl;

import com.jierong.share.Constants;
import com.jierong.share.mvp.model.IMeModel;
import com.jierong.share.mvp.model.info.LoginUserInfo;
import com.jierong.share.okhttp.BaseHttpUtil;
import com.jierong.share.okhttp.HttpFileCallBack;
import com.jierong.share.okhttp.HttpStringCallBack;
import com.jierong.share.okhttp.HttpUlCallBack;
import com.lzy.okgo.model.HttpParams;

import java.io.File;

/**
 * 首页界面数据接口实现
 */
public class MeModel implements IMeModel {
    private BaseHttpUtil mBaseHttpUtil;

    public MeModel() {
        mBaseHttpUtil = new BaseHttpUtil();
    }

    /**
     * 获取用户详情数据信息
     * @param uid       用户id
     * @param token     用户token
     * @param mCallBack 结果回调
     */
    @Override
    public void getUserInfo(String uid, String token, final HttpStringCallBack mCallBack) {
        String url = Constants.Http_Api_GetMe;
        HttpParams httpParams = new HttpParams();
        httpParams.put("uid", uid);
        httpParams.put("token", token);
        mBaseHttpUtil.doPost(url, httpParams, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                // 这里返回的是剥离出来的data
                LoginUserInfo user = LoginUserInfo.fromJSON(String.valueOf(result));
                mCallBack.onSuccess(user);
            }

            @Override
            public void onFailure(int code, String msg) {
                mCallBack.onFailure(code, msg);
            }
        });
    }

    /**
     * 获取用户余额
     * @param uid       用户id
     * @param token     用户token
     * @param mCallBack 结果回调
     */
    @Override
    public void getUserMoney(String uid, String token, final HttpStringCallBack mCallBack) {
        String url = Constants.Http_Api_GetUMoney;
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
     * 检查用户绑定支付宝状态
     * @param uid
     * @param token
     * @param mCallBack
     */
    @Override
    public void checkUserBindCard(String uid, String token, final HttpStringCallBack mCallBack) {
        String url = Constants.Http_Api_CheckBindCard;
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
     * 绑定支付宝账号
     * @param uid           用户id
     * @param token         用户token
     * @param name          用户姓名
     * @param zfb           用户支付宝账号
     * @param mCallBack     结果回调
     */
    @Override
    public void bindCard(String uid, String token, String name, String zfb, final HttpStringCallBack mCallBack) {
        String url = Constants.Http_Api_BindCard;
        HttpParams httpParams = new HttpParams();
        httpParams.put("uid", uid);
        httpParams.put("token", token);
        httpParams.put("name", name);
        httpParams.put("zfb", zfb);
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
     * 向支付宝提钱
     * @param uid       用户id
     * @param token     用户token
     * @param money     提钱金额
     * @param mCallBack 结果回调
     */
    @Override
    public void moneyOut(String uid, String token, String money, final HttpStringCallBack mCallBack) {
        String url = Constants.Http_Api_MoneyOut;
        HttpParams httpParams = new HttpParams();
        httpParams.put("uid", uid);
        httpParams.put("token", token);
        httpParams.put("money", money);
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
     * 查看公告信息
     * @param uid       用户id
     * @param token     用户token
     * @param mCallBack 结果回调
     */
    @Override
    public void getGg(String uid, String token, final HttpStringCallBack mCallBack) {
        String url = Constants.Http_Api_GetGg_Me;
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
     * 上传邀请码
     * @param uid           用户id
     * @param token         用户token
     * @param yqm           邀请码
     * @param mCallBack     结果回调
     */
    @Override
    public void upYqm(String uid, String token, String yqm, final HttpStringCallBack mCallBack) {
        String url = Constants.Http_Api_UpYqm;
        HttpParams httpParams = new HttpParams();
        httpParams.put("uid", uid);
        httpParams.put("token", token);
        httpParams.put("yqm", yqm);
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
     * 完善用户头像信息
     * @param uid       用户id
     * @param token     用户token
     * @param uic       用户头像
     * @param mCallBack 结果回调
     */
    @Override
    public void uploadUic(String uid, String token, File uic, final HttpUlCallBack mCallBack) {
        String url = Constants.Http_Api_UpdateUic;
        mBaseHttpUtil.doPostUic(url, uid, token, uic, new HttpUlCallBack() {
            @Override
            public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                mCallBack.upProgress(currentSize, totalSize, progress, networkSpeed);
            }

            @Override
            public void onSuccess() {
                mCallBack.onSuccess();
            }

            @Override
            public void onFailure() {
                mCallBack.onFailure();
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
     *
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
     * 执行退出
     * @param uid       用户id
     * @param mCallBack 结果回调
     */
    @Override
    public void logout(String uid, final HttpStringCallBack mCallBack) {
        String url = Constants.Http_Api_LoginOut;
        HttpParams httpParams = new HttpParams();
        httpParams.put("uid", uid);
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


    /**
     * 关闭网络请求
     */
    @Override
    public void closeHttp() {
        mBaseHttpUtil.closeHttp();
    }

}
