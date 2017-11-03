package com.jierong.share.banner.loader;

import android.content.Context;
import android.widget.ImageView;
import java.io.Serializable;

public interface ImageLoader extends Serializable {

    /**
     * 兼容第三方图片加载的接口
     * @param context
     * @param path
     * @param imageView
     */
    void displayImage(Context context, Object path, ImageView imageView);
}
