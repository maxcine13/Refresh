package com.jierong.share.mvp.view;

import android.content.Context;

import com.jierong.share.mvp.model.info.LoginUserInfo;

import java.io.File;

/**
 * 我的界面接口
 */
public interface IMeView {

    /** 获取当前界面的上下文 */
    public Context getMContext();

    /** 显示loading框 */
    public void showLoading();

    /** 隐藏loading框 */
    public void hideLoading();

    /** 接受获取到的用户数据 */
    public void receiveUser(LoginUserInfo user);

    /** 获取当前界面的上下文 */
    public String getUicPath();

    /** 编辑用户头像 */
    public void changeUicSuccess(File file);

    /** 获取输入的邀请码 */
    public String getYqm();

    /** 上传绑定邀请码成功 */
    public void upYqmSuccess();

    /** 获取公告成功 */
    public void getGgSuccess(String result);

    /** 获取当前的版本号 */
    public int getVersion();

    /** 安装APK */
    public void installAPK(File file);

    /** 是否显示更新对话框 */
    public void showSetUpda(String flag);

    /** 是否显示更新进度 */
    public void showProgress(float progress, long current, long total);

    /** 是否显示下载对话框 */
    public void showDownloadDialog();

    /** 获取用户余额 */
    public void getUMoney(String money);

    /** 接收获取到的最新版本号 */
    public void receiveVersionInfo(String version);

    /** 退出登录成功 */
    public void logoutSuccess();

    /** 显示错误信息 */
    public void showError(String msg, boolean flag);

}
