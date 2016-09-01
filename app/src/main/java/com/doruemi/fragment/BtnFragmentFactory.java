package com.doruemi.fragment;

import android.util.SparseArray;

/**
 * Created by Administrator on 2016/8/17.
 */
public class BtnFragmentFactory {
    public static final int TAB_MAIN = 0;
    public static final int TAB_SEARCH = 1;
    public static final int TAB_MESSAGE = 2;
    public static final int TAB_MY = 3;

    private static SparseArray<BaseFragment> mFragmentsHashMap = new SparseArray<BaseFragment>();

    public static BaseFragment createFragment(int position){
        BaseFragment mBaseFragment = mFragmentsHashMap.get(position);
        if (mBaseFragment == null){
            switch (position){
                case TAB_MAIN:
                    mBaseFragment = new MainFragment();
                    break;
                case TAB_SEARCH:
                    mBaseFragment = new SearchFragment();
                    break;
                case TAB_MESSAGE:
                    mBaseFragment = new MessageFragment();
                    break;
                case TAB_MY:
                    mBaseFragment = new MyFragment();
                    break;

            }
            mFragmentsHashMap.put(position, mBaseFragment);
        }
        return mBaseFragment;
    }
}
