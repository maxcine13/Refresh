package com.jierong.share.mvp.view.frag;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.text.InputFilter;
import android.text.format.Formatter;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.page.AlibcBasePage;
import com.alibaba.baichuan.android.trade.page.AlibcMyOrdersPage;
import com.alibaba.baichuan.trade.biz.context.AlibcTradeResult;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;
import com.jierong.share.AppManager;
import com.jierong.share.BaseApp;
import com.jierong.share.BaseFrag;
import com.jierong.share.Constants;
import com.jierong.share.R;
import com.jierong.share.imgfrom.ImagesSelectorActivity;
import com.jierong.share.imgfrom.SelectorSettings;
import com.jierong.share.mvp.model.info.LoginUserInfo;
import com.jierong.share.mvp.presenter.MePresenter;
import com.jierong.share.mvp.view.IMeView;
import com.jierong.share.mvp.view.act.AuthenticationAct;
import com.jierong.share.mvp.view.act.BzActivity;
import com.jierong.share.mvp.view.act.ChangePwAct;
import com.jierong.share.mvp.view.act.CollectionAct;
import com.jierong.share.mvp.view.act.CommonAct;
import com.jierong.share.mvp.view.act.EditTextAct;
import com.jierong.share.mvp.view.act.LoginAct;
import com.jierong.share.mvp.view.act.MoneyHaveAct;
import com.jierong.share.mvp.view.act.MyMessageAct;
import com.jierong.share.mvp.view.act.ReCommendAct;
import com.jierong.share.mvp.view.act.SendAct;
import com.jierong.share.mvp.view.act.ShopBuyAct;
import com.jierong.share.mvp.view.act.ShopFanAct;
import com.jierong.share.mvp.view.act.WebViewAct;
import com.jierong.share.mvp.view.act.WhileLineAct;
import com.jierong.share.util.AppPreferences;
import com.jierong.share.util.FileUtils;
import com.jierong.share.util.LogUtil;
import com.jierong.share.util.StringUtil;
import com.jierong.share.util.ToastUtils;
import com.jierong.share.widget.EditDialog;
import com.jierong.share.widget.LoadingDialog;
import com.jierong.share.widget.RoundImageView;
import com.jierong.share.widget.SimpleDia;
import com.jierong.share.widget.UpdateUserHeadDialog;
import com.just.library.AgentWebConfig;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import pub.devrel.easypermissions.EasyPermissions;
import static android.app.Activity.RESULT_OK;

/**
 * 个人中心界面
 */
