package com.jierong.share.mvp.presenter;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jierong.share.Constants;
import com.jierong.share.mvp.model.IHomeModel;
import com.jierong.share.mvp.model.impl.HomeModel;
import com.jierong.share.mvp.model.info.AdvInfo;
import com.jierong.share.mvp.model.info.AdvTypeInfo;
import com.jierong.share.mvp.model.info.BkInfo;
import com.jierong.share.mvp.model.info.UpdateInfo;
import com.jierong.share.mvp.view.IHomeView;
import com.jierong.share.okhttp.HttpFileCallBack;
import com.jierong.share.okhttp.HttpStringCallBack;
import com.jierong.share.util.AppPreferences;
import com.jierong.share.util.FileUtils;
import com.jierong.share.util.LogUtil;
import com.jierong.share.util.StringUtil;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.util.List;

/**
 * 首页管理者
 */
public class HomePresenter extends BasePresenter {
    private IHomeView mIHomeView;
    private IHomeModel mIHomeModel;
    private Context mContext;
    private UpdateInfo mUpdateInfo;

    public HomePresenter(IHomeView view) {
        this.mContext = view.getMContext();
        this.mIHomeView = view;
        this.mIHomeModel = new HomeModel();
    }

    // 判断网络是否连接
    private boolean isNetworkConnected() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager)
                mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        return mNetworkInfo != null && mNetworkInfo.isAvailable();
    }

    /**
     * 上传定位城市
     */
    public void uploadCity() {
        if (!isNetworkConnected()) {
            mIHomeView.showError("网络连接错误，请检查网络!", false);
            return;
        }
        final String city = mIHomeView.getCity();
        if (StringUtil.isEmptyIgnoreBlank(city)) {
            mIHomeView.showError("位置信息为空！", false);
            return;
        }

        mIHomeModel.uploadCity(getGlobalId(), getGlobalToken(), city, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                // 修改成功
                mIHomeView.uploadCitySuccess();
            }

            @Override
            public void onFailure(int code, String msg) {
                mIHomeView.showError(msg, false);
            }
        });
    }

    /**
     * 告知服务端
     */
    public void telServer() {
        if (!isNetworkConnected()) {
            mIHomeView.showError("网络连接错误，请检查网络!", false);
            return;
        }
        mIHomeModel.telServer(getGlobalId(), getGlobalToken(), new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                LogUtil.d("tel success");
            }

            @Override
            public void onFailure(int code, String msg) {
                LogUtil.e(msg);
            }
        });
    }

    /**
     * 获取首页数据
     */
    public void getHomeData() {
        if (!isNetworkConnected()) {
            mIHomeView.showError("网络连接错误，请检查网络!", false);
            return;
        }
        mIHomeView.showLoading();
        mIHomeModel.getHomeData(getGlobalId(), getGlobalToken(), new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                try {
                    JSONObject all = new JSONObject(String.valueOf(result));
                    List<AdvInfo> advInfos = AdvInfo.fromJSONS(String.valueOf(all.opt("topAdv")));
                    List<AdvTypeInfo> advTypeInfos = AdvTypeInfo.fromJSONS(String.valueOf(all.opt("advType")));
                    mIHomeView.hideLoading();
                    mIHomeView.receiveHomeData(advInfos, advTypeInfos);
                } catch (JSONException e) {
                    mIHomeView.showError("json转换异常", false);
                    mIHomeView.hideLoading();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                mIHomeView.showError(msg, false);
                mIHomeView.hideLoading();
            }
        });
    }

    /**
     * 获取爆款数据
     */
    public void getBk() {
        if (!isNetworkConnected()) {
            mIHomeView.showError("网络连接错误，请检查网络!", false);
            return;
        }
        mIHomeModel.getBkData(getGlobalId(), getGlobalToken(), new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                List<BkInfo> bkInfos = (List<BkInfo>) result;
                mIHomeView.receiveBkData(bkInfos);
            }

            @Override
            public void onFailure(int code, String msg) {
                mIHomeView.showError(msg, false);
                mIHomeView.hideLoading();
            }
        });
    }

    /**
     * 获取公告数据
     */
    public void getGg() {
        if (!isNetworkConnected()) {
            mIHomeView.showError("网络连接错误，请检查网络!", false);
            return;
        }
        mIHomeModel.getGg(getGlobalId(), getGlobalToken(), new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                mIHomeView.getGgSuccess(String.valueOf(result));
            }

            @Override
            public void onFailure(int code, String msg) {
                LogUtil.e(code + "_" + msg);
                // mIMeView.showError(msg, false);
            }
        });
    }

    private PackageInfo getPackageInfo() {
        PackageInfo info = null;
        try {
            info = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace(System.err);
        }
        if (info == null) info = new PackageInfo();
        return info;
    }

    /**
     * 检查更新
     */
    public void checkUpdate() {
        mIHomeModel.checkUpdate(getGlobalId(), getGlobalToken(), getPackageInfo().versionCode, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                mUpdateInfo = UpdateInfo.fromJSON(String.valueOf(result));
                LogUtil.d(mUpdateInfo.toString());
                mIHomeView.showSetUpdate(mUpdateInfo.getDesc());
            }

            @Override
            public void onFailure(int code, String msg) {
                if(code == 101) {
                    // 已是最新版本, 执行下边的操作
                    String new_state = AppPreferences.getString(mContext, Constants.PNK_NEW, null);
                    if (null != new_state && new_state.equals("1")) getmoney();
                } else {
                    // 其他错误码，则视为请求接口失败
                    mIHomeView.showError(msg, false);
                }
            }
        });
    }

    /**
     * 更新操作决定结果
     * @param flag  true则表示客户端要更新，false则表示客户端不更新
     */
    public void finishCheckUpdate(boolean flag) {
        if(flag) {
            LogUtil.d("开始更新下载");
            mIHomeView.showDownloadDialog();
        } else {
            LogUtil.d("忽略本次更新");
            mIHomeView.showError("请先更新安装包", true);
        }
    }

    /**
     * 下载APK
     */
    public void downNewApk() {
        FileUtils fu = new FileUtils();
        String path = fu.getSD_Path() + Constants.New_Down_Path;
        mIHomeModel.downNewApk(getGlobalId(), getGlobalToken(), path, Constants.New_Apk_Name, mUpdateInfo.getPath(),
                new HttpFileCallBack() {
                    @Override
                    public void onSuccess(File file) {
                        // 下载完成则进行安装
                        mIHomeView.installAPK(file);
                    }

                    @Override
                    public void onError(Exception e) {
                        // 下载失败
                        e.printStackTrace();
                        mIHomeView.showError("安装包下载失败!", true);
                    }

                    @Override
                    public void inProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                        // 下载进度
                        mIHomeView.showProgress(progress, currentSize, totalSize);
                    }
                });
    }

    /**
     * 检查新人状态，弹出新人大礼包的dialog
     */
    public void getmoney() {
        if (!isNetworkConnected()) {
            mIHomeView.showError("网络连接错误，请检查网络!", false);
            return;
        }

        mIHomeView.showLoading();
        mIHomeModel.getShowGift(getGlobalId(), getGlobalToken(), new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                mIHomeView.hideLoading();
                LogUtil.d(String.valueOf(result));
                mIHomeView.showGift(String.valueOf(result));
            }

            @Override
            public void onFailure(int code, String msg) {
                // 新人礼包接口查询失败
                LogUtil.e(msg);
                mIHomeView.hideLoading();
            }
        });
    }

    /**
     * 签到获得收益由接口返回值。做到签到收益可控。
     */
    public void getSign() {
        if (!isNetworkConnected()) {
            mIHomeView.showError("网络连接错误，请检查网络!", false);
            return;
        }
        mIHomeView.showLoading();
        mIHomeModel.getSign(getGlobalId(), getGlobalToken(), new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                mIHomeView.hideLoading();
                JsonObject jsonObject = new JsonParser().parse(result.toString()).getAsJsonObject();
                String money = jsonObject.get("money").getAsString();
                mIHomeView.receiveSign(money, true);
            }

            @Override
            public void onFailure(int code, String msg) {
                mIHomeView.receiveSign(msg, false);
                mIHomeView.hideLoading();
            }
        });
    }

    /**
     * 关闭网络请求
     */
    public void closeHttp(boolean flag) {
        mIHomeModel.closeHttp();
        if(flag) mContext = null;
    }

}
