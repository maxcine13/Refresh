package com.jierong.share.mvp.view;

import android.content.Context;
import com.jierong.share.mvp.model.info.MaInfo;

/**
 * 达人认证界面接口
 */
public interface IAuthenView {

    /* 获取当前界面的上下文 */
    public Context getMContext();

    /* 显示loading框 */
    public void showLoading();

    /* 隐藏loading框 */
    public void hideLoading();

    /* 达人认证成功 */
    public void authenSuccess(MaInfo info);

    /* 达人认证失败 */
    public void authenFail();

    /* 提交成功 */
    public void receiveData(String message);

    /* dialog提示*/
    public void showTips(String message);

    /* 获取网络请求的标识(1新增,2修改)*/
    public String getFlag();

    /* 是否同意条约*/
    public boolean getCheck();

    /* 获取输入的姓名*/
    public String getUName();

    /* 获取输入的身份证号码 */
    public String getUID();

    /* 显示错误信息 */
    public void showError(String msg, boolean flag);

}
