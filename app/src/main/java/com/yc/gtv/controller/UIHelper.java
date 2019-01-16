package com.yc.gtv.controller;

import android.os.Bundle;

import com.blankj.utilcode.util.ActivityUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yc.gtv.MainActivity;
import com.yc.gtv.base.BaseFragment;
import com.yc.gtv.bean.DataBean;
import com.yc.gtv.view.AccountManagementFrg;
import com.yc.gtv.view.AllChannelFrg;
import com.yc.gtv.view.BalanceDetailsFrg;
import com.yc.gtv.view.ChannelNameFrg;
import com.yc.gtv.view.ExtensionFrg;
import com.yc.gtv.view.ExtensionShareFrg;
import com.yc.gtv.view.ForgetPwdFrg;
import com.yc.gtv.view.HistoricalRecordsFrg;
import com.yc.gtv.view.LabelScreeningFrg;
import com.yc.gtv.view.MainFrg;
import com.yc.gtv.view.MemberCenterFrg;
import com.yc.gtv.view.MyCacheFrg;
import com.yc.gtv.view.MyCollectionFrg;
import com.yc.gtv.view.NotificationDescFrg;
import com.yc.gtv.view.NotificationFrg;
import com.yc.gtv.view.OpinionFrg;
import com.yc.gtv.view.PerformanceDescFrg;
import com.yc.gtv.view.PurchaseMemberFrg;
import com.yc.gtv.view.RegisterFrg;
import com.yc.gtv.view.SearchFrg;
import com.yc.gtv.view.act.GalleryDescAct;
import com.yc.gtv.view.act.HtmlAct;
import com.yc.gtv.view.act.LoginAct;
import com.yc.gtv.view.act.SetAct;
import com.yc.gtv.view.act.VideoDescAct;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/2/22.
 */

public final class UIHelper {

    private UIHelper() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void startMainAct() {
        ActivityUtils.startActivity(MainActivity.class);
    }

    /**
     * 设置
     */
    public static void startSetAct() {
        ActivityUtils.startActivity(SetAct.class);
    }


    public static void startHtmlAct(String desc) {
        Bundle bundle = new Bundle();
        bundle.putString("url", desc);
        ActivityUtils.startActivity(bundle, HtmlAct.class);
    }
    public static void startHtmlAct(int type) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        ActivityUtils.startActivity(bundle, HtmlAct.class);
    }

    /**
     * 登录
     */
    public static void startLoginAct() {
        ActivityUtils.startActivity(LoginAct.class);
    }

    /**
     * 忘记密码  重置密码
     */
    public static void startForgetPwdFrg(BaseFragment root, int pwdType) {
        ForgetPwdFrg frg = new ForgetPwdFrg();
        Bundle bundle = new Bundle();
        bundle.putInt("type", pwdType);
        frg.setArguments(bundle);
        root.start(frg);
    }

    /**
     * 注册
     *
     * @param root
     */
    public static void startRegisterFrg(BaseFragment root) {
        RegisterFrg frg = new RegisterFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        root.start(frg);
    }

    /**
     * 影片详情
     * @param id
     */
    public static void startVideoDescAct(String id) {
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        ActivityUtils.startActivity(bundle, VideoDescAct.class);
    }

    /**
     * 搜索
     */
    public static void startSearchFrg(BaseFragment root) {
        SearchFrg frg = new SearchFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        ((MainFrg) root.getParentFragment()).startBrotherFragment(frg);
    }

    /**
     * 全部频道
     */
    public static void startAllChannelFrg(BaseFragment root) {
        AllChannelFrg frg = new AllChannelFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        ((MainFrg) root.getParentFragment()).startBrotherFragment(frg);
    }

    /**
     * 频道名称
     */
    public static void startChannelNameFrg(BaseFragment root, String tagId) {
        ChannelNameFrg frg = new ChannelNameFrg();
        Bundle bundle = new Bundle();
        bundle.putString("id", tagId);
        frg.setArguments(bundle);
        ((MainFrg) root.getParentFragment()).startBrotherFragment(frg);
    }

    /**
     * 频道名称
     */
    public static void startGalleryDescAct(String id, int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putString("id", id);
        ActivityUtils.startActivity(bundle, GalleryDescAct.class);
    }

    /**
     * 历史记录
     */
    public static void startHistoricalRecordsFrg(BaseFragment root) {
        HistoricalRecordsFrg frg = new HistoricalRecordsFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        ((MainFrg) root.getParentFragment()).startBrotherFragment(frg);
    }

    /**
     * 意见反馈
     */
    public static void startOpinionFrg(BaseFragment root) {
        OpinionFrg frg = new OpinionFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        ((MainFrg) root.getParentFragment()).startBrotherFragment(frg);
    }

    /**
     * 系统通知
     */
    public static void startNotificationFrg(BaseFragment root) {
        NotificationFrg frg = new NotificationFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        ((MainFrg) root.getParentFragment()).startBrotherFragment(frg);
    }

    /**
     * 我要推广
     */
    public static void startExtensionFrg(BaseFragment root) {
        ExtensionFrg frg = new ExtensionFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        ((MainFrg) root.getParentFragment()).startBrotherFragment(frg);
    }

    /**
     * 会员中心
     */
    public static void startMemberCenterFrg(BaseFragment root) {
        MemberCenterFrg frg = new MemberCenterFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        ((MainFrg) root.getParentFragment()).startBrotherFragment(frg);
    }

    /**
     * 通知详情
     */
    public static void startNotificationDescFrg(BaseFragment root, DataBean bean) {
        NotificationDescFrg frg = new NotificationDescFrg();
        Bundle bundle = new Bundle();
        bundle.putString("bean", new Gson().toJson(bean));
        frg.setArguments(bundle);
        root.start(frg);
    }

    /**
     * 业绩明细
     */
    public static void startPerformanceDescFrg(BaseFragment root) {
        PerformanceDescFrg frg = new PerformanceDescFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        root.start(frg);
    }

    /**
     * 购买会员
     */
    public static void startPurchaseMemberFrg(BaseFragment root) {
        PurchaseMemberFrg frg = new PurchaseMemberFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        root.start(frg);
    }

    /**
     * 推广分享
     */
    public static void startExtensionShareFrg(BaseFragment root) {
        ExtensionShareFrg frg = new ExtensionShareFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        root.start(frg);
    }

    /**
     * 我的缓存
     */
    public static void startMyCacheFrg(BaseFragment root) {
        MyCacheFrg frg = new MyCacheFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        ((MainFrg) root.getParentFragment()).startBrotherFragment(frg);
    }

    /**
     * 我的收藏
     */
    public static void startMyCollectionFrg(BaseFragment root) {
        MyCollectionFrg frg = new MyCollectionFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        ((MainFrg) root.getParentFragment()).startBrotherFragment(frg);
    }

    /**
     * 标签筛选
     */
    public static void startLabelScreeningFrg(BaseFragment root, List<DataBean> list) {
        LabelScreeningFrg frg = new LabelScreeningFrg();
        Bundle bundle = new Bundle();
        bundle.putString("list", new Gson().toJson(list, new TypeToken<ArrayList<DataBean>>() {}.getType()));
        frg.setArguments(bundle);
        root.start(frg);
    }

    /**
     * 账户管理
     */
    public static void startAccountManagementFrg(BaseFragment root) {
        AccountManagementFrg frg = new AccountManagementFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        root.start(frg);
    }
   /**
     * 余额明细
     */
    public static void startBalanceDetailsFrg(BaseFragment root) {
        BalanceDetailsFrg frg = new BalanceDetailsFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        root.start(frg);
    }

}