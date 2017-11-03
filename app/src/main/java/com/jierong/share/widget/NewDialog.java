package com.jierong.share.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jierong.share.R;
import com.jierong.share.util.GlideRoundTransform;
import com.jierong.share.util.ScreenUtil;

import static com.igexin.sdk.GTServiceManager.context;


/**
 * Created by Administrator on 2017/1/12.
 */

public class NewDialog extends Dialog {
    private ImageView img_all, img_fork;
    private TextView button_yes;
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

    public NewDialog(Context context, String pic, String money) {
        super(context, R.style.MyDialog);
        this.pic = pic;
        this.money = money;
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_dialog);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);
        setCancelable(false);
      /*
         * 将对话框的大小按屏幕大小的百分比设置
         */
//        Window dialogWindow = getWindow();
//        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
//        p.height = (int) (ScreenUtil.getScreenHeight(context) * 0.44); // 高度设置为屏幕的0.6
//        p.width = (int) (ScreenUtil.getScreenWidth(context) * 0.65); // 宽度设置为屏幕的0.65
//        dialogWindow.setAttributes(p);

        //初始化界面控件
        initView();

        //初始化界面控件的事件
        initEvent();

    }

    /**
     * 初始化界面的确定和取消监听器
     */
    private void initEvent() {

        //设置确定按钮被点击后，向外界提供监听
        button_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yesOnclickListener != null) {
                    yesOnclickListener.onYesClick();
                }
            }
        });
        img_fork.setOnClickListener(new View.OnClickListener() {
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
        img_all = (ImageView) findViewById(R.id.img_all);
        img_fork = (ImageView) findViewById(R.id.img_fork);
        button_yes = (TextView) findViewById(R.id.button_yes);
              /*
         * 将对话框的大小按屏幕大小的百分比设置
         */
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        p.height = (int) (ScreenUtil.getScreenHeight(context) * 0.44); // 高度设置为屏幕的0.6
        p.width = (int) (ScreenUtil.getScreenWidth(context) * 0.65); // 宽度设置为屏幕的0.65
//        dialogWindow.setAttributes(p);
        Glide.with(getContext())
                .load(pic).thumbnail(0.5f).bitmapTransform(new GlideRoundTransform(getContext(), 5))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(img_all);

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
