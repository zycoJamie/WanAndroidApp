package com.example.levi.wanandroidapp.contract.drawer;

import com.example.levi.wanandroidapp.base.presenter.AbsPresenter;
import com.example.levi.wanandroidapp.base.view.AbstractView;
import com.example.levi.wanandroidapp.data.drawer.LiveList;

import java.util.List;

public class LiveListContract {
    public interface View extends AbstractView {

        void getLiveListOk(LiveList list, boolean isRefresh);

        void getLiveListErr(String info);

    }

    public interface Presenter extends AbsPresenter<View> {

        void autoRefresh(String cate);

        void loadMore(String cate);

        void getLiveList(String cate);

    }
}
