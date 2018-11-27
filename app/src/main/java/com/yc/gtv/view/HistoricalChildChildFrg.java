package com.yc.gtv.view;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yc.gtv.R;
import com.yc.gtv.adapter.VideoDescListAdapter;
import com.yc.gtv.base.BaseFragment;
import com.yc.gtv.bean.DataBean;
import com.yc.gtv.databinding.FChildListBinding;
import com.yc.gtv.event.HistoricalColseInEvent;
import com.yc.gtv.event.HistoricalEditInEvent;
import com.yc.gtv.presenter.HistoricalChildPresenter;
import com.yc.gtv.view.impl.HistoricalChildContract;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edison on 2018/11/21.
 *  图片视频历史记录
 */

@SuppressLint("ValidFragment")
public class HistoricalChildChildFrg extends BaseFragment<HistoricalChildPresenter, FChildListBinding> implements HistoricalChildContract.View, View.OnClickListener{

    private List<DataBean> listBean = new ArrayList<>();
    private VideoDescListAdapter adapter;

    private int mType;//数据要传的状态值
    private boolean isRefresh = false;
    private int type;//哪个页面进来的

    public HistoricalChildChildFrg(int i, int type) {
        this.mType = i;
        this.type = type;
    }

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_child_list;
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if (!isRefresh){
            isRefresh = true;
            mB.refreshLayout.startRefresh();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initView(View view) {
        mB.tvTotalSelection.setOnClickListener(this);
        mB.tvDel.setOnClickListener(this);
        if (adapter == null) {
            adapter = new VideoDescListAdapter(act, listBean, type);
        }
        setRecyclerViewType(mB.recyclerView);
        mB.recyclerView.setAdapter(adapter);
        mB.refreshLayout.setEnableLoadmore(false);
        setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.onRequest(pagerNumber = 1, mType);
            }
        });
        EventBus.getDefault().register(this);
        setSwipeBackEnable(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onMainEditInEvent(HistoricalEditInEvent event){
        adapter.setEdit(event.isEdit);
        adapter.notifyDataSetChanged();
        mB.lyBottom.setVisibility(event.isEdit == true ? View.VISIBLE : View.GONE);
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
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_total_selection:
                adapter.setAllSelect(true);
                adapter.notifyDataSetChanged();
                break;
            case R.id.tv_del:
                List<DataBean> temp = new ArrayList<>();
                for (int i = 0; i < listBean.size(); i++) {
                    DataBean bean = listBean.get(i);
                    if (bean.isSelect()){
                        temp.add(bean);
                    }
                }
                listBean.removeAll(temp);
                adapter.notifyDataSetChanged();
                showLoadEmpty();

                if (listBean.size() == 0)EventBus.getDefault().post(new HistoricalColseInEvent(false));
                break;
        }
    }
}
