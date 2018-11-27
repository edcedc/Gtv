package com.yc.gtv.controller;

import android.os.Bundle;

import com.blankj.utilcode.util.ActivityUtils;
import com.yc.gtv.MainActivity;
import com.yc.gtv.base.BaseFragment;
import com.yc.gtv.view.AccountManagementFrg;
import com.yc.gtv.view.AllChannelFrg;
import com.yc.gtv.view.ChannelNameFrg;
import com.yc.gtv.view.ExtensionFrg;
import com.yc.gtv.view.ExtensionShareFrg;
import com.yc.gtv.view.ForgetPwdFrg;
import com.yc.gtv.view.GalleryDescFrg;
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
import com.yc.gtv.view.act.HtmlAct;
import com.yc.gtv.view.act.LoginAct;
import com.yc.gtv.view.act.SetAct;
import com.yc.gtv.view.act.VideoDescAct;


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


    public static void startHtmlAct() {
        Bundle bundle = new Bundle();
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
     */
    public static void startVideoDescAct() {
        ActivityUtils.startActivity(VideoDescAct.class);
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
    public static void startChannelNameFrg(BaseFragment root) {
        ChannelNameFrg frg = new ChannelNameFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        ((MainFrg) root.getParentFragment()).startBrotherFragment(frg);
    }

    /**
     * 频道名称
     */
    public static void startGalleryDescFrg(BaseFragment root) {
        GalleryDescFrg frg = new GalleryDescFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        ((MainFrg) root.getParentFragment()).startBrotherFragment(frg);
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
    public static void startNotificationDescFrg(BaseFragment root) {
        NotificationDescFrg frg = new NotificationDescFrg();
        Bundle bundle = new Bundle();
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
    public static void startLabelScreeningFrg(BaseFragment root) {
        LabelScreeningFrg frg = new LabelScreeningFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        root.start(frg);
    }

    /**
     * 标签筛选
     */
    public static void startAccountManagementFrg(BaseFragment root) {
        AccountManagementFrg frg = new AccountManagementFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        root.start(frg);
    }

}