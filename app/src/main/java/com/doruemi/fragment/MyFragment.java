package com.doruemi.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doruemi.R;
import com.doruemi.bean.MyFragmentBean;
import com.doruemi.databinding.FragmentMyBinding;

/**
 * Created by Administrator on 2016-08-20.
 */
public class MyFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        FragmentMyBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my, container, false);
        binding.setBean(new MyFragmentBean("个人主页测试"));
        return binding.getRoot();
    }
}
