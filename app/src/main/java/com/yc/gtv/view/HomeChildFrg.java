package com.yc.gtv.view;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ImageView;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yc.gtv.R;
import com.yc.gtv.adapter.HomeChildListAdapter;
import com.yc.gtv.base.BaseFragment;
import com.yc.gtv.bean.DataBean;
import com.yc.gtv.controller.UIHelper;
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
    private String id;


    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {
        id = bundle.getString("id");
    }

    @Override
    protected int bindLayout() {
        return R.layout.f_home_child;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
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
                mPresenter.onRequest(pagerNumber = 1, id);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                mPresenter.onRequest(pagerNumber += 1, id);
            }
        });
        setSwipeBackEnable(false);
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        setSofia(true);
    }

    @Override
    public void setBannerData(List<DataBean> list) {
        if (list.size() != 0) {
            mB.banner.setVisibility(View.VISIBLE);
            mB.banner.setAutoPlayAble(list.size() > 1);
            mB.banner.setData(list, new ArrayList<String>());
            mB.banner.setAdapter(HomeChildFrg.this);
        }else {
            mB.banner.setVisibility(View.GONE);
        }
    }

    @Override
    public void fillBannerItem(BGABanner banner, ImageView itemView, @Nullable DataBean model, int position) {
        GlideLoadingUtils.load(act, model.getImageUrl(), itemView);
    }

    @Override
    public void onBannerItemClick(BGABanner banner, View itemView, @Nullable Object model, int position) {
        DataBean bean = (DataBean) model;
        switch (bean.getType()){//1： 图集， 2：视频，3：H5跳转
            case 1:
                UIHelper.startGalleryDescAct(bean.getHref(), -1);
                break;
            case 2:
                UIHelper.startVideoDescAct(bean.getHref());
                break;
            case 3:
                UIHelper.startHtmlAct(bean.getHref());
                break;
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
