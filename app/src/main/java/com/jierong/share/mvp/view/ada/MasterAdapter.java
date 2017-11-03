package com.jierong.share.mvp.view.ada;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jierong.share.R;
import com.jierong.share.mvp.model.info.MasterInfo;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * 分享达人列表适配器
 */

public class MasterAdapter extends BGARecyclerViewAdapter<MasterInfo> {
    private Context mContext;

    public MasterAdapter(RecyclerView recyclerView, Context context) {
        super(recyclerView, R.layout.item_master_adt);
        this.mContext = context;

    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, MasterInfo model) {

        if (position == 0) {
            helper.setImageResource(R.id.img_right, R.drawable.ic_master_one);

        } else if (position == 1) {
            helper.setImageResource(R.id.img_right, R.drawable.ic_master_two);

        } else if (position == 2) {
            helper.setImageResource(R.id.img_right, R.drawable.ic_master_three);
        } else {
            helper.setImageResource(R.id.img_right, R.drawable.ic_master_gray);
        }
        if (model.getULevel().equals("未认证")) {
            helper.setText(R.id.txt_grade, model.getULevel());
            helper.setImageResource(R.id.Img_grade, R.drawable.ic_level_normal);
        } else {
            helper.setText(R.id.txt_grade, model.getULevel());
            helper.setImageResource(R.id.Img_grade, R.drawable.ic_level_click);
        }
        Glide.with(mContext).load(model.getUIc())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .error(R.drawable.icon_header)
                .placeholder(R.drawable.icon_header)
                .bitmapTransform(new CropCircleTransformation(mContext))
                .into((ImageView) helper.getView(R.id.img_head));
        helper.setText(R.id.value_grade, position + 1 + "");//排名
        helper.setText(R.id.txt_name, model.getName());//姓名
        helper.setText(R.id.value_score, model.getUScore() + "分");//评分
        helper.setText(R.id.value_deal, model.getUVolume() + "单");//分享成交量
        helper.setText(R.id.value_number, model.getShare_number() + "次");//分享次数
        helper.setText(R.id.value_click, model.getUClicks() + "次");//分享点击量
        helper.setText(R.id.value_deal_profit, model.getUIncome() + "积分");//分享成交收益
        helper.setText(R.id.value_total_income, model.getUTotalIncome() + "积分");//达人总收益
        helper.setText(R.id.value_profit, model.getSharefee() + "积分");//分析收益

    }

    @Override
    protected void setItemChildListener(BGAViewHolderHelper helper, int viewType) {
        super.setItemChildListener(helper, viewType);
        helper.setItemChildClickListener(R.id.rela_all);
    }


}
