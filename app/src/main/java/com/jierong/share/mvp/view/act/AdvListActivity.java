package com.jierong.share.mvp.view.act;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jierong.share.BaseAct;
import com.jierong.share.Constants;
import com.jierong.share.R;
import com.jierong.share.mvp.model.info.AdvInfo;
import com.jierong.share.mvp.presenter.AdvListPresenter;
import com.jierong.share.mvp.view.IAdvListView;
import com.jierong.share.util.LogUtil;
import com.jierong.share.util.ToastUtils;
import com.jierong.share.widget.LoadingDialog;
import com.jierong.share.widget.TanTanCallback;
import com.mcxtzhang.commonadapter.rv.CommonAdapter;
import com.mcxtzhang.commonadapter.rv.OnItemClickListener;
import com.mcxtzhang.commonadapter.rv.ViewHolder;
import com.mcxtzhang.layoutmanager.swipecard.CardConfig;
import com.mcxtzhang.layoutmanager.swipecard.OverLayCardLayoutManager;
import java.util.List;

/**
 * 广告详情列表界面
 */
public class AdvListActivity extends BaseAct implements IAdvListView {
    private RecyclerView adv_rv;
    private CommonAdapter<AdvInfo> mAdapter;
    private Dialog mLoadingDialog;
    private AdvListPresenter mAdvListPresenter;
    private String tid, tName, aid; // 广告分类id, 广告分类名称, 广告id
    private TextView tip;//规则说明
    private TextView adv_list_tips;//页面异常的时候提示
    private RelativeLayout rela_empty;
    private ImageView empty_bg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_adv_list);
        initView();
    }

    @Override
    public void onNetNo() {
        LogUtil.d("onNetNo - begin");
    }

    @Override
    public void onNetOk() {
        LogUtil.d("onNetOk - begin");
    }

    private void initView() {
        mAdvListPresenter = new AdvListPresenter(this);
        rela_empty = (RelativeLayout) findViewById(R.id.rela_empty);
        tip = (TextView) findViewById(R.id.tip);
        adv_list_tips = (TextView) findViewById(R.id.adv_list_tips);
        empty_bg = (ImageView) findViewById(R.id.empty_bg);
        adv_rv = (RecyclerView) findViewById(R.id.adv_rv);
        findViewById(R.id.titleBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tid = getIntent().getStringExtra("AdvTypeId");
        tName = getIntent().getStringExtra("AdvTypeName");
        ((TextView) findViewById(R.id.titleName)).setText(tName);
        tip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdvListActivity.this, CommonAct.class);
                intent.putExtra("common", Constants.IncomeRule);
                startActivity(intent);

            }
        });
        init();
    }

    private void init() {
        adv_rv.setLayoutManager(new OverLayCardLayoutManager());
        if(isNetworkConnected()) {
            rela_empty.setVisibility(View.GONE);
            mAdvListPresenter.getAdvList();
        } else {
            rela_empty.setVisibility(View.VISIBLE);
            empty_bg.setImageResource(R.drawable.ic_placeholder_network_error);
            adv_list_tips.setText("没有网络了");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, 1500);
        }
        LogUtil.d("init - finish");
    }

    @Override
    public Context getMContext() {
        return AdvListActivity.this;
    }

    @Override
    public void showLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = LoadingDialog.createLoadingDialog(AdvListActivity.this, "正在加载中...");
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
    public String getAid() {
        return aid;
    }

    @Override
    public void getDataSuccess(final List<AdvInfo> data) {
        // 设置部分自己颜色
        String str = "温馨提示：内容分享有收益，成交返积分，详细规则可查看《分享赚客收益规则》";
        int fstart = str.indexOf("《分享赚客收益规则》");
        int fend = fstart + "《分享赚客收益规则》".length();
        // 如果需要在列表界面做区别处理，那么就在这个吧
        if(tid.equals("3")){ }
        else if(tid.equals("5")) { }
        else if(tid.equals("2")) { }

        SpannableStringBuilder style = new SpannableStringBuilder(str);
        style.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.zan_click)), fstart, fend, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        tip.setText(style);
        tip.setClickable(true);

        adv_rv.setAdapter(mAdapter = new CommonAdapter<AdvInfo>(this,
                data, R.layout.recyclerview_item_adv) {
            @Override
            public void convert(final ViewHolder viewHolder, final AdvInfo advInfo) {
                viewHolder.setText(R.id.adv_desc, advInfo.getDesc());
                viewHolder.setText(R.id.adv_man_name, advInfo.getManName());
                switch (advInfo.getType()) {
                    case 1:
                        viewHolder.setText(R.id.adv_one_money, "分享收益:" + advInfo.getOneMoney());
                        break;
                    case 2:
                        viewHolder.setText(R.id.adv_one_money, "成交返利:" + advInfo.getOneMoney());
                        break;
                    default: break;
                }
                if (advInfo.isLove()) {
                    ((ImageView) viewHolder.getView(R.id.zan_iv)).setImageResource(R.drawable.zan_ok);
                    ((TextView) viewHolder.getView(R.id.zan_tv)).setTextColor(getResources().getColor(R.color.zan_click));
                    ((TextView) viewHolder.getView(R.id.zan_tv)).setText("谢谢你");
                } else {
                    ((ImageView) viewHolder.getView(R.id.zan_iv)).setImageResource(R.drawable.zan_no);
                    ((TextView) viewHolder.getView(R.id.zan_tv)).setTextColor(getResources().getColor(R.color.zan_normal));
                    ((TextView) viewHolder.getView(R.id.zan_tv)).setText("赞一下");
                }
                Glide.with(AdvListActivity.this)
                        .load(advInfo.getImg())
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into((ImageView) viewHolder.getView(R.id.adv_img));
                Glide.with(AdvListActivity.this)
                        .load(advInfo.getManIc())
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into((ImageView) viewHolder.getView(R.id.adv_man_ic));
                viewHolder.getView(R.id.right_desc).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mAdvInfo = advInfo;
                        if (mAdvInfo.isLove()) return;
                        aid = mAdvInfo.getId();
                        mViewHolder = viewHolder;

                        mAdvListPresenter.doZan();
                    }
                });
            }
        });

        // 只能调用一次，如果复用，则仿探探模式会失败
        CardConfig.initConfig(this);
        TanTanCallback callback = new TanTanCallback(adv_rv, mAdapter, data);
        //ItemTouchHelper.SimpleCallback callback = new RenRenCallback(adv_rv, mAdapter, data);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(adv_rv);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup viewGroup, View view, Object o, int i) {
                AdvInfo info = (AdvInfo) o;
                Intent intent = new Intent(AdvListActivity.this, AdvDescActivity.class);
                intent.putExtra("Aid", info.getId());
                intent.putExtra("AdvTypeId", tid);
                intent.putExtra("AdvTypeName", info.getTitle());
                AdvListActivity.this.startActivity(intent);
                AdvListActivity.this.finish();
            }

            @Override
            public boolean onItemLongClick(ViewGroup viewGroup, View view, Object o, int i) {
                return false;
            }
        });
    }

    ViewHolder mViewHolder;
    AdvInfo mAdvInfo;
    @Override
    public void doZanSuccess() {
        // 目前只处理由 “未点赞” 到 “已点赞” 的状态切换，所以状态单一
        mAdvInfo.setLove(true);
        ((ImageView) mViewHolder.getView(R.id.zan_iv)).setImageResource(R.drawable.zan_ok);
        ((TextView) mViewHolder.getView(R.id.zan_tv)).setTextColor(getResources().getColor(R.color.zan_click));
        ((TextView) mViewHolder.getView(R.id.zan_tv)).setText("谢谢你");
    }

    @Override
    public void showError(String msg, boolean flag) {
        if ("暂无广告信息".equals(msg)) {
            rela_empty.setVisibility(View.VISIBLE);
            empty_bg.setImageResource(R.drawable.bg_empty);
            adv_list_tips.setText("抱歉,暂无广告! ");
            tip.setText("温馨提示：您所访问的专题暂无，若获取更多分享收益，请浏览其它专题版块，更多资讯请随时关注[分享赚客]。");
            tip.setClickable(false);
        }
        ToastUtils.show(this, msg);
        if (flag) finish();
    }

    @Override
    protected void onDestroy() {
        hideLoading();
        mAdvListPresenter.closeHttp();
        super.onDestroy();
    }
}
