package com.example.levi.wanandroidapp.contract.drawer;

import com.example.levi.wanandroidapp.base.presenter.AbsPresenter;
import com.example.levi.wanandroidapp.base.view.AbstractView;
import com.example.levi.wanandroidapp.data.drawer.TypeTitle;

import java.util.List;

public class VideoContract {
    public interface View extends AbstractView {

        void getLiveTitleOk(List<TypeTitle> typeTitles);

        void getLiveTitleErr(String info);

    }

    public interface Presenter extends AbsPresenter<View> {

        void getLiveTitle();

    }
}
