package com.doruemi.fragment;

import android.widget.AbsListView;
import android.widget.ListView;

import com.doruemi.DosnapApp;
import com.doruemi.R;
import com.doruemi.adapter.HomeListAdapter;
import com.doruemi.bean.MainPhotoBean;
import com.doruemi.protocol.PhotoProtocol;
import com.doruemi.util.LogUtil;
import com.doruemi.view.BannerView;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import okhttp3.Call;

/**
 * Created by Administrator on 2016/9/1.
 */
public class HomeFragment extends BaseFragment {

    private PullToRefreshListView listView;
    private List data;
    private List bannerlist;
    private HomeListAdapter mAdapter;
    private BannerView bannerView;


    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    private void getHttp() {
        PhotoProtocol.getHomeAttention(stringCallback, page);
    }

    private StringCallback stringCallback = new StringCallback() {
        @Override
        public void onError(Call call, Exception e, int id) {
            listView.onRefreshComplete();
        }

        @Override
        public void onResponse(String response, int id) {
            MainPhotoBean mainPhotoBean = new Gson().fromJson(response, MainPhotoBean.class);
            bannerlist = mainPhotoBean.getMatchlist();
            List mData = mainPhotoBean.getList();
            if (page == 1) {
                data.clear();
                bannerView.set(bannerlist);
                if (DosnapApp.isuploadPhoto) {
                    data.add(1, mainPhotoBean.getUnfollowing());
                } else {
                    data.add(0, mainPhotoBean.getUnfollowing());
                }
            } else {
                data.addAll(mData);
                data.add(new Random().nextInt(data.size() - 1), mainPhotoBean.getUnfollowing());
            }
            mAdapter.notifyDataSetChanged();
            listView.onRefreshComplete();
        }
    };



    @Override
    protected void initView() {
        listView = (PullToRefreshListView) currentView.findViewById(R.id.list);
        data = new ArrayList();
        bannerlist = new ArrayList<>();
        getHttp();
        ListView listv = listView.getRefreshableView();
        listView.setScrollingWhileRefreshingEnabled(true);
        listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                PullToRefreshBase.Mode mode = listView.getCurrentMode();
                if(mode == PullToRefreshBase.Mode.PULL_FROM_END){
                    page++;
                }else {
                    page = 1;
                }
                bannerView.stopPlay();
                PhotoProtocol.getHomeAttention(stringCallback, page);
            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState == SCROLL_STATE_IDLE){
                    if(view.getLastVisiblePosition() == view.getCount()-1){
                        page++;
                        PhotoProtocol.getHomeAttention(stringCallback,page);
                        LogUtil.e("滑到底部");
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        mAdapter = new HomeListAdapter(getContext(), data);
        bannerView = new BannerView(getActivity());
        listv.addHeaderView(bannerView);
        listView.setAdapter(mAdapter);

    }

    @Override
    public int setContentViewId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onPause() {
        LogUtil.e("homeFragment pause");
        super.onPause();
    }

    @Override
    public void onResume() {
        LogUtil.e("homeFragment resume");
        super.onResume();
    }

    @Override
    public void onDestroy() {
        LogUtil.e("homeFragment destroy");
        super.onDestroy();
    }
}
