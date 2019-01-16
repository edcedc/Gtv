package com.yc.gtv.view;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.DividerItemDecoration;
import android.view.View;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.umeng.socialize.ShareAction;
import com.yc.gtv.R;
import com.yc.gtv.adapter.GalleryAdapter;
import com.yc.gtv.base.BaseActivity;
import com.yc.gtv.base.BaseFragment;
import com.yc.gtv.bean.DataBean;
import com.yc.gtv.databinding.FGalleryDescBinding;
import com.yc.gtv.event.CollectionInEvent;
import com.yc.gtv.presenter.GalleryDescPresenter;
import com.yc.gtv.utils.GlideLoadingUtils;
import com.yc.gtv.utils.ShareTool;
import com.yc.gtv.view.impl.GalleryDescContract;
import com.yc.gtv.weight.LinearDividerItemDecoration;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edison on 2018/11/19.
 *  图库详情
 */

public class GalleryDescFrg extends BaseFragment<GalleryDescPresenter, FGalleryDescBinding> implements GalleryDescContract.View, View.OnClickListener{

    private List<DataBean> listBean = new ArrayList<>();
    private GalleryAdapter adapter;
    private String id;
    private int position = -1;
    private ShareAction shareAction;

    public static GalleryDescFrg newInstance() {
        Bundle args = new Bundle();
        GalleryDescFrg fragment = new GalleryDescFrg();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {
        id = bundle.getString("id");
        position = bundle.getInt("position");
    }

    @Override
    protected int bindLayout() {
        return R.layout.f_gallery_desc;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.gallery_desc));
        if (adapter == null) {
            adapter = new GalleryAdapter(act, this, listBean, false);
        }
        setRecyclerViewType(mB.recyclerView);
        mB.recyclerView.addItemDecoration(new LinearDividerItemDecoration(act, DividerItemDecoration.VERTICAL,  5));
        mB.recyclerView.setAdapter(adapter);
        mB.refreshLayout.setEnableLoadmore(false);
        mB.refreshLayout.startRefresh();
        setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.onRequest(id);
            }
        });
        mB.tvCollection.setOnClickListener(this);
        mB.tvDownload.setOnClickListener(this);
        mB.tvShare.setOnClickListener(this);
        shareAction = ShareTool.getInstance().shareAction(act, "https://www.baidu.com/");
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
        DataBean bean = (DataBean) data;
        mB.tvTitle.setText(bean.getTitle());
        String[] split = bean.getDate().split(" ");
        mB.tvTime.setText(split[0]);
        mB.tvCollection.setText(bean.isCollected() == true ? "已收藏" : "收藏");

        Object[] images = bean.getImages();
        if (images != null){
            listBean.clear();
            for (Object img: images){
                DataBean bean1 = new DataBean();
                bean1.setUrl((String) img);
                listBean.add(bean1);
            }
            mB.refreshLayout.finishRefreshing();
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View view) {
        if (!((BaseActivity)act).isLogin())return;
        switch (view.getId()){
            case R.id.tv_collection:
                mPresenter.onCollection(id, 1);
                break;
            case R.id.tv_download:
                if (!((BaseActivity)act).isMember())return;
                if (listBean.size() == 0)return;
                for (int i =0;i < listBean.size();i++){
                    DataBean bean = listBean.get(i);
                    if (listBean.size() > 1){
                        GlideLoadingUtils.saveImage(act, bean.getUrl(), mB.tvTitle.getText().toString() + i);
                    }else {
                        GlideLoadingUtils.saveImage(act, bean.getUrl(), mB.tvTitle.getText().toString());
                    }
                }
                break;
            case R.id.tv_share:
                shareAction.open();
                break;
        }
    }

    @Override
    public void onCollectionSuccess(int type) {
        if (position == -1)return;
        mB.tvCollection.setText("已收藏");
        EventBus.getDefault().post(new CollectionInEvent(position, type));
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

}
