package com.jierong.share.mvp.view.act;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jierong.share.BaseAct;
import com.jierong.share.Constants;
import com.jierong.share.R;
import com.jierong.share.mvp.model.info.MessageInfo;
import com.jierong.share.mvp.presenter.MessagePresenter;
import com.jierong.share.mvp.view.IMessageView;
import com.jierong.share.mvp.view.ada.MessageAdapter;
import com.jierong.share.refresh.MaterialHeader;
import com.jierong.share.util.AppPreferences;
import com.jierong.share.util.LogUtil;
import com.jierong.share.util.ToastUtils;
import com.jierong.share.widget.LoadingDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import java.util.List;

/**
 * 我的消息界面
 */
public class MyMessageAct extends BaseAct implements IMessageView,
        BaseQuickAdapter.RequestLoadMoreListener, OnRefreshListener {
    private Dialog mLoadingDialog;
    private LinearLayout emptyView, errorView;
    private RecyclerView mRecyclerView;
    private MessageAdapter mMessageAdapter;
    private SmartRefreshLayout refreshView;
    private List<MessageInfo> data;
    private MessagePresenter mMessagePresenter;
    int mPageIndex = 1;
    boolean isClosed = false;       // 是否锁定自增

    int times;  // 界面启动次数
    BroadcastReceiver refreshReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Constants.Push_Red_Packet)) {
                LogUtil.d("MyMessageAct - 红包处理：" + intent.getStringExtra("Data"));
                if(null == refreshView) return;
                onRefresh(refreshView);

                AppPreferences.putBoolean(getMContext(), "isShowRedPacket", false);
                Intent redPacket = new Intent();
                redPacket.setAction(Constants.Refresh_Red_Packet_Unread);
                sendBroadcast(redPacket);
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_message);
        init();

        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.Push_Red_Packet);
        registerReceiver(refreshReceiver, filter);
    }

    @Override
    public void onNetNo() { }

    @Override
    public void onNetOk() { }

    private void initAdapter() {
        mMessageAdapter = new MessageAdapter(data);
        mMessageAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mMessageAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mRecyclerView.setAdapter(mMessageAdapter);
    }

    private void init() {
        mMessagePresenter = new MessagePresenter(this);
        ((TextView) findViewById(R.id.titleName)).setText("我的消息");
        emptyView = (LinearLayout) findViewById(R.id.emptyView);
        errorView = (LinearLayout) findViewById(R.id.errorView);
        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);
        refreshView = (SmartRefreshLayout) findViewById(R.id.refreshView);
        refreshView.setRefreshHeader(new MaterialHeader(this)
                .setColorSchemeColors(getResources().getColor(R.color.bottom_text_click)));
        refreshView.setEnableHeaderTranslationContent(false);
        refreshView.setDisableContentWhenRefresh(true);
        refreshView.setOnRefreshListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        initAdapter();
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
        findViewById(R.id.titleBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mMessageAdapter.setEnableLoadMore(false);
        mPageIndex = 1;
        isClosed = false;
        LogUtil.d("mPageIndex：" + mPageIndex);
        mMessagePresenter.loadData(true);
    }

    @Override
    public void onLoadMoreRequested() {
        refreshView.setEnabled(false);
        if (!isClosed) mPageIndex ++;
        LogUtil.d("mPageIndex：" + mPageIndex);
        mMessagePresenter.loadData(false);
    }

    @Override
    public Context getMContext() {
        return MyMessageAct.this;
    }

    @Override
    public void showLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = LoadingDialog.createLoadingDialog(MyMessageAct.this, "正在加载中...");
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
    public void refreshSuccess(List<MessageInfo> data) {
        if(null == data) {
            if(emptyView.getVisibility() == View.GONE)
                emptyView.setVisibility(View.VISIBLE);
            if(errorView.getVisibility() == View.VISIBLE)
                errorView.setVisibility(View.GONE);
            if(refreshView.getVisibility() == View.VISIBLE)
                refreshView.setVisibility(View.GONE);
            mMessageAdapter.getData().clear();
            refreshView.finishRefresh();
            mMessageAdapter.setEnableLoadMore(true);
            return;
        }

        if(emptyView.getVisibility() == View.VISIBLE)
            emptyView.setVisibility(View.GONE);
        if(errorView.getVisibility() == View.VISIBLE)
            errorView.setVisibility(View.GONE);
        if(refreshView.getVisibility() == View.GONE)
            refreshView.setVisibility(View.VISIBLE);
        this.data = data;
        mMessageAdapter.getData().clear();
        mMessageAdapter.setNewData(data);
        refreshView.finishRefresh();
        mMessageAdapter.setEnableLoadMore(true);
    }

    @Override
    public void loadSuccess(List<MessageInfo> data) {
        if(null == data) {
            // 这页已经不存在更多数据
            loadEnd();
            return;
        }
        isClosed = false;
        mMessageAdapter.addData(data);
        mMessageAdapter.loadMoreComplete();
        refreshView.setEnabled(true);
    }

    @Override
    public void loadEnd() {
        isClosed = true;
        mMessageAdapter.loadMoreEnd(false);
        refreshView.setEnabled(true);
    }

    @Override
    public void showError(String msg, boolean flag, boolean isRefresh) {
        ToastUtils.show(getMContext(), msg);
        if(isRefresh) {
            LogUtil.e("刷新加载失败");
            if(emptyView.getVisibility() == View.VISIBLE)
                emptyView.setVisibility(View.GONE);
            if(errorView.getVisibility() == View.GONE)
                errorView.setVisibility(View.VISIBLE);
            if(refreshView.getVisibility() == View.VISIBLE)
                refreshView.setVisibility(View.GONE);
            mMessageAdapter.getData().clear();
            mMessageAdapter.notifyDataSetChanged();
            refreshView.finishRefresh();
            mMessageAdapter.setEnableLoadMore(true);
        } else {
            LogUtil.e("加载加载失败");
            isClosed = true;
            mMessageAdapter.loadMoreFail();
            refreshView.setEnabled(true);
        }
        if (flag) finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        times ++;
        LogUtil.d("times：" + times);
        if(times > 1 && AppPreferences.getBoolean(getMContext(), "needUpdateRead")) {
            if(null == refreshView) return;
            onRefresh(refreshView);

            AppPreferences.putBoolean(getMContext(), "isShowRedPacket", false);
            Intent redPacket = new Intent();
            redPacket.setAction(Constants.Refresh_Red_Packet_Unread);
            sendBroadcast(redPacket);

            AppPreferences.putBoolean(getMContext(), "needUpdateRead", false);
        }
    }

    @Override
    protected void onDestroy() {
        hideLoading();
        mMessagePresenter.closeHttp();
        super.onDestroy();
        if (refreshReceiver != null) {
            unregisterReceiver(refreshReceiver);
        }
    }

}
