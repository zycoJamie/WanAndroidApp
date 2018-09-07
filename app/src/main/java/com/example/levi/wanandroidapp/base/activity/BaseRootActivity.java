package com.example.levi.wanandroidapp.base.activity;

import android.view.View;
import android.view.ViewGroup;

import com.example.levi.wanandroidapp.R;
import com.example.levi.wanandroidapp.base.presenter.AbsPresenter;

/**
 * Created by zyco
 * on 2018/9/7
 */
public abstract class BaseRootActivity<T extends AbsPresenter> extends BaseActivity<T> {
    public static final int NORMAL_STATE=0;
    public static final int LOADING_STATE=1;
    public static final int ERROR_STATE=2;
    public static final int EMPTY_STATE=3;

    private int mCurrentState=NORMAL_STATE;
    private View mErrorView;
    private View mLoadingView;
    private View mEmptyView;
    private ViewGroup mNormalView;


    @Override
    protected void initUI() {
        if(mActivity==null){
            return;
        }
        /*mNormalView=findViewById(R.id.normal_view);*/
        if(mNormalView==null){
            throw new IllegalStateException("the subclass of BaseRootActivity need a View of \"normal_view\" ");
        }
        if(!(mNormalView.getParent() instanceof ViewGroup)){
            throw new IllegalStateException("\"normal_view\" must inherit ViewGroup");
        }
        ViewGroup parent= (ViewGroup) mNormalView.getParent();


    }

    @Override
    public void showNormal() {
        super.showNormal();
    }

    @Override
    public void showError() {
        super.showError();
    }

    @Override
    public void showLoading() {
        super.showLoading();
    }

    @Override
    public void showEmpty() {
        super.showEmpty();
    }
}
