package com.jierong.share.util;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by liguohui on 2017/1/11.
 */

public  class ListViewHeight {
    /**
     * 动态设置listview的高度
     *
     * @param listView
     *            需要设置高度的listview
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {

        // 取到listview的适配器
        ListAdapter listAdapter = listView.getAdapter();
        // 判断设配器是否为空
        if (listAdapter == null) {
            return;
        }
        // 定义一个变量记录listview的高度
        int totalHeight = 0;
        // 循环listview的每一条,计算每一条的高度
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));

        ((ViewGroup.MarginLayoutParams) params).setMargins(10, 10, 10, 10); // 可删除
        listView.setLayoutParams(params);
    }
    public static void setRecycleViewHeightBasedOnChildren(RecyclerView recyclerView) {
//
//        // 取到listview的适配器
//        RecyclerView.Adapter listAdapter = recyclerView.getAdapter();
//        // 判断设配器是否为空
//        if (listAdapter == null) {
//            return;
//        }
//        // 定义一个变量记录listview的高度
//        int totalHeight = 0;
//        // 循环listview的每一条,计算每一条的高度
//        for (int i = 0; i < listAdapter.getItemCount(); i++) {
//            View listItem = listAdapter.get
//            listItem.measure(0, 0);
//            totalHeight += listItem.getMeasuredHeight();
//        }
//
//        ViewGroup.LayoutParams params = listView.getLayoutParams();
//
//        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
//
//        ((ViewGroup.MarginLayoutParams) params).setMargins(10, 10, 10, 10); // 可删除
//        listView.setLayoutParams(params);
    }
}
