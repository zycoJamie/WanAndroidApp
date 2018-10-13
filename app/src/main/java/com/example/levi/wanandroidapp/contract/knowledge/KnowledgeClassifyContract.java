package com.example.levi.wanandroidapp.contract.knowledge;

import com.example.levi.wanandroidapp.base.presenter.AbsPresenter;
import com.example.levi.wanandroidapp.base.view.AbstractView;
import com.example.levi.wanandroidapp.data.knowledge.KnowledgeClassifyListBean;

/**
 * Author: Levi
 * CreateDate: 2018/10/13 18:09
 */
public class KnowledgeClassifyContract {
    public interface View extends AbstractView {

        void getKnowledgeClassifyListOk(KnowledgeClassifyListBean knowledgeClassifyListBean, boolean isRefresh);

        void getKnowledgeClassifyListErr(String info);

        void collectArticleOK(String info);

        void collectArticleErr(String info);

        void cancelCollectArticleOK(String info);

        void cancelCollectArticleErr(String info);

    }

    public interface Presenter extends AbsPresenter<View> {

        void autoRefresh();

        void loadMore();

        void getKnowledgeClassifyList(int page,int id);

        void collectArticle(int id);

        void cancelCollectArticle(int id);

    }
}
