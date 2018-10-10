package com.example.levi.wanandroidapp.contract.login;

import com.example.levi.wanandroidapp.base.presenter.AbsPresenter;
import com.example.levi.wanandroidapp.base.view.AbstractView;
import com.example.levi.wanandroidapp.data.login.UserInfo;

/**
 * Author: Levi
 * CreateDate: 2018/10/10 16:05
 */
public class RegisterContract {
    public interface IView extends AbstractView {

        void registerOk(UserInfo userInfo);

        void registerErr(String info);

    }

    public interface Presenter extends AbsPresenter<IView> {

        void register(String name, String password, String confirmPassword);

    }
}
