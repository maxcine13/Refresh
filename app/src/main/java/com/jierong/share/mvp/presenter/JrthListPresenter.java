package com.jierong.share.mvp.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.jierong.share.mvp.model.ITaoBaoModel;
import com.jierong.share.mvp.model.impl.TaoBaoModel;
import com.jierong.share.mvp.model.info.TaoBaoInfo;
import com.jierong.share.mvp.view.IJrthView;
import com.jierong.share.okhttp.HttpStringCallBack;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;

/**
 * 今日特惠界面管理者
 */
public class JrthListPresenter extends BasePresenter {
    private IJrthView mIJrthView;
    private ITaoBaoModel mITaoBaoModel;
    private Context mContext;

    public JrthListPresenter(IJrthView view) {
        this.mContext = view.getMContext();
        this.mIJrthView = view;
        this.mITaoBaoModel = new TaoBaoModel();
    }

    // 判断网络是否连接
    private boolean isNetworkConnected() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager)
                mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        return mNetworkInfo != null && mNetworkInfo.isAvailable();
    }

    /**
     * 获取列表数据
     */
    public void loadData() {
        if(!isNetworkConnected()) {
            mIJrthView.showError("网络连接错误，请检查网络!", false);
            return;
        }

        final int page = mIJrthView.getPage();
        int sort = mIJrthView.getSort();
        mIJrthView.showLoading();
        mITaoBaoModel.loadJrthData(page, sort, getGlobalId(), getGlobalToken(),
                new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                mIJrthView.hideLoading();
                try {
                    JSONObject all = new JSONObject(String.valueOf(result));
                    if(page == 1 ) {
                        String img = String.valueOf(all.opt("banner"));
                        List<TaoBaoInfo> data = TaoBaoInfo.fromJSONS(String.valueOf(all.opt("todayso")));
                        mIJrthView.refreshSuccess(data, img);
                    } else {
                        List<TaoBaoInfo> data = TaoBaoInfo.fromJSONS(String.valueOf(all.opt("todayso")));
                        mIJrthView.loadSuccess(data);
                    }
                } catch (JSONException e) {
                    mIJrthView.showError("json转换异常", false);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                mIJrthView.showError(msg, false);
                mIJrthView.hideLoading();
            }
        });
    }

    /**
     * 关闭网络请求
     */
    public void closeHttp() {
        mITaoBaoModel.closeHttp();
        mContext = null;
    }

}
