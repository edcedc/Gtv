package com.yc.gtv.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.EditText;

import com.flyco.tablayout.listener.OnTabSelectListener;
import com.yc.gtv.R;
import com.yc.gtv.adapter.MyPagerAdapter;
import com.yc.gtv.base.BaseFragment;
import com.yc.gtv.base.BaseListContract;
import com.yc.gtv.base.BaseListPresenter;
import com.yc.gtv.bean.DataBean;
import com.yc.gtv.controller.CloudApi;
import com.yc.gtv.controller.UIHelper;
import com.yc.gtv.databinding.FHomeBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edison on 2018/11/15.
 *  首页
 */

public class HomeFrg extends BaseFragment<BaseListPresenter, FHomeBinding> implements BaseListContract.View, View.OnClickListener{

    public static HomeFrg newInstance() {
        Bundle args = new Bundle();
        HomeFrg fragment = new HomeFrg();
        fragment.setArguments(args);
        return fragment;
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
        return R.layout.f_home;
    }

    @Override
    protected void initView(View view) {
        EditText etSearch = view.findViewById(R.id.et_searh);
        etSearch.setOnClickListener(this);
        etSearch.setFocusable(false);
        etSearch.setLongClickable(false);
        etSearch.setTextIsSelectable(false);
        view.findViewById(R.id.ly_search).setOnClickListener(this);
        showLoadDataing();
        mPresenter.onRequest(CloudApi.homeClassify);
        setSwipeBackEnable(false);
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ly_search:
            case R.id.et_searh:
                UIHelper.startSearchFrg(this);
                break;
        }
    }

    @Override
    public void setRefreshLayoutMode(int totalRow) {

    }

    @Override
    public void setData(Object data) {
        List<DataBean> list = (List<DataBean>) data;
        ArrayList<Fragment> mFragments = new ArrayList<>();
        String[] strings = new String[list.size()];
        for (int i = 0;i < list.size();i++){
            DataBean bean = list.get(i);
            strings[i] = bean.getName();
            HomeChildFrg frg = new HomeChildFrg();
            Bundle bundle = new Bundle();
            bundle.putString("id", bean.getId());
            frg.setArguments(bundle);
            mFragments.add(frg);
        }
        mB.viewPager.setAdapter(new MyPagerAdapter(getChildFragmentManager(), mFragments, strings));
        mB.tbLayout.setViewPager(mB.viewPager);
        mB.viewPager.setOffscreenPageLimit(list.size() - 1);
        mB.tbLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mB.viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

}
