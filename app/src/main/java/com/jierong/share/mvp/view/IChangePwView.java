package com.jierong.share.mvp.view;

import android.content.Context;

/**
 * 绑定界面接口
 */
public interface IChangePwView {

    /* 获取当前界面的上下文 */
    public Context getMContext();

    /* 显示loading框 */
    public void showLoading();

    /* 隐藏loading框 */
    public void hideLoading();

    /* 获取输入的旧密码 */
    public String getOldPw();

    /* 获取输入的新密码 */
    public String getNewPw();

    /* 获取输入的确认密码 */
    public String getTwoPw();

    /* 修改成功 */
    public void changeSuccess();

    /* 显示错误信息 */
    public void showError(String msg, boolean flag);

}
