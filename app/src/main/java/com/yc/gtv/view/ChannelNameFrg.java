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

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_channel_name;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.channel_desc));
        if (adapter == null){
            adapter = new ChannelChildAdapter(act, listBean);
        }
        mB.gridView.setAdapter(adapter);
        mB.refreshLayout.startRefresh();
        mB.refreshLayout.setEnableLoadmore(false);
        setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.onRequest(pagerNumber = 1);
            }
        });

        GlideLoadingUtils.load(act, "http://ww3.sinaimg.cn/large/0073tLPGgy1fxdg9yqrbqj30k00u0wfh.jpg", mB.ivImg);
        mB.tvContent.setText("频道介绍介绍介绍介绍介绍介绍事实上事实上事实上身上收拾收拾.......................最多显示两行");
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
