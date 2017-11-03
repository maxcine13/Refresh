package com.jierong.share.mvp.model.info;

import com.jierong.share.util.JSONUtil;

import java.io.Serializable;

/**
 * Created by Administrator on 2017-04-28.
 */

public class WhileLineInfo implements Serializable {

    /**
     * whitebars : 6000.00
     * leftbar : 6000.00
     * sevenday : 0
     * tenday : 0
     * allback : 0
     * rule : http://toshare.91jierong.com/index.php/API/help/bar_rule
     */

    private String whitebars;
    private String leftbar;
    private String sevenday;
    private String tenday;
    private String allback;
    private String rule;

    public String getWhitebars() {
        return whitebars;
    }

    public void setWhitebars(String whitebars) {
        this.whitebars = whitebars;
    }

    public String getLeftbar() {
        return leftbar;
    }

    public void setLeftbar(String leftbar) {
        this.leftbar = leftbar;
    }

    public String getSevenday() {
        return sevenday;
    }

    public void setSevenday(String sevenday) {
        this.sevenday = sevenday;
    }

    public String getTenday() {
        return tenday;
    }

    public void setTenday(String tenday) {
        this.tenday = tenday;
    }

    public String getAllback() {
        return allback;
    }

    public void setAllback(String allback) {
        this.allback = allback;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    // gson转换
    public static WhileLineInfo fromJSON(String JSONString) {
        if (JSONString == null)
            return null;
        return JSONUtil.fromJson(JSONString, WhileLineInfo.class);
    }
}
