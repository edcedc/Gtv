package com.yc.gtv.view;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yc.gtv.R;
import com.yc.gtv.adapter.NotificationAdapter;
import com.yc.gtv.base.BaseFragment;
import com.yc.gtv.bean.DataBean;
import com.yc.gtv.databinding.BRecyclerBinding;
import com.yc.gtv.presenter.NotificationPresenter;
import com.yc.gtv.view.impl.NotificationContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edison on 2018/11/21.
 *  系统通知
 */

public class NotificationFrg extends BaseFragment<NotificationPresenter, BRecyclerBinding> implements NotificationContract.View{

    private List<DataBean> listBean = new ArrayList<>();
    private NotificationAdapter adapter;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.b_recycler;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.system_notification2));
        if (adapter == null){
            adapter = new NotificationAdapter(act, this, listBean);
        }
        setRecyclerViewType(mB.recyclerView);
        mB.recyclerView.setAdapter(adapter);
        mB.refreshLayout.startRefresh();
        showLoadDataing();
        setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.onRequest(pagerNumber = 1);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                mPresenter.onRequest(pagerNumber += 1);
            }
        });
        setSwipeBackEnable(false);
    }

    @Override
    protected void setOnRightClickListener() {
        super.setOnRightClickListener();
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
    }

    @Override
    public void setRefreshLayoutMode(int totalRow) {
        super.setRefreshLayoutMode(listBean.size(), totalRow, mB.refreshLayout);
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        super.setRefreshLayout(pagerNumber, mB.refreshLayout);
    }

    @Override
    public void setData(Object data) {
        List<DataBean> list = (List<DataBean>) data;
        if (pagerNumber == 1) {
            listBean.clear();
            mB.refreshLayout.finishRefreshing();
        } else {
            mB.refreshLayout.finishLoadmore();
        }
        listBean.addAll(list);
        adapter.notifyDataSetChanged();
    }
}
