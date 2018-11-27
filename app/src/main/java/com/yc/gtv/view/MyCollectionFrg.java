package com.yc.gtv.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.yc.gtv.R;
import com.yc.gtv.adapter.MyPagerAdapter;
import com.yc.gtv.base.BaseFragment;
import com.yc.gtv.base.BasePresenter;
import com.yc.gtv.databinding.FHistoricalBinding;
import com.yc.gtv.event.HistoricalColseInEvent;
import com.yc.gtv.event.HistoricalEditInEvent;
import com.yc.gtv.utils.Constants;
import com.yc.gtv.weight.TabEntity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

/**
 * Created by edison on 2018/11/21.
 *  我的图片
 */
@SuppressLint("ValidFragment")
public class MyCollectionFrg extends BaseFragment<BasePresenter, FHistoricalBinding>{

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] strings = {"视频", "图片"};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private TextView topRight;

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_historical;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.my_collection), getString(R.string.edit));
        topRight = view.findViewById(R.id.top_right);
        mFragments.add(new MyCollectionChildFrg(Constants.HISTORICAL_CHILD_VIDEO));
        mFragments.add(new MyCollectionChildFrg(Constants.HISTORICAL_CHILD_IMG));

        for (int i = 0; i < strings.length; i++) {
            mTabEntities.add(new TabEntity(strings[i], 0, 0));
        }
        mB.tbLayout.setTabData(mTabEntities);
        mB.viewPager.setAdapter(new MyPagerAdapter(getChildFragmentManager(), mFragments));
//        mB.tbLayout.setViewPager(mB.viewPager);
        mB.viewPager.setOffscreenPageLimit(1);
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
        EventBus.getDefault().register(this);
    }

    @Subscribe
    public void onMainThreadInEvent(HistoricalColseInEvent event){
        if (!event.isClose){
            setTitle(getString(R.string.historical_sightseeing), getString(R.string.edit));
            EventBus.getDefault().post(new HistoricalEditInEvent(false));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void setOnRightClickListener() {
        super.setOnRightClickListener();
        String s = topRight.getText().toString();
        if (s.equals(getString(R.string.edit))){
            setTitle(getString(R.string.historical_sightseeing), getString(R.string.cancel));
            EventBus.getDefault().post(new HistoricalEditInEvent(true));
        }else {
            setTitle(getString(R.string.historical_sightseeing), getString(R.string.edit));
            EventBus.getDefault().post(new HistoricalEditInEvent(false));
        }
    }
}
