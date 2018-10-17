package com.example.levi.wanandroidapp.contract.collection;

import com.example.levi.wanandroidapp.base.presenter.AbsPresenter;
import com.example.levi.wanandroidapp.base.view.AbstractView;
import com.example.levi.wanandroidapp.data.collection.CollectionListBean;

/**
 * Author: Levi
 * CreateDate: 2018/10/17 18:51
 */
public class CollectionContract {
    public interface View extends AbstractView {

        void getCollectListOk(CollectionListBean collectionListBean, boolean isRefresh);

        void getCollectListErr(String info);

        void cancelCollectOk();

        void cancelCollectErr(String info);

    }

    public interface Presenter extends AbsPresenter<View> {

        void autoRefresh();

        void loadMore();

        void getCollectList(int page);

        void cancelCollect(int id);
    }
}
