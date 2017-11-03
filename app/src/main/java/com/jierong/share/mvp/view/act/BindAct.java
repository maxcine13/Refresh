package com.jierong.share.mvp.view.act;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.jierong.share.AppManager;
import com.jierong.share.BaseAct;
import com.jierong.share.R;
import com.jierong.share.mvp.presenter.BindPresenter;
import com.jierong.share.mvp.view.IBindView;
import com.jierong.share.util.AppPreferences;
import com.jierong.share.util.ToastUtils;
import com.jierong.share.widget.LoadingDialog;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 绑定手机号码界面
 */
public class BindAct extends BaseAct implements IBindView {
    private TextView get_key, submit, goLogin;
    private EditText input_tel, input_key;
    private Dialog mLoadingDialog;

    private BindPresenter mBindPresenter;
    private static final int Bind_Code_GetKey = 61;
    private int count;
    private Timer timer;
    private Handler nHandler = new Handler() {
        @SuppressWarnings("deprecation")
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                get_key.setBackgroundResource(R.drawable.button_getkey);
                get_key.setEnabled(true);
                get_key.setText(R.string.register_get_key);
                get_key.setTextColor(getResources().getColor(R.color.color_black));
                timer.cancel();
            } else if (msg.what == Bind_Code_GetKey) {
                hideLoading();
                ToastUtils.show(BindAct.this, "验证码已发送至手机，请注意查收!");
                // 等真正获取到验证码后，再倒计时，更严密
                count = 60;
                get_key.setEnabled(false);
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        nHandler.sendEmptyMessage(count--);
                    }
                }, 0, 1000);
            } else {
                get_key.setText(msg.what + "秒");
                get_key.setBackgroundResource(R.drawable.button_getkey_disable);
                get_key.setTextColor(getResources().getColor(R.color.color_white));
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_bind);
        init();
    }

    private void init() {
        mBindPresenter = new BindPresenter(this);
        ((TextView) findViewById(R.id.titleName)).setText(R.string.title_name_bind);
        input_tel = (EditText) findViewById(R.id.input_tel);
        input_key = (EditText) findViewById(R.id.input_key);
        get_key = (TextView) findViewById(R.id.get_key);
        submit = (TextView) findViewById(R.id.submit);
        goLogin = (TextView) findViewById(R.id.goLogin);
        goLogin.setVisibility(View.VISIBLE);

        goLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToLogin();
            }
        });
        get_key.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBindPresenter.getKey();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBindPresenter.bind();
            }
        });
        findViewById(R.id.titleBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onNetNo() {
    }

    @Override
    public void onNetOk() {
    }

    private void goToLogin() {
        // 无论如何先清理数据
        AppPreferences.clear(this);
        // 如果只退出账号，则不再重新走引导页
        AppPreferences.putBoolean(this, "isFirstStart", false);
        startActivity(new Intent(this, LoginAct.class));
        AppManager.getAppManager().finishAllActivity();
    }

    @Override
    public Context getMContext() {
        return BindAct.this;
    }

    @Override
    public void showLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = LoadingDialog.createLoadingDialog(BindAct.this, "正在加载中...");
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
    public String getUTel() {
        return input_tel.getText().toString();
    }

    @Override
    public String getUKey() {
        return input_key.getText().toString();
    }

    @Override
    public void getKeySuccess() {
        Message msg = new Message();
        msg.what = Bind_Code_GetKey;
        nHandler.sendMessage(msg);
    }

    @Override
    public void bindSuccess() {
        // 绑定成功的逻辑
        ToastUtils.show(this, "手机绑定成功，请设置您的密码！");
        Intent intent = new Intent(BindAct.this, MakePwAct.class);
        intent.putExtra("Tel", getUTel());
        startActivity(intent);
        finish();
    }

    @Override
    public void showError(String msg, boolean flag) {
        ToastUtils.show(this, msg);
        if (flag) finish();
    }

    @Override
    protected void onDestroy() {
        mBindPresenter.closeHttp();
        hideLoading();
        if (timer != null) timer.cancel();
        super.onDestroy();
    }

}
