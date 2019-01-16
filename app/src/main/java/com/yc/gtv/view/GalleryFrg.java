package com.yc.gtv.view;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.umeng.socialize.ShareAction;
import com.yc.gtv.R;
import com.yc.gtv.adapter.GalleryAdapter;
import com.yc.gtv.base.BaseFragment;
import com.yc.gtv.bean.DataBean;
import com.yc.gtv.databinding.BRecyclerBinding;
import com.yc.gtv.event.CollectionInEvent;
import com.yc.gtv.presenter.GalleryPresenter;
import com.yc.gtv.utils.ShareTool;
import com.yc.gtv.view.impl.GalleryContract;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edison on 2018/11/15.
 *  图库
 */

public class GalleryFrg extends BaseFragment<GalleryPresenter, BRecyclerBinding> implements GalleryContract.View{

    private ShareAction shareAction;

    public static GalleryFrg newInstance() {
        Bundle args = new Bundle();
        GalleryFrg fragment = new GalleryFrg();
        fragment.setArguments(args);
        return fragment;
    }

    private List<DataBean> listBean = new ArrayList<>();
    private GalleryAdapter adapter;
    private boolean isRefresh = true;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.b_recycler;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.gallery), false);
        if (adapter == null){
            adapter = new GalleryAdapter(act, this, listBean, true);
        }
        setRecyclerViewType(mB.recyclerView);
        mB.recyclerView.setAdapter(adapter);
        setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.onRequest(pagerNumber = 1);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                mPresenter.onRequest(pagerNumber += 1);
            }
        });
        setSwipeBackEnable(false);
        adapter.setOnClickListener(new GalleryAdapter.OnClickListener() {
            @Override
            public void collection(int position, int type, String id, boolean collected) {
                mPresenter.onCollection(position, type, id);
            }

            @Override
            public void share() {
                shareAction.open();
            }
        });
        EventBus.getDefault().register(this);
        shareAction = ShareTool.getInstance().shareAction(act, "https://www.baidu.com/");
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

    @Subscribe
    public void onCollectionMainThreadEvent(CollectionInEvent event){

    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if (isRefresh){
            mB.refreshLayout.startRefresh();
            showLoadDataing();
            isRefresh = false;
        }
    }

    @Override
    public void setRefreshLayoutMode(int totalRow) {
        super.setRefreshLayoutMode(listBean.size(), totalRow, mB.refreshLayout);
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        super.setRefreshLayout(pagerNumber, mB.refreshLayout);
    }

    @Override
    public void setData(Object data) {
        List<DataBean> list = (List<DataBean>) data;
        if (pagerNumber == 1) {
            listBean.clear();
            mB.refreshLayout.finishRefreshing();
        } else {
            mB.refreshLayout.finishLoadmore();
        }
        listBean.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCollectionSuccess(int position) {
        DataBean bean = listBean.get(position);
        bean.setCollected(true);
        adapter.notifyItemChanged(position);
    }
}
