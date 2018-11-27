package com.yc.gtv.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yc.gtv.R;
import com.yc.gtv.base.BaseFragment;
import com.yc.gtv.controller.UIHelper;
import com.yc.gtv.databinding.FMemberCenterBinding;
import com.yc.gtv.event.PayInEvent;
import com.yc.gtv.presenter.MemberCenterPresenter;
import com.yc.gtv.view.impl.MemberCenterContract;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by edison on 2018/11/22.
 *  会员中心
 */

public class MemberCenterFrg extends BaseFragment<MemberCenterPresenter, FMemberCenterBinding> implements MemberCenterContract.View, View.OnClickListener{

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_member_center;
    }

    @Override
    protected void initView(View view) {
        final AppCompatActivity mAppCompatActivity = (AppCompatActivity) act;
        mAppCompatActivity.setSupportActionBar(mB.toolbar);
        mAppCompatActivity.getSupportActionBar().setTitle("");
        mB.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                act.onBackPressed();

            }
        });
        mB.btJoin.setOnClickListener(this);
        mB.btRenew.setOnClickListener(this);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        setSofia(true);
    }

    @Subscribe
    public void onMainPayInEvent(PayInEvent event){
        mB.gpJoin.setVisibility(View.GONE);
        mB.gpMember.setVisibility(View.VISIBLE);
        mB.ivImg.setBackgroundResource(R.mipmap.my_zz_bg);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_join:
            case R.id.bt_renew:
                UIHelper.startPurchaseMemberFrg(this);
                break;
        }
    }
}
