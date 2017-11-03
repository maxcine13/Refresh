package com.jierong.share.mvp.view;

import android.content.Context;
import com.jierong.share.mvp.model.info.AdvInfo;
import com.jierong.share.mvp.model.info.AdvTypeInfo;
import com.jierong.share.mvp.model.info.BkInfo;
import java.io.File;
import java.util.List;

/**
 * 首页界面接口
 */
public interface IHomeView {

    /* 获取当前界面的上下文 */
    public Context getMContext();

    /* 显示loading框 */
    public void showLoading();

    /* 隐藏loading框 */
    public void hideLoading();

    /* 获取当前位置 */
    public String getCity();

    /* 上传地理位置信息成功 */
    public void uploadCitySuccess();

    /* 接受并绘制顶端轮播的广告 */
    public void receiveHomeData(List<AdvInfo> advInfos, List<AdvTypeInfo> advTypeInfos);

    /* 接受并绘制爆款返利的商品 */
    public void receiveBkData(List<BkInfo> bkInfos);

    /* 提示用户是否进行更新操作 */
    public void showSetUpdate(String desc);

    /* 提示下载进度框 */
    public void showDownloadDialog();

    /* 时刻更新下载进度 */
    public void showProgress(float progress, long currentSize, long totalSize);

    /* 下载完成就安装 */
    public void installAPK(File file);

    /* 展示新人大礼包金额 */
    public void showGift(String money);

    /* 获取公告成功 */
    public void getGgSuccess(String result);

    /* 领取签到收益成功 */
    public void receiveSign(String money, boolean flag);

    /* 显示错误信息 */
    public void showError(String msg, boolean flag);
}
