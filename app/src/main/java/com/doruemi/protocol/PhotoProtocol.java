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

    /**
     * 获取发现页主题列表
     * @param callback  callback
     */
    public static void getCategoryList(Callback callback) {
        String tlisturl = DosnapApp.apiHost + DosnapApp.apiDir + "theme/covers";
        OkHttpUtils.get().url(tlisturl)
                .addParams("identifier", DosnapApp.identifier)
                .addParams("token", DosnapApp.token)
                .addParams("pagesize", "30")
                .build()
                .execute(callback);
    }

    /**
     * 获取发现页推荐用户
     * @param callback  callback
     * @param page      页数
     */
    public static void getRecommandUsers(Callback callback, int page) {
        String tlisturl = DosnapApp.apiHost + DosnapApp.apiDir + "discover/getusersList";
        OkHttpUtils.post().url(tlisturl)
                .addParams("identifier", DosnapApp.identifier)
                .addParams("token", DosnapApp.token)
                .addParams("page", page + "")
                .build()
                .execute(callback);
    }

    /**
     * 关注用户
     * @param callback  回调
     * @param userid    用户id
     */
    public static void followUser(Callback callback, String userid) {
        String tlisturl = DosnapApp.apiHost + DosnapApp.apiDir + "Member/following";
        OkHttpUtils.post().url(tlisturl)
                .addParams("identifier", DosnapApp.identifier)
                .addParams("token", DosnapApp.token)
                .addParams("userid", userid)
//                .addParams("r", String.valueOf(new Random().nextInt(1000)))
                .build()
                .execute(callback);
    }

    /**
     * 获取主题标签列表
     * @param callback  callback
     * @param cid       主题ID
     */
    public static void getCategoryLabel(Callback callback, int cid) {
        String tlisturl = DosnapApp.apiHost + DosnapApp.apiDir + "theme/tags";
        OkHttpUtils.get().url(tlisturl)
                .addParams("identifier", DosnapApp.identifier)
                .addParams("token", DosnapApp.token)
                .addParams("cid", cid+"")
                .build()
                .execute(callback);
    }

    /**
     * 获取主题热门照片
     * @param callback  callback
     * @param cid       主题ID
     */
    public static void getCategoryHot(Callback callback, int cid) {
        String tlisturl = DosnapApp.apiHost + DosnapApp.apiDir + "theme/hot";
        OkHttpUtils.get().url(tlisturl)
                .addParams("identifier", DosnapApp.identifier)
                .addParams("token", DosnapApp.token)
                .addParams("cid", cid+"")
                .addParams("pagesize", "30")
                .build()
                .execute(callback);
    }

    /**
     * 获取主题最新图片
     * @param callback  callback
     * @param cid       主题ID
     * @param page      页数
     */
    public static void getCategoryNew(Callback callback, int cid, int page) {
        String tlisturl = DosnapApp.apiHost + DosnapApp.apiDir + "theme/new";
        OkHttpUtils.get().url(tlisturl)
                .addParams("identifier", DosnapApp.identifier)
                .addParams("token", DosnapApp.token)
                .addParams("cid", cid+"")
                .addParams("page", page+"")
                .addParams("pagesize", "15")
                .build()
                .execute(callback);
    }
}
