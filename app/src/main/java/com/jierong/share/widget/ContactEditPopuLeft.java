package com.jierong.share.widget;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jierong.share.R;

/**
 * Created by Safly on 2016/7/4.
 */
public class ContactEditPopuLeft extends PopupWindow implements View.OnClickListener, PopupWindow.OnDismissListener {

    private static final String TAG = "ContactEditPopu";

    private TextView item1, item2;

    private ItemClickListener listener;
    private Activity context;

    public ContactEditPopuLeft(Activity context, String[] itemTexts) {
        LayoutInflater mInflater = LayoutInflater.from(context);
        View contentView = mInflater.inflate(R.layout.popu_layout_left, null);
        this.context = context;
        item1 = (TextView) contentView.findViewById(R.id.popuItem1);
        item2 = (TextView) contentView.findViewById(R.id.popuItem2);
        item1.setOnClickListener(this);
        item2.setOnClickListener(this);

        if (itemTexts != null) {
            this.setContentView(contentView);
            item1.setText(itemTexts[0]);
            item2.setText(itemTexts[1]);

        }

        this.setOnDismissListener(this);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setWidth(-2);
        this.setHeight(-2);

        this.setBackgroundDrawable(new BitmapDrawable());
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            switch (v.getId()) {
                case R.id.popuItem1:
                    listener.item1Listener();
                    break;
                case R.id.popuItem2:
                    listener.item2Listener();
                    break;

            }
        }
        this.dismiss();
    }

    public void showToggle(View parent, int xoff, int yoff) {
        if (isShowing()) {
            dismiss();
        } else {
            showAsDropDown(parent, xoff, yoff);
            Window window = ((Activity) context).getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            params.alpha = 0.6f;
            window.setAttributes(params);
        }
    }

    public void setItemListener(ItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onDismiss() {
        Window window = ((Activity) context).getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.alpha = 1.0f;
        window.setAttributes(params);
    }

    public interface ItemClickListener {
        void item1Listener();

        void item2Listener();

    }


}
