package com.jierong.share.mvp.view.act;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaychan.viewlib.NumberRunningTextView;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jierong.share.BaseAct;
import com.jierong.share.Constants;
import com.jierong.share.R;
import com.jierong.share.mvp.model.info.WhileLineInfo;
import com.jierong.share.okhttp.BaseHttpUtil;
import com.jierong.share.okhttp.HttpStringCallBack;
import com.jierong.share.util.ToastUtils;
import com.jierong.share.widget.LoadingDialog;
import com.lzy.okgo.model.HttpParams;


/**
 * 白条界面
 */
public class WhileLineAct extends BaseAct implements View.OnClickListener {
    private BaseHttpUtil baseHttpUtil;
    private WhileLineInfo whileLineInfo;
    private TextView  leftbar2, Whitebars, sevenday, tenday, all, textView10;
    private NumberRunningTextView textView8;
    private Dialog mLoadingDialog;//
    private ImageView mImageView;//
    private final int WhileLineAct_FLAG = 1;
    private String flag="";
    private boolean netState = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_while_line);
        initview();

    }

    public void initview() {
        ((TextView) findViewById(R.id.titleName)).setText(R.string.while_line);
        ((FrameLayout) findViewById(R.id.titleMenu)).setVisibility(View.VISIBLE);
        mImageView = (ImageView) findViewById(R.id.imgMenu);
        mImageView.setImageResource(R.drawable.ic_whiteline);
        findViewById(R.id.titleBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        textView8 = (NumberRunningTextView) findViewById(R.id.textView8);
        textView10 = (TextView) findViewById(R.id.textView10);

        leftbar2 = (TextView) findViewById(R.id.leftbar2);
        Whitebars = (TextView) findViewById(R.id.Whitebars);
        sevenday = (TextView) findViewById(R.id.sevenday);
        tenday = (TextView) findViewById(R.id.tenday);
        all = (TextView) findViewById(R.id.all);
        mImageView.setOnClickListener(this);
        getData();
        activationMethod();
    }

    public void getData() {
        if (!isNetworkConnected()) {
            ToastUtils.show(WhileLineAct.this, "网络连接错误，请检查网络!");
            netState = true;
            return;
        }
        showLoading();
        baseHttpUtil = new BaseHttpUtil();
        String url = Constants.Http_Api_While_line;
        HttpParams httpParams = new HttpParams();
        httpParams.put("uid", getGlobalId());
        httpParams.put("token", getGlobalToken());
        baseHttpUtil.doPost(url, httpParams, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                hideLoading();
                whileLineInfo = WhileLineInfo.fromJSON(result.toString());
                if (whileLineInfo != null) {
                    textView8.setContent(whileLineInfo.getLeftbar());

                  //  textView8.setText(whileLineInfo.getLeftbar());
                    leftbar2.setText(whileLineInfo.getLeftbar());
                    Whitebars.setText(whileLineInfo.getWhitebars());
                    sevenday.setText(whileLineInfo.getSevenday() + "元");
                    tenday.setText(whileLineInfo.getTenday() + "元");
                    all.setText(whileLineInfo.getAllback() + "元");
                    getLousState();
                }

            }

            @Override
            public void onFailure(int code, String msg) {
                ToastUtils.show(WhileLineAct.this, msg);
                hideLoading();
            }
        });

    }

    @Override
    public void onNetNo() {
        netState = true;
    }

    @Override
    public void onNetOk() {
        if (netState) {
            getData();
            netState = false;
        }
    }

    /**
     * 判断用户是否已经激活额度
     */
    private void getLousState() {
        showLoading();
        String url = Constants.Http_Api_Jiwhite;
        BaseHttpUtil baseHttpUtil = new BaseHttpUtil();
        HttpParams httpParams = new HttpParams();
        httpParams.put("uid", getGlobalId());
        httpParams.put("token", getGlobalToken());
        baseHttpUtil.doPost(url, httpParams, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                hideLoading();
                JsonObject jsonObject = new JsonParser().parse(result.toString()).getAsJsonObject();
                flag = jsonObject.get("flag").getAsInt() + "";
                if (flag.equals("1")) {//1代表已激活,2代表未激活
                    textView10.setText("已激活");
                } else {
                    textView10.setText("激活额度");
                }

            }

            @Override
            public void onFailure(int code, String msg) {
                ToastUtils.show(WhileLineAct.this, msg);
                hideLoading();
            }
        });
    }

    public void showLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = LoadingDialog.createLoadingDialog(WhileLineAct.this, "正在加载中...");
            mLoadingDialog.show();
        }
    }

    public void hideLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }

    /**
     * 如果没有激活额度,跳转到激活页面
     *
     * @param
     */
    private void activationMethod() {
        textView10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //如果额度为0,不允许跳转到激活页面
                if (!isNetworkConnected()) {
                    ToastUtils.show(WhileLineAct.this, "网络连接错误，请检查网络!");
                    netState = true;
                    return;
                }else {
                    if (flag.equals("1"))//1代表已激活,2代表未激活
                        return;
                    if (!(textView8.getText().toString().equals("0.00"))) {
                        Intent intent = new Intent(WhileLineAct.this, ActivationAct.class);
                        startActivityForResult(intent, WhileLineAct_FLAG);
                    } else {
                        ToastUtils.show(WhileLineAct.this, "额度为0,不能激活");
                    }
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (WhileLineAct_FLAG == requestCode && resultCode == RESULT_OK) {
            //在激活页面激活后,返回此页面刷新
            getLousState();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgMenu:
                Intent intent = new Intent(WhileLineAct.this, CommonAct.class);
                intent.putExtra("common", Constants.WhileLineDetaile);
                startActivity(intent);
                break;
        }
    }

}
