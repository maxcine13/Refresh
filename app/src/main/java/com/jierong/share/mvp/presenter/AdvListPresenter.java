package com.jierong.share.mvp.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.jierong.share.mvp.model.IAdvListModel;
import com.jierong.share.mvp.model.impl.AdvListModel;
import com.jierong.share.mvp.model.info.AdvInfo;
import com.jierong.share.mvp.view.IAdvListView;
import com.jierong.share.okhttp.HttpStringCallBack;
import java.util.List;

/**
 * 获取广告列表
 */
public class AdvListPresenter extends BasePresenter {
    private IAdvListView mIAdvListView;
    private IAdvListModel mIAdvListModel;
    private Context mContext;

    public AdvListPresenter(IAdvListView view) {
        this.mIAdvListView = view;
        this.mIAdvListModel = new AdvListModel();
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
    public void getAdvList() {
        if(!isNetworkConnected()) {
            mIAdvListView.showError("网络连接错误，请检查网络!", false);
            return;
        }
        mIAdvListView.showLoading();
        mIAdvListModel.getAdvList(getGlobalId(), getGlobalToken(),
                mIAdvListView.getTid(), new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                mIAdvListView.getDataSuccess((List<AdvInfo>) result);
                mIAdvListView.hideLoading();
            }

            @Override
            public void onFailure(int code, String msg) {
                mIAdvListView.showError(msg, false);
                mIAdvListView.hideLoading();
            }
        });
    }

    /**
     * 点赞
     */
    public void doZan() {
        if(!isNetworkConnected()) {
            mIAdvListView.showError("网络连接错误，请检查网络!", false);
            return;
        }
        mIAdvListView.showLoading();
        mIAdvListModel.doZan(getGlobalId(), getGlobalToken(),
                mIAdvListView.getAid(), new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        mIAdvListView.hideLoading();
                        mIAdvListView.doZanSuccess();
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        mIAdvListView.showError(msg, false);
                        mIAdvListView.hideLoading();
                    }
                });
    }

    /**
     * 关闭网络请求
     */
    public void closeHttp() {
        mIAdvListModel.closeHttp();
        mContext = null;
    }

}
