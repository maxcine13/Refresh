package com.jierong.share.mvp.view;

import android.content.Context;

/**
 * 注册界面接口
 */
public interface IRegisterView {

    /* 获取当前界面的上下文 */
    public Context getMContext();

    /* 显示loading框 */
    public void showLoading();

    /* 隐藏loading框 */
    public void hideLoading();

    /* 获取输入的手机号码 */
    public String getUTel();

    /* 获取输入的验证码 */
    public String getUKey();

    /* 获取输入的推荐码 */
    public String getUTjm();

    /* 获取输入的密码 */
    public String getUPw();

    /* 获取输入的识别码 */
    public String getSignNum();

    /* 验证码获取成功 */
    public void getKeySuccess();

    /* 注册成功 */
    public void registerSuccess();

    /* 显示错误信息 */
    public void showError(String msg, boolean flag);

}
