package com.jierong.share.mvp.model.info;

import com.jierong.share.util.JSONUtil;

import java.util.List;

public class CollectionInfo {


    /**
     * talent_id : 43
     * create_time : 2017-05-09 09:12:41
     * avatar : http://toshare.91jierong.com/./Public/Avatar/2017-05-07/590e68cae3013.jpg
     * name : 分享达人
     * proof : 未认证
     * score : 8.8
     */

    private String talent_id;
    private String create_time;
    private String avatar;
    private String name;
    private String proof;
    private double score;


    public String getTalent_id() {
        return talent_id;
    }

    public void setTalent_id(String talent_id) {
        this.talent_id = talent_id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
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

    public String getProof() {
        return proof;
    }

    public void setProof(String proof) {
        this.proof = proof;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    // gson转换
    public static CollectionInfo fromJSON(String JSONString) {
        if (JSONString == null)
            return null;
        return JSONUtil.fromJson(JSONString, CollectionInfo.class);
    }
    public static List<CollectionInfo> fromJSONS(String JSONSString) {
        if (JSONSString == null) return null;
        return JSONUtil.fromJsons(JSONSString, CollectionInfo.class);
    }
}