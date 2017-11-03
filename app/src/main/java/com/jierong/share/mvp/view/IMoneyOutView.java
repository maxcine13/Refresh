package com.jierong.share.mvp.view;

import android.content.Context;

/**
 * 余额提现界面接口
 */
public interface IMoneyOutView {

    /* 获取当前界面的上下文 */
    public Context getMContext();

    /* 显示loading框 */
    public void showLoading();

    /* 隐藏loading框 */
    public void hideLoading();

    /* 获取用户余额 */
    public void returnUMoney(String money);

    /* 获取输入的提现金额 */
    public String getWant();

    /* 提现成功 */
    public void txSuccess();

    /* 显示错误信息 */
    public void showError(String msg, boolean flag);

}
