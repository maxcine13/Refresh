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
import com.jierong.share.mvp.presenter.ShopFanListPresenter;
import com.jierong.share.mvp.view.IShopFanView;
import com.jierong.share.mvp.view.ada.ShopFanAdapter;
import com.jierong.share.util.LogUtil;
import com.jierong.share.util.StringUtil;
import com.jierong.share.util.ToastUtils;
import com.jierong.share.widget.LoadingDialog;
import java.util.List;

/**
 * 购物返利记录界面
 */
public class ShopFanAct extends BaseAct implements IShopFanView, BaseQuickAdapter.
        RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    private Dialog mLoadingDialog;
    private ShopFanListPresenter mShopFanListPresenter;
    private LinearLayout emptyView, errorView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView shop_money_may, shop_money_done;
    private RecyclerView mRecyclerView;
    private ShopFanAdapter mShopFanAdapter;
    private List<ShopInfo> data;     // 购物历史数据
    int mPageIndex = 1;
    boolean isClosed = false;       // 是否锁定自增

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_shop_fan);
        init();
    }

    private void initAdapter() {
        mShopFanAdapter = new ShopFanAdapter(data);
        mShopFanAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mShopFanAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mRecyclerView.setAdapter(mShopFanAdapter);
    }

    private void addHeadView() {
        View headView = getLayoutInflater().inflate(
                R.layout.item_shop_header_fan, (ViewGroup) mRecyclerView.getParent(), false);
        shop_money_may = (TextView) headView.findViewById(R.id.shop_money_may);
        shop_money_done = (TextView) headView.findViewById(R.id.shop_money_done);
        mShopFanAdapter.addHeaderView(headView);
    }

    private void init() {
        mShopFanListPresenter = new ShopFanListPresenter(this);
        ((TextView) findViewById(R.id.titleName)).setText(R.string.title_name_shop_fan);
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
        mShopFanAdapter.setEnableLoadMore(false);
        mPageIndex = 1;
        isClosed = false;
        mShopFanListPresenter.loadData(true);
    }

    @Override
    public void onLoadMoreRequested() {
        mSwipeRefreshLayout.setEnabled(false);
        if (!isClosed) mPageIndex ++;
        mShopFanListPresenter.loadData(false);
    }

    @Override
    public void onNetNo() { }

    @Override
    public void onNetOk() { }

    @Override
    public Context getMContext() {
        return ShopFanAct.this;
    }

    @Override
    public void showLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = LoadingDialog.createLoadingDialog(ShopFanAct.this, "正在加载中...");
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
    public void refreshSuccess(String maySum, String doneSum, List<ShopInfo> data) {
        hideLoading();
        if(StringUtil.isNotEmptyIgnoreBlank(maySum)) shop_money_may.setText(maySum);
        else shop_money_may.setText("0.00");
        if(StringUtil.isNotEmptyIgnoreBlank(doneSum)) shop_money_done.setText(doneSum);
        else shop_money_done.setText("0.00");

        if(null == data) {
            if(emptyView.getVisibility() == View.GONE)
                emptyView.setVisibility(View.VISIBLE);
            if(errorView.getVisibility() == View.VISIBLE)
                errorView.setVisibility(View.GONE);
            if(mSwipeRefreshLayout.getVisibility() == View.VISIBLE)
                mSwipeRefreshLayout.setVisibility(View.GONE);
            mShopFanAdapter.getData().clear();
            mSwipeRefreshLayout.setRefreshing(false);
            mShopFanAdapter.setEnableLoadMore(true);
            return;
        }

        if(emptyView.getVisibility() == View.VISIBLE)
            emptyView.setVisibility(View.GONE);
        if(errorView.getVisibility() == View.VISIBLE)
            errorView.setVisibility(View.GONE);
        if(mSwipeRefreshLayout.getVisibility() == View.GONE)
            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
        this.data = data;
        mShopFanAdapter.getData().clear();
        mShopFanAdapter.setNewData(data);
        mSwipeRefreshLayout.setRefreshing(false);
        mShopFanAdapter.setEnableLoadMore(true);
    }

    @Override
    public void loadSuccess(String maySum, String doneSum, List<ShopInfo> data) {
        if(StringUtil.isNotEmptyIgnoreBlank(maySum)) shop_money_may.setText(maySum);
        else shop_money_may.setText("0.00");
        if(StringUtil.isNotEmptyIgnoreBlank(doneSum)) shop_money_done.setText(doneSum);
        else shop_money_done.setText("0.00");

        if(null == data) {
            // 这页已经不存在更多数据
            loadEnd();
            return;
        }
        isClosed = false;
        mShopFanAdapter.addData(data);
        mShopFanAdapter.loadMoreComplete();
        mSwipeRefreshLayout.setEnabled(true);
    }

    @Override
    public void loadEnd() {
        isClosed = true;
        mShopFanAdapter.loadMoreEnd(false);
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
            mShopFanAdapter.getData().clear();
            mShopFanAdapter.notifyDataSetChanged();
            mSwipeRefreshLayout.setRefreshing(false);
            mShopFanAdapter.setEnableLoadMore(true);
        } else {
            LogUtil.e("加载加载失败");
            isClosed = true;
            mShopFanAdapter.loadMoreFail();
            mSwipeRefreshLayout.setEnabled(true);
        }
        if (flag) finish();
    }

    @Override
    protected void onDestroy() {
        hideLoading();
        mShopFanListPresenter.closeHttp();
        super.onDestroy();
    }

}
