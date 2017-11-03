package com.jierong.share.mvp.view;

import android.content.Context;
import com.jierong.share.mvp.model.info.AdvInfo;
import java.util.List;

/**
 * 广告列表界面接口
 */
public interface IAdvListView {

    /* 获取当前界面的上下文 */
    public Context getMContext();

    /* 显示loading框 */
    public void showLoading();

    /* 隐藏loading框 */
    public void hideLoading();

    /* 获取广告分类id */
    public String getTid();

    /* 获取广告id */
    public String getAid();

    /* 广告列表数据获取成功 */
    public void getDataSuccess(List<AdvInfo> data);

    /* 点赞成功 */
    public void doZanSuccess();

    /* 显示错误信息 */
    public void showError(String msg, boolean flag);

}
