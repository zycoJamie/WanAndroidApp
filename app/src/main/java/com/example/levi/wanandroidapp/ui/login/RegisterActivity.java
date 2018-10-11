package com.example.levi.wanandroidapp.ui.login;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.example.levi.wanandroidapp.R;
import com.example.levi.wanandroidapp.base.activity.BaseActivity;
import com.example.levi.wanandroidapp.contract.login.RegisterContract;
import com.example.levi.wanandroidapp.data.login.UserInfo;
import com.example.levi.wanandroidapp.model.constant.Constant;
import com.example.levi.wanandroidapp.presenter.login.RegisterPresenter;
import com.example.levi.wanandroidapp.util.app.SkipUtil;
import com.example.levi.wanandroidapp.util.app.ToastUtil;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author: Levi
 * CreateDate: 2018/10/10 16:03
 */
public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterContract.IView {

    @BindView(R.id.tb_common)
    Toolbar mToolbar;
    @BindView(R.id.et_account)
    EditText mAccountEt;
    @BindView(R.id.et_pwd)
    EditText mPwdEt;
    @BindView(R.id.et_pwd_confirm)
    EditText mConfirmEt;

    private String username;
    private String password;
    private String confirmPWD;

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(getString(R.string.register_tool_bar_title));
        mToolbar.setNavigationOnClickListener(v -> finish());
    }

    @Override
    protected void initUI() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initInject() {
        super.initInject();
        mActivityComponent.inject(this);
    }

    @OnClick({R.id.btn_register})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.btn_register: {
                if (checkInfo()) {
                    mPresenter.register(username, password, confirmPWD);
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
        confirmPWD = mConfirmEt.getText().toString().trim();
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
        if (!TextUtils.equals(password, confirmPWD)) {
            ToastUtil.show(mContext, getString(R.string.register_password_is_different_from_confirm_password));
            return false;
        }
        return true;
    }


    @Override
    public void registerOk(UserInfo userInfo) {
        ToastUtil.show(mContext, getString(R.string.register_success));
        Bundle bundle=new Bundle();
        bundle.putString(Constant.EXTRA_USERNAME,userInfo.getUsername());
        bundle.putString(Constant.EXTRA_PASSWORD,userInfo.getPassword());
        SkipUtil.overlay(mContext, LoginActivity.class,bundle);
        finish();
    }

    @Override
    public void registerErr(String info) {
        ToastUtil.show(mContext, getString(R.string.register_error).concat("\n").concat(info));
    }
}
