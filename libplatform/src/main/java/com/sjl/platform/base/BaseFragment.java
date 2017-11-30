package com.sjl.platform.base;

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

    @Override
    public void onDestroyView() {
        if (view != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
        super.onDestroyView();
    }
}
