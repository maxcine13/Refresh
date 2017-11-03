package com.jierong.share.mvp.view.ada;

import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jierong.share.R;
import com.jierong.share.mvp.model.info.TaoBaoInfo;
import com.jierong.share.util.StringUtil;
import java.util.List;

/**
 * 爆款返利数据适配器
 */
public class BkflAdapter extends BaseQuickAdapter<TaoBaoInfo, BaseViewHolder> {

    public BkflAdapter(List<TaoBaoInfo> data) {
        super(R.layout.item_bkfl_content, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TaoBaoInfo item) {
        helper.setText(R.id.goods_title, item.getTitle())
                .setText(R.id.goods_price, "超低价：￥" + item.getZk_final_price())
                .setText(R.id.goods_fan, "预计返佣：" + item.getTk_rate())
                .setText(R.id.goods_day, "活动剩余：" + item.getDay() + "天")
                .setText(R.id.goods_sellNum, "月销量：" + item.getVolume())
                .addOnClickListener(R.id.goods_yhq);

        if(StringUtil.isEmptyIgnoreBlank(item.getCoupon_click_url())) {
            helper.setGone(R.id.goods_yhq, false);
            helper.setText(R.id.goods_action, null)
                    .setText(R.id.goods_have, null);
        } else {
            helper.setGone(R.id.goods_yhq, true);
            helper.setText(R.id.goods_action, item.getCoupon_info())
                    .setText(R.id.goods_have, "剩余：" + item.getCoupon_remain_count());
        }

        Glide.with(mContext)
                .load(item.getPict_url())
                //.placeholder(Color.parseColor("#3577ef"))
                //.bitmapTransform(new CropCircleTransformation(mContext))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into((ImageView) helper.getView(R.id.goods_img));
    }

}
