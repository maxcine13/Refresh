package com.jierong.share.mvp.view.ada;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jierong.share.R;
import com.jierong.share.mvp.model.info.CollectionInfo;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * 我的收藏页面的适配器
 */

public class CollectionAdapter extends BGARecyclerViewAdapter<CollectionInfo> {
    private Context context;

    public CollectionAdapter(RecyclerView recyclerView, Context context) {
        super(recyclerView, R.layout.item_collection_adt);
        this.context = context;
    }

    @Override
    protected void setItemChildListener(BGAViewHolderHelper helper, int viewType) {
        helper.setItemChildClickListener(R.id.collection);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, CollectionInfo model) {
        helper.setText(R.id.txt_name, model.getName());
        helper.setText(R.id.txt_authen, model.getProof());
        helper.setText(R.id.value_score, model.getScore() + "");
        helper.setText(R.id.txt_time, model.getCreate_time());
        Glide.with(mContext).load(model.getAvatar())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .error(R.drawable.icon_header)
                .placeholder(R.drawable.icon_header)
                .bitmapTransform(new CropCircleTransformation(context))
                .into((ImageView) helper.getView(R.id.img_head));
        if (model.getProof().equals("未认证")) {
            helper.setImageResource(R.id.Img_grade, R.drawable.ic_level_normal);
        } else {
            helper.setImageResource(R.id.Img_grade, R.drawable.ic_level_click);
        }


    }


}
