package com.jierong.share.mvp.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.jierong.share.mvp.model.IAdvModel;
import com.jierong.share.mvp.model.impl.AdvModel;
import com.jierong.share.mvp.model.info.AdvTypeInfo;
import com.jierong.share.mvp.view.IAdvView;
import com.jierong.share.okhttp.HttpStringCallBack;
import java.util.List;

/**
 * 获取fragment中的广告分类
 */
public class AdvPresenter extends BasePresenter {
    private IAdvView mIAdvView;
    private IAdvModel mAdvModel;
    private Context mContext;

    public AdvPresenter(IAdvView view) {
        this.mIAdvView = view;
        this.mAdvModel = new AdvModel();
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
     * 获取分享赚钱 广告分类列表数据
     */
    public void getAdvListData() {
        if (!isNetworkConnected()) {
            mIAdvView.showError("网络连接错误，请检查网络!", false);
            return;
        }
        mIAdvView.showLoading();
        mAdvModel.getAdvListData(getGlobalId(), getGlobalToken(), new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                mIAdvView.hideLoading();
                List<AdvTypeInfo> info = (List<AdvTypeInfo>) result;
                mIAdvView.receiveAdvData(info);
            }

            @Override
            public void onFailure(int code, String msg) {
                mIAdvView.showError(msg, false);
                mIAdvView.hideLoading();
            }
        });
    }

    /**
     * 关闭网络请求
     */
    public void closeHttp() {
        mAdvModel.closeHttp();
        mContext = null;
    }

}
