package com.example.levi.wanandroidapp.util.network;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

public class NetUtils {
    /**
     * 没有连接网络
     */
    public static final int NETWORK_NONE=-1;
    /**
     * 移动网络
     */
    public static final int NETWORK_MOBILE=0;
    /**
     * 无线网络
     */
    public static final int NETWORK_WIFI=1;

    public static Context mContext;

    public static void init(Context context){
        mContext=context;
    }
    @TargetApi(21)
    public static int getNetWorkState(){
        if(mContext==null){
            throw new UnsupportedOperationException("please use NetUtils before init it");
        }
        ConnectivityManager connectivityManager= (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        Network[] networks=connectivityManager.getAllNetworks();
        for(int i=0;i<networks.length;i++){
            NetworkInfo networkInfo=connectivityManager.getNetworkInfo(networks[i]);
            if(networkInfo.isConnected()){
                if(networkInfo.getType()==ConnectivityManager.TYPE_MOBILE){
                    return NETWORK_MOBILE;
                }else{
                    return NETWORK_WIFI;
                }
            }
        }
        return NETWORK_NONE;
    }
}
