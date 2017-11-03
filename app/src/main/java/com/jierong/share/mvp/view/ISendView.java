package com.jierong.share.mvp.view;

import android.content.Context;
import com.jierong.share.mvp.model.info.AdvTypeInfo;
import java.util.List;

/**
 * 广告发送界面的接口
 */
public interface ISendView {

    /* 获取当前界面的上下文 */
    public Context getMContext();

    /* 显示loading框 */
    public void showLoading();

    /* 隐藏loading框 */
    public void hideLoading();

    /* 接受获取到的用户数据 */
    public void receiveType(List<AdvTypeInfo> advTypeInfos);

    /* 获取输入的用户名 */
    public String getUName();

    /* 获取输入的电话号码 */
    public String getUTel();

    /* 获取选择的广告类型 */
    public String getAdvType();

    /* 获取输入的广告需求描述 */
    public String getAdvDesc();

    /* 广告提交成功 */
    public void sendSuccess();

    /* 显示错误信息 */
    public void showError(String msg, boolean flag);

}
