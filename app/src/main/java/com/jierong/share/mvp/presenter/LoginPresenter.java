package com.jierong.share.mvp.presenter;

import android.content.Context;
import android.os.Handler;
import com.jierong.share.Constants;
import com.jierong.share.mvp.model.ILoginModel;
import com.jierong.share.mvp.model.impl.LoginModel;
import com.jierong.share.mvp.model.info.LoginUserInfo;
import com.jierong.share.mvp.view.ILoginView;
import com.jierong.share.okhttp.HttpStringCallBack;
import com.jierong.share.util.AppPreferences;
import com.jierong.share.util.LogUtil;
import com.jierong.share.util.StringUtil;

/**
 * 登录管理者
 */
public class LoginPresenter extends BasePresenter {
    private ILoginView mILoginView;
    private ILoginModel mILoginModel;
    private Context mContext;

    public LoginPresenter(ILoginView view) {
        this.mILoginView = view;
        this.mILoginModel = new LoginModel();
        this.mContext = view.getMContext();
    }

    /**
     * 通过用户名 + 密码的方式登录
     */
    public void loginByPass() {
        //String pid = PushManager.getInstance().getClientid(mContext);
        final String name = mILoginView.getUName();
        final String pass = mILoginView.getUPass();
        if (StringUtil.isEmptyIgnoreBlank(name)) {
            mILoginView.showError("请填写您的手机号!", false);
            return;
        }
        if (StringUtil.isEmptyIgnoreBlank(pass)) {
            mILoginView.showError("请填写您的登录密码!", false);
            return;
        }

        mILoginView.showLoading();
        mILoginModel.loginByPass(getImei(mContext), mILoginView.getPushId(), mILoginView.getUName(),
                mILoginView.getUPass(), new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        LoginUserInfo info = (LoginUserInfo) result;
                        LogUtil.d("login by Tel：" + info.toString());
                        saveOrUpdateUser(info);
                        AppPreferences.putString(mContext, Constants.PNK_UWay, info.getWay());
                        mILoginView.hideLoading();
                        if (info.getuTel() != null) {
                            mILoginView.turnToMain();
                        } else {
                            mILoginView.turnToBind();
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        mILoginView.hideLoading();
                        mILoginView.showError(msg, false);
                    }
                });


//        final Handler mHandler = new Handler();
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                if (null == mILoginView.getPushId()) {
//                    LogUtil.e("- pid_null");
//                    mILoginView.refreshPushId();
//                    mHandler.postDelayed(this, 1000);
//                } else {
//                    LogUtil.e("- pid: " + mILoginView.getPushId());
//                    // getImei(mContext)
//                    mILoginModel.loginByPass(getImei(mContext), mILoginView.getPushId(), mILoginView.getUName(),
//                            mILoginView.getUPass(), new HttpStringCallBack() {
//                                @Override
//                                public void onSuccess(Object result) {
//                                    LoginUserInfo info = (LoginUserInfo) result;
//                                    LogUtil.d("login by Tel：" + info.toString());
//                                    saveOrUpdateUser(info);
//                                    AppPreferences.putString(mContext, Constants.PNK_UWay, "phone");
//                                    mILoginView.hideLoading();
//                                    if (info.getuTel() != null) {
//                                        mILoginView.turnToMain();
//                                    } else {
//                                        mILoginView.turnToBind();
//                                    }
//                                }
//
//                                @Override
//                                public void onFailure(int code, String msg) {
//                                    mILoginView.hideLoading();
//                                    mILoginView.showError(msg, false);
//                                }
//                            });
//                    mHandler.removeCallbacks(this);
//                }
//            }
//        };
//        mHandler.postDelayed(runnable, 0);  // 立即执行
    }

    /**
     * 执行QQ登录
     *
     * @param id
     * @param token
     */
    public void loginByQQ(final String id, final String token) {
        mILoginView.showLoading();
        mILoginModel.loginByQQ(getImei(mContext), mILoginView.getPushId(), id, token, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                LoginUserInfo info = (LoginUserInfo) result;
                LogUtil.d("login by QQ：" + info.toString());
                saveOrUpdateUser(info);
                AppPreferences.putString(mContext, Constants.PNK_UWay, info.getWay());
                mILoginView.hideLoading();
                if (info.getuTel() != null) {
                    mILoginView.turnToMain();
                } else {
                    mILoginView.turnToBind();
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                mILoginView.hideLoading();
                mILoginView.showError(msg, false);
            }
        });


