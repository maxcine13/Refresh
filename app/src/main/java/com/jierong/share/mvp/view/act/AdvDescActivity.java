package com.jierong.share.mvp.view.act;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jierong.share.BaseAct;
import com.jierong.share.Constants;
import com.jierong.share.R;
import com.jierong.share.mvp.model.info.AdvInfo;
import com.jierong.share.mvp.model.info.MoneyInfo;
import com.jierong.share.mvp.presenter.AdvDescPresenter;
import com.jierong.share.mvp.view.IAdvDescView;
import com.jierong.share.util.LogUtil;
import com.jierong.share.util.ToastUtils;
import com.jierong.share.widget.LoadingDialog;
import com.jierong.share.widget.ScrollWebView;
import com.just.library.AgentWeb;
import com.mob.MobSDK;
import java.util.HashMap;
import cn.sharesdk.alipay.friends.Alipay;
import cn.sharesdk.alipay.moments.AlipayMoments;
import cn.sharesdk.dingding.friends.Dingding;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.OnekeyShareTheme;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * 广告详情界面
 */
public class AdvDescActivity extends BaseAct implements IAdvDescView, View.OnClickListener {
    private Dialog mLoadingDialog;
    private TextView titleName;
    private FrameLayout errorView;
    private AdvDescPresenter mAdvDescPresenter;
    private String tid, tName; // 广告分类id, 广告分类名称
    private String aid, aType;
    private LinearLayout titleMenu;
    private ImageView share_red;
    private AdvInfo advInfo;//分享数据
    private boolean netState = false;//记录此页面的网络状态
    private LinearLayout container;
    private AgentWeb mAgentWeb;
    private ScrollWebView mScrollWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_adv_desc);
        // 分享初始化
        MobSDK.init(getApplicationContext());

        init();
    }

    @Override
    public void onNetNo() {
        netState = true;
    }

    @Override
    public void onNetOk() {
        if (netState) {
            netState = false;
            mAdvDescPresenter.getAdvDesc();
        }
    }

    private void init() {
        mAdvDescPresenter = new AdvDescPresenter(this);
        tid = getIntent().getStringExtra("AdvTypeId");
        tName = getIntent().getStringExtra("AdvTypeName");
        aid = getIntent().getStringExtra("Aid");
        titleName = (TextView) findViewById(R.id.titleName);
        titleMenu = (LinearLayout) findViewById(R.id.titleMenu);
        errorView = (FrameLayout) findViewById(R.id.errorView);
        container = (LinearLayout) findViewById(R.id.container);
        share_red = (ImageView) findViewById(R.id.share_red);

        Glide.with(getMContext())
                .load(R.drawable.ic_share_red)
                .asGif()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(share_red);
        titleName.setText(null);
        titleMenu.setOnClickListener(this);
        titleMenu.setVisibility(View.VISIBLE);
        findViewById(R.id.titleBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!netState) {
                    Intent intent = new Intent(AdvDescActivity.this, AdvListActivity.class);
                    intent.putExtra("AdvTypeId", tid);
                    intent.putExtra("AdvTypeName", advInfo.getTitle());
                    startActivity(intent);
                }
                finish();
            }
        });
        mAdvDescPresenter.getAdvDesc();
    }

    // 执行分享操作
    private void doShare(final String title, final String desc, final String img, final String url) {
        if (!isNetworkConnected()) {
            ToastUtils.show(AdvDescActivity.this, "网络连接错误，请检查网络!");
            netState = true;
            return;
        }
        OnekeyShare oks = new OnekeyShare();
        oks.setTheme(OnekeyShareTheme.CLASSIC);
        oks.disableSSOWhenAuthorize();
        oks.setTitle(title);
        oks.setText(desc);
        oks.setImageUrl(img);
        oks.setUrl(url);
        oks.setTitleUrl(url);

        if(!advInfo.isQq()) oks.addHiddenPlatform(QQ.NAME);
        if(!advInfo.isQqZone()) oks.addHiddenPlatform(QZone.NAME);
        if(!advInfo.isWx()) oks.addHiddenPlatform(Wechat.NAME);
        if(!advInfo.isWxCircle()) oks.addHiddenPlatform(WechatMoments.NAME);
        if(!advInfo.isWb()) oks.addHiddenPlatform(SinaWeibo.NAME);
        if(!advInfo.isDingding()) oks.addHiddenPlatform(Dingding.NAME);
        if(!advInfo.isAlipayMoments()) oks.addHiddenPlatform(Alipay.NAME);
        if(!advInfo.isAllpaylifecircle()) oks.addHiddenPlatform(AlipayMoments.NAME);

        oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
            @Override
            public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
                LogUtil.e("setShare" + platform.getName());
                if (SinaWeibo.NAME.equals(platform.getName())) {
                    paramsToShare.setText(desc + " " + url + "&tongdao=" + 4);
                    paramsToShare.setUrl(null);
                    return;
                } else if (QQ.NAME.equals(platform.getName())) {
                    paramsToShare.setTitleUrl(url + "&tongdao=" + 3);
                    paramsToShare.setUrl(url + "&tongdao=" + 3);
                } else if (QZone.NAME.equals(platform.getName())) {
                    paramsToShare.setTitleUrl(url + "&tongdao=" + 2);
                    paramsToShare.setUrl(url + "&tongdao=" + 2);
                    LogUtil.d("setUrl" + url);
                } else if (Wechat.NAME.equals(platform.getName())) {
                    paramsToShare.setTitleUrl(url + "&tongdao=" + 5);
                    paramsToShare.setUrl(url + "&tongdao=" + 5);
                } else if (WechatMoments.NAME.equals(platform.getName())) {
                    paramsToShare.setTitleUrl(url + "&tongdao=" + 1);
                    paramsToShare.setUrl(url + "&tongdao=" + 1);
                } else if (Dingding.NAME.equals(platform.getName())) {
                    LogUtil.d("setShareurl" + url);
                    paramsToShare.setTitleUrl(url + "&tongdao=" + 6);
                    paramsToShare.setUrl(url + "&tongdao=" + 6);
                } else if (AlipayMoments.NAME.equals(platform.getName())) {
                    LogUtil.d("setShareurl" + url + "platform.getName()" + platform.getName());
                    paramsToShare.setTitleUrl(url + "&tongdao=" + 8);
                    paramsToShare.setUrl(url + "&tongdao=" + 8);
                } else if (Alipay.NAME.equals(platform.getName())) {
                    LogUtil.d("setShareurl" + url + "platform.getName()" + platform.getName());
                    //后台要求，支付宝好友、朋友圈用同一个通道。不要问我微信、微信朋友圈、QQ、QQ控件为什么分开了。
                    paramsToShare.setTitleUrl(url + "&tongdao=" + 7);
                    paramsToShare.setUrl(url + "&tongdao=" + 7);
                }
            }
        });

        oks.setCallback(new PlatformActionListener() {
            @Override
            public void onComplete(final Platform platform, int i, HashMap<String, Object> hashMap) {
                LogUtil.d("callback" + platform.getName());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (SinaWeibo.NAME.equals(platform.getName())) {
                            aType = "wb";
                            ToastUtils.show(AdvDescActivity.this, "新浪微博分享已完成");
                        } else if (QQ.NAME.equals(platform.getName())) {
                            aType = "qq";
                            ToastUtils.show(AdvDescActivity.this, "QQ聊天分享已完成");
                        } else if (QZone.NAME.equals(platform.getName())) {
                            aType = "qqZone";
                            ToastUtils.show(AdvDescActivity.this, "QQ空间分享已完成");
                        } else if (Wechat.NAME.equals(platform.getName())) {
                            aType = "wx";
                            ToastUtils.show(AdvDescActivity.this, "微信聊天分享已完成");
                        } else if (WechatMoments.NAME.equals(platform.getName())) {
                            aType = "wxCircle";
                            ToastUtils.show(AdvDescActivity.this, "微信朋友圈分享已完成");
                        } else if (Dingding.NAME.equals(platform.getName())) {
                            aType = "Dingding";
                            ToastUtils.show(AdvDescActivity.this, "钉钉分享已完成");
                        } else if (AlipayMoments.NAME.equals(platform.getName())) {
                            aType = "Allpaylifecircle";
                            ToastUtils.show(AdvDescActivity.this, "支付宝生活圈分享已完成");
                        } else if (Alipay.NAME.equals(platform.getName())) {
                            aType = "AlipayMoments";
                            ToastUtils.show(AdvDescActivity.this, "支付宝好友分享已完成");
                        }
                        mAdvDescPresenter.getShareMoney();
                    }
                });
            }

            @Override
            public void onError(Platform platform, int i, final Throwable throwable) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.show(AdvDescActivity.this, "分享失败");
                        LogUtil.e("" + throwable);
                    }
                });
            }

            @Override
            public void onCancel(Platform platform, int i) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.show(AdvDescActivity.this, "分享已取消");
                    }
                });
            }
        });

        oks.show(AdvDescActivity.this);
    }

    @Override
    public Context getMContext() {
        return AdvDescActivity.this;
    }

    @Override
    public void showLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = LoadingDialog.createLoadingDialog(AdvDescActivity.this, "正在加载中...");
            mLoadingDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }

    @Override
    public String getAid() {
        return aid;
    }

    @Override
    public String getAType() {
        return aType;
    }

    @Override
    public void getMoneySuccess(MoneyInfo info) {
        Intent intent = new Intent();
        //intent.putExtra("Num", info.getNum());
        intent.setAction(Constants.Refresh_User_Center);
        sendBroadcast(intent);

        // 刷新一下数据
        mAdvDescPresenter.getAdvDesc();
    }

    @Override
    public void getDataSuccess(AdvInfo data) {
        advInfo = data;
        titleName.setText(data.getTitle());
        AdvDescWeb layout = new AdvDescWeb(this);
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(container, new LinearLayout.LayoutParams(-1, -1))//
                .useDefaultIndicator()
                .defaultProgressBarColor()
                //.setReceivedTitleCallback(mCallback) // 标题回调
                //.setWebChromeClient(mWebChromeClient)
                //.setWebViewClient(mWebViewClient)
                .setWebLayout(layout)
                .setSecutityType(AgentWeb.SecurityType.strict)
                .createAgentWeb()
                .ready()
                .go(advInfo.getContentUrl());

        mScrollWebView = layout.getWeb();
        mScrollWebView.setOnScrollChangeListener(new ScrollWebView.OnScrollChangeListener() {
            @Override
            public void onScrollChanged(int l, int t, int oldl, int oldt) { }

            @Override
            public void onChangedEnd() {
                doShare(tName, advInfo.getShareDesc(), advInfo.getShareImg(), advInfo.getLink());
            }

            @Override
            public void onPageTop(int l, int t, int oldl, int oldt) {
                // 滑动到顶部
            }

            @Override
            public void onPageEnd(int l, int t, int oldl, int oldt) { }
        });
    }

    @Override
    public void showError(String msg, boolean flag) {
        errorView.setVisibility(View.VISIBLE);
        ToastUtils.show(this, msg);
        if (flag) finish();
    }

    @Override
    public void onBackPressed() {
        if (!netState) {
            Intent intent = new Intent(AdvDescActivity.this, AdvListActivity.class);
            intent.putExtra("AdvTypeId", tid);
            intent.putExtra("AdvTypeName", advInfo.getTitle());
            startActivity(intent);
        }
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.titleMenu:
                if (isNetworkConnected()) {//如果没有网络的时候不允许分享
                    doShare(tName, advInfo.getShareDesc(), advInfo.getShareImg(), advInfo.getLink());
                } else {
                    ToastUtils.show(AdvDescActivity.this, "网络连接错误，请检查网络!");
                }
                break;
        }
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
        if(null != mAgentWeb) {
            mAgentWeb.getWebLifeCycle().onPause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        if(null != mAgentWeb) {
            mAgentWeb.getWebLifeCycle().onResume();
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        hideLoading();
        mAdvDescPresenter.closeHttp();
        if(null != mAgentWeb) {
            mAgentWeb.getWebLifeCycle().onDestroy();
        }
        super.onDestroy();
    }

}
