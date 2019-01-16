package com.yc.gtv.view.impl;

import android.support.constraint.Group;
import android.support.v7.widget.RecyclerView;

import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yc.gtv.base.BasePresenter;
import com.yc.gtv.base.IBaseListView;
import com.yc.gtv.bean.DataBean;

import java.util.List;

/**
 * Created by edison on 2018/11/18.
 */

public interface SearchContract {

    interface View extends IBaseListView {

        void setSearchData();

        void setSearchList(List<DataBean> list);
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onRequest(int pagerNumber);

        public abstract void onSaveHistory(String trim, Group gpList, RecyclerView recyclerView, TwinklingRefreshLayout refreshLayout, int pagerNumber);

        public abstract void onSearch(String trim, int pagerNumber);
    }

}
