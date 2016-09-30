package com.doruemi.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.widget.RadioGroup;

import com.doruemi.DosnapApp;
import com.doruemi.R;
import com.doruemi.fragment.BaseFragment;
import com.doruemi.fragment.EventFragment;
import com.doruemi.fragment.GalleryFragment;
import com.doruemi.fragment.MainFragment;
import com.doruemi.fragment.MyFragment;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener{

    private RadioGroup rbs_fragment;
    public MainFragment mainFragment;
    public EventFragment eventFragment;
    public MyFragment myFragment;
    public GalleryFragment mGalleryFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initFragments();
        initData();
    }


    private void initData() {
//        if (!(DosnapApp.userid > 0 && DosnapApp.timeout > System.currentTimeMillis() / 1000)) {
//            Intent intent_login = new Intent(MainActivity.this, LoginActivity.class);
//            startActivityForResult(intent_login, DosnapApp.ACTIVITY_LOGIN);
//        }
        setwidth();
        changeFragment(0);
        rbs_fragment.setOnCheckedChangeListener(this);
    }

    private void initFragments() {
    }

    private void initView() {
        rbs_fragment = (RadioGroup) findViewById(R.id.rbs_fragment);
        rbs_fragment.check(R.id.rb_main);
    }



    public void changeFragment(int i){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        hideFragments(transaction);
        switch (i){
            case 0:
                if(mainFragment == null){
                    mainFragment = new MainFragment();
                    transaction.add(R.id.fl_container, mainFragment,"MainFragment");
                }else {
                    transaction.show(mainFragment);
                }
                break;
            case 1:
                if(mGalleryFragment == null){
                    mGalleryFragment = new GalleryFragment();
                    transaction.add(R.id.fl_container, mGalleryFragment,"GalleryFragment");
                }else {
                    transaction.show(mGalleryFragment);
                }
                break;
            case 2:
                if(eventFragment == null){
                    eventFragment = new EventFragment();
                    transaction.add(R.id.fl_container, eventFragment,"EventFragment");
                }else {
                    transaction.show(eventFragment);
                }
                break;
            case 3:
                if(myFragment == null){
                    myFragment = new MyFragment();
                    transaction.add(R.id.fl_container, myFragment,"MyFragment");
                }else {
                    transaction.show(myFragment);
                }
                break;
        }
        transaction.commitAllowingStateLoss();

    }

    private void hideFragments(FragmentTransaction transaction) {
        if (mainFragment != null) {
            transaction.hide(mainFragment);
        }
        if (mGalleryFragment != null) {
            transaction.hide(mGalleryFragment);
        }
        if (eventFragment != null) {
            transaction.hide(eventFragment);
        }
        if (myFragment != null) {
            transaction.hide(myFragment);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i){
            case R.id.rb_main:
                changeFragment(0);
                break;
            case R.id.rb_serarch:
                changeFragment(1);
                break;
            case R.id.rb_camera:
//                changeFragment(BtnFragmentFactory.TAB_MESSAGE);
                break;
            case R.id.rb_message:
                changeFragment(2);
                break;
            case R.id.rb_my:
                changeFragment(3);
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
