package com.doruemi.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.doruemi.R;
import com.doruemi.bean.MainPhotoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-09-20.
 */
public class BannerView extends FrameLayout {

    private Context mContext;
    private List<MainPhotoBean.MatchListBean> elements;

    private LinearLayout dotLayout;
    private ViewPager viewPager;

    public BannerView(Context context) {
        this(context,null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        LayoutInflater.from(context).inflate(R.layout.layout_slideshowview,this,true);
        elements = new ArrayList<>();
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        dotLayout = (LinearLayout) findViewById(R.id.dotLayout);

        initData();
    }

    public void set(List<MainPhotoBean.MatchListBean> data) {
        elements.clear();
        elements.addAll(data);
        if (data.size() > 0) {
            initUI(data);
            if (data.size() > 1) {
                autoPlayTask.start();
            }
        } else {
            setVisibility(View.GONE);
        }
    }

    private void initUI(List<MainPhotoBean.MatchListBean> data) {
        dotLayout.removeAllViews();
        for(int i = 0; i < data.size(); i++){

        }

    }

    private void initData() {
    }
}
