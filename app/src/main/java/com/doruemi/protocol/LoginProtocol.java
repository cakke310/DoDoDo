package com.doruemi.protocol;

import android.os.Build;

import com.doruemi.DosnapApp;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

/**
 * 登录相关协议
 * Created by seekingL on 2016/7/15.
 *
 */
public class LoginProtocol {



    /** 微信登录 */
    public static void weixinLogin(Callback callback, String uid, String unionid, String username, String face, String logintype) {
        String tlisturl = DosnapApp.apiHost + DosnapApp.apiDir + "member/login";
        OkHttpUtils.post().url(tlisturl)
                .addParams("logintype", logintype)
                .addParams("unionid", unionid)
                .addParams("uid", uid)
                .addParams("username", username)
                .addParams("face", face)
                .build()
                .execute(callback);
    }



    /** 绑定设备 */
    public static void bindDevice(Callback callback) {
        String tlisturl = DosnapApp.apiHost + DosnapApp.apiDir + "Device/abind";
        OkHttpUtils.post().url(tlisturl)
                .addParams("identifier", DosnapApp.identifier)
                .addParams("token", DosnapApp.token)
                .addParams("devicetoken", DosnapApp.xgtoken)
                .addParams("devicemodel", Build.BRAND + " " + Build.MODEL)
                .addParams("devicename", DosnapApp.username + " Android")
                .build()
                .execute(callback);
    }

    /** 手机号登录 */
    public static void loginWithPhonenum(Callback callback, String phonenum, String password) {
        String tlisturl = DosnapApp.apiHost + DosnapApp.apiDir + "member/login";
        OkHttpUtils.post().url(tlisturl)
                .addParams("logintype", "1")
                .addParams("loginid", phonenum)
                .addParams("loginpwd", password)
                .addParams("countrycode", "86")
                .build()
                .execute(callback);
    }

}
