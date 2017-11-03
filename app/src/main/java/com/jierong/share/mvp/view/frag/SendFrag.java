package com.jierong.share.mvp.view.frag;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.jierong.share.BaseFrag;
import com.jierong.share.R;
import com.jierong.share.mvp.model.info.AdvTypeInfo;
import com.jierong.share.mvp.presenter.SendPresenter;
import com.jierong.share.mvp.view.ISendView;
import com.jierong.share.mvp.view.act.EditTextAct;
import com.jierong.share.util.ToastUtils;
import com.jierong.share.widget.LoadingDialog;
import com.jierong.share.widget.TypeDialog;

import java.util.List;

/**
 * 广告投放模块
 */
public class SendFrag extends BaseFrag implements View.OnClickListener, ISendView {
    private RelativeLayout view_nonet;
    private Spinner value_type;
    private TextView send_tip, send_submit, value_desc, value_typ;
    private RelativeLayout send_name, send_tel, send_type, send_desc;
    private boolean isClick = false;    // 当前是否在点击状态
    private Dialog mLoadingDialog;
    private String mType;
    private SendPresenter mSendPresenter;
    private EditText value_tel, value_name;
    private final int Request_Code_Desc = 103;  // 广告需求
    private List<AdvTypeInfo> data;
    private TypeDialog typeDialog;//分类dialog

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_send, null);
        initView(view);
        return view;
    }

    @Override
    public void onNetNo() {
        view_nonet.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNetOk() {
        view_nonet.setVisibility(View.GONE);
    }

    public void setClick(boolean click) {
        isClick = click;
    }

    private void initView(View view) {
        mSendPresenter = new SendPresenter(this);
        ((TextView) view.findViewById(R.id.titleName)).setText(R.string.title_name_send);
        view.findViewById(R.id.titleBack).setVisibility(View.GONE);
        view_nonet = (RelativeLayout) view.findViewById(R.id.view_nonet);
        send_name = (RelativeLayout) view.findViewById(R.id.send_name);
        send_tel = (RelativeLayout) view.findViewById(R.id.send_tel);
        send_type = (RelativeLayout) view.findViewById(R.id.send_type);
        send_desc = (RelativeLayout) view.findViewById(R.id.send_desc);
        send_tip = (TextView) view.findViewById(R.id.send_tip);
        value_name = (EditText) view.findViewById(R.id.value_name);
        value_tel = (EditText) view.findViewById(R.id.value_tel);
        value_desc = (TextView) view.findViewById(R.id.value_desc);
        send_submit = (TextView) view.findViewById(R.id.send_submit);
        value_typ = (TextView) view.findViewById(R.id.value_typ);
        value_type = (Spinner) view.findViewById(R.id.value_type);
        mSendPresenter.getSendType();
        send_tip.setText("审核过程为人工审核，请准确填写以下信息，咨询热线：037962362088");
        send_tip.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        send_tip.setSingleLine(true);
        send_tip.setSelected(true);
        send_tip.setFocusable(true);
        send_tip.setFocusableInTouchMode(true);
        send_name.setOnClickListener(this);
        send_tel.setOnClickListener(this);
        send_type.setOnClickListener(this);
        send_submit.setOnClickListener(this);
        send_desc.setOnClickListener(this);
        value_typ.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send_desc:
                editDesc();
                break;
            case R.id.value_typ:
                typeDialog = new TypeDialog(getContext(), data);
                typeDialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        value_typ.setText(data.get(position).getTitle());
                        mType = data.get(position).getId();
                        typeDialog.dismiss();
                    }
                });
                typeDialog.show();
                break;
            case R.id.send_submit:
                mSendPresenter.doSend();
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Request_Code_Desc) {
            if (resultCode == Activity.RESULT_OK) {
                value_desc.setText(data.getStringExtra("InputName"));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public Context getMContext() {
        return getActivity();
    }

    @Override
    public void showLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = LoadingDialog.createLoadingDialog(getActivity(), "正在加载中...");
            mLoadingDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }


    private void editDesc() {
        Intent intent = new Intent(getActivity(), EditTextAct.class);
        intent.putExtra("Ask", "SendFrag");
        intent.putExtra("MName", value_desc.getText().toString());
        intent.putExtra("TitleName", "广 告 需 求");
        startActivityForResult(intent, Request_Code_Desc);
    }

    @Override
    public void receiveType(List<AdvTypeInfo> advTypeInfos) {
        data = advTypeInfos;
        value_typ.setText(data.get(0).getTitle());

    }

    @Override
    public String getUName() {
        return value_name.getText().toString();
    }

    @Override
    public String getUTel() {
        return value_tel.getText().toString();
    }

    @Override
    public String getAdvType() {
        return mType;
    }

    @Override
    public String getAdvDesc() {
        return value_desc.getText().toString();
    }

    @Override
    public void sendSuccess() {
        ToastUtils.show(getActivity(), "需求提交成功,工作人员将在24小时内完成审核!");
        value_name.setText(null);
        value_tel.setText(null);
        value_desc.setText(null);
    }

    @Override
    public void showError(String msg, boolean flag) {
        ToastUtils.show(getActivity(), msg);
        if (flag) getActivity().finish();
    }

    @Override
    public void onDestroyView() {
        if (typeDialog != null) {
            typeDialog.dismiss();
            typeDialog = null;
        }
        hideLoading();
        mSendPresenter.closeHttp();
        super.onDestroyView();
    }

}
