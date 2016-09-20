package com.doruemi.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import com.doruemi.widget.TagView;

import java.util.List;

/**
 * 主页标签的Adapter
 * Created by seekingL on 2016/7/19.
 */
public class TagsHlistAdapter extends BaseAdapter {

    List<String> list;
    Context context;

    public TagsHlistAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void refresh(List<String> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public String getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = new TagView(context);
        }
        ((TagView)convertView).set(list.get(position));
        return convertView;
    }
}
