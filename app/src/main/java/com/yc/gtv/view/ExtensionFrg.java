package com.yc.gtv.view;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.AppCompatTextView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.umeng.socialize.ShareAction;
import com.yanzhenjie.sofia.Sofia;
import com.yc.gtv.R;
import com.yc.gtv.base.BaseFragment;
import com.yc.gtv.base.User;
import com.yc.gtv.bean.DataBean;
import com.yc.gtv.controller.UIHelper;
import com.yc.gtv.databinding.FExtensionBinding;
import com.yc.gtv.presenter.ExtensionPresenter;
import com.yc.gtv.utils.GlideLoadingUtils;
import com.yc.gtv.utils.ShareTool;
import com.yc.gtv.view.impl.ExtensionContract;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

/**
 * Created by edison on 2018/11/22.
 * 我要推广
 */

public class ExtensionFrg extends BaseFragment<ExtensionPresenter, FExtensionBinding> implements ExtensionContract.View, View.OnClickListener {

    private View mToolbar;
    private View top_view;
    private View status_view;
    private ShareAction shareAction;

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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initView(View view) {
        mToolbar = view.findViewById(R.id.toolbar);
        top_view = view.findViewById(R.id.top_view);
        status_view = view.findViewById(R.id.status_view);
        mB.lyPrice.setOnClickListener(this);
        setTitle(getString(R.string.promote), R.mipmap.tg_nav_share);
        setAnyBarAlpha(0);
        TextViewCompat.setAutoSizeTextTypeWithDefaults(mB.tvMonthPromotionPrice, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);

        mB.ivExtension.setOnClickListener(this);
        mB.tvMore.setOnClickListener(this);
        mPresenter.onRequest();
        mPresenter.onQueryAPPAgreement();
        mPresenter.onUserGetViewTimesAtToday();


        mB.webView.setInitialScale(100);
        mB.webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView var1, int var2, String var3, String var4) {
                ToastUtils.showShort("网页加载失败");
            }
        });
        mB.scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                int headerHeight = mB.ivImg.getHeight();
                int scrollDistance = Math.min(scrollY, headerHeight);
                int statusAlpha = (int) ((float) scrollDistance / (float) headerHeight * 255F);
                setAnyBarAlpha(statusAlpha);
            }
        });
        shareAction = ShareTool.getInstance().shareAction(act, "https://www.baidu.com/");

    }

    private void setAnyBarAlpha(int alpha) {
        mToolbar.getBackground().mutate().setAlpha(alpha);
        top_view.getBackground().mutate().setAlpha(alpha);
        status_view.getBackground().mutate().setAlpha(alpha);
        Sofia.with(act)
                .statusBarBackgroundAlpha(alpha);
    }

    @Override
    protected void setOnRightClickListener() {
        super.setOnRightClickListener();
        shareAction.open();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        ShareTool.getInstance().release(act);
    }

    /**
     * 屏幕横竖屏切换时避免出现window leak的问题
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        shareAction.close();
    }

    private void setTextSize(String str, AppCompatTextView textView) {
        Spannable sp = new SpannableString(str);
        sp.setSpan(new AbsoluteSizeSpan(20, true), 0, sp.length() - 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        sp.setSpan(new AbsoluteSizeSpan(13, true), sp.length() - 1, sp.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        textView.setText(sp);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_extension://分享
                UIHelper.startExtensionShareFrg(this);
                break;
            case R.id.tv_more://更多业绩
                UIHelper.startPerformanceDescFrg(this);
                break;
            case R.id.ly_price://业绩明细
                UIHelper.startBalanceDetailsFrg(this);
                break;
        }
    }

    @Override
    public void setAgreement(String remark) {
        mB.webView.loadDataWithBaseURL(null, remark, "text/html", "utf-8", null);
    }

    @Override
    public void onUserAnonymousViewTimesAtToday(DataBean data) {
        mB.tvNum.setText((data.getRemainingViewTimes() < 0 == true ? 0 : data.getRemainingViewTimes()) + "/" + data.getAllowViewTimes());
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void setData(DataBean data) {
        JSONObject userInfo = User.getInstance().getUserInfo();
        if (userInfo != null) {
            GlideLoadingUtils.load(act, userInfo.optString("headImg"), mB.ivHead);
            mB.tvName.setText(userInfo.optString("nickname"));
            mB.tvCode.setText(userInfo.optString("invitCode"));

            if (!userInfo.optBoolean("oem")) {//是否代理商
                mB.tvPrice.setText(data.getBonus() + "");
            } else {
                mB.tvPriceText.setText(getText(R.string.promotion_number));
                mB.tvPrice.setTextColor(act.getColor(R.color.black_333333));
                setTextSize(data.getNumberOfPeoplePromoted() + " 人", mB.tvPrice);//以推广人数
                setTextSize(data.getNumberOfPeoplePromoted() + " 人", mB.tvMonthPromotionNumber);//本月推广人数
                setTextSize(data.getNumberOfVipMember() + " 人", mB.tvMonthPromotionMemberNumber);//本月推广会员人数
//                setTextSize(data.getBonus() + " 元", mB.tvMonthPromotionPrice);//本月推广金额
                mB.gpMonth.setVisibility(View.VISIBLE);
                mB.tvMonthPromotionPrice.setText(data.getBonus() + "");
            }
            mB.tvPromotionNumber.setText(data.getNumberOfPeoplePromoted() + "人");
            mB.tvPromotionMemberNumber.setText(data.getNumberOfVipMember() + "人");
            mB.tvPromotionMemberPrice.setText(data.getPromotedAmount() + " 元");
            mB.tvGetPrice.setText(data.getBonus() + " 元");
        }
    }
}
