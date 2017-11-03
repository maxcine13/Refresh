package com.jierong.share.mvp.model.info;

import com.jierong.share.util.JSONUtil;
import java.io.Serializable;
import java.util.List;

public class MessageInfo implements Serializable {
    private String time;        // 时间
    private String desc;        // 描述

    // get，set注入
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }

    // gson转换
    public static MessageInfo fromJSON(String JSONString) {
        if (JSONString == null)
            return null;
        return JSONUtil.fromJson(JSONString, MessageInfo.class);
    }
    public static List<MessageInfo> fromJSONS(String JSONSString) {
        if (JSONSString == null) return null;
        return JSONUtil.fromJsons(JSONSString, MessageInfo.class);
    }
    public String toJSON() {
        return JSONUtil.toJson(this);
    }
    public String toString() {
        return toJSON();
    }
}
