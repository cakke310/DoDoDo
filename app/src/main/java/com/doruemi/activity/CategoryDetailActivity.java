package com.doruemi.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.doruemi.R;
import com.doruemi.adapter.DividerItemDecoration;
import com.doruemi.adapter.HListTagsAdapter;
import com.doruemi.adapter.ThemeDetailAdapterN;
import com.doruemi.bean.ThemeLabelList;
import com.doruemi.bean.ThemePhotoBean;
import com.doruemi.protocol.PhotoProtocol;
import com.doruemi.util.Hutils;
import com.doruemi.util.LogUtil;
import com.doruemi.util.UIUtils;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshRecyclerView;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import it.sephiroth.android.library.widget.HListView;
import okhttp3.Call;

/**
 * Created by c_xuwei-010 on 2016/12/6.
 */
public class CategoryDetailActivity extends Activity implements View.OnClickListener{

    private ImageView iv_back;
    private TextView title;
    private HListView hlist_recommand;
    private List hlistData;
    private int cid;
    private HListTagsAdapter hListTagsAdapter;
    private int page;
    private TextView tv_newest;
    private TextView tv_hotest;
    private PullToRefreshRecyclerView mPtrRecycleView;
    private List mHotData;
    private List mNewData;
    private List mData;
    private ThemeDetailAdapterN mAdapter;
    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;
    private TextView footerView;
    private int cur = 0;
    private boolean canload = false;
    private boolean isfirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorydetail);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        title = (TextView) findViewById(R.id.title);
        hlist_recommand = (HListView) findViewById(R.id.hlist_recommand);
        hlistData = new ArrayList();
        iv_back.setOnClickListener(this);
        tv_newest = (TextView) findViewById(R.id.tv_newest);
        tv_hotest = (TextView) findViewById(R.id.tv_hotest);
        tv_newest.setOnClickListener(this);
        tv_hotest.setOnClickListener(this);

        mPtrRecycleView = (PullToRefreshRecyclerView) findViewById(R.id.recycler_view);
        mHotData = new ArrayList<>();
        mNewData = new ArrayList<>();
        mData = new ArrayList<>();

        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        cid = intent.getIntExtra("cid", 0);
        String cname = intent.getStringExtra("cname");
        title.setText("#"+cname);
