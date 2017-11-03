package com.jierong.share.mvp.view.ada;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jierong.share.R;
import com.jierong.share.mvp.model.info.ShopInfo;
import java.util.List;

/**
 * 商城购物记录适配器
 */
public class ShopBuyAdapter extends BaseQuickAdapter<ShopInfo, BaseViewHolder> {

    public ShopBuyAdapter(List<ShopInfo> data) {
        super(R.layout.item_shop_content_buy, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopInfo item) {
        helper.setText(R.id.shop_goods_name, item.getName())
                .setText(R.id.shop_goods_time, item.getTime())
                .setText(R.id.shop_goods_price, "-" + item.getPrice());
    }

}
