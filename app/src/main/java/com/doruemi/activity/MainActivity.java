package com.doruemi.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.doruemi.R;
import com.doruemi.fragment.BaseFragment;
import com.doruemi.fragment.BtnFragmentFactory;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity {

    private ArrayList<BaseFragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initFragments();
        initData();
    }


    private void initData() {
        changeFragment(0);
    }

    private void initFragments() {
        fragments = new ArrayList<>();
        fragments.add(BtnFragmentFactory.createFragment(BtnFragmentFactory.TAB_MAIN));
    }

    private void initView() {
    }



    public void changeFragment(int i){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction beginTransaction = fm.beginTransaction();
        BaseFragment fragment = fragments.get(i);
        beginTransaction.replace(R.id.fl_container,fragment);
        beginTransaction.commit();
    }
}
