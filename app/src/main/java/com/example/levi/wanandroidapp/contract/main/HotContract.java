package com.example.levi.wanandroidapp.contract.main;

import com.example.levi.wanandroidapp.base.presenter.AbsPresenter;
import com.example.levi.wanandroidapp.base.view.AbstractView;
import com.example.levi.wanandroidapp.data.main.HotBean;

import java.util.List;

/**
 * Author: Levi
 * CreateDate: 2018/10/19 13:56
 */
public class HotContract {
    public interface View extends AbstractView {

        void getHotWebOk(List<HotBean> dataBean);

        void getHotWebErr(String info);

    }

    public interface Presenter extends AbsPresenter<View> {

        void getHotWeb();

    }
}
