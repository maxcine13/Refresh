package com.jierong.share.mvp.view;

import android.content.Context;
import com.jierong.share.mvp.model.info.AdvInfo;

/**
 * 欢迎界面接口
 */
public interface IWelcomeView {

    /* 获取当前界面的上下文 */
    public Context getMContext();

    /* 显示loading框 */
    public void showLoading();

    /* 隐藏loading框 */
    public void hideLoading();

    /* 刷新获取推送id */
    public void refreshPushId();

    /* 获取推送id */
    public String getPushId();

    /* 显示动态广告图 */
    public void showNetAdv(AdvInfo advInfo);

    /* 显示本地默认广告图 */
    public void showLocalAdv();

    /* 显示错误信息 */
    public void showError(String msg, boolean flag);

    /* 跳转到引导页面 */
    public void turnToGuide();

    /* 跳转到登录 */
    public void turnToLogin();

    /* 跳转到绑定界面 */
    public void turnToBind();

    /* 跳转到主界面 */
    public void turnToMain();
}
