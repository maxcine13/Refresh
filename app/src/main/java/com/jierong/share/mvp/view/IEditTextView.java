package com.jierong.share.mvp.view;

import android.content.Context;
import com.jierong.share.mvp.model.info.TagInfo;
import java.util.List;

/**
 * 编辑文本资料界面接口
 */
public interface IEditTextView {

    /* 获取当前界面的上下文 */
    public Context getMContext();

    /* 显示loading框 */
    public void showLoading();

    /* 隐藏loading框 */
    public void hideLoading();

    /* 获取输入的名称内容 */
    public String getUName();
    /* 获取输入的广告需求 */
    public String getNeed();

    /* 获取选择的城市内容 */
    public String getUCity();

    /* 获取标签数据列表 */
    public String getTagList();

    /* 获取标签列表成功 */
    public void getTagSuccess(List<TagInfo> data);

    /* 文本提交成功 */
    public void doSuccess();

    /* 显示错误信息 */
    public void showError(String msg, boolean flag);

}
