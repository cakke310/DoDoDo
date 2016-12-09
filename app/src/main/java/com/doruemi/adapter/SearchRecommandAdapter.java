package com.doruemi.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.doruemi.R;
import com.doruemi.bean.RecommandUser;
import com.doruemi.protocol.PhotoProtocol;
import com.doruemi.util.LogUtil;
import com.doruemi.view.AvatarView;
import com.zhy.adapter.abslistview.MultiItemTypeAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.adapter.abslistview.base.ItemViewDelegate;

import java.util.List;

/**
 * Created by Administrator on 2016/10/19.
 */
public class SearchRecommandAdapter extends MultiItemTypeAdapter<RecommandUser> {
    public SearchRecommandAdapter(Context context, List<RecommandUser> datas, final View parent) {
        super(context, datas);
        addItemViewDelegate(new ItemViewDelegate<RecommandUser>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.item_search_recommand;
            }

            @Override
            public boolean isForViewType(RecommandUser item, int position) {
                return true;
            }

            @Override
            public void convert(ViewHolder holder, final RecommandUser recommandUser, final int position) {
                AvatarView avatarView = holder.getView(R.id.avatarview);
                avatarView.set(recommandUser.userid, 0, true);

                holder.setText(R.id.tv_username, recommandUser.username);

                final ImageView ivFollow = holder.getView(R.id.iv_follow);
                int tag = ivFollow.getTag() == null ? 0 : (int) ivFollow.getTag();
                if (tag == recommandUser.userid) {
                    setDisable(ivFollow);
                } else {
                    setEnable(ivFollow);
                }
                ivFollow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PhotoProtocol.followUser(null, recommandUser.userid + "");
                        setDisable(ivFollow);
                        ivFollow.setTag(recommandUser.userid);
                        mDatas.remove(position);
                        SearchRecommandAdapter.this.notifyDataSetChanged();

                    }
                });

                holder.getView(R.id.iv_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDatas.remove(position);
                        SearchRecommandAdapter.this.notifyDataSetChanged();
                        if (mDatas.size() < 1) {
                            parent.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
    }

    public void appendDatas(List<RecommandUser> data) {
        mDatas.clear();
        mDatas.addAll(data);
        this.notifyDataSetChanged();
    }

    private void setDisable(ImageView iv) {
        iv.setImageResource(R.drawable.icon_yiguanzhu);
        iv.setEnabled(false);
    }

    private void setEnable(ImageView iv) {
        iv.setEnabled(true);
        iv.setImageResource(R.drawable.icon_guanzhu);
    }


}