//        getHttps();
        initHListView();
        initPTRRecycleView();
    }

    private void initPTRRecycleView() {
        mPtrRecycleView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        RecyclerView recyclerView = mPtrRecycleView.getRefreshableView();
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL_LIST));
        mAdapter = new ThemeDetailAdapterN(this, mData);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        initHeaderAndFooter();
        recyclerView.setAdapter(mHeaderAndFooterWrapper);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    int lastVisibleItemPosition = gridLayoutManager.findLastVisibleItemPosition();
                    if (lastVisibleItemPosition >= gridLayoutManager.getItemCount() / 5 && canload) {
                        if (cur == 0) {
                            page++;
                            PhotoProtocol.getCategoryNew(detailCallback, cid, page);
                        }
                    }
                }
            }
        });
        new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                mPtrRecycleView.setRefreshing();
                return true;
            }
        }).sendEmptyMessageDelayed(0, 300);
        mPtrRecycleView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<RecyclerView>() {
            @Override
            public void onRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                PullToRefreshBase.Mode mode = mPtrRecycleView.getCurrentMode();
                if(mode == PullToRefreshBase.Mode.PULL_FROM_END){
                    if(cur ==1){
                        LogUtil.e("onRefresh cur ==1");
                        page++;
                        PhotoProtocol.getCategoryNew(detailCallback, cid, page);
                    }
                }else {
                    LogUtil.e("onRefresh cur !=1");
                    page = 1;
                    PhotoProtocol.getCategoryLabel(lableCallback, cid);
                    mData.clear();
                    if (cur == 1) {
                        LogUtil.e("");
                        mHotData.clear();
                        canload = false;
                        PhotoProtocol.getCategoryHot(hotCallback, cid);
                    } else {
                        mNewData.clear();
                        canload = true;
                        PhotoProtocol.getCategoryNew(detailCallback, cid, page);
                    }
                }
            }
        });
    }

    private void initHeaderAndFooter() {
        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(mAdapter);
        footerView = new TextView(this);
        footerView.setText(getResources().getString(R.string.no_more_data));
        footerView.setTextColor(getResources().getColor(R.color.gray_4D4D4D));
        footerView.setGravity(Gravity.CENTER);
        footerView.setPadding(0, UIUtils.dip2px(10), 0, UIUtils.dip2px(10));
        footerView.setVisibility(View.GONE);
        mHeaderAndFooterWrapper.addFootView(footerView);
    }

    private void getHttps() {
        LogUtil.e("getHttps");
        PhotoProtocol.getCategoryLabel(lableCallback, cid);
        PhotoProtocol.getCategoryNew(detailCallback, cid, page);
        PhotoProtocol.getCategoryHot(hotCallback, cid);


    }

    private void initHListView() {
        hListTagsAdapter = new HListTagsAdapter(this, hlistData);
        hlist_recommand.setAdapter(hListTagsAdapter);
    }

    private StringCallback lableCallback = new StringCallback(){

        @Override
        public void onError(Call call, Exception e, int id) {
            LogUtil.e("onError"+e);
        }

        @Override
        public void onResponse(String response, int id) {
            try {
                JSONObject obj = new JSONObject(response);
                String code = obj.getString("code");
                if (code.equals("S001")) {
                    ThemeLabelList themeLabelList = new Gson().fromJson(response, ThemeLabelList.class);
                    hlistData = themeLabelList.tags;
                    hListTagsAdapter.appendDatas(hlistData);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private StringCallback hotCallback = new StringCallback() {
        @Override
        public void onError(Call call, Exception e, int id) {
            e.printStackTrace();
        }

        @Override
        public void onResponse(String response, int id) {
            LogUtil.e("hotCallback="+response);
            try {
                JSONObject obj = new JSONObject(response);
                String code = obj.getString("code");
                if (code.startsWith("S") || code.endsWith("1")) {
                    JSONArray hotArray = obj.getJSONArray("data");
                    List<ThemePhotoBean> list = new ArrayList<>();
                    list.addAll(getPhotoList(hotArray));
                    mData = list;
                    mHotData = list;
                    mAdapter.refresh(mData);
                    mHeaderAndFooterWrapper.notifyDataSetChanged();
                    isfirst = false;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mPtrRecycleView.onRefreshComplete();
        }
    };

    private StringCallback detailCallback = new StringCallback() {
        @Override
        public void onError(Call call, Exception e, int id) {
            e.printStackTrace();
            mPtrRecycleView.onRefreshComplete();
        }

        @Override
        public void onResponse(String response, int id) {
//            LogUtil.e("detailCallback="+response);
            try {
                JSONObject obj = new JSONObject(response);
                String code = obj.getString("code");
                if (code.startsWith("S") || code.endsWith("1")) {
                    JSONArray hotArray = obj.getJSONArray("data");
                    List<ThemePhotoBean> list = new ArrayList<>();
                    list.addAll(getPhotoList(hotArray));
                    if (page == 1) {
                        mNewData.clear();
                    }
                    mNewData.addAll(list);
                    mData = mNewData;
                    canload = list.size() >= 15;
                    if (canload) {
                        mPtrRecycleView.setMode(PullToRefreshBase.Mode.BOTH);
                    } else {
                        mPtrRecycleView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                    }
                    mAdapter.refresh(mData);
                    mHeaderAndFooterWrapper.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mPtrRecycleView.onRefreshComplete();
        }
    };

    private List<ThemePhotoBean> getPhotoList(JSONArray array) throws JSONException {
        JSONObject jsonObject;
        List<ThemePhotoBean> list = new ArrayList<>();
        ThemePhotoBean bean;
        for (int i = 0; i < array.length(); i++) {
            jsonObject = array.getJSONObject(i);
            bean = new Gson().fromJson(jsonObject.toString(), ThemePhotoBean.class);
            list.add(bean);
        }
        return list;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                this.finish();
                break;
            case R.id.tv_newest:
                cur = 0;
                tv_hotest.setTextColor(getResources().getColor(R.color.gray_c5));
                tv_newest.setTextColor(getResources().getColor(R.color.text_gray2));
                mData = mNewData;
                mAdapter.refresh(mData);
                footerView.setVisibility(View.GONE);
                mHeaderAndFooterWrapper.notifyDataSetChanged();

                break;
            case R.id.tv_hotest:
                cur = 1;
                tv_hotest.setTextColor(getResources().getColor(R.color.text_gray2));
                tv_newest.setTextColor(getResources().getColor(R.color.gray_c5));
                if (isfirst) {
                    PhotoProtocol.getCategoryHot(hotCallback, cid);
                }
                mData = mHotData;
                mAdapter.refresh(mData);
                footerView.setVisibility(View.VISIBLE);
                mHeaderAndFooterWrapper.notifyDataSetChanged();
                break;
        }
    }
}
