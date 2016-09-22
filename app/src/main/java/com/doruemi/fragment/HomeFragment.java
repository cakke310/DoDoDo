package com.doruemi.fragment;

import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import com.doruemi.R;
import com.doruemi.adapter.HomeListAdapter;
import com.doruemi.bean.MainPhotoBean;
import com.doruemi.protocol.PhotoProtocol;
import com.doruemi.util.LogUtil;
import com.doruemi.view.BannerView;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshRecyclerView;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

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
        data = new ArrayList();
        bannerlist = new ArrayList<>();
        getHttp();
    }

    private void getHttp() {
        PhotoProtocol.getHomeAttention(stringCallback, page);
    }

    private StringCallback stringCallback = new StringCallback() {
        @Override
        public void onError(Call call, Exception e, int id) {
            LogUtil.e(e.toString());
        }

        @Override
        public void onResponse(String response, int id) {
            MainPhotoBean mainPhotoBean = new Gson().fromJson(response, MainPhotoBean.class);
            data = mainPhotoBean.getList();
            bannerlist = mainPhotoBean.getMatchlist();
        }
    };



    @Override
    protected void initView() {
        listView = (PullToRefreshListView) currentView.findViewById(R.id.list);
        ListView listv = listView.getRefreshableView();
        listView.setScrollingWhileRefreshingEnabled(true);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                PullToRefreshBase.Mode mode = listView.getCurrentMode();
                if(mode == PullToRefreshBase.Mode.PULL_FROM_END){
                    page++;
                }else {
                    page = 1;
                }
                PhotoProtocol.getHomeAttention(stringCallback, page);
            }
        });

        mAdapter = new HomeListAdapter(this.getActivity(), data);
        bannerView = new BannerView(getActivity());
        listv.addHeaderView(bannerView);
        listView.setAdapter(mAdapter);
        bannerView.set(bannerlist);
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
