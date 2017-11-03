package com.jierong.share.location.adapter;

import java.util.List;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.jierong.share.R;
import com.jierong.share.location.bean.City;

public class HotCityAdapter extends BaseAdapter {

    private List<City> mHotCityList;
    private LayoutInflater mInflater;
    private Context mContext;
    private onHotCityOnclickListener onCityOnclickListener;

    public void setOnHotCityclickListener(onHotCityOnclickListener onCityOnclickListener) {
        this.onCityOnclickListener = onCityOnclickListener;
    }

    public HotCityAdapter(Context context, List<City> hotCityList) {
        this.mHotCityList = hotCityList;
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mHotCityList.size();
    }

    @Override
    public Object getItem(int position) {
        return mHotCityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_city, null);
            viewHolder.tvCityName = (TextView) convertView
                    .findViewById(R.id.tv_city_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvCityName.setText(mHotCityList.get(position).getName());
        viewHolder.tvCityName.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (onCityOnclickListener != null) {
                    onCityOnclickListener.onHotCityClick(position, mHotCityList.get(position).getName());
                }
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView tvCityName;
    }

    /**
     * listview 的点击接口
     */
    public interface onHotCityOnclickListener {
        public void onHotCityClick(int position, String recentcity);

    }
}
