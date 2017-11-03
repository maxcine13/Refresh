package com.jierong.share.mvp.presenter;

import android.content.Context;
import android.telephony.TelephonyManager;
import com.jierong.share.BaseApp;
import com.jierong.share.Constants;
import com.jierong.share.util.AppPreferences;

/**
 * 通用接口方法
 */
public class BasePresenter {

    /**
     * 获取全局变量uid
     */
    protected String getGlobalId() {
        String result;
        result = AppPreferences.getString(BaseApp.getContext(), Constants.PNK_UId, null);
        return result;
    }

    /**
     * 获取全局变量token
     */
    protected String getGlobalToken() {
        String result;
        result = AppPreferences.getString(BaseApp.getContext(), Constants.PNK_UToken, null);
        return result;
    }

    /**
     * 获取手机imei
     * @return
     */
    protected String getImei(Context context) {
        String result;
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        result = telephonyManager.getDeviceId();
        return result;
    }

}
