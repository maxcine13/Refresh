package com.jierong.share.mvp.model.info;

import android.content.Intent;

/**
 * Created by wht on 2017/5/9.
 */

public class EventBusInfo {
    private String name;
    private String value;

    public Intent getData() {
        return data;
    }

    public void setData(Intent data) {
        this.data = data;
    }

    private Intent data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
