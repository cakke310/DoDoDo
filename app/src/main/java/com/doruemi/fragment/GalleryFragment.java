package com.doruemi.fragment;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.doruemi.DosnapApp;
import com.doruemi.R;
import com.doruemi.adapter.DividerItemDecoration;
import com.doruemi.bean.MainPhotoBean;
import com.doruemi.configs.ConfigConstants;
import com.doruemi.protocol.PhotoProtocol;
import com.doruemi.util.LogUtil;
import com.doruemi.view.CategoryLayout;
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
    private boolean isFirst = true;
    private boolean canLoad;
    private RecyclerView recyclerView;
    private boolean canload = true;
    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;
    private CategoryLayout categoryLayout;


    @Override
    protected void initListener() {

    }

    @Override
    public int setContentViewId() {
        return R.layout.fragment_search;
    }


    @Override
    protected void initView() {
        list = new ArrayList<>();
        mPtrRecycleView = (PullToRefreshRecyclerView) currentView.findViewById(R.id.recycler_view);
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
                    categoryLayout.refreshing();
                    LogUtil.e("categoryLayout refreshing");
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
    }

    @Override
    protected void initData() {
//        getHttpUtils();
        commonAdapter = new CommonAdapter<MainPhotoBean.PhotoInfoBean>(this.getActivity(),R.layout.item_activity_photo,list) {
            @Override
            protected void convert(ViewHolder holder, MainPhotoBean.PhotoInfoBean photoInfoBean, int position) {
                SimpleDraweeView photoView = holder.getView(R.id.photo);
                String imgurl = DosnapApp.apiHost +"crop_250x250/"+ photoInfoBean.imgurl
                        .replaceAll("crop_\\d+x\\d+/", "");
                DraweeController controller = ConfigConstants.getDraweeController(
                        ConfigConstants.getImageRequest(photoView, imgurl), photoView);
                photoView.setController(controller);
            }
        };
        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(commonAdapter);
        categoryLayout = new CategoryLayout(this.getContext());
        mHeaderAndFooterWrapper.addHeaderView(categoryLayout);
        recyclerView.setAdapter(mHeaderAndFooterWrapper);

//        categoryLayout.refreshing();
        new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                mPtrRecycleView.setRefreshing();
                return true;
            }
        }).sendEmptyMessageDelayed(0, 300);
//        mPtrRecycleView.setRefreshing();
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
//            LogUtil.e("waterFallCallback"+response);
            processData(response);
        }
    };



    private void processData(String response) {
        MainPhotoBean mainPhotoBean = new Gson().fromJson(response, MainPhotoBean.class);
        List<MainPhotoBean.PhotoInfoBean> data = mainPhotoBean.getList() != null ? mainPhotoBean.getList() : null;
        canload = data.size() >= 15;
        list = data;
        if (canload) {
            mPtrRecycleView.setMode(PullToRefreshBase.Mode.BOTH);
        } else {
            mPtrRecycleView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        }
        if(page == 1){
            commonAdapter.getDatas().clear();
        }
        commonAdapter.getDatas().addAll(list);
        mHeaderAndFooterWrapper.notifyDataSetChanged();
        isFirst = false;
        mPtrRecycleView.onRefreshComplete();

    }

}




