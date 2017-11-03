package com.jierong.share.mvp.model.info;

import com.jierong.share.util.JSONUtil;

import java.io.Serializable;
import java.util.List;

/**
 * 月返利额榜模型
 */
public class MonthFanInfo implements Serializable {
    private String id;
    private String name;
    private String reward;
    private String rebate;

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
    public String getRebate() {
        return rebate;
    }
    public void setRebate(String rebate) {
        this.rebate = rebate;
    }

    // gson转换
    public static MonthFanInfo fromJSON(String JSONString) {
        if (JSONString == null)
            return null;
        return JSONUtil.fromJson(JSONString, MonthFanInfo.class);
    }
    public static List<MonthFanInfo> fromJSONS(String JSONSString) {
        if (JSONSString == null) return null;
        return JSONUtil.fromJsons(JSONSString, MonthFanInfo.class);
    }
    public String toJSON() {
        return JSONUtil.toJson(this);
    }
    public String toString() {
        return toJSON();
    }

}
