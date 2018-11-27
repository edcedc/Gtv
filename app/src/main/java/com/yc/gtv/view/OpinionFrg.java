package com.yc.gtv.view;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.yc.gtv.R;
import com.yc.gtv.adapter.OpinionAdapter;
import com.yc.gtv.base.BaseFragment;
import com.yc.gtv.bean.DataBean;
import com.yc.gtv.databinding.FOpinionBinding;
import com.yc.gtv.presenter.OpinionPresenter;
import com.yc.gtv.view.impl.OpinionContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edison on 2018/11/21.
 *  意见反馈
 */

public class OpinionFrg extends BaseFragment<OpinionPresenter, FOpinionBinding> implements OpinionContract.View{

    private List<DataBean> listBean = new ArrayList<>();
    private OpinionAdapter adapter;

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
        if (adapter == null){
            adapter = new OpinionAdapter(act, listBean);
        }
        mB.gridView.setAdapter(adapter);
        mB.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DataBean bean = listBean.get(i);
                mB.etText.setText(bean.getName());
                adapter.setmPosition(i);
                adapter.notifyDataSetChanged();
            }
        });
        mPresenter.onLebel();
        mB.btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.onSubmit(mB.etText.getText().toString());
            }
        });
    }

    @Override
    public void setLabel(List<DataBean> listStr) {
        listBean.addAll(listStr);
        adapter.notifyDataSetChanged();
    }
}
