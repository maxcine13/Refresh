package com.jierong.share.mvp.view.act;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jierong.share.Constants;
import com.jierong.share.R;
import com.jierong.share.mvp.presenter.RegisterPresenter;
import com.jierong.share.mvp.view.IRegisterView;
import com.jierong.share.util.ToastUtils;
import com.jierong.share.widget.CaptchaImageView;
import com.jierong.share.widget.LoadingDialog;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 注册界面
 */
public class RegisterAct extends AppCompatActivity implements IRegisterView, View.OnClickListener {
    private TextView get_key, submit, go_login_tel, register_service, sign_btn;
    private ImageView look_mm, go_login_qq, go_login_wb, go_login_wx;
    private EditText input_tel, input_key, input_pw, input_tjm, input_sign;
    private CaptchaImageView sign_img;
    private Dialog mLoadingDialog;
    private RegisterPresenter mRegisterPresenter;
    private static final int Register_Code_GetKey = 61;
    private int count;
    private Timer timer;
    private Handler nHandler = new Handler() {
        @SuppressWarnings("deprecation")
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                get_key.setBackgroundColor(getResources().getColor(R.color.transparent));
                get_key.setEnabled(true);
                get_key.setText("点击获取");
                get_key.setTextColor(getResources().getColor(R.color.color_white));
                timer.cancel();
            } else if (msg.what == Register_Code_GetKey) {
                hideLoading();
                ToastUtils.show(RegisterAct.this, "验证码已发送至手机，请注意查收!");
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
        setContentView(R.layout.act_register);

        init();
    }

    private void init() {
        mRegisterPresenter = new RegisterPresenter(this);
        input_tel = (EditText) findViewById(R.id.input_tel);
        input_key = (EditText) findViewById(R.id.input_key);
        input_pw = (EditText) findViewById(R.id.input_pw);
        input_tjm = (EditText) findViewById(R.id.input_tjm);
        get_key = (TextView) findViewById(R.id.get_key);
        submit = (TextView) findViewById(R.id.submit);

        go_login_tel = (TextView) findViewById(R.id.go_login_tel);
        register_service = (TextView) findViewById(R.id.register_service);
        look_mm = (ImageView) findViewById(R.id.look_mm);
        go_login_qq = (ImageView) findViewById(R.id.go_login_qq);
        go_login_wb = (ImageView) findViewById(R.id.go_login_wb);
        go_login_wx = (ImageView) findViewById(R.id.go_login_wx);
        go_login_tel.setOnClickListener(this);
        register_service.setOnClickListener(this);
        look_mm.setOnClickListener(this);
        go_login_qq.setOnClickListener(this);
        go_login_wb.setOnClickListener(this);
        go_login_wx.setOnClickListener(this);

        sign_btn = (TextView) findViewById(R.id.sign_btn);
        sign_img = (CaptchaImageView) findViewById(R.id.sign_img);
        input_sign = (EditText) findViewById(R.id.input_sign);
        sign_img.setCaptchaType(CaptchaImageView.CaptchaGenerator.BOTH);
        sign_img.setCaptchaLength(4);
        // sign_img.setIsDotNeeded(true);
        input_sign.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});

        sign_btn.setOnClickListener(this);
        get_key.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    @Override
    public Context getMContext() {
        return RegisterAct.this;
    }

    @Override
    public void showLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = LoadingDialog.createLoadingDialog(RegisterAct.this, "正在加载中...");
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
    public String getUTjm() {
        return input_tjm.getText().toString();
    }

    @Override
    public String getUPw() {
        return input_pw.getText().toString();
    }

    @Override
    public String getSignNum() {
        return input_sign.getText().toString();
    }

    @Override
    public void getKeySuccess() {
        Message msg = new Message();
        msg.what = Register_Code_GetKey;
        nHandler.sendMessage(msg);
    }

    /**
     * 注册成功之后调用自动登录接口,提示新人注册大礼包
     */
    @Override
    public void registerSuccess() {
        // 注册成功的逻辑
        ToastUtils.show(this, "恭喜您注册成功！");
        Intent telIntent = new Intent(this, LoginAct.class);
        startActivity(telIntent);
        finish();
    }

    @Override
    public void showError(String msg, boolean flag) {
        ToastUtils.show(this, msg);
        if (flag) finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_btn: {
                sign_img.regenerate();
                break;
            }
            case R.id.get_key:
                mRegisterPresenter.getKey();
                break;
            case R.id.submit:
                mRegisterPresenter.register(sign_img.getCaptchaCode());
                break;
            case R.id.go_login_tel:
                Intent telIntent = new Intent(this, LoginAct.class);
                startActivity(telIntent);
                finish();
                break;
            case R.id.register_service:
                Intent intent = new Intent(RegisterAct.this,CommonAct.class);
                intent.putExtra("common",Constants.RegisterActivity);
                startActivity(intent);
                break;
            case R.id.look_mm:
                int type = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
                if (input_pw.getInputType() == type) {
                    input_pw.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    input_pw.setSelection(input_pw.getText().length());     //把光标设置到当前文本末尾
                    look_mm.setImageResource(R.drawable.ic_pw_show);
                } else {
                    input_pw.setInputType(type);
                    look_mm.setImageResource(R.drawable.ic_pw_hide);
                    input_pw.setSelection(input_pw.getText().length());
                }
                break;
            case R.id.go_login_qq:
                Intent qqIntent = new Intent(this, LoginAct.class);
                qqIntent.putExtra("LoginType", "qqLogin");
                startActivity(qqIntent);
                finish();
                break;
            case R.id.go_login_wb:
                Intent wbIntent = new Intent(this, LoginAct.class);
                wbIntent.putExtra("LoginType", "wbLogin");
                startActivity(wbIntent);
                finish();
                break;
            case R.id.go_login_wx:
                Intent wxIntent = new Intent(this, LoginAct.class);
                wxIntent.putExtra("LoginType", "wxLogin");
                startActivity(wxIntent);
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        mRegisterPresenter.closeHttp();
        hideLoading();
        if (timer != null) timer.cancel();
        super.onDestroy();
    }

}
