package com.sjl.gankapp.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.sjl.gankapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 展开菜单
 *
 * @author SJL
 * @date 2017/3/22
 */

public class ExpandMenu extends LinearLayout {
    private static final String TAG = "ExpandMenu";

    public static final int TOP = 0;
    public static final int BOTTOM = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    //菜单位置
    private int menuGravity = TOP;
    //菜单图片
    private Drawable menuDrawable;
    //菜单背景色
    private ColorStateList menuBackgroundTintList;

    //显示的按钮
    private FloatingActionButton floatingActionButton;
    //菜单列表容器
    private LinearLayout menuLayout;
    //菜单列表
    private List<View> menuList;

    //是否已展开
    private boolean isExpanded = false;
    //正在播放动画
    private boolean isAnimating;
    //动画时间
    private int duration = 50;
    //菜单点击事件
    private OnMenuItemClickListener onMenuItemClickListener;

    public interface OnMenuItemClickListener {
        void onClick(View view);
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        this.onMenuItemClickListener = onMenuItemClickListener;
    }

    public ExpandMenu(Context context) {
        this(context, null);
    }

    public ExpandMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    /**
     * 初始化
     *
     * @param attrs
     */
    private void init(AttributeSet attrs) {
        //初始化属性
        initAttrs(attrs);
        //设置方向
        setOrientation((menuGravity == TOP || menuGravity == BOTTOM) ? VERTICAL : HORIZONTAL);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //布局加载后初始化菜单
        initMenuList();
    }

    /**
     * 初始化菜单列表
     */
    private void initMenuList() {
        menuList = new ArrayList<>();
        for (int i = 0; i < getChildCount(); i++) {
            //将菜单添加进菜单列表
            View childView = getChildAt(i);
            childView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onMenuItemClickListener != null) {
                        onMenuItemClickListener.onClick(v);
                    }
                }
            });
            menuList.add(childView);
        }
        removeAllViews();
        //初始化菜单容器
        menuLayout = new LinearLayout(getContext());
        menuLayout.setOrientation(getOrientation());
        //初始化展开按钮
        floatingActionButton = new FloatingActionButton(getContext());
        if (menuDrawable != null) {
            floatingActionButton.setImageDrawable(menuDrawable);
        }
        if (menuBackgroundTintList != null) {
            floatingActionButton.setBackgroundTintList(menuBackgroundTintList);
        }
        //根据位置添加菜单
        if (menuGravity == TOP || menuGravity == LEFT) {
            addView(menuLayout);
            addView(floatingActionButton);
        } else {
            addView(floatingActionButton);
            addView(menuLayout);
        }
        //设置菜单容器透明
//        ViewCompat.setAlpha(menuLayout, 0);
        menuLayout.setAlpha(0);
        
        floatingActionButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //切换菜单状态
                toggle();
            }
        });
    }

    /**
     * 切换菜单状态
     */
    public void toggle() {
        synchronized (TAG) {
            if (isAnimating) {
                return;
            }
            if (isExpanded) {
                closeMenu();
            } else {
                openMenu();
            }
            requestFocus();
            isExpanded = !isExpanded;
            floatingActionButton.setSelected(isExpanded);
        }
    }

    /**
     * 初始化属性
     *
     * @param attrs
     */
    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ExpandMenu);
        menuGravity = typedArray.getInt(R.styleable.ExpandMenu_menuGravity, TOP);
        menuDrawable = typedArray.getDrawable(R.styleable.ExpandMenu_menuDrawable);
        menuBackgroundTintList = typedArray.getColorStateList(R.styleable.ExpandMenu_menuBackgroundTintList);
        typedArray.recycle();
    }

    /**
     * 展开菜单
     */
    public void openMenu() {
        addMenuItem();
        animateInMenuItems();
    }

    /**
     * 添加菜单项
     */
    private void addMenuItem() {
        //设置菜单容器不透明
        ViewCompat.setAlpha(menuLayout, 1f);
        for (View view : menuList) {
            menuLayout.addView(view);
        }
    }

    /**
     * 菜单进入动画
     */
    private void animateInMenuItems() {
        int count = menuLayout.getChildCount();
        for (int i = 0; i < count; i++) {
            animateInMenuItem(menuLayout.getChildAt(i), (menuGravity == TOP || menuGravity == LEFT) ? count - 1 - i : i);
        }
    }

    /**
     * 单个菜单进入动画
     *
     * @param view
     * @param index 第几个显示的菜单，可根据该值设置启动延迟
     */
    private void animateInMenuItem(View view, int index) {
        //设置开始状态
        ViewCompat.setScaleX(view, 0.25f);
        ViewCompat.setScaleY(view, 0.25f);
        ViewCompat.setAlpha(view, 0);

        //进入动画
        ViewCompat.animate(view)
                .setDuration(duration * 4)
                .scaleX(1f)
                .scaleY(1f)
                .alpha(1f)
                .setStartDelay(duration * index)//动画启动延迟
                .setInterpolator(new FastOutSlowInInterpolator())
                .setListener(new ViewPropertyAnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(View view) {
                        super.onAnimationStart(view);
//                        ViewCompat.setAlpha(view, 1f);
                        isAnimating = true;
                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        super.onAnimationEnd(view);
                        isAnimating = false;
                    }
                })
                .start();
    }

    /**
     * 关闭菜单
     */
    public void closeMenu() {
        //菜单透明度渐变
        ViewCompat.animate(menuLayout)
                .setDuration(duration)
                .setInterpolator(new FastOutLinearInInterpolator())
                .alpha(0f)
                .setListener(new ViewPropertyAnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(View view) {
                        super.onAnimationStart(view);
                        isAnimating = true;
                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        super.onAnimationEnd(view);
                        isAnimating = false;
                        //动画结束后移除
                        menuLayout.removeAllViews();
                    }
                })
                .start();
    }
}
