package com.doruemi.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.doruemi.DosnapApp;
import com.doruemi.bean.MainPhotoBean;
import com.google.gson.Gson;

/**
 * Created by Administrator on 2016/9/29.
 */
public class SharedPreferencesUtils {
    private final static String SP_NAME = "config";
    private static SharedPreferences sp;

    public static void saveMainPhotoBean(MainPhotoBean mainPhotoBean){
        SharedPreferences sp = DosnapApp.getApplication().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("MainPhotoBean",new Gson().toJson(mainPhotoBean));
        editor.apply();
    }

    public static MainPhotoBean getMainPhotoBean(){
        SharedPreferences sp = DosnapApp.getApplication().getSharedPreferences(SP_NAME,Context.MODE_PRIVATE);
        String json = sp.getString("MainPhotoBean", null);
        MainPhotoBean info = null;
        if(json!=null){
            info = new Gson().fromJson(json,MainPhotoBean.class);
        }
        return info;
    }
}
