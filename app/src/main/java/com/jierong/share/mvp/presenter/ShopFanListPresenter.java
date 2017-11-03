package com.jierong.share.mvp.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.jierong.share.mvp.model.IShopListModel;
import com.jierong.share.mvp.model.impl.ShopListModel;
import com.jierong.share.mvp.model.info.ShopInfo;
import com.jierong.share.mvp.view.IShopFanView;
import com.jierong.share.okhttp.HttpStringCallBack;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;

/**
 * 商城购物列表返利管理者
 */
public class ShopFanListPresenter extends BasePresenter {
    private IShopFanView mIShopFanView;
    private IShopListModel mIShopListModel;
    private Context mContext;

    public ShopFanListPresenter(IShopFanView view) {
        this.mIShopFanView = view;
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
            mIShopFanView.showError("网络连接错误，请检查网络!", false, flag);
            return;
        }

        int page = mIShopFanView.getPage();
        mIShopFanView.showLoading();
        mIShopListModel.getFanList(page, getGlobalId(), getGlobalToken(), new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                mIShopFanView.hideLoading();
                try {
                    JSONObject all = new JSONObject(String.valueOf(result));
                    String maySum = String.valueOf(all.opt("wfanli"));
                    String doneSum = String.valueOf(all.opt("yfanli"));
                    List<ShopInfo> data = ShopInfo.fromJSONS(String.valueOf(all.opt("shoplist")));
                    if(flag) mIShopFanView.refreshSuccess(maySum, doneSum, data);
                    else mIShopFanView.loadSuccess(maySum, doneSum, data);
                } catch (JSONException e) {
                    mIShopFanView.showError("json转换异常", false, flag);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                mIShopFanView.showError(msg, false, flag);
                mIShopFanView.hideLoading();
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
