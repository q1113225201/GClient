package com.sjl.gank.ui.tab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sjl.gank.R;
import com.sjl.platform.base.BaseFragment;

/**
 * 分类Fragment
 *
 * @author SJL
 * @date 2017/11/30
 */
public class SortFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sort, container, false);
        return view;
    }
}
