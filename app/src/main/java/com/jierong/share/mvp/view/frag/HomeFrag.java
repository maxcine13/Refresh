package com.jierong.share.mvp.view.frag;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.page.AlibcBasePage;
import com.alibaba.baichuan.android.trade.page.AlibcPage;
import com.alibaba.baichuan.trade.biz.context.AlibcTradeResult;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jierong.share.BaseFrag;
import com.jierong.share.Constants;
import com.jierong.share.R;
import com.jierong.share.banner.Banner;
import com.jierong.share.banner.BannerConfig;
import com.jierong.share.banner.listener.OnBannerClickListener;
import com.jierong.share.banner.loader.ImageLoader;
import com.jierong.share.mvp.model.info.AdvInfo;
import com.jierong.share.mvp.model.info.AdvTypeInfo;
import com.jierong.share.mvp.model.info.BkInfo;
import com.jierong.share.mvp.model.info.EventBusInfo;
import com.jierong.share.mvp.presenter.HomePresenter;
import com.jierong.share.mvp.view.IHomeView;
import com.jierong.share.mvp.view.act.AdvDescActivity;
import com.jierong.share.mvp.view.act.BkflAct;
import com.jierong.share.mvp.view.act.CzflAct;
import com.jierong.share.mvp.view.act.GoodsApply;
import com.jierong.share.mvp.view.act.JrthAct;
import com.jierong.share.mvp.view.act.LocationAct;
import com.jierong.share.mvp.view.act.MainAct;
import com.jierong.share.mvp.view.act.PzjdAct;
import com.jierong.share.mvp.view.act.ReCommendAct;
import com.jierong.share.mvp.view.act.TaobkwebAct;
import com.jierong.share.mvp.view.act.WebViewAct;
import com.jierong.share.mvp.view.act.taotestAc;
import com.jierong.share.util.AppPreferences;
import com.jierong.share.util.LogUtil;
import com.jierong.share.util.StringUtil;
import com.jierong.share.util.ToastUtils;
import com.jierong.share.widget.LoadingDialog;
import com.jierong.share.widget.MyDialog;
import com.jierong.share.widget.NewDialogUp;
import com.jierong.share.widget.SimpleDia;
import com.jierong.share.zz.Guide;
import com.jierong.share.zz.GuideBuilder;
import com.jierong.share.zz.YdFlCom;
import com.jierong.share.zz.YdJhsCom;
import com.jierong.share.zz.YdSQCom;
import com.jierong.share.zz.YdShareCom;
import com.jierong.share.zz.YdSmCom;
import com.jierong.share.zz.YdTjCom;
import com.mcxtzhang.commonadapter.rv.CommonAdapter;
import com.mcxtzhang.commonadapter.rv.ViewHolder;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 首页模块界面
 */
