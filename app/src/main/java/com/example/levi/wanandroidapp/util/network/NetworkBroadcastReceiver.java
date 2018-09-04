package com.example.levi.wanandroidapp.util.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.levi.wanandroidapp.base.activity.BaseActivity;
import com.example.levi.wanandroidapp.model.constant.Constant;


public class NetworkBroadcastReceiver extends BroadcastReceiver {
    public NetEvent pEventActivity = BaseActivity.gEventActivity;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Constant.NETBROADCAST)) {
            NetUtils.init(context);
            int netWorkState = NetUtils.getNetWorkState();
            pEventActivity.onNetChange(netWorkState);
        }
    }

    //自定义接口
    public interface NetEvent {
        void onNetChange(int netMobile);
    }
}
