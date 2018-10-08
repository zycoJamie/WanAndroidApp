package com.example.levi.wanandroidapp.base.fragment;

import android.view.View;
import android.view.ViewGroup;

import com.example.levi.wanandroidapp.R;
import com.example.levi.wanandroidapp.base.presenter.BasePresenter;

public abstract class BaseRootFragment<T extends BasePresenter> extends BaseFragment<T> {
    public static final int NORMAL_STATE = 0;
    public static final int LOADING_STATE = 1;
    public static final int ERROR_STATE = 2;
    public static final int EMPTY_STATE = 3;

    private View mErrorView;
    private View mLoadingView;
    private View mEmptyView;
    private ViewGroup mNormalView;
    private int mCurrentState = NORMAL_STATE;

    @Override
    protected void initUI() {
        if (getView() == null) {
            return;
        }
        mNormalView=getView().findViewById(R.id.normal_view);
        if (mNormalView == null) {
            throw new IllegalStateException("the subclass of BaseRootFragment need a View of \"normal_view\"");
        }
        if (!(mNormalView.getParent() instanceof ViewGroup)) {
            throw new IllegalStateException("\"normal_view\" must inherit ViewGroup");
        }
        ViewGroup parent = (ViewGroup) mNormalView.getParent();
        View.inflate(mActivity, R.layout.view_loading, parent);
        View.inflate(mActivity, R.layout.view_error, parent);
        View.inflate(mActivity, R.layout.view_empty, parent);
        mLoadingView = parent.findViewById(R.id.rl_loading_view);
        mErrorView = parent.findViewById(R.id.rl_error_view);
        mEmptyView = parent.findViewById(R.id.rl_empty_view);
        mErrorView.setOnClickListener(v -> reload());
        mLoadingView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.GONE);
        mNormalView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNormal() {
        if (mCurrentState == NORMAL_STATE) {
            return;
        }
        hideCurrentView();
        mNormalView.setVisibility(View.VISIBLE);
        mCurrentState = NORMAL_STATE;
    }

    @Override
    public void showError() {
        if (mCurrentState == ERROR_STATE) {
            return;
        }
        hideCurrentView();
        mErrorView.setVisibility(View.VISIBLE);
        mCurrentState = ERROR_STATE;
    }

    @Override
    public void showLoading() {
        if (mCurrentState == LOADING_STATE) {
            return;
        }
        hideCurrentView();
        mLoadingView.setVisibility(View.VISIBLE);
        mCurrentState = LOADING_STATE;
    }

    protected void hideCurrentView() {
        switch (mCurrentState) {
            case NORMAL_STATE: {
                if (mNormalView == null) {
                    return;
                }
                mNormalView.setVisibility(View.GONE);
                break;
            }
            case LOADING_STATE: {
                mLoadingView.setVisibility(View.GONE);
                break;
            }
            case ERROR_STATE: {
                mErrorView.setVisibility(View.GONE);
                break;
            }
            case EMPTY_STATE: {
                mEmptyView.setVisibility(View.GONE);
                break;
            }
            default:
                break;
        }
    }

    @Override
    public void showEmpty() {
        if (mCurrentState == EMPTY_STATE) {
            return;
        }
        hideCurrentView();
        mEmptyView.setVisibility(View.VISIBLE);
        mCurrentState = EMPTY_STATE;
    }
}
