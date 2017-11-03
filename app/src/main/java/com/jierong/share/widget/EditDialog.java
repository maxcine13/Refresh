package com.jierong.share.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.TextView;
import com.jierong.share.R;

/**
 * 输入框对话框
 */
public class EditDialog extends Dialog implements View.OnClickListener {
    private DelEditText edit_view;
    private TextView tv_no, tv_ok;
    private OnClickButtonListener mOnClickButtonListener;

    public EditDialog(Context context) {
        this(context, 0);
    }

    public EditDialog(Context context, int themeResId) {
        super(context, R.style.alert_dialog);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_input_yqm);

        edit_view = (DelEditText)findViewById(R.id.edit_view);
        tv_no = (TextView)findViewById(R.id.tv_no);
        tv_ok = (TextView)findViewById(R.id.tv_ok);

        tv_no.setOnClickListener(this);
        tv_ok.setOnClickListener(this);
    }

    /**
     * 获取输入框编辑内容
     * @return
     */
    public String getEditContent() {
        return edit_view.getText().toString();
    }

    /**
     * 设置输入框过滤器
     * @param filters
     */
    public EditDialog setEditFilter(InputFilter[] filters) {
        edit_view.setEditFilter(filters);
        return this;
    }

    /**
     * 外部引入监听事件
     * @param listener
     * @return
     */
    public EditDialog setClickListener(OnClickButtonListener listener) {
        mOnClickButtonListener = listener;
        return this;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_no) {
            if (mOnClickButtonListener != null) {
                mOnClickButtonListener.onClick(EditDialog.this, 0);
                cancel();
            } else {
                cancel();
            }
        } else if (view.getId() == R.id.tv_ok) {
            if (mOnClickButtonListener != null) {
                mOnClickButtonListener.onClick(EditDialog.this, 1);
            }
        }
    }

    /**
     * 对话框中按钮点击事件
     */
    public interface OnClickButtonListener {
        void onClick(EditDialog dialog, int flag);
    }

}
