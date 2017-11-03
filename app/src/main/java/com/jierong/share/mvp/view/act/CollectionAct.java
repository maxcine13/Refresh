package com.jierong.share.mvp.view.act;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.jierong.share.BaseAct;
import com.jierong.share.R;
import com.jierong.share.mvp.model.info.CollectionInfo;
import com.jierong.share.mvp.presenter.CollectionPresenter;
import com.jierong.share.mvp.view.ICollectionView;
import com.jierong.share.mvp.view.ada.CollectionAdapter;
import com.jierong.share.util.ToastUtils;
import com.jierong.share.widget.LoadingDialog;
import com.jierong.share.widget.SpacesItemDecoration;
import java.util.ArrayList;
import java.util.List;
import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 我的收藏界面
 */
public class CollectionAct extends BaseAct implements ICollectionView, BGARefreshLayout.BGARefreshLayoutDelegate {
    private Dialog mLoadingDialog;
    private BGARefreshLayout mBGARefreshLayout;
    private RecyclerView mRecyclerView;
    private CollectionAdapter mCollectionAdapter;
    private CollectionPresenter mCollectionPresenter;
    private List<CollectionInfo> list = new ArrayList<>();
    private String tid;
    private int mPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_collection);
        init();

    }


    private void init() {
        mCollectionPresenter = new CollectionPresenter(this);
        ((TextView) findViewById(R.id.titleName)).setText(R.string.title_name_collection);
        findViewById(R.id.titleBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mBGARefreshLayout = (BGARefreshLayout) findViewById(R.id.rl_modulename_refresh);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycleview);
        //添加自定义的分隔线
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(2));
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getMContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        //设置适配器
        mCollectionAdapter = new CollectionAdapter(mRecyclerView, CollectionAct.this);
        mRecyclerView.setAdapter(mCollectionAdapter);
        //设置刷新的样式(此处true为上拉加载可用,默认发false即上拉加载更多不可用)
        mBGARefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getMContext(), false));
        mBGARefreshLayout.setDelegate(this);
        mCollectionPresenter.getCollectionData();
    }


    @Override
    public void onNetNo() {
        //当没有网络时,把请求的刷新请求关掉
        mBGARefreshLayout.endRefreshing();
    }

    @Override
    public void onNetOk() { }

    @Override
    public Context getMContext() {
        return CollectionAct.this;
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
    public String getTid() {
        return tid;
    }

    @Override
    public void receiveData(String data) {
        loadData(data);
    }

    @Override
    public void doCancelSuccess() {
        mCollectionAdapter.removeItem(mPosition);
    }

    @Override
    public void showError(String msg, boolean flag) {
        mBGARefreshLayout.endRefreshing();
        ToastUtils.show(getMContext(), msg);
        if (flag) finish();
    }

    /**
     * 下拉刷新
     * @param refreshLayout
     */
    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mCollectionPresenter.getCollectionData();
    }

    /**
     * 上拉加载更多
     * @param refreshLayout
     * @return
     */
    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }

    /**
     * 获取最新的数据,并将值赋给RecycleView
     * @param json
     */
    private void loadData(String json) {
        if (!json.equals("[]")) {
            list.clear();
            final List<CollectionInfo> infoList = CollectionInfo.fromJSONS(json);
            if (infoList.size() > 0) {
                list.addAll(infoList);
                mCollectionAdapter.setData(list);
                mCollectionAdapter.setOnItemChildClickListener(new BGAOnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(ViewGroup parent, View childView, int position) {
                        tid = infoList.get(position).getTalent_id();
                        mPosition = position;
                        mCollectionPresenter.doCancel();
                    }
                });

            }
        } else {
            //没数据
            showError("暂无数据", false);
        }
        mBGARefreshLayout.endRefreshing();
    }

    @Override
    public void onDestroy() {
        hideLoading();
        mCollectionPresenter.closeHttp();
        super.onDestroy();
    }

}
