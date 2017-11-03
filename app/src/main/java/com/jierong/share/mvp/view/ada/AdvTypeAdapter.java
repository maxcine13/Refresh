package com.jierong.share.mvp.view.ada;

import android.content.Context;
import android.content.Intent;
import com.jierong.share.Constants;
import com.jierong.share.R;
import com.jierong.share.mvp.model.info.AdvTypeInfo;
import com.jierong.share.mvp.view.act.AdvListActivity;
import com.jierong.share.mvp.view.act.LocationAct;
import com.jierong.share.util.AppPreferences;
import com.jierong.share.util.LogUtil;
import com.jierong.share.util.ToastUtils;
import java.util.List;

/**
 * http://www.jianshu.com/p/82a74c9ccba5
 * 广告分类列表适配器
 */
public class AdvTypeAdapter extends SolidRVBaseAdapter<AdvTypeInfo> {

    public AdvTypeAdapter(Context context, List<AdvTypeInfo> beans) {
        super(context, beans);
    }

    @Override
    public int getItemLayoutID(int viewType) {
        return R.layout.recyclerview_item_advtype;
    }

    @Override
    public void onBindViewHolder(SolidRVBaseAdapter.SolidCommonViewHolder holder, int position) {
        holder.setText(R.id.type_title, mBeans.get(position).getTitle());
        holder.setText(R.id.type_tip, mBeans.get(position).getTip());
        holder.setText(R.id.type_tip_more, mBeans.get(position).getTipMore());
        holder.setImageFromInternet(R.id.type_ic, mBeans.get(position).getImg());
    }

    @Override
    protected void onItemClick(int position) {
        AdvTypeInfo info = mBeans.get(position);
        LogUtil.d(info.toString());
        if(info.getId().equals("3")) {
            Intent intent = new Intent(mContext, AdvListActivity.class);
            intent.putExtra("AdvTypeId", mBeans.get(position).getId());
            intent.putExtra("AdvTypeName", mBeans.get(position).getTitle());
            mContext.startActivity(intent);
        } else if(info.getId().equals("5")) {
            Intent intent = new Intent(mContext, AdvListActivity.class);
            intent.putExtra("AdvTypeId", mBeans.get(position).getId());
            intent.putExtra("AdvTypeName", mBeans.get(position).getTitle());
            mContext.startActivity(intent);
        } else if(info.getId().equals("2")) {
            String city = AppPreferences.getString(mContext, Constants.PNK_Location, "");
            if (city.equals("") || city.equals("None")) {
                Intent intent = new Intent(mContext, LocationAct.class);
                mContext.startActivity(intent);
                ToastUtils.show(mContext, "请先选择当前城市!");
            } else {
                Intent intent = new Intent(mContext, AdvListActivity.class);
                intent.putExtra("AdvTypeId", mBeans.get(position).getId());
                intent.putExtra("AdvTypeName", mBeans.get(position).getTitle());
                mContext.startActivity(intent);
            }
        }
    }

}
