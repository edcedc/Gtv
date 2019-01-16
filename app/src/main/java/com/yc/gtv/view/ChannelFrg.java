package com.yc.gtv.view;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.DividerItemDecoration;
import android.view.View;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yc.gtv.R;
import com.yc.gtv.adapter.ChannelAdapter;
import com.yc.gtv.base.BaseFragment;
import com.yc.gtv.base.BaseListContract;
import com.yc.gtv.base.BaseListPresenter;
import com.yc.gtv.bean.DataBean;
import com.yc.gtv.controller.CloudApi;
import com.yc.gtv.controller.UIHelper;
import com.yc.gtv.databinding.BRecyclerBinding;
import com.yc.gtv.weight.LinearDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edison on 2018/11/15.
 *  频道
 */

public class ChannelFrg extends BaseFragment<BaseListPresenter, BRecyclerBinding> implements BaseListContract.View{

    public static ChannelFrg newInstance() {
        Bundle args = new Bundle();
        ChannelFrg fragment = new ChannelFrg();
        fragment.setArguments(args);
        return fragment;
    }

    private List<DataBean> listBean = new ArrayList<>();
    private ChannelAdapter adapter;
    private boolean isRefresh = true;

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
        setTitle(getString(R.string.channel), getString(R.string.all), false);
        if (adapter == null){
            adapter = new ChannelAdapter(act, this, listBean);
        }
        setRecyclerViewType(mB.recyclerView);
        mB.recyclerView.addItemDecoration(new LinearDividerItemDecoration(act, DividerItemDecoration.VERTICAL,  5));
        mB.recyclerView.setAdapter(adapter);
        mB.refreshLayout.setEnableLoadmore(false);
        setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.onRequest(CloudApi.channelGetAllChannelTag);
            }
        });
        setSwipeBackEnable(false);
    }

    @Override
    protected void setOnRightClickListener() {
        super.setOnRightClickListener();
        UIHelper.startAllChannelFrg(this);
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if (isRefresh){
            mB.refreshLayout.startRefresh();
            showLoadDataing();
            isRefresh = false;
        }
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
        listBean.clear();
        mB.refreshLayout.finishRefreshing();
        listBean.addAll(list);
        adapter.notifyDataSetChanged();
    }
}
