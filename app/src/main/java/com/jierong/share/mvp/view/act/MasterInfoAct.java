package com.jierong.share.mvp.view.act;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.jierong.share.BaseAct;
import com.jierong.share.BaseApp;
import com.jierong.share.Constants;
import com.jierong.share.R;
import com.jierong.share.okhttp.BaseHttpUtil;
import com.jierong.share.okhttp.HttpStringCallBack;
import com.jierong.share.util.AppPreferences;
import com.jierong.share.util.LogUtil;
import com.jierong.share.util.ToastUtils;
import com.jierong.share.widget.LoadingDialog;
import com.lzy.okgo.model.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 分享达人详情界面
 */
public class MasterInfoAct extends BaseAct implements View.OnClickListener {
    private String CLICK_LOVE = "1";//收藏
    private String CLICK_FABULOUS = "2";//点赞
    private WebView webView;
    private Dialog mLoadingDialog;
    private RelativeLayout rela_love, rela_fabulous, rela_comment;
    private TextView text_love, text_fabulous;
    private LinearLayout line_btn;
    private String ULink, ULike, UCollection, talent_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_master_info);
        initView();
    }

    private void initView() {
        ((TextView) findViewById(R.id.titleName)).setText(R.string.master_info);
        findViewById(R.id.titleBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        rela_comment = (RelativeLayout) findViewById(R.id.rela_comment);
        rela_fabulous = (RelativeLayout) findViewById(R.id.rela_fabulous);
        rela_love = (RelativeLayout) findViewById(R.id.rela_love);
        text_love = (TextView) findViewById(R.id.text_love);
        text_fabulous = (TextView) findViewById(R.id.text_fabulous);
        line_btn = (LinearLayout) findViewById(R.id.line_btn);
        webView = (WebView) findViewById(R.id.common);
        rela_comment.setOnClickListener(this);
        rela_fabulous.setOnClickListener(this);
        rela_love.setOnClickListener(this);
        getData();
    }

    /**
     * 获取上一个页面的传值
     */
    private void getData() {
        Intent intent = getIntent();
        ULink = intent.getStringExtra("ULink");
        talent_id = intent.getStringExtra("talent_id");
        getWebview();
    }

    /**
     * webview的加载
     */
    private void getWebview() {
        if (mLoadingDialog == null) {
            mLoadingDialog = LoadingDialog.createLoadingDialog(MasterInfoAct.this, "正在加载中...");
            mLoadingDialog.show();
        }
        webView.loadUrl(ULink);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                getState(talent_id);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
        });
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
    }

    @Override
    public void onNetNo() { }

    @Override
    public void onNetOk() { }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rela_love:
                if (UCollection.equals("0")) {
                    Drawable drawable = getResources().getDrawable(R.drawable.ic_love);
                    // 这一步必须要做,否则不会显示.
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    text_love.setCompoundDrawables(drawable, null, null, null);
                    postData(CLICK_LOVE, talent_id);
                }
                break;
            case R.id.rela_fabulous:
                if (ULike.equals("0")) {
                    Drawable drawable2 = getResources().getDrawable(R.drawable.zan_ok);
                    // 这一步必须要做,否则不会显示.
                    drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
                    text_fabulous.setCompoundDrawables(drawable2, null, null, null);
                    postData(CLICK_FABULOUS, talent_id);
                }
                break;
            case R.id.rela_comment:
                Intent intent = new Intent(MasterInfoAct.this, CommentAct.class);
                intent.putExtra("talent_id", talent_id);
                startActivity(intent);
                break;
        }
    }

    /**
     * 网络请求进行收藏和点赞
     * @param flag      1是收藏   2是点赞
     * @param talent_id 达人id
     */
    private void postData(final String flag, String talent_id) {
        BaseHttpUtil httpUtil = new BaseHttpUtil();
        HttpParams httpParams = new HttpParams();
        httpParams.put("uid", getGlobalId());
        httpParams.put("token", getGlobalToken());
        httpParams.put("talent_id", talent_id);
        httpParams.put("flag", flag);
        httpUtil.doPost(Constants.Http_Api_Myclick, httpParams, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                if (flag.equals("1")) {
                    ToastUtils.show(MasterInfoAct.this, "收藏成功!");
                } else {
                    ToastUtils.show(MasterInfoAct.this, "点赞成功!");
                }

            }

            @Override
            public void onFailure(int code, String msg) {
                if (flag.equals("1")) {
                    ToastUtils.show(MasterInfoAct.this, "已收藏!");
                } else {
                    ToastUtils.show(MasterInfoAct.this, "已点赞!");
                }
            }
        });
    }

    /**
     * 获取点赞和收藏状态
     * @param talent_id 达人id
     */
    private void getState(String talent_id) {
        BaseHttpUtil httpUtil = new BaseHttpUtil();
        HttpParams httpParams = new HttpParams();
        httpParams.put("uid", getGlobalId());
        httpParams.put("token", getGlobalToken());
        httpParams.put("talent_id", talent_id);
        httpUtil.doPost(Constants.Http_Api_State, httpParams, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                LogUtil.d(result.toString());
                try {
                    JSONObject jsonObject = new JSONObject(result.toString());
                    UCollection = jsonObject.getString("uCollection");
                    ULike = jsonObject.getString("uLike");
                    if (ULike.equals("1")) {
                        Drawable drawable = getResources().getDrawable(R.drawable.zan_ok);
                        // 这一步必须要做,否则不会显示.
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        text_fabulous.setCompoundDrawables(drawable, null, null, null);
                    }
                    if (UCollection.equals("1")) {
                        Drawable drawable2 = getResources().getDrawable(R.drawable.ic_love);
                        // 这一步必须要做,否则不会显示.
                        drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
                        text_love.setCompoundDrawables(drawable2, null, null, null);
                    }

                    if (mLoadingDialog != null) {
                        mLoadingDialog.dismiss();
                        mLoadingDialog = null;
                    }
                    line_btn.setVisibility(View.VISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                    finish();
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                finish();
            }
        });
    }

}
