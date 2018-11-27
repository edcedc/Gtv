package com.yc.gtv.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yc.gtv.R;
import com.yc.gtv.adapter.HomeChildListAdapter;
import com.yc.gtv.base.BaseFragment;
import com.yc.gtv.bean.DataBean;
import com.yc.gtv.databinding.FHomeChildBinding;
import com.yc.gtv.presenter.HomeChildPresenter;
import com.yc.gtv.utils.GlideLoadingUtils;
import com.yc.gtv.view.impl.HomeChildContract;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * Created by edison on 2018/11/15.
 */

public class HomeChildFrg extends BaseFragment<HomeChildPresenter, FHomeChildBinding> implements HomeChildContract.View, BGABanner.Delegate, BGABanner.Adapter<ImageView, DataBean>{

    private HomeChildListAdapter adapter;
    private List<DataBean> listBean = new ArrayList<>();
    private boolean isRefresh = false;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_home_child;
    }

    @Override
    protected void initView(View view) {
        if (adapter == null) {
            adapter = new HomeChildListAdapter(act, listBean);
        }
        setRecyclerViewGridType(mB.recyclerView, 2, 10, R.color.white );
        mB.recyclerView.setAdapter(adapter);
        mB.banner.setDelegate(this);
        mB.refreshLayout.setEnableLoadmore(false);
        if (!isRefresh){
            isRefresh = true;
            mB.refreshLayout.startRefresh();
        }
        setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.onBanner();
                mPresenter.onRequest(pagerNumber = 1);
            }
        });
        setSwipeBackEnable(false);
    }

    @Override
    public void setBannerData(List<DataBean> list) {
        if (null != list && list.size() != 0) {
            mB.banner.setAutoPlayAble(list.size() > 1);
            mB.banner.setData(list, new ArrayList<String>());
            mB.banner.setAdapter(HomeChildFrg.this);
        }
    }

    @Override
    public void fillBannerItem(BGABanner banner, ImageView itemView, @Nullable DataBean model, int position) {
        GlideLoadingUtils.load(act, "http://ww3.sinaimg.cn/large/006XNEY7gy1fx9q4brtqlj30dw0kkjvj.jpg", itemView);
    }

    @Override
    public void onBannerItemClick(BGABanner banner, View itemView, @Nullable Object model, int position) {

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
