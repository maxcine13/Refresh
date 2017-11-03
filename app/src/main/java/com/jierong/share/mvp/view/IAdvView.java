package com.jierong.share.mvp.view;

import android.content.Context;
import com.jierong.share.mvp.model.info.AdvTypeInfo;
import java.util.List;

/**
 * 绑定界面接口
 */
public interface IAdvView {

    /* 获取当前界面的上下文 */
    public Context getMContext();

    /* 显示loading框 */
    public void showLoading();

    /* 隐藏loading框 */
    public void hideLoading();

    /* 获取广告信息 */
    public void receiveAdvData(List<AdvTypeInfo> advTypeInfos);

    /* 显示错误信息 */
    public void showError(String msg, boolean flag);

}
