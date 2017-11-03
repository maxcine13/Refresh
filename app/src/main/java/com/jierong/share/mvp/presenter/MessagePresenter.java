package com.jierong.share.mvp.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.jierong.share.mvp.model.IMessageModel;
import com.jierong.share.mvp.model.impl.MessageModel;
import com.jierong.share.mvp.model.info.MessageInfo;
import com.jierong.share.mvp.view.IMessageView;
import com.jierong.share.okhttp.HttpStringCallBack;
import com.jierong.share.util.LogUtil;
import java.util.List;

/**
 * 我的消息管理者
 */
public class MessagePresenter extends BasePresenter {
    private IMessageView mIMessageView;
    private IMessageModel mIMessageModel;
    private Context mContext;

    public MessagePresenter(IMessageView view) {
        this.mContext = view.getMContext();
        this.mIMessageView = view;
        this.mIMessageModel = new MessageModel();
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
            mIMessageView.showError("网络连接错误，请检查网络!", false, flag);
            return;
        }

        int page = mIMessageView.getPage();
        mIMessageView.showLoading();
        mIMessageModel.getMessageList(page, getGlobalId(), getGlobalToken(), new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                mIMessageView.hideLoading();
                List<MessageInfo> data = (List<MessageInfo>) result;
                if(flag) mIMessageView.refreshSuccess(data);
                else mIMessageView.loadSuccess(data);
            }

            @Override
            public void onFailure(int code, String msg) {
                LogUtil.e(code + " : " + msg);
                mIMessageView.showError(msg, false, flag);
                mIMessageView.hideLoading();
            }
        });
    }

    /**
     * 关闭网络请求
     */
    public void closeHttp() {
        mIMessageModel.closeHttp();
        mContext = null;
    }

}
