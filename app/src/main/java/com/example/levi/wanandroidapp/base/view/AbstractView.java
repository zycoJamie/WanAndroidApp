package com.example.levi.wanandroidapp.base.view;

import android.view.View;

public interface AbstractView {
    void showNormal();
    void showError();
    void showLoading();
    void showEmpty();
    void reload();
    void setVisible(View... views);
    void setInVisible(View... views);
    void setGone(View... views);
}
