package com.jierong.share.mvp.view;

import android.content.Context;

/**
 * 用户评论界面的接口
 */
public interface ICommentView {

    /* 获取当前界面的上下文 */
    public Context getMContext();

    /* 显示loading框 */
    public void showLoading();

    /* 隐藏loading框 */
    public void hideLoading();

    /* 接受获取到的用户数据 */
    public void receiveData(String data);

    /* 获取用户的评论 */
    public String getContent();

    /* 提交评论成功 */
    public void sendSuccess();

    /* 显示错误信息 */
    public void showError(String msg, boolean flag);

}
