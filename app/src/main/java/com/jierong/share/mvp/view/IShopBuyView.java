package com.jierong.share.mvp.view;

import android.content.Context;
import com.jierong.share.mvp.model.info.ShopInfo;
import java.util.List;

/**
 * 购物记录界面接口
 */
public interface IShopBuyView {

    /* 获取当前界面的上下文 */
    public Context getMContext();

    /* 显示loading框 */
    public void showLoading();

    /* 隐藏loading框 */
    public void hideLoading();

    /* 获取当前页面 */
    public int getPage();

    /* 刷新数据成功 */
    public void refreshSuccess(String sum, List<ShopInfo> data);

    /* 加载数据成功 */
    public void loadSuccess(String sum, List<ShopInfo> data);

    /* 加载已经全部加载完 */
    public void loadEnd();

    /* 显示错误信息 */
    public void showError(String msg, boolean flag, boolean isRefresh);

}
