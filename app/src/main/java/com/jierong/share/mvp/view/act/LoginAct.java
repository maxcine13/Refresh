package com.jierong.share.mvp.view.act;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.igexin.sdk.PushManager;
import com.jierong.share.Constants;
import com.jierong.share.R;
import com.jierong.share.mvp.presenter.LoginPresenter;
import com.jierong.share.mvp.view.ILoginView;
import com.jierong.share.service.MgtIntentService;
import com.jierong.share.service.MgtService;
import com.jierong.share.util.LogUtil;
import com.jierong.share.util.ToastUtils;
import com.jierong.share.widget.LoadingDialog;
import com.mob.MobSDK;
import java.util.HashMap;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * 登录界面
 */
public class LoginAct extends AppCompatActivity implements
        ILoginView, View.OnClickListener {
    private EditText input_name, input_pass;
    private TextView submit, register, forget;
    private ImageView look_mm, bt_wb, bt_qq, bt_wx;
    private Dialog mLoadingDialog;
    private LoginPresenter mLoginPresenter;
    private String pushId;

    final int Login_By_Wb = 101;
    final int Login_By_Wx = 102;
    final int Login_By_QQ = 103;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // LogUtil.d(((Platform) msg.obj).getDb().exportData());
            switch (msg.what) {
                case Login_By_Wb:
                    mLoginPresenter.loginByWB(((Platform) msg.obj).getDb().getUserId(),
                            ((Platform) msg.obj).getDb().getToken());
                    break;
                case Login_By_Wx:
                    mLoginPresenter.loginByWX(((Platform) msg.obj).getDb().getUserId(),
                            ((Platform) msg.obj).getDb().getToken());
                    break;
                case Login_By_QQ:
                    mLoginPresenter.loginByQQ(((Platform) msg.obj).getDb().getUserId(),
                            ((Platform) msg.obj).getDb().getToken());
                    break;
            }
        }
    };

    BroadcastReceiver pushReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Constants.Push_Action_GetId)) {
                LogUtil.d("Act_login - pr");
                pushId = intent.getStringExtra("Pid");
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);

        // 注册推送ID获取成功的广播
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.Push_Action_GetId);
        registerReceiver(pushReceiver, filter);
        // 分享初始化
        MobSDK.init(getApplicationContext());
        init();
    }

    private void init() {
        mLoginPresenter = new LoginPresenter(this);
        input_name = (EditText) findViewById(R.id.input_name);
        input_pass = (EditText) findViewById(R.id.input_pass);
        forget = (TextView) findViewById(R.id.forget);
        submit = (TextView) findViewById(R.id.submit);
        register = (TextView) findViewById(R.id.register);
        look_mm = (ImageView) findViewById(R.id.look_mm);
        bt_wb = (ImageView) findViewById(R.id.bt_wb);
        bt_qq = (ImageView) findViewById(R.id.bt_qq);
        bt_wx = (ImageView) findViewById(R.id.bt_wx);

        forget.setOnClickListener(this);
        submit.setOnClickListener(this);
        register.setOnClickListener(this);
        bt_wb.setOnClickListener(this);
        bt_qq.setOnClickListener(this);
        bt_wx.setOnClickListener(this);
        look_mm.setOnClickListener(this);
//        input_pass.setInputType(InputType.TYPE_CLASS_TEXT
//                | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        if (null != getIntent().getStringExtra("LoginType")) {
            String loginType = getIntent().getStringExtra("LoginType");
            if (loginType.equals("qqLogin")) {
                doQQLogin();
            } else if (loginType.equals("wbLogin")) {
                doWBLogin();
            } else if (loginType.equals("wxLogin")) {
                doWXLogin();
            }
        }


    }

    /**
     * 判断网络是否连接
     *
     * @return
     */
    private boolean isNetworkConnected() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        return mNetworkInfo != null && mNetworkInfo.isAvailable();
    }

    // 执行普通登录
    private void doNormalLogin() {
        if (!isNetworkConnected()) {
            ToastUtils.show(this, "网络异常, 请检查您的网络");
            return;
        }
        mLoginPresenter.loginByPass();
    }

    // 执行微博登录
    private void doWBLogin() {
        if (!isNetworkConnected()) {
            ToastUtils.show(this, "网络异常, 请检查您的网络");
            return;
        }

        refreshPushId();
        Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
        weibo.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                Message msg = new Message();
                msg.what = Login_By_Wb;
                msg.obj = platform;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                LogUtil.e("Act_Login - " + throwable);
            }

            @Override
            public void onCancel(Platform platform, int i) {
                LogUtil.e("Act_Login - cancel");
            }
        });
        weibo.authorize();
        if (weibo.isAuthValid()) {
            weibo.removeAccount(true);
            ShareSDK.removeCookieOnAuthorize(true);
        }
    }

    // 执行微信登录
    private void doWXLogin() {
        if (!isNetworkConnected()) {
            ToastUtils.show(this, "网络异常, 请检查您的网络");
            return;
        }

        refreshPushId();
        Platform weixin = ShareSDK.getPlatform(Wechat.NAME);
        if(null == weixin) {
            LogUtil.e("微信登录渠道没取到！");
            return;
        }
        if(!weixin.isClientValid()) {
            ToastUtils.show(this, "未安装微信客户端");
            return;
        }
        weixin.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                Message msg = new Message();
                msg.what = Login_By_Wx;
                msg.obj = platform;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                LogUtil.e("Act_Login - " + throwable);
            }

            @Override
            public void onCancel(Platform platform, int i) {
                LogUtil.e("Act_Login - cancel");
            }
        });
        weixin.authorize();
        if (weixin.isAuthValid()) {
            weixin.removeAccount(true);
            ShareSDK.removeCookieOnAuthorize(true);
        }
    }

    // 执行QQ登录
    private void doQQLogin() {
        if (!isNetworkConnected()) {
            ToastUtils.show(this, "网络异常, 请检查您的网络");
            return;
        }

        refreshPushId();
        Platform qq = ShareSDK.getPlatform(QQ.NAME);
        qq.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                Message msg = new Message();
                msg.what = Login_By_QQ;
                msg.obj = platform;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                LogUtil.e("Act_Login - " + throwable);
            }

            @Override
            public void onCancel(Platform platform, int i) {
                LogUtil.e("Act_Login - cancel");
            }
        });
        qq.authorize();
        if (qq.isAuthValid()) {
            qq.removeAccount(true);
            ShareSDK.removeCookieOnAuthorize(true);
        }
    }

    @Override
    public Context getMContext() {
        return LoginAct.this;
    }

    @Override
    public void showLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = LoadingDialog.createLoadingDialog(LoginAct.this, "正在加载中...");
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
    public void refreshPushId() {
        PushManager.getInstance().initialize(
                this.getApplicationContext(), MgtService.class);
        PushManager.getInstance().registerPushIntentService(
                this.getApplicationContext(), MgtIntentService.class);
        LogUtil.d(" - login_refreshPushId");
    }

    @Override
    public String getPushId() {
        return pushId;
    }

    @Override
    public String getUName() {
        return input_name.getText().toString();
    }

    @Override
    public String getUPass() {
        return input_pass.getText().toString();
    }

    @Override
    public void turnToMain() {
        Intent intent = new Intent(this, MainAct.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void turnToBind() {
        Intent intent3 = new Intent(this, BindAct.class);
        startActivity(intent3);
        finish();
    }

    @Override
    public void showError(String msg, boolean flag) {
        ToastUtils.show(this, msg);
        if (flag) finish();
    }

    // 跳转到注册
    private void turnToRegister() {
        Intent intent = new Intent(this, RegisterAct.class);
        startActivity(intent);
        finish();
    }

    //
    private void turnToForgetPassWord() {
        Intent intent = new Intent(this, UpPassAct.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.look_mm:
                int type = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
                if (input_pass.getInputType() == type) {
                    input_pass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    input_pass.setSelection(input_pass.getText().length());     //把光标设置到当前文本末尾
                    look_mm.setImageResource(R.drawable.ic_pw_show);
                } else {
                    input_pass.setInputType(type);
                    look_mm.setImageResource(R.drawable.ic_pw_hide);
                    input_pass.setSelection(input_pass.getText().length());
                }

                break;
            case R.id.forget:
                turnToForgetPassWord();
                break;
            case R.id.submit:
                doNormalLogin();
                break;
            case R.id.bt_wb:
                ToastUtils.show(LoginAct.this, "正在启动微博");
                doWBLogin();
                break;
            case R.id.bt_qq:
                ToastUtils.show(LoginAct.this, "正在启动QQ");
                doQQLogin();
                break;
            case R.id.bt_wx:
                ToastUtils.show(LoginAct.this, "正在启动微信");
                doWXLogin();
                break;
            case R.id.register:
                turnToRegister();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        hideLoading();
        mLoginPresenter.closeHttp();
        if (pushReceiver != null) unregisterReceiver(pushReceiver);
        super.onDestroy();
    }

    private long nowTime;
    private long oldTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            nowTime = System.currentTimeMillis();
            if (nowTime - oldTime < 2000) {
                finish();
            } else {
                ToastUtils.show(this, "再按一次退出程序");
                oldTime = nowTime;
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }
}
