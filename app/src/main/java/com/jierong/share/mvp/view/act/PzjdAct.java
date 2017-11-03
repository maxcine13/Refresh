package com.jierong.share.mvp.view.act;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.page.AlibcBasePage;
import com.alibaba.baichuan.android.trade.page.AlibcPage;
import com.alibaba.baichuan.trade.biz.context.AlibcTradeResult;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jierong.share.BaseAct;
import com.jierong.share.R;
import com.jierong.share.mvp.model.info.TaoBaoInfo;
import com.jierong.share.mvp.presenter.PzjdListPresenter;
import com.jierong.share.mvp.view.IPzjdView;
import com.jierong.share.mvp.view.ada.PzjdAdapter;
import com.jierong.share.refresh.MaterialHeader;
import com.jierong.share.util.LogUtil;
import com.jierong.share.util.ToastUtils;
import com.jierong.share.widget.FastScrollManger;
import com.jierong.share.widget.LoadingDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import java.util.List;

/**
 * 品质家电界面
 */
public class PzjdAct extends BaseAct implements IPzjdView,
        BaseQuickAdapter.RequestLoadMoreListener, OnRefreshListener {
    private RelativeLayout bt_search;
    private FrameLayout bt_top;
    private Dialog mLoadingDialog;
    private LinearLayout emptyView, errorView;
    private TextView bt_sellNum, bt_priceUp, bt_priceDown;
    private SmartRefreshLayout refreshView;
    private RecyclerView mRecyclerView;
    private PzjdAdapter mPzjdAdapter;
    private List<TaoBaoInfo> data;
    int mPageIndex = 1, mSort = 1;
    boolean isClosed = false;       // 是否锁定自增
    private PzjdListPresenter mPzjdListPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_pzjd);

        init();
    }

    @Override
    public void onNetNo() { }

    @Override
    public void onNetOk() { }

    private void initAdapter() {
        mPzjdAdapter = new PzjdAdapter(data);
        mPzjdAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mPzjdAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mRecyclerView.setAdapter(mPzjdAdapter);

        mPzjdAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                TaoBaoInfo info = data.get(position);
                AlibcBasePage alibcBasePage = new AlibcPage(info.getClick_url());
                AlibcShowParams alibcShowParams = new AlibcShowParams(OpenType.Auto, false);
                AlibcTrade.show(PzjdAct.this, alibcBasePage, alibcShowParams, null, null, new AlibcTradeCallback() {
                    @Override
                    public void onTradeSuccess(AlibcTradeResult alibcTradeResult) {
                        LogUtil.d("onTradeSuccess !");
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        LogUtil.e(code + "_" + msg);
                    }
                });
            }
        });
        mPzjdAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                TaoBaoInfo info = data.get(position);
                AlibcBasePage alibcBasePage = new AlibcPage(info.getCoupon_click_url());
                AlibcShowParams alibcShowParams = new AlibcShowParams(OpenType.Auto, false);
                AlibcTrade.show(PzjdAct.this, alibcBasePage, alibcShowParams, null, null, new AlibcTradeCallback() {
                    @Override
                    public void onTradeSuccess(AlibcTradeResult alibcTradeResult) {
                        LogUtil.d("onTradeSuccess !");
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        LogUtil.e(code + "_" + msg);
                    }
                });
            }
        });
    }

    private void addHeadView() {
        View headView = getLayoutInflater().inflate(R.layout.item_pzjd_header,
                (ViewGroup) mRecyclerView.getParent(), false);
        bt_sellNum = (TextView) headView.findViewById(R.id.bt_sellNum);
        bt_priceUp = (TextView) headView.findViewById(R.id.bt_priceUp);
        bt_priceDown = (TextView) headView.findViewById(R.id.bt_priceDown);
        mPzjdAdapter.addHeaderView(headView);

        bt_sellNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSort = 1;
                onRefresh(refreshView);
            }
        });
        bt_priceUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSort = 2;
                onRefresh(refreshView);
            }
        });
        bt_priceDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSort = 3;
                onRefresh(refreshView);
            }
        });
    }

    private void init() {
        mPzjdListPresenter = new PzjdListPresenter(this);
        bt_search = (RelativeLayout) findViewById(R.id.bt_search);
        bt_top = (FrameLayout) findViewById(R.id.bt_top);
        emptyView = (LinearLayout) findViewById(R.id.emptyView);
        errorView = (LinearLayout) findViewById(R.id.errorView);
        refreshView = (SmartRefreshLayout) findViewById(R.id.refreshView);
        refreshView.setRefreshHeader(new MaterialHeader(PzjdAct.this)
                .setColorSchemeColors(getResources().getColor(R.color.bottom_text_click)));
        refreshView.setEnableHeaderTranslationContent(false);
        refreshView.setDisableContentWhenRefresh(true);
        refreshView.setOnRefreshListener(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);
        //mRecyclerView.setLayoutManager(new LinearLayoutManager(PzjdAct.this));
        mRecyclerView.setLayoutManager(new FastScrollManger(getMContext(),
                LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);
        initAdapter();
        addHeadView();
        onRefresh(refreshView);

        mRecyclerView.addOnScrollListener(new MyRecyclerViewScrollListener());
        emptyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRefresh(refreshView);
            }
        });
        errorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRefresh(refreshView);
            }
        });
        bt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent searchIntent = new Intent(PzjdAct.this, WebViewAct.class);
                searchIntent.putExtra("Url", "https://ai.m.taobao.com/index.html?pid=mm_123166441_28158373_109094623");
                startActivity(searchIntent);
            }
        });
        bt_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRecyclerView.smoothScrollToPosition(0);
            }
        });
    }

    @Override
    public void onLoadMoreRequested() {
        refreshView.setEnabled(false);
        if (!isClosed) mPageIndex ++;
        LogUtil.d(" mPageIndex：" + mPageIndex);
        mPzjdListPresenter.loadData();
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mPzjdAdapter.setEnableLoadMore(false);
        mPageIndex = 1;
        isClosed = false;
        LogUtil.d(" mPageIndex：" + mPageIndex);
        mPzjdListPresenter.loadData();
    }

    @Override
    public Context getMContext() {
        return PzjdAct.this;
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
    public int getPage() {
        return mPageIndex;
    }

    @Override
    public int getSort() {
        return mSort;
    }

    @Override
    public void refreshSuccess(List<TaoBaoInfo> data) {
        if(null == data) {
            if(emptyView.getVisibility() == View.GONE)
                emptyView.setVisibility(View.VISIBLE);
            if(errorView.getVisibility() == View.VISIBLE)
                errorView.setVisibility(View.GONE);
            if(refreshView.getVisibility() == View.VISIBLE)
                refreshView.setVisibility(View.GONE);
            mPzjdAdapter.getData().clear();
            refreshView.finishRefresh();
            mPzjdAdapter.setEnableLoadMore(true);
            return;
        }

        if(emptyView.getVisibility() == View.VISIBLE)
            emptyView.setVisibility(View.GONE);
        if(errorView.getVisibility() == View.VISIBLE)
            errorView.setVisibility(View.GONE);
        if(refreshView.getVisibility() == View.GONE)
            refreshView.setVisibility(View.VISIBLE);
        this.data = data;
        mPzjdAdapter.getData().clear();
        mPzjdAdapter.setNewData(data);
        refreshView.finishRefresh();
        mPzjdAdapter.setEnableLoadMore(true);
    }

    @Override
    public void loadSuccess(List<TaoBaoInfo> data) {
        if(null == data) {
            // 这页已经不存在更多数据
            loadEnd();
            return;
        }
        isClosed = false;
        mPzjdAdapter.addData(data);
        mPzjdAdapter.loadMoreComplete();
        refreshView.setEnabled(true);
    }

    @Override
    public void loadEnd() {
        isClosed = true;
        mPzjdAdapter.loadMoreEnd(false);
        refreshView.setEnabled(true);
    }

    @Override
    public void showError(String msg, boolean flag) {
        ToastUtils.show(getMContext(), msg);
        if(mPageIndex == 1) {
            LogUtil.e("刷新加载失败");
            if(emptyView.getVisibility() == View.VISIBLE)
                emptyView.setVisibility(View.GONE);
            if(errorView.getVisibility() == View.GONE)
                errorView.setVisibility(View.VISIBLE);
            if(refreshView.getVisibility() == View.VISIBLE)
                refreshView.setVisibility(View.GONE);
            mPzjdAdapter.getData().clear();
            mPzjdAdapter.notifyDataSetChanged();
            refreshView.finishRefresh();
            mPzjdAdapter.setEnableLoadMore(true);
        } else {
            LogUtil.e("加载加载失败");
            isClosed = true;
            mPzjdAdapter.loadMoreFail();
            refreshView.setEnabled(true);
        }
        if (flag) finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AlibcTradeSDK.destory();
        mPzjdListPresenter.closeHttp();
        if(refreshView.isRefreshing()) refreshView.finishRefresh();
    }

    private class MyRecyclerViewScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            LinearLayoutManager manager =
                    (LinearLayoutManager) recyclerView.getLayoutManager();
            int firstVisibleItemPosition = manager.findFirstVisibleItemPosition();
            // 当不滚动时
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                // 判断是否滚动超过一屏
                if (firstVisibleItemPosition == 0) {
                    bt_top.setVisibility(View.GONE);
                } else {
                    bt_top.setVisibility(View.VISIBLE);
                }
            } else if(newState == RecyclerView.SCROLL_STATE_DRAGGING) { // 拖动中
                bt_top.setVisibility(View.GONE);
            }
        }
    }

}
