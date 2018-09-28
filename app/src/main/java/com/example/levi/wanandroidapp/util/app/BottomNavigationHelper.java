package com.example.levi.wanandroidapp.util.app;

import android.annotation.SuppressLint;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;

import java.lang.reflect.Field;

/**
 * Author: Levi
 * CreateDate: 2018/9/28 15:03
 * 参考博客:https://blog.csdn.net/aiynmimi/article/details/72967585
 * BottomNavigationView的子项大于3个时，点击子项会有切换动画(icon缩放，title隐藏)
 * 该工具类用于关闭item切换动画
 */
@SuppressLint("RestrictedApi")
public class BottomNavigationHelper {
    public static void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field mShiftingMode = BottomNavigationMenuView.class.getDeclaredField("mShiftingMode");
            mShiftingMode.setAccessible(true);
            mShiftingMode.setBoolean(menuView, false);
            mShiftingMode.setAccessible(false);
            for(int i=0;i<menuView.getChildCount();i++){
                BottomNavigationItemView itemView= (BottomNavigationItemView) menuView.getChildAt(i);
                itemView.setShiftingMode(false);
                itemView.setChecked(itemView.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
