package com.example.levi.wanandroidapp.util.app;

import android.content.Context;
import android.graphics.Color;
import android.view.inputmethod.InputMethodManager;

import com.example.levi.wanandroidapp.base.app.MyApplication;

import java.util.Random;

/**
 * Author: Levi
 * CreateDate: 2018/10/20 18:12
 */
public class CommonUtil {

    /**
     * 隐藏键盘
     */
    public static void hideKeyBoard(){
        InputMethodManager imm= (InputMethodManager) MyApplication.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm!=null;
        imm.toggleSoftInput(0,0);
    }

    /**
     * 获取随机rgb颜色值
     */
    public static int randomColor() {
        Random random = new Random();
        //0-190, 如果颜色值过大,就越接近白色,就看不清了,所以需要限定范围
        int red = random.nextInt(150);
        //0-190
        int green = random.nextInt(150);
        //0-190
        int blue = random.nextInt(150);
        //使用rgb混合生成一种新的颜色,Color.rgb生成的是一个int数
        return Color.rgb(red, green, blue);
    }

    /**
     * 计算当前观看人数
     */
    public static String getOnlineNum(int num) {
        String online = "";
        if (num > 10000) {
            StringBuilder sb = new StringBuilder();
            sb.append(num / 10000);
            sb.append(".");
            if (num % 10000 == 0) {
                sb.append("0");
            } else {
                sb.append(num % 10000 / 1000);
            }
            sb.append("万");
            online = String.valueOf(sb);
        } else if (num > 0 && num < 10000) {
            online = String.valueOf(num);
        }
        return online;
    }
}
