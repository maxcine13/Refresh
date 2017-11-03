package com.jierong.share.mvp.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import com.jierong.share.Constants;
import com.jierong.share.mvp.model.IWelcomeModel;
import com.jierong.share.mvp.model.impl.WelcomeModel;
import com.jierong.share.mvp.model.info.AdvInfo;
import com.jierong.share.mvp.model.info.LoginUserInfo;
import com.jierong.share.mvp.view.IWelcomeView;
import com.jierong.share.okhttp.HttpStringCallBack;
import com.jierong.share.util.AppPreferences;
import com.jierong.share.util.LogUtil;
import com.jierong.share.util.ToastUtils;

/**
 * 欢迎界面管理者
 */
public class WelcomePresenter extends BasePresenter {
    private IWelcomeView mIWelcomeView;
    private IWelcomeModel mIWelcomeModel;
    private Context mContext;

    public WelcomePresenter(IWelcomeView view) {
        this.mIWelcomeView = view;
        this.mIWelcomeModel = new WelcomeModel();
        this.mContext = view.getMContext();
    }

    // 判断网络是否连接
    private boolean isNetworkConnected() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager)
                mIWelcomeView.getMContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        return mNetworkInfo != null && mNetworkInfo.isAvailable();
    }

    // 更新本地用户数据
    private void updateUser(LoginUserInfo user) {
        AppPreferences.putString(mContext, Constants.PNK_UId, user.getuId());
        AppPreferences.putString(mContext, Constants.PNK_UToken, user.getuToken());
        AppPreferences.putString(mContext, Constants.PNK_NEW, user.getNew_state());
        if(null != user.getuIc())
            AppPreferences.putString(mContext, Constants.PNK_UIc, user.getuIc());
        if(null != user.getuName())
            AppPreferences.putString(mContext, Constants.PNK_UName, user.getuName());
        if(null != user.getuTel())
            AppPreferences.putString(mContext, Constants.PNK_UTel, user.getuTel());
    }

    // 同步用户接口
    private void initUser(final String uid, final String token, final String way) {
        mIWelcomeModel.enterByToken(getImei(mContext), uid, token, null,
                way, new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        LogUtil.d("- isLogin_ok");
                        LoginUserInfo user = (LoginUserInfo) result;
                        updateUser(user);
                        if(user.getuTel() != null) {
                            mIWelcomeView.turnToMain();
                        } else {
                            mIWelcomeView.turnToBind();
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        LogUtil.d("- isLogin_err");
                        AppPreferences.clear(mContext);
                        // 如果只退出账号，则不再重新走引导页
                        AppPreferences.putBoolean(mContext, "isFirstStart", false);
                        mIWelcomeView.showError(msg, false);
                        mIWelcomeView.turnToLogin();
                    }
                });


//        final Handler mHandler = new Handler();
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                if(null == mIWelcomeView.getPushId()) {
//                    LogUtil.d("- pid_null");
//                    mIWelcomeView.refreshPushId();
//                    mHandler.postDelayed(this, 1000);
//                } else {
//                    LogUtil.d("- pid: " + mIWelcomeView.getPushId());
//                    mIWelcomeModel.enterByToken(getImei(mContext), uid, token, mIWelcomeView.getPushId(),
//                            way, new HttpStringCallBack() {
//                        @Override
//                        public void onSuccess(Object result) {
//                            LogUtil.d("- isLogin_ok");
//                            LoginUserInfo user = (LoginUserInfo) result;
//                            updateUser(user);
//                            if(user.getuTel() != null) {
//                                mIWelcomeView.turnToMain();
//                            } else {
//                                mIWelcomeView.turnToBind();
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(int code, String msg) {
//                            LogUtil.d("- isLogin_err");
//                            AppPreferences.clear(mContext);
//                            // 如果只退出账号，则不再重新走引导页
//                            AppPreferences.putBoolean(mContext, "isFirstStart", false);
//                            mIWelcomeView.showError(msg, false);
//                            mIWelcomeView.turnToLogin();
//                        }
//                    });
//                    mHandler.removeCallbacks(this);
//                }
//            }
//        };
//        mHandler.postDelayed(runnable, 0);  // 立即执行
    }

    // 检查是否登录过
    private void checkIsLogin() {
        LogUtil.d("do_checkIsLogin");
        String uid = AppPreferences.getString(mContext, Constants.PNK_UId, "");
        String token = AppPreferences.getString(mContext, Constants.PNK_UToken, "");
        String way = AppPreferences.getString(mContext, Constants.PNK_UWay, "");
        if(uid.equals("") || token.equals("") || way.equals("")) {
            LogUtil.d("- notLogin");
            mIWelcomeView.turnToLogin();
        } else {
            LogUtil.d("- isLogin");
            if(isNetworkConnected()) {
                initUser(uid, token, way);
            } else {
                mIWelcomeView.turnToLogin();
                ToastUtils.show(mContext,"网络异常，请检查网络!");
               // mIWelcomeView.showError("网络异常，请检查网络!", true);
            }
        }
    }

    // 检查是否是第一次启动
    private void checkIsFirst() {
        LogUtil.d("do_checkIsFirst");
        if(AppPreferences.getBoolean(mContext, "isFirstStart", true)) {
            // 如果是第一次进app,则修改状态值，并跳转到引导页
            AppPreferences.putBoolean(mContext, "isFirstStart", false);
            mIWelcomeView.turnToGuide();
        } else {    // 否则
            //version = BaseApp.getInstance().getPackageInfo().versionName;
            //if(null == version) return; // 一般不会有该类情况
            //LogUtil.d("now version: " + version);
            checkIsLogin();
        }
    }

    /**
     * 做一下初始化操作
     */
    public void init() {
        if(isNetworkConnected()) {
            mIWelcomeModel.getTopAdv(new HttpStringCallBack() {
                @Override
                public void onSuccess(Object result) {
                    AdvInfo advInfo = (AdvInfo) result;
                    mIWelcomeView.showNetAdv(advInfo);
                    new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            checkIsFirst();
                        }
                    }.sendEmptyMessageDelayed(0, 1800); // 1.8秒的广告显示时间
                }

                @Override
                public void onFailure(int code, String msg) {
                    mIWelcomeView.showLocalAdv();
                    new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            checkIsFirst();
                        }
                    }.sendEmptyMessageDelayed(0, 1800); // 1.8秒的广告显示时间
                }
            });
        } else {
            // 如果无网络，则直接走本地广告流程
            mIWelcomeView.showLocalAdv();
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    checkIsFirst();
                }
            }.sendEmptyMessageDelayed(0, 1800); // 1.8秒的广告显示时间
        }
    }

    /**
     * 关闭网络请求
     */
    public void closeHttp() {
        mIWelcomeModel.closeHttp();
        mContext = null;
    }
}
