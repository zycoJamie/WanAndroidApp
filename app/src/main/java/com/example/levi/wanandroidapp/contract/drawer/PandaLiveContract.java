package com.example.levi.wanandroidapp.contract.drawer;

import com.example.levi.wanandroidapp.base.presenter.AbsPresenter;
import com.example.levi.wanandroidapp.base.view.AbstractView;

public class PandaLiveContract {
    public interface View extends AbstractView {

        void getLiveUrlOk(String liveUrl);

        void getLiveUrlErr(String info);

    }

    public interface Presenter extends AbsPresenter<View> {

        void getLiveUrl(String roomId);

    }
}
