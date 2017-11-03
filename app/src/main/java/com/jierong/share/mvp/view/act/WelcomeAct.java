package com.jierong.share.mvp.view.act;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.igexin.sdk.PushManager;
import com.jierong.share.Constants;
import com.jierong.share.R;
import com.jierong.share.mvp.model.info.AdvInfo;
import com.jierong.share.mvp.presenter.WelcomePresenter;
import com.jierong.share.mvp.view.IWelcomeView;
import com.jierong.share.service.MgtIntentService;
import com.jierong.share.service.MgtService;
import com.jierong.share.util.LogUtil;
import com.jierong.share.util.ToastUtils;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 欢迎界面
 */
public class WelcomeAct extends AppCompatActivity implements IWelcomeView,
        EasyPermissions.PermissionCallbacks {
    private WelcomePresenter mWelcomePresenter;
    private ImageView wel_img;
    private String pushId;
    // android.permission.MANAGE_ACCOUNTS
    // 挂载、反挂载外部文件系统     Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS
    // 允许程序获取当前或最近运行的应用     Manifest.permission.GET_TASKS
    // 显示系统窗口       Manifest.permission.SYSTEM_ALERT_WINDOW

    BroadcastReceiver pushReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Constants.Push_Action_GetId)) {
                LogUtil.d("Act_wel - pr");
                pushId = intent.getStringExtra("Pid");
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  Debug.startMethodTracing("GithubApp");这个debug在6.0以上的手机中需要动态获取权限
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.Push_Action_GetId);
        registerReceiver(pushReceiver, filter);
        init();
       // Debug.stopMethodTracing();
    }

    private void init() {
        mWelcomePresenter = new WelcomePresenter(this);
        View rootView = LayoutInflater.from(this).inflate(R.layout.act_welcome, null);
        setContentView(rootView);
        wel_img = (ImageView) findViewById(R.id.wel_img);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha_in);
        rootView.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (Build.VERSION.SDK_INT >= 23) {
                    LogUtil.e("Act_wel - sys6.0+");
                    locationTask();
                } else {
                    LogUtil.e("Act_wel - sys6.0-");
                    initSdk();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

    }

    // 申请位置权限
    @AfterPermissionGranted(Constants.Permission_Location)
    public void locationTask() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            LogUtil.e("locationTask success");
            phoneTask();
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.permission_tip_location),
                    Constants.Permission_Location, Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }

    // 申请手机权限
    @AfterPermissionGranted(Constants.Permission_Phone)
    public void phoneTask() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.READ_PHONE_STATE)) {
            LogUtil.e("phoneTask success");
            storageTask();
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(this, getString(R.string.permission_tip_phone),
                    Constants.Permission_Phone, Manifest.permission.READ_PHONE_STATE);
        }
    }

    // 申请SD卡读写权限
    @AfterPermissionGranted(Constants.Permission_Storage)
    public void storageTask() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            LogUtil.e("storageTask success");
            cameraTask();
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(this, getString(R.string.permission_tip_sd),
                    Constants.Permission_Storage, Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    // 申请拍照权限，暂时好多手机都不能实现
    @AfterPermissionGranted(Constants.Permission_Camera)
    public void cameraTask() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
            LogUtil.e("cameraTask success");
            accountsTask();
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(this, getString(R.string.permission_tip_camera),
                    Constants.Permission_Camera, Manifest.permission.CAMERA);
        }
    }

    // 申请用户列表（联系人）数据权限
    @AfterPermissionGranted(Constants.Permission_Accounts)
    public void accountsTask() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.GET_ACCOUNTS)) {
            LogUtil.e("accountsTask success");
            initSdk();
            LogUtil.e("-- initSdk --");
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.permission_tip_accounts),
                    Constants.Permission_Accounts, Manifest.permission.GET_ACCOUNTS);
        }
    }

    private void initSdk() {
        // 初始化个推
        refreshPushId();
        mWelcomePresenter.init();
    }

    @Override
    public Context getMContext() {
        return WelcomeAct.this;
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void refreshPushId() {
        PushManager.getInstance().initialize(
                this.getApplicationContext(), MgtService.class);
        PushManager.getInstance().registerPushIntentService(
                this.getApplicationContext(), MgtIntentService.class);
        LogUtil.d(" - wel_refreshPushId");
    }

    @Override
    public String getPushId() {
        return pushId;
    }

    @Override
    public void showNetAdv(AdvInfo advInfo) {
//        Glide.with(this)
//                .load(advInfo.getPic_path())
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                .into(wel_img);
    }

    @Override
    public void showLocalAdv() {
//        wel_img.setImageResource(R.drawable.demo_adv);
    }

    @Override
    public void showError(String msg, boolean flag) {
        ToastUtils.show(this, msg);
        if (flag) finish();//没有网络的时候退出程序，这里暂时屏蔽掉。应xx要求，没有网络的情况下不退出程序
    }

    @Override
    public void turnToGuide() {
        Intent intent = new Intent(WelcomeAct.this, GuideAct.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void turnToLogin() {
        Intent intent = new Intent(WelcomeAct.this, LoginAct.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void turnToBind() {
        Intent intent1 = new Intent(this, BindAct.class);
        startActivity(intent1);
        finish();
    }

    @Override
    public void turnToMain() {
        Intent intent = new Intent(WelcomeAct.this, MainAct.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        LogUtil.e("-- onRequestPermissionsResult --");
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        LogUtil.e("-- onPermissionsGranted --");
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            LogUtil.e("-- onPermissionsDenied -- " + requestCode);
            String tip = null;
            switch (requestCode) {
                case Constants.Permission_Camera:
                    tip = getString(R.string.permission_tip_camera);
                    break;
                case Constants.Permission_Phone:
                    tip = getString(R.string.permission_tip_phone);
                    break;
                case Constants.Permission_Storage:
                    tip = getString(R.string.permission_tip_sd);
                    break;
                case Constants.Permission_Location:
                    tip = getString(R.string.permission_tip_location);
                    break;
                case Constants.Permission_Accounts:
                    tip = getString(R.string.permission_tip_accounts);
                    break;
                default:
                    break;
            }

            new AppSettingsDialog.Builder(this)
                    .setTitle("权限申请")
                    .setRationale(tip)
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    })
                    .build()
                    .show();
        } else {
            LogUtil.e("-- onPermissionsDenied -- 0");
            finish();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            // Do something after user returned from app settings screen, like showing a Toast.
            LogUtil.e("Permissions onActivityResult");
            locationTask();
        }
    }

    // 刷启动页的时候屏蔽返回键
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        mWelcomePresenter.closeHttp();
        if (pushReceiver != null) unregisterReceiver(pushReceiver);
        super.onDestroy();
        LogUtil.e("Act_wel - onDes");
    }

}