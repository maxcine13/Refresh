package com.jierong.share.mvp.view.ada;

import android.graphics.Color;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jierong.share.R;
import com.jierong.share.mvp.model.info.WeekClickInfo;
import java.util.List;

/**
 * 周点击榜适配器
 */
public class WeekClickAdapter extends BaseQuickAdapter<WeekClickInfo, BaseViewHolder> {

    public WeekClickAdapter(List<WeekClickInfo> data) {
        super(R.layout.item_phb_week_click, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WeekClickInfo item) {
        helper.setText(R.id.name, item.getName())
                .setText(R.id.click_num, item.getClick() + "次");

        if (item.getId().equals("1")) {
            helper.setVisible(R.id.index_img, true);
            helper.setVisible(R.id.index_tv, false);
            helper.setVisible(R.id.reward, true);
            helper.setImageResource(R.id.index_img, R.drawable.phb_one);
            helper.setText(R.id.reward_num, item.getReward());
            helper.setTextColor(R.id.click_num, Color.parseColor("#f5b417"));
            helper.setTextColor(R.id.click_num_tip, Color.parseColor("#f5b417"));
        } else if (item.getId().equals("2")) {
            helper.setVisible(R.id.index_img, true);
            helper.setVisible(R.id.index_tv, false);
            helper.setVisible(R.id.reward, false);
            helper.setImageResource(R.id.index_img, R.drawable.phb_two);
            helper.setTextColor(R.id.click_num, Color.parseColor("#a4b8c4"));
            helper.setTextColor(R.id.click_num_tip, Color.parseColor("#a4b8c4"));
        } else if (item.getId().equals("3")) {
            helper.setVisible(R.id.index_img, true);
            helper.setVisible(R.id.index_tv, false);
            helper.setVisible(R.id.reward, false);
            helper.setImageResource(R.id.index_img, R.drawable.phb_three);
            helper.setTextColor(R.id.click_num, Color.parseColor("#785736"));
            helper.setTextColor(R.id.click_num_tip, Color.parseColor("#785736"));
        } else {
            helper.setVisible(R.id.index_img, false);
            helper.setVisible(R.id.index_tv, true);
            helper.setVisible(R.id.reward, false);
            helper.setText(R.id.index_tv, item.getId());
            helper.setTextColor(R.id.click_num, Color.parseColor("#666666"));
            helper.setTextColor(R.id.click_num_tip, Color.parseColor("#666666"));
        }
    }

}
