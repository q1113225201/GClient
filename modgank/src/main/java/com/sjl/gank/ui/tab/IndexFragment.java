package com.sjl.gank.ui.tab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sjl.gank.R;
import com.sjl.gank.bean.DBHistoryDate;
import com.sjl.gank.bean.GankData;
import com.sjl.gank.bean.HistoryDate;
import com.sjl.gank.service.ServiceClient;
import com.sjl.platform.base.BaseFragment;
import com.sjl.platform.base.db.DBManager;
import com.sjl.platform.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

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
        LogUtil.i(TAG,"IndexFragment onViewCreated");

        initView();
    }

    private void initView() {
        tvResult = view.findViewById(R.id.tvResult);

        getHistory();
    }

    /**
     * 获取历史发布日期
     */
    private void getHistory() {
        Observable.concat(Observable.create(new ObservableOnSubscribe<HistoryDate>() {
            @Override
            public void subscribe(ObservableEmitter<HistoryDate> e) throws Exception {
                getNewData(true);
                e.onComplete();
            }
        }),ServiceClient.getGankAPI().getHistorys())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<HistoryDate>() {
                    @Override
                    public void accept(HistoryDate historyDate) throws Exception {
                        List<DBHistoryDate> localList = DBManager.getInstance().getList(DBHistoryDate.class,"","");
                        List<DBHistoryDate> netList = historyDate.getResults();
                        netList = netList==null?new ArrayList<DBHistoryDate>():netList;
                        if(localList.size()==0){
                            //本地无数据，保存到本地并获取首页数据
                            DBManager.getInstance().save(netList);
                            getNewData(false);
                        }else if(localList.size()!=netList.size()){
                            //本地数据与网络数据不一致，保存为保持数据，获取首页数据
                            DBManager.getInstance().save(netList.subList(localList.size()-1,netList.size()));
                            getNewData(false);
                        }
                        tvResult.setText(historyDate.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG,throwable.getMessage());
                    }
                });
    }
    private void getNewData(boolean isLocal) {
        if(isLocal) {
            //显示本地数据
            List<GankData> list = DBManager.getInstance().getList(GankData.class, "", "publishedAt");
            if (list != null && list.size() > 0) {
                //显示最新数据
            }
        }else{
            //显示网络数据

        }
    }

}
