package com.doruemi.fragment;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.doruemi.R;
import com.doruemi.adapter.DividerItemDecoration;
import com.doruemi.bean.SearchBean;
import com.doruemi.configs.ConfigConstants;
import com.doruemi.util.LogUtil;
import com.doruemi.util.NetworkUtils;
import com.doruemi.util.UIUtils;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshRecyclerView;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2016-08-20.
 */
public class GalleryFragment extends BaseFragment {


    private PullToRefreshRecyclerView mPtrRecycleView;

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }


    @Override
    protected void initView() {
        mPtrRecycleView = (PullToRefreshRecyclerView) currentView.findViewById(R.id.recycler_view);
        RecyclerView recyclerView = mPtrRecycleView.getRefreshableView();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),GridLayoutManager.HORIZONTAL));


    }

    @Override
    public int setContentViewId() {
        return R.layout.fragment_search;
    }


}




