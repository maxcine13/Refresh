package com.jierong.share.mvp.view.act;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.jierong.share.R;
import com.jierong.share.mvp.presenter.MakePwPresenter;
import com.jierong.share.mvp.view.IMakePwView;
import com.jierong.share.util.ToastUtils;
import com.jierong.share.widget.LoadingDialog;

/**
 * 绑定完成之后的设置密码界面
 */
public class MakePwAct extends AppCompatActivity implements IMakePwView {
    private EditText input_pw, input_pw_two;
    private TextView submit;
    private Dialog mLoadingDialog;
    private MakePwPresenter mMakePwPresenter;
    private String mTel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_make_pw);

        init();
    }

    private void init() {
        mTel = getIntent().getStringExtra("Tel");
        mMakePwPresenter = new MakePwPresenter(this);
        ((TextView) findViewById(R.id.titleName)).setText(R.string.title_make_pw);
        input_pw = (EditText) findViewById(R.id.input_pw);
        input_pw_two = (EditText) findViewById(R.id.input_pw_two);
        submit = (TextView) findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMakePwPresenter.makePw();
            }
        });
    }

    @Override
    public Context getMContext() {
        return MakePwAct.this;
    }

    @Override
    public void showLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = LoadingDialog.createLoadingDialog(MakePwAct.this, "正在加载中...");
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
        return mTel;
    }

    @Override
    public String getUPw() {
        return input_pw.getText().toString();
    }

    @Override
    public String getUPwTwo() {
        return input_pw_two.getText().toString();
    }

    @Override
    public void makeSuccess() {
        // 设置密码成功的逻辑
        ToastUtils.show(this, "恭喜您设置密码成功！");
        Intent intent = new Intent(MakePwAct.this, MainAct.class);
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
        super.onDestroy();
        hideLoading();
        mMakePwPresenter.closeHttp();
    }

}
