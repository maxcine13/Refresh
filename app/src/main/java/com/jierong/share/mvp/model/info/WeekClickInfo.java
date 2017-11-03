package com.jierong.share.mvp.model.info;

import com.jierong.share.util.JSONUtil;
import java.io.Serializable;
import java.util.List;

/**
 * 周点击量榜模型
 */
public class WeekClickInfo implements Serializable {
    private String id;
    private String name;
    private String reward;
    private String click;

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
    public String getClick() {
        return click;
    }
    public void setClick(String click) {
        this.click = click;
    }

    // gson转换
    public static WeekClickInfo fromJSON(String JSONString) {
        if (JSONString == null)
            return null;
        return JSONUtil.fromJson(JSONString, WeekClickInfo.class);
    }
    public static List<WeekClickInfo> fromJSONS(String JSONSString) {
        if (JSONSString == null) return null;
        return JSONUtil.fromJsons(JSONSString, WeekClickInfo.class);
    }
    public String toJSON() {
        return JSONUtil.toJson(this);
    }
    public String toString() {
        return toJSON();
    }

}
