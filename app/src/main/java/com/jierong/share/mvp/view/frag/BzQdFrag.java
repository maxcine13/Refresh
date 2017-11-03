package com.jierong.share.mvp.view.frag;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jierong.share.BaseFrag;
import com.jierong.share.R;
import com.jierong.share.mvp.model.info.ShopInfo;
import com.jierong.share.mvp.presenter.BzQdListPresenter;
import com.jierong.share.mvp.view.IBzQdView;
import com.jierong.share.mvp.view.ada.BzQdAdapter;
import com.jierong.share.util.LogUtil;
import com.jierong.share.util.StringUtil;
import com.jierong.share.util.ToastUtils;
import com.jierong.share.widget.LoadingDialog;
import java.util.List;

public class BzQdFrag extends BaseFrag implements IBzQdView, BaseQuickAdapter.
        RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    private boolean beginToLoad = false;    // 是否加载数据
    private Dialog mLoadingDialog;
    private BzQdListPresenter mBzQdListPresenter;
    private LinearLayout emptyView, errorView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView bz_money_total;
    private RecyclerView mRecyclerView;
    private BzQdAdapter mBzQdAdapter;
    private List<ShopInfo> data;     // 购物历史数据
    int mPageIndex = 1;
    boolean isClosed = false;       // 是否锁定自增

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_bz_qd, null);
        initView(view);
        return view;
    }

    @Override
    public void onNetNo() { }

    @Override
    public void onNetOk() { }

    private void initAdapter() {
        mBzQdAdapter = new BzQdAdapter(data);
        mBzQdAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mBzQdAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mRecyclerView.setAdapter(mBzQdAdapter);
    }

    private void addHeadView() {
        View headView = getActivity().getLayoutInflater().inflate(
                R.layout.item_bz_header, (ViewGroup) mRecyclerView.getParent(), false);
        bz_money_total = (TextView) headView.findViewById(R.id.bz_money_total);
        mBzQdAdapter.addHeaderView(headView);
    }

    private void initView(View view) {
        mBzQdListPresenter = new BzQdListPresenter(this);
        emptyView = (LinearLayout) view.findViewById(R.id.emptyView);
        errorView = (LinearLayout) view.findViewById(R.id.errorView);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshView);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.bottom_text_click));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getMContext()));
        mRecyclerView.setHasFixedSize(true);
        initAdapter();
        addHeadView();
        beginToLoad = true;
        if(getUserVisibleHint()) {
            showLoading();
            onRefresh();
            beginToLoad = false;
        }

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
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(!beginToLoad) {

        } else {
            showLoading();
            onRefresh();
            beginToLoad = false;
        }
    }

    @Override
    public void onRefresh() {
        mBzQdAdapter.setEnableLoadMore(false);
        mPageIndex = 1;
        isClosed = false;
        mBzQdListPresenter.loadData(true);
    }

    @Override
    public void onLoadMoreRequested() {
        mSwipeRefreshLayout.setEnabled(false);
        if (!isClosed) mPageIndex ++;
        mBzQdListPresenter.loadData(false);
    }

    @Override
    public Context getMContext() {
        return getActivity();
    }

    @Override
    public void showLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = LoadingDialog.createLoadingDialog(getActivity(), "正在加载中...");
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
        if(StringUtil.isNotEmptyIgnoreBlank(sum)) bz_money_total.setText(sum);
        else bz_money_total.setText("0.00");

        if(null == data) {
            if(emptyView.getVisibility() == View.GONE)
                emptyView.setVisibility(View.VISIBLE);
            if(errorView.getVisibility() == View.VISIBLE)
                errorView.setVisibility(View.GONE);
            if(mSwipeRefreshLayout.getVisibility() == View.VISIBLE)
                mSwipeRefreshLayout.setVisibility(View.GONE);
            mBzQdAdapter.getData().clear();
            mSwipeRefreshLayout.setRefreshing(false);
            mBzQdAdapter.setEnableLoadMore(true);
            return;
        }

        if(emptyView.getVisibility() == View.VISIBLE)
            emptyView.setVisibility(View.GONE);
        if(errorView.getVisibility() == View.VISIBLE)
            errorView.setVisibility(View.GONE);
        if(mSwipeRefreshLayout.getVisibility() == View.GONE)
            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
        this.data = data;
        mBzQdAdapter.getData().clear();
        mBzQdAdapter.setNewData(data);
        mSwipeRefreshLayout.setRefreshing(false);
        mBzQdAdapter.setEnableLoadMore(true);
    }

    @Override
    public void loadSuccess(String sum, List<ShopInfo> data) {
        if(StringUtil.isNotEmptyIgnoreBlank(sum)) bz_money_total.setText(sum);
        else bz_money_total.setText("0.00");

        if(null == data) {
            // 这页已经不存在更多数据
            loadEnd();
            return;
        }
        isClosed = false;
        mBzQdAdapter.addData(data);
        mBzQdAdapter.loadMoreComplete();
        mSwipeRefreshLayout.setEnabled(true);
    }

    @Override
    public void loadEnd() {
        isClosed = true;
        mBzQdAdapter.loadMoreEnd(false);
        mSwipeRefreshLayout.setEnabled(true);
    }

    @Override
    public void showError(String msg, boolean flag, boolean isRefresh) {
        ToastUtils.show(getMContext(), msg);
        if(isRefresh) {
            LogUtil.e("刷新加载失败");
            hideLoading();
            if(emptyView.getVisibility() == View.VISIBLE)
                emptyView.setVisibility(View.GONE);
            if(errorView.getVisibility() == View.GONE)
                errorView.setVisibility(View.VISIBLE);
            if(mSwipeRefreshLayout.getVisibility() == View.VISIBLE)
                mSwipeRefreshLayout.setVisibility(View.GONE);
            mBzQdAdapter.getData().clear();
            mBzQdAdapter.notifyDataSetChanged();
            mSwipeRefreshLayout.setRefreshing(false);
            mBzQdAdapter.setEnableLoadMore(true);
        } else {
            LogUtil.e("加载加载失败");
            isClosed = true;
            mBzQdAdapter.loadMoreFail();
            mSwipeRefreshLayout.setEnabled(true);
        }
        if (flag) getActivity().finish();
    }

    @Override
    public void onDestroyView() {
        hideLoading();
        mBzQdListPresenter.closeHttp();
        super.onDestroyView();
    }

}
