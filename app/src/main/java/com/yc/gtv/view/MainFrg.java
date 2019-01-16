package com.yc.gtv.view;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import com.yc.gtv.R;
import com.yc.gtv.base.BaseFragment;
import com.yc.gtv.base.BasePresenter;
import com.yc.gtv.base.IBaseView;
import com.yc.gtv.databinding.FMainBinding;
import com.yc.gtv.weight.buttonBar.BottomBar;
import com.yc.gtv.weight.buttonBar.BottomBarTab;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * 作者：yc on 2018/7/25.
 * 邮箱：501807647@qq.com
 * 版本：v1.0
 */

public class MainFrg extends BaseFragment<BasePresenter, FMainBinding> implements IBaseView {

    public static MainFrg newInstance() {
        Bundle args = new Bundle();
        MainFrg fragment = new MainFrg();
        fragment.setArguments(args);
        return fragment;
    }

    private final int FIRST = 0;
    private final int SECOND = 1;
    private final int THIRD = 2;
    private final int FOUR = 3;

    private SupportFragment[] mFragments = new SupportFragment[4];


    @Override
    public void initPresenter() {

    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_main;
    }

    @Override
    protected void initView(View view) {
       mB.bottomBar
                .addItem(new BottomBarTab(_mActivity, R.mipmap.tab_home_nor, "首页"))
                .addItem(new BottomBarTab(_mActivity, R.mipmap.tab_pd_nor,"频道"))
                .addItem(new BottomBarTab(_mActivity, R.mipmap.tab_tk_nor,"图库"))
                .addItem(new BottomBarTab(_mActivity, R.mipmap.tab_my_nor,"我的"));
        mB.bottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                if (position == SECOND){
//                    if (!isUserLogin())return;
                }
                showHideFragment(mFragments[position], mFragments[prePosition]);
            }

            @Override
            public void onTabUnselected(int position) {
            }

            @Override
            public void onTabReselected(int position) {
                // 在FirstPagerFragment,FirstHomeFragment中接收, 因为是嵌套的Fragment
                // 主要为了交互: 重选tab 如果列表不在顶部则移动到顶部,如果已经在顶部,则刷新
//                EventBusActivityScope.getDefault(_mActivity).post(new TabSelectedEvent(position));
            }
        });
        mB.bottomBar.setCurrentItem(0);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SupportFragment firstFragment = findChildFragment(HomeFrg.class);
        if (firstFragment == null) {
            mFragments[FIRST] = HomeFrg.newInstance();
            mFragments[SECOND] = ChannelFrg.newInstance();
            mFragments[THIRD] = GalleryFrg.newInstance();
            mFragments[FOUR] = MeFrg.newInstance();

            loadMultipleRootFragment(R.id.fl_container,
                    FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD],
                    mFragments[FOUR]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题

            // 这里我们需要拿到mFragments的引用
            mFragments[FIRST] = firstFragment;
            mFragments[SECOND] = findChildFragment(ChannelFrg.class);
            mFragments[THIRD] = findChildFragment(GalleryFrg.class);
            mFragments[FOUR] = findChildFragment(MeFrg.class);
        }
        setSwipeBackEnable(false);
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
//        setSofia(false);
    }

    /**
     * start other BrotherFragment
     */
    public void startBrotherFragment(SupportFragment targetFragment) {
        start(targetFragment);
    }

    @Override
    public boolean onBackPressedSupport() {
        exitBy2Click();// 调用双击退出函数
//        return super.onBackPressedSupport();
        return true;
    }

    private Boolean isExit = false;

    private void exitBy2Click() {
        Handler tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            showToast("再按一次退出程序");
            tExit = new Handler();
            tExit.postDelayed(new Runnable() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000);// 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
            return;
        } else {
//            Cockroach.uninstall();
            act.finish();
            System.exit(0);
        }
    }

}
