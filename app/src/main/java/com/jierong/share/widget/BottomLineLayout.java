package com.jierong.share.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jierong.share.R;

/**
 * @Author: duke
 * @DateTime: 2017-04-22 21:59
 * @Description:
 */
public class BottomLineLayout extends LinearLayout {
    private int itemDefaultBgResId = R.drawable.radius_unselected;//单个元素默认背景样式
    private int itemSelectedBgResId = R.drawable.radius_selected;//单个元素选中背景样式
    private int currentPosition;//当前选中位置
    private int itemHeight = 10;//item宽高
    private int itemMargin = 20;//item间距

    public BottomLineLayout(Context context) {
        this(context, null, 0);
    }

    public BottomLineLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomLineLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
    }

    public void initViews(int count) {
        initViews(count, this.itemHeight, this.itemMargin);
    }

    public void initViews(int count, int itemHeight) {
        initViews(count, itemHeight, this.itemMargin);
    }

    public void initViews(int count, int itemHeight, int itemMargin) {
        this.itemHeight = itemHeight;
        this.itemMargin = itemMargin;
        removeAllViews();
        if(count == 0 || itemHeight == 0){
            return;
        }
        View view = createView(itemHeight,itemMargin);
        view.setBackgroundResource(itemSelectedBgResId);
        addView(view);
        if(count == 1){
            return;
        }
        for (int i = 1; i < count; i++) {
            view = createView(itemHeight,itemMargin);
            view.setBackgroundResource(itemDefaultBgResId);
            addView(view);
        }
    }

    /**
     * 创建view
     * @param sideLength 边长
     * @param itemMargin 外间距
     * @return
     */
    public View createView(int sideLength,int itemMargin){
        TextView textview = new TextView(getContext());
        LayoutParams params = new LayoutParams(sideLength, sideLength);
        if(itemMargin > 0){
            params.setMargins(itemMargin,0,itemMargin,0);
        }
        textview.setLayoutParams(params);
        return textview;
    }

    //切换到目标位置
    public void changePosition(int position) {
        if(getChildCount() <= 1){
            return;
        }
        getChildAt(currentPosition).setBackgroundResource(itemDefaultBgResId);
        currentPosition = position % getChildCount();
        getChildAt(currentPosition).setBackgroundResource(itemSelectedBgResId);
    }
}
