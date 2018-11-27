package com.yc.gtv.view;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;

import com.yc.gtv.R;
import com.yc.gtv.base.BaseFragment;
import com.yc.gtv.controller.UIHelper;
import com.yc.gtv.databinding.FExtensionBinding;
import com.yc.gtv.presenter.ExtensionPresenter;
import com.yc.gtv.view.impl.ExtensionContract;

/**
 * Created by edison on 2018/11/22.
 *  我要推广
 */

public class ExtensionFrg extends BaseFragment<ExtensionPresenter, FExtensionBinding> implements ExtensionContract.View, View.OnClickListener{
    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_extension;
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

        mB.topRightFy.setOnClickListener(this);
        mB.ivExtension.setOnClickListener(this);
        mB.tvMore.setOnClickListener(this);
        mPresenter.onRequest();
    }

    @Override
    public void setData() {
        mB.tvName.setText("用户昵称");
        mB.tvCode.setText("xxxxxx");
        mB.tvNum.setText("6/6");
        mB.tvPromotionNumber.setText("8" + "人");
        mB.tvPromotionMemberNumber.setText("7" + "人");
        mB.tvPromotionMemberPrice.setText("1086.00 " + "元");
        mB.tvGetPrice.setText("1086.00 " + "元");
        mB.tvPrice.setText("85.96");

        showToast("点击分享按钮进入代理商页面");
    }

    private void setTextSize(String str, AppCompatTextView textView){
        Spannable sp = new SpannableString(str) ;
        sp.setSpan(new AbsoluteSizeSpan(20,true),0,sp.length() - 1,Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        sp.setSpan(new AbsoluteSizeSpan(15,true),sp.length() - 1,sp.length(),Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        textView.setText(sp);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.top_right_fy:
                mB.tvPriceText.setText(getText(R.string.promotion_number));
                mB.tvPrice.setTextColor(act.getColor(R.color.black_333333));
                setTextSize("7人", mB.tvPrice);//以推广人数
                setTextSize("7人", mB.tvMonthPromotionNumber);//本月推广人数
                setTextSize("7人", mB.tvMonthPromotionMemberNumber);//本月推广会员人数
                setTextSize("7人", mB.tvMonthPromotionPrice);//本月推广金额
                mB.gpMonth.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_extension:
                UIHelper.startExtensionShareFrg(this);
                break;
            case R.id.tv_more://更多业绩
                UIHelper.startPerformanceDescFrg(this);
                break;
        }
    }
}
