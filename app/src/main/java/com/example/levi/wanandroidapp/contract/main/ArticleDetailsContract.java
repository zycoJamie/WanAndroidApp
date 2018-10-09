package com.example.levi.wanandroidapp.contract.main;

import com.example.levi.wanandroidapp.base.presenter.AbsPresenter;
import com.example.levi.wanandroidapp.base.view.AbstractView;

/**
 * Author: Levi
 * CreateDate: 2018/10/9 11:42
 */
public class ArticleDetailsContract {

    public interface IView extends AbstractView {
        void collectArticleOK(String info);

        void collectArticleErr(String info);

        void cancelCollectArticleOK(String info);

        void cancelCollectArticleErr(String info);
    }

    public interface Presenter extends AbsPresenter<IView> {
        void collectArticle(int id);

        void cancelCollectArticle(int id);
    }
}
