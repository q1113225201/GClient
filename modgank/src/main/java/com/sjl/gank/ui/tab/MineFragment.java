package com.sjl.gank.ui.tab;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sjl.gank.R;
import com.sjl.platform.base.BaseFragment;
import com.sjl.platform.util.LogUtil;

/**
 * 我的Fragment
 *
 * @author SJL
 * @date 2017/11/30
 */
public class MineFragment extends BaseFragment {
    private static final String TAG = "MineFragment";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mine, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LogUtil.i(TAG,"MineFragment onViewCreated");

    }
}
