package com.jierong.share.mvp.view.frag;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.jierong.share.BaseFrag;
import com.jierong.share.R;
import com.jierong.share.mvp.model.info.AdvTypeInfo;
import com.jierong.share.mvp.presenter.AdvPresenter;
import com.jierong.share.mvp.view.IAdvView;
import com.jierong.share.mvp.view.ada.AdvTypeAdapter;
import com.jierong.share.util.LogUtil;
import com.jierong.share.util.ToastUtils;
import com.jierong.share.widget.CustomRecycle;
import com.jierong.share.widget.LoadingDialog;
import com.jierong.share.widget.SpacesItemDecoration;
import java.util.List;

public class AdvFrag extends BaseFrag implements IAdvView, SwipeRefreshLayout.OnRefreshListener {
    private CustomRecycle typeLv;
    private AdvTypeAdapter mAdvTypeAdapter;
    private SwipeRefreshLayout refreshView;
    private RelativeLayout view_nonet;
    private boolean isClick = false;    // 当前是否在点击状态
    private LinearLayoutManager lm;
    private AdvPresenter mAdvPresenter;
    private Dialog mLoadingDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_adv, null);
        initView(view);
        return view;
    }

    public void setClick(boolean click) {
        isClick = click;
    }

    @Override
    public void onNetNo() {
        view_nonet.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNetOk() {
        view_nonet.setVisibility(View.GONE);
    }

    private void initView(View view) {
        mAdvPresenter = new AdvPresenter(this);
        ((TextView) view.findViewById(R.id.titleName)).setText(R.string.title_name_tao);
        view.findViewById(R.id.titleBack).setVisibility(View.GONE);
        view_nonet = (RelativeLayout) view.findViewById(R.id.view_nonet);
        refreshView = (SwipeRefreshLayout) view.findViewById(R.id.refreshView);
        refreshView.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        typeLv = (CustomRecycle) view.findViewById(R.id.typeLv);
        lm = new LinearLayoutManager(getActivity());
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        typeLv.setLayoutManager(lm);
        refreshView.setOnRefreshListener(this);
        typeLv.addItemDecoration(new SpacesItemDecoration(1));
        mAdvPresenter.getAdvListData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hideLoading();
        mAdvPresenter.closeHttp();
    }

    @Override
    public void onRefresh() {
        mAdvPresenter.getAdvListData();
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
    public void receiveAdvData(List<AdvTypeInfo> advTypeInfos) {
        LogUtil.d("advTypeInfos" + advTypeInfos.size());
        refreshView.setRefreshing(false);
        // 绘制广告分类列表
        mAdvTypeAdapter = new AdvTypeAdapter(getActivity(), advTypeInfos);
        typeLv.setAdapter(mAdvTypeAdapter);
        typeLv.setMinimumHeight(lm.getHeight());
    }

    @Override
    public void showError(String msg, boolean flag) {
        refreshView.setRefreshing(false);
        ToastUtils.show(getActivity(), msg);
    }

}