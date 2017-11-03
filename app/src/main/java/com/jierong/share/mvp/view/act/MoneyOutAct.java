package com.jierong.share.mvp.view.act;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import com.jierong.share.BaseAct;
import com.jierong.share.Constants;
import com.jierong.share.R;
import com.jierong.share.mvp.presenter.MOPresenter;
import com.jierong.share.mvp.view.IMoneyOutView;
import com.jierong.share.util.StringUtil;
import com.jierong.share.util.ToastUtils;
import com.jierong.share.widget.DoubleTwoEditText;
import com.jierong.share.widget.LoadingDialog;

/**
 * 余额提现界面
 */
public class MoneyOutAct extends BaseAct implements IMoneyOutView {
    private TextView tv_out_zfb, money_out_total, money_out_submit;
    private Dialog mLoadingDialog;
    private DoubleTwoEditText out_money_want;
    private MOPresenter mMOPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_money_out);
        init();
    }

    private void refreshM() {
        Intent intent = new Intent();
        intent.setAction(Constants.Refresh_User_Money);
        sendBroadcast(intent);
    }

    private void init() {
        mMOPresenter = new MOPresenter(this);
        ((TextView) findViewById(R.id.titleName)).setText(R.string.title_name_money_out);
        tv_out_zfb = (TextView) findViewById(R.id.tv_out_zfb);
        money_out_total = (TextView) findViewById(R.id.money_out_total);
        money_out_submit = (TextView) findViewById(R.id.money_out_submit);
        out_money_want = (DoubleTwoEditText) findViewById(R.id.out_money_want);
        tv_out_zfb.setText(getIntent().getStringExtra("Card"));

        findViewById(R.id.titleBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        money_out_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMOPresenter.moneyOut();
            }
        });

        mMOPresenter.getUMoney();
    }

    @Override
    public void onNetNo() { }

    @Override
    public void onNetOk() { }

    @Override
    public Context getMContext() {
        return MoneyOutAct.this;
    }

    @Override
    public void showLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = LoadingDialog.createLoadingDialog(MoneyOutAct.this, "正在加载中...");
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
    public void returnUMoney(String money) {
        if(StringUtil.isNotEmptyIgnoreBlank(money))
            money_out_total.setText("可提现金额" + money + "元");
        else money_out_total.setText("可提现金额0.00元");
        refreshM();
    }

    @Override
    public String getWant() {
        return out_money_want.getText().toString().trim();
    }

    @Override
    public void txSuccess() {
        ToastUtils.show(this, "提现请求已经提交，请等待审核");
        refreshM();
        finish();
    }

    @Override
    public void showError(String msg, boolean flag) {
        ToastUtils.show(this, msg);
        if (flag) finish();
    }

    @Override
    protected void onDestroy() {
        mMOPresenter.closeHttp();
        hideLoading();
        super.onDestroy();
    }

}
