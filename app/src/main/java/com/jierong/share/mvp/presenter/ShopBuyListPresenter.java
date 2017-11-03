package com.jierong.share.mvp.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.jierong.share.mvp.model.IShopListModel;
import com.jierong.share.mvp.model.impl.ShopListModel;
import com.jierong.share.mvp.model.info.ShopInfo;
import com.jierong.share.mvp.view.IShopBuyView;
import com.jierong.share.okhttp.HttpStringCallBack;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;

/**
 * 商城购物列表管理者
 */
public class ShopBuyListPresenter extends BasePresenter {
    private IShopBuyView mIShopBuyView;
    private IShopListModel mIShopListModel;
    private Context mContext;

    public ShopBuyListPresenter(IShopBuyView view) {
        this.mIShopBuyView = view;
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
            mIShopBuyView.showError("网络连接错误，请检查网络!", false, flag);
            return;
        }

        int page = mIShopBuyView.getPage();
        mIShopBuyView.showLoading();
        mIShopListModel.getBuyList(page, getGlobalId(), getGlobalToken(), new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                mIShopBuyView.hideLoading();
                try {
                    JSONObject all = new JSONObject(String.valueOf(result));
                    String sum = String.valueOf(all.opt("sum"));
                    List<ShopInfo> data = ShopInfo.fromJSONS(String.valueOf(all.opt("shoplist")));
                    if(flag) mIShopBuyView.refreshSuccess(sum, data);
                    else mIShopBuyView.loadSuccess(sum, data);
                } catch (JSONException e) {
                    mIShopBuyView.showError("json转换异常", false, flag);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                mIShopBuyView.showError(msg, false, flag);
                mIShopBuyView.hideLoading();
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
