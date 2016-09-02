package com.doruemi.fragment;

import com.doruemi.R;
import com.doruemi.util.LogUtil;

/**
 * Created by Administrator on 2016/9/1.
 */
public class HomeFragment extends BaseFragment {
    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        LogUtil.e("homeFragment init");
    }

    @Override
    public int setContentViewId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onPause() {
        LogUtil.e("homeFragment pause");
        super.onPause();
    }

    @Override
    public void onResume() {
        LogUtil.e("homeFragment resume");
        super.onResume();
    }

    @Override
    public void onDestroy() {
        LogUtil.e("homeFragment destroy");
        super.onDestroy();
    }
}
