package com.doruemi.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.doruemi.R;
import com.doruemi.util.LogUtil;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/17.
 */
public class MainFragment extends BaseFragment{

    private TabLayout tabLayout;
    private ViewPager pager;

    @Override
    protected void initListener() {
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void initData() {
        pager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            private String[] mTitles = new String[]{"关注", "精选"};

            @Override
            public BaseFragment getItem(int position) {
                return MainFragmentFactory.createFragment(position);
            }

            @Override
            public int getCount() {
                return mTitles.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitles[position];
            }
        });
        tabLayout.setupWithViewPager(pager);
        pager.setOffscreenPageLimit(2);
    }

    @Override
    protected void initView() {
        tabLayout = (TabLayout) currentView.findViewById(R.id.tabLayout);
        pager = (ViewPager) currentView.findViewById(R.id.pager);
    }

    @Override
    public int setContentViewId() {
        return R.layout.fragment_main;
    }
}
