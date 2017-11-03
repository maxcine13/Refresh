package com.jierong.share.mvp.view.ada;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jierong.share.R;
import com.jierong.share.mvp.model.info.ShopInfo;
import java.util.List;

/**
 * 商城购物返利宝记录适配器
 */
public class ShopFanAdapter extends BaseQuickAdapter<ShopInfo, BaseViewHolder> {

    public ShopFanAdapter(List<ShopInfo> data) {
        super(R.layout.item_shop_content_fan, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopInfo item) {
        helper.setText(R.id.shop_goods_name, item.getName())
                .setText(R.id.shop_goods_time, item.getTime())
                .setText(R.id.shop_goods_price, "-" + item.getPrice());

        if (item.getFlag() == 1) {
            helper.setText(R.id.shop_goods_fan, "已返利：+" + item.getFanli());
        } else {
            helper.setText(R.id.shop_goods_fan, "预计返利：+" + item.getFanli());
        }
    }

}
