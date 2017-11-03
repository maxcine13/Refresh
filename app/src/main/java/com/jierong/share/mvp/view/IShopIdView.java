package com.jierong.share.mvp.view;

import android.content.Context;

/**
 * 达人认证界面接口
 */
public interface IShopIdView {

    /* 获取当前界面的上下文 */
    public Context getMContext();

    /* 显示loading框 */
    public void showLoading();

    /* 隐藏loading框 */
    public void hideLoading();

    /* 获取输入的商品id */
    public String getShopId();

    /* 接收查询预计返利的信息*/
    public void receiveBudgetData(String data);

    /* 接收查询到最终返利的信息*/
    public void receiveRealData(String data);

    /* 显示错误信息 */
    public void showError(String msg, boolean flag);

}
