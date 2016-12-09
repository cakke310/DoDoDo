package com.doruemi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.doruemi.DosnapApp;
import com.doruemi.R;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * Created by c_xuwei-010 on 2016/12/6.
 */
public class UserIndexActivity extends Activity{

    private PullToRefreshListView listView;
    private ImageView banuserView;
    private int userid;
    private String username;
    private TextView titleView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userindex);
        listView = (PullToRefreshListView) findViewById(R.id.list);
        banuserView = (ImageView) findViewById(R.id.more);
        try {
            userid = getIntent().getIntExtra("userid", 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            username = getIntent().getStringExtra("username");
            if (username == null)
                username = "";
        } catch (Exception e) {
            e.printStackTrace();
        }

        titleView = (TextView)findViewById(R.id.title);
        if(userid== DosnapApp.userid) {
            titleView.setText(R.string.myhome);
            banuserView.setVisibility(View.GONE);
        } else {
            titleView.setText(R.string.home);
        }

        initListView();

    }

    private void initListView() {

    }
}
