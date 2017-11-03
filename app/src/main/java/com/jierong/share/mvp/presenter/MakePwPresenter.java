package com.jierong.share.mvp.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.jierong.share.mvp.model.IMakePwModel;
import com.jierong.share.mvp.model.impl.MakePwModel;
import com.jierong.share.mvp.view.IMakePwView;
import com.jierong.share.okhttp.HttpStringCallBack;
import com.jierong.share.util.StringUtil;
import java.io.UnsupportedEncodingException;

/**
 * 设置密码模块
 */
public class MakePwPresenter extends BasePresenter {
    private IMakePwView mIMakePwView;
    private IMakePwModel mIMakePwModel;
    private Context mContext;

    public MakePwPresenter(IMakePwView view) {
        this.mIMakePwView = view;
        this.mIMakePwModel = new MakePwModel();
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
     * 设置密码
     */
    public void makePw() {
        if(!isNetworkConnected()) {
            mIMakePwView.showError("网络连接错误，请检查网络!", false);
            return;
        }

        String tel = mIMakePwView.getUTel();
        if(StringUtil.isEmptyIgnoreBlank(tel)) {
            mIMakePwView.showError("手机号不能为空!", false);
            return;
        }

        String pw = mIMakePwView.getUPw();
        if(StringUtil.isEmptyIgnoreBlank(pw)) {
            mIMakePwView.showError("密码不能为空!", false);
            return;
        }
        if(isCN(pw)) {
            mIMakePwView.showError("密码包含非法字符!", false);
            return;
        }
        if(pw.length() < 6 || pw.length() > 20) {
            mIMakePwView.showError("密码格式错误,请输入6~20位数的密码!", false);
            return;
        }

        String pwTwo = mIMakePwView.getUPwTwo();
        if(StringUtil.isEmptyIgnoreBlank(pwTwo)) {
            mIMakePwView.showError("确认密码不能为空!", false);
            return;
        }
        if(!pw.equals(pwTwo)) {
            mIMakePwView.showError("两次密码输入不一致", false);
            return;
        }

        mIMakePwView.showLoading();
        mIMakePwModel.makePw(getGlobalId(), tel, pwTwo, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                mIMakePwView.hideLoading();
                mIMakePwView.makeSuccess();
            }

            @Override
            public void onFailure(int code, String msg) {
                mIMakePwView.hideLoading();
                mIMakePwView.showError(msg, false);
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
        mIMakePwModel.closeHttp();
        mContext = null;
    }

}
