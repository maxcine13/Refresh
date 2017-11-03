package com.jierong.share.mvp.view.ada;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jierong.share.R;
import com.jierong.share.mvp.model.info.MessageInfo;
import java.util.List;

/**
 * 我的消息列表适配器
 */
public class MessageAdapter extends BaseQuickAdapter<MessageInfo, BaseViewHolder> {

    public MessageAdapter(List<MessageInfo> data) {
        super(R.layout.item_message, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageInfo item) {
        helper.setText(R.id.time, item.getTime())
                .setText(R.id.desc, item.getDesc());
    }

}
