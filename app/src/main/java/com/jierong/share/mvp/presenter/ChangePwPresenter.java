package com.jierong.share.mvp.presenter;

import android.content.Context;
import com.jierong.share.mvp.model.IChangePwModel;
import com.jierong.share.mvp.model.impl.ChangePwModel;
import com.jierong.share.mvp.view.IChangePwView;
import com.jierong.share.okhttp.HttpStringCallBack;
import com.jierong.share.util.StringUtil;
import java.io.UnsupportedEncodingException;

/**
 * 修改手机密码模块
 */
public class ChangePwPresenter extends BasePresenter {
    private IChangePwView mIChangePwView;
    private IChangePwModel mIChangePwModel;


    public ChangePwPresenter(IChangePwView view) {
        this.mIChangePwView = view;
        this.mIChangePwModel = new ChangePwModel();
    }

    /**
     * 获取验证码
     */
    public void change() {
        final String oldPw = mIChangePwView.getOldPw();
        final String newPw = mIChangePwView.getNewPw();
        final String twoPw = mIChangePwView.getTwoPw();
        if(StringUtil.isEmptyIgnoreBlank(oldPw)) {
            mIChangePwView.showError("请输入您的旧密码!", false);
            return;
        } else if(oldPw.length() < 6 || oldPw.length() > 20) {
            mIChangePwView.showError("密码格式错误!", false);
            return;
        } else if(isCN(oldPw)) {
            mIChangePwView.showError("密码包含非法字符,请重新输入!", false);
            return;
        }

        if(StringUtil.isEmptyIgnoreBlank(newPw)) {
            mIChangePwView.showError("请输入您的新密码!", false);
            return;
        } else if(newPw.length() < 6 || newPw.length() > 20) {
            mIChangePwView.showError("密码格式错误!", false);
            return;
        } else if(isCN(newPw)) {
            mIChangePwView.showError("密码包含非法字符,请重新输入!", false);
            return;
        }

        if(StringUtil.isEmptyIgnoreBlank(twoPw)) {
            mIChangePwView.showError("请确认您的新密码!", false);
            return;
        } else if(!twoPw.equals(newPw)) {
            mIChangePwView.showError("两次输入的新密码不一致！", false);
            return;
        }
        mIChangePwView.showLoading();
        mIChangePwModel.change(getGlobalId(), getGlobalToken(), oldPw, twoPw, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                // 修改成功
                mIChangePwView.hideLoading();
                mIChangePwView.changeSuccess();
            }

            @Override
            public void onFailure(int code, String msg) {
                mIChangePwView.showError(msg, false);
                mIChangePwView.hideLoading();
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
        mIChangePwModel.closeHttp();
    }

}
