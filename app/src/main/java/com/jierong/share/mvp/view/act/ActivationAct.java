package com.jierong.share.mvp.view.act;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.jierong.share.BaseAct;
import com.jierong.share.Constants;
import com.jierong.share.R;
import com.jierong.share.mvp.presenter.ActivationPersenter;
import com.jierong.share.mvp.view.IAcitvationView;
import com.jierong.share.util.LogUtil;
import com.jierong.share.util.ToastUtils;
import com.jierong.share.widget.LoadingDialog;


/**
 * 白条激活页面
 */

public class ActivationAct extends BaseAct implements IAcitvationView, View.OnClickListener {
    private EditText value_name, value_ID, value_bankcard, activation_phone;
    private CheckBox cb_tips;
    private TextView auth_tips;
    private Button btn_submit;
    private Dialog mLoadingDialog;//
    private ActivationPersenter activationPersenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_activation);
        initView();
    }


    private void initView() {
        activationPersenter = new ActivationPersenter(this);
        ((TextView) findViewById(R.id.titleName)).setText(R.string.me_activation);
        findViewById(R.id.titleBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        value_name = (EditText) findViewById(R.id.value_name);
        value_ID = (EditText) findViewById(R.id.value_ID);
        value_bankcard = (EditText) findViewById(R.id.value_bankcard);
        activation_phone = (EditText) findViewById(R.id.activation_phone);
        cb_tips = (CheckBox) findViewById(R.id.cb_tips);
        auth_tips = (TextView) findViewById(R.id.auth_tips);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        auth_tips.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
    }

    @Override
    public void onNetNo() {

    }

    @Override
    public void onNetOk() {

    }

    @Override
    public Context getMContext() {
        return ActivationAct.this;
    }

    @Override
    public void showLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = LoadingDialog.createLoadingDialog(getMContext(), "正在加载中...");
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
    public void receiveData(String message) {
        ToastUtils.show(ActivationAct.this, message);
        Intent data = new Intent(ActivationAct.this, WhileLineAct.class);
        setResult(Activity.RESULT_OK, data);
        finish();

    }


    @Override
    public boolean getCheck() {
        return cb_tips.isChecked();
    }

    @Override
    public String getUName() {
        return value_name.getText().toString().trim();
    }

    @Override
    public String getUID() {
        return value_ID.getText().toString().trim();
    }

    @Override
    public String getUCard() {
        return value_bankcard.getText().toString().trim();
    }

    @Override
    public String getUPhone() {
        return activation_phone.getText().toString().trim();
    }

    @Override
    public void showError(String msg, boolean flag) {
        ToastUtils.show(this, msg);
        if (flag) finish();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit://激活
                activationPersenter.activationMethod();
                break;
            case R.id.auth_tips://条约
                Intent intent = new Intent(getMContext(), CommonAct.class);
                intent.putExtra("common", Constants.Activation);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        activationPersenter.closeHttp();
        hideLoading();
        super.onDestroy();
    }

}
