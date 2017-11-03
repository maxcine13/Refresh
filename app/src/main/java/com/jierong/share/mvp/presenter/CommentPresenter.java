package com.jierong.share.mvp.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.jierong.share.mvp.model.ICommentModel;
import com.jierong.share.mvp.model.impl.CommentModel;
import com.jierong.share.mvp.view.ICommentView;
import com.jierong.share.okhttp.HttpStringCallBack;
import com.jierong.share.util.StringUtil;

/**
 * 评论列表管理者
 */
public class CommentPresenter extends BasePresenter {
    private ICommentView mICommentView;
    private ICommentModel mICommentModel;
    private Context mContext;

    public CommentPresenter(ICommentView view) {
        this.mICommentView = view;
        this.mICommentModel=new CommentModel();
        this.mContext = view.getMContext();
    }

    // 判断网络是否连接
    private boolean isNetworkConnected() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager)
                mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        return mNetworkInfo != null && mNetworkInfo.isAvailable();
    }

    //获取分享达人页面评论的数据
    public void getMasterData(String talent_id) {
        if (!isNetworkConnected()) {
            mICommentView.showError("网络连接错误，请检查网络!", false);
            return;
        }
        mICommentView.showLoading();
        mICommentModel.getMasterData(getGlobalId(), getGlobalToken(), talent_id, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
               mICommentView.receiveData(result.toString());
                mICommentView.hideLoading();
            }

            @Override
            public void onFailure(int code, String msg) {
                mICommentView.showError(msg, false);
                mICommentView.hideLoading();
            }
        });


    }

    /**
     * 提交用户评论
     * @param talent_id  评论人的id
     */
    public void sendComment(String talent_id){
        if (!isNetworkConnected()) {
            mICommentView.showError("网络连接错误，请检查网络!", false);
            return;
        }

        String content=mICommentView.getContent();
        if (!StringUtil.isNotEmptyIgnoreBlank(content)) {
            mICommentView.showError("评论内容不能为空!", false);
            return;
        }
        mICommentView.showLoading();
        mICommentModel.sendComment(getGlobalId(), getGlobalToken(), talent_id,content, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                mICommentView.sendSuccess();
                mICommentView.hideLoading();
            }

            @Override
            public void onFailure(int code, String msg) {
                mICommentView.showError(msg, false);
                mICommentView.hideLoading();
            }
        });
    }




    /**
     * 关闭网络请求
     */
    public void closeHttp() {
        mICommentModel.closeHttp();
        mContext = null;
    }

}
