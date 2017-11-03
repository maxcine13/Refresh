package com.jierong.share.mvp.model.info;

import com.jierong.share.util.JSONUtil;
import java.io.Serializable;
import java.util.List;

/**
 * 爆款返利商品模型
 */
public class BkInfo implements Serializable {
    private String pic;         // 商品图片
    private String price;       // 商品价格
    private String fold;        // 返利
    private String url;         // 商品链接

    // get,set注入
    public String getPic() {
        return pic;
    }
    public void setPic(String pic) {
        this.pic = pic;
    }
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public String getFold() {
        return fold;
    }
    public void setFold(String fold) {
        this.fold = fold;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    // gson转换
    public static BkInfo fromJSON(String JSONString) {
        if (JSONString == null)
            return null;
        return JSONUtil.fromJson(JSONString, BkInfo.class);
    }
    public static List<BkInfo> fromJSONS(String JSONSString) {
        if (JSONSString == null) return null;
        return JSONUtil.fromJsons(JSONSString, BkInfo.class);
    }
    public String toJSON() {
        return JSONUtil.toJson(this);
    }
    public String toString() {
        return toJSON();
    }
}
