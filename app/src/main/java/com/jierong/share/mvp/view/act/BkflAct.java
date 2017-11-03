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
import android.widget.Button;
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
import com.jierong.share.mvp.presenter.BkflListPresenter;
import com.jierong.share.mvp.view.IBkflView;
import com.jierong.share.mvp.view.ada.BkflAdapter;
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
 * 爆款返利界面
 */
public class BkflAct extends BaseAct implements IBkflView, View.OnClickListener,
        BaseQuickAdapter.RequestLoadMoreListener, OnRefreshListener {
    private RelativeLayout topbar;
    private FrameLayout bt_top;
    private Dialog mLoadingDialog;
    private LinearLayout emptyView, errorView;
    private TextView tip_name, bt_sellNum, bt_priceUp, bt_priceDown;
    private SmartRefreshLayout refreshView;
    private RecyclerView mRecyclerView;
    private BkflAdapter mBkflAdapter;
    private List<TaoBaoInfo> data;
    int mPageIndex = 1, mSort = 1;
    private String mCategory = "allgood";
    boolean isClosed = false;       // 是否锁定自增
    private BkflListPresenter mBkflListPresenter;

    private Button button2, button3, button4, button5, button6, button7, button8,
            button9, button10, button11, button12, button13, button14, button15,
            button16, button17, button18, button19, button20;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_bkfl);

        init();
    }

    @Override
    public void onNetNo() { }

    @Override
    public void onNetOk() { }

    private void initAdapter() {
        mBkflAdapter = new BkflAdapter(data);
        mBkflAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mBkflAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mRecyclerView.setAdapter(mBkflAdapter);

        mBkflAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                TaoBaoInfo info = data.get(position);
                AlibcBasePage alibcBasePage = new AlibcPage(info.getClick_url());
                AlibcShowParams alibcShowParams = new AlibcShowParams(OpenType.Auto, false);
                AlibcTrade.show(BkflAct.this, alibcBasePage, alibcShowParams, null, null, new AlibcTradeCallback() {
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
        mBkflAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                TaoBaoInfo info = data.get(position);
                AlibcBasePage alibcBasePage = new AlibcPage(info.getCoupon_click_url());
                AlibcShowParams alibcShowParams = new AlibcShowParams(OpenType.Auto, false);
                AlibcTrade.show(BkflAct.this, alibcBasePage, alibcShowParams, null, null, new AlibcTradeCallback() {
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
        View headView = getLayoutInflater().inflate(R.layout.item_bkfl_header,
                (ViewGroup) mRecyclerView.getParent(), false);
        tip_name = (TextView) headView.findViewById(R.id.tip_name);
        bt_sellNum = (TextView) headView.findViewById(R.id.bt_sellNum);
        bt_priceUp = (TextView) headView.findViewById(R.id.bt_priceUp);
        bt_priceDown = (TextView) headView.findViewById(R.id.bt_priceDown);
        mBkflAdapter.addHeaderView(headView);

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
        mBkflListPresenter = new BkflListPresenter(this);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        button6 = (Button) findViewById(R.id.button6);
        button7 = (Button) findViewById(R.id.button7);
        button8 = (Button) findViewById(R.id.button8);
        button9 = (Button) findViewById(R.id.button9);
        button10 = (Button) findViewById(R.id.button10);
        button11 = (Button) findViewById(R.id.button11);
        button12 = (Button) findViewById(R.id.button12);
        button13 = (Button) findViewById(R.id.button13);
        button14 = (Button) findViewById(R.id.button14);
        button15 = (Button) findViewById(R.id.button15);
        button16 = (Button) findViewById(R.id.button16);
        button17 = (Button) findViewById(R.id.button17);
        button18 = (Button) findViewById(R.id.button18);
        button19 = (Button) findViewById(R.id.button19);
        button20 = (Button) findViewById(R.id.button20);
        topbar = (RelativeLayout) findViewById(R.id.topbar);
        bt_top = (FrameLayout) findViewById(R.id.bt_top);
        emptyView = (LinearLayout) findViewById(R.id.emptyView);
        errorView = (LinearLayout) findViewById(R.id.errorView);
        refreshView = (SmartRefreshLayout) findViewById(R.id.refreshView);
        refreshView.setRefreshHeader(new MaterialHeader(BkflAct.this)
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
        topbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent searchIntent = new Intent(BkflAct.this, WebViewAct.class);
                searchIntent.putExtra("Url",
                        "https://temai.m.taobao.com/new/index.htm?pid=mm_123166441_28158373_109094623");
                startActivity(searchIntent);
            }
        });
        bt_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRecyclerView.smoothScrollToPosition(0);
            }
        });

        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);
        button10.setOnClickListener(this);
        button11.setOnClickListener(this);
        button12.setOnClickListener(this);
        button13.setOnClickListener(this);
        button14.setOnClickListener(this);
        button15.setOnClickListener(this);
        button16.setOnClickListener(this);
        button17.setOnClickListener(this);
        button18.setOnClickListener(this);
        button19.setOnClickListener(this);
        button20.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button2: {
                mCategory = "allgood";
                tip_name.setText("- - 全部 - -");
                onRefresh(refreshView);
                break;
            }
            case R.id.button3: {
                mCategory = "girl";
                tip_name.setText("- - 女装 - -");
                onRefresh(refreshView);
                break;
            }
            case R.id.button4: {
                mCategory = "boy";
                tip_name.setText("- - 男装 - -");
                onRefresh(refreshView);
                break;
            }
            case R.id.button5: {
                mCategory = "shoes";
                tip_name.setText("- - 鞋包 - -");
                onRefresh(refreshView);
                break;
            }
            case R.id.button6: {
                mCategory = "food";
                tip_name.setText("- - 食品 - -");
                onRefresh(refreshView);
                break;
            }
            case R.id.button7: {
                mCategory = "beau";
                tip_name.setText("- - 美妆 - -");
                onRefresh(refreshView);
                break;
            }
            case R.id.button8: {
                mCategory = "ha";
                tip_name.setText("- - 家电 - -");
                onRefresh(refreshView);
                break;
            }case R.id.button9: {
                mCategory = "jd";
                tip_name.setText("- - 珠宝配饰 - -");
                onRefresh(refreshView);
                break;
            }
            case R.id.button10: {
                mCategory = "sport";
                tip_name.setText("- - 运动户外 - -");
                onRefresh(refreshView);
                break;
            }
            case R.id.button11: {
                mCategory = "mson";
                tip_name.setText("- - 母婴 - -");
                onRefresh(refreshView);
                break;
            }
            case R.id.button12: {
                mCategory = "clothes";
                tip_name.setText("- - 内衣 - -");
                onRefresh(refreshView);
                break;
            }
            case R.id.button13: {
                mCategory = "digital";
                tip_name.setText("- - 数码 - -");
                onRefresh(refreshView);
                break;
            }
            case R.id.button14: {
                mCategory = "hi";
                tip_name.setText("- - 家装 - -");
                onRefresh(refreshView);
                break;
            }
            case R.id.button15: {
                mCategory = "hp";
                tip_name.setText("- - 居家用品 - -");
                onRefresh(refreshView);
                break;
            }
            case R.id.button16: {
                mCategory = "car";
                tip_name.setText("- - 汽车 - -");
                onRefresh(refreshView);
                break;
            }
            case R.id.button17: {
                mCategory = "server";
                tip_name.setText("- - 生活服务 - -");
                onRefresh(refreshView);
                break;
            }
            case R.id.button18: {
                mCategory = "vedio";
                tip_name.setText("- - 图书音像 - -");
                onRefresh(refreshView);
                break;
            }
            case R.id.button19: {
                mCategory = "play";
                tip_name.setText("- - 游戏话费 - -");
                onRefresh(refreshView);
                break;
            }
            case R.id.button20: {
                mCategory = "other";
                tip_name.setText("- - 其他 - -");
                onRefresh(refreshView);
                break;
            }
            default: break;
        }
    }

    @Override
    public void onLoadMoreRequested() {
        refreshView.setEnabled(false);
        if (!isClosed) mPageIndex ++;
        LogUtil.d(" mPageIndex：" + mPageIndex);
        mBkflListPresenter.loadData();
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mBkflAdapter.setEnableLoadMore(false);
        mPageIndex = 1;
        isClosed = false;
        LogUtil.d(" mPageIndex：" + mPageIndex);
        mBkflListPresenter.loadData();
    }

    @Override
    public Context getMContext() {
        return BkflAct.this;
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
    public String getCategory() {
        return mCategory;
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
            mBkflAdapter.getData().clear();
            refreshView.finishRefresh();
            mBkflAdapter.setEnableLoadMore(true);
            return;
        }

        if(emptyView.getVisibility() == View.VISIBLE)
            emptyView.setVisibility(View.GONE);
        if(errorView.getVisibility() == View.VISIBLE)
            errorView.setVisibility(View.GONE);
        if(refreshView.getVisibility() == View.GONE)
            refreshView.setVisibility(View.VISIBLE);
        this.data = data;
        mBkflAdapter.getData().clear();
        mBkflAdapter.setNewData(data);
        refreshView.finishRefresh();
        mBkflAdapter.setEnableLoadMore(true);
    }

    @Override
    public void loadSuccess(List<TaoBaoInfo> data) {
        if(null == data) {
            // 这页已经不存在更多数据
            loadEnd();
            return;
        }
        isClosed = false;
        mBkflAdapter.addData(data);
        mBkflAdapter.loadMoreComplete();
        refreshView.setEnabled(true);
    }

    @Override
    public void loadEnd() {
        isClosed = true;
        mBkflAdapter.loadMoreEnd(false);
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
            mBkflAdapter.getData().clear();
            mBkflAdapter.notifyDataSetChanged();
            refreshView.finishRefresh();
            mBkflAdapter.setEnableLoadMore(true);
        } else {
            LogUtil.e("加载加载失败");
            isClosed = true;
            mBkflAdapter.loadMoreFail();
            refreshView.setEnabled(true);
        }
        if (flag) finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AlibcTradeSDK.destory();
        mBkflListPresenter.closeHttp();
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
