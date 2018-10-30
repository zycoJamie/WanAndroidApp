package com.example.levi.wanandroidapp.presenter.drawer;

import com.example.levi.wanandroidapp.base.presenter.BasePresenter;
import com.example.levi.wanandroidapp.contract.drawer.VideoContract;

import javax.inject.Inject;

public class VideoPresenter extends BasePresenter<VideoContract.View> implements VideoContract.Presenter {

    @Inject
    public VideoPresenter() {
    }


    @Override
    public void getLiveTitle() {

    }
}
