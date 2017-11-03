package com.jierong.share.mvp.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.jierong.share.mvp.model.ITaoBaoModel;
import com.jierong.share.mvp.model.impl.TaoBaoModel;
import com.jierong.share.mvp.model.info.TaoBaoInfo;
import com.jierong.share.mvp.view.ICzflView;
import com.jierong.share.okhttp.HttpStringCallBack;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;

/**
 * 超值返利界面管理者
 */
public class CzflListPresenter extends BasePresenter {
    private ICzflView mICzflView;
    private ITaoBaoModel mITaoBaoModel;
    private Context mContext;

    public CzflListPresenter(ICzflView view) {
        this.mContext = view.getMContext();
        this.mICzflView = view;
        this.mITaoBaoModel = new TaoBaoModel();
    }

    // 判断网络是否连接
    private boolean isNetworkConnected() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager)
                mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        return mNetworkInfo != null && mNetworkInfo.isAvailable();
    }

    /**
     * 获取列表数据
     */
    public void loadData() {
        if(!isNetworkConnected()) {
            mICzflView.showError("网络连接错误，请检查网络!", false);
            return;
        }

        final int page = mICzflView.getPage();
        int sort = mICzflView.getSort();
        mICzflView.showLoading();
        mITaoBaoModel.loadCzflData(page, sort, getGlobalId(), getGlobalToken(),
                new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                mICzflView.hideLoading();
                try {
                    JSONObject all = new JSONObject(String.valueOf(result));
                    if(page == 1 ) {
                        String img = String.valueOf(all.opt("banner"));
                        List<TaoBaoInfo> data = TaoBaoInfo.fromJSONS(String.valueOf(all.opt("valuer")));
                        mICzflView.refreshSuccess(data, img);
                    } else {
                        List<TaoBaoInfo> data = TaoBaoInfo.fromJSONS(String.valueOf(all.opt("valuer")));
                        mICzflView.loadSuccess(data);
                    }
                } catch (JSONException e) {
                    mICzflView.showError("json转换异常", false);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                mICzflView.showError(msg, false);
                mICzflView.hideLoading();
            }
        });
    }

    /**
     * 关闭网络请求
     */
    public void closeHttp() {
        mITaoBaoModel.closeHttp();
        mContext = null;
    }

}
