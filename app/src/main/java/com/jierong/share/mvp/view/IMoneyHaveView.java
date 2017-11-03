package com.jierong.share.mvp.view;

import android.content.Context;

/**
 * 我的余额界面接口
 */
public interface IMoneyHaveView {

    /* 获取当前界面的上下文 */
    public Context getMContext();

    /* 显示loading框 */
    public void showLoading();

    /* 隐藏loading框 */
    public void hideLoading();

    /* 获取用户余额 */
    public void getUMoney(String money);

    /* 获取绑定账号 */
    public void getCard(String card);

    public void turnToBind();

    /* 显示错误信息 */
    public void showError(String msg, boolean flag);

}
