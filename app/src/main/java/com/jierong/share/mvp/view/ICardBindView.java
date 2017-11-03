package com.jierong.share.mvp.view;

import android.content.Context;

/**
 * 绑定支付宝界面接口
 */
public interface ICardBindView {

    /* 获取当前界面的上下文 */
    public Context getMContext();

    /* 显示loading框 */
    public void showLoading();

    /* 隐藏loading框 */
    public void hideLoading();

    /* 获取输入的姓名 */
    public String getUName();

    /* 获取输入的支付宝账号 */
    public String getCard();

    /* 执行输入内容的二次确认提示 */
    public void doCardBind(String name, String zfb);

    /* 绑定支付宝成功 */
    public void cardBindSuccess();

    /* 显示错误信息 */
    public void showError(String msg, boolean flag);

}
