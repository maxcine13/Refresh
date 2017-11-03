package com.jierong.share.mvp.view.act;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.jierong.share.BaseAct;
import com.jierong.share.Constants;
import com.jierong.share.R;
import com.jierong.share.mvp.model.info.MaInfo;
import com.jierong.share.mvp.presenter.AuthenPersenter;
import com.jierong.share.mvp.view.IAuthenView;
import com.jierong.share.util.ToastUtils;
import com.jierong.share.widget.LoadingDialog;
import com.jierong.share.widget.SimpleDia;

/**
 * 达人认证界面
 */
public class AuthenticationAct extends BaseAct implements IAuthenView, View.OnClickListener {
    private EditText value_name, value_ID;
    private Button btn_submit;//提交审核
    private CheckBox cb_tips;//
    private Boolean isClick = true;//
    private Dialog mLoadingDialog;//
    private AuthenPersenter authenPersenter;
    private SimpleDia mSimpleDia;
    private TextView auth_tips;
    private RelativeLayout auth_rela;
    private String flag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_authenticaton);
        initView();
    }

    /**
     * view的初始化
     */
    private void initView() {
        authenPersenter = new AuthenPersenter(this);
        ((TextView) findViewById(R.id.titleName)).setText(R.string.title_name_authen);
        findViewById(R.id.titleBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
        value_name = (EditText) findViewById(R.id.value_name);
        auth_rela = (RelativeLayout) findViewById(R.id.auth_rela);
        value_ID = (EditText) findViewById(R.id.value_ID);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        cb_tips = (CheckBox) findViewById(R.id.cb_tips);
        auth_tips = (TextView) findViewById(R.id.auth_tips);

        btn_submit.setOnClickListener(this);
        auth_tips.setOnClickListener(this);
        cb_tips.setOnClickListener(this);

        authenPersenter.check();
        changeState(true);
    }

    @Override
    public void onNetNo() { }

    @Override
    public void onNetOk() { }

    @Override
    public Context getMContext() {
        return AuthenticationAct.this;
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
    public void authenSuccess(MaInfo info) {
        flag = "2";
        value_name.setText(info.getName());
        value_ID.setText(info.getIdcard());
    }

    @Override
    public void authenFail() {
        flag = "1";
        ToastUtils.show(getMContext(), "请填写达人认证申请信息!");
    }

    @Override
    public void showError(String msg, boolean flag) {
        ToastUtils.show(this, msg);
        if (flag) finish();
    }

    /**
     * 提交信息成功
     * @param message
     */
    @Override
    public void receiveData(String message) {
        ToastUtils.show(getMContext(), "提交成功！");
        Intent data = new Intent();
        setResult(Activity.RESULT_OK, data);
        finish();
    }

    @Override
    public void showTips(String message) {
        mSimpleDia = new SimpleDia(getMContext(), SimpleDia.Ok_No_Type);
        mSimpleDia.setTitleText("温馨提示")
                .setContentText(Html.fromHtml(message))
                .setConfirmText("确定")
                .setCancelText("取消")
                .showCancelButton(true)
                .setClickListener(new SimpleDia.OnClickButtonListener() {
                    @Override
                    public void onClick(SimpleDia dialog, int flag) {
                        switch (flag) {
                            case 0: // 取消
                                break;
                            case 1: // 确定
                                break;
                            default:
                                break;
                        }
                    }
                }).show();
    }

    @Override
    public String getFlag() {
        return flag;
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                authenPersenter.examine();
                break;
            case R.id.auth_tips:
                Intent intent = new Intent(AuthenticationAct.this, CommonAct.class);
                intent.putExtra("common", Constants.Authen);
                startActivity(intent);
                break;
        }
    }

    /**
     * 改变输入框的状态
     * @param chick
     */
    private void changeState(boolean chick) {
        if (chick) {
            setFocusableInTouchMode(chick);
            setFocusable(chick);
            setrequestFocus();
            isClick = false;
        } else {
            setFocusableInTouchMode(chick);
            setFocusable(chick);
            isClick = true;
        }
    }

    private void setFocusable(boolean Chick) {
        value_ID.setFocusable(Chick);
        value_name.setFocusable(Chick);
    }

    private void setFocusableInTouchMode(boolean Chick) {
        value_ID.setFocusableInTouchMode(Chick);
        value_name.setFocusableInTouchMode(Chick);
    }

    private void setrequestFocus() {
        value_ID.requestFocus();
        value_name.requestFocus();
    }

}
