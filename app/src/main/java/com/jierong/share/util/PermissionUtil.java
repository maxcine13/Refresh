package com.jierong.share.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.ContextCompat;

/**
 * 检查权限工具类
 */
public class PermissionUtil {
    private Context mContext;

    public PermissionUtil(Context context) {
        this.mContext = context.getApplicationContext();
    }

    /**
     * 判断是否缺少权限
     * PackageManager.PERMISSION_GRANTED 授予权限
     * PackageManager.PERMISSION_DENIED 缺少权限
     * @param permission
     * @return
     */
    private boolean isLackPermission(String permission) {
        return ContextCompat.checkSelfPermission(mContext, permission)
                == PackageManager.PERMISSION_DENIED;
    }

    public boolean permissionSet(String... permissions) {
        for (String permission : permissions) {
            //是否添加完全部权限集合
            if (isLackPermission(permission)) return true;
        }
        return false;
    }

    /**
     * 相机是否可用
     * @return true表示可以使用; false表示不可以使用
     */
    public boolean cameraIsCanUse() {
        boolean isCanUse = true;
        Camera mCamera = null;
        try {
            mCamera = Camera.open();
            Camera.Parameters mParameters = mCamera.getParameters();
            mCamera.setParameters(mParameters);
        } catch (Exception e) {
            isCanUse = false;
        }

        if (mCamera != null) {
            try {
                mCamera.release();
            } catch (Exception e) {
                e.printStackTrace();
                return isCanUse;
            }
        }
        return isCanUse;
    }

    private String getPackageName(Context context) {
        PackageManager pm = context.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo.packageName;
    }

    /**
     * 获取应用详情页面intent
     * @param context
     * @return
     */
    public Intent getAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", getPackageName(context), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", getPackageName(context));
        }
        return localIntent;
    }









    /**
     * Android6.0 打开自启动管理页面（华为、小米）
     * http://blog.csdn.net/jin_qing/article/details/53087538
     * @param context
     */
//    public void openStart(Context context){
//        if(Build.VERSION.SDK_INT < 23){
//            return;
//        }
//        String system = getSystem();
//        Intent intent = new Intent();
//        if(system.equals(SYS_EMUI)){//华为
//            ComponentName componentName = new ComponentName("com.huawei.systemmanager","com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity");
//            intent.setComponent(componentName);
//        }else if(system.equals(SYS_MIUI)){//小米
//            ComponentName componentName = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity");
//            intent.setComponent(componentName);
//        }
//        try{
//            context.startActivity(intent);
//        }catch (Exception e){//抛出异常就直接打开设置页面
//            intent=new Intent(Settings.ACTION_SETTINGS);
//            context.startActivity(intent);
//        }
//    }


}
