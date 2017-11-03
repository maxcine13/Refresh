package com.jierong.share;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;

public abstract class BaseFrag extends Fragment {

    public abstract void onNetNo();

    public abstract void onNetOk();

    /**
     * 判断网络是否连接
     *
     * @return
     */
    protected boolean isNetworkConnected() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        return mNetworkInfo != null && mNetworkInfo.isAvailable();
    }

}
