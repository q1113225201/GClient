package com.sjl.gank.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.sjl.gank.R;
import com.sjl.platform.base.adapter.CommonRVAdapter;

import java.util.List;

/**
 * MenuPopWindow
 *
 * @author SJL
 * @date 2017/12/11 21:45
 */

public class MenuPopWindow implements View.OnClickListener {
    private Context context;
    private View parentView;
    public PopupWindow popupWindow;
    private LinearLayout llParent;
    private RecyclerView rvMenu;
    private List<String> list;
    private CommonRVAdapter<String> adapter;
    private boolean isShow;
    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }
    private OnItemClickListener onItemClickListener;

    public MenuPopWindow(Context context,View parentView, List<String> list, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.parentView = parentView;
        this.list = list;
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 显示底部弹窗
     */
    public void show() {
        if (popupWindow == null) {
            setDefaultLayout();
        }
        if (popupWindow != null && !isShow) {
            popupWindow.showAtLocation(parentView, Gravity.BOTTOM, (int) parentView.getX(), (int) (parentView.getY()));
            changeAlpha(0.3f);
            isShow = true;
        }
    }

    private void setDefaultLayout() {
        View popupView = LayoutInflater.from(context).inflate(R.layout.popwindow_menu, null);
        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                dismiss();
            }
        });
        llParent = popupView.findViewById(R.id.llParent);
        rvMenu = popupView.findViewById(R.id.rvMenu);
        adapter = new CommonRVAdapter<String>(context,list,R.layout.item_menu,R.layout.item_menu_empty) {
            @Override
            protected void onBindNullViewHolder(RecyclerView.Adapter adapter, RVViewHolder viewHolder, int position, String item, List<String> list) {

            }

            @Override
            protected void onBindViewHolder(RecyclerView.Adapter adapter, RVViewHolder viewHolder, final int position, String item, List<String> list) {
                viewHolder.findViewById(R.id.viewDevide).setVisibility(position==0?View.GONE:View.VISIBLE);
                ((TextView)viewHolder.findViewById(R.id.tvItemName)).setText(item);
                viewHolder.findViewById(R.id.tvItemName).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dismiss();
                        if(onItemClickListener!=null){
                            onItemClickListener.onItemClick(view,position);
                        }
                    }
                });
            }
        };
        rvMenu.setAdapter(adapter);
        rvMenu.setLayoutManager(new LinearLayoutManager(context));

        llParent.setOnClickListener(this);
    }

    /**
     * 取消弹窗
     */
    public void dismiss() {
        if (popupWindow != null && isShow) {
            popupWindow.dismiss();
            changeAlpha(1f);
            isShow = false;
        }
    }

    //改变activity透明度
    private void changeAlpha(float alpha) {
        WindowManager.LayoutParams params = ((Activity) context).getWindow().getAttributes();
        params.alpha = alpha;
        ((Activity) context).getWindow().setAttributes(params);
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.llParent){
            dismiss();
        }
    }
}
