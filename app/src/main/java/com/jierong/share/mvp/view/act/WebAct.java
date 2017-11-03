package com.jierong.share.mvp.view.act;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.jierong.share.R;
import com.jierong.share.util.LogUtil;
import com.jierong.share.util.ToastUtils;

/**
 * 网页界面
 */
public class WebAct extends AppCompatActivity {
    private String own = " - WebAct - ";
    public static final String Data_Title = "title";
    public static final String Data_Url = "url";
    private TextView titleBack, titleName;
    private WebView mWebView;
    private ProgressBar mProgressBar;
    private LinearLayout layout_error;
    private boolean isLoadError = false;
    private String mTitle, mUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_web);
        init();
    }

    private void initHtml(String url) {
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true); // 支持javascript
        webSettings.setDomStorageEnabled(true); // 支持使用localStorage(H5页面的支持)
        webSettings.setDatabaseEnabled(true);   // 支持数据库
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);    // 关闭WebView中缓存
        webSettings.setUseWideViewPort(true);   // 设置可以支持缩放
        webSettings.setSupportZoom(true);       // 扩大比例的缩放
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);  // 隐藏缩放按钮
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);  // 自适应屏幕
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setDefaultTextEncodingName("utf-8");
        webSettings.setLoadWithOverviewMode(true);
        mWebView.setHorizontalScrollBarEnabled(false);  // 隐藏滚动条
        mWebView.setVerticalScrollBarEnabled(false);
        // mWebView.addJavascriptInterface(new WebAppInterface(this), "Android");
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return true;
            }
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                isLoadError = true;
                if (isLoadError) {
                    layout_error.setVisibility(View.VISIBLE);
                    mWebView.setVisibility(View.GONE);
                } else {
                    layout_error.setVisibility(View.GONE);
                    mWebView.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                isLoadError = true;
                if (isLoadError) {
                    layout_error.setVisibility(View.VISIBLE);
                    mWebView.setVisibility(View.GONE);
                } else {
                    layout_error.setVisibility(View.GONE);
                    mWebView.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (isLoadError) {
                    layout_error.setVisibility(View.VISIBLE);
                    mWebView.setVisibility(View.GONE);
                } else {
                    layout_error.setVisibility(View.GONE);
                    mWebView.setVisibility(View.VISIBLE);
                }
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress >= 99) {
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    if (mProgressBar.getVisibility() == View.GONE)
                        mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setProgress(newProgress);  // 设置加载进度
                }
                super.onProgressChanged(view, newProgress);
            }
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (!TextUtils.isEmpty(title) && title.toLowerCase().contains("error")) {
                    isLoadError = true;
                    if (isLoadError) {
                        layout_error.setVisibility(View.VISIBLE);
                        mWebView.setVisibility(View.GONE);
                    } else {
                        layout_error.setVisibility(View.GONE);
                        mWebView.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        mWebView.loadUrl(url);
    }

    private void init() {
        mTitle = getIntent().getStringExtra(Data_Title);
        mUrl = getIntent().getStringExtra(Data_Url);
        LogUtil.d(own + mTitle + "_" + mUrl);
        titleBack = (TextView) findViewById(R.id.titleBack);
        titleName = (TextView) findViewById(R.id.titleName);
        mWebView = (WebView) findViewById(R.id.mWebView);
        mProgressBar = (ProgressBar) findViewById(R.id.mProgressBar);
        layout_error = (LinearLayout) findViewById(R.id.layout_error);

        titleName.setText(mTitle);
        initHtml(mUrl);

        titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        layout_error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isLoadError = false;
                mWebView.reload();
                layout_error.setVisibility(View.GONE);
            }
        });
    }

    // 设置返回键动作（防止按返回键直接退出程序)
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView.canGoBack()) {
                // 当webView不是处于第一页面时，返回上一个页面
                mWebView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    // 定义一个“接口”类
    public class WebAppInterface {
        Context mContext;

        WebAppInterface(Context c) {
            mContext = c;
        }

        @JavascriptInterface
        public void showToast(String toast) {
            ToastUtils.show(mContext, toast);
        }
    }

    @Override
    protected void onDestroy() {
        mWebView.clearCache(true);
        super.onDestroy();
    }

}
