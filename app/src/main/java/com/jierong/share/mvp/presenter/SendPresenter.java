package com.jierong.share.mvp.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.jierong.share.mvp.model.ISendModel;
import com.jierong.share.mvp.model.impl.SendModel;
import com.jierong.share.mvp.model.info.AdvTypeInfo;
import com.jierong.share.mvp.view.ISendView;
import com.jierong.share.okhttp.HttpStringCallBack;
import com.jierong.share.util.StringUtil;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;

/**
 * 首页管理者
 */
public class SendPresenter extends BasePresenter {
    private ISendView mISendView;
    private ISendModel mISendModel;
    private Context mContext;

    public SendPresenter(ISendView view) {
        this.mISendView = view;
        this.mISendModel = new SendModel();
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
     * 获取广告可发送分类
     */
    public void getSendType() {
        if (!isNetworkConnected()) {
            mISendView.showError("网络连接错误，请检查网络!", false);
            return;
        }

        mISendView.showLoading();
        mISendModel.getAdvType(getGlobalId(), getGlobalToken(), new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                try {
                    JSONObject all = new JSONObject(String.valueOf(result));
                    List<AdvTypeInfo> advTypeInfos = AdvTypeInfo.fromJSONS(String.valueOf(all.opt("advType")));
                    mISendView.hideLoading();
                    mISendView.receiveType(advTypeInfos);
                } catch (JSONException e) {
                    mISendView.showError("json转换异常", false);
                    mISendView.hideLoading();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                mISendView.showError(msg, false);
                mISendView.hideLoading();
            }
        });
    }

    public void doSend() {
        if (!isNetworkConnected()) {
            mISendView.showError("网络连接错误，请检查网络!", false);
            return;
        }
        String name = mISendView.getUName();
        String tel = mISendView.getUTel();
        String type = mISendView.getAdvType();
        String desc = mISendView.getAdvDesc();
        if (StringUtil.isEmptyIgnoreBlank(name)) {
            mISendView.showError("姓名不能为空!", false);
            return;
        }
        if (StringUtil.isEmptyIgnoreBlank(tel)) {
            mISendView.showError("手机号码不能为空!", false);
            return;
        } else if (!StringUtil.isMobile(tel)) {
            mISendView.showError("手机号码格式错误!", false);
            return;
        }
        if (StringUtil.isEmptyIgnoreBlank(desc)) {
            mISendView.showError("广告需求不能为空!", false);
            return;
        }

        mISendView.showLoading();
        mISendModel.sendAdv(getGlobalId(), getGlobalToken(), name, tel, type, desc, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                mISendView.hideLoading();
                mISendView.sendSuccess();
            }

            @Override
            public void onFailure(int code, String msg) {
                mISendView.showError(msg, false);
                mISendView.hideLoading();
            }
        });
    }

    /**
     * 关闭网络请求
     */
    public void closeHttp() {
        mISendModel.closeHttp();
        mContext = null;
    }

}
