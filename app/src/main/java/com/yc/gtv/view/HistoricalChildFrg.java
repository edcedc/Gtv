package com.yc.gtv.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.yc.gtv.R;
import com.yc.gtv.adapter.MyPagerAdapter;
import com.yc.gtv.base.BaseFragment;
import com.yc.gtv.base.BasePresenter;
import com.yc.gtv.databinding.FHistoricalChildBinding;
import com.yc.gtv.event.HistoricalColseInEvent;
import com.yc.gtv.utils.Constants;
import com.yc.gtv.weight.TabEntity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by edison on 2018/11/21.
 *  视频历史 图片历史
 */
@SuppressLint("ValidFragment")
public class HistoricalChildFrg extends BaseFragment<BasePresenter, FHistoricalChildBinding>{

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] strings = {"今天", "7天", "更早"};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private int type;

    public HistoricalChildFrg(int type) {
        this.type = type;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_historical_child;
    }

    @Override
    protected void initView(View view) {
        if (type == Constants.HISTORICAL_VIDEO){
            mFragments.add(new HistoricalChildChildFrg(0, type));
            mFragments.add(new HistoricalChildChildFrg(1, type));
            mFragments.add(new HistoricalChildChildFrg(2, type));
        }else {
            mFragments.add(new HistoricalChildChildFrg(0, type));
            mFragments.add(new HistoricalChildChildFrg(1, type));
            mFragments.add(new HistoricalChildChildFrg(2, type));
        }
        for (int i = 0; i < strings.length; i++) {
            mTabEntities.add(new TabEntity(strings[i], 0, 0));
        }
        mB.tbLayout.setTabData(mTabEntities);
        mB.viewPager.setAdapter(new MyPagerAdapter(getChildFragmentManager(), mFragments));
//        mB.tbLayout.setViewPager(mB.viewPager);
        mB.viewPager.setOffscreenPageLimit(2);
        mB.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                EventBus.getDefault().post(new HistoricalColseInEvent(false));
                mB.tbLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mB.tbLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                EventBus.getDefault().post(new HistoricalColseInEvent(false));
                mB.viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        setSwipeBackEnable(false);
    }
}
