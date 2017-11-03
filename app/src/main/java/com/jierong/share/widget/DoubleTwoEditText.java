package com.jierong.share.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.AttributeSet;
import android.widget.EditText;
import com.jierong.share.R;

public class DoubleTwoEditText extends EditText {
    private static final int DEFAULT_DECIMAL_NUMBER = 2;
    private int mDecimalNumber = DEFAULT_DECIMAL_NUMBER;

    public DoubleTwoEditText(Context context) {
        this(context, null, R.attr.editTextStyle);
    }

    public DoubleTwoEditText(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.editTextStyle);
    }

    public DoubleTwoEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DecimalEditText);
        mDecimalNumber = typedArray.getInt(R.styleable.DecimalEditText_decimalNumber, DEFAULT_DECIMAL_NUMBER);
        typedArray.recycle();
        init();
    }

    private void init() {
        setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String lastInputContent = dest.toString();
                if (source.equals(".") && lastInputContent.length() == 0) {
                    return "0.";
                }
                if (lastInputContent.contains(".")) {
                    int index = lastInputContent.indexOf(".");
                    if (dend - index >= mDecimalNumber + 1) {
                        return "";
                    }
                }
                return null;
            }
        }});
    }

    public int getDecimalNumber() {
        return mDecimalNumber;
    }

    public void setDecimalNumber(int decimalNumber) {
        mDecimalNumber = decimalNumber;
    }
}
