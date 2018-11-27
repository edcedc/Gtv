package com.yc.gtv.view;

import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yc.gtv.R;
import com.yc.gtv.adapter.PerformanceAdapter;
import com.yc.gtv.base.BaseFragment;
import com.yc.gtv.bean.DataBean;
import com.yc.gtv.databinding.FPerformanceDescBinding;
import com.yc.gtv.presenter.PerformanceDescPresenter;
import com.yc.gtv.view.impl.PerformanceDescContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edison on 2018/11/22.
 *  业绩明细
 */

public class PerformanceDescFrg extends BaseFragment<PerformanceDescPresenter, FPerformanceDescBinding> implements PerformanceDescContract.View{

    private List<DataBean> listBean = new ArrayList<>();
    private PerformanceAdapter adapter;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_performance_desc;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.performance_desc));

        showLoadDataing();
        if (adapter == null){
            adapter = new PerformanceAdapter(act, listBean);
        }
        mB.listView.setAdapter(adapter);
        mB.refreshLayout.startRefresh();
        mB.refreshLayout.setEnableLoadmore(false);
        setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.onRequest(pagerNumber = 1);
            }
        });


        mB.tvMonthPromotionPrice.setText("85.36元");
        setTextSize("7人", mB.tvMonthPromotionNumber);
        setTextSize("7人", mB.tvMonthPromotionMemberNumber);
    }

    private void setTextSize(String str, AppCompatTextView textView){
        Spannable sp = new SpannableString(str) ;
        sp.setSpan(new AbsoluteSizeSpan(20,true),0,sp.length() - 1,Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        sp.setSpan(new AbsoluteSizeSpan(15,true),sp.length() - 1,sp.length(),Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        textView.setText(sp);
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
