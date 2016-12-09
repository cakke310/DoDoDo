package com.doruemi.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.doruemi.R;
import com.doruemi.adapter.HListTagsAdapter;
import com.doruemi.bean.ThemeLabelList;
import com.doruemi.protocol.PhotoProtocol;
import com.doruemi.util.LogUtil;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
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

        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        cid = intent.getIntExtra("cid", 0);
        String cname = intent.getStringExtra("cname");
        title.setText("#"+cname);
        getHttps();
        initHListView();
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
            LogUtil.e("lableCallback"+response);
            try {
                JSONObject obj = new JSONObject(response);
                String code = obj.getString("code");
                if (code.equals("S001")) {
                    LogUtil.e("lableCallback success");
                    ThemeLabelList themeLabelList = new Gson().fromJson(response, ThemeLabelList.class);
                    LogUtil.e("lableCallback="+response);
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
//            LogUtil.e("hotCallback="+response);
            try {
                JSONObject obj = new JSONObject(response);
                String code = obj.getString("code");
                if (code.startsWith("S") || code.endsWith("1")) {
                    JSONArray hotArray = obj.getJSONArray("data");
//                    List<ThemePhotoBean> list = new ArrayList<>();
//                    list.addAll(getPhotoList(hotArray));
//                    mData = list;
//                    mHotData = list;
//                    mAdapter.refresh(mData);
//                    mHeaderAndFooterWrapper.notifyDataSetChanged();
//                    isfirst = false;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
//            mPtrRecycleView.onRefreshComplete();
        }
    };

    private StringCallback detailCallback = new StringCallback() {
        @Override
        public void onError(Call call, Exception e, int id) {
            e.printStackTrace();
//            mPtrRecycleView.onRefreshComplete();
        }

        @Override
        public void onResponse(String response, int id) {
//            LogUtil.e("detailCallback="+response);
            try {
                JSONObject obj = new JSONObject(response);
                String code = obj.getString("code");
                if (code.startsWith("S") || code.endsWith("1")) {
                    JSONArray hotArray = obj.getJSONArray("data");
//                    List<ThemePhotoBean> list = new ArrayList<>();
//                    list.addAll(getPhotoList(hotArray));
//                    if (page == 1) {
//                        mNewData.clear();
//                    }
//                    mNewData.addAll(list);
//                    mData = mNewData;
//                    canload = list.size() >= 15;
//                    if (canload) {
//                        mPtrRecycleView.setMode(PullToRefreshBase.Mode.BOTH);
//                    } else {
//                        mPtrRecycleView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
//                    }
//                    mAdapter.refresh(mData);
//                    mHeaderAndFooterWrapper.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
//            mPtrRecycleView.onRefreshComplete();
        }
    };


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                this.finish();
                break;
            case R.id.tv_newest:
                tv_hotest.setTextColor(getResources().getColor(R.color.gray_c5));
                tv_newest.setTextColor(getResources().getColor(R.color.text_gray2));

                break;
            case R.id.tv_hotest:
                tv_hotest.setTextColor(getResources().getColor(R.color.text_gray2));
                tv_newest.setTextColor(getResources().getColor(R.color.gray_c5));
                break;
        }
    }
}
