package com.doruemi.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.doruemi.DosnapApp;
import com.doruemi.R;
import com.doruemi.adapter.DividerItemDecoration;
import com.doruemi.bean.MainPhotoBean;
import com.doruemi.configs.ConfigConstants;
import com.doruemi.protocol.PhotoProtocol;
import com.doruemi.util.LogUtil;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshRecyclerView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Administrator on 2016-08-20.
 */
public class GalleryFragment extends BaseFragment {


    private PullToRefreshRecyclerView mPtrRecycleView;
    private int page = 1;
    List<MainPhotoBean.PhotoInfoBean> list = new ArrayList<>();
    private CommonAdapter commonAdapter;
    private boolean isFirst;
    private boolean canLoad;
    private RecyclerView recyclerView;

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        if(isFirst){
            mPtrRecycleView.setRefreshing();
            return;
        }
        getHttpUtils();
        recyclerView.setAdapter(commonAdapter);
    }

    private void getHttpUtils() {
        PhotoProtocol.getSearchPhotoList(waterFallCallback, page);
    }

    private StringCallback waterFallCallback = new StringCallback() {
        @Override
        public void onError(Call call, Exception e, int id) {
            mPtrRecycleView.onRefreshComplete();
        }

        @Override
        public void onResponse(String response, int id) {
            processData(response);
            isFirst = false;
            mPtrRecycleView.onRefreshComplete();
        }
    };

    private void processData(String response) {
        MainPhotoBean mainPhotoBean = new Gson().fromJson(response, MainPhotoBean.class);
        handleData(mainPhotoBean);
    }

    private void handleData(MainPhotoBean mainPhotoBean) {
        List<MainPhotoBean.PhotoInfoBean> data = mainPhotoBean.getList() != null ? mainPhotoBean.getList() : null;
        if(data.size()>=15){
            canLoad = true;
        }
        if(page == 1){
            list.clear();
        }

        if(data != null){
            list.addAll(data);
            commonAdapter.notifyDataSetChanged();
        }
//        new HeaderAndFooterWrapper(commonAdapter);
        recyclerView = mPtrRecycleView.getRefreshableView();
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), GridLayoutManager.HORIZONTAL));


        mPtrRecycleView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        mPtrRecycleView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<RecyclerView>() {
            @Override
            public void onRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                PullToRefreshBase.Mode mode = mPtrRecycleView.getCurrentMode();
                if(mode == PullToRefreshBase.Mode.PULL_FROM_START){
                    page = 1;
                }
                getHttpUtils();
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    int lastVisibleItemPosition = gridLayoutManager.findLastVisibleItemPosition();
                    if(lastVisibleItemPosition >= gridLayoutManager.getItemCount()-5 && canLoad){
                        page++;
                        getHttpUtils();
                    }
                }
            }
        });
        commonAdapter.notifyDataSetChanged();

    }


    @Override
    protected void initView() {
        mPtrRecycleView = (PullToRefreshRecyclerView) currentView.findViewById(R.id.recycler_view);


    }

    @Override
    public int setContentViewId() {
        return R.layout.fragment_search;
    }


}




