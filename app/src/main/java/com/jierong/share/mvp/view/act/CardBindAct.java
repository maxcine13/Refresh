package com.jierong.share.mvp.view.act;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.jierong.share.BaseAct;
import com.jierong.share.R;
import com.jierong.share.mvp.presenter.CardBindPresenter;
import com.jierong.share.mvp.view.ICardBindView;
import com.jierong.share.util.ToastUtils;
import com.jierong.share.widget.LoadingDialog;
import com.jierong.share.widget.SimpleDia;

/**
 * 绑定支付宝界面
 */
public class CardBindAct extends BaseAct implements ICardBindView {
    private TextView bind_submit;
    private Dialog mLoadingDialog;
    private EditText input_name, input_zfb;
    private CardBindPresenter mCardBindPresenter;
    private SimpleDia mSimpleDia;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_card_bind);
        init();
    }

    private void init() {
        mCardBindPresenter = new CardBindPresenter(this);
        ((TextView) findViewById(R.id.titleName)).setText(R.string.title_name_bind_card);
        input_name = (EditText) findViewById(R.id.input_name);
        input_zfb = (EditText) findViewById(R.id.input_zfb);
        bind_submit = (TextView) findViewById(R.id.bind_submit);
        input_name.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});

        findViewById(R.id.titleBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        bind_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCardBindPresenter.checkInput();
            }
        });
    }

    @Override
    public void onNetNo() { }

    @Override
    public void onNetOk() { }

    @Override
    public Context getMContext() {
        return CardBindAct.this;
    }

    @Override
    public void showLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = LoadingDialog.createLoadingDialog(CardBindAct.this, "正在加载中...");
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
    public String getUName() {
        return input_name.getText().toString();
    }

    @Override
    public String getCard() {
        return input_zfb.getText().toString();
    }

    @Override
    public void doCardBind(String name, String zfb) {
        mSimpleDia = new SimpleDia(this, SimpleDia.Ok_No_Type);
        mSimpleDia.setTitleText("温馨提示")
                .setContentText(Html.fromHtml(
                        "请确认您的绑定信息："
                                + "<br/>姓&nbsp;&nbsp;&nbsp;&nbsp;名：<font color=\"#FF8C19\">" + name + "</font>"
                                + "<br/>支付宝：<font color=\"#FF8C19\">" + zfb + "</font>"))
                .setConfirmText("确定")
                .setCancelText("取消")
                .showCancelButton(true)
                .setClickListener(new SimpleDia.OnClickButtonListener() {
                    @Override
                    public void onClick(SimpleDia dialog, int flag) {
                        switch (flag) {
                            case 0: break;
                            case 1: // 确定
                                mCardBindPresenter.cardBind();
                                break;
                            default: break;
                        }
                    }
                }).show();
    }

    @Override
    public void cardBindSuccess() {
        ToastUtils.show(this, "恭喜您，绑定成功！");
        Intent intent = new Intent(this, MoneyOutAct.class);
        intent.putExtra("Card", getCard().trim());
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
        mCardBindPresenter.closeHttp();
        hideLoading();
        if (mSimpleDia != null) mSimpleDia.dismissWithAnimation();
        super.onDestroy();
    }

}
