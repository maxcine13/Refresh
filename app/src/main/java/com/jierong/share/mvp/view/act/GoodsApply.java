package com.jierong.share.mvp.view.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.jierong.share.BaseAct;
import com.jierong.share.Constants;
import com.jierong.share.R;
import com.jierong.share.mvp.model.info.GoodsInfo;
import com.jierong.share.mvp.view.ada.GoodsApplyAdapter;
import com.jierong.share.okhttp.BaseHttpUtil;
import com.jierong.share.okhttp.HttpStringCallBack;
import com.jierong.share.util.LogUtil;
import com.jierong.share.util.ToastUtils;
import com.lzy.okgo.model.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;

/**
 * Created by lovvol on 2017-06-29. 申请返利页面
 */

public class GoodsApply extends BaseAct implements View.OnClickListener {
    private RecyclerView recyclerView;
    private List<GoodsInfo> info;
    private GoodsApplyAdapter adapter;
    private Button btn_search;
    private EditText edit_search;
    private BaseHttpUtil baseHttpUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_goods);
        ((TextView) findViewById(R.id.titleName)).setText(R.string.me_money_shopid);
        findViewById(R.id.titleBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_search = (Button) findViewById(R.id.btn_search);
        btn_search.setOnClickListener(this);
        edit_search = (EditText) findViewById(R.id.edit_search);
        recyclerView = (RecyclerView) findViewById(R.id.goods_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getData();
    }

    @Override
    public void onNetNo() {

    }

    @Override
    public void onNetOk() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_search:
                if (edit_search.getText().toString().trim().equals("")) {
                    ToastUtils.show(this, "订单号不能为空");
                } else {
                    submit(edit_search.getText().toString().trim());
                }

                break;
            default:
                break;
        }
    }

    public void submit(String ordernumber) {
        String url = Constants.Http_Api_Submit;
        HttpParams httpParams = new HttpParams();
        httpParams.put("uid", getGlobalId());
        httpParams.put("token", getGlobalToken());
        httpParams.put("ordernumber", ordernumber);
        new BaseHttpUtil().doPost(url, httpParams, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                try {
                    JSONObject jsonObject = new JSONObject(result.toString());
                    String msg2 = jsonObject.getString("msg");
                    ToastUtils.show(GoodsApply.this, msg2.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //数据库先插入再查询
                getData();

            }

            @Override
            public void onFailure(int code, String msg) {
                ToastUtils.show(GoodsApply.this, msg.toString());
            }
        });

    }

    public void getData() {
        baseHttpUtil = new BaseHttpUtil();
        String url = Constants.Http_Api_GoodsLists;
        HttpParams httpParams = new HttpParams();
        httpParams.put("uid", getGlobalId());
        httpParams.put("token", getGlobalToken());
        baseHttpUtil.doPost(url, httpParams, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                info = GoodsInfo.fromJSONS(String.valueOf(result));
                LogUtil.d("resultresult" + result.toString());
                adapter = new GoodsApplyAdapter(GoodsApply.this, info);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(int code, String msg) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        baseHttpUtil.closeHttp();
    }
}
