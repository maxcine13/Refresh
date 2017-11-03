package com.jierong.share.mvp.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.jierong.share.mvp.model.IAdvDescModel;
import com.jierong.share.mvp.model.impl.AdvDescModel;
import com.jierong.share.mvp.model.info.AdvInfo;
import com.jierong.share.mvp.model.info.MoneyInfo;
import com.jierong.share.mvp.view.IAdvDescView;
import com.jierong.share.okhttp.HttpStringCallBack;

/**
 * 获取广告详情数据
 */
public class AdvDescPresenter extends BasePresenter {
    private IAdvDescView mIAdvDescView;
    private IAdvDescModel mIAdvDescModel;
    private Context mContext;

    public AdvDescPresenter(IAdvDescView view) {
        this.mIAdvDescView = view;
        this.mIAdvDescModel = new AdvDescModel();
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
     * 获取广告列表数据
     */
    public void getAdvDesc() {
        if(!isNetworkConnected()) {
            mIAdvDescView.showError("网络连接错误，请检查网络!", false);
            return;
        }
        mIAdvDescView.showLoading();
        mIAdvDescModel.getAdvDesc(getGlobalId(), getGlobalToken(),
                mIAdvDescView.getAid(), new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                mIAdvDescView.getDataSuccess((AdvInfo) result);
                mIAdvDescView.hideLoading();
            }

            @Override
            public void onFailure(int code, String msg) {
                mIAdvDescView.showError(msg, false);
                mIAdvDescView.hideLoading();
            }
        });
    }

    /**
     * 获取分享金额
     */
    public void getShareMoney() {
        if(!isNetworkConnected()) {
            mIAdvDescView.showError("网络连接错误，请检查网络!", false);
            return;
        }
        mIAdvDescView.showLoading();
        mIAdvDescModel.getShareMoney(getGlobalId(), getGlobalToken(), mIAdvDescView.getAid(),
                mIAdvDescView.getAType(), new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        mIAdvDescView.getMoneySuccess((MoneyInfo) result);
                        mIAdvDescView.hideLoading();
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        //mIAdvDescView.showError(msg, false);
                        mIAdvDescView.hideLoading();
                    }
                });
    }

    /**
     * 关闭网络请求
     */
    public void closeHttp() {
        mIAdvDescModel.closeHttp();
        mContext = null;
    }

}
