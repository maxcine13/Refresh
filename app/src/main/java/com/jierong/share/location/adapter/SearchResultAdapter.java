package com.jierong.share.location.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jierong.share.R;
import com.jierong.share.location.bean.City;


public class SearchResultAdapter extends BaseAdapter {
	
	private List<City> mSearchList;
	private Context mContext;
	private LayoutInflater mInflater;
	private onCityOnclickListener monCityOnclickListener;

	public SearchResultAdapter(Context context,List<City> searchList){
		this.mSearchList=searchList;
		this.mContext=context;
		mInflater=LayoutInflater.from(mContext);
	}
	public void setOnCityclickListener(onCityOnclickListener monCityOnclickListener) {
		this.monCityOnclickListener = monCityOnclickListener;
	}
	@Override
	public int getCount() {
		return mSearchList.size();
	}

	@Override
	public Object getItem(int position) {
		return mSearchList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder=null;
		if(convertView==null){
			viewHolder=new ViewHolder();
			convertView=mInflater.inflate(R.layout.item_search_list,null);
			viewHolder.tvCityName=(TextView) convertView.findViewById(R.id.tv_city_name);
			convertView.setTag(viewHolder);
		}else{
			viewHolder =(ViewHolder) convertView.getTag();
		}
		
		viewHolder.tvCityName.setText(mSearchList.get(position).getName());
		viewHolder.tvCityName.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (monCityOnclickListener!=null) {
					monCityOnclickListener.onCityClick(position,mSearchList.get(position).getName());
				}
			}
		});
		
		return convertView;
	}
	
	class ViewHolder{
		LinearLayout ll_item;
		TextView tvCityName;
	}
	/**
	 * listview 的点击接口
	 */
	public interface onCityOnclickListener {
		public void onCityClick(int position, String cityList);


	}
}
