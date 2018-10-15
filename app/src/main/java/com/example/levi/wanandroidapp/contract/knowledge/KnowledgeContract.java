package com.example.levi.wanandroidapp.contract.knowledge;

import com.example.levi.wanandroidapp.base.presenter.AbsPresenter;
import com.example.levi.wanandroidapp.base.view.AbstractView;
import com.example.levi.wanandroidapp.data.knowledge.KnowledgeListBean;

import java.util.List;

/**
 * Author: Levi
 * CreateDate: 2018/10/15 15:37
 */
public class KnowledgeContract {
    public interface View extends AbstractView {

        void getKnowledgeListOk(List<KnowledgeListBean> dataBean, boolean isRefresh);

        void getKnowledgeListErr(String info);

    }

    public interface Presenter extends AbsPresenter<View> {

        void autoRefresh();

        void loadMore();

        void getKnowledgeList();

    }
}