        //String pid = PushManager.getInstance().getClientid(mContext);
//        mILoginView.showLoading();
//        final Handler mHandler = new Handler();
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                if (null == mILoginView.getPushId()) {
//                    LogUtil.e("- pid_null");
//                    mILoginView.refreshPushId();
//                    mHandler.postDelayed(this, 1000);
//                } else {
//                    LogUtil.e("- pid: " + mILoginView.getPushId());
//                    mILoginModel.loginByQQ(getImei(mContext), mILoginView.getPushId(), id, token, new HttpStringCallBack() {
//                        @Override
//                        public void onSuccess(Object result) {
//                            LoginUserInfo info = (LoginUserInfo) result;
//                            LogUtil.d("login by QQ：" + info.toString());
//                            saveOrUpdateUser(info);
//                            AppPreferences.putString(mContext, Constants.PNK_UWay, "qq");
//                            mILoginView.hideLoading();
//                            if (info.getuTel() != null) {
//                                mILoginView.turnToMain();
//                            } else {
//                                mILoginView.turnToBind();
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(int code, String msg) {
//                            mILoginView.hideLoading();
//                            mILoginView.showError(msg, false);
//                        }
//                    });
//                    mHandler.removeCallbacks(this);
//                }
//            }
//        };
//        mHandler.postDelayed(runnable, 0);  // 立即执行
    }

    /**
     * 执行新浪微博登录
     *
     * @param id
     * @param token
     */
    public void loginByWB(final String id, final String token) {
        mILoginView.showLoading();
        mILoginModel.loginByWB(getImei(mContext), mILoginView.getPushId(), id, token, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                LoginUserInfo info = (LoginUserInfo) result;
                LogUtil.d("login by WB：" + info.toString());
                saveOrUpdateUser(info);
                AppPreferences.putString(mContext, Constants.PNK_UWay, info.getWay());
                mILoginView.hideLoading();
                if (info.getuTel() != null) {
                    mILoginView.turnToMain();
                } else {
                    mILoginView.turnToBind();
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                mILoginView.hideLoading();
                mILoginView.showError(msg, false);
            }
        });


        //String pid = PushManager.getInstance().getClientid(mContext);
//        final Handler mHandler = new Handler();
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                if (null == mILoginView.getPushId()) {
//                    LogUtil.e("- pid_null");
//                    mILoginView.refreshPushId();
//                    mHandler.postDelayed(this, 1000);
//                } else {
//                    LogUtil.e("- pid: " + mILoginView.getPushId());
//                    mILoginModel.loginByWB(getImei(mContext), mILoginView.getPushId(), id, token, new HttpStringCallBack() {
//                        @Override
//                        public void onSuccess(Object result) {
//                            LoginUserInfo info = (LoginUserInfo) result;
//                            LogUtil.d("login by WB：" + info.toString());
//                            saveOrUpdateUser(info);
//                            AppPreferences.putString(mContext, Constants.PNK_UWay, "weibo");
//                            mILoginView.hideLoading();
//                            if (info.getuTel() != null) {
//                                mILoginView.turnToMain();
//                            } else {
//                                mILoginView.turnToBind();
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(int code, String msg) {
//                            mILoginView.hideLoading();
//                            mILoginView.showError(msg, false);
//                        }
//                    });
//                    mHandler.removeCallbacks(this);
//                }
//            }
//        };
//        mHandler.postDelayed(runnable, 0);  // 立即执行
    }

    /**
     * 执行微信登录
     *
     * @param id
     * @param token
     */
    public void loginByWX(final String id, final String token) {
        mILoginView.showLoading();
        mILoginModel.loginByWX(getImei(mContext), mILoginView.getPushId(), id, token, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                LoginUserInfo info = (LoginUserInfo) result;
                LogUtil.d("login by WB：" + info.toString());
                saveOrUpdateUser(info);
                AppPreferences.putString(mContext, Constants.PNK_UWay, info.getWay());
                mILoginView.hideLoading();
                if (info.getuTel() != null) {
                    mILoginView.turnToMain();
                } else {
                    mILoginView.turnToBind();
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                mILoginView.hideLoading();
                mILoginView.showError(msg, false);
            }
        });


        //String pid = PushManager.getInstance().getClientid(mContext);
//        mILoginView.showLoading();
//        final Handler mHandler = new Handler();
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                if (null == mILoginView.getPushId()) {
//                    LogUtil.e("- pid_null");
//                    mILoginView.refreshPushId();
//                    mHandler.postDelayed(this, 1000);
//                } else {
//                    LogUtil.e("- pid: " + mILoginView.getPushId());
//                    mILoginModel.loginByWX(getImei(mContext), mILoginView.getPushId(), id, token, new HttpStringCallBack() {
//                        @Override
//                        public void onSuccess(Object result) {
//                            LoginUserInfo info = (LoginUserInfo) result;
//                            LogUtil.d("login by WB：" + info.toString());
//                            saveOrUpdateUser(info);
//                            AppPreferences.putString(mContext, Constants.PNK_UWay, "weixin");
//                            mILoginView.hideLoading();
//                            if (info.getuTel() != null) {
//                                mILoginView.turnToMain();
//                            } else {
//                                mILoginView.turnToBind();
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(int code, String msg) {
//                            mILoginView.hideLoading();
//                            mILoginView.showError(msg, false);
//                        }
//                    });
//                    mHandler.removeCallbacks(this);
//                }
//            }
//        };
//        mHandler.postDelayed(runnable, 0);  // 立即执行
    }

    // 更新本地用户数据
    private void saveOrUpdateUser(LoginUserInfo user) {
        AppPreferences.putString(mContext, Constants.PNK_UId, user.getuId());
        AppPreferences.putString(mContext, Constants.PNK_UToken, user.getuToken());
        AppPreferences.putString(mContext, Constants.PNK_NEW, user.getNew_state());
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
        mILoginModel.closeHttp();
        mContext = null;
    }

}
