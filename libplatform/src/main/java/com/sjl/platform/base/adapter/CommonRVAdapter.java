package com.sjl.platform.base.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * CommonRVAdapter
 *
 * @author SJL
 * @date 2017/7/20
 */

public abstract class CommonRVAdapter<T> extends RecyclerView.Adapter {
    private static final int VIEW_TYPE_NULL = -1;
    private Context context;
    private List<T> list;
    private int itemLayout;
    private int nullLayout;

    public CommonRVAdapter(Context context, List<T> list, int itemLayout, int nullLayout) {
        this.context = context;
        this.list = list;
        this.itemLayout = itemLayout;
        this.nullLayout = nullLayout;
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_NULL) {
            return new RVViewHolder(LayoutInflater.from(context).inflate(nullLayout, parent, false));
        } else {
            return new RVViewHolder(LayoutInflater.from(context).inflate(itemLayout, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_NULL) {
            onBindNullViewHolder(this, (RVViewHolder) holder, position, null, null);
        } else {
            onBindViewHolder(this, (RVViewHolder) holder, position, list.get(position), list);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (list == null || list.size() == 0) {
            return VIEW_TYPE_NULL;
        }
        return super.getItemViewType(position);
    }

    protected abstract void onBindNullViewHolder(RecyclerView.Adapter adapter, RVViewHolder viewHolder, int position, T item, List<T> list);

    protected abstract void onBindViewHolder(RecyclerView.Adapter adapter, RVViewHolder viewHolder, int position, T item, List<T> list);

    protected class RVViewHolder extends RecyclerView.ViewHolder {
        private SparseArray<View> viewList;
        private View itemView;

        public RVViewHolder(View itemView) {
            super(itemView);
            viewList = new SparseArray<>();
            this.itemView = itemView;
        }

        public View findViewById(int id) {
            View view = viewList.get(id);
            if (view == null) {
                view = itemView.findViewById(id);
                viewList.put(id, view);
            }
            return view;
        }
    }

    public void flush(List<T> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void addFlush(T item) {
        if (list == null) {
            list = new ArrayList<>();
        }
        list.add(item);
        notifyDataSetChanged();
    }

    public void update(T item) {
        update(list.indexOf(item), item);
    }

    public void update(int position, T item) {
        list.add(position + 1, item);
        list.remove(position);
        notifyDataSetChanged();
    }

    public void addList(List<T> newList) {
        if (list == null) {
            list = new ArrayList<>();
        }
        list.addAll(newList);
        flush(list);
    }
}
