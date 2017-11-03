package com.jierong.share.mvp.view;

import android.content.Context;
import com.jierong.share.mvp.model.info.AdvInfo;
import com.jierong.share.mvp.model.info.MoneyInfo;

/**
 * 广告详情界面接口
 */
public interface IAdvDescView {

    /* 获取当前界面的上下文 */
    public Context getMContext();

    /* 显示loading框 */
    public void showLoading();

    /* 隐藏loading框 */
    public void hideLoading();

    /* 获取广告id */
    public String getAid();

    /* 获取广告分享成功的渠道 */
    public String getAType();

    public void getMoneySuccess(MoneyInfo info);

    /* 广告详情数据获取成功 */
    public void getDataSuccess(AdvInfo data);

    /* 显示错误信息 */
    public void showError(String msg, boolean flag);

}
