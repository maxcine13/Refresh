package com.jierong.share.mvp.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.jierong.share.mvp.model.IShopListModel;
import com.jierong.share.mvp.model.impl.ShopListModel;
import com.jierong.share.mvp.model.info.ShopInfo;
import com.jierong.share.mvp.view.IBzTjView;
import com.jierong.share.okhttp.HttpStringCallBack;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;

/**
 * 白赚-推荐列表管理者
 */
public class BzTjListPresenter extends BasePresenter {
    private IBzTjView mIBzTjView;
    private IShopListModel mIShopListModel;
    private Context mContext;

    public BzTjListPresenter(IBzTjView view) {
        this.mIBzTjView = view;
        this.mIShopListModel = new ShopListModel();
        this.mContext = view.getMContext();
    }

    // 判断网络是否连接
    private boolean isNetworkConnected() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager)
                mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        return mNetworkInfo != null && mNetworkInfo.isAvailable();
    }

    public void loadData(final boolean flag) {
        if (!isNetworkConnected()) {
            mIBzTjView.showError("网络连接错误，请检查网络!", false, flag);
            return;
        }

        int page = mIBzTjView.getPage();
        mIBzTjView.showLoading();
        mIShopListModel.getBzTjList(page, getGlobalId(), getGlobalToken(), new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                mIBzTjView.hideLoading();
                try {
                    JSONObject all = new JSONObject(String.valueOf(result));
                    String sum = String.valueOf(all.opt("recommend_money"));
                    List<ShopInfo> data = ShopInfo.fromJSONS(String.valueOf(all.opt("recommend_list")));
                    if(flag) mIBzTjView.refreshSuccess(sum, data);
                    else mIBzTjView.loadSuccess(sum, data);
                } catch (JSONException e) {
                    mIBzTjView.showError("json转换异常", false, flag);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                mIBzTjView.showError(msg, false, flag);
                mIBzTjView.hideLoading();
            }
        });
    }

    /**
     * 关闭网络请求
     */
    public void closeHttp() {
        mIShopListModel.closeHttp();
        mContext = null;
    }

}
