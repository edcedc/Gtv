package com.yc.gtv.view;

import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;

import com.yc.gtv.R;
import com.yc.gtv.base.BaseFragment;
import com.yc.gtv.controller.UIHelper;
import com.yc.gtv.databinding.FMeBinding;
import com.yc.gtv.presenter.MePresenter;
import com.yc.gtv.view.impl.MeContract;

/**
 * Created by edison on 2018/11/15.
 *  我的
 */

public class MeFrg extends BaseFragment<MePresenter, FMeBinding> implements MeContract.View, View.OnClickListener{

    public static MeFrg newInstance() {
        Bundle args = new Bundle();
        MeFrg fragment = new MeFrg();
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
        return R.layout.f_me;
    }

    @Override
    protected void initView(View view) {
        setSwipeBackEnable(false);
        mB.ivHead.setOnClickListener(this);
        mB.tvLogin.setOnClickListener(this);
        mB.tvNotLogin.setOnClickListener(this);
        mB.ivHead.setOnClickListener(this);
        mB.topRightFy.setOnClickListener(this);
        setRecyclerViewType(mB.recyclerView);
        mPresenter.onInit(mB.gridView, mB.recyclerView, this);

        mB.tvNum.setText("6/6");
        mB.tvTime.setText("2018-09-10 到期");
        setTextSize("6/人0", mB.tvUserNumber);
    }

    private void setTextSize(String str, AppCompatTextView textView){
        Spannable sp = new SpannableString(str) ;
        sp.setSpan(new AbsoluteSizeSpan(20,true),0,sp.length() - 1,Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        sp.setSpan(new AbsoluteSizeSpan(15,true),sp.length() - 1,sp.length(),Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        textView.setText(sp);
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        setSofia(true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_head:
                mB.gpMember.setVisibility(View.GONE);
                mB.gpMemberTime.setVisibility(View.VISIBLE);
                mB.ivImg.setBackgroundResource(R.mipmap.my_zz_bg);
                break;
            case R.id.tv_login:
            case R.id.tv_not_login:
                UIHelper.startLoginAct();
                break;
            case R.id.top_right_fy:
                UIHelper.startSetAct();
                break;
        }
    }
}
