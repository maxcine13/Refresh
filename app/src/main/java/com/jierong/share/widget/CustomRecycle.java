package com.jierong.share.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by wht on 2017/5/10.
 */

public class CustomRecycle extends RecyclerView{
    public CustomRecycle(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    public CustomRecycle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    public CustomRecycle(Context context) {
        super(context);
    }


//    @Override
//    public boolean onTouchEvent(MotionEvent e) {
//        super.onTouchEvent(e);
//        if (MotionEvent.ACTION_DOWN==e.ACTION_DOWN) {
//            LogUtil.d("MotionEvent.ACTION_DOWN");
//            return true;
//        }else{
//            return false;
//        }
//
//    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
