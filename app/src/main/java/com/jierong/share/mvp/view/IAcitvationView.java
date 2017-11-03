package com.jierong.share.mvp.view;

import android.content.Context;

/**
 * 达人认证界面接口
 */
public interface IAcitvationView {

    /* 获取当前界面的上下文 */
    public Context getMContext();

    /* 显示loading框 */
    public void showLoading();

    /* 隐藏loading框 */
    public void hideLoading();

    /* 提交成功 */
    public void receiveData(String message);

    /* 是否同意条约*/
    public boolean getCheck();

    /* 获取输入的姓名*/
    public String getUName();

    /* 获取输入的身份证号码 */
    public String getUID();

    /* 获取输入的银行卡号码 */
    public String getUCard();

    /* 获取输入的银行卡号码绑定的手机号 */
    public String getUPhone();

    /* 显示错误信息 */
    public void showError(String msg, boolean flag);

}
