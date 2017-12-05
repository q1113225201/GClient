package com.sjl.gank.ui.tab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sjl.gank.R;
import com.sjl.gank.bean.GankData;
import com.sjl.gank.bean.GankWebData;
import com.sjl.gank.bean.GankWebDataResult;
import com.sjl.gank.bean.HistoryDate;
import com.sjl.gank.service.ServiceClient;
import com.sjl.platform.base.BaseFragment;
import com.sjl.platform.base.db.DBManager;
import com.sjl.platform.util.LogUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 首页Fragment
 *
 * @author SJL
 * @date 2017/11/30
 */
public class IndexFragment extends BaseFragment {
    private static final String TAG = "IndexFragment";
    private TextView tvResult;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_index, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LogUtil.i(TAG, "IndexFragment onViewCreated");

        initView();
    }

    private void initView() {
        tvResult = view.findViewById(R.id.tvResult);
        getNewData();
    }

    private void getNewData() {
        //显示本地数据
        List<GankWebDataResult> list = DBManager.getInstance().getList(GankWebDataResult.class, "", "publishedAt");
        if (list != null && list.size() > 0) {
            //显示最新数据
            LogUtil.i(TAG, "db-----"+list.get(0).toString());
            tvResult.setText(list.get(0).toString());
        }
        //显示网络数据
        ServiceClient.getGankAPI().getWebDataByPage(1, 1).enqueue(new Callback<GankWebData>() {
            @Override
            public void onResponse(Call<GankWebData> call, Response<GankWebData> response) {
                LogUtil.i(TAG, "net-----"+response.body().toString());
                tvResult.setText(response.body().toString());
                GankWebDataResult gankWebDataResult = response.body().getResults().get(0);
                if(DBManager.getInstance().getList(GankWebDataResult.class,String.format("_id='%s'",gankWebDataResult.get_id()),"").size()>0) {
                    DBManager.getInstance().update(gankWebDataResult);
                }else{
                    DBManager.getInstance().save(gankWebDataResult);
                }
            }

            @Override
            public void onFailure(Call<GankWebData> call, Throwable t) {

            }
        });
    }

}
