package com.jierong.share.mvp.view.act;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jierong.share.BaseAct;
import com.jierong.share.Constants;
import com.jierong.share.R;
import com.jierong.share.okhttp.BaseHttpUtil;
import com.jierong.share.okhttp.HttpStringCallBack;
import com.jierong.share.util.LogUtil;
import com.jierong.share.util.StringUtil;
import com.jierong.share.util.ToastUtils;
import com.lzy.okgo.model.HttpParams;

import java.io.UnsupportedEncodingException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017-04-21.忘记密码
 */

public class UpPassAct extends BaseAct {
    private EditText input_tel, input_key, input_new;
    private TextView get_key, submit;
    private String mTel, mKey, mNew;
    private static final int Forget_Code_GetKey = 61;
    private int count;
    private Timer timer;
    private Handler nHandler = new Handler() {
        @SuppressWarnings("deprecation")
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                get_key.setEnabled(true);
                get_key.setText("重新获取");
                get_key.setTextColor(getResources().getColor(R.color.bottom_text_click));
                timer.cancel();
            } else if (msg.what == Forget_Code_GetKey) {
                ToastUtils.show(UpPassAct.this, "验证码已发送至手机，请注意查收!");
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
                //get_key.setBackgroundResource(R.drawable.button_getkey_disable);
                get_key.setTextColor(getResources().getColor(R.color.color_red));
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_updatapassword);
        initview();


    }

    private void initview() {
        ((TextView) findViewById(R.id.titleName)).setText(R.string.forget_password);
        input_tel = (EditText) findViewById(R.id.up_input_tel);
        input_key = (EditText) findViewById(R.id.up_input_key);
        input_new = (EditText) findViewById(R.id.up_input_pw);
        // input_old = (EditText) findViewById(R.id.up_input_old);
        get_key = (TextView) findViewById(R.id.up_get_key);
        submit = (TextView) findViewById(R.id.up_forget_submit);

        findViewById(R.id.titleBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        get_key.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getKey();

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doForget();
            }
        });
    }

    // 是否包含中文
    private boolean isCN(String str) {
        try {
            byte[] bytes = str.getBytes("UTF-8");
            if (bytes.length == str.length()) {
                return false;
            } else {
                return true;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 获取验证码
    private void getKey() {
        if (!isNetworkConnected()) {
            ToastUtils.show(UpPassAct.this, "网络连接错误，请检查网络!");
            return;
        }
        mTel = input_tel.getText().toString().trim();
        if (StringUtil.isEmptyIgnoreBlank(mTel)) {
            ToastUtils.show(this, "手机号不能为空");
            return;
        } else if (!StringUtil.isMobile(mTel)) {
            ToastUtils.show(this, "手机号码格式错误!");
            return;
        }
        String url = Constants.Http_Api_Get_Mobile_Code;
        HttpParams httpParams = new HttpParams();
        httpParams.put("mobile", mTel);
        BaseHttpUtil baseHttpUtil = new BaseHttpUtil();
        baseHttpUtil.doPost(url, httpParams, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                LogUtil.d(String.valueOf(result));
                Message msg = new Message();
                msg.what = Forget_Code_GetKey;
                nHandler.sendMessage(msg);
            }

            @Override
            public void onFailure(int code, String msg) {
                ToastUtils.show(UpPassAct.this, msg);
            }
        });
    }

    // 忘记密码并且重置密码
    private void doForget() {
        if (!isNetworkConnected()) {
            ToastUtils.show(UpPassAct.this, "网络连接错误，请检查网络!");
            return;
        }
        mTel = input_tel.getText().toString().trim();
        mKey = input_key.getText().toString().trim();
        mNew = input_new.getText().toString().trim();
        if (StringUtil.isEmptyIgnoreBlank(mTel)) {
            ToastUtils.show(this, "手机号不能为空");
            return;
        } else if (!StringUtil.isMobile(mTel)) {
            ToastUtils.show(this, "手机号码格式错误!");
            return;
        }
        if (StringUtil.isEmptyIgnoreBlank(mKey)) {
            ToastUtils.show(this, "验证码不能为空!");
            return;
        } else if (!(mKey.length() == 4 || mKey.length() == 6)) {
            ToastUtils.show(this, "请您填写有效的验证码!");
            return;
        }
        if (StringUtil.isEmptyIgnoreBlank(mNew)) {
            ToastUtils.show(this, "密码不能为空!");
            return;
        } else if (mNew.length() < 6 || mNew.length() > 20) {
            ToastUtils.show(this, "密码格式错误!");
            return;
        } else if (isCN(mNew)) {
            ToastUtils.show(this, "密码包含非法字符,请重新输入!");
            return;
        }
        BaseHttpUtil baseHttpUtil = new BaseHttpUtil();
        String url1 = Constants.Http_Api_Forget;
        HttpParams httpParams = new HttpParams();
        httpParams.put("mobile", mTel);
        httpParams.put("fcode", mKey);
        httpParams.put("newpwd", mNew);
        baseHttpUtil.doPost(url1, httpParams, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                ToastUtils.show(UpPassAct.this, "密码重置成功,请登录!");
                finish();
            }

            @Override
            public void onFailure(int code, String msg) {
                ToastUtils.show(UpPassAct.this, msg);
            }
        });
    }

    @Override
    public void onNetNo() {

    }

    @Override
    public void onNetOk() {

    }
}
