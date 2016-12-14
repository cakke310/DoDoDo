package com.doruemi.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.doruemi.DosnapApp;
import com.doruemi.R;
import com.doruemi.bean.ThemePhotoBean;
import com.doruemi.configs.ConfigConstants;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by c_xuwei-010 on 2016/12/12.
 */
public class ThemeDetailAdapterN extends MultiItemTypeAdapter<ThemePhotoBean> {
    public ThemeDetailAdapterN(Context context, List<ThemePhotoBean> datas) {
        super(context, datas);
        addItemViewDelegate(new ItemViewDelegate<ThemePhotoBean>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.item_activity_photo;
            }

            @Override
            public boolean isForViewType(ThemePhotoBean item, int position) {
                return true;
            }

            @Override
            public void convert(ViewHolder holder, ThemePhotoBean item, int position) {
                SimpleDraweeView photoView = holder.getView(R.id.photo);
                String imgurl = DosnapApp.apiHost +"crop_250x250/"+ item.imgurl
                        .replaceAll("crop_\\d+x\\d+/", "");
                DraweeController controller = ConfigConstants.getDraweeController(
                        ConfigConstants.getImageRequest(photoView, imgurl), photoView);
                photoView.setController(controller);
                photoView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Intent intent = new Intent(v.getContext(), PhotoActivity.class);
//                        Bundle bundle = new Bundle();
//                        bundle.putInt("id", item.id);
//                        intent.putExtras(bundle);
//                        v.getContext().startActivity(intent);
                    }
                });
            }
        });
    }

    public void appendData(List<ThemePhotoBean> data) {
        mDatas.addAll(data);
        notifyDataSetChanged();
    }

    public void refresh(List<ThemePhotoBean> data) {
//        mDatas.clear();
//        mDatas.addAll(data);
        mDatas = data;
//        notifyDataSetChanged();
    }
}
