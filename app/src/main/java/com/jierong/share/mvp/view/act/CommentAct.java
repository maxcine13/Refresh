package com.jierong.share.mvp.view.act;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.jierong.share.BaseAct;
import com.jierong.share.R;
import com.jierong.share.mvp.model.info.CommentInfo;
import com.jierong.share.mvp.presenter.CommentPresenter;
import com.jierong.share.mvp.view.ICommentView;
import com.jierong.share.mvp.view.ada.CommentAdapter;
import com.jierong.share.util.ToastUtils;
import com.jierong.share.widget.LoadingDialog;
import com.jierong.share.widget.MyDecoration;
import com.jierong.share.widget.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by wht on 2017/5/8.
 */

public class CommentAct extends BaseAct implements View.OnTouchListener,ICommentView ,View.OnClickListener{
    private EditText send_edt;
    private TextView send_btn;
    private RecyclerView recycleview;
    private BGARefreshLayout mBGARefreshLayout;
    private Dialog mLoadingDialog;
    private CommentAdapter commentAdapter;
    private List<CommentInfo> list=new ArrayList<>();
    private CommentPresenter commentPresenter;
    private String talent_id;//达人id

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_comment);
        initView();
    }
    private  void initView(){
        commentPresenter=new CommentPresenter(this);
        ((TextView)findViewById(R.id.titleName)).setText(R.string.master_comment);
        findViewById(R.id.titleBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        send_edt= (EditText) findViewById(R.id.send_edt);
        send_btn= (TextView) findViewById(R.id.send_btn);
        recycleview= (RecyclerView) findViewById(R.id.recycleview);
        mBGARefreshLayout= (BGARefreshLayout) findViewById(R.id.rl_modulename_refresh);
        talent_id=getIntent().getStringExtra("talent_id");

        //添加自定义的分隔线
        recycleview.addItemDecoration(new SpacesItemDecoration(2));
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getMContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleview.setLayoutManager(linearLayoutManager);
        //设置适配器
        commentAdapter = new CommentAdapter(recycleview, CommentAct.this);
        recycleview.setAdapter(commentAdapter);
        commentPresenter.getMasterData(talent_id);

        recycleview.setOnTouchListener(this);
        //设置刷新的样式(此处true为上拉加载可用,默认发false即上拉加载更多不可用)
        mBGARefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getMContext(), false));
        mBGARefreshLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                commentPresenter.getMasterData(talent_id);

            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {

                return false;
            }
        });

        send_btn.setOnClickListener(this);

    }




    @Override
    public void onNetNo() {

    }

    @Override
    public void onNetOk() {

    }

    /**
     * 隐藏输入法
     */
    private void hideKeyboard() {
        InputMethodManager imm =
                (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        send_edt.clearFocus();
        imm.hideSoftInputFromWindow(send_edt.getWindowToken(), 0);
    }

    /**
     * 当RecycleView的滑动事件触发,把软键盘隐藏
     * @param view
     * @param motionEvent
     * @return
     */
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    hideKeyboard();
                }
                return false;
    }

    @Override
    public Context getMContext() {
        return CommentAct.this;
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

    /**
     * 接收接口请求成功后返回的数据
     * @param data
     */
    @Override
    public void receiveData(String data) {
        loadData(data);
    }

    /**
     * 返回用户输入的评论内容
     * @return
     */
    @Override
    public String getContent() {
        return send_edt.getText().toString().trim();
    }

    @Override
    public void sendSuccess() {
       showError("提交成功",false);
        send_edt.setText("");
        commentPresenter.getMasterData(talent_id);
    }

    @Override
    public void showError(String msg, boolean flag) {
        ToastUtils.show(getMContext(), msg);
        if (flag)finish();
    }

    /**
     * 加载网络请求的数据
     * @param json
     */
    private void loadData(String json) {
        if (!json.equals("[]")) {
            list.clear();
            List<CommentInfo> infoList= CommentInfo.fromJSONS(json);
            if (infoList.size() > 0) {
                list.addAll(infoList);
                commentAdapter.setData(list);
            }
        }else{
            //没数据
            showError("暂无数据", false);

        }
        mBGARefreshLayout.endRefreshing();
    }


    @Override
    protected void onDestroy() {
        hideLoading();
        commentPresenter.closeHttp();
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send_btn:
                commentPresenter.sendComment(talent_id);
            break;
        }
    }
}
