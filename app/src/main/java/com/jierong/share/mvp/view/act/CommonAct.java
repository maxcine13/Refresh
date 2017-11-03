package com.jierong.share.mvp.view.act;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jierong.share.BaseAct;
import com.jierong.share.Constants;
import com.jierong.share.R;
import com.jierong.share.util.LogUtil;
import com.jierong.share.widget.LoadingDialog;


/**
 * Created by Administrator on 2017-05-02.
 */

public class CommonAct extends BaseAct {
    private String While_line_information = Constants.Http_Api_While_line_information;
    private WebView webView;
    private Dialog mLoadingDialog;
    private String url;
    private boolean netState = false;
    private RelativeLayout net_error;
    private TextView net_error_tips;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_common);
        initView();

        String common = getIntent().getStringExtra("common");
        if (common.equals(Constants.WhileLineDetaile)) {
            ((TextView) findViewById(R.id.titleName)).setText(R.string.details);
            url = Constants.Http_Api_While_line_information;
        }
        if (common.equals(Constants.Activation)) {
            ((TextView) findViewById(R.id.titleName)).setText(R.string.details);
            url = Constants.Http_Api_While_line_Bar_Agreement;
        }
        if (common.equals(Constants.RegisterActivity)) {
            ((TextView) findViewById(R.id.titleName)).setText(R.string.register_agreement_txt);
            url = Constants.Http_Api_UserArgreement;
        }
        if (common.equals(Constants.IncomeRule)) {
            ((TextView) findViewById(R.id.titleName)).setText(R.string.incomerule);
            url = Constants.Http_Api_InComeRule;
        }
        if (common.equals(Constants.GetMoney)) {
            ((TextView) findViewById(R.id.titleName)).setText(R.string.me_money_get);
            url = Constants.Http_Api_GetMoney;
        }
        if (common.equals(Constants.HelperCenter)) {
            ((TextView) findViewById(R.id.titleName)).setText(R.string.me_help_center);
            url = Constants.Http_Api_HelperCenter;
        }
        if (common.equals(Constants.Authen)) {
            ((TextView) findViewById(R.id.titleName)).setText(R.string.auth_tips);
            url = Constants.Http_Api_Drxz;
        }
        if (common.equals(Constants.Income)) {
            ((TextView) findViewById(R.id.titleName)).setText(R.string.me_money_think);
            url = Constants.Http_Api_IncomeGetUrl;
        }
        if (common.equals(Constants.Customer)) {
            ((TextView) findViewById(R.id.titleName)).setText(R.string.Customer);
            url = Constants.Http_Api_customer;
        }
        initData();

    }

    public void initView() {
        findViewById(R.id.titleBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        webView = (WebView) findViewById(R.id.common);
        net_error = (RelativeLayout) findViewById(R.id.net_error);
        net_error_tips = (TextView) findViewById(R.id.net_error_tips);
        net_error_tips.setText("没有网络了");

    }

    private void initData() {
        if (!isNetworkConnected()) {
            net_error.setVisibility(View.VISIBLE);
            webView.setVisibility(View.GONE);
            return;
        }

        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (mLoadingDialog == null) {
                    mLoadingDialog = LoadingDialog.createLoadingDialog(CommonAct.this, "正在加载中...");
                    mLoadingDialog.show();
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (mLoadingDialog != null) {
                    mLoadingDialog.dismiss();
                    mLoadingDialog = null;
                }
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);


            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
                LogUtil.d("onReceivedHttpError");
            }
        });
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
    }

    @Override
    public void onNetOk() {
        net_error.setVisibility(View.GONE);
        webView.setVisibility(View.VISIBLE);
        if (netState) {//此处保证只会调用一次
            initData();
            netState = false;
        }


    }

    @Override
    public void onNetNo() {
        net_error.setVisibility(View.VISIBLE);
        webView.setVisibility(View.GONE);
        netState = true;

    }
}
