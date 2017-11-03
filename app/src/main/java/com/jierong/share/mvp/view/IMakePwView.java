package com.jierong.share.mvp.view;

import android.content.Context;

/**
 * 设置密码界面接口
 */
public interface IMakePwView {

    /* 获取当前界面的上下文 */
    public Context getMContext();

    /* 显示loading框 */
    public void showLoading();

    /* 隐藏loading框 */
    public void hideLoading();

    /* 获取用户绑定的手机号码 */
    public String getUTel();

    /* 获取输入的密码 */
    public String getUPw();

    /* 获取输入的确认密码 */
    public String getUPwTwo();

    /* 设置密码成功 */
    public void makeSuccess();

    /* 显示错误信息 */
    public void showError(String msg, boolean flag);

}
