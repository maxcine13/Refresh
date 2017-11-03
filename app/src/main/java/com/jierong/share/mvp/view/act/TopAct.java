package com.jierong.share.mvp.view.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import com.jierong.share.AppManager;
import com.jierong.share.BaseAct;
import com.jierong.share.util.AppPreferences;
import com.jierong.share.util.LogUtil;
import com.jierong.share.widget.SimpleDia;

/**
 * 顶端透明界面
 */
public class TopAct extends BaseAct {
    private SimpleDia mSimpleDia;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
    }

    @Override
    public void onNetNo() { }

    @Override
    public void onNetOk() { }

    private void init() {
        // 无论如何先清理数据
        AppPreferences.clear(this);
        // 如果只退出账号，则不再重新走引导页
        AppPreferences.putBoolean(this, "isFirstStart", false);
        mSimpleDia = new SimpleDia(TopAct.this, SimpleDia.Ok_No_Type);
        mSimpleDia.setTitleText("账号下线提醒")
                .setContentText(Html.fromHtml("您的账号已经在异地登录，是否重新登录？"))
                .setConfirmText("确定")
                .setCancelText("取消")
                .showCancelButton(true)
                .setClickListener(new SimpleDia.OnClickButtonListener() {
                    @Override
                    public void onClick(SimpleDia dialog, int flag) {
                        switch (flag) {
                            case 0: // 取消
                                LogUtil.d("-do Logout");
                                AppManager.getAppManager().AppExit(TopAct.this);
                                break;
                            case 1: // 确定
                                LogUtil.d("-do Turn");
                                TopAct.this.startActivity(new Intent(TopAct.this, LoginAct.class));
                                AppManager.getAppManager().finishAllActivity();
                                break;
                            default:
                                break;
                        }
                    }
                }).show();
    }

    @Override
    protected void onDestroy() {
        if(mSimpleDia != null) mSimpleDia.dismissWithAnimation();
        super.onDestroy();
    }
}
