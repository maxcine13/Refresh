package com.jierong.share.mvp.model.info;

import com.jierong.share.util.JSONUtil;
import java.io.Serializable;
import java.util.List;

/**
 * 推送命令模型模型
 */
public class PushInfo implements Serializable {
    public static final int Push_Logout = 9002;     // 强退的推送命令
    public static final int Push_Gg_Me = 9003;         // 发布“我的”公告的命令
    public static final int Push_Gg_Home = 9004;         // 发布“首页”公告的命令
    public static final int Push_Red_Packet = 9005;     // 发布“红包”的命令

    private String id;     // 推送消息的唯一id
    private int code;       // 推送的命令标识
    private String data;    // 推送带过来的数据
    private String time;    // 推送的时间

    // get，set注入
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }

    // gson转换
    public static PushInfo fromJSON(String JSONString) {
        if (JSONString == null)
            return null;
        return JSONUtil.fromJson(JSONString, PushInfo.class);
    }
    public static List<PushInfo> fromJSONS(String JSONSString) {
        if (JSONSString == null) return null;
        return JSONUtil.fromJsons(JSONSString, PushInfo.class);
    }
    public String toJSON() {
        return JSONUtil.toJson(this);
    }
    public String toString() {
        return toJSON();
    }
}
