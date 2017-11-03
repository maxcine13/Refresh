package com.jierong.share.mvp.view.frag;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jierong.share.BaseFrag;
import com.jierong.share.Constants;
import com.jierong.share.R;
import com.jierong.share.mvp.model.info.TabEntity;
import com.jierong.share.mvp.view.act.WebAct;
import com.jierong.share.util.AppPreferences;

import java.util.ArrayList;

/**
 * 分享达人模块
 */
public class MasterFrag extends BaseFrag {
    private RelativeLayout view_nonet;
    private FrameLayout look_level_desc;
    private boolean isClick = false;    // 当前是否在点击状态
    private ViewPager master_top_pager, vp_pager;
    private ArrayList<View> topViews;
    private View topOne, topTwo;
    private TextView week_top_tip, month_top_tip;
    private CommonTabLayout tab_title;
    private String[] mTitles = {"周榜", "月榜"};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_master, null);

        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.Refresh_Phb_time);
        getActivity().registerReceiver(refreshReceiver, filter);

        initView(view);
        return view;
    }

    BroadcastReceiver refreshReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Constants.Refresh_Phb_time)) {
                String updateType = intent.getStringExtra("updateType");
                if(updateType.equals("weekData")) {
                    String weekTime = intent.getStringExtra("weekTime");
                    week_top_tip.setText("最近更新：" + weekTime);
                } else if(updateType.equals("monthData")) {
                    String monthTime = intent.getStringExtra("monthTime");
                    month_top_tip.setText("时间段：" + monthTime + "月");
                }
            }
        }
    };

    @Override
    public void onNetNo() {
        view_nonet.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNetOk() {
        view_nonet.setVisibility(View.GONE);
    }

    private void initView(View view) {
        ((TextView) view.findViewById(R.id.titleName)).setText(R.string.title_name_master);
        view_nonet = (RelativeLayout) view.findViewById(R.id.view_nonet);
        look_level_desc = (FrameLayout) view.findViewById(R.id.look_level_desc);
        tab_title = (CommonTabLayout) view.findViewById(R.id.tab_title);
        master_top_pager = (ViewPager) view.findViewById(R.id.master_top_pager);
        vp_pager = (ViewPager) view.findViewById(R.id.vp_pager);
        for (String mTitle : mTitles) {
            mTabEntities.add(new TabEntity(mTitle));
        }
        tab_title.setTabData(mTabEntities);
        mFragments.add(new PhbWeekFrag());
        mFragments.add(new PhbMonthFrag());
        vp_pager.setAdapter(new MasterFrag.MyPagerAdapter(getChildFragmentManager()));
        //vp_pager.setAdapter(new MasterFrag.MyPagerAdapter(getActivity().getSupportFragmentManager()));
        vp_pager.setOffscreenPageLimit(mFragments.size() - 1);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        topOne = inflater.inflate(R.layout.frag_phb_week_top, null);
        topTwo = inflater.inflate(R.layout.frag_phb_month_top, null);
        week_top_tip = (TextView) topOne.findViewById(R.id.week_top_tip);
        month_top_tip = (TextView) topTwo.findViewById(R.id.month_top_tip);
        topViews = new ArrayList<View>();
        topViews.add(topOne);
        topViews.add(topTwo);
        master_top_pager.setAdapter(new TopPageAdapter(topViews));

        master_top_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                tab_title.setCurrentTab(position);
                vp_pager.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });
        tab_title.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                vp_pager.setCurrentItem(position);
                master_top_pager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) { }
        });
        vp_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                tab_title.setCurrentTab(position);
                master_top_pager.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });
        look_level_desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WebAct.class);
                intent.putExtra(WebAct.Data_Title, "等级明细");
                intent.putExtra(WebAct.Data_Url, Constants.Http_Api_DJMX
                                + "?uid=" + AppPreferences.getString(getActivity(), Constants.PNK_UId)
                                + "&token=" + AppPreferences.getString(getActivity(), Constants.PNK_UToken));
                getActivity().startActivity(intent);
            }
        });
    }

    public void setClick(boolean click) {
        isClick = click;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (refreshReceiver != null)
            getActivity().unregisterReceiver(refreshReceiver);
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

    private class TopPageAdapter extends PagerAdapter {
        private ArrayList<View> mListViews;

        public TopPageAdapter(ArrayList<View> mListViews) {
            this.mListViews = mListViews;
        }

        // 获得当前界面数
        @Override
        public int getCount() {
            if (mListViews != null) {
                return mListViews.size();
            }
            else return 0;
        }

        // 判断是否由对象生成界面
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view == object);
        }

        // 当页面滑动出屏幕的时候,销毁position位置的界面
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView(mListViews.get(position));
        }

        // 初始化position位置的界面
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager) container).addView(mListViews.get(position), 0);
            return mListViews.get(position);
        }
    }

}
