package com.jierong.share.mvp.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.jierong.share.Constants;
import com.jierong.share.mvp.model.IMeModel;
import com.jierong.share.mvp.model.impl.MeModel;
import com.jierong.share.mvp.model.info.LoginUserInfo;
import com.jierong.share.mvp.model.info.UpdateInfo;
import com.jierong.share.mvp.view.IMeView;
import com.jierong.share.okhttp.HttpFileCallBack;
import com.jierong.share.okhttp.HttpStringCallBack;
import com.jierong.share.okhttp.HttpUlCallBack;
import com.jierong.share.util.FileUtils;
import com.jierong.share.util.LogUtil;
import com.jierong.share.util.StringUtil;
import java.io.File;

/**
 * 首页管理者
 */
public class MePresenter extends BasePresenter {
    private IMeView mIMeView;
    private IMeModel mIMeModel;
    private Context mContext;
    private UpdateInfo mUpdateInfo;

    public MePresenter(IMeView view) {
        this.mIMeView = view;
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
     * 获取用户数据
     */
    public void getUserInfo() {
        if (!isNetworkConnected()) {
            mIMeView.showError("网络连接错误，请检查网络!", false);
            return;
        }
        mIMeView.showLoading();
        mIMeModel.getUserInfo(getGlobalId(), getGlobalToken(), new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                mIMeView.hideLoading();
                mIMeView.receiveUser((LoginUserInfo) result);
            }

            @Override
            public void onFailure(int code, String msg) {
                mIMeView.showError(msg, false);
                mIMeView.hideLoading();
            }
        });
    }

    /**
     * 获取余额
     */
    public void getUMoney() {
        if (!isNetworkConnected()) {
            mIMeView.showError("网络连接错误，请检查网络!", false);
            return;
        }
        mIMeModel.getUserMoney(getGlobalId(), getGlobalToken(), new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                mIMeView.getUMoney(String.valueOf(result));
            }

            @Override
            public void onFailure(int code, String msg) {
                LogUtil.e(code + "_" + msg);
            }
        });
    }

    /**
     * 获取公告数据
     */
    public void getGg() {
        if (!isNetworkConnected()) {
            mIMeView.showError("网络连接错误，请检查网络!", false);
            return;
        }
        mIMeModel.getGg(getGlobalId(), getGlobalToken(), new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                mIMeView.getGgSuccess(String.valueOf(result));
            }

            @Override
            public void onFailure(int code, String msg) {
                LogUtil.e(code + "_" + msg);
                // mIMeView.showError(msg, false);
            }
        });
    }

    /**
     * 上传邀请码
     */
    public void upYqm() {
        if (!isNetworkConnected()) {
            mIMeView.showError("网络连接错误，请检查网络!", false);
            return;
        }
        String yqm = mIMeView.getYqm();
        if (StringUtil.isEmptyIgnoreBlank(yqm)) {
            mIMeView.showError("邀请码信息不能为空", false);
            return;
        }

        mIMeView.showLoading();
        mIMeModel.upYqm(getGlobalId(), getGlobalToken(), yqm, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                mIMeView.hideLoading();
                mIMeView.upYqmSuccess();
            }

            @Override
            public void onFailure(int code, String msg) {
                mIMeView.showError(msg, false);
                mIMeView.hideLoading();
            }
        });
    }

    /**
     * 完善用户头像
     */
    public void uploadUic() {
        String uicPath = mIMeView.getUicPath();
        if (StringUtil.isEmptyIgnoreBlank(uicPath)) {
            return;
        }
        final File uic = new File(uicPath);
        FileUtils fu = new FileUtils();
        String targetPath = fu.getSD_Path() + Constants.New_User_Ic +
                System.currentTimeMillis() + ".jpg";
////        // 首先过滤一下目录，如果有图片，就遍历删除
//        File dir = new File(fu.getSD_Path() + Constants.New_User_Ic);
//        if (dir.exists()) fu.deleteAllFiles(dir);
////        // 压缩宽高值，根据实际情况来定
////        String compressImage = fu.compressImage(uic.getPath(), targetPath, 480, 200, 30);
        final File compressedPic = new File(targetPath);
        mIMeView.showLoading();
        if (uic.exists()) {
            LogUtil.d("compress to up " + uic);
            mIMeModel.uploadUic(getGlobalId(), getGlobalToken(), uic, new HttpUlCallBack() {
                @Override
                public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                }

                @Override
                public void onSuccess() {
                    mIMeView.hideLoading();
                    mIMeView.changeUicSuccess(uic);
                }

                @Override
                public void onFailure() {
                    mIMeView.showError("头像上传失败!", false);
                    mIMeView.hideLoading();
                }
            });
        } else { // 直接上传
            LogUtil.d("- to up");
            mIMeModel.uploadUic(getGlobalId(), getGlobalToken(), uic, new HttpUlCallBack() {
                @Override
                public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                }

                @Override
                public void onSuccess() {
                    mIMeView.hideLoading();
                    mIMeView.changeUicSuccess(uic);
                }

                @Override
                public void onFailure() {
                    mIMeView.showError("头像上传失败!", false);
                    mIMeView.hideLoading();
                }
            });
        }
    }

    /**
     * 更新用户数据
     */
    public void refreshUserInfo() {
        if (!isNetworkConnected()) {
            mIMeView.showError("网络连接错误，请检查网络!", false);
            return;
        }
        mIMeModel.getUserInfo(getGlobalId(), getGlobalToken(), new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                mIMeView.receiveUser((LoginUserInfo) result);
            }

            @Override
            public void onFailure(int code, String msg) {
                mIMeView.showError(msg, false);
            }
        });
    }

    /**
     * 检查更新
     */
    public void checkUpdate() {
        mIMeModel.checkUpdate(getGlobalId(), getGlobalToken(), mIMeView.getVersion(), new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                mUpdateInfo = UpdateInfo.fromJSON(String.valueOf(result));
                mIMeView.showSetUpda(mUpdateInfo.getDesc());
            }

            @Override
            public void onFailure(int code, String msg) {
                if (msg.equals("已是最新版本")) {
                    mIMeView.showError(msg, false);
                }
            }
        });
    }

    /**
     * 下载APK
     */
    public void downNewApk() {
        FileUtils fu = new FileUtils();
        String path = fu.getSD_Path() + Constants.New_Down_Path;

        mIMeModel.downNewApk(getGlobalId(), getGlobalToken(), path, Constants.New_Apk_Name, mUpdateInfo.getPath(),
                new HttpFileCallBack() {
                    @Override
                    public void onSuccess(File file) {
                        // 下载完成则进行安装
                        mIMeView.installAPK(file);
                    }

                    @Override
                    public void onError(Exception e) {
                        // 下载失败
                        e.printStackTrace();
                        mIMeView.showError("安装包下载失败!", false);
                    }

                    @Override
                    public void inProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                        // 下载进度
                        mIMeView.showProgress(progress, currentSize, totalSize);
                    }
                });
    }

    /**
     * 更新操作决定结果
     * @param flag true则表示客户端要更新，false则表示客户端不更新
     */
    public void finishCheckUpdate(boolean flag) {
        if (flag) {
            LogUtil.d("开始更新下载");
            mIMeView.showDownloadDialog();
        }
    }

    /**
     * 执行退出
     */
    public void doLogout() {
        mIMeModel.logout(getGlobalId(), new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                mIMeView.logoutSuccess();
            }

            @Override
            public void onFailure(int code, String msg) {
                mIMeView.showError(msg, false);
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
