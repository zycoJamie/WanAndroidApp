package com.example.levi.wanandroidapp.contract.main;

import com.example.levi.wanandroidapp.base.presenter.AbsPresenter;
import com.example.levi.wanandroidapp.base.view.AbstractView;
import com.example.levi.wanandroidapp.data.main.SearchHotWordBean;

import java.util.List;


/**
 * Author: Levi
 * CreateDate: 2018/10/20 15:51
 */
public class SearchContract {
    public interface View extends AbstractView {

        void getSearchHotOk(List<SearchHotWordBean> dataBean);
        void getSearchHotError(String info);

    }

    public interface Presenter extends AbsPresenter<View> {

        void getSearchHotWord();

    }
}
