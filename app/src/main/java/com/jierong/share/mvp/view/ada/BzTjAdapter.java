package com.jierong.share.mvp.view.ada;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jierong.share.R;
import com.jierong.share.mvp.model.info.ShopInfo;
import java.util.List;

/**
 * 白赚-推荐记录适配器
 */
public class BzTjAdapter extends BaseQuickAdapter<ShopInfo, BaseViewHolder> {

    public BzTjAdapter(List<ShopInfo> data) {
        super(R.layout.item_bz_content_tj, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopInfo item) {
        helper.setText(R.id.bz_tj_time, item.getTime())
                .setText(R.id.bz_tj_tel, item.getTel())
                .setText(R.id.bz_tj_price, "+ " + item.getPrice());
    }

}
