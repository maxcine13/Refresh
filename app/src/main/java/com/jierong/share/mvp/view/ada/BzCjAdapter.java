package com.jierong.share.mvp.view.ada;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jierong.share.R;
import com.jierong.share.mvp.model.info.ShopInfo;
import java.util.List;

/**
 * 白赚-成交记录适配器
 */
public class BzCjAdapter extends BaseQuickAdapter<ShopInfo, BaseViewHolder> {

    public BzCjAdapter(List<ShopInfo> data) {
        super(R.layout.item_bz_content_cj, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopInfo item) {
        helper.setText(R.id.bz_cj_time, item.getTime())
                .setText(R.id.bz_cj_name, item.getStore())
                .setText(R.id.bz_cj_price, "+ " + item.getPrice());
    }

}
