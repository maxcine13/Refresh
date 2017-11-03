package com.jierong.share.mvp.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.jierong.share.mvp.model.ITaoBaoModel;
import com.jierong.share.mvp.model.impl.TaoBaoModel;
import com.jierong.share.mvp.model.info.TaoBaoInfo;
import com.jierong.share.mvp.view.IBkflView;
import com.jierong.share.okhttp.HttpStringCallBack;
import java.util.List;

/**
 * 爆款返利界面管理者
 */
public class BkflListPresenter extends BasePresenter {
    private IBkflView mIBkflView;
    private ITaoBaoModel mITaoBaoModel;
    private Context mContext;

    public BkflListPresenter(IBkflView view) {
        this.mContext = view.getMContext();
        this.mIBkflView = view;
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
            mIBkflView.showError("网络连接错误，请检查网络!", false);
            return;
        }

        final int page = mIBkflView.getPage();
        int sort = mIBkflView.getSort();
        String category = mIBkflView.getCategory();
        mIBkflView.showLoading();
        mITaoBaoModel.loadBkflData(category, page, sort, getGlobalId(), getGlobalToken(),
                new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                mIBkflView.hideLoading();
                List<TaoBaoInfo> data = (List<TaoBaoInfo>) result;
                if(page == 1 ) {
                    mIBkflView.refreshSuccess(data);
                } else {
                    mIBkflView.loadSuccess(data);
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                mIBkflView.showError(msg, false);
                mIBkflView.hideLoading();
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
