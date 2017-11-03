package com.jierong.share.mvp.view;

import android.content.Context;

/**
 * 登录界面接口
 */
public interface ICollectionView {

    /* 获取当前界面的上下文 */
    public Context getMContext();

    /* 显示loading框 */
    public void showLoading();

    /* 隐藏loading框 */
    public void hideLoading();

    /* 获取点击项收藏id */
    public String getTid();

    /* 接受获取到的我的收藏数据 */
    public void receiveData(String data);

    /* 取消收藏成功 */
    public void doCancelSuccess();

    /* 显示错误信息 */
    public void showError(String msg, boolean flag);

}
