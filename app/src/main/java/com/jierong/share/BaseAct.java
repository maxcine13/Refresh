package com.jierong.share;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.jierong.share.mvp.view.act.TopAct;
import com.jierong.share.okhttp.BaseHttpUtil;
import com.jierong.share.util.AppPreferences;
import com.jierong.share.util.NetUtil;

public abstract class BaseAct extends AppCompatActivity {
    private static final int Net_Ok = 101;  // 联网的标记
    private static final int Net_No = 102;  // 断网的标记
    private static final int User_Logout = 201;  // 强退的标记

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Net_Ok:
                    onNetOk();
                    break;
                case Net_No:
                    onNetNo();
                    break;
                case User_Logout:
                    doLogout();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppManager.getAppManager().addActivity(this);
        init();
    }

    BroadcastReceiver baseReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                int netWorkState = NetUtil.getNetWorkState(context);
                if (netWorkState == NetUtil.NETWORK_NONE) {
                    mHandler.sendEmptyMessage(Net_No);
                } else {
                    mHandler.sendEmptyMessage(Net_Ok);
                }
            } else if (action.equals(Constants.Push_Action_Logout)) {
                mHandler.sendEmptyMessage(User_Logout);
            }
        }
    };

    private void init() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(Constants.Push_Action_Logout);
        registerReceiver(baseReceiver, filter);
    }

    public abstract void onNetNo();

    public abstract void onNetOk();

    // 处理退出的逻辑
    private void doLogout() {
        Intent intent = new Intent(this, TopAct.class);
        startActivity(intent);
    }

    /**
     * 判断网络是否连接
     *
     * @return
     */
    protected boolean isNetworkConnected() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        return mNetworkInfo != null && mNetworkInfo.isAvailable();
    }

    /**
     * 获取App安装包信息
     *
     * @return
     */
    protected PackageInfo getPackageInfo() {
        PackageInfo info = null;
        try {
            info = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace(System.err);
        }
        if (info == null) info = new PackageInfo();
        return info;
    }

    /**
     * 获取进程名称
     *
     * @param context
     * @return
     */
    protected String getCurProcessName(Context context) {
        try {
            int pid = android.os.Process.myPid();
            ActivityManager mActivityManager = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager
                    .getRunningAppProcesses()) {
                if (appProcess.pid == pid) {
                    return appProcess.processName;
                }
            }
        } catch (Exception e) {
            // ignore
        }
        return null;
    }

    protected void closeNet(BaseHttpUtil baseHttpUtil) {
        if (baseHttpUtil != null) {
            baseHttpUtil.closeHttp();
        }
    }

    /**
     * 获取全局变量uid
     */
    protected String getGlobalId() {
        String result;
        result = AppPreferences.getString(BaseApp.getContext(), Constants.PNK_UId, null);
        return result;
    }

    /**
     * 获取全局变量token
     */
    protected String getGlobalToken() {
        String result;
        result = AppPreferences.getString(BaseApp.getContext(), Constants.PNK_UToken, null);
        return result;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!BaseApp.getInstance().isBackground()) {
            NotificationManager nm = (NotificationManager) BaseApp.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
            nm.cancel(10);
        }
    }

    @Override
    protected void onDestroy() {
        if (baseReceiver != null) unregisterReceiver(baseReceiver);
        AppManager.getAppManager().finishActivity(this);
        super.onDestroy();
    }
}
