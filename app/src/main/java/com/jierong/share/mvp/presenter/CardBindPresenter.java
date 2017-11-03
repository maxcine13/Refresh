package com.jierong.share.mvp.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.jierong.share.mvp.model.IMeModel;
import com.jierong.share.mvp.model.impl.MeModel;
import com.jierong.share.mvp.view.ICardBindView;
import com.jierong.share.okhttp.HttpStringCallBack;
import com.jierong.share.util.LogUtil;
import com.jierong.share.util.StringUtil;

/**
 * 绑定支付宝管理者
 */
public class CardBindPresenter extends BasePresenter {
    private ICardBindView mICardBindView;
    private IMeModel mIMeModel;
    private Context mContext;

    public CardBindPresenter(ICardBindView view) {
        this.mICardBindView = view;
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

    public void checkInput() {
        String name = mICardBindView.getUName().trim();
        String card = mICardBindView.getCard().trim();
        if (StringUtil.isEmptyIgnoreBlank(name)) {
            mICardBindView.showError("请填写您的姓名", false);
            return;
        }
        if (StringUtil.isEmptyIgnoreBlank(card)) {
            mICardBindView.showError("请填写您的绑定支付宝账号", false);
            return;
        }
        mICardBindView.doCardBind(name, card);
    }

    public void cardBind() {
        if (!isNetworkConnected()) {
            mICardBindView.showError("网络连接错误，请检查网络!", false);
            return;
        }
        String name = mICardBindView.getUName().trim();
        String card = mICardBindView.getCard().trim();
        if (StringUtil.isEmptyIgnoreBlank(name)) {
            mICardBindView.showError("请填写您的姓名", false);
            return;
        }
        if (StringUtil.isEmptyIgnoreBlank(card)) {
            mICardBindView.showError("请填写您的绑定支付宝账号", false);
            return;
        }

        mICardBindView.showLoading();
        mIMeModel.bindCard(getGlobalId(), getGlobalToken(), name, card, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                mICardBindView.hideLoading();
                LogUtil.d(String.valueOf(result));
                mICardBindView.cardBindSuccess();
            }

            @Override
            public void onFailure(int code, String msg) {
                mICardBindView.hideLoading();
                LogUtil.e(code + "_" + msg);
                mICardBindView.showError(msg, false);
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
