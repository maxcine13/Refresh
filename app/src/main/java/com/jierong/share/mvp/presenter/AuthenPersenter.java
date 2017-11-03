package com.jierong.share.mvp.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.jierong.share.mvp.model.IAuthenModel;
import com.jierong.share.mvp.model.impl.AuthenModel;
import com.jierong.share.mvp.model.info.MaInfo;
import com.jierong.share.mvp.view.IAuthenView;
import com.jierong.share.okhttp.HttpStringCallBack;
import com.jierong.share.util.LogUtil;
import com.jierong.share.util.StringUtil;
import java.util.regex.Pattern;

public class AuthenPersenter extends BasePresenter {
    private IAuthenModel iAuthenModel;
    private IAuthenView iAuthenView;
    private Context context;

    public AuthenPersenter(IAuthenView view) {
        this.iAuthenView = view;
        this.iAuthenModel = new AuthenModel();
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
     * 检查达人认证状态
     */
    public void check() {
        if (!isNetworkConnected()) {
            iAuthenView.showError("网络连接错误，请检查网络!", false);
            return;
        }

        iAuthenView.showLoading();
        iAuthenModel.check(getGlobalId(), getGlobalToken(), new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                iAuthenView.hideLoading();
                MaInfo info = (MaInfo) result;
                iAuthenView.authenSuccess(info);
            }

            @Override
            public void onFailure(int code, String msg) {
                LogUtil.e(code + "_" + msg);
                if(code == 9876) {
                    iAuthenView.authenFail();
                } else {
                    iAuthenView.showError(msg, false);
                }
                iAuthenView.hideLoading();
            }
        });
    }

    /**
     * 提交审核
     */
    public void examine() {
        if (!isNetworkConnected()) {
            iAuthenView.showError("网络连接错误，请检查网络!", false);
            return;
        }

        String ID = iAuthenView.getUID();
        String name = iAuthenView.getUName();
        if (StringUtil.isEmptyIgnoreBlank(name)) {
            iAuthenView.showError("请填写您的姓名!", false);
            return;
        }
        if (StringUtil.isEmptyIgnoreBlank(ID)) {
            iAuthenView.showError("请填写您的身份证号！", false);
            return;
        } else {
            if (!isIDCard(ID)) {
                iAuthenView.showTips("身份证格式错误！请核实您的身份证信息。");
                return;
            }
        }
        if (!iAuthenView.getCheck()) {
            iAuthenView.showError("您必须同意《分享赚客达人条约》！", false);
            return;
        }

        iAuthenView.showLoading();
        iAuthenModel.examine(getGlobalId(), getGlobalToken(), iAuthenView.getFlag(), name, ID, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                iAuthenView.hideLoading();
                iAuthenView.receiveData(result.toString());
            }

            @Override
            public void onFailure(int code, String msg) {
                iAuthenView.hideLoading();
                iAuthenView.showError(msg, false);
            }
        });
    }

    /**
     * 3.判断字符串是否是银行卡
     * 判断是否是银行卡号
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
    public static final String REGEX_ID_CARD = "(^\\d{15}$)|(^\\d{17}([0-9]|X|x)$)";

    /**
     * 校验身份证
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
        iAuthenModel.closeHttp();
        context = null;
    }
}
