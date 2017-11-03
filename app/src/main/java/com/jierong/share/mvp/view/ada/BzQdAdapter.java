package com.jierong.share.mvp.view.ada;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jierong.share.R;
import com.jierong.share.mvp.model.info.ShopInfo;
import java.util.List;

/**
 * 白赚-签到记录适配器
 */
public class BzQdAdapter extends BaseQuickAdapter<ShopInfo, BaseViewHolder> {

    public BzQdAdapter(List<ShopInfo> data) {
        super(R.layout.item_bz_content_qd, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopInfo item) {
        String time = item.getTime();
        helper.setText(R.id.bz_qd_time1, time.substring(0, 11))
                .setText(R.id.bz_qd_time2, time.substring(11))
                .setText(R.id.bz_qd_price, "+ " + item.getPrice());
    }

}