public class MeFrag extends BaseFrag implements View.OnClickListener, IMeView,
        SwipeRefreshLayout.OnRefreshListener, EasyPermissions.PermissionCallbacks {
    private TextView tv_laba;
    private RoundImageView iv_uic;
    private ImageView iv_level, del_laba, iv_unread;
    private RelativeLayout me_uic_edit, view_laba;
    private TextView tv_name, tv_addr, tv_tel, tv_share_tip, tv_money_have;
    private RelativeLayout bt_money_have, bt_myMessage, bt_order, bt_pw_change,
            bt_update_check, bt_help_center, bt_logout, bt_send, bt_collection, bt_yqm;
    private RelativeLayout bt_white_line, bt_recommend, bt_master, bt_customer;
    private RelativeLayout view_nonet;
    private LinearLayout btn_free, btn_shop, btn_fan;
    private View yqm_line;
    private Dialog mLoadingDialog, mDownLoadDialog;
    private SimpleDia mSimpleDia;
    private SwipeRefreshLayout refreshView;
    private MePresenter mMePresenter;
    private ProgressBar mProgress;
    private boolean isClick = false;    // 当前是否在点击状态
    private final int Request_Code_Ic = 312;   // 完善图片的标记
    private final int Request_Code_Name = 612;   // 完善名称的标记
    private String mResult;
    private TextView updateValue, updateSize;
    private String name, bankcard, idcard, mYqm;
    private UpdateUserHeadDialog mUserHeadDialog;
    private static final int REQUEST_CODE_PERMISSION_PHOTO_PICKER = 1;
    private static final int PHOTO_CODE_ONE = 1;
    private static final int PHOTO_CODE_TWO = 2;
    private static final int FLAG_AuthenticationAct = 4;
    private String pictureName = "";
    BroadcastReceiver refreshReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Constants.Refresh_User_Center)) {
                LogUtil.d("frag_refresh - user");
                // 目前采用全部刷新的策略，保证所有数据都是最新
                //mMePresenter.getUserInfo();   // 采用这个暂时也可以
                mMePresenter.refreshUserInfo();
            }
            if (intent.getAction().equals(Constants.Push_Gg_Me)) {
                // 呈现公告
                String gg = intent.getStringExtra("Data");
                LogUtil.d("刷新公告：" + gg);
                if(view_laba.getVisibility() == View.GONE)
                    view_laba.setVisibility(View.VISIBLE);
                tv_laba.setText(gg);
                tv_laba.setSelected(true);
            }
            if (intent.getAction().equals(Constants.Refresh_User_Money)) {
                LogUtil.d("frag_refresh - money");
                mMePresenter.getUMoney();
            }
            if (intent.getAction().equals(Constants.Push_Red_Packet)) {
                LogUtil.d("红包处理：" + intent.getStringExtra("Data"));
                mMePresenter.getUMoney();
                updateMessageUnRead();
            }
            if (intent.getAction().equals(Constants.Refresh_Red_Packet_Unread)) {
                LogUtil.d("红包已读");
                AppPreferences.putBoolean(getMContext(), "isShowRedPacket", false);
                updateMessageUnRead();
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_me, null);
        initView(view);

        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.Refresh_User_Center);
        filter.addAction(Constants.Push_Gg_Me);
        filter.addAction(Constants.Refresh_User_Money);
        filter.addAction(Constants.Push_Red_Packet);
        filter.addAction(Constants.Refresh_Red_Packet_Unread);
        getActivity().registerReceiver(refreshReceiver, filter);
        return view;
    }

    @Override
    public void onNetNo() {
        view_nonet.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNetOk() {
        view_nonet.setVisibility(View.GONE);
    }

    public void setClick(boolean click) {
        isClick = click;
    }

    private void initView(View view) {
        mMePresenter = new MePresenter(this);
        ((TextView) view.findViewById(R.id.titleName)).setText(R.string.title_name_me);
        view.findViewById(R.id.titleBack).setVisibility(View.GONE);
        view_nonet = (RelativeLayout) view.findViewById(R.id.view_nonet);
        iv_uic = (RoundImageView) view.findViewById(R.id.iv_uic);
        me_uic_edit = (RelativeLayout) view.findViewById(R.id.me_uic_edit);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_addr = (TextView) view.findViewById(R.id.tv_addr);
        tv_tel = (TextView) view.findViewById(R.id.tv_tel);
        tv_money_have = (TextView) view.findViewById(R.id.tv_money_have);
        tv_share_tip = (TextView) view.findViewById(R.id.tv_share_tip);
        btn_free = (LinearLayout) view.findViewById(R.id.btn_free);
        btn_shop = (LinearLayout) view.findViewById(R.id.btn_shop);
        btn_fan = (LinearLayout) view.findViewById(R.id.btn_fan);
        bt_money_have = (RelativeLayout) view.findViewById(R.id.bt_money_have);
        bt_myMessage = (RelativeLayout) view.findViewById(R.id.bt_myMessage);
        bt_order = (RelativeLayout) view.findViewById(R.id.bt_order);
        bt_pw_change = (RelativeLayout) view.findViewById(R.id.bt_pw_change);
        bt_update_check = (RelativeLayout) view.findViewById(R.id.bt_update_check);
        bt_help_center = (RelativeLayout) view.findViewById(R.id.bt_help_center);
        bt_logout = (RelativeLayout) view.findViewById(R.id.bt_logout);
        bt_yqm = (RelativeLayout) view.findViewById(R.id.bt_yqm);
        bt_collection = (RelativeLayout) view.findViewById(R.id.bt_collection);
        bt_white_line = (RelativeLayout) view.findViewById(R.id.bt_white_line);
        bt_master = (RelativeLayout) view.findViewById(R.id.bt_master);
        bt_recommend = (RelativeLayout) view.findViewById(R.id.bt_recommend);
        bt_send = (RelativeLayout) view.findViewById(R.id.bt_send);
        bt_customer = (RelativeLayout) view.findViewById(R.id.bt_customer);
        yqm_line = view.findViewById(R.id.yqm_line);
        refreshView = (SwipeRefreshLayout) view.findViewById(R.id.refreshView);
        refreshView.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        refreshView.setOnRefreshListener(this);
        view_laba = (RelativeLayout) view.findViewById(R.id.view_laba);
        iv_level = (ImageView) view.findViewById(R.id.iv_level);
        del_laba = (ImageView) view.findViewById(R.id.del_laba);
        iv_unread = (ImageView) view.findViewById(R.id.iv_unread);
        tv_laba = (TextView) view.findViewById(R.id.tv_laba);
        tv_laba.setSelected(true);
        view_laba.setVisibility(View.GONE);
        updateMessageUnRead();

        iv_uic.setOnClickListener(this);
        me_uic_edit.setOnClickListener(this);
        bt_money_have.setOnClickListener(this);
        bt_myMessage.setOnClickListener(this);
        bt_order.setOnClickListener(this);
        bt_pw_change.setOnClickListener(this);
        bt_update_check.setOnClickListener(this);
        bt_help_center.setOnClickListener(this);
        bt_logout.setOnClickListener(this);
        bt_white_line.setOnClickListener(this);
        bt_yqm.setOnClickListener(this);
        bt_collection.setOnClickListener(this);
        bt_master.setOnClickListener(this);
        bt_recommend.setOnClickListener(this);
        bt_send.setOnClickListener(this);
        bt_customer.setOnClickListener(this);
        del_laba.setOnClickListener(this);
        btn_free.setOnClickListener(this);
        btn_shop.setOnClickListener(this);
        btn_fan.setOnClickListener(this);
        showNormal();
        showPWChange();
        mMePresenter.getUserInfo();
        mMePresenter.getUMoney();
        mMePresenter.getGg();
    }

    private void updateMessageUnRead() {
        if(null == iv_unread) return;
        LogUtil.d("isShowRedPacket_" + AppPreferences.getBoolean(getMContext(), "isShowRedPacket"));
        if(AppPreferences.getBoolean(getMContext(), "isShowRedPacket")) {
            iv_unread.setVisibility(View.VISIBLE);
            Glide.with(getMContext())
                    .load(R.drawable.unread_bg)
                    .asGif()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(iv_unread);
        } else {
            iv_unread.setVisibility(View.INVISIBLE);
        }
    }

    private void showNormal() {
        tv_addr.setText(null);
        tv_addr.setVisibility(View.GONE);

        tv_tel.setText("暂未手机认证");
        tv_share_tip.setText("今日还未分享，抓紧分享赚钱吧~");
    }

    // 编辑头像
    private void editUic() {
        Intent intent = new Intent(getActivity(), ImagesSelectorActivity.class);
        intent.putExtra(SelectorSettings.SELECTOR_MAX_IMAGE_NUMBER, 1);
        intent.putExtra(SelectorSettings.SELECTOR_MIN_IMAGE_SIZE, 100000);
        intent.putExtra(SelectorSettings.SELECTOR_SHOW_CAMERA, true);
        //intent.putStringArrayListExtra(SelectorSettings.SELECTOR_INITIAL_SELECTED_LIST, mResults);
        startActivityForResult(intent, Request_Code_Ic);
    }


    // 编辑用户文本资料
    private void editUName(String name, String city) {
        Intent intent = new Intent(getActivity(), EditTextAct.class);
        intent.putExtra("Ask", "MeFrag");
        intent.putExtra("MName", name);
        intent.putExtra("MCity", city);
        intent.putExtra("TitleName", "完 善 资 料");
        startActivityForResult(intent, Request_Code_Name);
    }

    // 退出账号
    private void logout() {
        mSimpleDia = new SimpleDia(getActivity(), SimpleDia.Ok_No_Type);
        mSimpleDia.setTitleText("账号退出")
                .setContentText(Html.fromHtml("您确定要退出当前账号么？"))
                .setConfirmText("确定")
                .setCancelText("取消")
                .showCancelButton(true)
                .setClickListener(new SimpleDia.OnClickButtonListener() {
                    @Override
                    public void onClick(SimpleDia dialog, int flag) {
                        switch (flag) {
                            case 0: // 取消
                                break;
                            case 1: // 确定
                                if(AppPreferences.getBoolean(getMContext(), "needClearWeb")) {
                                    AgentWebConfig.removeAllCookies();
                                    AgentWebConfig.clearDiskCache(getMContext());
                                    AppPreferences.putBoolean(getMContext(), "needClearWeb", false);
                                }
                                mMePresenter.doLogout();
                                break;
                            default:
                                break;
                        }
                    }
                }).show();
    }

    /**
     * 是否显示修改密码功能
     */
    private void showPWChange() {
        String way = AppPreferences.getString(getActivity(), Constants.PNK_UWay, "");
        if (!way.equals("phone")) {
            bt_pw_change.setVisibility(View.GONE);
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
    public String getUicPath() {
        return pictureName;
    }

    @Override
    public void changeUicSuccess(File file) {
        Glide.with(getActivity())
                .load(file)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .placeholder(R.drawable.icon_header)
                .error(R.drawable.icon_header)
                .bitmapTransform(new CropCircleTransformation(getActivity()))
                .into(iv_uic);
    }

    @Override
    public String getYqm() {
        return mYqm;
    }

    @Override
    public void upYqmSuccess() {
        ToastUtils.show(getMContext(), "邀请码填写成功");
        bt_yqm.setVisibility(View.GONE);
        yqm_line.setVisibility(View.GONE);
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
    public int getVersion() {
        try {
            return getVersionCode(getActivity());
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public void installAPK(File file) {
        installApk(file);
    }

    @Override
    public void showSetUpda(String flag) {
        mSimpleDia = new SimpleDia(getActivity(), SimpleDia.Ok_No_Type);
        mSimpleDia.setTitleText("版本升级")
                .setContentText(Html.fromHtml(flag))
                .setConfirmText("更新")
                .setCancelText("忽略")
                .showCancelButton(true)
                .setClickListener(new SimpleDia.OnClickButtonListener() {
                    @Override
                    public void onClick(SimpleDia dialog, int flag) {
                        switch (flag) {
                            case 0: // 取消
                                mMePresenter.finishCheckUpdate(false);
                                break;
                            case 1: // 确定
                                mMePresenter.finishCheckUpdate(true);
                                break;
                            default:
                                break;
                        }
                    }
                }).show();
    }

    @Override
    public void receiveVersionInfo(String version) {

    }

    @Override
    public void logoutSuccess() {
        AppPreferences.clear(getActivity());
        // 如果只退出账号，则不再重新走引导页
        AppPreferences.putBoolean(getActivity(), "isFirstStart", false);
        getActivity().startActivity(new Intent(getActivity(), LoginAct.class));
        AppManager.getAppManager().AppExit(getActivity());
    }

    @Override
    public void receiveUser(LoginUserInfo user) {
        if(!user.isBindYqm()) {
            bt_yqm.setVisibility(View.VISIBLE);
            yqm_line.setVisibility(View.VISIBLE);
        } else {
            bt_yqm.setVisibility(View.GONE);
            yqm_line.setVisibility(View.GONE);
        }

        refreshView.setRefreshing(false);
        Glide.with(getActivity())
                .load(user.getGrade_mark())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(iv_level);
        Glide.with(getActivity())
                .load(user.getuIc())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .error(R.drawable.icon_header)
                .placeholder(R.drawable.icon_header)
                .bitmapTransform(new CropCircleTransformation(getActivity()))
                .into(iv_uic);
        name = user.getName();
        idcard = user.getIdcard();
        bankcard = user.getBank();

        if (StringUtil.isNotEmptyIgnoreBlank(user.getuName())) {
            tv_name.setText(user.getuName());
        } else {
            tv_name.setHint("编辑姓名");
        }
        if (StringUtil.isNotEmptyIgnoreBlank(user.getuTel())) tv_tel.setText(user.getuTel());
        if (StringUtil.isNotEmptyIgnoreBlank(user.getuShareNum())) {
            if ("0".equals(user.getuShareNum())) {
                tv_share_tip.setText("今日还未分享，抓紧分享赚钱吧~");
            } else {
                tv_share_tip.setText("今日您已经分享" + user.getuShareNum() + "条信息哦~ 真棒！");
            }
        }

        if (StringUtil.isNotEmptyIgnoreBlank(user.getuAddr())) {
            tv_addr.setVisibility(View.VISIBLE);
            tv_addr.setText(user.getuAddr());
        }
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
                dialog.dismiss();
                mMePresenter.closeHttp();
                //取消更新的时候把Textview的值初始化一下
                updateValue.setText("");
                updateSize.setText("");
                mProgress.setProgress(0);

            }
        });
        mDownLoadDialog = builder.create();
        mDownLoadDialog.setCancelable(false);
        mDownLoadDialog.show();
        mMePresenter.downNewApk();
    }

    @Override
    public void getUMoney(String money) {
        tv_money_have.setText("￥" + money);
    }

    @Override
    public void showProgress(float progress, long current, long total) {
        String downloadLength = Formatter.formatFileSize(getActivity(), current);
        String totalLength = Formatter.formatFileSize(getActivity(), total);
        // String netSpeed = Formatter.formatFileSize(getApplicationContext(), networkSpeed);
        // LogUtil.e(netSpeed + "/S");     // 下载网速

        mProgress.setProgress((int) (progress * 100));
        updateValue.setText((Math.round(progress * 10000) * 1.0f / 100) + "%");
        updateSize.setText(downloadLength + "/" + totalLength);
    }


    @Override
    public void showError(String msg, boolean flag) {
        refreshView.setRefreshing(false);
        ToastUtils.show(getActivity(), msg);
        if (flag) getActivity().finish();
    }

    @Override
    public void onRefresh() {
        // mMePresenter.getGg();
        mMePresenter.refreshUserInfo();
        mMePresenter.getUMoney();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_free:
                getActivity().startActivity(new Intent(getActivity(), BzActivity.class));
                break;
            case R.id.btn_shop:
                getActivity().startActivity(new Intent(getActivity(), ShopBuyAct.class));
                break;
            case R.id.btn_fan:
                getActivity().startActivity(new Intent(getActivity(), ShopFanAct.class));
                break;
            case R.id.iv_uic:
                choicePhotoWrapper();
                break;
            case R.id.me_uic_edit:
                editUName(tv_name.getText().toString(), tv_addr.getText().toString());
                break;
            case R.id.bt_money_have:
                getActivity().startActivity(new Intent(getActivity(), MoneyHaveAct.class));
                break;
            case R.id.bt_myMessage: {
                getActivity().startActivity(new Intent(getActivity(), MyMessageAct.class));
                AppPreferences.putBoolean(getMContext(), "isShowRedPacket", false);
                updateMessageUnRead();
                break;
            }
            case R.id.bt_order: {
                // true、false没有多大的分别
                AlibcBasePage alibcBasePage = new AlibcMyOrdersPage(0, false);
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
                break;
            }
            case R.id.bt_customer:
                Intent intentcustomer = new Intent(getActivity(), CommonAct.class);
                intentcustomer.putExtra("common", Constants.Customer);
                getActivity().startActivity(intentcustomer);
                break;
            case R.id.bt_pw_change:
                getActivity().startActivity(new Intent(getActivity(), ChangePwAct.class));
                break;
            case R.id.bt_update_check:
                mMePresenter.checkUpdate();
                break;
            case R.id.bt_help_center:
                Intent intent2 = new Intent(getActivity(), CommonAct.class);
                intent2.putExtra("common", Constants.HelperCenter);
                getActivity().startActivity(intent2);
                break;
            case R.id.bt_logout:
                logout();
                break;
            case R.id.bt_white_line:
                if (!isNetworkConnected()) return;
                getActivity().startActivity(new Intent(getActivity(), WhileLineAct.class));
                break;
            case R.id.bt_recommend:
                if (!isNetworkConnected()) return;
                getActivity().startActivity(new Intent(getActivity(), ReCommendAct.class));
                break;
            case R.id.bt_send:
                getActivity().startActivity(new Intent(getActivity(), SendAct.class));
                break;
            case R.id.bt_master:
                Intent authIntent = new Intent(getActivity(), AuthenticationAct.class);
                getActivity().startActivity(authIntent);

                //String flag;
                //判断是不是第一次进入达人认证页面
//                if (StringUtil.isNotEmptyIgnoreBlank(name) && StringUtil.isNotEmptyIgnoreBlank(idcard) && StringUtil.isNotEmptyIgnoreBlank(bankcard)) {
//                    flag = "2";
//                    authIntent.putExtra("flag", flag);
//                    authIntent.putExtra("name", name);
//                    authIntent.putExtra("idcard", idcard);
//                    authIntent.putExtra("bankcard", bankcard);
//                } else {
//                    flag = "1";
//                    authIntent.putExtra("flag", flag);
//                }
                //getActivity().startActivityForResult(authIntent, FLAG_AuthenticationAct);
                break;
            case R.id.bt_collection:
                getActivity().startActivity(new Intent(getActivity(), CollectionAct.class));
                break;
            case R.id.bt_yqm:
                editYqm();
                break;
            case R.id.del_laba: {
                tv_laba.setText(null);
                view_laba.setVisibility(View.GONE);
                break;
            }
        }
    }

    // 编辑邀请码
    private EditDialog mEditDialog;
    private void editYqm() {
        mEditDialog = new EditDialog(getMContext());
        mEditDialog.setClickListener(new EditDialog.OnClickButtonListener() {
            @Override
            public void onClick(EditDialog dialog, int flag) {
                switch (flag) {
                    case 0: // 取消
                        break;
                    case 1: // 确定
                        mYqm = dialog.getEditContent();
                        LogUtil.d("yqm：" + mYqm);
                        if(StringUtil.isEmptyIgnoreBlank(mYqm)) {
                            ToastUtils.show(getMContext(), "邀请码不能为空");
                        } else {
                            mMePresenter.upYqm();
                            mEditDialog.dismiss();
                        }
                        break;
                    default: break;
                }
            }
        }).show();
        mEditDialog.setEditFilter(new InputFilter[]{new InputFilter.LengthFilter(6)});
    }

    private Bitmap head;// 头像Bitmap
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PHOTO_CODE_ONE:
                    mUserHeadDialog.dismiss();
                    cropPhoto(data.getData());// 裁剪图片
                    break;
                case PHOTO_CODE_TWO:
                    File temp = new File(BaseApp.CACHE_DIR + Constants.New_User_Ic + "/head.jpg");
                    cropPhoto(Uri.fromFile(temp));// 裁剪图片
                    mUserHeadDialog.dismiss();
                    break;
                case 3:
                    if (data != null) {
                        Bundle bundle = data.getExtras();
                        head = bundle.getParcelable("data");
                        if (head != null) {
                            //上传服务器代码
                            savePicture(head);// 保存在SD卡中
                            mMePresenter.uploadUic();
                        }
                    }
                    break;
                case Request_Code_Name:
                    tv_addr.setVisibility(View.VISIBLE);
                    tv_name.setText(data.getStringExtra("InputName"));
                    tv_addr.setText(data.getStringExtra("InputCity"));//InputCity
                    break;

                //case FLAG_AuthenticationAct:
                //    LogUtil.d("FLAG_AuthenticationAct");
                //    mMePresenter.getUserInfo();
                //    break;
            }
        }
    }

    /**
     * 获取当前版本号
     * @return
     * @throws Exception
     */
    public static int getVersionCode(Context context) throws Exception {
        //获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        //getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        return packInfo.versionCode;
    }

    //安装apk
    protected void installApk(File file) {
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

    private void choicePhotoWrapper() {
        //需要申请的权限
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        //检查是否获取该权限
        if (EasyPermissions.hasPermissions(getMContext(), perms)) {
            mUserHeadDialog = new UpdateUserHeadDialog(getMContext(), iv_uic);
            /**************Dialog设置全屏***************/
            WindowManager windowManager = getActivity().getWindowManager();
//                WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            Display display = windowManager.getDefaultDisplay();
            WindowManager.LayoutParams lp = mUserHeadDialog.getWindow().getAttributes();
            lp.width = (int) (display.getWidth()); //设置宽度
            mUserHeadDialog.getWindow().setAttributes(lp);
            /****************************************/
            Window window = mUserHeadDialog.getWindow();
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.dialogWindowAnim);
            mUserHeadDialog.show();
        } else {
            //第二个参数是被拒绝后再次申请该权限的解释
            //第三个参数是请求码
            //第四个参数是要申请的权限
            EasyPermissions.requestPermissions(this, "图片选择需要以下权限:\n\n1.访问设备上的照片\n\n2.拍照", REQUEST_CODE_PERMISSION_PHOTO_PICKER, perms);
        }
    }

    /**
     * 调用系统的裁剪
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        LogUtil.d("cropPhoto");
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 120);
        intent.putExtra("outputY", 120);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

    //包存到本地图片
    public void savePicture(Bitmap bitmap) {
        FileUtils fu = new FileUtils();
        pictureName = BaseApp.CACHE_DIR + Constants.New_User_Ic +
                System.currentTimeMillis() + ".jpg";
//        PrefsUtil.setString(SettingActivity.this,"headImgUrl",pictureName);
        File file = new File(pictureName);
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //成功
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) { }

    //失败
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (requestCode == REQUEST_CODE_PERMISSION_PHOTO_PICKER) {
            Toast.makeText(getMContext(), "您拒绝了「图片选择」所需要的相关权限!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //把申请权限的回调交由EasyPermissions处理
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.d("meFrag - onResume");
        updateMessageUnRead();
    }

    @Override
    public void onDestroy() {
        mMePresenter.closeHttp();
        hideLoading();
        if (mSimpleDia != null) mSimpleDia.dismissWithAnimation();
        if (mEditDialog != null) mEditDialog.dismiss();
        if (mLoadingDialog != null) mLoadingDialog.dismiss();
        if (mDownLoadDialog != null) mDownLoadDialog.dismiss();
        if (refreshReceiver != null) getActivity().unregisterReceiver(refreshReceiver);
        AlibcTradeSDK.destory();
        super.onDestroy();
    }

}
