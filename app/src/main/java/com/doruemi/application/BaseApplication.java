package com.doruemi.application;

import android.app.Application;
import android.content.Context;

import com.doruemi.configs.ConfigConstants;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2016/8/16.
 */
public class BaseApplication extends Application {
    private static BaseApplication mContext;
    public static final String TAG = "BaseApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = (BaseApplication) getApplicationContext();
        OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10000L, TimeUnit.MILLISECONDS).readTimeout(10000L, TimeUnit.MILLISECONDS).build();
        OkHttpUtils.initClient(okHttpClient);
        Fresco.initialize(this, ConfigConstants.getImagePipelineConfig(this));
    }

    public static BaseApplication getContext(){
        return mContext;
    }




}
