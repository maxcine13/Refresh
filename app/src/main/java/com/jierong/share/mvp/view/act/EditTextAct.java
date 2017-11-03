package com.jierong.share.mvp.view.act;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.jierong.share.BaseAct;
import com.jierong.share.R;
import com.jierong.share.mvp.model.info.TagInfo;
import com.jierong.share.mvp.presenter.EditTextPresenter;
import com.jierong.share.mvp.view.IEditTextView;
import com.jierong.share.tag.FlowLayout;
import com.jierong.share.tag.TagAdapter;
import com.jierong.share.tag.TagFlowLayout;
import com.jierong.share.util.LogUtil;
import com.jierong.share.util.StringUtil;
import com.jierong.share.util.ToastUtils;
import com.jierong.share.widget.LoadingDialog;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 编辑文本界面
 */
public class EditTextAct extends BaseAct implements IEditTextView {
    private EditText input_name, input_need;
    private TextView titleName, input_city, submit;
    private Dialog mLoadingDialog;
    private String mAsk, mName, mCity;
    private EditTextPresenter mEditTextPresenter;
    private int mRequestCode = 101;  // 地址请求码
    private RelativeLayout send_need;
    private LinearLayout tagLayout;
    private RelativeLayout rela_city;
    private TagFlowLayout tagList;
    private TagAdapter<TagInfo> tagAdapter;
    private String tagSelectList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_edit_text);

        init();
    }

    private void init() {
        mEditTextPresenter = new EditTextPresenter(this);
        mAsk = getIntent().getStringExtra("Ask");
        mName = getIntent().getStringExtra("MName");
        mCity = getIntent().getStringExtra("MCity");
        LogUtil.d(mAsk + " : " + mName + " : " + mCity);
        titleName = (TextView) findViewById(R.id.titleName);
        input_name = (EditText) findViewById(R.id.input_name);
        send_need = (RelativeLayout) findViewById(R.id.send_need);
        input_need = (EditText) findViewById(R.id.input_need);
        input_city = (TextView) findViewById(R.id.input_city);
        rela_city = (RelativeLayout) findViewById(R.id.rela_city);
        tagLayout = (LinearLayout) findViewById(R.id.tagLayout);
        tagList = (TagFlowLayout) findViewById(R.id.tagList);
        submit = (TextView) findViewById(R.id.submit);
        titleName.setText(getIntent().getStringExtra("TitleName"));
        input_name.setText(mName);
        if (getIntent().getStringExtra("TitleName").equals("提 交 人 电 话")) {
            input_name.setHint("手机号码");
            input_name.setInputType(InputType.TYPE_CLASS_PHONE);
            input_name.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
        } else if (getIntent().getStringExtra("TitleName").equals("广 告 需 求")) {
            input_name.setVisibility(View.GONE);
            rela_city.setVisibility(View.GONE);
            send_need.setVisibility(View.VISIBLE);
        }
        if (mAsk.equals("MeFrag")) {
            tagLayout.setVisibility(View.VISIBLE);
            input_city.setVisibility(View.VISIBLE);

            // 初始化标签数据
            mEditTextPresenter.getTagList();
            input_city.setText(mCity);
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //mEditTextPresenter.changeNameCity();
                    mEditTextPresenter.changeNameCityTag();
                }
            });
        } else {
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String newName = getUName().trim();
                    String need = getNeed().trim();
                    if (StringUtil.isNotEmptyIgnoreBlank(newName) || StringUtil.isNotEmptyIgnoreBlank(need)) {
                        if (getIntent().getStringExtra("TitleName").equals("提 交 人 电 话")) {
                            if (StringUtil.isMobile(newName)) {
                                Intent data = new Intent();
                                data.putExtra("InputName", newName);
                                setResult(Activity.RESULT_OK, data);
                                finish();
                            } else {
                                ToastUtils.show(EditTextAct.this, "电话号码格式错误！！");
                                return;
                            }
                        } else if (getIntent().getStringExtra("TitleName").equals("广 告 需 求")) {
                            Intent data = new Intent();
                            data.putExtra("InputName", need);
                            setResult(Activity.RESULT_OK, data);
                            finish();
                        } else {
                            Intent data = new Intent();
                            data.putExtra("InputName", newName);
                            setResult(Activity.RESULT_OK, data);
                            finish();
                        }
                    } else {
                        ToastUtils.show(EditTextAct.this, "编辑内容不能为空！！");
                        return;
                    }
                }
            });
        }

        rela_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditTextAct.this, CityAct.class);
                intent.putExtra("address", "address");
                //intent.putExtra("allAddress","addAddress");
                EditTextAct.this.startActivityForResult(intent, mRequestCode);
            }
        });
        findViewById(R.id.titleBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });
    }

    @Override
    public void onNetNo() {
    }

    @Override
    public void onNetOk() {
    }

    @Override
    public Context getMContext() {
        return EditTextAct.this;
    }

    @Override
    public void showLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = LoadingDialog.createLoadingDialog(EditTextAct.this, "正在加载中...");
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

    @Override
    public String getUName() {
        return input_name.getText().toString();
    }

    @Override
    public String getNeed() {
        return input_need.getText().toString();
    }

    @Override
    public String getUCity() {
        return input_city.getText().toString();
    }

    @Override
    public String getTagList() {
        return tagSelectList;
    }

    @Override
    public void getTagSuccess(final List<TagInfo> data) {
        tagList.setAdapter(tagAdapter = new TagAdapter<TagInfo>(data) {
            @Override
            public View getView(FlowLayout parent, int position, TagInfo s) {
                TextView tv = (TextView) LayoutInflater.from(EditTextAct.this)
                        .inflate(R.layout.view_tv_tag, tagList, false);
                tv.setText(s.getLabel());
                return tv;
            }
        });
        tagList.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                tagSelectList = null;
                for (int index : selectPosSet) {
                    LogUtil.d("one ：" + index);
                    if(null == tagSelectList) tagSelectList = data.get(index).getId();
                    else tagSelectList += "," + data.get(index).getId();

                }
                LogUtil.d("all ：" + tagSelectList);
            }
        });

        Set<Integer> mSelected = new HashSet<>();
        for (TagInfo tagInfo : data) {
            if(tagInfo.getFlag() == 1)
                mSelected.add(data.indexOf(tagInfo));
        }
        tagAdapter.setSelectedList(mSelected);
    }

    @Override
    public void doSuccess() {
        String newName = getUName().trim();
        String newCity = getUCity().trim();
        if (StringUtil.isNotEmptyIgnoreBlank(newName) ||
                StringUtil.isNotEmptyIgnoreBlank(newCity)) {
            Intent data = new Intent();
            data.putExtra("InputName", newName);
            data.putExtra("InputCity", newCity);
            setResult(Activity.RESULT_OK, data);
            finish();
        } else {
            ToastUtils.show(EditTextAct.this, "编辑内容不能为空！！");
            return;
        }
    }

    @Override
    public void showError(String msg, boolean flag) {
        ToastUtils.show(this, msg);
        if (flag) finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == mRequestCode && resultCode == 102) {
            input_city.setText(data.getStringExtra("address"));
        }
    }

    @Override
    protected void onDestroy() {
        mEditTextPresenter.closeHttp();
        hideLoading();
        super.onDestroy();
    }
}
