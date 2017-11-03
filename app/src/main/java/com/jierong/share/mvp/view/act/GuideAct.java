package com.jierong.share.mvp.view.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jierong.share.R;
import com.jierong.share.widget.BottomLineLayout;
import java.util.ArrayList;


/**
 * 首次启动引导页
 */
public class GuideAct extends AppCompatActivity {
    private ViewPager guide_pager;
    private ArrayList<View> pageViews;
    private View pageOne, pageTwo, pageFour;  // pageThree
    private BottomLineLayout bottomLayout;
    private int number = 3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_guide);

        init();
    }

    private void init() {
        LayoutInflater inflater = getLayoutInflater();
        pageOne = inflater.inflate(R.layout.guide_page_one, null);
        pageTwo = inflater.inflate(R.layout.guide_page_two, null);
        //pageThree = inflater.inflate(R.layout.guide_page_three, null);
        pageFour = inflater.inflate(R.layout.guide_page_four,null);

        pageViews = new ArrayList<View>();
        pageViews.add(pageOne);
        pageViews.add(pageTwo);
        //pageViews.add(pageThree);
        pageViews.add(pageFour);
        guide_pager = (ViewPager) findViewById(R.id.guide_pager);
        guide_pager.setAdapter(new GuidePageAdapter(pageViews));
        bottomLayout = (BottomLineLayout) findViewById(R.id.bottomLayout);
        bottomLayout.initViews(number, 15, 8);

        pageFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到登录界面
                Intent intent = new Intent(GuideAct.this, LoginAct.class);
                GuideAct.this.startActivity(intent);
                GuideAct.this.finish();
            }
        });
        guide_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (bottomLayout != null) bottomLayout.changePosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    // 第一次刷引导页的时候，屏蔽返回键
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {}
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    class GuidePageAdapter extends PagerAdapter {
        private ArrayList<View> mListViews;

        public GuidePageAdapter(ArrayList<View> mListViews) {
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
