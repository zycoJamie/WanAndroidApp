package com.example.levi.wanandroidapp.presenter.drawer;

import android.util.ArrayMap;

import com.example.levi.wanandroidapp.base.presenter.BasePresenter;
import com.example.levi.wanandroidapp.contract.drawer.LiveListContract;
import com.example.levi.wanandroidapp.model.api.ApiService;
import com.example.levi.wanandroidapp.model.api.ApiStore;
import com.example.levi.wanandroidapp.util.app.LogUtil;
import com.example.levi.wanandroidapp.util.network.RxUtil;

import java.util.Map;

import javax.inject.Inject;


public class LiveListPresenter extends BasePresenter<LiveListContract.View> implements LiveListContract.Presenter {

    private static final String TAG = LiveListPresenter.class.getSimpleName();
    private String mPage = "1";
    private boolean isRefresh = true;

    @Inject
    public LiveListPresenter() {
    }

    @Override
    public void autoRefresh(String cate) {
        isRefresh = true;
        mPage = "1";
        getLiveList(cate);
    }

    @Override
    public void loadMore(String cate) {
        isRefresh = false;
        mPage = String.valueOf(Integer.parseInt(mPage) + 1);
        getLiveList(cate);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void getLiveList(String cate) {
        Map<String, String> map = getCommonParams(cate);
        mCompositeDisposable.add(ApiStore.createApi(ApiService.class)
                .getLiveList(map)
                .compose(RxUtil.rxSchedulerHelper())
                .subscribe(listBaseResponse -> mView.getLiveListOk(listBaseResponse.getData(), isRefresh), throwable -> mView.getLiveListErr(throwable.getMessage())));
    }

    /**
     * 构造参数列表
     */
    private Map getCommonParams(String cate) {
        Map<String, String> params = new ArrayMap<>();
        params.put("cate", cate);
        params.put("needFilterMachine", "1");
        params.put("pageno", mPage);
        params.put("pagenum", "40");
        params.put("__plat", "android");
        params.put("__version", "4.0.32.7735");
        params.put("__channel", "meizu");
        return params;
    }
}
