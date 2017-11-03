package com.jierong.share.mvp.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.jierong.share.mvp.model.IPhbMonthModel;
import com.jierong.share.mvp.model.impl.PhbMonthModel;
import com.jierong.share.mvp.model.info.MonthBuyInfo;
import com.jierong.share.mvp.model.info.MonthClickInfo;
import com.jierong.share.mvp.model.info.MonthFanInfo;
import com.jierong.share.mvp.view.IPhbMonthView;
import com.jierong.share.okhttp.HttpStringCallBack;
import com.jierong.share.util.LogUtil;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;

/**
 * 月排行榜管理者
 */
public class PhbMonthPresenter extends BasePresenter {
    private IPhbMonthView mIPhbMonthView;
    private IPhbMonthModel mIPhbMonthModel;
    private Context mContext;

    public PhbMonthPresenter(IPhbMonthView view) {
        this.mIPhbMonthView = view;
        this.mIPhbMonthModel = new PhbMonthModel();
        this.mContext = view.getMContext();
    }

    // 判断网络是否连接
    private boolean isNetworkConnected() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager)
                mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        return mNetworkInfo != null && mNetworkInfo.isAvailable();
    }

    /**
     * 加载数据
     */
    public void loadData() {
        if (!isNetworkConnected()) {
            mIPhbMonthView.showError("网络连接错误，请检查网络!", false);
            return;
        }
        mIPhbMonthModel.loadData(getGlobalId(), getGlobalToken(), new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                try {
                    JSONObject all = new JSONObject(String.valueOf(result));
                    String time = String.valueOf(all.opt("month"));
                    List<MonthClickInfo> click = MonthClickInfo.fromJSONS(String.valueOf(all.opt("mc_ranking")));
                    List<MonthBuyInfo> buy = MonthBuyInfo.fromJSONS(String.valueOf(all.opt("mb_ranking")));
                    List<MonthFanInfo> fan = MonthFanInfo.fromJSONS(String.valueOf(all.opt("mr_ranking")));
                    mIPhbMonthView.getDataSuccess(time, click, buy, fan);
                } catch (JSONException e) {
                    mIPhbMonthView.showError("json转换异常", false);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                LogUtil.e(code + "_" + msg);
                mIPhbMonthView.showError(msg, false);
            }
        });
    }

    /**
     * 关闭网络请求
     */
    public void closeHttp() {
        mIPhbMonthModel.closeHttp();
        mContext = null;
    }

}
