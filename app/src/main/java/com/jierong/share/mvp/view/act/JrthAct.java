package com.jierong.share.mvp.view.act;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.page.AlibcBasePage;
import com.alibaba.baichuan.android.trade.page.AlibcPage;
import com.alibaba.baichuan.trade.biz.context.AlibcTradeResult;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jierong.share.BaseAct;
import com.jierong.share.R;
import com.jierong.share.mvp.model.info.TaoBaoInfo;
import com.jierong.share.mvp.presenter.JrthListPresenter;
import com.jierong.share.mvp.view.IJrthView;
import com.jierong.share.mvp.view.ada.JrthAdapter;
import com.jierong.share.refresh.MaterialHeader;
import com.jierong.share.util.LogUtil;
import com.jierong.share.util.ToastUtils;
import com.jierong.share.widget.LoadingDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import java.util.List;

/**
 * 今日特惠界面
 */
public class JrthAct extends BaseAct implements IJrthView,
        BaseQuickAdapter.RequestLoadMoreListener, OnRefreshListener {
    private Dialog mLoadingDialog;
    private LinearLayout emptyView, errorView;
    private ImageView img_logo;
    private TextView bt_sellNum, bt_priceUp, bt_priceDown;
    private SmartRefreshLayout refreshView;
    private RecyclerView mRecyclerView;
    private JrthAdapter mJrthAdapter;
    private List<TaoBaoInfo> data;
    int mPageIndex = 1, mSort = 1;
    boolean isClosed = false;       // 是否锁定自增
    private JrthListPresenter mJrthListPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_jrth);

        init();
    }

    @Override
    public void onNetNo() { }

    @Override
    public void onNetOk() { }

    private void initAdapter() {
        mJrthAdapter = new JrthAdapter(data);
        mJrthAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mJrthAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mRecyclerView.setAdapter(mJrthAdapter);

        mJrthAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                TaoBaoInfo info = data.get(position);
                AlibcBasePage alibcBasePage = new AlibcPage(info.getClick_url());
                AlibcShowParams alibcShowParams = new AlibcShowParams(OpenType.Auto, false);
                AlibcTrade.show(JrthAct.this, alibcBasePage, alibcShowParams, null, null, new AlibcTradeCallback() {
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
        mJrthAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                TaoBaoInfo info = data.get(position);
                AlibcBasePage alibcBasePage = new AlibcPage(info.getCoupon_click_url());
                AlibcShowParams alibcShowParams = new AlibcShowParams(OpenType.Auto, false);
                AlibcTrade.show(JrthAct.this, alibcBasePage, alibcShowParams, null, null, new AlibcTradeCallback() {
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
        View headView = getLayoutInflater().inflate(R.layout.item_jrth_header,
                (ViewGroup) mRecyclerView.getParent(), false);
        img_logo = (ImageView) headView.findViewById(R.id.img_logo);
        bt_sellNum = (TextView) headView.findViewById(R.id.bt_sellNum);
        bt_priceUp = (TextView) headView.findViewById(R.id.bt_priceUp);
        bt_priceDown = (TextView) headView.findViewById(R.id.bt_priceDown);
        mJrthAdapter.addHeaderView(headView);

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
        mJrthListPresenter = new JrthListPresenter(this);
        emptyView = (LinearLayout) findViewById(R.id.emptyView);
        errorView = (LinearLayout) findViewById(R.id.errorView);
        refreshView = (SmartRefreshLayout) findViewById(R.id.refreshView);
        refreshView.setRefreshHeader(new MaterialHeader(JrthAct.this)
                .setColorSchemeColors(getResources().getColor(R.color.bottom_text_click)));
        refreshView.setEnableHeaderTranslationContent(false);
        refreshView.setDisableContentWhenRefresh(true);
        refreshView.setOnRefreshListener(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(JrthAct.this));
        mRecyclerView.setHasFixedSize(true);
        initAdapter();
        addHeadView();
        onRefresh(refreshView);

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
    }

    @Override
    public void onLoadMoreRequested() {
        refreshView.setEnabled(false);
        if (!isClosed) mPageIndex ++;
        LogUtil.d(" mPageIndex：" + mPageIndex);
        mJrthListPresenter.loadData();
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mJrthAdapter.setEnableLoadMore(false);
        mPageIndex = 1;
        isClosed = false;
        LogUtil.d(" mPageIndex：" + mPageIndex);
        mJrthListPresenter.loadData();
    }

    @Override
    public Context getMContext() {
        return JrthAct.this;
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
    public void refreshSuccess(List<TaoBaoInfo> data, String img) {
        if(null == data) {
            if(emptyView.getVisibility() == View.GONE)
                emptyView.setVisibility(View.VISIBLE);
            if(errorView.getVisibility() == View.VISIBLE)
                errorView.setVisibility(View.GONE);
            if(refreshView.getVisibility() == View.VISIBLE)
                refreshView.setVisibility(View.GONE);
            mJrthAdapter.getData().clear();
            refreshView.finishRefresh();
            mJrthAdapter.setEnableLoadMore(true);
            return;
        }

        if(emptyView.getVisibility() == View.VISIBLE)
            emptyView.setVisibility(View.GONE);
        if(errorView.getVisibility() == View.VISIBLE)
            errorView.setVisibility(View.GONE);
        if(refreshView.getVisibility() == View.GONE)
            refreshView.setVisibility(View.VISIBLE);
        this.data = data;
        mJrthAdapter.getData().clear();
        mJrthAdapter.setNewData(data);
        refreshView.finishRefresh();
        mJrthAdapter.setEnableLoadMore(true);

        Glide.with(getMContext())
                .load(img)
                //.placeholder(Color.parseColor("#3577ef"))
                //.bitmapTransform(new CropCircleTransformation(mContext))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(img_logo);
    }

    @Override
    public void loadSuccess(List<TaoBaoInfo> data) {
        if(null == data) {
            // 这页已经不存在更多数据
            loadEnd();
            return;
        }
        isClosed = false;
        mJrthAdapter.addData(data);
        mJrthAdapter.loadMoreComplete();
        refreshView.setEnabled(true);
    }

    @Override
    public void loadEnd() {
        isClosed = true;
        mJrthAdapter.loadMoreEnd(false);
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
            mJrthAdapter.getData().clear();
            mJrthAdapter.notifyDataSetChanged();
            refreshView.finishRefresh();
            mJrthAdapter.setEnableLoadMore(true);
        } else {
            LogUtil.e("加载加载失败");
            isClosed = true;
            mJrthAdapter.loadMoreFail();
            refreshView.setEnabled(true);
        }
        if (flag) finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AlibcTradeSDK.destory();
        mJrthListPresenter.closeHttp();
        if(refreshView.isRefreshing()) refreshView.finishRefresh();
    }

}
