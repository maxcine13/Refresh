package com.jierong.share.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.jierong.share.R;

/**
 * 带删除按钮控件
 */
public class DelEditText extends LinearLayout {
    EditText edit;      // 编辑框
    ImageView image;    // 右边图片 删除用
    int color;          // 字体颜色
    int hintColor;          // 提示字体颜色
    String textHint;    // 提示信息
    int imageId;

    public DelEditText(Context context) {
        super(context, null);
    }

    public DelEditText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);   // 初始化操作
    }

    public DelEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs) {
        // 加载布局
        LayoutInflater.from(context).inflate(R.layout.dialog_edit_del, this, true);
        edit = (EditText) findViewById(R.id.et);
        image = (ImageView) findViewById(R.id.iv_del);
        // 获取自定义属性
        TypedArray typeArray = context.obtainStyledAttributes(attrs, R.styleable.DelEditText);
        color = typeArray.getColor(R.styleable.DelEditText_EditTextColor, Color.BLACK);
        hintColor = typeArray.getColor(R.styleable.DelEditText_EditHintColor, Color.BLACK);
        textHint = typeArray.getString(R.styleable.DelEditText_EditHint);
        imageId = typeArray.getResourceId(R.styleable.DelEditText_Image, R.drawable.delete_selector);
        // 添加方法
        setEditTextColor(color);    // 设置颜色
        setEditHint(textHint);      // 设置提示信息
        setEditImage(imageId);      // 设置删除图片

        edit.addTextChangedListener(textW); // EditText的输入内容改变监听
        // image view的点击监听
        image.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                edit.setText("");
                image.setVisibility(View.GONE);
            }
        });
        typeArray.recycle();
    }

    /**
     * 设置删除按钮图片
     */
    public void setEditImage(int imageId) {
        image.setImageResource(imageId);
    }

    /**
     * 设置输入框过滤器
     * @param filters
     */
    public void setEditFilter(InputFilter[] filters) {
        edit.setFilters(filters);
    }

    /**
     * 设置输入提示
     */
    public void setEditHint(String textHint) {
        edit.setHint(textHint);
        edit.setHintTextColor(hintColor);
    }

    /**
     * 设置字体颜色
     */
    public void setEditTextColor(int color) {
        edit.setTextColor(color);
    }

    // 返回EditText控件
    public EditText getEditText() {
        return edit;
    }

    // 返回EditText中输入的内容
    public String getText() {
        return edit.getText().toString();
    }

    // 设置EditText中内容
    public void setText(int text) {
        edit.setText(text);
    }

    // 设置EditText中内容
    public void setText(String text) {
        edit.setText(text);
    }

    /**
     * 编辑框改变监听
     */
    TextWatcher textW = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() == 0) {  // 没有输入隐藏按钮
                image.setVisibility(View.GONE);
            } else {
                image.setVisibility(View.VISIBLE);
            }
        }
    };

}
