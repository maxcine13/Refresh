package com.jierong.share.mvp.view.act;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.jierong.share.BaseAct;
import com.jierong.share.Constants;
import com.jierong.share.R;
import com.jierong.share.okhttp.BaseHttpUtil;
import com.jierong.share.okhttp.HttpStringCallBack;
import com.jierong.share.util.AppPreferences;
import com.jierong.share.util.LogUtil;
import com.lzy.okgo.model.HttpParams;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.Timer;
import java.util.TimerTask;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * create by Song
 * 2016-04-15
 */
public class taotestAc extends BaseAct implements View.OnClickListener {
    @Bind(R.id.button2)
    Button button2;
    @Bind(R.id.button3)
    Button button3;
    @Bind(R.id.button4)
    Button button4;
    @Bind(R.id.button5)
    Button button5;
    @Bind(R.id.button6)
    Button button6;
    @Bind(R.id.button7)
    Button button7;
    @Bind(R.id.relative2)
    LinearLayout relative2;
    @Bind(R.id.button8)
    Button button8;
    @Bind(R.id.button9)
    Button button9;
    @Bind(R.id.button10)
    Button button10;
    @Bind(R.id.button11)
    Button button11;
    @Bind(R.id.button12)
    Button button12;
    @Bind(R.id.relative3)
    LinearLayout relative3;
    @Bind(R.id.button13)
    Button button13;
    @Bind(R.id.button14)
    Button button14;
    @Bind(R.id.button15)
    Button button15;
    @Bind(R.id.button16)
    Button button16;
    @Bind(R.id.button17)
    Button button17;
    @Bind(R.id.relative4)
    LinearLayout relative4;
    @Bind(R.id.button18)
    Button button18;
    @Bind(R.id.button19)
    Button button19;
    @Bind(R.id.button20)
    Button button20;
    private ImageView ivQQ;
    //下拉布局的高度
    private int rlTopShareHeight;
    private ImageButton ibtnClose;
    private RelativeLayout topbar ,rlShare;
    private TextView ibtnTopShare;
    private ObjectAnimator topUpAnimation;
    private ObjectAnimator topPullAnimation;
    private WebView tao_webview;

    private static final String TAG = "-SONG-";
    private ProgressBar progressBar;
    private TextView yesNoNet;
    private int cont = 0;
    private Timer timer;
    Handler handler1 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            LogUtil.d("-----------" + msg.what);
            if (msg.what == 100) {
                timer.cancel();
                progressBar.setVisibility(View.GONE);
                cont = 0;//进度条恢复为0；打开下个网页继续走。
            } else {
                progressBar.setProgress(msg.what);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        ButterKnife.bind(this);
        initView();
        setListener();


    }

