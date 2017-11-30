package com.sjl.gank.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.sjl.gank.R;
import com.sjl.gank.view.bean.TabItem;

import java.util.ArrayList;
import java.util.List;

/**
 * TabGroupView
 *
 * @author SJL
 * @date 2017/11/30
 */

public class TabGroupView extends RadioGroup {
    public TabGroupView(Context context) {
        this(context, null);
    }

    public TabGroupView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    /**
     * 初始化
     *
     * @param attrs
     */
    private void init(AttributeSet attrs) {
        setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id = group.getCheckedRadioButtonId();
            }
        });
        initAttrs(attrs);
        addTab(new TabItem(0, R.drawable.ic_home,"首页",R.color.normal_selected_color));
        addTab(new TabItem(1, R.drawable.ic_person,"我的",R.color.normal_selected_color));
    }

    private int tabBgColor;
    private int tabNormalColor;
    private int tabSelectedColor;
    private float textSize = 12f;
    private int tabItemPadding = 5;

    /**
     * 初始化属性
     *
     * @param attrs
     */
    private void initAttrs(AttributeSet attrs) {

    }

    private List<TabItem> tabItemList = new ArrayList<>();

    public void addTab(TabItem tabItem) {
        tabItemList.add(tabItem);
        addView(initTabItem(tabItem));
    }

    private View initTabItem(TabItem tabItem) {
        RadioButton radioButton = new RadioButton(getContext());
        LinearLayout.LayoutParams layoutParams = new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layoutParams.weight = 1;
        radioButton.setLayoutParams(layoutParams);
        radioButton.setCompoundDrawables(null, ContextCompat.getDrawable(getContext(),tabItem.getTopDrawable()),null,null);
        radioButton.setTextColor(ContextCompat.getColor(getContext(),tabItem.getTextColor()));
        radioButton.setTextSize(textSize);
        radioButton.setGravity(Gravity.CENTER);
        radioButton.setPadding(tabItemPadding,tabItemPadding,tabItemPadding,tabItemPadding);
        radioButton.setButtonDrawable(null);
        return radioButton;
    }
}
