package com.example.levi.wanandroidapp.ui.main.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.example.levi.wanandroidapp.R;
import com.example.levi.wanandroidapp.base.activity.BaseRootActivity;
import com.example.levi.wanandroidapp.contract.main.ArticleDetailsContract;
import com.example.levi.wanandroidapp.model.constant.Constant;
import com.example.levi.wanandroidapp.model.constant.EventConstant;
import com.example.levi.wanandroidapp.model.constant.MessageEvent;
import com.example.levi.wanandroidapp.presenter.main.ArticleDetailsPresenter;
import com.example.levi.wanandroidapp.ui.login.LoginActivity;
import com.example.levi.wanandroidapp.util.app.LogUtil;
import com.example.levi.wanandroidapp.util.app.SharedPreferenceUtil;
import com.example.levi.wanandroidapp.util.app.SkipUtil;
import com.example.levi.wanandroidapp.util.app.ToastUtil;
import com.just.agentweb.AgentWeb;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import butterknife.BindView;

/**
 * Author: Levi
 * CreateDate: 2018/10/9 11:40
 */
public class ArticleDetailsActivity extends BaseRootActivity<ArticleDetailsPresenter> implements ArticleDetailsContract.IView {

    private static final String TAG = ArticleDetailsActivity.class.getSimpleName();
    @BindView(R.id.tb_common)
    Toolbar mToolbar;
    @BindView(R.id.fl_article_detail_web_view)
    FrameLayout mContainerFl;

    private String mTitle;
    private String mArticleLink;
    private int mArticleId;
    private boolean isCollect;
    private AgentWeb mAgentWeb;

    @Override
    protected void onPause() {
        super.onPause();
        mAgentWeb.getWebLifeCycle().onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAgentWeb.getWebLifeCycle().onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAgentWeb.getWebLifeCycle().onDestroy();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_article_details;
    }

    /**
     * 配置webview
     */
    @SuppressWarnings("all")
    @Override
    protected void initData() {
        if (mTitle != null && mArticleLink != null) {
            mAgentWeb = AgentWeb.with(this)
                    .setAgentWebParent(mContainerFl, new FrameLayout.LayoutParams(-1, -1))
                    .useDefaultIndicator()
                    .createAgentWeb()
                    .ready()
                    .go(mArticleLink);
            WebView mWebView = mAgentWeb.getWebCreator().getWebView();
            WebSettings mSettings = mWebView.getSettings();
            mSettings.setJavaScriptEnabled(true);
            mSettings.setSupportZoom(true);
            mSettings.setBuiltInZoomControls(true);
            mSettings.setDisplayZoomControls(false);
            mSettings.setUseWideViewPort(true);
            mSettings.setLoadWithOverviewMode(true);
        }
    }

    @Override
    protected void initUI() {

    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        getBundleData();
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(mTitle);
        mToolbar.setNavigationOnClickListener(v -> onBackPressedSupport());
    }

    /**
     * 获取从上一个界面传递过来的extra data
     */
    private void getBundleData() {
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        mTitle = bundle.getString(Constant.ARTICLE_TITLE);
        mArticleLink = bundle.getString(Constant.ARTICLE_LINK);
        mArticleId = bundle.getInt(Constant.ARTICLE_ID, -1);
        isCollect = bundle.getBoolean(Constant.ARTICLE_IS_COLLECT);
    }

    @Override
    protected void initInject() {
        super.initInject();
        mActivityComponent.inject(this);
    }

    @Override
    public void initBind() {
        super.initBind();
    }

    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
    }

    /**
     * 创建toolbar menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_article_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressWarnings("all")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_article_share: {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "【Android】祝梓心水推荐的技术文章《" + mTitle + "》 " + mArticleLink);
                startActivity(Intent.createChooser(intent, "分享快乐"));
                break;
            }
            case R.id.menu_article_collect: {
                if ((Boolean) SharedPreferenceUtil.get(mContext, Constant.ISLOGIN, false)) {
                    if (isCollect) {
                        mPresenter.cancelCollectArticle(mArticleId);
                    } else {
                        mPresenter.collectArticle(mArticleId);
                    }
                } else {
                    ToastUtil.show(mContext, getString(R.string.please_you_must_login));
                    SkipUtil.overlay(mActivity, LoginActivity.class);
                }
                break;
            }
            case R.id.menu_article_browser: {
                Uri uri = Uri.parse(mArticleLink);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 每次menu show之前调用
     */
    @SuppressWarnings("all")
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.menu_article_collect).setIcon(isCollect ? R.drawable.ic_collect : R.drawable.ic_no_collect);
        menu.findItem(R.id.menu_article_collect).setTitle(isCollect ? R.string.detail_menu_collect_2 : R.string.detail_menu_collect);
        //通过反射，让menu 同时显示title 和 icon，其中menu.getClass()实际获取的是MenuBuilder.class
        try {
            if (menu != null) {
                LogUtil.i(TAG, "className: " + menu.getClass().getSimpleName());
                LogUtil.i(TAG, "isClassEqual: " + (Boolean.TYPE == Boolean.class));
                LogUtil.i(TAG, "isClassEqual: " + (Boolean.TYPE == boolean.class));
                LogUtil.i(TAG, "isClassEqual: " + boolean.class);
                LogUtil.i(TAG, "isClassEqual: " + Boolean.TYPE);
                LogUtil.i(TAG, "isClassEqual: " + Boolean.class);
                Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", boolean.class);
                method.setAccessible(true);
                method.invoke(menu, true);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void collectArticleOK(String info) {
        isCollect = true;
        ToastUtil.show(mContext, getString(R.string.collect_article));
        EventBus.getDefault().post(new MessageEvent(EventConstant.REFRESH_HOMEPAGE, ""));
    }

    @Override
    public void collectArticleErr(String info) {
        ToastUtil.show(mContext, getString(R.string.please_you_must_login));
        SkipUtil.overlay(mActivity, LoginActivity.class);
    }

    @Override
    public void cancelCollectArticleOK(String info) {
        isCollect = false;
        ToastUtil.show(mContext, getString(R.string.collect_cancel_article));
        EventBus.getDefault().post(new MessageEvent(EventConstant.REFRESH_HOMEPAGE, ""));
    }

    @Override
    public void cancelCollectArticleErr(String info) {
        ToastUtil.show(mContext, getString(R.string.please_you_must_login_2));
        SkipUtil.overlay(mActivity, LoginActivity.class);
    }
}
