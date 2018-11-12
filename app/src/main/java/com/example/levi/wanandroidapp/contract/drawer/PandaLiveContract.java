package com.example.levi.wanandroidapp.contract.drawer;

import com.example.levi.wanandroidapp.base.presenter.AbsPresenter;
import com.example.levi.wanandroidapp.base.view.AbstractView;

public class PandaLiveContract {
    public interface View extends AbstractView {
        void getLiveUrlSuccess(String url);

        void getLiveUrlError(String errMsg);
    }

    public interface Presenter extends AbsPresenter<View> {

        void getLiveUrl(int rid);

    }
}
