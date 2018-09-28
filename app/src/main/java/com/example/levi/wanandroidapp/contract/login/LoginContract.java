package com.example.levi.wanandroidapp.contract.login;

import com.example.levi.wanandroidapp.base.presenter.AbsPresenter;
import com.example.levi.wanandroidapp.base.view.AbstractView;
import com.example.levi.wanandroidapp.data.login.UserInfo;

public class LoginContract {
    public interface IView extends AbstractView {
        void loginOK(UserInfo userInfo);

        void loginErr(String info);
    }

    public interface Presenter extends AbsPresenter<IView> {
        void login(String name, String password);
    }
}
