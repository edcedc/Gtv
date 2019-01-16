package com.yc.gtv.view;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.NestedScrollView;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.yanzhenjie.sofia.Sofia;
import com.yc.gtv.R;
import com.yc.gtv.base.BaseFragment;
import com.yc.gtv.base.User;
import com.yc.gtv.controller.UIHelper;
import com.yc.gtv.databinding.FMemberCenterBinding;
import com.yc.gtv.event.MemberSuccessInEvent;
import com.yc.gtv.event.PayInEvent;
import com.yc.gtv.presenter.MemberCenterPresenter;
import com.yc.gtv.utils.GlideLoadingUtils;
import com.yc.gtv.view.impl.MemberCenterContract;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

/**
 * Created by edison on 2018/11/22.
 *  会员中心
 */

public class MemberCenterFrg extends BaseFragment<MemberCenterPresenter, FMemberCenterBinding> implements MemberCenterContract.View, View.OnClickListener{

    private View mToolbar;
    private View top_view;
    private View status_view;

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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initView(View view) {
        mToolbar = view.findViewById(R.id.toolbar);
        top_view = view.findViewById(R.id.top_view);
        status_view = view.findViewById(R.id.status_view);
        setTitle(getString(R.string.member_center));
        setAnyBarAlpha(0);

        mB.btJoin.setOnClickListener(this);
        mB.btRenew.setOnClickListener(this);
        mPresenter.onRequest();
        EventBus.getDefault().register(this);

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
    }

    private void setAnyBarAlpha(int alpha) {
        mToolbar.getBackground().mutate().setAlpha(alpha);
        top_view.getBackground().mutate().setAlpha(alpha);
        status_view.getBackground().mutate().setAlpha(alpha);
        Sofia.with(act)
                .statusBarBackgroundAlpha(alpha);
    }

    @Subscribe
    public void onMainThreadInEvent(MemberSuccessInEvent event){
        onSupportVisible();
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
//        setSofia(true);
        JSONObject userInfo = User.getInstance().getUserInfo();
        if (userInfo != null){
            GlideLoadingUtils.load(act, userInfo.optString("headImg"), mB.ivHead);
            mB.tvName.setText(userInfo.optString("nickname"));
            mB.tvTime.setText(userInfo.optString("expirTime") + "到期");
            if (userInfo.optBoolean("vip")){
                mB.gpMember.setVisibility(View.VISIBLE);
                mB.gpJoin.setVisibility(View.GONE);
                mB.ivImg.setBackgroundResource(R.mipmap.my_zz_bg);
            }else {
                mB.gpMember.setVisibility(View.GONE);
                mB.gpJoin.setVisibility(View.VISIBLE);
                mB.ivImg.setBackgroundResource(R.mipmap.my_zz_join_bg);
            }
        }
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

    @Override
    public void setData(String remark) {
        mB.webView.loadDataWithBaseURL(null, remark, "text/html", "utf-8", null);
    }
}
