package com.jierong.share.mvp.view.act;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jierong.share.BaseAct;
import com.jierong.share.R;
import com.jierong.share.mvp.model.info.ShopInfo;
import com.jierong.share.mvp.presenter.ShopBuyListPresenter;
import com.jierong.share.mvp.view.IShopBuyView;
import com.jierong.share.mvp.view.ada.ShopBuyAdapter;
import com.jierong.share.util.LogUtil;
import com.jierong.share.util.StringUtil;
import com.jierong.share.util.ToastUtils;
import com.jierong.share.widget.LoadingDialog;
import java.util.List;

/**
 * 购物记录界面
 */
public class ShopBuyAct extends BaseAct implements IShopBuyView, BaseQuickAdapter.
        RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    private Dialog mLoadingDialog;
    private ShopBuyListPresenter mShopBuyListPresenter;
    private LinearLayout emptyView, errorView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView shop_money_total;
    private RecyclerView mRecyclerView;
    private ShopBuyAdapter mShopBuyAdapter;
    private List<ShopInfo> data;     // 购物历史数据
    int mPageIndex = 1;
    boolean isClosed = false;       // 是否锁定自增

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_shop_buy);
        init();
    }

    private void initAdapter() {
        mShopBuyAdapter = new ShopBuyAdapter(data);
        mShopBuyAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mShopBuyAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mRecyclerView.setAdapter(mShopBuyAdapter);
    }

    private void addHeadView() {
        View headView = getLayoutInflater().inflate(
                R.layout.item_shop_header_buy, (ViewGroup) mRecyclerView.getParent(), false);
        shop_money_total = (TextView) headView.findViewById(R.id.shop_money_total);
        mShopBuyAdapter.addHeaderView(headView);
    }

    private void init() {
        mShopBuyListPresenter = new ShopBuyListPresenter(this);
        ((TextView) findViewById(R.id.titleName)).setText(R.string.title_name_shop);
        emptyView = (LinearLayout) findViewById(R.id.emptyView);
        errorView = (LinearLayout) findViewById(R.id.errorView);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshView);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.bottom_text_click));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        initAdapter();
        addHeadView();
        showLoading();
        onRefresh();

        emptyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoading();
                onRefresh();
            }
        });
        errorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoading();
                onRefresh();
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
    public void onRefresh() {
        mShopBuyAdapter.setEnableLoadMore(false);
        mPageIndex = 1;
        isClosed = false;
        mShopBuyListPresenter.loadData(true);

//        data = new ArrayList<>();
//        for (int i = 0; i < 6; i++) {
//            ShopInfo info = new ShopInfo();
//            info.setId(String.valueOf(i + 10));
//            info.setName("羊毛衫" + i);
//            info.setTime("2017年09月18日  18:30");
//            info.setPrice("-258.29");
//            data.add(info);
//        }
//        //this.data = data;
//        mShopBuyAdapter.getData().clear();
//        mShopBuyAdapter.setNewData(data);
//        mSwipeRefreshLayout.setRefreshing(false);
//        mShopBuyAdapter.setEnableLoadMore(true);
    }

    @Override
    public void onLoadMoreRequested() {
        mSwipeRefreshLayout.setEnabled(false);
        if (!isClosed) mPageIndex ++;
        mShopBuyListPresenter.loadData(false);
    }

    @Override
    public void onNetNo() { }

    @Override
    public void onNetOk() { }

    @Override
    public Context getMContext() {
        return ShopBuyAct.this;
    }

    @Override
    public void showLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = LoadingDialog.createLoadingDialog(ShopBuyAct.this, "正在加载中...");
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
    public int getPage() {
        return mPageIndex;
    }

    @Override
    public void refreshSuccess(String sum, List<ShopInfo> data) {
        hideLoading();
        if(StringUtil.isNotEmptyIgnoreBlank(sum)) shop_money_total.setText(sum);
        else shop_money_total.setText("0.00");

        if(null == data) {
            if(emptyView.getVisibility() == View.GONE)
                emptyView.setVisibility(View.VISIBLE);
            if(errorView.getVisibility() == View.VISIBLE)
                errorView.setVisibility(View.GONE);
            if(mSwipeRefreshLayout.getVisibility() == View.VISIBLE)
                mSwipeRefreshLayout.setVisibility(View.GONE);
            mShopBuyAdapter.getData().clear();
            mSwipeRefreshLayout.setRefreshing(false);
            mShopBuyAdapter.setEnableLoadMore(true);
            return;
        }

        if(emptyView.getVisibility() == View.VISIBLE)
            emptyView.setVisibility(View.GONE);
        if(errorView.getVisibility() == View.VISIBLE)
            errorView.setVisibility(View.GONE);
        if(mSwipeRefreshLayout.getVisibility() == View.GONE)
            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
        this.data = data;
        mShopBuyAdapter.getData().clear();
        mShopBuyAdapter.setNewData(data);
        mSwipeRefreshLayout.setRefreshing(false);
        mShopBuyAdapter.setEnableLoadMore(true);
    }

    @Override
    public void loadSuccess(String sum, List<ShopInfo> data) {
        if(StringUtil.isNotEmptyIgnoreBlank(sum)) shop_money_total.setText(sum);
        else shop_money_total.setText("0.00");

        if(null == data) {
            // 这页已经不存在更多数据
            loadEnd();
            return;
        }
        isClosed = false;
        mShopBuyAdapter.addData(data);
        mShopBuyAdapter.loadMoreComplete();
        mSwipeRefreshLayout.setEnabled(true);
    }

    @Override
    public void loadEnd() {
        isClosed = true;
        mShopBuyAdapter.loadMoreEnd(false);
        mSwipeRefreshLayout.setEnabled(true);
    }

    @Override
    public void showError(String msg, boolean flag, boolean isRefresh) {
        ToastUtils.show(this, msg);
        if(isRefresh) {
            LogUtil.e("刷新加载失败");
            hideLoading();
            if(emptyView.getVisibility() == View.VISIBLE)
                emptyView.setVisibility(View.GONE);
            if(errorView.getVisibility() == View.GONE)
                errorView.setVisibility(View.VISIBLE);
            if(mSwipeRefreshLayout.getVisibility() == View.VISIBLE)
                mSwipeRefreshLayout.setVisibility(View.GONE);
            mShopBuyAdapter.getData().clear();
            mShopBuyAdapter.notifyDataSetChanged();
            mSwipeRefreshLayout.setRefreshing(false);
            mShopBuyAdapter.setEnableLoadMore(true);
        } else {
            LogUtil.e("加载加载失败");
            isClosed = true;
            mShopBuyAdapter.loadMoreFail();
            mSwipeRefreshLayout.setEnabled(true);
        }
        if (flag) finish();
    }

    @Override
    protected void onDestroy() {
        hideLoading();
        mShopBuyListPresenter.closeHttp();
        super.onDestroy();
    }

}
