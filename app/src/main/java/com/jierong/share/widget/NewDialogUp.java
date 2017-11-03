package com.jierong.share.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.jierong.share.R;
import com.jierong.share.util.ScreenUtil;

/**
 * Created by lovvol on 2017/1/12.
 */
public class NewDialogUp extends Dialog {
    private TextView cancle_text, cancle;
    private onYesOnclickListener yesOnclickListener;//确定按钮被点击了的监听器
    private onDismissOnclickListener dismissOnclickListener;// 取消
    private String pic, money;
    private Context context;

    /**
     * 设置确定按钮的显示内容和监听
     *
     * @param onYesOnclickListener
     */
    public void setYesOnclickListener(onYesOnclickListener onYesOnclickListener) {
        this.yesOnclickListener = onYesOnclickListener;
    }

    /**
     * 设置取消按钮的显示内容和监听
     *
     * @param dismissOnclickListener
     */
    public void setDismissOnclickListener(onDismissOnclickListener dismissOnclickListener) {
        this.dismissOnclickListener = dismissOnclickListener;
    }

    public NewDialogUp(Context context, String money) {
        super(context, R.style.MyDialog);
        this.money = money;
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_dialogup);
        //按空白处取消动画 点击空白处dialog消失
        setCanceledOnTouchOutside(true);
        setCancelable(true);
      /*
         * 将对话框的大小按屏幕大小的百分比设置
         */

        //初始化界面控件
        initView();

        //初始化界面控件的事件
        initEvent();

    }

    /**
     * 初始化界面的确定和取消监听器
     */
    private void initEvent() {
        cancle_text.setText(money);

        //设置确定按钮被点击后，向外界提供监听
        cancle_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yesOnclickListener != null) {
                    yesOnclickListener.onYesClick();
                }
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dismissOnclickListener != null) {
                    dismissOnclickListener.onNoClick();
                }
            }
        });


    }


    /**
     * 初始化界面控件
     */
    private void initView() {
        cancle = (TextView) findViewById(R.id.cancle);
        cancle_text = (TextView) findViewById(R.id.cancle_text);
        /*
         * 将对话框的大小按屏幕大小的百分比设置
         */
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        p.height = (int) (ScreenUtil.getScreenHeight(context) * 0.44); // 高度设置为屏幕的0.6
        p.width = (int) (ScreenUtil.getScreenWidth(context) * 0.65); // 宽度设置为屏幕的0.65
        dialogWindow.setAttributes(p);
    }

    /**
     * 设置确定按钮和取消被点击的接口
     */
    public interface onYesOnclickListener {
        public void onYesClick();
    }

    /**
     * 设置取消被点击的接口
     */
    public interface onDismissOnclickListener {
        public void onNoClick();
    }

}
