package com.jierong.share.mvp.model.info;

import com.jierong.share.util.JSONUtil;

import java.io.Serializable;

/**
 * Created by Administrator on 2017-04-28.
 */

public class RecommendInfo implements Serializable {

    /**
     * recode : 4B031B
     * desc : 邀请好友扫描二维码下载APP，注册时填写上面邀请码，就可获得推荐随机奖励，推荐越多，奖励越多哦
     * down_img : http://toshare.91jierong.com/QRcode/app_down.png
     */

    private String recode;
    private String desc;
    private String down_img;

    public String getRecode() {
        return recode;
    }

    public void setRecode(String recode) {
        this.recode = recode;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDown_img() {
        return down_img;
    }

    public void setDown_img(String down_img) {
        this.down_img = down_img;
    }

    // gson转换
    public static RecommendInfo fromJSON(String JSONString) {
        if (JSONString == null)
            return null;
        return JSONUtil.fromJson(JSONString, RecommendInfo.class);
    }
}
