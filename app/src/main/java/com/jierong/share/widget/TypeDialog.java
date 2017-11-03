package com.jierong.share.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jierong.share.R;
import com.jierong.share.mvp.model.info.AdvTypeInfo;
import com.jierong.share.util.ListViewHeight;

import java.util.List;


/**
 * Created by Administrator on 2017/1/12.
 */

public class TypeDialog extends Dialog {
    private ListView value_list;
    private AdapterView.OnItemClickListener onItemClickListener;//确定按钮被点击了的监听器
    private Context context;
    private List<AdvTypeInfo> items;

    /**
     * 设置确定按钮的显示内容和监听
     *
     * @param onclickListener
     */
    public void setOnItemClickListener(AdapterView.OnItemClickListener onclickListener) {
        this.onItemClickListener = onclickListener;
    }

    public TypeDialog(Context context, List<AdvTypeInfo> objects) {
        super(context, R.style.MyDialog);
        this.context = context;
        this.items = objects;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.type_dialog);
        //按空白处能取消动画
        setCanceledOnTouchOutside(true);
        //初始化界面控件
        initView();
        //初始化界面控件的事件
        initEvent();
        MyAdapter myAdapter = new MyAdapter(context, items);
        value_list.setAdapter(myAdapter);

    }

    /**
     * 初始化界面的确定和取消监听器
     */
    private void initEvent() {
        value_list.setOnItemClickListener(onItemClickListener);


    }


    /**
     * 初始化界面控件
     */
    private void initView() {
        value_list = (ListView) findViewById(R.id.value_list);
    }


    public class MyAdapter extends BaseAdapter {
        private LayoutInflater layoutInflater;
        private List<AdvTypeInfo> data;

        public MyAdapter(Context context, List<AdvTypeInfo> data) {
            layoutInflater = LayoutInflater.from(context);
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            ViewHodel viewHodel;
            // 对view判空
            if (view == null) {
                viewHodel = new ViewHodel();
                view = layoutInflater.inflate(R.layout.item_typedialog, null);
                // 通过ViewHolder对象获取对应控件
                viewHodel.textView = (TextView) view.findViewById(R.id.value_type);
                // 通过setTag()绑定ViewHolder和view
                view.setTag(viewHodel);
            } else {
                // 通过getTag()获取已绑定的ViewHolder
                viewHodel = (ViewHodel) view.getTag();
            }
            viewHodel.textView.setText(data.get(position).getTitle());

            return view;
        }

        /*创建内部类*/
        class ViewHodel {
            public TextView textView;


        }

    }


}
