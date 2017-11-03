package com.jierong.share.mvp.view.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jierong.share.BaseAct;
import com.jierong.share.R;
import com.jierong.share.mvp.model.info.TabEntity;
import com.jierong.share.mvp.view.frag.BzCjFrag;
import com.jierong.share.mvp.view.frag.BzFxFrag;
import com.jierong.share.mvp.view.frag.BzQdFrag;
import com.jierong.share.mvp.view.frag.BzTjFrag;
import java.util.ArrayList;

public class BzActivity extends BaseAct {
    private ViewPager vp_pager;
    private CommonTabLayout tab_title;
    private String[] mTitles = {"签到收益", "推荐收益", "分享收益", "成交收益"};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_baizhuan);

        init();
    }

    @Override
    public void onNetNo() { }

    @Override
    public void onNetOk() { }

    private void init() {
        ((TextView) findViewById(R.id.titleName)).setText(R.string.title_name_bz);
        tab_title = (CommonTabLayout) findViewById(R.id.tab_title);
        vp_pager = (ViewPager) findViewById(R.id.vp_pager);

        for (String mTitle : mTitles) {
            mTabEntities.add(new TabEntity(mTitle));
        }
        tab_title.setTabData(mTabEntities);
        mFragments.add(new BzQdFrag());
        mFragments.add(new BzTjFrag());
        mFragments.add(new BzFxFrag());
        mFragments.add(new BzCjFrag());
        vp_pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        vp_pager.setOffscreenPageLimit(mFragments.size() - 1);
        tab_title.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                vp_pager.setCurrentItem(position);
            }
            @Override
            public void onTabReselect(int position) { }
        });
        vp_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) { }
            @Override
            public void onPageSelected(int position) {
                tab_title.setCurrentTab(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) { }
        });

        findViewById(R.id.titleBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }

}
