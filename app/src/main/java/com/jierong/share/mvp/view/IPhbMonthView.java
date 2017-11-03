package com.jierong.share.mvp.view;

import android.content.Context;
import com.jierong.share.mvp.model.info.MonthBuyInfo;
import com.jierong.share.mvp.model.info.MonthClickInfo;
import com.jierong.share.mvp.model.info.MonthFanInfo;
import java.util.List;

public interface IPhbMonthView {

    /* 获取当前界面的上下文 */
    public Context getMContext();

    public void getDataSuccess(String time, List<MonthClickInfo> click,
                               List<MonthBuyInfo> buy, List<MonthFanInfo> fan);

    /* 显示错误信息 */
    public void showError(String msg, boolean flag);

}
