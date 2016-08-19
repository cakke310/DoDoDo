package com.doruemi.fragment;

import android.util.SparseArray;

/**
 * Created by Administrator on 2016/8/17.
 */
public class BtnFragmentFactory {
    public static final int TAB_MAIN = 0;
    public static final int TAB_SEARCH = 1;
    public static final int TAB_MESSAGE = 1;
    public static final int TAB_MY = 2;

    private static SparseArray<BaseFragment> mFragmentsHashMap = new SparseArray<BaseFragment>();

    public static BaseFragment createFragment(int position){
        BaseFragment mBaseFragment = mFragmentsHashMap.get(position);
        if (mBaseFragment == null){
            switch (position){
                case TAB_MAIN:
                    mBaseFragment = new MainFragment();
                    break;
            }
            mFragmentsHashMap.put(position, mBaseFragment);
        }
        return mBaseFragment;
    }


}
