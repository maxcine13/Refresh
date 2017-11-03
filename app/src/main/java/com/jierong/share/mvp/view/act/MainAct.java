package com.jierong.share.mvp.view.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.jierong.share.AppManager;
import com.jierong.share.BaseAct;
import com.jierong.share.R;
import com.jierong.share.mvp.view.frag.AdvFrag;
import com.jierong.share.mvp.view.frag.HomeFrag;
import com.jierong.share.mvp.view.frag.MasterFrag;
import com.jierong.share.mvp.view.frag.MeFrag;
import com.jierong.share.util.LogUtil;
import com.jierong.share.util.ToastUtils;

/**
 * 主界面
 */
public class MainAct extends BaseAct implements View.OnClickListener {
    private RelativeLayout bt_one, bt_two, bt_three, bt_four;
    private ImageView ic_one, ic_two, ic_three, ic_four;
    private TextView tv_one, tv_two, tv_three, tv_four;
    private FragmentManager mFragmentManager;
    private HomeFrag mHomeFrag;
    private AdvFrag mAdvFrag;
    private MasterFrag mMasterFrag;
    private MeFrag mMeFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);

        mFragmentManager = getSupportFragmentManager();
        if (null == savedInstanceState) {
            // 正常重新启动的时候，不做特殊处理
            LogUtil.d("Act_main_admin - savedInstanceState is null");
        } else {
            LogUtil.e("Act_main_admin - savedInstanceState is not null");
            mHomeFrag = (HomeFrag) mFragmentManager.findFragmentByTag("mHomeFrag");
            mAdvFrag = (AdvFrag) mFragmentManager.findFragmentByTag("mAdvFrag");
            mMasterFrag = (MasterFrag) mFragmentManager.findFragmentByTag("mMasterFrag");
            mMeFrag = (MeFrag) mFragmentManager.findFragmentByTag("mMeFrag");
            hideFragments(mFragmentManager.beginTransaction());
            mFragmentManager.beginTransaction().commit();
            LogUtil.e("Act_main_admin - fragment总数 " + mFragmentManager.getFragments().size());
        }
        init();
    }

    @Override
    public void onNetNo() {
        if (null != mHomeFrag) mHomeFrag.onNetNo();
        if (null != mAdvFrag) mAdvFrag.onNetNo();
        if (null != mMasterFrag) mMasterFrag.onNetNo();
        if (null != mMeFrag) mMeFrag.onNetNo();
    }

    @Override
    public void onNetOk() {
        if (null != mHomeFrag) mHomeFrag.onNetOk();
        if (null != mAdvFrag) mAdvFrag.onNetOk();
        if (null != mMasterFrag) mMasterFrag.onNetOk();
        if (null != mMeFrag) mMeFrag.onNetOk();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (null != mHomeFrag) {
            transaction.hide(mHomeFrag);
            mHomeFrag.setClick(false);
        }
        if (null != mAdvFrag) {
            transaction.hide(mAdvFrag);
            mAdvFrag.setClick(false);
        }
        if (null != mMasterFrag) {
            transaction.hide(mMasterFrag);
            mMasterFrag.setClick(false);
        }
        if (null != mMeFrag) {
            transaction.hide(mMeFrag);
            mMeFrag.setClick(false);
        }
    }

    /**
     * 重置颜色
     */
    private void clearColor() {
        ic_one.setImageResource(R.drawable.ic_one_normal);
        ic_two.setImageResource(R.drawable.ic_two_normal);
        ic_three.setImageResource(R.drawable.ic_three_normal);
        ic_four.setImageResource(R.drawable.ic_four_normal);
        tv_one.setTextColor(getResources().getColor(R.color.bottom_text_normal));
        tv_two.setTextColor(getResources().getColor(R.color.bottom_text_normal));
        tv_three.setTextColor(getResources().getColor(R.color.bottom_text_normal));
        tv_four.setTextColor(getResources().getColor(R.color.bottom_text_normal));
    }

    /**
     * 设置默认选中
     */
    private void setDefault() {
        ic_one.setImageResource(R.drawable.ic_one_click);
        tv_one.setTextColor(getResources().getColor(R.color.bottom_text_click));
        FragmentTransaction fragmentTran = mFragmentManager.beginTransaction();
        hideFragments(fragmentTran);
        if (mHomeFrag == null) {
            mHomeFrag = new HomeFrag();
            fragmentTran.add(R.id.content, mHomeFrag, "mHomeFrag");
        }
        fragmentTran.show(mHomeFrag);
        mHomeFrag.setClick(true);
        fragmentTran.commit();
    }

    private void init() {
        bt_one = (RelativeLayout) findViewById(R.id.bt_one);
        bt_two = (RelativeLayout) findViewById(R.id.bt_two);
        bt_three = (RelativeLayout) findViewById(R.id.bt_three);
        bt_four = (RelativeLayout) findViewById(R.id.bt_four);
        ic_one = (ImageView) findViewById(R.id.ic_one);
        ic_two = (ImageView) findViewById(R.id.ic_two);
        ic_three = (ImageView) findViewById(R.id.ic_three);
        ic_four = (ImageView) findViewById(R.id.ic_four);
        tv_one = (TextView) findViewById(R.id.tv_one);
        tv_two = (TextView) findViewById(R.id.tv_two);
        tv_three = (TextView) findViewById(R.id.tv_three);
        tv_four = (TextView) findViewById(R.id.tv_four);

        bt_one.setOnClickListener(this);
        bt_two.setOnClickListener(this);
        bt_three.setOnClickListener(this);
        bt_four.setOnClickListener(this);
        setDefault();
    }

    // 供给首次引导提醒使用
    public RelativeLayout getTwoView() {
        return bt_two;
    }

    @Override
    public void onClick(View view) {
        clearColor();
        FragmentTransaction fragmentTran = mFragmentManager.beginTransaction();
        hideFragments(fragmentTran);
        switch (view.getId()) {
            case R.id.bt_one:
                ic_one.setImageResource(R.drawable.ic_one_click);
                tv_one.setTextColor(getResources().getColor(R.color.bottom_text_click));
                if (mHomeFrag == null) {
                    mHomeFrag = new HomeFrag();
                    fragmentTran.add(R.id.content, mHomeFrag, "mHomeFrag");
                }
                fragmentTran.show(mHomeFrag);
                mHomeFrag.setClick(true);
                break;

            case R.id.bt_two:
                ic_two.setImageResource(R.drawable.ic_two_click);
                tv_two.setTextColor(getResources().getColor(R.color.bottom_text_click));
                if (mAdvFrag == null) {
                    mAdvFrag = new AdvFrag();
                    fragmentTran.add(R.id.content, mAdvFrag, "mAdvFrag");
                }
                fragmentTran.show(mAdvFrag);
                mAdvFrag.setClick(true);
                break;
            case R.id.bt_three:
                ic_three.setImageResource(R.drawable.ic_three_click);
                tv_three.setTextColor(getResources().getColor(R.color.bottom_text_click));
                if (mMasterFrag == null) {
                    mMasterFrag = new MasterFrag();
                    fragmentTran.add(R.id.content, mMasterFrag, "mMasterFrag");
                }
                fragmentTran.show(mMasterFrag);
                mMasterFrag.setClick(true);
                break;
            case R.id.bt_four:
                ic_four.setImageResource(R.drawable.ic_four_click);
                tv_four.setTextColor(getResources().getColor(R.color.bottom_text_click));
                if (mMeFrag == null) {
                    mMeFrag = new MeFrag();
                    fragmentTran.add(R.id.content, mMeFrag, "mMeFrag");
                }
                fragmentTran.show(mMeFrag);
                mMeFrag.setClick(true);
                break;
            default:
                break;
        }
        fragmentTran.commit();
    }

    private long nowTime, oldTime;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            nowTime = System.currentTimeMillis();
            if (nowTime - oldTime < 2000) {
                AppManager.getAppManager().finishAllActivity();
            } else {
                ToastUtils.show(this, "再按一次退出程序");
                oldTime = nowTime;
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mMeFrag.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
