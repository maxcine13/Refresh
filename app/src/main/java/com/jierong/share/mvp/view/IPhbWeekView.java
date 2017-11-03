package com.jierong.share.mvp.view;

import android.content.Context;
import com.jierong.share.mvp.model.info.WeekBuyInfo;
import com.jierong.share.mvp.model.info.WeekClickInfo;
import java.util.List;

public interface IPhbWeekView {

    /* 获取当前界面的上下文 */
    public Context getMContext();

    public void getDataSuccess(String time, List<WeekClickInfo> click, List<WeekBuyInfo> buy);

    /* 显示错误信息 */
    public void showError(String msg, boolean flag);

}
