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
import com.jierong.share.mvp.presenter.MHPresenter;
import com.jierong.share.mvp.view.IMoneyHaveView;
import com.jierong.share.util.StringUtil;
import com.jierong.share.util.ToastUtils;
import com.jierong.share.widget.LoadingDialog;

/**
 * 我的余额界面
 */
public class MoneyHaveAct extends BaseAct implements IMoneyHaveView {
    private TextView money_have_num, money_have_submit;
    private Dialog mLoadingDialog;
    private MHPresenter mMHPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_money_have);
        init();
    }

    private void refreshM() {
        Intent intent = new Intent();
        intent.setAction(Constants.Refresh_User_Money);
        sendBroadcast(intent);
    }

    private void init() {
        mMHPresenter = new MHPresenter(this);
        ((TextView) findViewById(R.id.titleName)).setText(R.string.title_name_money_have);
        money_have_num = (TextView) findViewById(R.id.money_have_num);
        money_have_submit = (TextView) findViewById(R.id.money_have_submit);

        findViewById(R.id.titleBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        money_have_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMHPresenter.checkBindCard();
//                Intent intent = new Intent(MoneyHaveAct.this, CommonAct.class);
//                intent.putExtra("common", Constants.GetMoney);
//                MoneyHaveAct.this.startActivity(intent);
//                MoneyHaveAct.this.finish();
            }
        });

        mMHPresenter.getUMoney();
    }

    @Override
    public void onNetNo() { }

    @Override
    public void onNetOk() { }

    @Override
    public Context getMContext() {
        return MoneyHaveAct.this;
    }

    @Override
    public void showLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = LoadingDialog.createLoadingDialog(MoneyHaveAct.this, "正在加载中...");
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
    public void getUMoney(String money) {
        if(StringUtil.isNotEmptyIgnoreBlank(money)) money_have_num.setText(money);
        else money_have_num.setText("0.00");
        refreshM();
    }

    @Override
    public void getCard(String card) {
        refreshM();
        Intent intent = new Intent(this, MoneyOutAct.class);
        intent.putExtra("Card", card);
        startActivity(intent);
        finish();
    }

    @Override
    public void turnToBind() {
        ToastUtils.show(getMContext(), "请您先绑定支付宝提现账号");
        refreshM();
        Intent intent = new Intent(this, CardBindAct.class);
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
        mMHPresenter.closeHttp();
        hideLoading();
        super.onDestroy();
    }

}
