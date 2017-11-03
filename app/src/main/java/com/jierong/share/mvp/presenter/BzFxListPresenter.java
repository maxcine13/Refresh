package com.jierong.share.mvp.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.jierong.share.mvp.model.IShopListModel;
import com.jierong.share.mvp.model.impl.ShopListModel;
import com.jierong.share.mvp.model.info.ShopInfo;
import com.jierong.share.mvp.view.IBzFxView;
import com.jierong.share.okhttp.HttpStringCallBack;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;

/**
 * 白赚-分享列表管理者
 */
public class BzFxListPresenter extends BasePresenter {
    private IBzFxView mIBzFxView;
    private IShopListModel mIShopListModel;
    private Context mContext;

    public BzFxListPresenter(IBzFxView view) {
        this.mIBzFxView = view;
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
            mIBzFxView.showError("网络连接错误，请检查网络!", false, flag);
            return;
        }

        int page = mIBzFxView.getPage();
        mIBzFxView.showLoading();
        mIShopListModel.getBzFxList(page, getGlobalId(), getGlobalToken(), new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                mIBzFxView.hideLoading();
                try {
                    JSONObject all = new JSONObject(String.valueOf(result));
                    String sum = String.valueOf(all.opt("share_money"));
                    List<ShopInfo> data = ShopInfo.fromJSONS(String.valueOf(all.opt("share_list")));
                    if(flag) mIBzFxView.refreshSuccess(sum, data);
                    else mIBzFxView.loadSuccess(sum, data);
                } catch (JSONException e) {
                    mIBzFxView.showError("json转换异常", false, flag);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                mIBzFxView.showError(msg, false, flag);
                mIBzFxView.hideLoading();
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
