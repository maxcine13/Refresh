package com.jierong.share.mvp.model.info;

import com.jierong.share.util.JSONUtil;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017-06-29.
 */

public class GoodsInfo implements Serializable {

    /**
     * ordernumber : 8001898673932358
     * create_time : 2017-06-29 15:21:36
     * ordtime : 1498813323
     * flag : 4
     * state : 申请中
     */

    private String ordernumber;
    private String create_time;
    private int ordtime;
    private int flag;
    private String state;

    public String getOrdernumber() {
        return ordernumber;
    }

    public void setOrdernumber(String ordernumber) {
        this.ordernumber = ordernumber;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getOrdtime() {
        return ordtime;
    }

    public void setOrdtime(int ordtime) {
        this.ordtime = ordtime;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    //json转换
    public static List<GoodsInfo> fromJSONS(String JSONSString) {
        if (JSONSString == null) return null;
        return JSONUtil.fromJsons(JSONSString, GoodsInfo.class);
    }
}
