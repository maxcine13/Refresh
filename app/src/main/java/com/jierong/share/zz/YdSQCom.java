package com.jierong.share.zz;

import android.view.LayoutInflater;
import android.view.View;
import com.jierong.share.R;

/**
 * 申请返利引导图
 */
public class YdSQCom implements Component {

    @Override
    public View getView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.view_yd_shenqing, null);
        return view;
    }

    @Override
    public int getAnchor() {
        return Component.ANCHOR_TOP;
    }

    @Override
    public int getFitPosition() {
        return Component.FIT_START;
    }

    @Override
    public int getXOffset() {
        return 5;
    }

    @Override
    public int getYOffset() {
        return -5;
    }
}
