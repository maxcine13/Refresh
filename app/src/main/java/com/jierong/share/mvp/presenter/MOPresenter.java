package com.jierong.share.mvp.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.jierong.share.mvp.model.IMeModel;
import com.jierong.share.mvp.model.impl.MeModel;
import com.jierong.share.mvp.view.IMoneyOutView;
import com.jierong.share.okhttp.HttpStringCallBack;
import com.jierong.share.util.LogUtil;
import com.jierong.share.util.StringUtil;

/**
 * 余额提现管理者
 */
public class MOPresenter extends BasePresenter {
    private IMoneyOutView mIMoneyOutView;
    private IMeModel mIMeModel;
    private Context mContext;

    public MOPresenter(IMoneyOutView view) {
        this.mIMoneyOutView = view;
        this.mIMeModel = new MeModel();
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
     * 获取余额
     */
    public void getUMoney() {
        if (!isNetworkConnected()) {
            mIMoneyOutView.showError("网络连接错误，请检查网络!", false);
            return;
        }
        mIMoneyOutView.showLoading();
        mIMeModel.getUserMoney(getGlobalId(), getGlobalToken(), new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                mIMoneyOutView.hideLoading();
                mIMoneyOutView.returnUMoney(String.valueOf(result));
            }

            @Override
            public void onFailure(int code, String msg) {
                mIMoneyOutView.hideLoading();
                LogUtil.e(code + "_" + msg);
            }
        });
    }

    public void moneyOut() {
        if (!isNetworkConnected()) {
            mIMoneyOutView.showError("网络连接错误，请检查网络!", false);
            return;
        }
        String money = mIMoneyOutView.getWant();
        if (StringUtil.isEmptyIgnoreBlank(money)) {
            mIMoneyOutView.showError("请输入您要提现的金额", false);
            return;
        }
        mIMoneyOutView.showLoading();
        mIMeModel.moneyOut(getGlobalId(), getGlobalToken(), money, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                mIMoneyOutView.hideLoading();
                LogUtil.d(String.valueOf(result));
                mIMoneyOutView.txSuccess();
            }

            @Override
            public void onFailure(int code, String msg) {
                mIMoneyOutView.hideLoading();
                LogUtil.e(code + "_" + msg);
                mIMoneyOutView.showError(msg, false);
            }
        });
    }

    /**
     * 关闭网络请求
     */
    public void closeHttp() {
        mIMeModel.closeHttp();
        mContext = null;
    }

}
