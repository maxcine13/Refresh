package com.jierong.share.mvp.model;

import com.jierong.share.okhttp.HttpFileCallBack;
import com.jierong.share.okhttp.HttpStringCallBack;
import com.jierong.share.okhttp.HttpUlCallBack;
import java.io.File;

/**
 * 我的界面数据接口
 */
public interface IMeModel extends ModelListener {

    /**
     * 获取用户详情数据信息
     * @param uid       用户id
     * @param token     用户token
     * @param mCallBack 结果回调
     */
    public void getUserInfo(String uid, String token, HttpStringCallBack mCallBack);

    /**
     * 获取用户余额
     * @param uid       用户id
     * @param token     用户token
     * @param mCallBack 结果回调
     */
    public void getUserMoney(String uid, String token, HttpStringCallBack mCallBack);

    /**
     * 检查用户绑定支付宝状态
     * @param uid           用户id
     * @param token         用户token
     * @param mCallBack     结果回调
     */
    public void checkUserBindCard(String uid, String token, HttpStringCallBack mCallBack);

    /**
     * 绑定支付宝账号
     * @param uid           用户id
     * @param token         用户token
     * @param name          用户姓名
     * @param zfb           用户支付宝账号
     * @param mCallBack     结果回调
     */
    public void bindCard(String uid, String token, String name, String zfb, HttpStringCallBack mCallBack);

    /**
     * 向支付宝提钱
     * @param uid       用户id
     * @param token     用户token
     * @param money     提钱金额
     * @param mCallBack 结果回调
     */
    public void moneyOut(String uid, String token, String money, HttpStringCallBack mCallBack);

    /**
     * 查看公告信息
     * @param uid       用户id
     * @param token     用户token
     * @param mCallBack 结果回调
     */
    public void getGg(String uid, String token, HttpStringCallBack mCallBack);

    /**
     * 上传邀请码
     * @param uid           用户id
     * @param token         用户token
     * @param yqm           邀请码
     * @param mCallBack     结果回调
     */
    public void upYqm(String uid, String token, String yqm, HttpStringCallBack mCallBack);

    /**
     * 完善用户头像信息
     *
     * @param uid       用户id
     * @param token     用户token
     * @param uic       用户头像
     * @param mCallBack 结果回调
     */
    public void uploadUic(String uid, String token, File uic, HttpUlCallBack mCallBack);

    /**
     * 下载最新的apk
     *
     * @param uid       用户ID
     * @param token     用户token
     * @param path      文件储存路径
     * @param name      文件名
     * @param url       下载路径
     * @param mCallBack 结果回调
     */
    public void downNewApk(String uid, String token, String path, String name, String url, HttpFileCallBack mCallBack);


    /**
     * 检查更新APP版本
     * @param uid       用户id
     * @param token     用户token
     * @param version   当前APP的Version
     * @param mCallBack 结果回调
     */
    public void checkUpdate(String uid, String token, int version, HttpStringCallBack mCallBack);

    /**
     * 执行退出
     * @param uid       用户id
     * @param mCallBack 结果回调
     */
    public void logout(String uid, HttpStringCallBack mCallBack);

}
