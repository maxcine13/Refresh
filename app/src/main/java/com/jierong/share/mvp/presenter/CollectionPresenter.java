package com.jierong.share.mvp.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.jierong.share.mvp.model.ICollectionModel;
import com.jierong.share.mvp.model.impl.CollectionModel;
import com.jierong.share.mvp.view.ICollectionView;
import com.jierong.share.okhttp.HttpStringCallBack;

/**
 * 我的收藏页面的管理者
 */
public class CollectionPresenter extends BasePresenter {
    private ICollectionView mICollectionView;
    private ICollectionModel mICollectionModel;
    private Context context;

    public CollectionPresenter(ICollectionView iCollectionView) {
        this.mICollectionModel = new CollectionModel();
        this.mICollectionView = iCollectionView;
        this.context = iCollectionView.getMContext();
    }

    // 判断网络是否连接
    private boolean isNetworkConnected() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        return mNetworkInfo != null && mNetworkInfo.isAvailable();
    }

    /**
     * 获取收藏列表
     */
    public void getCollectionData() {
        if (!isNetworkConnected()) {
            mICollectionView.showError("网络连接错误，请检查网络!", false);
            return;
        }
        mICollectionView.showLoading();
        mICollectionModel.getCollectionData(getGlobalId(), getGlobalToken(), new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                mICollectionView.hideLoading();
                mICollectionView.receiveData(result.toString());
            }

            @Override
            public void onFailure(int code, String msg) {
                mICollectionView.hideLoading();
                mICollectionView.showError(msg, false);
            }
        });
    }

    /**
     * 取消收藏
     */
    public void doCancel() {
        if (!isNetworkConnected()) {
            mICollectionView.showError("网络连接错误，请检查网络!", false);
            return;
        }
        mICollectionView.showLoading();
        mICollectionModel.doCancel(getGlobalId(), getGlobalToken(), mICollectionView.getTid(), new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                mICollectionView.hideLoading();
                mICollectionView.doCancelSuccess();
            }

            @Override
            public void onFailure(int code, String msg) {
                mICollectionView.hideLoading();
                mICollectionView.showError(msg, false);
            }
        });
    }

    /**
     * 关闭网络请求
     */
    public void closeHttp() {
        mICollectionModel.closeHttp();
        context = null;
    }

}
