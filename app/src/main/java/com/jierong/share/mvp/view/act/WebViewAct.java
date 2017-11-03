package com.jierong.share.mvp.view.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.LinearLayout;
import com.jierong.share.R;
import com.jierong.share.util.AppPreferences;
import com.just.library.AgentWeb;

/**
 * 网页界面
 */
public class WebViewAct extends AppCompatActivity {
    private LinearLayout container;
    private AgentWeb mAgentWeb;
    private String mUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_webview);

        init();
        AppPreferences.putBoolean(WebViewAct.this, "needClearWeb", true);
    }

    private void init() {
        mUrl = getIntent().getStringExtra("Url");
        container = (LinearLayout) findViewById(R.id.container);

        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(container, new LinearLayout.LayoutParams(-1, -1))//
                .useDefaultIndicator()
                .defaultProgressBarColor()
                //.setReceivedTitleCallback(mCallback) // 标题回调
                //.setWebChromeClient(mWebChromeClient)
                //.setWebViewClient(mWebViewClient)
                //.setWebLayout(new WebLayout(this))
                .setSecutityType(AgentWeb.SecurityType.strict)
                .createAgentWeb()
                .ready()
                .go(mUrl);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAgentWeb.getWebLifeCycle().onDestroy();
    }

}
