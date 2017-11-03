package com.jierong.share.mvp.model;

import com.jierong.share.okhttp.HttpStringCallBack;

/**
 * 绑定手机界面数据接口
 */
public interface IActivationModel extends ModelListener {


    /** 激活白条额度
     * @param uid       用户id
     * @param token     用户唯一标识
     * @param name      用户姓名
     * @param idcard    用户身份证号
     * @param bankcard  用户银行卡号
     * @param tel       用户银行卡号所绑定的手机号码
     * @param mCallBack
     */
      void activationMethod(String uid, String token, String name, String idcard, String bankcard, String tel, HttpStringCallBack mCallBack);

}
