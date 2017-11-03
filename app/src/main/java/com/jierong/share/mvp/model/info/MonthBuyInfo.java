package com.jierong.share.mvp.model.info;

import com.jierong.share.util.JSONUtil;
import java.io.Serializable;
import java.util.List;

/**
 * 月购买量榜模型
 */
public class MonthBuyInfo implements Serializable {
    private String id;
    private String name;
    private String reward;
    private String buy;

    private String user_id;

    // get,set注入
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getReward() {
        return reward;
    }
    public void setReward(String reward) {
        this.reward = reward;
    }
    public String getBuy() {
        return buy;
    }
    public void setBuy(String buy) {
        this.buy = buy;
    }

    // gson转换
    public static MonthBuyInfo fromJSON(String JSONString) {
        if (JSONString == null)
            return null;
        return JSONUtil.fromJson(JSONString, MonthBuyInfo.class);
    }
    public static List<MonthBuyInfo> fromJSONS(String JSONSString) {
        if (JSONSString == null) return null;
        return JSONUtil.fromJsons(JSONSString, MonthBuyInfo.class);
    }
    public String toJSON() {
        return JSONUtil.toJson(this);
    }
    public String toString() {
        return toJSON();
    }

}
