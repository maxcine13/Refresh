package com.jierong.share.mvp.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.jierong.share.Constants;
import com.jierong.share.mvp.model.IActivationModel;
import com.jierong.share.mvp.model.impl.ActivationModel;
import com.jierong.share.mvp.view.IAcitvationView;
import com.jierong.share.okhttp.HttpStringCallBack;
import com.jierong.share.util.StringUtil;
import java.util.regex.Pattern;

/**
 * 激活白条额度管理者
 */

public class ActivationPersenter extends BasePresenter {
    private IActivationModel mIActivationModel;
    private IAcitvationView mIAcitvationView;
    private Context context;

    public ActivationPersenter(IAcitvationView view) {
        this.mIAcitvationView = view;
        this.mIActivationModel = new ActivationModel();
        this.context = view.getMContext();

    }

    // 判断网络是否连接
    private boolean isNetworkConnected() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        return mNetworkInfo != null && mNetworkInfo.isAvailable();
    }

    /**
     * 激活额度
     */
    public void activationMethod() {
        if (!isNetworkConnected()) {
            mIAcitvationView.showError("网络连接错误，请检查网络!", false);
            return;
        }
        String bankcard = mIAcitvationView.getUCard();
        String ID = mIAcitvationView.getUID();
        String name = mIAcitvationView.getUName();
        String phone = mIAcitvationView.getUPhone();
        if (StringUtil.isEmptyIgnoreBlank(name)) {
            mIAcitvationView.showError("请填写您的姓名!", false);
            return;
        }

        if (StringUtil.isEmptyIgnoreBlank(ID)) {
            mIAcitvationView.showError("请填写您的身份证号！", false);
            return;
        } else {
            if (!isIDCard(ID)) {
                mIAcitvationView.showError("身份证格式错误！请核实您的身份证信息。", false);
                return;
            }
        }
        if (StringUtil.isEmptyIgnoreBlank(bankcard)) {
            mIAcitvationView.showError("请填写您的银行卡号码！", false);
            return;
        } else {
            if (!checkBankCard(bankcard)) {
                mIAcitvationView.showError("银行卡格式错误！请核实您的银行卡信息。", false);
                return;
            }
        }
        if (StringUtil.isEmptyIgnoreBlank(phone)) {
            mIAcitvationView.showError("请填写您的银行卡预留手机号码！", false);
            return;
        } else {
            if (!StringUtil.isMobile(phone)) {
                mIAcitvationView.showError("手机号码格式错误！请核实您的手机号码。", false);
                return;
            }
        }

        if (!mIAcitvationView.getCheck()) {
            mIAcitvationView.showError("您必须同意《分享赚客白条条约》！", false);
            return;
        }
        mIAcitvationView.showLoading();
        mIActivationModel.activationMethod(getGlobalId(), getGlobalToken(), name, ID, bankcard, phone, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                mIAcitvationView.hideLoading();
                mIAcitvationView.receiveData(result.toString());
            }
            @Override
            public void onFailure(int code, String msg) {
                mIAcitvationView.hideLoading();
                mIAcitvationView.showError(msg, false);
            }
        });

    }

    /**
     * 3.判断字符串是否是银行卡
     * 判断是否是银行卡号
     *
     * @param cardId
     * @return
     */
    public static boolean checkBankCard(String cardId) {
        char bit = getBankCardCheckCode(cardId
                .substring(0, cardId.length() - 1));
        if (bit == 'N') {
            return false;
        }
        return cardId.charAt(cardId.length() - 1) == bit;

    }

    private static char getBankCardCheckCode(String nonCheckCodeCardId) {
        if (nonCheckCodeCardId == null
                || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
            // 如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }

    /**
     * 正则表达式:验证身份证
     */
    public static final String REGEX_ID_CARD = "(^\\d{15}$)|(^\\d{17}([0-9]|X)$)";

    /**
     * 校验身份证
     *
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIDCard(String idCard) {
        return Pattern.matches(REGEX_ID_CARD, idCard);
    }

    /**
     * 关闭网络请求
     */
    public void closeHttp() {
        mIActivationModel.closeHttp();
        context = null;

    }
}
