package com.doruemi.adapter;

import android.content.Context;

import com.doruemi.R;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by c_xuwei-010 on 2016/12/19.
 */
public class PhotoListAdapter extends MultiItemTypeAdapter {
    public PhotoListAdapter(Context context, List datas) {
        super(context, datas);
        addItemViewDelegate(new ItemViewDelegate() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.item_activity_photoshow;
            }

            @Override
            public boolean isForViewType(Object item, int position) {
                return true;
            }

            @Override
            public void convert(ViewHolder holder, Object o, int position) {

            }
        });
    }


}
