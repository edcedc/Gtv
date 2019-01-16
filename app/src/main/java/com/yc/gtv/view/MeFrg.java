package com.yc.gtv.view;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.AppCompatTextView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;

import com.yc.gtv.R;
import com.yc.gtv.base.BaseActivity;
import com.yc.gtv.base.BaseFragment;
import com.yc.gtv.base.User;
import com.yc.gtv.bean.DataBean;
import com.yc.gtv.controller.UIHelper;
import com.yc.gtv.databinding.FMeBinding;
import com.yc.gtv.event.LoginInEvent;
import com.yc.gtv.presenter.MePresenter;
import com.yc.gtv.utils.GlideLoadingUtils;
import com.yc.gtv.view.impl.MeContract;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

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

    @RequiresApi(api = Build.VERSION_CODES.M)
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
        mPresenter.onRequest();
        setTextSize(0 + "/人", mB.tvUserNumber);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    //登录成功之后UI改变
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainLoginInEvent(LoginInEvent event){
        mPresenter.onRequest();
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
        if (User.getInstance().isLogin()){
            onUserGetUserInfo(User.getInstance().getUserInfo());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_head:
//                if (!User.getInstance().isLogin())return;
//                UIHelper.startAccountManagementFrg(this);
                break;
            case R.id.tv_login:
                if (User.getInstance().isLogin()){
                    JSONObject userInfo = User.getInstance().getUserInfo();
                    if (userInfo != null && !userInfo.optBoolean("vip")){
                        UIHelper.startMemberCenterFrg(this);
                    }
                }else {
                    UIHelper.startLoginAct();
                }
                break;
            case R.id.tv_not_login:
//                UIHelper.startLoginAct();
                break;
            case R.id.top_right_fy:
                if (!((BaseActivity)act).isLogin())return;
                UIHelper.startSetAct();
                break;
        }
    }

    @Override
    public void onUserAnonymousViewTimesAtToday(DataBean data) {
        mB.tvNum.setText((data.getRemainingViewTimes() < 0 == true ? 0 : data.getRemainingViewTimes()) + "/" + data.getAllowViewTimes());
    }

    @Override
    public void onUserGetUserInfo(JSONObject userInfo) {
        if (userInfo == null)return;
        GlideLoadingUtils.load(act, userInfo.optString("headImg"), mB.ivHead);
        mB.tvTime.setText(userInfo.optString("expirTime") + "到期");
        String mobile = userInfo.optString("mobile");
        if (mobile != null && mobile.length() >= 9){
            String substring = mobile.substring(0, 3);
            String substring1 = mobile.substring(mobile.length() - 4, mobile.length());
            mB.tvNotLogin.setText(substring + "****" + substring1 + "/" + userInfo.optString("nickname"));
        }
        if (userInfo.optBoolean("vip")){
            mB.gpMember.setVisibility(View.GONE);
            mB.gpMemberTime.setVisibility(View.VISIBLE);
            mB.ivImg.setBackgroundResource(R.mipmap.my_zz_bg);
        }else {
            mB.tvNotLogin.setOnClickListener(null);
            mB.tvLogin.setText(getString(R.string.purchase_member2));
        }
        setTextSize(userInfo.optInt("referNum") + "/人", mB.tvUserNumber);
    }
}
