package com.example.levi.wanandroidapp.presenter.drawer;

import com.example.levi.wanandroidapp.base.presenter.BasePresenter;
import com.example.levi.wanandroidapp.contract.drawer.PandaLiveContract;
import com.example.levi.wanandroidapp.data.drawer.StreamUrl;
import com.example.levi.wanandroidapp.model.api.ApiService;
import com.example.levi.wanandroidapp.model.api.ApiStore;
import com.example.levi.wanandroidapp.model.api.BaseResponse;
import com.example.levi.wanandroidapp.util.network.RxUtil;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class PandaLivePresenter extends BasePresenter<PandaLiveContract.View> implements PandaLiveContract.Presenter {
    @Inject
    public PandaLivePresenter() {

    }

    @Override
    public void getLiveUrl(int rid) {
        Map<String, String> map = new HashMap<>();
        map.put("hostid", String.valueOf(rid));
        map.put("pageno", "1");
        map.put("pagenum", "10");
        map.put("isinfo", "1");
        map.put("__version", "4.0.32.7735");
        map.put("__plat", "android");
        map.put("__channel", "meizu");
        mCompositeDisposable.add(ApiStore.createApi(ApiService.class)
                .getLiveUrl(map)
                .compose(RxUtil.rxSchedulerHelper())
                .subscribe(streamUrlBaseResponse -> {
                    StreamUrl streamUrl = null;
                    if (streamUrlBaseResponse != null) {
                        streamUrl = streamUrlBaseResponse.getData();
                    }
                    if (streamUrl != null) {
                        String url = streamUrl.getItems().get(0).getV_url();
                        mView.getLiveUrlSuccess(url);
                    } else {
                        if (streamUrlBaseResponse != null) {
                            mView.getLiveUrlError(streamUrlBaseResponse.getErrorMsg());
                        }
                    }
                }, throwable -> mView.getLiveUrlError(throwable.getMessage())));
    }
}
