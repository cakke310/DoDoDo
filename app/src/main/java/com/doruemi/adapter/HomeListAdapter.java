package com.doruemi.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;

import com.doruemi.DosnapApp;
import com.doruemi.R;
import com.doruemi.activity.PhotoShowActivity;
import com.doruemi.adapter.delegate.HomeListDelegate;
import com.doruemi.bean.MainPhotoBean;
import com.doruemi.configs.ConfigConstants;
import com.doruemi.view.AvatarView;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhy.adapter.abslistview.MultiItemTypeAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.adapter.abslistview.base.ItemViewDelegate;

import java.util.List;

/**
 * Created by Administrator on 2016-09-16.
 */
public class HomeListAdapter extends MultiItemTypeAdapter {
    public HomeListAdapter(final Context context, List data) {
        super(context,data);
        addItemViewDelegate(new HomeListDelegate());
        addItemViewDelegate(new ItemViewDelegate() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.item_home_recommand;
            }

            @Override
            public boolean isForViewType(Object item, int position) {
                return item instanceof MainPhotoBean.UnfollowInfo;
            }

            @Override
            public void convert(ViewHolder holder, Object o, int position) {
                final MainPhotoBean.UnfollowInfo data = (MainPhotoBean.UnfollowInfo) o;
                holder.setText(R.id.tv_username, data.getUsername());

                AvatarView avatarView = holder.getView(R.id.avatarView);
                avatarView.set(data.getUserid(), data.getIsdaren(), true);

                SimpleDraweeView photoView = holder.getView(R.id.photo);
                ViewGroup.LayoutParams para = photoView.getLayoutParams();
                para.height = DosnapApp.devicewidth * data.getHeight() / 850;
                photoView.setLayoutParams(para);
                final String imgurl = DosnapApp.apiHost + "width_850/" + data.getImgurl().replaceAll("width_\\d+/", "");
                DraweeController controller = ConfigConstants.getDraweeController(
                        ConfigConstants.getImageRequest(photoView, imgurl), photoView);
                photoView.setController(controller);
                photoView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DosnapApp.inphotoshow = true;
                        Intent intent = new Intent(context, PhotoShowActivity.class);
                        Bundle b = new Bundle();
                        b.putString("imgurl", imgurl);
                        b.putInt("id", data.getId());
                        b.putInt("height", data.getHeight());
                        intent.putExtras(b);
                        context.startActivity(intent);
//                        ((Activity) context).startActivityForResult(intent, DosnapApp.ACTIVITY_PHOTOSHOW);
                    }
                });
            }
        });
    }

    public void appendDatas(List data, int page) {
        if (page == 1) {
            mDatas.clear();
        }
        mDatas.addAll(data);
        this.notifyDataSetChanged();
    }
}
