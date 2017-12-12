package com.sjl.platform.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;

/**
 * BaseFragment
 *
 * @author SJL
 * @date 2017/11/30
 */

public class BaseFragment extends Fragment {
    protected View view;
    protected Context mContext;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public void onDestroyView() {
        if (view != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
        mContext = null;
        super.onDestroyView();
    }
}
