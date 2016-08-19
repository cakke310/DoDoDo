package com.doruemi.application;

import android.app.Application;
import android.content.Context;
/**
 * Created by Administrator on 2016/8/16.
 */
public class App extends Application {
    private static Context mContext;
    public static final String TAG = "App";

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getContext(){
        return mContext;
    }




}
