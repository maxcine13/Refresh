package com.jierong.share.mvp.view.act;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jierong.share.BaseAct;
import com.jierong.share.Constants;
import com.jierong.share.R;
import com.jierong.share.util.LogUtil;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by lovvol on 2017-06-21.
 */

public class TaobkwebActUp extends BaseAct implements View.OnClickListener {
    private WebView webView;
    private ProgressBar progressBar;
    private TextView yesNoNet;
    private int cont = 0;
    private Timer timer;
    Handler handler1 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            LogUtil.d("-----------" + msg.what);
            if (msg.what == 100) {
                timer.cancel();
                progressBar.setVisibility(View.GONE);
            } else {
                progressBar.setProgress(msg.what);
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_taobk_up);
        initview();
        timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler1.sendEmptyMessage(cont++);
            }
        }, 0, 10);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }

    public void initview() {

        webView = (WebView) findViewById(R.id.tao_webview);
        progressBar = (ProgressBar) findViewById(R.id.pb);
        yesNoNet = (TextView) findViewById(R.id.introduce);
        String i = getIntent().getStringExtra("type");
        if (getIntent().getStringExtra("type").equals("1")) {
            openWebview(Constants.Http_Api_TaoPreferential);
        }
        if (getIntent().getStringExtra("type").equals("2")) {
            openWebview(Constants.Http_Api_Allgoods);

        }
        if (getIntent().getStringExtra("type").equals("3")) {
            openWebview(Constants.Http_Api_TaoValure);

        }
        if (getIntent().getStringExtra("type").equals("4")) {
            openWebview(Constants.Http_Api_While_line_Bar_Agreement);
        }

    }

    @Override
    public void onNetNo() {
        yesNoNet.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNetOk() {
        webView.reload();
        yesNoNet.setVisibility(View.GONE);
    }

    /**
     * 加载webview的方法及设置
     */
    private void openWebview(String url) {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);//支持js
        webView.getSettings().setAppCacheEnabled(true); //开启缓存功能
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                yesNoNet.setText("我的小主人，奴家找不到网页了");
                yesNoNet.setVisibility(View.VISIBLE);
                LogUtil.d("--------------------------------------------");
            }
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)//新的属性写法，必须设置api的属性
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return true;
            }
        });
        /*
        设置进度条
         */
        webView.setWebChromeClient(new WebChromeClient() {


            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                LogUtil.d("ProgressChanged++  " + newProgress);


                super.onProgressChanged(view, newProgress);
            }
        });

        /*
        webView监听返回。
         */
        webView.setOnKeyListener(new View.OnKeyListener() {

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
                    webView.goBack();
                    return true;
                }
                return false;
            }
        });

    }

    public void progresstest(final int j) {


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.e("-++++++----------------------------------");
        if (timer != null) timer.cancel();
    }


    }

