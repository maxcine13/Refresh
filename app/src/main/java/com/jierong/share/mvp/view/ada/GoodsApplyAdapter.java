package com.jierong.share.mvp.view.ada;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jierong.share.Constants;
import com.jierong.share.R;
import com.jierong.share.mvp.model.info.GoodsInfo;
import com.jierong.share.okhttp.BaseHttpUtil;
import com.jierong.share.okhttp.HttpStringCallBack;
import com.jierong.share.util.AppPreferences;
import com.jierong.share.util.ToastUtils;
import com.lzy.okgo.model.HttpParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by lovvol on 2017-03-23.
 */

public class GoodsApplyAdapter extends RecyclerView.Adapter<GoodsApplyAdapter.MyRecyclerView> {
    private Context context;
    private List<GoodsInfo> data;
    private static final int VIEW_TYPE = -1;


    public GoodsApplyAdapter(Context context, List<GoodsInfo> data) {
        this.context = context;
        this.data = data;

    }

    @Override
    public void onBindViewHolder(final MyRecyclerView holder, final int position) {
        holder.goodsnumber.setText(data.get(position).getOrdernumber());
        holder.goods_time.setText(data.get(position).getCreate_time());
        if (data.get(position).getState().equals("结算佣金")) {
            holder.operation.getResources().getColor(R.color.color_white);
            holder.operation.setBackgroundResource(R.drawable.cheng);
            holder.operation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getmoney(data.get(position).getOrdernumber(), holder, data.get(position).getFlag());
                }
            });
        }
        holder.operation.setText(data.get(position).getState());
    }


    @Override
    public MyRecyclerView onCreateViewHolder(ViewGroup parent, int viewType) {
        MyRecyclerView holder;
        holder = new MyRecyclerView(LayoutInflater.from(context).inflate(R.layout.act_goods_item, parent, false));
        return holder;
    }

    class MyRecyclerView extends RecyclerView.ViewHolder {

        TextView goodsnumber;//商品编号
        TextView goods_time;//订单时间
        TextView operation;//操作

        public MyRecyclerView(View itemView) {
            super(itemView);
            goodsnumber = (TextView) itemView.findViewById(R.id.goodsnumber);
            goods_time = (TextView) itemView.findViewById(R.id.goods_time);
            operation = (TextView) itemView.findViewById(R.id.operation);
        }
    }


    @Override
    public int getItemCount() {
//三目运算符
//        if (null == data) {
//            return 0;
//        } else {
//            return data.size();
//        }
        return data == null ? 0 : data.size();

    }

    public void getmoney(String ordernumber, final MyRecyclerView holder, int flag) {
        BaseHttpUtil baseHttpUtil = new BaseHttpUtil();
        String uid = AppPreferences.getString(context, Constants.PNK_UId, "");
        String token = AppPreferences.getString(context, Constants.PNK_UToken, "");
        String url = Constants.Http_Api_TaoUpdatem;
        HttpParams httpParams = new HttpParams();
        httpParams.put("uid", uid);
        httpParams.put("token", token);
        httpParams.put("ordernumber", ordernumber);
        httpParams.put("flag", flag);

        baseHttpUtil.doPost(url, httpParams, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                String incomelv = null;
                try {
                    JSONObject jsonObject = new JSONObject(result.toString());
                    incomelv = jsonObject.getString("incomelv");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                holder.operation.setBackgroundResource(R.drawable.textbg);
                holder.operation.setText(incomelv);
            }

            @Override
            public void onFailure(int code, String msg) {
                ToastUtils.show(context, msg.toString());
            }
        });
    }
}
