package com.jierong.share;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import com.ali.auth.third.core.MemberSDK;
import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeInitCallback;
import com.jierong.share.util.LogUtil;
import com.jierong.share.util.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.mob.MobApplication;
import java.io.File;

/**
 * 应用程序入口
 */
public class BaseApp extends MobApplication {
    private int activityCount;
    private static BaseApp instance;
    private static Context mContext;
    public static final String CACHE_DIR;   // 本地缓存全路径名目录

    static {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            CACHE_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
        } else {
            CACHE_DIR = Environment.getRootDirectory().getAbsolutePath() + File.separator;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        BaseApp.instance = this;

        init();
    }

    private void init() {
        LogUtil.isOpen = false;
        ToastUtils.isShow = false;
        mContext = getApplicationContext();

        createCacheDir();
        initOkHttp();
        initABC();
        checkIsRunning();
    }

    /**
     * 获取系统可自动回收的Context引用
     * @return
     */
    public static Context getContext() {
        return mContext;
    }

    /**
     * 获取BaseApp实例对象
     * @return
     */
    public static BaseApp getInstance() {
        return instance;
    }

    /**
     * 检查app是否在后台运行
     * @return
     */
    public boolean isBackground() {
        return activityCount == 0;
    }

    private void createCacheDir() {
        File f = new File(CACHE_DIR);
        if (f.exists()) {
            LogUtil.d("SD卡缓存目录:已存在!");
        } else {
            if (f.mkdirs()) {
                LogUtil.d("SD卡缓存目录:" + f.getAbsolutePath() + "已创建!");
            } else {
                LogUtil.d("SD卡缓存目录:创建失败!");
            }
        }
        LogUtil.d("Constants.New_User_Ic"+CACHE_DIR+Constants.New_User_Ic);
        File fff = new File(CACHE_DIR+Constants.New_User_Ic);
        if (fff.exists()) {
            System.out.println("SD卡图片缓存目录:已存在!");
        } else {
            if (fff.mkdirs()) {
                LogUtil.d("SD卡图片缓存目录:" + fff.getAbsolutePath() + "已创建!");
            } else {
                LogUtil.d("SD卡图片缓存目录:创建失败!");
            }
        }
    }

    private void initOkHttp() {
        OkGo.init(this);
        try {
            OkGo.getInstance()
                    // .debug("Test_Q")
                    .setConnectTimeout(5000)  // 全局的连接超时时间(默认的 60秒)
                    .setReadTimeOut(OkGo.DEFAULT_MILLISECONDS)     // 全局的读取超时时间
                    .setWriteTimeOut(OkGo.DEFAULT_MILLISECONDS)    // 全局的写入超时时间
                    .setCacheMode(CacheMode.NO_CACHE);				// 设置缓存模式
            // .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)	// 设置缓存时间,默认永不过期
            // 如果不想让框架管理cookie,以下不需要
            // .setCookieStore(new MemoryCookieStore())		// cookie使用内存缓存（app退出后，cookie消失
            // .setCookieStore(new PersistentCookieStore());	// cookie持久化存储，如果cookie不过期，则一直有效

            // 可以设置https的证书
            // .setCertificates()	// 1.信任所有证书
            // .setCertificates(getAssets().open("srca.cer"))	// 2.自己设置https证书
            // 3.传入bks证书,密码,和cer证书,支持双向加密
            // .setCertificates(getAssets().open("aaaa.bks"), "123456", getAssets().open("srca.cer"))
            // 可以添加全局拦截器(慎用)
            // .addCommonHeaders(headers)	// 设置全局公共头
            // .addCommonParams(params)	// 设置全局公共参数
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 初始化阿里百川
    private void initABC() {
        MemberSDK.turnOnDebug();    // 开启百川日志
        AlibcTradeSDK.asyncInit(this, new AlibcTradeInitCallback() {
            @Override
            public void onSuccess() {
                // 初始化成功，设置相关的全局配置参数
                LogUtil.d("abc init success!");
            }

            @Override
            public void onFailure(int code, String msg) {
                // 初始化失败
                LogUtil.e(code + "_" + msg);
            }
        });
    }

    private void checkIsRunning() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) { }

            @Override
            public void onActivityStarted(Activity activity) {
                activityCount++;
            }

            @Override
            public void onActivityResumed(Activity activity) { }

            @Override
            public void onActivityPaused(Activity activity) { }

            @Override
            public void onActivityStopped(Activity activity) {
                activityCount--;
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) { }

            @Override
            public void onActivityDestroyed(Activity activity) { }
        });
    }

}
