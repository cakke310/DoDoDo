package com.doruemi.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;

import com.doruemi.DosnapApp;
import com.doruemi.R;
import com.doruemi.bean.CategoryBean;
import com.doruemi.configs.ConfigConstants;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Administrator on 2016/10/18.
 */
public class SearchCategoryAdapter extends MultiItemTypeAdapter<CategoryBean.CategoryInfo> {
    public SearchCategoryAdapter(Context context, List<CategoryBean.CategoryInfo> datas) {
        super(context, datas);
        addItemViewDelegate(new ItemViewDelegate<CategoryBean.CategoryInfo>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.item_search_category;
            }

            @Override
            public boolean isForViewType(CategoryBean.CategoryInfo item, int position) {
                return true;
            }

            @Override
            public void convert(com.zhy.adapter.recyclerview.base.ViewHolder holder, CategoryBean.CategoryInfo categoryInfo, int position) {
                holder.setText(R.id.tv_category, categoryInfo.cname);
                holder.getView(R.id.tv_category).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Intent intent = new Intent(v.getContext(), CategoryDetailActivity.class);
//                        intent.putExtra("cid", categoryInfo.cid);
//                        intent.putExtra("cname", categoryInfo.cname);
//                        v.getContext().startActivity(intent);
                    }
                });
                String imgurl = DosnapApp.apiHost + categoryInfo.bgcover;
                SimpleDraweeView photoView = holder.getView(R.id.photo);
                DraweeController controller = ConfigConstants.getDraweeController(
                        ConfigConstants.getImageRequest(photoView, imgurl), photoView);
                photoView.setController(controller);
            }
        });
    }

    public void appendDatas(List<CategoryBean.CategoryInfo> data) {
        mDatas.clear();
        mDatas.addAll(data);
        this.notifyDataSetChanged();
    }
}
