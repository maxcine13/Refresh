package com.jierong.share.mvp.view.act;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jierong.share.BaseAct;
import com.jierong.share.R;
import com.jierong.share.mvp.presenter.ChangePwPresenter;
import com.jierong.share.mvp.view.IChangePwView;
import com.jierong.share.util.ToastUtils;
import com.jierong.share.widget.LoadingDialog;

/**
 * 修改密码界面
 */
public class ChangePwAct extends BaseAct implements IChangePwView {
    private EditText input_old, input_new, input_two;
    private TextView btn_confirm;
    private Dialog mLoadingDialog;
    private ChangePwPresenter mChangePwPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_change_pw);

        init();
    }

    @Override
    public void onNetNo() {
    }

    @Override
    public void onNetOk() {
    }

    private void init() {
        mChangePwPresenter = new ChangePwPresenter(this);
        ((TextView) findViewById(R.id.titleName)).setText(R.string.title_name_change_pw);
        input_old = ((EditText) findViewById(R.id.input_old));
        input_new = ((EditText) findViewById(R.id.input_new));
        input_two = ((EditText) findViewById(R.id.input_two));
        btn_confirm = ((TextView) findViewById(R.id.btn_confirm));

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mChangePwPresenter.change();

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
    public Context getMContext() {
        return ChangePwAct.this;
    }

    @Override
    public void showLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = LoadingDialog.createLoadingDialog(ChangePwAct.this, "正在加载中...");
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
    public String getOldPw() {
        return input_old.getText().toString();
    }

    @Override
    public String getNewPw() {
        return input_new.getText().toString();
    }

    @Override
    public String getTwoPw() {
        return input_two.getText().toString();
    }

    @Override
    public void changeSuccess() {
        ToastUtils.show(ChangePwAct.this, "恭喜您，修改成功！");
        finish();
    }

    @Override
    public void showError(String msg, boolean flag) {
        ToastUtils.show(ChangePwAct.this, msg);
        if (flag) finish();
    }

    @Override
    protected void onDestroy() {
        mChangePwPresenter.closeHttp();
        hideLoading();
        super.onDestroy();
    }
}
