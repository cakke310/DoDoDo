package com.doruemi.adapter.delegate;

import android.text.format.DateUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.doruemi.DosnapApp;
import com.doruemi.R;
import com.doruemi.bean.MainPhotoBean;
import com.doruemi.configs.ConfigConstants;
import com.doruemi.view.AvatarView;
import com.doruemi.view.CommentView;
import com.doruemi.view.LaudView;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

/**
 * Created by Administrator on 2016-09-16.
 */
public class HomeListDelegate implements ItemViewDelegate {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_main_list;
    }

    @Override
    public boolean isForViewType(Object item, int position) {
        return item instanceof MainPhotoBean.PhotoInfoBean;
    }

    @Override
    public void convert(ViewHolder holder, Object o, int position) {
        final MainPhotoBean.PhotoInfoBean photoInfoBean = (MainPhotoBean.PhotoInfoBean) o;
        holder.setText(R.id.tv_username, photoInfoBean.getUsername());
        holder.setText(R.id.tv_location, photoInfoBean.getAddress());
//        holder.getView(R.id.tv_location).setVisibility(photoInfoBean);

        long ftime = Long.parseLong(photoInfoBean.getAddtime())*1000;
        if(System.currentTimeMillis() - ftime < 120000){
            holder.setText(R.id.tv_addtime, "刚刚");
        }else {
            String addtime = DateUtils.getRelativeTimeSpanString(ftime).toString();
            holder.setText(R.id.tv_addtime, addtime);
        }

        ((AvatarView)holder.getView(R.id.avatarview)).set(photoInfoBean.getUserid(),photoInfoBean.getIsdaren(),true);
        SimpleDraweeView photoView = holder.getView(R.id.photo);
        ViewGroup.LayoutParams para = photoView.getLayoutParams();
        para.height = DosnapApp.devicewidth * photoInfoBean.height / 640;
        photoView.setLayoutParams(para);
        final String imgurl = DosnapApp.apiHost + "width_850/" + photoInfoBean.imgurl.replaceAll("width_\\d+/", "");
        DraweeController controller = ConfigConstants.getDraweeController(
                ConfigConstants.getImageRequest(photoView, imgurl), photoView);
        photoView.setController(controller);

        /** 设置评论、关注、分享按钮点击 */
        final CommentView commentView = holder.getView(R.id.commentView);
        commentView.set(photoInfoBean.id, photoInfoBean.comment);

        final LaudView laudView = holder.getView(R.id.laudView);
        TextView tvLaudnum = holder.getView(R.id.tv_laudnum);
        laudView.setlistener(photoInfoBean.id, photoInfoBean.laud, photoInfoBean.haslaud, tvLaudnum,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        photoInfoBean.haslaud = Math.abs(photoInfoBean.haslaud - 1);
                    }
                });

//        ShareView shareView = holder.getView(R.id.img_share);
//        shareView.set(photoInfoBean.id, photoInfoBean.userid, photoInfoBean.username, photoInfoBean.imgurl,
//                "", "http://app.dosnap.com/api/Share/shareimg?arcid=" + photoInfoBean.id);

    }
}
