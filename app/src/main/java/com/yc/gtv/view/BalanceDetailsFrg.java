package com.yc.gtv.view;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yc.gtv.R;
import com.yc.gtv.adapter.BalanceDetailsAdapter;
import com.yc.gtv.base.BaseFragment;
import com.yc.gtv.base.BaseListContract;
import com.yc.gtv.base.BaseListPresenter;
import com.yc.gtv.bean.DataBean;
import com.yc.gtv.controller.CloudApi;
import com.yc.gtv.databinding.BRecyclerBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edison on 2018/12/8.
 *  余额明细
 */

public class BalanceDetailsFrg extends BaseFragment<BaseListPresenter, BRecyclerBinding> implements BaseListContract.View{

    private List<DataBean> listBean = new ArrayList<>();
    private BalanceDetailsAdapter adapter;

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
        setTitle(getString(R.string.balance_details));
        if (adapter == null) {
            adapter = new BalanceDetailsAdapter(act, listBean);
        }
        setRecyclerViewType(mB.recyclerView);
        mB.recyclerView.setAdapter(adapter);
        showLoadDataing();
        mB.refreshLayout.startRefresh();
        mB.refreshLayout.setEnableLoadmore(false);
        setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.onRequest(CloudApi.mineBalanceFlow);
            }
        });
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
