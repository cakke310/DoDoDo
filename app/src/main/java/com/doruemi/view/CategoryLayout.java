package com.doruemi.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.doruemi.R;
import com.doruemi.protocol.PhotoProtocol;
import com.doruemi.util.LogUtil;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Administrator on 2016-10-15.
 */
public class CategoryLayout extends LinearLayout {
    private Context mContext;
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
    }

    private StringCallback categoryCallback = new StringCallback() {
        @Override
        public void onError(Call call, Exception e, int id) {

        }

        @Override
        public void onResponse(String response, int id) {
            LogUtil.e("categoryCallback--"+response);
        }
    };

    private StringCallback recommandCallback = new StringCallback() {
        @Override
        public void onError(Call call, Exception e, int id) {

        }

        @Override
        public void onResponse(String response, int id) {
            LogUtil.e("recommandCallback--"+response);
        }
    };

    public void refreshing() {
        PhotoProtocol.getCategoryList(categoryCallback);
        PhotoProtocol.getRecommandUsers(recommandCallback, 1);
    }
}
