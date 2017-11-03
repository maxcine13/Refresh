package com.jierong.share.mvp.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.jierong.share.mvp.model.ITaoBaoModel;
import com.jierong.share.mvp.model.impl.TaoBaoModel;
import com.jierong.share.mvp.model.info.TaoBaoInfo;
import com.jierong.share.mvp.view.IPzjdView;
import com.jierong.share.okhttp.HttpStringCallBack;
import java.util.List;

/**
 * 品质家电界面管理者
 */
public class PzjdListPresenter extends BasePresenter {
    private IPzjdView mIPzjdView;
    private ITaoBaoModel mITaoBaoModel;
    private Context mContext;

    public PzjdListPresenter(IPzjdView view) {
        this.mContext = view.getMContext();
        this.mIPzjdView = view;
        this.mITaoBaoModel = new TaoBaoModel();
    }

    // 判断网络是否连接
    private boolean isNetworkConnected() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager)
                mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        return mNetworkInfo != null && mNetworkInfo.isAvailable();
    }

    /**
     * 获取列表数据
     */
    public void loadData() {
        if(!isNetworkConnected()) {
            mIPzjdView.showError("网络连接错误，请检查网络!", false);
            return;
        }

        final int page = mIPzjdView.getPage();
        int sort = mIPzjdView.getSort();
        mIPzjdView.showLoading();
        mITaoBaoModel.loadPzjdData(page, sort, getGlobalId(), getGlobalToken(),
                new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                mIPzjdView.hideLoading();
                List<TaoBaoInfo> data = (List<TaoBaoInfo>) result;
                if(page == 1 ) {
                    mIPzjdView.refreshSuccess(data);
                } else {
                    mIPzjdView.loadSuccess(data);
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                mIPzjdView.showError(msg, false);
                mIPzjdView.hideLoading();
            }
        });
    }

    /**
     * 关闭网络请求
     */
    public void closeHttp() {
        mITaoBaoModel.closeHttp();
        mContext = null;
    }

}
