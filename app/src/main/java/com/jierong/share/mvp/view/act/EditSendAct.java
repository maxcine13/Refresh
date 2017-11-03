package com.jierong.share.mvp.view.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.jierong.share.BaseAct;
import com.jierong.share.R;

/**
 * 编辑填写界面
 */
public class EditSendAct extends BaseAct {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_send_item);

        init();
    }

    @Override
    public void onNetNo() { }

    @Override
    public void onNetOk() {}

    private void init() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
