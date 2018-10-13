package com.example.levi.wanandroidapp.base.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Author: Levi
 * CreateDate: 2018/10/13 10:21
 */
public class SimpleFragmentStateAdapter extends FragmentPagerAdapter {
    private List<Fragment> mLists;

    public SimpleFragmentStateAdapter(FragmentManager manager, List<Fragment> list) {
        super(manager);
        mLists = list;
    }

    @Override
    public Fragment getItem(int position) {
        return mLists.get(position);
    }

    @Override
    public int getCount() {
        return mLists == null ? 0 : mLists.size();
    }
}
