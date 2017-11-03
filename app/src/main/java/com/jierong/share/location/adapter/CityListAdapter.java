package com.jierong.share.location.adapter;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.jierong.share.R;
import com.jierong.share.location.bean.City;
import com.jierong.share.location.view.MyGridView;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;


public class CityListAdapter extends BaseAdapter implements AMapLocationListener {

    private Context mContext;
    private List<City> mAllCityList;
    private List<City> mHotCityList;
    private List<String> mRecentCityList;
    public HashMap<String, Integer> alphaIndexer;// 存放存在的汉语拼音首字母和与之对应的列表位置
    private String[] sections;// 存放存在的汉语拼音首字母
    // 定位模块属性
    private AMapLocationClient mLocationClient = null;
    private AMapLocationClientOption mLocationOption = null;
    private String currentCity="";//当前城市
    private boolean isNeedRefresh;//当前定位的城市是否需要刷新
    private final int VIEW_TYPE = 5;//view的类型个数
    private onCityOnclickListener onCityOnclickListener;

    public void setOnCityclickListener(onCityOnclickListener onCityOnclickListener) {
        this.onCityOnclickListener = onCityOnclickListener;
    }

    public CityListAdapter(Context context, List<City> allCityList,
                           List<City> hotCityList, List<String> recentCityList) {
        this.mContext = context;
        this.mAllCityList = allCityList;
        this.mHotCityList = hotCityList;
        this.mRecentCityList = recentCityList;

        alphaIndexer = new HashMap<String, Integer>();
        sections = new String[allCityList.size()];

        //这里的主要目的是将listview中要显示字母的条目保存下来，方便在滑动时获得位置，alphaIndexer在Acitivity有调用
        for (int i = 0; i < mAllCityList.size(); i++) {
            // 当前汉语拼音首字母
            String currentStr = getAlpha(mAllCityList.get(i).getPinyin());
            // 上一个汉语拼音首字母，如果不存在为" "
            String previewStr = (i - 1) >= 0 ? getAlpha(mAllCityList.get(i - 1).getPinyin()) : " ";
            if (!previewStr.equals(currentStr)) {
                String name = getAlpha(mAllCityList.get(i).getPinyin());
                alphaIndexer.put(name, i);
                sections[i] = name;
            }
        }
        isNeedRefresh = true;
        initLocation();

    }

    @Override
    public int getViewTypeCount() {

        return VIEW_TYPE;
    }

    @Override
    public int getItemViewType(int position) {
        return position < 4 ? position : 4;
    }

    @Override
    public int getCount() {
        return mAllCityList.size();
    }

