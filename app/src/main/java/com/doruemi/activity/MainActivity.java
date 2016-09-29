package com.doruemi.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.widget.RadioGroup;

import com.doruemi.DosnapApp;
import com.doruemi.R;
import com.doruemi.fragment.BaseFragment;
import com.doruemi.fragment.BtnFragmentFactory;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener{

    private ArrayList<BaseFragment> fragments;
    private RadioGroup rbs_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initFragments();
        initData();
    }


    private void initData() {
        if (!(DosnapApp.userid > 0 && DosnapApp.timeout > System.currentTimeMillis() / 1000)) {
            Intent intent_login = new Intent(MainActivity.this, LoginActivity.class);
            startActivityForResult(intent_login, DosnapApp.ACTIVITY_LOGIN);
        }
        setwidth();
        changeFragment(0);
        rbs_fragment.setOnCheckedChangeListener(this);
    }

    private void initFragments() {
        fragments = new ArrayList<>();
        fragments.add(BtnFragmentFactory.createFragment(BtnFragmentFactory.TAB_MAIN));
        fragments.add(BtnFragmentFactory.createFragment(BtnFragmentFactory.TAB_SEARCH));
        fragments.add(BtnFragmentFactory.createFragment(BtnFragmentFactory.TAB_MESSAGE));
        fragments.add(BtnFragmentFactory.createFragment(BtnFragmentFactory.TAB_MY));
    }

    private void initView() {
        rbs_fragment = (RadioGroup) findViewById(R.id.rbs_fragment);
        rbs_fragment.check(R.id.rb_main);
    }



    public void changeFragment(int i){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction beginTransaction = fm.beginTransaction();
        BaseFragment fragment = fragments.get(i);
        beginTransaction.replace(R.id.fl_container,fragment);
        beginTransaction.commit();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i){
            case R.id.rb_main:
                changeFragment(BtnFragmentFactory.TAB_MAIN);
                break;
            case R.id.rb_serarch:
                changeFragment(BtnFragmentFactory.TAB_SEARCH);
                break;
            case R.id.rb_camera:
//                changeFragment(BtnFragmentFactory.TAB_MESSAGE);
                break;
            case R.id.rb_message:
                changeFragment(BtnFragmentFactory.TAB_MESSAGE);
                break;
            case R.id.rb_my:
                changeFragment(BtnFragmentFactory.TAB_MY);
                break;

        }

    }

    private void setwidth() {
        if (DosnapApp.devicewidth == 100) {
            DisplayMetrics displaymetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            DosnapApp.devicewidth = displaymetrics.widthPixels;
            DosnapApp.deviceheight = displaymetrics.heightPixels;
        }
    }
}
