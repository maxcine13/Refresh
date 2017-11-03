package com.jierong.share.mvp.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.jierong.share.mvp.model.IMeModel;
import com.jierong.share.mvp.model.impl.MeModel;
import com.jierong.share.mvp.view.IMoneyHaveView;
import com.jierong.share.okhttp.HttpStringCallBack;
import com.jierong.share.util.LogUtil;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 我的余额管理者
 */
public class MHPresenter extends BasePresenter {
    private IMoneyHaveView mIMoneyHaveView;
    private IMeModel mIMeModel;
    private Context mContext;

    public MHPresenter(IMoneyHaveView view) {
        this.mIMoneyHaveView = view;
        this.mIMeModel = new MeModel();
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
     * 获取余额
     */
    public void getUMoney() {
        if (!isNetworkConnected()) {
            mIMoneyHaveView.showError("网络连接错误，请检查网络!", false);
            return;
        }
        mIMoneyHaveView.showLoading();
        mIMeModel.getUserMoney(getGlobalId(), getGlobalToken(), new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                mIMoneyHaveView.hideLoading();
                mIMoneyHaveView.getUMoney(String.valueOf(result));
            }

            @Override
            public void onFailure(int code, String msg) {
                mIMoneyHaveView.hideLoading();
                LogUtil.e(code + "_" + msg);
            }
        });
    }

    /**
     * 检查用户绑定支付宝状态
     */
    public void checkBindCard() {
        if (!isNetworkConnected()) {
            mIMoneyHaveView.showError("网络连接错误，请检查网络!", false);
            return;
        }
        mIMoneyHaveView.showLoading();
        mIMeModel.checkUserBindCard(getGlobalId(), getGlobalToken(), new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                mIMoneyHaveView.hideLoading();
                LogUtil.d(String.valueOf(result));
                try {
                    JSONObject all = new JSONObject(String.valueOf(result));
                    String sum = String.valueOf(all.opt("zfb"));
                    mIMoneyHaveView.getCard(sum);
                } catch (JSONException e) {
                    mIMoneyHaveView.showError("json转换异常", false);
                    mIMoneyHaveView.hideLoading();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                mIMoneyHaveView.hideLoading();
                LogUtil.e(code + "_" + msg);
                if(code == 1002) {
                    mIMoneyHaveView.turnToBind();
                }
            }
        });
    }

    /**
     * 关闭网络请求
     */
    public void closeHttp() {
        mIMeModel.closeHttp();
        mContext = null;
    }

}
