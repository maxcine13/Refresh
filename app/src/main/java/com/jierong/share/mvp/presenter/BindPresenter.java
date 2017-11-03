package com.jierong.share.mvp.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.jierong.share.mvp.model.IBindModel;
import com.jierong.share.mvp.model.impl.BindModel;
import com.jierong.share.mvp.view.IBindView;
import com.jierong.share.okhttp.HttpStringCallBack;
import com.jierong.share.util.StringUtil;
import java.io.UnsupportedEncodingException;

/**
 * 绑定手机模块
 */
public class BindPresenter extends BasePresenter {
    private IBindView mIBindView;
    private IBindModel mIBindModel;
    private Context mContext;

    public BindPresenter(IBindView view) {
        this.mIBindView = view;
        this.mIBindModel = new BindModel();
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
     * 获取验证码
     */
    public void getKey() {
        if(!isNetworkConnected()) {
            mIBindView.showError("网络连接错误，请检查网络!", false);
            return;
        }

        final String tel = mIBindView.getUTel();
        if(StringUtil.isEmptyIgnoreBlank(tel)) {
            mIBindView.showError("手机号码不能为空!", false);
            return;
        } else if(!StringUtil.isMobile(tel)) {
            mIBindView.showError("手机号码格式错误!", false);
            return;
        }

        mIBindView.showLoading();
        mIBindModel.getKey(tel, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                // 获取成功
                mIBindView.hideLoading();
                mIBindView.getKeySuccess();
            }

            @Override
            public void onFailure(int code, String msg) {
                mIBindView.showError(msg, false);
                mIBindView.hideLoading();
            }
        });
    }

    /**
     * 执行绑定手机号码
     */
    public void bind() {
        if(!isNetworkConnected()) {
            mIBindView.showError("网络连接错误，请检查网络!", false);
            return;
        }

        final String tel = mIBindView.getUTel();
        final String key = mIBindView.getUKey();
        if(StringUtil.isEmptyIgnoreBlank(tel)) {
            mIBindView.showError("手机号码不能为空!", false);
            return;
        } else if(!StringUtil.isMobile(tel)) {
            mIBindView.showError("手机号码格式错误!", false);
            return;
        }

        if(StringUtil.isEmptyIgnoreBlank(key)) {
            mIBindView.showError("验证码不能为空!", false);
            return;
        } else if(!(key.length() == 4 || key.length() == 6)) {
            mIBindView.showError("请您填写有效的验证码!", false);
            return;
        }

        mIBindView.showLoading();
        mIBindModel.bind(getGlobalId(), tel, key, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                mIBindView.bindSuccess();
                mIBindView.hideLoading();
            }

            @Override
            public void onFailure(int code, String msg) {
                mIBindView.showError(msg, false);
                mIBindView.hideLoading();
            }
        });
    }

    // 是否包含中文
    private boolean isCN(String str){
        try {
            byte [] bytes = str.getBytes("UTF-8");
            if(bytes.length == str.length()){
                return false;
            }else{
                return true;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 关闭网络请求
     */
    public void closeHttp() {
        mIBindModel.closeHttp();
        mContext = null;
    }

}
