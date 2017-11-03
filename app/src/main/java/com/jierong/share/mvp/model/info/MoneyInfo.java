package com.jierong.share.mvp.model.info;

import com.jierong.share.util.JSONUtil;
import java.io.Serializable;
import java.util.List;

/**
 * 金额反馈模型
 */
public class MoneyInfo implements Serializable {
    private String num;     // 获取的金额

    // get, set注入
    public String getNum() {
        return num;
    }
    public void setNum(String num) {
        this.num = num;
    }

    // gson转换
    public static MoneyInfo fromJSON(String JSONString) {
        if (JSONString == null)
            return null;
        return JSONUtil.fromJson(JSONString, MoneyInfo.class);
    }
    public static List<MoneyInfo> fromJSONS(String JSONSString) {
        if (JSONSString == null) return null;
        return JSONUtil.fromJsons(JSONSString, MoneyInfo.class);
    }
    public String toJSON() {
        return JSONUtil.toJson(this);
    }
    public String toString() {
        return toJSON();
    }
}
