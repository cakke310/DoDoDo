package com.doruemi.adapter.delegate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.doruemi.DosnapApp;
import com.doruemi.R;
import com.doruemi.adapter.TagsHlistAdapter;
import com.doruemi.bean.MainPhotoBean;
import com.doruemi.configs.ConfigConstants;
import com.doruemi.util.LogUtil;
import com.doruemi.util.Utility;
import com.doruemi.view.AvatarView;
import com.doruemi.view.CommentView;
import com.doruemi.view.LaudView;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.adapter.abslistview.base.ItemViewDelegate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import it.sephiroth.android.library.widget.HListView;

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
//        para.height = 860;
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

        /** 设置图片文字内容和标签 */
        TextView tvContent = holder.getView(R.id.tv_content);
        HListView hlistView = holder.getView(R.id.hlist);
        TagsHlistAdapter hlistAdapter = new TagsHlistAdapter(new ArrayList<String>(), hlistView.getContext());
        String content = photoInfoBean.content;
        hlistView.setAdapter(hlistAdapter);
        if (photoInfoBean.content.length() < 1) {
            tvContent.setVisibility(View.GONE);
            hlistView.setVisibility(View.GONE);
        } else {
            HashMap<String, List<String>> map = Utility.text2TagMap(photoInfoBean.content);
//			List<String> tags = map.get("all");
            List<String> replaceTags = map.get("replace");
            List<String> textTags = map.get("text");
            if (textTags.size() > 0) {
                hlistView.setVisibility(View.VISIBLE);
                hlistAdapter.refresh(textTags);
                for (int i = 0; i < replaceTags.size(); i++) {
                    content = content.replace(replaceTags.get(i), "");
                }
            } else {
                hlistView.setVisibility(View.GONE);
            }
            if (content.length() < 1) {
                tvContent.setVisibility(View.GONE);
            } else {
                tvContent.setVisibility(View.VISIBLE);
                tvContent.setText(Utility.text2Span(content));
                tvContent.setMovementMethod(LinkMovementMethod.getInstance());
            }
        }

        /** 设置评论 */
        LinearLayout comLayout = holder.getView(R.id.comlayout);
        final TextView comtextView = holder.getView(R.id.comtext);
        LinearLayout moreLayout = holder.getView(R.id.morelayout);
        TextView moreView = holder.getView(R.id.moretext);
        comLayout.setVisibility(View.GONE);
        List commentList = photoInfoBean.getComments();
        if (commentList.size() > 0) {
            comLayout.setVisibility(View.VISIBLE);
            String comtext = "";
            MainPhotoBean.Comment comment;
            for (int i = 0; i < commentList.size(); i++) {
                try {
                    comment = (MainPhotoBean.Comment) commentList.get(i);
                    if (comment.content.startsWith("回复")) {
                        comtext += "~"
                                + comment.username
                                + " "
                                + comment.content.replaceAll("回复 ", "回复 ~");
                    } else {
                        comtext += "~"
                                + comment.username
                                + " : "
                                + comment.content.replaceAll("回复 ", "回复 ~");
                    }
                    if (i < commentList.size() - 1)
                        comtext += "\r\n";
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            comtextView.setText(Utility.comment2Span(comtext));
            comtextView.setMovementMethod(LinkMovementMethod.getInstance());
            if (photoInfoBean.comment > 3) {
                String moreString = comLayout.getContext().getResources().getString(R.string.morecom);
                moreView.setText(String.format(moreString, photoInfoBean.comment + ""));
                moreView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        commentView.onClick(commentView);
                    }
                });
                moreLayout.setVisibility(View.VISIBLE);
            } else {
                moreLayout.setVisibility(View.GONE);
            }
        } else {
            comLayout.setVisibility(View.GONE);
        }

        /** 设置喜欢的人数点击 */
        holder.getView(R.id.ll_laud).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext(), LaudUserActivity.class);
//                Bundle b = new Bundle();
//                b.putInt("photoid", photoInfoBean.id);
//                intent.putExtras(b);
//                v.getContext().startActivity(intent);
                Toast.makeText(v.getContext(),"设置喜欢的人数点击 ", Toast.LENGTH_SHORT).show();
            }
        });

        /** 设置点赞动画 */
        final ImageView imgGood = holder.getView(R.id.iv_big_good);
        final Animation animation = AnimationUtils.loadAnimation(DosnapApp.getApplication(), R.anim.lauded_anim);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                imgGood.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        imgGood.clearAnimation();
                        imgGood.setVisibility(View.GONE);
                    }
                }, 200);
            }
        });


        /** 设置双击点赞事件 */
        FrameLayout fram = holder.getView(R.id.frame);
        fram.setOnClickListener(new View.OnClickListener() {
            boolean waitDouble = true;
            int DOUBLE_CLICK_TIME = 350;

            private Handler handler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    if (photoInfoBean.haslaud == 0) {
                        imgGood.startAnimation(animation);
                        photoInfoBean.haslaud = 1;
                    } else if (photoInfoBean.haslaud == 1) {
                        photoInfoBean.haslaud = 0;
                    }
                }
            };

            // 等待双击
            public void onClick(final View v) {
                try {
                    if (waitDouble) {
                        waitDouble = false;
                        Thread thread = new Thread() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(DOUBLE_CLICK_TIME);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                //等待双击时间，否则执行单击事件
                                if (!waitDouble) {
                                    //如果过了等待事件还是预执行双击状态，则视为单击
                                    waitDouble = true;
                                    if (!DosnapApp.inphotoshow)
                                        floatView(v.getContext(), imgurl, photoInfoBean.id, photoInfoBean.height);
                                }
                            }
                        };
                        thread.start();
                    } else {
                        waitDouble = true;
                        Message msg = handler.obtainMessage();
                        msg.obj = photoInfoBean.haslaud;
                        handler.sendMessage(msg);
                        laudView.onClick(null);
                        photoInfoBean.haslaud = Math.abs(photoInfoBean.haslaud - 1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }


    /**
     * 显示单张图片
     * @param context context
     * @param imgurl imgurl
     * @param aid id
     * @param height height
     */
    private void floatView(Context context, String imgurl, int aid, int height) {
        Toast.makeText(context,"显示单张图片 ", Toast.LENGTH_SHORT).show();
//        DosnapApp.inphotoshow = true;
//        Intent intent = new Intent(context, PhotoShowActivity.class);
//        Bundle b = new Bundle();
//        b.putString("imgurl", imgurl);
//        b.putInt("id", aid);
//        b.putInt("height", height);
//        intent.putExtras(b);
//        ((Activity) context).startActivityForResult(intent, DosnapApp.ACTIVITY_PHOTOSHOW);
    }
}