    public void progressBarStar() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler1.sendEmptyMessage(cont++);
            }
        }, 0, 10);
    }


    @Override
    public void onNetNo() {

    }

    @Override
    public void onNetOk() {

    }

    /**
     * 初始化View
     */
    private void initView() {
        getDatabt("allgood");
        progressBar = (ProgressBar) findViewById(R.id.pb);
        tao_webview = (WebView) findViewById(R.id.tao_webview);
        ivQQ = (ImageView) findViewById(R.id.iv_qq);
        //  ibtnTopShare = (TextView) findViewById(R.id.ibtn_top_share);
        ibtnClose = (ImageButton) findViewById(R.id.ibtn_close);
        rlShare = (RelativeLayout) findViewById(R.id.rl_share);
        topbar = (RelativeLayout) findViewById(R.id.topbar);
        //  rlBottomShare = (RelativeLayout) findViewById(R.id.rl_bottom_share);
        //解决在onCreate中不能获取高度的问题
        rlShare.post(new Runnable() {
            @Override
            public void run() {
                rlTopShareHeight = rlShare.getHeight();
                initAnimation();
            }
        });
    }

    /**
     * 注册事件
     */
    private void setListener() {
        ivQQ.setOnClickListener(this);
        ibtnClose.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);
        button10.setOnClickListener(this);
        button11.setOnClickListener(this);
        button12.setOnClickListener(this);
        button13.setOnClickListener(this);
        button14.setOnClickListener(this);
        button15.setOnClickListener(this);
        button16.setOnClickListener(this);
        button17.setOnClickListener(this);
        button18.setOnClickListener(this);
        button19.setOnClickListener(this);
        button20.setOnClickListener(this);
        topbar.setOnClickListener(this);

        //ibtnTopShare.setOnClickListener(this);
        //   ibtnBottomClose.setOnClickListener(this);
    }

    /**
     * 初始化Animation
     */
    private void initAnimation() {
        /**
         * 顶部动画
         */
        //打开动画
        topPullAnimation = ObjectAnimator.ofFloat(
                rlShare, "translationY", rlTopShareHeight);
        topPullAnimation.setDuration(1000);
        topPullAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        //关闭动画
        topUpAnimation = ObjectAnimator.ofFloat(
                rlShare, "translationY", -rlTopShareHeight);
        topUpAnimation.setDuration(500);
        topUpAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        topUpAnimation.start();
    }

    /**
     * 单击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.topbar: {
                Intent searchIntent = new Intent(taotestAc.this, WebViewAct.class);
                searchIntent.putExtra("Url",
                        "https://temai.m.taobao.com/new/index.htm?pid=mm_123166441_28158373_109094623");
                taotestAc.this.startActivity(searchIntent);
                taotestAc.this.finish();
                break;
            }
            case R.id.button2:
                getDatabt("allgood");
                break;
            case R.id.button3:
                getDatabt("girl");
                break;
            case R.id.button4:
                getDatabt("boy");
                break;
            case R.id.button5:
                getDatabt("shoes");
                break;
            case R.id.button6:
                getDatabt("food");
                break;
            case R.id.button7:
                getDatabt("beau");
                break;
            case R.id.button8:
                getDatabt("ha");
                break;
            case R.id.button9:
                getDatabt("jd");
                break;
            case R.id.button10:
                getDatabt("sport");
                break;
            case R.id.button11:
                getDatabt("mson");
                break;
            case R.id.button12:
                getDatabt("clothes");
                break;
            case R.id.button13:
                getDatabt("digital");
                break;
            case R.id.button14:
                getDatabt("hi");
                break;
            case R.id.button15:
                getDatabt("hp");
                break;
            case R.id.button16:
                getDatabt("car");
                break;
            case R.id.button17:
                getDatabt("server");
                break;
            case R.id.button18:
                getDatabt("vedio");
                break;
            case R.id.button19:
                getDatabt("play");
                break;
            case R.id.button20:
                getDatabt("other");
                break;
            default:
                break;
        }
    }

    //分类按钮网络请求 分类是商品库中的商品分类。 搜索是对整个库进行搜索
    public void getDatabt(String category) {
        BaseHttpUtil baseHttpUtil = new BaseHttpUtil();
        String url = Constants.Http_Api_GoodsUrl;
        HttpParams httpParams = new HttpParams();
        httpParams.put("uid", getGlobalId());
        httpParams.put("token", getGlobalToken());
        httpParams.put("category", category);
        baseHttpUtil.doPost(url, httpParams, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                LogUtil.d("result" + result.toString());
                try {
                    JSONObject jsonObject = new JSONObject(result.toString());
                    String url = jsonObject.getString("url");
                    openWebview(url);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int code, String msg) {

            }
        });
    }

    //商品搜索网络请求
    public void getData(String goodname) {
        BaseHttpUtil baseHttpUtil = new BaseHttpUtil();
        String url = Constants.Http_Api_GoodsSS;
        HttpParams httpParams = new HttpParams();
        httpParams.put("uid", getGlobalId());
        httpParams.put("token", getGlobalToken());
        httpParams.put("goodname", goodname);
        baseHttpUtil.doPost(url, httpParams, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                LogUtil.d("result" + result.toString());
                try {
                    JSONObject jsonObject = new JSONObject(result.toString());
                    String url = jsonObject.getString("url");
                    openWebview(url);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int code, String msg) {

            }
        });
    }

    /**
     * 加载webview的方法及设置
     */
    private void openWebview(String url) {
        progressBarStar();
        WebSettings webSettings = tao_webview.getSettings();
        webSettings.setJavaScriptEnabled(true);//支持js
        tao_webview.getSettings().setAppCacheEnabled(true); //开启缓存功能
        tao_webview.loadUrl(url);

//        tao_webview.setWebViewClient(new WebViewClient() {
//
//            @Override
//            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
//                super.onReceivedError(view, request, error);
//                yesNoNet.setText("我的小主人，奴家找不到网页了");
//                yesNoNet.setVisibility(View.VISIBLE);
//                LogUtil.d("--------------------------------------------");
//            }
//            @TargetApi(Build.VERSION_CODES.LOLLIPOP)//新的属性写法，必须设置api的属性
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                view.loadUrl(request.getUrl().toString());
//                return true;
//            }
//        });
        /*
        设置进度条
         */
        tao_webview.setWebChromeClient(new WebChromeClient() {


            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                LogUtil.d("ProgressChanged++  " + newProgress);


                super.onProgressChanged(view, newProgress);
            }
        });

        /*
        webView监听返回。
         */
        tao_webview.setOnKeyListener(new View.OnKeyListener() {

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_BACK) && tao_webview.canGoBack()) {
                    tao_webview.goBack();
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
}