public class HomeFrag extends BaseFrag implements AMapLocationListener, IHomeView,
        View.OnClickListener {
    private AMapLocationClient mLocationClient = null;
    private AMapLocationClientOption mLocationOption = null;
    private RotateAnimation refreshingAnimation;
    private FrameLayout cityLayout;
    private TextView titleCity;
    private ImageView titleLoading;
    private SimpleDia mSimpleDia;   // 定位切换提示
    private ScrollView sv;
    private Dialog mLoadingDialog;
    private Banner banner;
    private RelativeLayout home_search, view_nonet, layout_baokuan;
    private HomePresenter mHomePresenter;
    private boolean isClick = false;    // 当前是否在点击状态
    private ImageView ivSign;           // 签到按钮
    private MyDialog myDialog = null;   // 签到提示对话框
    private Dialog mDownLoadDialog;     // 强制更新对话框
    private TextView updateValue, updateSize;
    private ProgressBar mProgress;
    private LinearLayout jhs_layout, linear_pzjd, linear_jinri, linear_chaozhi;
    private ImageView img_fan, img_tuijian, img_jiaocheng;
    private Guide guide;
    private MainAct mMainAct;
    private FrameLayout view_laba;
    private TextView tv_laba;
    private RecyclerView bkGoodsRv;
    private CommonAdapter<BkInfo> mAdapter;
    private LinearLayoutManager mLayoutManager;
    private boolean isRegister = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_home, null);
        initView(view);
        return view;
    }

    BroadcastReceiver refreshReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Constants.Push_Gg_Home)) {
                // 呈现Home公告
                String gg = intent.getStringExtra("Data");
                LogUtil.d("刷新公告：" + gg);
                if(view_laba.getVisibility() == View.GONE)
                    view_laba.setVisibility(View.VISIBLE);
                tv_laba.setText(gg);
                tv_laba.setSelected(true);
            }
        }
    };

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(AppPreferences.getBoolean(getMContext(), "isShowYD", true)) {
            getActivity().getWindow()
                    .getDecorView()
                    .getViewTreeObserver()
                    .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override public void onGlobalLayout() {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                getActivity().getWindow()
                                        .getDecorView()
                                        .getViewTreeObserver()
                                        .removeOnGlobalLayoutListener(this);
                            } else {
                                getActivity().getWindow()
                                        .getDecorView()
                                        .getViewTreeObserver()
                                        .removeGlobalOnLayoutListener(this);
                            }
                            showJhsView();
                        }
                    });
            AppPreferences.putBoolean(getMContext(), "isShowYD", false);
        }
    }

    // 显示聚划算提示
    public void showJhsView() {
        GuideBuilder builder = new GuideBuilder();
        builder.setTargetView(jhs_layout)
                .setAlpha(150)
                .setHighTargetCorner(0)
                .setHighTargetPadding(0)
                .setOverlayTarget(false)
                .setOutsideTouchable(false);
        builder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() {
            @Override
            public void onShown() { }

            @Override
            public void onDismiss() {
                showFanLiView();
            }
        });
        builder.addComponent(new YdJhsCom());
        guide = builder.createGuide();
        guide.setShouldCheckLocInWindow(true);
        guide.show(getActivity());
    }

    // 显示"爆款返利"提示
    public void showFanLiView() {
        GuideBuilder builder = new GuideBuilder();
        builder.setTargetView(layout_baokuan)
                .setAlpha(150)
                .setHighTargetCorner(0)
                .setHighTargetPadding(0)
                .setOverlayTarget(false)
                .setOutsideTouchable(false);
        builder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() {
            @Override
            public void onShown() {
                LogUtil.d("----onShown----");
            }

            @Override
            public void onDismiss() {
                showShenQingView();
            }
        });
        builder.addComponent(new YdFlCom());
        guide = builder.createGuide();
        guide.setShouldCheckLocInWindow(true);
        guide.show(getActivity());
    }

    // 显示"申请返利"提示
    public void showShenQingView() {
        GuideBuilder builder = new GuideBuilder();
        builder.setTargetView(img_fan)
                .setAlpha(150)
                .setHighTargetCorner(0)
                .setHighTargetPadding(0)
                .setOverlayTarget(false)
                .setOutsideTouchable(false);
        builder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() {
            @Override
            public void onShown() { }

            @Override
            public void onDismiss() {
                showTuiJianView();
            }
        });
        builder.addComponent(new YdSQCom());
        guide = builder.createGuide();
        guide.setShouldCheckLocInWindow(true);
        guide.show(getActivity());
    }

    // 显示"推荐有好礼"
    public void showTuiJianView() {
        GuideBuilder builder = new GuideBuilder();
        builder.setTargetView(img_tuijian)
                .setAlpha(150)
                .setHighTargetCorner(0)
                .setHighTargetPadding(0)
                .setOverlayTarget(false)
                .setOutsideTouchable(false);
        builder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() {
            @Override
            public void onShown() { }

            @Override
            public void onDismiss() {
                showShuoMingView();
            }
        });
        builder.addComponent(new YdTjCom());
        guide = builder.createGuide();
        guide.setShouldCheckLocInWindow(true);
        guide.show(getActivity());
    }

    // 显示"一分钟说明"
    public void showShuoMingView() {
        GuideBuilder builder = new GuideBuilder();
        builder.setTargetView(img_jiaocheng)
                .setAlpha(150)
                .setHighTargetCorner(0)
                .setHighTargetPadding(0)
                .setOverlayTarget(false)
                .setOutsideTouchable(false);
        builder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() {
            @Override
            public void onShown() { }

            @Override
            public void onDismiss() {
                showShareView();
            }
        });
        builder.addComponent(new YdSmCom());
        guide = builder.createGuide();
        guide.setShouldCheckLocInWindow(true);
        guide.show(getActivity());
    }

    // 显示"分享赚钱"
    public void showShareView() {
        GuideBuilder builder = new GuideBuilder();
        builder.setTargetView(mMainAct.getTwoView())
                .setAlpha(150)
                .setHighTargetCorner(0)
                .setHighTargetPadding(0)
                .setOverlayTarget(false)
                .setOutsideTouchable(false);
        builder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() {
            @Override
            public void onShown() { }

            @Override
            public void onDismiss() {
                LogUtil.d("----onDismiss----");
                IntentFilter filter = new IntentFilter();
                filter.addAction(Constants.Push_Gg_Home);
                getActivity().registerReceiver(refreshReceiver, filter);
                isRegister = true;
                mHomePresenter.getGg();
            }
        });
        builder.addComponent(new YdShareCom());
        guide = builder.createGuide();
        guide.setShouldCheckLocInWindow(true);
        guide.show(getActivity());
    }

    @Override
    public void onNetNo() {
        view_nonet.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNetOk() {
        view_nonet.setVisibility(View.GONE);
        startLoc();
        mHomePresenter.telServer();
    }

    public void setClick(boolean click) {
        isClick = click;
    }

    @Override
    public void onHiddenChanged(boolean hidd) {
        if (!hidd) {    //当fragment从隐藏到出现的时候
            if (null != sv) {
                sv.post(new Runnable() {
                    @Override
                    public void run() {
                        // 这个是能看到还原动画的效果
                        // sv.fullScroll(ScrollView.FOCUS_UP);
                        // 这个是看不到还原动画的效果
                        sv.scrollTo(0, 0);
                    }
                });
            }
        }
    }

    // 开始定位
    private void startLoc() {
        String Location = AppPreferences.getString(getMContext(), Constants.PNK_Location, "");
        if (!Location.equals("")) {
            if (Location.equals("None")) {
                titleCity.setText("定位失败");
                mLocationClient.startLocation();
                titleLoading.clearAnimation();
                titleLoading.setVisibility(View.GONE);
                titleCity.setVisibility(View.VISIBLE);
            } else {
                mLocationClient.startLocation();
                titleLoading.clearAnimation();
                titleLoading.setVisibility(View.GONE);
                titleCity.setText(Location);
                titleCity.setVisibility(View.VISIBLE);
            }
        } else {
            mLocationClient.startLocation();
            titleLoading.setVisibility(View.VISIBLE);
            titleLoading.startAnimation(refreshingAnimation);
            titleCity.setText("");
            if (titleCity.getVisibility() == View.VISIBLE)
                titleCity.setVisibility(View.GONE);
        }

    }

    // 初始化高德定位
    private void initAMap() {
        mLocationClient = new AMapLocationClient(getActivity().getApplicationContext());
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(
                AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setOnceLocation(true);
        mLocationOption.setOnceLocationLatest(true);
        mLocationOption.setNeedAddress(true);
        //mLocationOption.setInterval(1000);   // 自定义连续定位
        mLocationOption.setMockEnable(false);   // 不允许模拟位置
        mLocationOption.setHttpTimeOut(20000);  // 建议超时时间不要低于8000毫秒
        mLocationOption.setLocationCacheEnable(false);  // 关闭缓存机制
        mLocationClient.setLocationOption(mLocationOption);
        mLocationClient.setLocationListener(this);
    }

    private void initView(View view) {
        mMainAct = (MainAct) getActivity();
        EventBus.getDefault().register(this);
        mHomePresenter = new HomePresenter(this);
        refreshingAnimation = (RotateAnimation) AnimationUtils.loadAnimation(getActivity(), R.anim.rotating);
        refreshingAnimation.setInterpolator(new LinearInterpolator());  // 添加匀速转动动画
        if(!AppPreferences.getBoolean(getMContext(), "isShowYD", true)) {
            IntentFilter filter = new IntentFilter();
            filter.addAction(Constants.Push_Gg_Home);
            getActivity().registerReceiver(refreshReceiver, filter);
            isRegister = true;
            mHomePresenter.getGg();
        }

        home_search = (RelativeLayout) view.findViewById(R.id.home_search);
        jhs_layout = (LinearLayout) view.findViewById(R.id.jhs_layout);
        bkGoodsRv = (RecyclerView) view.findViewById(R.id.bkGoodsRv);
        layout_baokuan = (RelativeLayout) view.findViewById(R.id.layout_baokuan);
        img_fan = (ImageView) view.findViewById(R.id.img_fan);
        img_tuijian = (ImageView) view.findViewById(R.id.img_tuijian);
        img_jiaocheng = (ImageView) view.findViewById(R.id.img_jiaocheng);
        linear_pzjd = (LinearLayout) view.findViewById(R.id.linear_pzjd);
        linear_jinri = (LinearLayout) view.findViewById(R.id.linear_jinri);
        linear_chaozhi = (LinearLayout) view.findViewById(R.id.linear_chaozhi);
        sv = (ScrollView) view.findViewById(R.id.sv);
        cityLayout = (FrameLayout) view.findViewById(R.id.cityLayout);
        cityLayout.setOnClickListener(null);
        titleCity = (TextView) view.findViewById(R.id.titleCity);
        titleLoading = (ImageView) view.findViewById(R.id.titleLoading);
        view_nonet = (RelativeLayout) view.findViewById(R.id.view_nonet);
        banner = (Banner) view.findViewById(R.id.banner);
        view_laba = (FrameLayout) view.findViewById(R.id.view_laba);
        tv_laba = (TextView) view.findViewById(R.id.tv_laba);
        tv_laba.setSelected(true);
        view_laba.setVisibility(View.GONE);
        ivSign = (ImageView) view.findViewById(R.id.ivSign);

        bkGoodsRv.setHasFixedSize(true);//设置固定大小
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        bkGoodsRv.setLayoutManager(mLayoutManager);
        initAMap();
        mHomePresenter.telServer();
        mHomePresenter.getHomeData();

        img_fan.setOnClickListener(this);
        img_tuijian.setOnClickListener(this);
        img_jiaocheng.setOnClickListener(this);
        linear_pzjd.setOnClickListener(this);
        linear_jinri.setOnClickListener(this);
        linear_chaozhi.setOnClickListener(this);
        titleCity.setOnClickListener(this);
        ivSign.setOnClickListener(this);
        jhs_layout.setOnClickListener(this);
        layout_baokuan.setOnClickListener(this);
        home_search.setOnClickListener(this);
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                titleLoading.clearAnimation();
                titleLoading.setVisibility(View.GONE);
                if (titleCity.getText().toString().equals("")) {
                    titleCity.setText(aMapLocation.getCity().replace("市", ""));
                    titleCity.setVisibility(View.VISIBLE);
                    mHomePresenter.uploadCity();
                    AppPreferences.putString(getMContext(), Constants.PNK_Location, titleCity.getText().toString());
                } else if (!titleCity.getText().toString().equals(aMapLocation.getCity().replace("市", ""))) {
                    cityChange(aMapLocation.getCity());
                }
            } else {
                String Location = AppPreferences.getString(getMContext(), Constants.PNK_Location, "None");
                //如果定位失败的时候,先去去上次打开时存的值,当为None时,不改变city的值;
                if (Location.equals("None")) {
                    AppPreferences.putString(getMContext(), Constants.PNK_Location, "None");
                    titleLoading.clearAnimation();
                    titleLoading.setVisibility(View.GONE);
                    titleCity.setText("定位失败");
                    titleCity.setVisibility(View.VISIBLE);
                }
            }
        } else {
            LogUtil.e(aMapLocation.getErrorCode() + " : "
                    + aMapLocation.getErrorInfo());
        }
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
    public String getCity() {
        return titleCity.getText().toString();
    }

    @Override
    public void uploadCitySuccess() {
        LogUtil.e("位置上传成功！");
    }

    @Override
    public void receiveHomeData(final List<AdvInfo> advInfos, List<AdvTypeInfo> advTypeInfos) {
        //  refreshView.setRefreshing(false);
        List<String> imgs = new ArrayList<String>();
        for (AdvInfo info : advInfos) {
            imgs.add(info.getImg());
        }
        // 绘制轮播图
        banner.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {
                //Intent intent = new Intent(getActivity(), AdvDescActivity.class);
                //intent.putExtra("AdvId", advInfos.get(position - 1).getId());
                //getActivity().startActivity(intent);

                AdvInfo advInfo = advInfos.get(position - 1);
                LogUtil.d("id：" + advInfo.getId());
                if(advInfo.getId().equals("88")) {
                    String url = advInfo.getLink();
                    LogUtil.d("link：" + url);
                    Intent jhsIntent = new Intent(getActivity(), WebViewAct.class);
                    jhsIntent.putExtra("Url", url);
                    startActivity(jhsIntent);
                }
            }
        });
        banner.setImages(imgs)
                .setDelayTime(4000)
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                .setIndicatorGravity(BannerConfig.CENTER)
                .setImageLoader(new ImageLoader() {
                    @Override
                    public void displayImage(Context context, Object path, ImageView imageView) {
                        Glide.with(context)
                                .load(path)
                                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                .into(imageView);
                    }
                }).start();
        mHomePresenter.getBk();
    }

    @Override
    public void receiveBkData(List<BkInfo> bkInfos) {
        bkGoodsRv.setAdapter(mAdapter = new CommonAdapter<BkInfo>(
                getMContext(), bkInfos, R.layout.item_bk) {
            @Override
            public void convert(ViewHolder viewHolder, final BkInfo bkInfo) {
                viewHolder.setText(R.id.bk_price, bkInfo.getPrice());
                viewHolder.setText(R.id.bk_fan, "返￥" + bkInfo.getFold());
                Glide.with(getMContext())
                        .load(bkInfo.getPic())
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into((ImageView) viewHolder.getView(R.id.bk_img));

                viewHolder.getView(R.id.bk_layout).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(null == bkInfo.getUrl()) {
                            LogUtil.e("url is null");
                            return;
                        }
                        AlibcBasePage alibcBasePage = new AlibcPage(bkInfo.getUrl());
                        AlibcShowParams alibcShowParams = new AlibcShowParams(OpenType.Auto, false);
                        AlibcTrade.show(getActivity(), alibcBasePage, alibcShowParams, null, null, new AlibcTradeCallback() {
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
        });
        mHomePresenter.checkUpdate();   // 执行检查更新方法
    }

    private SimpleDia updateDia;
    @Override
    public void showSetUpdate(String desc) {
        updateDia = new SimpleDia(getActivity(), SimpleDia.Ok_No_Type);
        updateDia.setTitleText("版本升级")
                .setContentText(Html.fromHtml(desc))
                .setConfirmText("更新")
                .setCancelText("忽略")
                .showCancelButton(true)
                .setClickListener(new SimpleDia.OnClickButtonListener() {
                    @Override
                    public void onClick(SimpleDia dialog, int flag) {
                        switch (flag) {
                            case 0: // 取消
                                mHomePresenter.finishCheckUpdate(false);
                                break;
                            case 1: // 确定
                                mHomePresenter.finishCheckUpdate(true);
                                break;
                            default:
                                break;
                        }
                    }
                }).show();
    }

    @Override
    public void showDownloadDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("正在更新");
        final LayoutInflater inflater = LayoutInflater.from(getActivity());
        View v = inflater.inflate(R.layout.softupdate_progress, null);
        mProgress = (ProgressBar) v.findViewById(R.id.update_progress);
        updateValue = (TextView) v.findViewById(R.id.update_value);
        updateSize = (TextView) v.findViewById(R.id.update_size);
        builder.setView(v);

        // 取消更新
        builder.setNegativeButton("取消更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mHomePresenter.closeHttp(false);
                // 删除下载目录下的文件
                dialog.dismiss();
                LogUtil.d("取消本次更新");
            }
        });
        mDownLoadDialog = builder.create();
        mDownLoadDialog.setCancelable(false);
        mDownLoadDialog.show();
        mHomePresenter.downNewApk();
    }

    @Override
    public void showProgress(float progress, long current, long total) {
        String downloadLength = Formatter.formatFileSize(getActivity(), current);
        String totalLength = Formatter.formatFileSize(getActivity(), total);
        mProgress.setProgress((int) (progress * 100));
        updateValue.setText((Math.round(progress * 10000) * 1.0f / 100) + "%");
        updateSize.setText(downloadLength + "/" + totalLength);
    }

    @Override
    public void installAPK(File file) {
        if (!file.exists()) return;
        if (mDownLoadDialog != null) {
            mDownLoadDialog.dismiss();
            mDownLoadDialog = null;
        }
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void showGift(String money) {
        final NewDialogUp newDialogUp = new NewDialogUp(getMContext(), money);
        newDialogUp.setYesOnclickListener(new NewDialogUp.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                // ToastUtils.show(getMContext(), "领取新人礼包成功");
                newDialogUp.dismiss();
            }
        });
        newDialogUp.setDismissOnclickListener(new NewDialogUp.onDismissOnclickListener() {
            @Override
            public void onNoClick() {
                newDialogUp.dismiss();
            }
        });
        newDialogUp.show();
    }

    @Override
    public void getGgSuccess(String result) {
        if(StringUtil.isNotEmptyIgnoreBlank(result)) {
            if(view_laba.getVisibility() == View.GONE)
                view_laba.setVisibility(View.VISIBLE);
            tv_laba.setText(result);
            tv_laba.setSelected(true);
        }
    }

    @Override
    public void receiveSign(String money, boolean flag) {
        myDialog = new MyDialog(getContext());
        if (flag) {
            myDialog.setTitle("+ " + money);
            myDialog.setMessage("明天签到将继续获得收益");
        } else {
            myDialog.setMessage(money);
        }
        myDialog.setYesOnclickListener(new MyDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                myDialog.dismiss();
            }
        });
        myDialog.show();
    }

    @Override
    public void showError(String msg, boolean flag) {
        //  refreshView.setRefreshing(false);
        ToastUtils.show(getActivity(), msg);
        if (flag) getActivity().finish();
    }

    /**
     * 如果检测到当前定位和左上角的城市不一致时,提示用户是否切换到当前城市
     * @param city
     */
    private void cityChange(String city) {
        final String changeCity = city.replace("市", "");
        mSimpleDia = new SimpleDia(getActivity(), SimpleDia.Ok_No_Type);
        mSimpleDia.setTitleText("提示")
                .setContentText(Html.fromHtml("系统定位到您在" + changeCity + ",需要切换到" + changeCity + "吗？"))
                .setConfirmText("确定")
                .setCancelText("取消")
                .showCancelButton(false)
                .setClickListener(new SimpleDia.OnClickButtonListener() {
                    @Override
                    public void onClick(SimpleDia dialog, int flag) {
                        switch (flag) {
                            case 0: // 取消
                                break;
                            case 1: // 确定
                                titleCity.setText(changeCity);
                                titleCity.setVisibility(View.VISIBLE);
                                mHomePresenter.uploadCity();
                                AppPreferences.putString(getMContext(), Constants.PNK_Location, changeCity);
                                break;
                            default:
                                break;
                        }
                    }
                }).show();
    }

    // 使用EventBus订阅LocationAct页面返回的选择城市
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateCityEventBus(EventBusInfo message) {
        if (message.getName().equals("LocationAct")) {
            AppPreferences.putString(getMContext(), Constants.PNK_Location, message.getValue());
            titleCity.setText(message.getValue());
            //修改定位城市后,把新的城市上传
            mHomePresenter.uploadCity();
            //在选择完城市后,刷新本地的数据
            mHomePresenter.getHomeData();
        }
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.titleCity:
                Intent intent = new Intent(getMContext(), LocationAct.class);
                startActivity(intent);
                break;
            case R.id.ivSign: // 签到
                if (null == myDialog || !myDialog.isShowing())
                    mHomePresenter.getSign();
                break;
            case R.id.img_fan:
                getActivity().startActivity(new Intent(getActivity(), GoodsApply.class));
                break;
            case R.id.img_tuijian:
                getActivity().startActivity(new Intent(getActivity(), ReCommendAct.class));//推荐
                break;
            case R.id.img_jiaocheng://如何赚钱
                Intent intent4 = new Intent(getActivity(), TaobkwebAct.class);
                intent4.putExtra("type", "4");//教程
                startActivity(intent4);
                break;
            case R.id.linear_pzjd: // 品质家电
                Intent intent5 = new Intent(getActivity(), PzjdAct.class);
                startActivity(intent5);
                break;
            case R.id.linear_jinri: // 今日特惠
                Intent intent1 = new Intent(getActivity(), JrthAct.class);
                startActivity(intent1);
                break;
            case R.id.linear_chaozhi://超值返利
                Intent intent3 = new Intent(getActivity(), CzflAct.class);
                startActivity(intent3);
                break;
            case R.id.layout_baokuan:{ // 爆款返利
                Intent bkIntent = new Intent(getActivity(), BkflAct.class);
                startActivity(bkIntent);
                break;
            }
            case R.id.jhs_layout:{  // 聚划算
                Intent jhsIntent = new Intent(getActivity(), WebViewAct.class);
                jhsIntent.putExtra("Url", "https://s.click.taobao.com/t?e=m%3D2%26s%3DenRNMM8vdaMcQi" +
                        "pKwQzePCperVdZeJviEViQ0P1Vf2kguMN8XjClAhxglWAMBeqqEzdo621fDJVtYUO%2BrPRbk84" +
                        "EuSisiM2E%2FktSwM7hOi2CPsm9aZoPQune%2BSK2%2FEQFbpSCl1%2BmsLkxFiXT%2FI5kYaDjw%" +
                        "2FF04D8O0MJq4IL1Xu0VxWHoneb%2BC%2FKUDpU9p7JZZkaS8ir90CpomOqpod6M%2BnA%2BQH%2BO" +
                        "ZdLxLqovVbkw%2BshhQs2DjqgEA%3D%3D");
                startActivity(jhsIntent);
                break;
            }
            case R.id.home_search:{ // 搜索框
                Intent searchIntent = new Intent(getActivity(), WebViewAct.class);
                searchIntent.putExtra("Url", "https://ai.m.taobao.com/search.html?pid=mm_123166441_28158373_109094623");
                startActivity(searchIntent);
                break;
            }
            default: break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // 开始轮播
        banner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        // 结束轮播
        banner.stopAutoPlay();
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        if (null != mLocationClient) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        if (null != myDialog) {
            myDialog.dismiss();
            myDialog = null;
        }
        if (mDownLoadDialog != null) mDownLoadDialog.dismiss();
        if (mSimpleDia != null) mSimpleDia.dismissWithAnimation();
        if (updateDia != null) updateDia.dismissWithAnimation();
        mHomePresenter.closeHttp(true);
        hideLoading();
        if (isRegister && refreshReceiver != null)
            getActivity().unregisterReceiver(refreshReceiver);
        AlibcTradeSDK.destory();
        super.onDestroy();
    }

}
