package com.jierong.share.mvp.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.jierong.share.mvp.model.IShopIdModel;
import com.jierong.share.mvp.model.impl.ShopIdModel;
import com.jierong.share.mvp.view.IShopIdView;
import com.jierong.share.okhttp.HttpStringCallBack;
import com.jierong.share.util.StringUtil;

/**
 * 返利查询模块
 */
public class ShopIdPresenter extends BasePresenter {
    private IShopIdView mIShopIdView;
    private IShopIdModel mShopIdModel;
    private Context mContext;

    public ShopIdPresenter(IShopIdView view) {
        this.mIShopIdView = view;
        this.mShopIdModel = new ShopIdModel();
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
     * 查询预计返利
     */
    public void searchBudgetrebate() {
        if (!isNetworkConnected()) {
            mIShopIdView.showError("网络连接错误，请检查网络!", false);
            return;
        }
        if (StringUtil.isEmpty(mIShopIdView.getShopId())) {
            mIShopIdView.showError("请输入订单编号", false);
            return;
        }
        mIShopIdView.showLoading();
        mShopIdModel.searchBudgetrebate(getGlobalId(), getGlobalToken(), mIShopIdView.getShopId(), new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                mIShopIdView.hideLoading();
                mIShopIdView.receiveBudgetData(result.toString());
            }

            @Override
            public void onFailure(int code, String msg) {
                mIShopIdView.hideLoading();
                mIShopIdView.showError(msg, false);
            }
        });


    }

    /**
     * 查询最终返利
     */
    public void searchRealRebate() {
        if (!isNetworkConnected()) {
            mIShopIdView.showError("网络连接错误，请检查网络!", false);
            return;
        }
        if (StringUtil.isEmpty(mIShopIdView.getShopId())) {
            mIShopIdView.showError("请输入订单编号", false);
            return;
        }

        mIShopIdView.showLoading();
        mShopIdModel.searchRealRebate(getGlobalId(), getGlobalToken(), mIShopIdView.getShopId(), new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                mIShopIdView.hideLoading();
                mIShopIdView.receiveRealData(result.toString());
            }

            @Override
            public void onFailure(int code, String msg) {
                mIShopIdView.hideLoading();
                mIShopIdView.showError(msg, false);
            }
        });


    }

    /**
     * 关闭网络请求
     */
    public void closeHttp() {
        mShopIdModel.closeHttp();
        mContext = null;
    }

}
