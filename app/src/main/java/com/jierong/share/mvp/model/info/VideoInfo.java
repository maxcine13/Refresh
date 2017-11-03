package com.jierong.share.mvp.model.info;

import java.io.Serializable;

/**
 * 视频模型
 */
public class VideoInfo implements Serializable {
    private String id;
    private String url;
    private String name;
    private String time;
    private boolean isLove;
    private int loveNum;
    private int commentNum;

    // get,set方法
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
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public boolean isLove() {
        return isLove;
    }
    public void setLove(boolean love) {
        isLove = love;
    }
    public int getLoveNum() {
        return loveNum;
    }
    public void setLoveNum(int loveNum) {
        this.loveNum = loveNum;
    }
    public int getCommentNum() {
        return commentNum;
    }
    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

}
