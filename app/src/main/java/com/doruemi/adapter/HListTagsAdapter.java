package com.doruemi.adapter;

import android.content.Context;
import android.view.View;

import com.doruemi.R;
import com.doruemi.bean.ThemeLabel;
import com.doruemi.widget.TagView;
import com.zhy.adapter.abslistview.MultiItemTypeAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.adapter.abslistview.base.ItemViewDelegate;

import java.util.List;

/**
 * Created by c_xuwei-010 on 2016/12/7.
 */
public class HListTagsAdapter extends MultiItemTypeAdapter<ThemeLabel> {
    public HListTagsAdapter(Context context, List<ThemeLabel> datas) {
        super(context, datas);
        addItemViewDelegate(new ItemViewDelegate<ThemeLabel>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.item_themelabel;
            }

            @Override
            public boolean isForViewType(ThemeLabel item, int position) {
                return true;
            }

            @Override
            public void convert(ViewHolder holder, ThemeLabel themeLabel, int position) {
                TagView tagView = holder.getView(R.id.tagView);
                tagView.set(themeLabel.labelname);
            }
        });
    }

    public void appendDatas(List<ThemeLabel> data){
        mDatas.clear();
        mDatas.addAll(data);
        this.notifyDataSetChanged();
    }
}