    @Override
    public Object getItem(int position) {
        return mAllCityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    ViewHolderOne viewHolderOne = null;
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        int viewType = getItemViewType(position);
        if (viewType == 0) {//view类型为0，也就是：当前定位城市的布局
            if (convertView == null) {
                viewHolderOne = new ViewHolderOne();
                convertView = View.inflate(mContext, R.layout.item_location_city,
                        null);
                viewHolderOne.tvLocate = (TextView) convertView.findViewById(R.id.tv_locate);
                viewHolderOne.tvCurrentLocateCity = (TextView) convertView.findViewById(R.id.tv_current_locate_city);
                viewHolderOne.pbLocate = (ProgressBar) convertView.findViewById(R.id.pb_loacte);
                convertView.setTag(viewHolderOne);
            } else {
                convertView.getTag();
            }

            if (!isNeedRefresh) {
                viewHolderOne. tvLocate.setText("当前定位城市");
                viewHolderOne.tvCurrentLocateCity.setVisibility(View.VISIBLE);
                viewHolderOne.tvCurrentLocateCity.setText(currentCity);
                viewHolderOne.pbLocate.setVisibility(View.GONE);
            } else {
                mLocationClient.startLocation();
            }

            viewHolderOne.tvCurrentLocateCity.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!viewHolderOne.tvCurrentLocateCity.equals("") && !viewHolderOne.pbLocate.isShown()
                            && !"重新选择".equals(viewHolderOne.tvCurrentLocateCity.getText().toString())) {
                        if (onCityOnclickListener != null) {
                            onCityOnclickListener.onCityClick(position, viewHolderOne.tvCurrentLocateCity.getText().toString());
                        }
                    } else {
                        viewHolderOne.pbLocate.setVisibility(View.VISIBLE);
                        viewHolderOne.tvLocate.setText("正在定位");
                        viewHolderOne.tvCurrentLocateCity.setVisibility(View.GONE);
                        mLocationClient.startLocation();
                    }

                }
            });

        } else if (viewType == 1) {//最近访问城市
            convertView = View.inflate(mContext, R.layout.item_recent_visit_city, null);
            TextView tvRecentVisitCity = (TextView) convertView.findViewById(R.id.tv_recent_visit_city);
            tvRecentVisitCity.setText("最近访问城市");
            MyGridView gvRecentVisitCity = (MyGridView) convertView.findViewById(R.id.gv_recent_visit_city);
            RecentVisitCityAdapter recentVisitCityAdapter = new RecentVisitCityAdapter(mContext, mRecentCityList);
            gvRecentVisitCity.setAdapter(recentVisitCityAdapter);
            recentVisitCityAdapter.setOnCityclickListener(new RecentVisitCityAdapter.onCityOnclickListener() {
                @Override
                public void onCityClick(int position, String recentcity) {
                    if (onCityOnclickListener != null) {
                        onCityOnclickListener.onCityClick(position, mRecentCityList.get(position));
                    }
                }
            });


        } else if (viewType == 2) {//热门城市
            convertView = View.inflate(mContext, R.layout.item_recent_visit_city, null);
            TextView tvRecentVisitCity = (TextView) convertView.findViewById(R.id.tv_recent_visit_city);
            tvRecentVisitCity.setText("热门城市");
            MyGridView gvRecentVisitCity = (MyGridView) convertView.findViewById(R.id.gv_recent_visit_city);
            HotCityAdapter hotCityAdapter = new HotCityAdapter(mContext, mHotCityList);
            gvRecentVisitCity.setAdapter(hotCityAdapter);
            hotCityAdapter.setOnHotCityclickListener(new HotCityAdapter.onHotCityOnclickListener() {
                @Override
                public void onHotCityClick(int position, String hotcity) {
                    if (onCityOnclickListener != null) {
                        onCityOnclickListener.onCityClick(position, mHotCityList.get(position).getName());
                    }
                }
            });
        } else if (viewType == 3) {//全部城市，仅展示“全部城市这四个字”
            convertView = View.inflate(mContext, R.layout.item_all_city_textview, null);
        } else {//数据库中所有的城市的名字展示
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = View.inflate(mContext, R.layout.item_city_list, null);
                viewHolder.tvAlpha = (TextView) convertView.findViewById(R.id.tv_alpha);
                viewHolder.tvCityName = (TextView) convertView.findViewById(R.id.tv_city_name);
                viewHolder.llMain = (LinearLayout) convertView.findViewById(R.id.ll_main);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            if (position >= 1) {
                viewHolder.tvCityName.setText(mAllCityList.get(position).getName());
                viewHolder.llMain.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onCityOnclickListener != null) {
                            onCityOnclickListener.onCityClick(position, mAllCityList.get(position).getName());
                        }

                    }
                });
                String currentStr = getAlpha(mAllCityList.get(position).getPinyin());
                String previewStr = (position - 1) >= 0 ? getAlpha(mAllCityList
                        .get(position - 1).getPinyin()) : " ";
                //如果当前的条目的城市名字的拼音的首字母和其前一条条目的城市的名字的拼音的首字母不相同，则将布局中的展示字母的TextView展示出来
                if (!previewStr.equals(currentStr)) {
                    viewHolder.tvAlpha.setVisibility(View.VISIBLE);
                    viewHolder.tvAlpha.setText(currentStr);
                } else {
                    viewHolder.tvAlpha.setVisibility(View.GONE);
                }
            }


        }


        return convertView;
    }


    // 获得汉语拼音首字母
    private String getAlpha(String str) {
        if (str == null) {
            return "#";
        }
        if (str.trim().length() == 0) {
            return "#";
        }
        char c = str.trim().substring(0, 1).charAt(0);
        // 正则表达式，判断首字母是否是英文字母
        Pattern pattern = Pattern.compile("^[A-Za-z]+$");
        if (pattern.matcher(c + "").matches()) {
            return (c + "").toUpperCase();
        } else if (str.equals("0")) {
            return "定位";
        } else if (str.equals("1")) {
            return "最近";
        } else if (str.equals("2")) {
            return "热门";
        } else if (str.equals("3")) {
            return "全部";
        } else {
            return "#";
        }
    }

    class ViewHolder {
        TextView tvAlpha;
        TextView tvCityName;
        LinearLayout llMain;
    }

    class ViewHolderOne {
         TextView tvCurrentLocateCity;
         ProgressBar pbLocate;
         TextView tvLocate;
    }

    public void initLocation() {
        mLocationClient = new AMapLocationClient(mContext);
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(
                AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setOnceLocation(true);
        mLocationOption.setOnceLocationLatest(true);
        mLocationOption.setNeedAddress(true);
        //mLocationOption.setInterval(1000);   // 自定义连续定位
        mLocationOption.setMockEnable(false);   // 不允许模拟位置
        mLocationOption.setHttpTimeOut(20000);  // 建议超时时间不要低于8000毫秒
        mLocationOption.setLocationCacheEnable(false);  // 关闭缓存机制
        mLocationClient.setLocationOption(mLocationOption);
        mLocationClient.setLocationListener(this);
    }


    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {

        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                isNeedRefresh = false;
                //定位成功
                currentCity = aMapLocation.getCity().substring(0, aMapLocation.getCity().length() - 1);
                viewHolderOne.tvLocate.setText("当前定位城市");
                viewHolderOne.tvCurrentLocateCity.setVisibility(View.VISIBLE);
                viewHolderOne.tvCurrentLocateCity.setText(currentCity.replace("市", ""));
                mLocationClient.stopLocation();
                viewHolderOne.pbLocate.setVisibility(View.GONE);
            } else {
                //定位失败
                viewHolderOne.tvLocate.setText("未定位到城市,请选择");
                viewHolderOne.tvCurrentLocateCity.setVisibility(View.VISIBLE);
                viewHolderOne.tvCurrentLocateCity.setText("重新选择");
                viewHolderOne. pbLocate.setVisibility(View.GONE);
                return;
            }
        } else {
            //定位失败
            viewHolderOne.tvLocate.setText("未定位到城市,请选择");
            viewHolderOne.tvCurrentLocateCity.setVisibility(View.VISIBLE);
            viewHolderOne.tvCurrentLocateCity.setText("重新选择");
            viewHolderOne.pbLocate.setVisibility(View.GONE);
            return;
        }

    }


    /**
     * listview 的点击接口
     */
    public interface onCityOnclickListener {
        void onCityClick(int position, String cityList);


    }

}
