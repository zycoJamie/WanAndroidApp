package com.example.levi.wanandroidapp.ui.login;


import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.example.levi.wanandroidapp.R;
import com.example.levi.wanandroidapp.base.activity.BaseActivity;
import com.example.levi.wanandroidapp.contract.login.LoginContract;
import com.example.levi.wanandroidapp.data.login.UserInfo;
import com.example.levi.wanandroidapp.model.constant.Constant;
import com.example.levi.wanandroidapp.model.constant.EventConstant;
import com.example.levi.wanandroidapp.model.constant.MessageEvent;
import com.example.levi.wanandroidapp.presenter.login.LoginPresenter;
import com.example.levi.wanandroidapp.util.app.SharedPreferenceUtil;
import com.example.levi.wanandroidapp.util.app.SkipUtil;
import com.example.levi.wanandroidapp.util.app.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.IView {

    @BindView(R.id.tb_common)
    Toolbar mToolbar;
    @BindView(R.id.et_account)
    EditText mAccountEt;
    @BindView(R.id.et_pwd)
    EditText mPwdEt;

    private String username;
    private String password;

    @Override
    protected void initInject() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initUI() {

    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(getString(R.string.login_tool_bar_title));
        mToolbar.setNavigationOnClickListener(v -> finish());

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.btn_login, R.id.tv_register})
    void click(View view) {
        switch (view.getId()) {
            case R.id.tv_register: {
                SkipUtil.overlay(mContext, RegisterActivity.class);
                break;
            }
            case R.id.btn_login: {
                if (checkInfo()) {
                    mPresenter.login(username, password);
                }
                break;
            }
        }
    }

    /**
     * 验证用户名和密码
     */
    private boolean checkInfo() {
        username = mAccountEt.getText().toString().trim();
        password = mPwdEt.getText().toString().trim();
        Pattern pattern = Pattern.compile("[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]");
        if (pattern.matcher(username).find()) {
            ToastUtil.show(mContext, getString(R.string.login_account_illegal));
            return false;
        } else if (pattern.matcher(password).find()) {
            ToastUtil.show(mContext, getString(R.string.login_password_illegal));
            return false;
        }
        if (username.length() < 6 || password.length() < 6) {
            ToastUtil.show(mContext, getString(R.string.login_length_illegal));
            return false;
        }
        return true;
    }

    @Override
    public void loginOK(UserInfo userInfo) {
        ToastUtil.show(mContext, getString(R.string.login_success));
        SharedPreferenceUtil.put(mContext, Constant.USERNAME, userInfo.getUsername());
        SharedPreferenceUtil.put(mContext, Constant.PASSWORD, userInfo.getPassword());
        SharedPreferenceUtil.put(mContext, Constant.ISLOGIN, true);
        EventBus.getDefault().post(new MessageEvent(EventConstant.LOGIN_SUCCESS, ""));
        finish();
    }

    @Override
    public void loginErr(String info) {
        ToastUtil.show(mContext, info);
    }
}
