package com.doruemi.view;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.doruemi.DosnapApp;
import com.doruemi.R;
import com.doruemi.bean.MainPhotoBean;
import com.doruemi.configs.ConfigConstants;
import com.doruemi.util.LogUtil;
import com.doruemi.util.UIUtils;
import com.doruemi.util.Utility;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-09-20.
 */
public class BannerView extends FrameLayout {

    private AutoPlayTask autoPlayTask;
    private Context mContext;
    private List<MainPhotoBean.MatchListBean> elements;

    private LinearLayout dotLayout;
    private ViewPager viewPager;
    private Handler handler;

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
        autoPlayTask = new AutoPlayTask();
        handler = new Handler();
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

    private class AutoPlayTask implements Runnable{
        private int AUTO_PLAY_TIME = 2000;

        private boolean has_auto_play = false;

        @Override
        public void run() {
            if(has_auto_play){
                viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                handler.postDelayed(this, AUTO_PLAY_TIME);
            }
        }

        public void stop() {
            has_auto_play = false;
            handler.removeCallbacks(this);
        }

        public void start() {
            if(!has_auto_play){
                has_auto_play = true;
                //UIUtils.removeCallbacks(this);
                handler.postDelayed(this, AUTO_PLAY_TIME);
            }
        }
    }

    public void stopPlay() {
        autoPlayTask.stop();
    }

    private void initUI(List<MainPhotoBean.MatchListBean> data) {
        dotLayout.removeAllViews();
        for(int i = 0; i < data.size(); i++){
            if(data.size()>1){
                dotLayout.addView(addDotView(mContext,i));
            }
        }
        viewPager.setAdapter(new MyPagerAdapter());
        viewPager.setCurrentItem(1000);
        if(elements.size()>1){
            viewPager.setOnPageChangeListener(new MyPageChangeListener());
        }

        viewPager.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    autoPlayTask.stop();
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    autoPlayTask.start();
                }

                return false;
            }
        });

    }

    private class MyPageChangeListener implements ViewPager.OnPageChangeListener{

        boolean isAutoPlay = false;
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            for(int i=0; i< dotLayout.getChildCount(); i++){
                if(i==position%elements.size()){
                    ((ImageView)dotLayout.getChildAt(i)).setImageResource(R.drawable.dot_blue);
                }else {
                    ((ImageView)dotLayout.getChildAt(i)).setImageResource(R.drawable.dot_white);

                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            switch (state) {
                case 1:// 手势滑动，空闲中
                    isAutoPlay = false;
                    autoPlayTask.stop();
                    break;
                case 2:// 界面切换中
                    isAutoPlay = true;
                    break;
            }

        }
    }

    private class MyPagerAdapter extends PagerAdapter{
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int i = position % elements.size();
            SimpleDraweeView photoView = new SimpleDraweeView(getContext());
            DraweeController controller = ConfigConstants.getDraweeController(
                    ConfigConstants.getImageRequest(photoView, DosnapApp.apiHost + elements.get(i).getImgurl()), photoView);
            photoView.setController(controller);
            container.addView(photoView);
//            MainPhotoBean.MatchListBean item = elements.get(position);

            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }


    private ImageView addDotView(Context context, int i){
        int width = Utility.Dp2Px(context, 7);
        int padding = Utility.Dp2Px(context, 7);
        ImageView dotVoew = new ImageView(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, width);
        lp.setMargins(padding,padding,padding,padding);
        dotVoew.setLayoutParams(lp);
        if(i == 0){
            dotVoew.setImageResource(R.drawable.dot_blue);
//            dotVoew.setImageDrawable(getResources().getDrawable(R.drawable.dot_blue));
        }else {
            dotVoew.setImageResource(R.drawable.dot_white);
        }
        return dotVoew;


    }
}
