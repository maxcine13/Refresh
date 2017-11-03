package com.jierong.share.mvp.view.ada;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jierong.share.R;
import com.jierong.share.mvp.model.info.CommentInfo;
import com.jierong.share.mvp.model.info.MasterInfo;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * Created by Administrator on 2017/4/11.
 */

public class CommentAdapter extends BGARecyclerViewAdapter<CommentInfo> {
    private Context mContext;

    public CommentAdapter(RecyclerView recyclerView, Context context) {
        super(recyclerView, R.layout.item_comment_adt);
        this.mContext = context;

    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, CommentInfo model) {
        helper.setText(R.id.vaalue_name,model.getName());
        helper.setText(R.id.value_time,model.getCreate_time());
        helper.setText(R.id.value_context,model.getComment());
        Glide.with(mContext).load(model.getAvatar())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .error(R.drawable.icon_header)
                .into((ImageView) helper.getView(R.id.img_head));


    }


}
