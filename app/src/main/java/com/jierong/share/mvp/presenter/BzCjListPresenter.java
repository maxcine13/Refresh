package com.jierong.share.mvp.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.jierong.share.mvp.model.IShopListModel;
import com.jierong.share.mvp.model.impl.ShopListModel;
import com.jierong.share.mvp.model.info.ShopInfo;
import com.jierong.share.mvp.view.IBzCjView;
import com.jierong.share.okhttp.HttpStringCallBack;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;

/**
 * 白赚-成交列表管理者
 */
public class BzCjListPresenter extends BasePresenter {
    private IBzCjView mIBzCjView;
    private IShopListModel mIShopListModel;
    private Context mContext;

    public BzCjListPresenter(IBzCjView view) {
        this.mIBzCjView = view;
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
            mIBzCjView.showError("网络连接错误，请检查网络!", false, flag);
            return;
        }

        int page = mIBzCjView.getPage();
        mIBzCjView.showLoading();
        mIShopListModel.getBzCjList(page, getGlobalId(), getGlobalToken(), new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                mIBzCjView.hideLoading();
                try {
                    JSONObject all = new JSONObject(String.valueOf(result));
                    String sum = String.valueOf(all.opt("share_money"));
                    List<ShopInfo> data = ShopInfo.fromJSONS(String.valueOf(all.opt("share_list")));
                    if(flag) mIBzCjView.refreshSuccess(sum, data);
                    else mIBzCjView.loadSuccess(sum, data);
                } catch (JSONException e) {
                    mIBzCjView.showError("json转换异常", false, flag);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                mIBzCjView.showError(msg, false, flag);
                mIBzCjView.hideLoading();
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
