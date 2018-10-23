package com.example.levi.wanandroidapp.contract.main;

import com.example.levi.wanandroidapp.base.presenter.AbsPresenter;
import com.example.levi.wanandroidapp.base.view.AbstractView;
import com.example.levi.wanandroidapp.data.main.HomePageArticleBean;

/**
 * Author: Levi
 * CreateDate: 2018/10/22 16:00
 */
public class SearchResultContract {
    public interface View extends AbstractView {

        void getSearchResultOk(HomePageArticleBean searchResult, boolean isRefresh);

        void getSearchResultErr(String info);

        void collectArticleOK(String info);

        void collectArticleErr(String info);

        void cancelCollectArticleOK(String info);

        void cancelCollectArticleErr(String info);

    }

    public interface Presenter extends AbsPresenter<View> {

        void autoRefresh(String key);

        void loadMore(String key);

        void getSearchResult(int page, String key);

        void collectArticle(int id);

        void cancelCollectArticle(int id);

    }
}
