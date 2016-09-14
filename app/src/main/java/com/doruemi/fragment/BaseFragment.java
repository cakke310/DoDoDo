package com.doruemi.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/8/17.
 */
public abstract class BaseFragment extends Fragment{

    public View currentView;
    public int page = 1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        currentView = inflater.inflate(setContentViewId(),null,false);
        initView();
        initData();
        initListener();
        return  currentView;
    }

    protected abstract void initListener();

    protected abstract void initData();

    protected abstract void initView();

    public abstract int setContentViewId();


}
