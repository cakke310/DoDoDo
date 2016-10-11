package com.doruemi.protocol;

import com.doruemi.DosnapApp;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

/**
 * Created by Administrator on 2016/9/13.
 */
public class PhotoProtocol {

    /**
     * 获取主页
     * @param callback  回调
     * @param page      页数
     */
    public static void getHomeAttention(Callback callback, int page) {
        String tlisturl = DosnapApp.apiHost + DosnapApp.apiDir + "home/index";
        OkHttpUtils.post().url(tlisturl)
                .addParams("identifier", DosnapApp.identifier)
                .addParams("token", DosnapApp.token)
                .addParams("type", "index")
                .addParams("page", page + "")
                .addParams("pagesize", "15")
                .addParams("ver", "2")
                .build()
                .execute(callback);
    }

    /**
     * 获取发现页瀑布流图片列表
     * @param callback  callback
     * @param page      页数
     */
    public static void getSearchPhotoList(Callback callback, int page) {
        String tlisturl = DosnapApp.apiHost + DosnapApp.apiDir + "home/searcher";
        OkHttpUtils.post().url(tlisturl)
                .addParams("identifier", DosnapApp.identifier)
                .addParams("token", DosnapApp.token)
                .addParams("page", page + "")
                .addParams("pagesize", "15")
                .build()
                .execute(callback);
    }
}
