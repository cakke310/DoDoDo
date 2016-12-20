package com.doruemi.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;

import com.bm.library.PhotoView;
import com.doruemi.DosnapApp;
import com.doruemi.R;
import com.doruemi.configs.ConfigConstants;
import com.doruemi.util.LogUtil;
import com.doruemi.view.ZoomableDraweeView;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import me.relex.photodraweeview.PhotoDraweeView;

/**
 * Created by c_xuwei-010 on 2016/12/14.
 */
public class PhotoActivity extends Activity {

    private PullToRefreshListView listView;
    private Bundle bundle;
    // 当前模式,0为图片模式，1为纯评论模式,2为带图评论模式
    private int mode = 0;
    private int photoid = 0;
    private int isdaren = 0;
    private ZoomableDraweeView photoView;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_activity_photoshow);
        photoView = (ZoomableDraweeView) findViewById(R.id.photoShow);
        url = getIntent().getStringExtra("url");
        String imgurl = DosnapApp.apiHost +"crop_250x250/"+ url
                .replaceAll("crop_\\d+x\\d+/", "");
        LogUtil.e("imgurl="+imgurl);
//        DraweeController draweeController = ConfigConstants.getDraweeController(ConfigConstants.getImageRequest(photoView, url), photoView);
//        photoView.setController(draweeController);
        photoView.setImageURI(Uri.parse(imgurl));
    }


    public static Bitmap getHttpBitmap(String url){
        URL myFileURL;
        Bitmap bitmap=null;
        try{
            myFileURL = new URL(url);
            //获得连接
            HttpURLConnection conn=(HttpURLConnection)myFileURL.openConnection();
            //设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制
            conn.setConnectTimeout(6000);
            //连接设置获得数据流
            conn.setDoInput(true);
            //不使用缓存
            conn.setUseCaches(false);
            //这句可有可无，没有影响
            //conn.connect();
            //得到数据流
            InputStream is = conn.getInputStream();
            //解析得到图片
            bitmap = BitmapFactory.decodeStream(is);
            //关闭数据流
            is.close();
        }catch(Exception e){
            e.printStackTrace();
        }

        return bitmap;

    }


}
