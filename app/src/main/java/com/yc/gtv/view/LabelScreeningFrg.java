package com.yc.gtv.view;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.library.AutoFlowLayout;
import com.example.library.FlowAdapter;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yc.gtv.R;
import com.yc.gtv.adapter.VideoDescListAdapter;
import com.yc.gtv.base.BaseFragment;
import com.yc.gtv.bean.DataBean;
import com.yc.gtv.databinding.FLabelScreeningBinding;
import com.yc.gtv.presenter.LabelScreeningPresenter;
import com.yc.gtv.view.impl.LabelScreeningContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edison on 2018/11/19.
 *  标签筛选
 */

public class LabelScreeningFrg extends BaseFragment<LabelScreeningPresenter, FLabelScreeningBinding> implements LabelScreeningContract.View{

    private List<DataBean> listBean = new ArrayList<>();
    private VideoDescListAdapter adapter;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_label_screening;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.label_screening));
        if (adapter == null){
            adapter = new VideoDescListAdapter(act, listBean);
        }
        setRecyclerViewType(mB.recyclerView);
        mB.recyclerView.setAdapter(adapter);
        mB.refreshLayout.startRefresh();
        mB.refreshLayout.setEnableLoadmore(false);
        setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.onRequest(pagerNumber = 1);
            }
        });
        mB.rvHistoricalRecords.setMultiChecked(true);
        mB.rvHistoricalRecords.setOnItemClickListener(new AutoFlowLayout.OnItemClickListener() {
            @Override
            public void onItemClick(int i, View view) {

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
        final List<DataBean> list = (List<DataBean>) data;
        if (pagerNumber == 1) {
            listBean.clear();
            mB.refreshLayout.finishRefreshing();
        } else {
            mB.refreshLayout.finishLoadmore();
        }
        listBean.addAll(list);
        adapter.notifyDataSetChanged();


        mB.rvHistoricalRecords.removeAllViews();
        mB.rvHistoricalRecords.setAdapter(new FlowAdapter(list) {
            @Override
            public View getView(final int i) {
                View view = View.inflate(act, R.layout.i_selected_label, null);
                TextView tvText = view.findViewById(R.id.tv_text);
                DataBean bean = listBean.get(i);
                tvText.setText("标签" + bean.getName());
                return view;
            }
        });
    }
}
