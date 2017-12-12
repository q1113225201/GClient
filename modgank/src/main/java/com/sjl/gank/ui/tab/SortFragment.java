package com.sjl.gank.ui.tab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sjl.gank.R;
import com.sjl.gank.ui.SortListActivity;
import com.sjl.platform.base.BaseFragment;
import com.sjl.platform.base.adapter.CommonRVAdapter;

import java.util.ArrayList;
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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
    }

    private RecyclerView rvSort;
    private List<String> list;
    private CommonRVAdapter<String> adapter;

    private void initView() {
        rvSort = view.findViewById(R.id.rvSort);

        initSort();
    }

    private void initSort() {
        list = new ArrayList<>();
        list.add("all");
        list.add("Android");
        list.add("iOS");
        list.add("休息视频");
        list.add("福利");
        list.add("拓展资源");
        list.add("前端");
        list.add("瞎推荐");
        list.add("App");
        adapter = new CommonRVAdapter<String>(mContext, list, R.layout.item_menu, R.layout.item_menu_empty) {
            @Override
            protected void onBindNullViewHolder(RecyclerView.Adapter adapter, RVViewHolder viewHolder, int position, String item, List<String> list) {

            }

            @Override
            protected void onBindViewHolder(RecyclerView.Adapter adapter, RVViewHolder viewHolder, int position, final String item, List<String> list) {
                viewHolder.findViewById(R.id.viewDevide).setVisibility(position==0?View.GONE:View.VISIBLE);
                ((TextView)viewHolder.findViewById(R.id.tvItemName)).setText(item);
                viewHolder.findViewById(R.id.tvItemName).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(SortListActivity.newIntent(mContext,item));
                    }
                });
            }
        };
        rvSort.setAdapter(adapter);
        rvSort.setLayoutManager(new LinearLayoutManager(mContext));
    }
}
