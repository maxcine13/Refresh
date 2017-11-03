package com.jierong.share.mvp.model.info;

import com.jierong.share.util.JSONUtil;
import java.io.Serializable;
import java.util.List;

/**
 * 广告分类模型
 */
public class AdvTypeInfo implements Serializable {
    private String id;      // 广告分类唯一标示
    private String title;   // 广告分类标题
    private String tip;     // 广告分类副标题
    private String img;     // 广告分类图标
    private String tipMore; // 广告分类副标题（详细）

    // get,set注入
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTip() {
        return tip;
    }
    public void setTip(String tip) {
        this.tip = tip;
    }
    public String getImg() {
        return img;
    }
    public void setImg(String img) {
        this.img = img;
    }
    public String getTipMore() {
        return tipMore;
    }
    public void setTipMore(String tipMore) {
        this.tipMore = tipMore;
    }

    // gson转换
    public static AdvTypeInfo fromJSON(String JSONString) {
        if (JSONString == null)
            return null;
        return JSONUtil.fromJson(JSONString, AdvTypeInfo.class);
    }
    public static List<AdvTypeInfo> fromJSONS(String JSONSString) {
        if (JSONSString == null) return null;
        return JSONUtil.fromJsons(JSONSString, AdvTypeInfo.class);
    }
    public String toJSON() {
        return JSONUtil.toJson(this);
    }
    public String toString() {
        return toJSON();
    }
}
