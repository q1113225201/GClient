package com.sjl.gankapp.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.sjl.gankapp.R;
import com.sjl.gankapp.mvp.presenter.GankMainPresenter;
import com.sjl.gankapp.mvp.view.GankMainMvpView;
import com.sjl.gankapp.ui.fragment.AboutFragment;
import com.sjl.gankapp.ui.fragment.IndexFragment;
import com.sjl.gankapp.ui.fragment.SortFragment;
import com.sjl.platform.base.BaseFragmentActivity;
import com.sjl.platform.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 首页
 *
 * @author SJL
 * @date 2017/12/14
 */
public class MainActivity extends BaseFragmentActivity<GankMainMvpView, GankMainPresenter> implements GankMainMvpView {
    private static final String TAG = "MainActivity";
    @BindView(R.id.rbIndex)
    RadioButton rbIndex;
    @BindView(R.id.rbSort)
    RadioButton rbSort;
    @BindView(R.id.rbMine)
    RadioButton rbMine;
    @BindView(R.id.rgTab)
    RadioGroup rgTab;
    @BindView(R.id.vpContent)
    ViewPager vpContent;

    private List<Integer> tabList = new ArrayList<>();
    private List<Fragment> tabContentList = new ArrayList<>();

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        //初始化tab
        tabList.add(R.id.rbIndex);
        tabList.add(R.id.rbSort);
        tabList.add(R.id.rbMine);

        tabContentList.add(new IndexFragment());
        tabContentList.add(new SortFragment());
        tabContentList.add(new AboutFragment());
        vpContent.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return tabContentList.get(position);
            }

            @Override
            public int getCount() {
                return tabContentList.size();
            }
        });
        rgTab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                LogUtil.i(TAG, "checkedId=" + checkedId);
                vpContent.setCurrentItem(tabList.indexOf(checkedId));
            }
        });
        vpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                LogUtil.i(TAG, "vpContent.getCurrentItem()=" + vpContent.getCurrentItem());
                findViewById(tabList.get(vpContent.getCurrentItem())).performClick();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        findViewById(tabList.get(0)).performClick();
    }

    @Override
    protected GankMainMvpView obtainMvpView() {
        return this;
    }

    @Override
    protected GankMainPresenter obtainPresenter() {
        mPresenter = new GankMainPresenter();
        return (GankMainPresenter) mPresenter;
    }

    @Override
    public void onClick(View v) {

    }
}