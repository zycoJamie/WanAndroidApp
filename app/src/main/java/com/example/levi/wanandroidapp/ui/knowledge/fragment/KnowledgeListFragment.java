package com.example.levi.wanandroidapp.ui.knowledge.fragment;

import com.example.levi.wanandroidapp.base.fragment.BaseRootFragment;
import com.example.levi.wanandroidapp.contract.knowledge.KnowledgeClassifyContract;
import com.example.levi.wanandroidapp.data.knowledge.KnowledgeClassifyListBean;
import com.example.levi.wanandroidapp.presenter.knowledge.KnowledgeClassifyPresenter;

/**
 * Author: Levi
 * CreateDate: 2018/10/13 18:06
 */
public class KnowledgeListFragment extends BaseRootFragment<KnowledgeClassifyPresenter> implements KnowledgeClassifyContract.View{

    @Override
    public int getLayoutResID() {
        return 0;
    }

    @Override
    protected void initUI() {
        super.initUI();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initInject() {
        super.initInject();
    }

    @Override
    public void getKnowledgeClassifyListOk(KnowledgeClassifyListBean knowledgeClassifyListBean, boolean isRefresh) {

    }

    @Override
    public void getKnowledgeClassifyListErr(String info) {

    }

    @Override
    public void collectArticleOK(String info) {

    }

    @Override
    public void collectArticleErr(String info) {

    }

    @Override
    public void cancelCollectArticleOK(String info) {

    }

    @Override
    public void cancelCollectArticleErr(String info) {

    }
}
