package com.jierong.share.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

import com.jierong.share.util.LogUtil;


public class ScrollWebView extends WebView {
    private boolean isFirst = true;
    //手指按下的点为(y1)手指离开屏幕的点为(y2)
    float y1 = 0;
    float y2 = 0;
    //记录滑动到最底部的时候的getScrollY的最大值
    float sy1 = 0;
    //记录抬起手指时getScrollY的值
    float sy2 = 0;
    public OnScrollChangeListener listener;

    public ScrollWebView(Context context) {
        super(context);
    }

    public ScrollWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (isFirst) {//当用户一下子滑动到底部时,显示分享的弹窗
            float webcontent = getContentHeight() * getScale();// webview的高度
            float webnow = getHeight() + getScrollY();// 当前webview的高度
            if (Math.abs(webcontent - webnow) < 1) {
                sy1 = getScrollY();
                listener.onChangedEnd();
                isFirst = false;
            } else if (getScrollY() == 0) {
                listener.onPageTop(l, t, oldl, oldt);
            } else {
                listener.onScrollChanged(l, t, oldl, oldt);

            }
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

            //继承了Activity的onTouchEvent方法，直接监听点击事件
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                //当手指按下的时候
                y1 = event.getY();
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                //当手指离开的时候
                y2 = event.getY();
                sy2 = getScrollY();//当手指离开屏幕时,webview的滑动距离
                if (y1 - y2 > 300 & sy1 == sy2) {//sy1==sy2代表滑动到底部
                    listener.onChangedEnd();

                }
            } else {
                if (event.getAction() == MotionEvent.ACTION_MOVE) {

                }
            }



        return super.onTouchEvent(event);
    }

    public void setOnScrollChangeListener(OnScrollChangeListener listener) {
        this.listener = listener;
    }

    public interface OnScrollChangeListener {
        public void onPageEnd(int l, int t, int oldl, int oldt);

        public void onPageTop(int l, int t, int oldl, int oldt);

        public void onScrollChanged(int l, int t, int oldl, int oldt);

        public void onChangedEnd();

    }

}
