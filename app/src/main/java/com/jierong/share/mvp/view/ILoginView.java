package com.jierong.share.mvp.view;

import android.content.Context;

/**
 * 登录界面接口
 */
public interface ILoginView {

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

    /* 获取输入的手机号码 */
    public String getUName();

    /* 获取输入的验证码 */
    public String getUPass();

    /* 跳转到主界面 */
    public void turnToMain();

    /* 跳转到绑定界面 */
    public void turnToBind();

    /* 显示错误信息 */
    public void showError(String msg, boolean flag);

}
