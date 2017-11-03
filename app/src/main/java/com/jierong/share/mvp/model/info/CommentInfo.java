package com.jierong.share.mvp.model.info;

import com.jierong.share.util.JSONUtil;

import java.util.List;

public class CommentInfo {


    /**
     * user_id : 48
     * create_time : 2017-05-09 10:41:49
     * comment : 很牛
     * avatar : http://toshare.91jierong.com/./Public/Avatar/2017-04-27/59018f942cf4c.jpg
     * name : 杨明天
     */

    private String user_id;
    private String create_time;
    private String comment;
    private String avatar;
    private String name;

    // gson转换
    public static CommentInfo fromJSON(String JSONString) {
        if (JSONString == null)
            return null;
        return JSONUtil.fromJson(JSONString, CommentInfo.class);
    }
    public static List<CommentInfo> fromJSONS(String JSONSString) {
        if (JSONSString == null) return null;
        return JSONUtil.fromJsons(JSONSString, CommentInfo.class);
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}