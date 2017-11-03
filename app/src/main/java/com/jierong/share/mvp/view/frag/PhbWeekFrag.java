package com.jierong.share.mvp.view.frag;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jierong.share.BaseFrag;
import com.jierong.share.Constants;
import com.jierong.share.R;
import com.jierong.share.mvp.model.info.WeekBuyInfo;
import com.jierong.share.mvp.model.info.WeekClickInfo;
import com.jierong.share.mvp.presenter.PhbWeekPresenter;
import com.jierong.share.mvp.view.IPhbWeekView;
import com.jierong.share.mvp.view.ada.WeekBuyAdapter;
import com.jierong.share.mvp.view.ada.WeekClickAdapter;
import com.jierong.share.refresh.MaterialHeader;
import com.jierong.share.util.ToastUtils;
import com.jierong.share.widget.LoadingDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import java.util.List;

public class PhbWeekFrag extends BaseFrag implements IPhbWeekView, OnRefreshListener {
    private boolean beginToLoad = false;    // 是否加载数据
    private Dialog mLoadingDialog;
    private SmartRefreshLayout refreshView;
    private RecyclerView rv_list_click, rv_list_buy;
    private PhbWeekPresenter mPhbWeekPresenter;
    private WeekClickAdapter mWeekClickAdapter;
    private List<WeekClickInfo> clickData;
    private WeekBuyAdapter mWeekBuyAdapter;
    private List<WeekBuyInfo> buyData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_phb_week, null);
        initView(view);
        return view;
    }

    @Override
    public void onNetNo() { }

    @Override
    public void onNetOk() { }

    private void showLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = LoadingDialog.createLoadingDialog(getActivity(), "正在加载中...");
            mLoadingDialog.show();
        }
    }

    private void hideLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }

    private void initAdapter() {
        mWeekClickAdapter = new WeekClickAdapter(clickData);
        mWeekClickAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        rv_list_click.setAdapter(mWeekClickAdapter);

        mWeekBuyAdapter = new WeekBuyAdapter(buyData);
        mWeekBuyAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        rv_list_buy.setAdapter(mWeekBuyAdapter);
    }

    private void initView(View view) {
        mPhbWeekPresenter = new PhbWeekPresenter(this);
        rv_list_click = (RecyclerView) view.findViewById(R.id.rv_list_click);
        rv_list_buy = (RecyclerView) view.findViewById(R.id.rv_list_buy);
        refreshView = (SmartRefreshLayout) view.findViewById(R.id.refreshView);
        refreshView.setRefreshHeader(new MaterialHeader(getMContext())
                .setColorSchemeColors(getActivity().getResources().getColor(R.color.bottom_text_click)));
        refreshView.setEnableHeaderTranslationContent(false);
        refreshView.setDisableContentWhenRefresh(true);
        refreshView.setOnRefreshListener(this);

        rv_list_click.setLayoutManager(new LinearLayoutManager(getMContext()));
        rv_list_click.setHasFixedSize(true);
        rv_list_buy.setLayoutManager(new LinearLayoutManager(getMContext()));
        rv_list_buy.setHasFixedSize(true);
        initAdapter();
        beginToLoad = true;
        if(getUserVisibleHint()) {
            showLoading();
            mPhbWeekPresenter.loadData();
            beginToLoad = false;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(!beginToLoad) {

        } else {
            mPhbWeekPresenter.loadData();
            beginToLoad = false;
        }
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mPhbWeekPresenter.loadData();
    }

    @Override
    public Context getMContext() {
        return getActivity();
    }

    @Override
    public void getDataSuccess(String time, List<WeekClickInfo> click, List<WeekBuyInfo> buy) {
        Intent timeIntent = new Intent();
        timeIntent.setAction(Constants.Refresh_Phb_time);
        timeIntent.putExtra("updateType", "weekData");
        timeIntent.putExtra("weekTime", time);
        getActivity().sendBroadcast(timeIntent);
        hideLoading();
        if(refreshView.isRefreshing()) refreshView.finishRefresh();

        this.clickData = click;
        mWeekClickAdapter.getData().clear();
        mWeekClickAdapter.setNewData(clickData);

        this.buyData = buy;
        mWeekBuyAdapter.getData().clear();
        mWeekBuyAdapter.setNewData(buyData);
    }

    @Override
    public void showError(String msg, boolean flag) {
        hideLoading();
        ToastUtils.show(getMContext(), msg);
        if (flag) getActivity().finish();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        hideLoading();
        mPhbWeekPresenter.closeHttp();
    }

}
