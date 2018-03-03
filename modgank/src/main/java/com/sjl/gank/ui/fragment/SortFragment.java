package com.sjl.gank.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.igalata.bubblepicker.BubblePickerListener;
import com.igalata.bubblepicker.adapter.BubblePickerAdapter;
import com.igalata.bubblepicker.model.PickerItem;
import com.igalata.bubblepicker.rendering.BubblePicker;
import com.sjl.gank.R;
import com.sjl.gank.config.GankConfig;
import com.sjl.gank.ui.activity.SortListActivity;
import com.sjl.platform.base.BaseFragment;
import com.sjl.platform.base.MvpView;
import com.sjl.platform.base.Presenter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

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

    private BubblePicker picker;
    private List<String> list;
    private int[] colors = {android.R.color.holo_red_light, android.R.color.holo_green_light, android.R.color.holo_blue_light,
            android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_green_light,
            android.R.color.holo_green_light, android.R.color.holo_blue_light, android.R.color.holo_red_light};
    private BubblePickerAdapter adapter;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_sort;
    }

    @Override
    protected MvpView obtainMvpView() {
        return this;
    }

    @Override
    protected Presenter obtainPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        picker = view.findViewById(R.id.picker);
        initSort();
    }

    private void initSort() {
        list = GankConfig.sortList;
        adapter = new BubblePickerAdapter() {
            @Override
            public int getTotalCount() {
                return list.size();
            }

            @NotNull
            @Override
            public PickerItem getItem(int i) {
                PickerItem item = new PickerItem();
                item.setTitle(list.get(i));
                item.setTextColor(Color.WHITE);
                item.setColor(ContextCompat.getColor(getActivity(), colors[i]));
                return item;
            }
        };
        picker.setCenterImmediately(true);
        picker.setBubbleSize(10);
        picker.setAdapter(adapter);
        picker.setListener(new BubblePickerListener() {
            @Override
            public void onBubbleSelected(PickerItem pickerItem) {
                startActivity(SortListActivity.newIntent(mContext, pickerItem.getTitle()));
            }

            @Override
            public void onBubbleDeselected(PickerItem pickerItem) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        picker.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        picker.onPause();
    }
}
