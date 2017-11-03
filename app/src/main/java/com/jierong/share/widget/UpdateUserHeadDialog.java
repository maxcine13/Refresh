package com.jierong.share.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import com.jierong.share.BaseApp;
import com.jierong.share.Constants;
import com.jierong.share.R;
import com.jierong.share.util.FileUtils;

import java.io.File;

/**
 * Created by jinjin on 16/2/18.
 */
public class UpdateUserHeadDialog extends Dialog implements View.OnClickListener {

    private Button btn_photograph;
    private Button btn_gallery;
    private Button btn_system;
    private Button btn_cancle;
    private Activity mContext;

    public UpdateUserHeadDialog(Context context, ImageView iv) {
        super(context, R.style.recommend_dialog);
        this.mContext = (Activity) context;
        setContentView(R.layout.dialog_choose_head);
        initView();
    }

    private void initView() {
        btn_photograph = (Button) findViewById(R.id.btn_photograph);
        btn_gallery = (Button) findViewById(R.id.btn_gallery);
        btn_system = (Button) findViewById(R.id.btn_system);
        btn_cancle = (Button) findViewById(R.id.btn_cancle);

        setCanceledOnTouchOutside(false);

        btn_cancle.setOnClickListener(this);
        btn_photograph.setOnClickListener(this);
        btn_gallery.setOnClickListener(this);
        btn_system.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_photograph:
                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(BaseApp.CACHE_DIR + Constants.New_User_Ic, "head.jpg")));
                mContext.startActivityForResult(intent2, 2);// 采用ForResult打开
                break;
            case R.id.btn_gallery:
                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                mContext.startActivityForResult(intent1, 1);
                break;
            case R.id.btn_system:
//                Intent intent3 = new Intent(Intent.ACTION_PICK, null);
//
//                mContext.startActivityForResult(intent1, 1);
//                Intent intent_sys=new Intent(mContext,MyHeadActivity.class);
//                mContext.startActivityForResult(intent_sys,4);
                break;
            case R.id.btn_cancle:
                dismiss();
                break;
        }
    }

}
