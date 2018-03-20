package com.sjl.gankapp.ui.fragment;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.igalata.bubblepicker.BubblePickerListener;
import com.igalata.bubblepicker.adapter.BubblePickerAdapter;
import com.igalata.bubblepicker.model.PickerItem;
import com.igalata.bubblepicker.rendering.BubblePicker;
import com.sjl.gankapp.R;
import com.sjl.gankapp.config.GankConfig;
import com.sjl.gankapp.mvp.presenter.SortPresenter;
import com.sjl.gankapp.mvp.view.SortMvpView;
import com.sjl.gankapp.ui.activity.SortListActivity;
import com.sjl.platform.base.BaseFragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;

/**
 * 分类Fragment
 *
 * @author SJL
 * @date 2017/11/30
 */
public class SortFragment extends BaseFragment<SortMvpView, SortPresenter> implements SortMvpView {
    @BindView(R.id.picker)
    BubblePicker picker;
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
    protected void initView() {
        initSort();
    }

    @Override
    protected SortMvpView obtainMvpView() {
        return this;
    }

    @Override
    protected SortPresenter obtainPresenter() {
        mPresenter = new SortPresenter();
        return (SortPresenter) mPresenter;
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
                startActivity(SortListActivity.newIntent(activity, pickerItem.getTitle()));
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

    @Override
    public void onClick(View v) {

    }
}
