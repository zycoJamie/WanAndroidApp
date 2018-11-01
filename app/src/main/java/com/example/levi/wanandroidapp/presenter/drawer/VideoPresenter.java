package com.example.levi.wanandroidapp.presenter.drawer;


import com.example.levi.wanandroidapp.base.presenter.BasePresenter;
import com.example.levi.wanandroidapp.contract.drawer.VideoContract;
import com.example.levi.wanandroidapp.model.api.ApiService;
import com.example.levi.wanandroidapp.model.api.ApiStore;
import com.example.levi.wanandroidapp.util.network.RxUtil;


import javax.inject.Inject;


public class VideoPresenter extends BasePresenter<VideoContract.View> implements VideoContract.Presenter {

    @Inject
    public VideoPresenter() {
    }


    @Override
    public void getLiveTitle() {
        mCompositeDisposable.add(ApiStore.createApi(ApiService.class)
                .getLiveType("category.alllist", "android", "4.0.32.7735", "meizu")
                .compose(RxUtil.rxSchedulerHelper())
                .subscribe(listBaseResponse -> mView.getLiveTitleOk(listBaseResponse.getData()), throwable -> mView.getLiveTitleErr(throwable.getMessage())));
    }
}
