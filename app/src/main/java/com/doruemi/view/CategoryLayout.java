package com.doruemi.view;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doruemi.R;
import com.doruemi.adapter.SearchCategoryAdapter;
import com.doruemi.adapter.SearchRecommandAdapter;
import com.doruemi.bean.CategoryBean;
import com.doruemi.bean.RecommandUser;
import com.doruemi.protocol.PhotoProtocol;
import com.doruemi.util.LogUtil;
import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import it.sephiroth.android.library.widget.HListView;
import okhttp3.Call;

/**
 * Created by Administrator on 2016-10-15.
 */
public class CategoryLayout extends LinearLayout {
    private Context mContext;
    private TextView mTvAll;
    private HListView mHlistRecommand;
    private RelativeLayout mLayoutRecommand;
    private RecyclerView mRecyclerView;
    private List<CategoryBean.CategoryInfo> categoryData;
    private List<RecommandUser> recommandData;
    private SearchCategoryAdapter mCategoryAdapter;
    private SearchRecommandAdapter mRecommandAdapter;

    public CategoryLayout(Context context) {
        this(context,null);
    }

    public CategoryLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CategoryLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        LayoutInflater.from(context).inflate(R.layout.layout_category,null);
        mTvAll = (TextView) findViewById(R.id.tv_all);
        mHlistRecommand = (HListView) findViewById(R.id.hlist_recommand);
        mLayoutRecommand = (RelativeLayout) findViewById(R.id.rl_recommand);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        categoryData = new ArrayList<>();
        recommandData = new ArrayList<>();
        initData();

    }

    private void initData() {
//        mCategoryAdapter = new SearchCategoryAdapter(getContext(), categoryData);
        mRecommandAdapter = new SearchRecommandAdapter(getContext(), recommandData);
        mHlistRecommand.setAdapter(mRecommandAdapter);

//        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2, LinearLayoutManager.VERTICAL,false));
//        mRecyclerView.setAdapter(mCategoryAdapter);
    }

    private StringCallback categoryCallback = new StringCallback() {
        @Override
        public void onError(Call call, Exception e, int id) {

        }

        @Override
        public void onResponse(String response, int id) {
            LogUtil.e("categoryData"+response);
            try {
                JSONObject obj = new JSONObject(response);
                String code = obj.getString("code");
                if (code.startsWith("S") || code.endsWith("1")) {
                    CategoryBean categoryBean = new Gson().fromJson(response, CategoryBean.class);
                    categoryData = categoryBean.data;
                    mCategoryAdapter.appendDatas(categoryData);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private StringCallback recommandCallback = new StringCallback() {
        @Override
        public void onError(Call call, Exception e, int id) {

        }

        @Override
        public void onResponse(String response, int id) {
            LogUtil.e("recommandData---"+response);
            JSONArray jsonArray;
            try {
                jsonArray = new JSONArray(response);
//                if (jsonArray.length() < 1) {
//                    mLayoutRecommand.setVisibility(View.GONE);
//                    return;
//                } else {
//                    mLayoutRecommand.setVisibility(View.VISIBLE);
//                }
                JSONObject jsonObject;
                RecommandUser bean;
                List<RecommandUser> list = new ArrayList<>();
                int size = jsonArray.length();
                for (int i = 0; i < size; i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                    bean = new Gson().fromJson(jsonObject.toString(), RecommandUser.class);
                    list.add(bean);
                }
                recommandData = list;
                mRecommandAdapter.appendDatas(recommandData);

            } catch (JSONException e) {
                e.printStackTrace();
                mLayoutRecommand.setVisibility(View.GONE);
            }
        }

    };

    public void refreshing() {
        PhotoProtocol.getCategoryList(categoryCallback);
        PhotoProtocol.getRecommandUsers(recommandCallback, 1);
    }
}
