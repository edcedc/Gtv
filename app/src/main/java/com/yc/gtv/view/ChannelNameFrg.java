package com.yc.gtv.view;

import android.os.Bundle;
import android.view.View;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yc.gtv.R;
import com.yc.gtv.adapter.ChannelChildAdapter;
import com.yc.gtv.base.BaseFragment;
import com.yc.gtv.bean.DataBean;
import com.yc.gtv.databinding.FChannelNameBinding;
import com.yc.gtv.presenter.ChannelNamePresenter;
import com.yc.gtv.utils.GlideLoadingUtils;
import com.yc.gtv.view.impl.ChannelNameContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edison on 2018/11/19.
 *  频道名称
 */

public class ChannelNameFrg extends BaseFragment<ChannelNamePresenter, FChannelNameBinding> implements ChannelNameContract.View{

    private List<DataBean> listBean = new ArrayList<>();
    private ChannelChildAdapter adapter;
    private String id;
    private String tagId;

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
        return R.layout.f_channel_name;
    }

    @Override
    protected void initView(View view) {
        if (adapter == null){
            adapter = new ChannelChildAdapter(act, this, listBean);
        }
        mB.gridView.setAdapter(adapter);
        showLoadDataing();
        mB.refreshLayout.startRefresh();
        setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.onChannelGetChannelTagDetail(id);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                mPresenter.onRequest(tagId, pagerNumber += 1);
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

    @Override
    public void onChannelGetChannelTagDetailSuccess(DataBean bean) {
        setTitle(bean.getTagName());
        GlideLoadingUtils.load(act, bean.getPoster(), mB.ivImg);
        mB.tvContent.setText(bean.getRemark());
        tagId = bean.getTagId();
        mPresenter.onRequest(tagId, pagerNumber = 1);
    }
}
