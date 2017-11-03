package com.jierong.share.mvp.view.act;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jierong.share.R;
import com.jierong.share.widget.ScrollWebView;
import com.just.library.IWebLayout;

public class AdvDescWeb implements IWebLayout {
    private Activity mActivity;
    private View rootView;
    private ScrollWebView mWebView = null;

    public AdvDescWeb(Activity activity) {
        this.mActivity = activity;
        rootView = LayoutInflater.from(activity).inflate(R.layout.view_adv_web, null);
        mWebView = (ScrollWebView) rootView.findViewById(R.id.adView);
    }


    @NonNull
    @Override
    public ViewGroup getLayout() {
        return (ViewGroup) rootView;
    }

    @Nullable
    @Override
    public ScrollWebView getWeb() {
        return mWebView;
    }

}
