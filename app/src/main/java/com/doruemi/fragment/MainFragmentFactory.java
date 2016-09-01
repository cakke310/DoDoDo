package com.doruemi.fragment;

import android.util.SparseArray;

/**
 * Created by Administrator on 2016/9/1.
 */
public class MainFragmentFactory {
    public static final int TAB_HOME = 0;
    public static final int TAB_SPECIAL = 1;

    private static SparseArray<BaseFragment> mFragmentsHashMap = new SparseArray<BaseFragment>();

    public static BaseFragment createFragment(int position){
        BaseFragment baseFragment = mFragmentsHashMap.get(position);
        if(baseFragment == null){
            switch (position){
                case TAB_HOME:
                    baseFragment = new HomeFragment();
                    break;
                case TAB_SPECIAL:
                    baseFragment = new SpecialFragment();
                    break;
            }
            mFragmentsHashMap.put(position,baseFragment);
        }
        return baseFragment;
    }
}
