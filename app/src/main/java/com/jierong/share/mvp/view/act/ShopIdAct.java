package com.jierong.share.mvp.view.act;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jierong.share.BaseAct;
import com.jierong.share.R;
import com.jierong.share.mvp.model.info.Budgetinfo;
import com.jierong.share.mvp.presenter.ShopIdPresenter;
import com.jierong.share.mvp.view.IShopIdView;
import com.jierong.share.util.ToastUtils;
import com.jierong.share.widget.ContactEditPopuLeft;
import com.jierong.share.widget.LoadingDialog;

/**
 * 商品申请返利
 */

public class ShopIdAct extends BaseAct implements IShopIdView, View.OnClickListener, ContactEditPopuLeft.ItemClickListener {
    private Dialog mLoadingDialog;//
    private EditText edit_search;//搜索框
    private Button btn_search;//搜索按钮
    private ShopIdPresenter shopIdPresenter;
    private ContactEditPopuLeft popu = null;
    private LinearLayout linear_budget;
    private TextView txt_budget, value_goods, value_shop, value_order, value_price, value_time, value_budget;
    private RelativeLayout rela_empty;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_shopid);
        initView();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        shopIdPresenter = new ShopIdPresenter(this);
        ((TextView) findViewById(R.id.titleName)).setText(R.string.me_money_shopid);
        findViewById(R.id.titleBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        edit_search = (EditText) findViewById(R.id.edit_search);
        btn_search = (Button) findViewById(R.id.btn_search);
        linear_budget = (LinearLayout) findViewById(R.id.linear_budget);
        txt_budget = (TextView) findViewById(R.id.txt_budget);
        value_goods = (TextView) findViewById(R.id.value_goods);
        value_shop = (TextView) findViewById(R.id.value_shop);
        value_order = (TextView) findViewById(R.id.value_order);
        value_price = (TextView) findViewById(R.id.value_price);
        value_time = (TextView) findViewById(R.id.value_time);
        value_budget = (TextView) findViewById(R.id.value_budget);
        rela_empty = (RelativeLayout) findViewById(R.id.rela_empty);
        btn_search.setOnClickListener(this);


    }

    @Override
    public void onNetNo() {

    }

    @Override
    public void onNetOk() {

    }

    @Override
    public Context getMContext() {
        return ShopIdAct.this;
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
    public String getShopId() {
        return edit_search.getText().toString().trim();
    }

    @Override
    public void receiveBudgetData(String data) {
        if (0 != data.length()) {
            rela_empty.setVisibility(View.GONE);
            linear_budget.setVisibility(View.VISIBLE);
            linear_budget.setVisibility(View.VISIBLE);
            Budgetinfo budgetinfo = Budgetinfo.fromJSON(data);
            txt_budget.setText("等待返现");
            value_goods.setText("商品名称:" + budgetinfo.getGoodname());
            value_shop.setText("所属商铺:" + budgetinfo.getFrom());
            value_order.setText("订单编号:" + budgetinfo.getOrdernumber());
            value_price.setText("订单价格:¥" + budgetinfo.getPay_money());
            value_budget.setText("预计返利:¥" + budgetinfo.getPredictmoney());
            value_time.setText("购买时间:" + budgetinfo.getOrdertime());

        } else {
            rela_empty.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void receiveRealData(String data) {
        if (0 != data.length()) {
            rela_empty.setVisibility(View.GONE);
            linear_budget.setVisibility(View.VISIBLE);
            Budgetinfo budgetinfo = Budgetinfo.fromJSON(data);
            txt_budget.setText("已返现");
            value_goods.setText("商品名称:" + budgetinfo.getGoodname());
            value_shop.setText("所属商铺:" + budgetinfo.getFrom());
            value_order.setText("订单编号:" + budgetinfo.getOrdernumber());
            value_price.setText("订单价格:¥" + budgetinfo.getPay_money());
            value_budget.setText("实际返利:¥" + budgetinfo.getPredictmoney());
            value_time.setText("购买时间:" + budgetinfo.getOrdertime());
        } else {
            rela_empty.setVisibility(View.VISIBLE);
        }

    }


    @Override
    public void showError(String msg, boolean flag) {
        ToastUtils.show(this, msg);
        if (flag) finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_search:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
                popu = new ContactEditPopuLeft(
                        ShopIdAct.this, new String[]{"预估返利查询", "实际返利查询"});
                popu.showToggle(btn_search, 0, 0);
                popu.setItemListener(ShopIdAct.this);
                break;
            default:
                break;
        }

    }

    /**
     * 预计返利
     */
    @Override
    public void item1Listener() {
        shopIdPresenter.searchBudgetrebate();
    }

    /**
     * 最终返利
     */
    @Override
    public void item2Listener() {
        shopIdPresenter.searchRealRebate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideLoading();
        if (null != popu)
            popu = null;
        shopIdPresenter.closeHttp();
    }
}
