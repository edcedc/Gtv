package com.yc.gtv.view;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.LogUtils;
import com.yc.gtv.R;
import com.yc.gtv.adapter.VideoDescListAdapter;
import com.yc.gtv.base.BaseFragment;
import com.yc.gtv.bean.DataBean;
import com.yc.gtv.databinding.FChildListBinding;
import com.yc.gtv.event.HistoricalColseInEvent;
import com.yc.gtv.event.HistoricalEditInEvent;
import com.yc.gtv.presenter.MyCacheChildPresenter;
import com.yc.gtv.utils.Constants;
import com.yc.gtv.view.impl.MyCacheChildContract;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edison on 2018/11/21.
 * 图片视频历史记录
 */

@SuppressLint("ValidFragment")
public class MyCacheChildFrg extends BaseFragment<MyCacheChildPresenter, FChildListBinding> implements MyCacheChildContract.View, View.OnClickListener {

    private List<DataBean> listBean = new ArrayList<>();
    private VideoDescListAdapter adapter;

    private boolean isRefresh = false;
    private int type;//哪个页面进来的

    public MyCacheChildFrg(int type) {
        this.type = type;
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
        return R.layout.f_child_list;
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if (!isRefresh) {
            isRefresh = true;
            mB.refreshLayout.startRefresh();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initView(View view) {
        mB.tvTotalSelection.setOnClickListener(this);
        mB.tvDel.setOnClickListener(this);
        if (adapter == null) {
            adapter = new VideoDescListAdapter(act, listBean, type);
        }
        setRecyclerViewType(mB.recyclerView);
        mB.recyclerView.setAdapter(adapter);
        mB.refreshLayout.setPureScrollModeOn();
        mPresenter.onRequest(type);
        EventBus.getDefault().register(this);
        setSwipeBackEnable(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onMainEditInEvent(HistoricalEditInEvent event) {
        adapter.setEdit(event.isEdit);
        adapter.notifyDataSetChanged();
        mB.lyBottom.setVisibility(event.isEdit == true ? View.VISIBLE : View.GONE);
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        super.setRefreshLayout(pagerNumber, mB.refreshLayout);
    }

    @Override
    public void setData(Object data) {
        List<DataBean> list = (List<DataBean>) data;
        mB.refreshLayout.finishRefreshing();
        listBean.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_total_selection:
                adapter.setAllSelect(true);
                adapter.notifyDataSetChanged();
                break;
            case R.id.tv_del:
                List<DataBean> temp = new ArrayList<>();
                List<String> listStr = new ArrayList<>();
                for (int i = 0; i < listBean.size(); i++) {
                    DataBean bean = listBean.get(i);
                    if (bean.isSelect()){
                        temp.add(bean);
                        listStr.add(bean.getCover());
                    }
                }
                listBean.removeAll(temp);
                adapter.notifyDataSetChanged();

                for (String name:listStr){
                    if (type == Constants.CACHE_IMG){
                        boolean b = FileUtils.deleteFile( name);
                    }else {
                        boolean b = FileUtils.deleteFile(name);
                    }
                }

                if (listBean.size() == 0) {
                    showLoadEmpty();
                    EventBus.getDefault().post(new HistoricalColseInEvent(false));
                }
                break;
        }
    }
}
