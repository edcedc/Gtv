package com.yc.gtv.presenter;

import android.support.constraint.Group;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yc.gtv.bean.DataBean;
import com.yc.gtv.bean.SaveSearchListBean;
import com.yc.gtv.view.impl.SearchContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edison on 2018/11/18.
 */

public class SearchPresenter extends SearchContract.Presenter{

    @Override
    public void onRequest(int pagerNumber) {
        List<DataBean> list = new ArrayList<>();
        for (int i = 0;i < 10;i++){
            list.add(new DataBean());
        }
        mView.setData(list);
    }

    @Override
    public void onSaveHistory(String trim, Group gpList, RecyclerView recyclerView, TwinklingRefreshLayout refreshLayout) {
        SaveSearchListBean bean = new SaveSearchListBean();
        bean.setContent(trim);
        boolean save = bean.save();
        if (save){
            gpList.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            refreshLayout.startRefresh();
            mView.setSearchData();
        }
    }

    @Override
    public void onSearch(String trim, int pagerNumber) {
        List<DataBean> list = new ArrayList<>();
        for (int i = 0;i < 10;i++){
            list.add(new DataBean());
        }
        mView.setSearchList(list);
    }

}
