package com.jierong.share.mvp.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.jierong.share.Constants;
import com.jierong.share.mvp.model.IRegisterModel;
import com.jierong.share.mvp.model.impl.RegisterModel;
import com.jierong.share.mvp.model.info.RegistUserInfo;
import com.jierong.share.mvp.view.IRegisterView;
import com.jierong.share.okhttp.HttpStringCallBack;
import com.jierong.share.util.AppPreferences;
import com.jierong.share.util.StringUtil;
import java.io.UnsupportedEncodingException;

/**
 * 注册手机模块
 */
public class RegisterPresenter extends BasePresenter {
    private IRegisterView mIRegisterView;
    private IRegisterModel mIRegisterModel;
    private Context mContext;

    public RegisterPresenter(IRegisterView view) {
        this.mIRegisterView = view;
        this.mIRegisterModel = new RegisterModel();
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
        if (!isNetworkConnected()) {
            mIRegisterView.showError("网络连接错误，请检查网络!", false);
            return;
        }

        final String tel = mIRegisterView.getUTel();
        if (StringUtil.isEmptyIgnoreBlank(tel)) {
            mIRegisterView.showError("请填写您的手机号码!", false);
            return;
        } else if (!StringUtil.isMobile(tel)) {
            mIRegisterView.showError("手机号码格式错误!", false);
            return;
        }

        mIRegisterView.showLoading();
        mIRegisterModel.getKey(tel, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                // 获取成功
                mIRegisterView.hideLoading();
                mIRegisterView.getKeySuccess();
            }

            @Override
            public void onFailure(int code, String msg) {
                mIRegisterView.showError(msg, false);
                mIRegisterView.hideLoading();
            }
        });
    }

    /**
     * 执行绑定手机号码
     */
    public void register(String sign) {
        if (!isNetworkConnected()) {
            mIRegisterView.showError("网络连接错误，请检查网络!", false);
            return;
        }
        final String tel = mIRegisterView.getUTel();
        final String key = mIRegisterView.getUKey();
        final String pass = mIRegisterView.getUPw();
        final String verify = mIRegisterView.getSignNum();

        if (StringUtil.isEmptyIgnoreBlank(tel)) {
            mIRegisterView.showError("请填写您的手机号码!", false);
            return;
        } else if (!StringUtil.isMobile(tel)) {
            mIRegisterView.showError("手机号码格式错误!", false);
            return;
        }

        if (StringUtil.isEmptyIgnoreBlank(key)) {
            mIRegisterView.showError("请填写您的验证码!", false);
            return;
        } else if (!(key.length() == 4 || key.length() == 6)) {
            mIRegisterView.showError("请您填写有效的验证码!", false);
            return;
        }

        if (StringUtil.isEmptyIgnoreBlank(pass)) {
            mIRegisterView.showError("请设置您的登录密码!", false);
            return;
        } else if (pass.length() < 6 || pass.length() > 20) {
            mIRegisterView.showError("请填写6-20位的密码!", false);
            return;
        } else if (isCN(pass)) {
            mIRegisterView.showError("密码包含非法字符,请重新输入!", false);
            return;
        }

        if (StringUtil.isEmptyIgnoreBlank(verify)) {
            mIRegisterView.showError("请填写您的识别码!", false);
            return;
        } else if (verify.length() != 4) {
            mIRegisterView.showError("请填写4位有效识别码!", false);
            return;
        } else if (!verify.equals(sign)) {
            mIRegisterView.showError("识别码错误，请重新输入!", false);
            return;
        }

        mIRegisterView.showLoading();
        mIRegisterModel.register(tel, key, pass, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                mIRegisterView.hideLoading();
                RegistUserInfo info = RegistUserInfo.fromJSON(result.toString());
                saveOrUpdateUser(info);
                mIRegisterView.registerSuccess();
            }

            @Override
            public void onFailure(int code, String msg) {
                mIRegisterView.showError(msg, false);
                mIRegisterView.hideLoading();
            }
        });
    }

    // 是否包含中文
    private boolean isCN(String str) {
        try {
            byte[] bytes = str.getBytes("UTF-8");
            if (bytes.length == str.length()) {
                return false;
            } else {
                return true;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 更新本地用户数据
    private void saveOrUpdateUser(RegistUserInfo user) {
        AppPreferences.putString(mContext, Constants.PNK_UId, user.getuId());
        AppPreferences.putString(mContext, Constants.PNK_UToken, user.getuToken());
        if (null != user.getuIc())
            AppPreferences.putString(mContext, Constants.PNK_UIc, user.getuIc());
        if (null != user.getuName())
            AppPreferences.putString(mContext, Constants.PNK_UName, user.getuName());
        if (null != user.getuTel())
            AppPreferences.putString(mContext, Constants.PNK_UTel, user.getuTel());
    }

    /**
     * 关闭网络请求
     */
    public void closeHttp() {
        mIRegisterModel.closeHttp();
        mContext = null;
    }

}
