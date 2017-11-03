package com.jierong.share.mvp.view.act;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.jierong.share.BaseAct;
import com.jierong.share.Constants;
import com.jierong.share.R;
import com.jierong.share.mvp.model.info.RecommendInfo;
import com.jierong.share.okhttp.BaseHttpUtil;
import com.jierong.share.okhttp.HttpStringCallBack;
import com.jierong.share.util.LogUtil;
import com.jierong.share.util.ToastUtils;
import com.jierong.share.util.Util;
import com.jierong.share.widget.LoadingDialog;
import com.lzy.okgo.model.HttpParams;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lovvol on 2017-04-28.
 */
public class ReCommendAct extends BaseAct {
    private TextView tvYqm, tvTip;
    private RecommendInfo recommendInfo;
    private BaseHttpUtil baseHttpUtil;
    private Dialog mLoadingDialog;
    private FrameLayout titleMenu;
    private boolean netState = false;
    private IWXAPI api;
    private static final int THUMB_SIZE = 150;
    private String title, description, url5;
    private int mTargetScene = SendMessageToWX.Req.WXSceneTimeline;

    @Override
    public void onNetNo() {
        netState = true;
    }

    @Override
    public void onNetOk() {
        if (netState) {
            netState = false;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注意Constants.APP_ID_WX可能有错
        api = WXAPIFactory.createWXAPI(this, null);
        // 将该app注册到微信
        api.registerApp(Constants.APP_ID_WX);
        setContentView(R.layout.act_recommetd);
        initview();
    }

    public void getData() {
        if (!isNetworkConnected()) {
            ToastUtils.show(ReCommendAct.this, "网络连接错误，请检查网络!");
            netState = true;
            return;
        }
        showLoading();
        baseHttpUtil = new BaseHttpUtil();
        final String url = Constants.Http_Api_Recommend;
        HttpParams httpParams = new HttpParams();
        httpParams.put("uid", getGlobalId());
        httpParams.put("token", getGlobalToken());
        baseHttpUtil.doPost(url, httpParams, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                recommendInfo = RecommendInfo.fromJSON(result.toString());
                tvYqm.setText(recommendInfo.getRecode());
                tvTip.setText(recommendInfo.getDesc());
                hideLoading();
            }

            @Override
            public void onFailure(int code, String msg) {
                hideLoading();
                LogUtil.e(msg);
                ToastUtils.show(ReCommendAct.this, msg);
            }
        });
/*
微信朋友圈分享
 */
        String url2 = Constants.Http_Api_WXShare;
        HttpParams httpParams2 = new HttpParams();
        httpParams2.put("uid", getGlobalId());
        httpParams2.put("token", getGlobalToken());
        new BaseHttpUtil().doPost(url2, httpParams2, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                Log.d("share", "onSuccess: " + result.toString());
                try {
                    JSONObject object = new JSONObject(result.toString());
                    title = object.getString("title");
                    description = object.getString("description");
                    url5 = object.getString("url");
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(int code, String msg) {

            }
        });
    }

    public void initview() {
        ((TextView) findViewById(R.id.titleName)).setText(R.string.me_recommend);
        findViewById(R.id.titleBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        titleMenu = (FrameLayout) findViewById(R.id.titleMenu);
        titleMenu.setVisibility(View.VISIBLE);
        titleMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("WXShare", "onClick: " + title + description);
                if (!api.isWXAppInstalled()) {//检测是否安装微信客户端
                    ToastUtils.show(ReCommendAct.this, "请先安装微信客户端！");
                } else {
                    if (!title.equals("") && !description.equals("") && !url5.equals("")) {//防止接口传来空参数导致分享“失败”
                        WXShare(title, description, url5);
                    } else {
                        ToastUtils.show(ReCommendAct.this, "暂无法分享");
                    }
                }
            }
        });
        tvYqm = (TextView) findViewById(R.id.tvYqm);
        tvTip = (TextView) findViewById(R.id.tvTip);
        getData();
    }

    //分享至微信朋友圈
    public void WXShare(String title, String description, String url) {
        //初始化一个WXWebpageObject对象，用来填写url
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = description;
        //图片采用默认的应用图标，不设置的话，发到好友是微信平台上传的图标，朋友圈的自己设置，不设置就没有
        //Util这个工具类是将Bitmap装换为byte字节
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
        bmp.recycle();
        msg.thumbData = Util.bmpToByteArray(thumbBmp, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = mTargetScene;
        api.sendReq(req);
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideLoading();
        closeNet(baseHttpUtil);
    }

    public void showLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = LoadingDialog.createLoadingDialog(ReCommendAct.this, "正在加载中...");
            mLoadingDialog.show();
        }
    }


    public void hideLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }
}
