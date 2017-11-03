package com.jierong.share.mvp.model.info;

import com.jierong.share.util.JSONUtil;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/4/16.
 */

public class UpdateInfo implements Serializable {

    /**
     * version : 4
     * desc : 1.优化头像上传，更加方便美观。
     * 2.优化搜索功能。
     * path : http://skb.91jierong.com/index.php/Ports/Server/download?name=skb_1.2.2
     */

    private String version;//服务端的最新版本号
    private String desc;    //更新的描述
    private String path;     //更新的地址

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    // gson转换
    public static UpdateInfo fromJSON(String JSONString) {
        if (JSONString == null)
            return null;
        return JSONUtil.fromJson(JSONString, UpdateInfo.class);
    }

    public static List<UpdateInfo> fromJSONS(String JSONSString) {
        if (JSONSString == null) return null;
        return JSONUtil.fromJsons(JSONSString, UpdateInfo.class);
    }

    public String toJSON() {
        return JSONUtil.toJson(this);
    }

    public String toString() {
        return toJSON();
    }
}
