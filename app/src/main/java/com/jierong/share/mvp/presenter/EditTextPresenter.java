package com.jierong.share.mvp.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.jierong.share.mvp.model.IEditTextModel;
import com.jierong.share.mvp.model.impl.EditTextModel;
import com.jierong.share.mvp.model.info.TagInfo;
import com.jierong.share.mvp.view.IEditTextView;
import com.jierong.share.okhttp.HttpStringCallBack;
import com.jierong.share.util.LogUtil;
import com.jierong.share.util.StringUtil;
import java.util.List;

/**
 * 绑定手机模块
 */
public class EditTextPresenter extends BasePresenter {
    private IEditTextView mIEditTextView;
    private IEditTextModel mIEditTextModel;
    private Context mContext;

    public EditTextPresenter(IEditTextView view) {
        this.mIEditTextView = view;
        this.mIEditTextModel = new EditTextModel();
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
     * 获取标签列表
     */
    public void getTagList() {
        if(!isNetworkConnected()) {
            mIEditTextView.showError("网络连接错误，请检查网络!", false);
            return;
        }
        mIEditTextModel.getTagList(getGlobalId(), getGlobalToken(), new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                List<TagInfo> data = (List<TagInfo>) result;
                mIEditTextView.getTagSuccess(data);
            }

            @Override
            public void onFailure(int code, String msg) {
                LogUtil.e(code + msg);
                mIEditTextView.showError(msg, false);
            }
        });
    }

    /**
     * 完善名称
     */
    public void changeNameCity() {
        if(!isNetworkConnected()) {
            mIEditTextView.showError("网络连接错误，请检查网络!", false);
            return;
        }
        String name = mIEditTextView.getUName();
        String city = mIEditTextView.getUCity();
        if(StringUtil.isEmptyIgnoreBlank(name)) {
            mIEditTextView.showError("名称信息不能为空!", false);
            return;
        }
        if(StringUtil.isEmptyIgnoreBlank(city)) {
            mIEditTextView.showError("所在城市信息不能为空!", false);
            return;
        }

        mIEditTextModel.changeNameCity(getGlobalId(), getGlobalToken(), name, city, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                mIEditTextView.hideLoading();
                mIEditTextView.doSuccess();
            }

            @Override
            public void onFailure(int code, String msg) {
                mIEditTextView.showError(msg, false);
                mIEditTextView.hideLoading();
            }
        });
    }

    /**
     * 完善用户信息
     */
    public void changeNameCityTag() {
        if(!isNetworkConnected()) {
            mIEditTextView.showError("网络连接错误，请检查网络!", false);
            return;
        }
        String name = mIEditTextView.getUName();
        String city = mIEditTextView.getUCity();
        String tag = mIEditTextView.getTagList();
        if(StringUtil.isEmptyIgnoreBlank(name)) {
            mIEditTextView.showError("名称信息不能为空!", false);
            return;
        }
        if(StringUtil.isEmptyIgnoreBlank(city)) {
            mIEditTextView.showError("所在城市信息不能为空!", false);
            return;
        }

        mIEditTextView.showLoading();
        mIEditTextModel.changeNameCityTag(getGlobalId(), getGlobalToken(), name, city, tag, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                mIEditTextView.hideLoading();
                mIEditTextView.doSuccess();
            }

            @Override
            public void onFailure(int code, String msg) {
                mIEditTextView.showError(msg, false);
                mIEditTextView.hideLoading();
            }
        });
    }

    /**
     * 关闭网络请求
     */
    public void closeHttp() {
        mIEditTextModel.closeHttp();
        mContext = null;
    }

}
