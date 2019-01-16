package com.yc.gtv.view;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.yc.gtv.R;
import com.yc.gtv.adapter.OpinionAdapter;
import com.yc.gtv.base.BaseFragment;
import com.yc.gtv.bean.DataBean;
import com.yc.gtv.databinding.FOpinionBinding;
import com.yc.gtv.presenter.OpinionPresenter;
import com.yc.gtv.view.impl.OpinionContract;
import com.yc.gtv.weight.GridDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edison on 2018/11/21.
 *  意见反馈
 */

public class OpinionFrg extends BaseFragment<OpinionPresenter, FOpinionBinding> implements OpinionContract.View{

    private List<DataBean> listBean = new ArrayList<>();
    private OpinionAdapter adapter;
    private String id;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_opinion;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.opinion));
        mB.refreshLayout.setPureScrollModeOn();
        if (adapter == null){
            adapter = new OpinionAdapter(act, listBean);
        }
        mB.gridView.setLayoutManager(new GridLayoutManager(act, 2));
        mB.gridView.setHasFixedSize(true);
        mB.gridView.setItemAnimator(new DefaultItemAnimator());
        mB.gridView.addItemDecoration(new GridDividerItemDecoration(20, ContextCompat.getColor(act,R.color.white_f4f4f4)));
        mB.gridView.setAdapter(adapter);
        adapter.setOnClickListener(new OpinionAdapter.OnClickListener() {
            @Override
            public void onClick(int position) {
                DataBean bean = listBean.get(position);
                adapter.setmPosition(position);
                adapter.notifyDataSetChanged();
                id = bean.getId();
            }
        });
        mPresenter.onLebel();
        mB.btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.onSubmit(id, mB.etText.getText().toString());
            }
        });
    }

    @Override
    public void setLabel(List<DataBean> listStr) {
        listBean.addAll(listStr);
        adapter.notifyDataSetChanged();
    }
}
