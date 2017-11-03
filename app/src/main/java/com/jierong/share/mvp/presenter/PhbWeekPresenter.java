package com.jierong.share.mvp.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.jierong.share.mvp.model.IPhbWeekModel;
import com.jierong.share.mvp.model.impl.PhbWeekModel;
import com.jierong.share.mvp.model.info.WeekBuyInfo;
import com.jierong.share.mvp.model.info.WeekClickInfo;
import com.jierong.share.mvp.view.IPhbWeekView;
import com.jierong.share.okhttp.HttpStringCallBack;
import com.jierong.share.util.LogUtil;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;

/**
 * 周排行榜管理者
 */
public class PhbWeekPresenter extends BasePresenter {
    private IPhbWeekView mIPhbWeekView;
    private IPhbWeekModel mIPhbWeekModel;
    private Context mContext;

    public PhbWeekPresenter(IPhbWeekView view) {
        this.mIPhbWeekView = view;
        this.mIPhbWeekModel = new PhbWeekModel();
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
            mIPhbWeekView.showError("网络连接错误，请检查网络!", false);
            return;
        }
        mIPhbWeekModel.loadData(getGlobalId(), getGlobalToken(), new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                try {
                    JSONObject all = new JSONObject(String.valueOf(result));
                    String time = String.valueOf(all.opt("week"));
                    List<WeekClickInfo> click = WeekClickInfo.fromJSONS(String.valueOf(all.opt("wc_ranking")));
                    List<WeekBuyInfo> buy = WeekBuyInfo.fromJSONS(String.valueOf(all.opt("wb_ranking")));
                    mIPhbWeekView.getDataSuccess(time, click, buy);
                } catch (JSONException e) {
                    mIPhbWeekView.showError("json转换异常", false);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                LogUtil.e(code + "_" + msg);
                mIPhbWeekView.showError(msg, false);
            }
        });
    }

    /**
     * 关闭网络请求
     */
    public void closeHttp() {
        mIPhbWeekModel.closeHttp();
        mContext = null;
    }

}
