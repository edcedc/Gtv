package com.yc.gtv.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.yc.gtv.R;
import com.yc.gtv.adapter.MeAdapter;
import com.yc.gtv.base.BaseFragment;
import com.yc.gtv.base.BasePresenter;
import com.yc.gtv.bean.DataBean;
import com.yc.gtv.bean.SaveSearchListBean;
import com.yc.gtv.controller.UIHelper;
import com.yc.gtv.databinding.BRecyclerBinding;
import com.yc.gtv.utils.PopupWindowTool;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edison on 2018/11/20.
 *  设置
 */

public class SetFrg extends BaseFragment<BasePresenter, BRecyclerBinding>{

    public static SetFrg newInstance() {
        Bundle args = new Bundle();
        SetFrg fragment = new SetFrg();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.b_recycler;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.set));
        String[] str = {act.getString(R.string.account_management), act.getString(R.string.clean_caching), act.getString(R.string.updated_version), act.getString(R.string.user_protocol)};
        List<DataBean> listBean = new ArrayList<>();
        for (int i = 0;i < str.length;i++){
            DataBean bean = new DataBean();
            bean.setName(str[i]);
            listBean.add(bean);
        }
        hideLoading();
        mB.refreshLayout.setPureScrollModeOn();
        setRecyclerViewType(mB.recyclerView);
        MeAdapter meAdapter = new MeAdapter(act, listBean, true);
        mB.recyclerView.setAdapter(meAdapter);
        meAdapter.setClick(new MeAdapter.OnClick() {
            @Override
            public void onClick(int position, String text) {
                switch (position){
                    case 0:
                        UIHelper.startAccountManagementFrg(SetFrg.this);
                        break;
                    case 1:
                        PopupWindowTool.showDialog(act, PopupWindowTool.clear_cache, new PopupWindowTool.DialogListener() {
                            @Override
                            public void onClick() {
                                showLoading();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        LitePal.deleteAll(SaveSearchListBean.class);
                                        hideLoading();
                                        showToast("清除缓存成功");
                                    }
                                }, 1000);
                            }
                        });
                        break;
                    case 2:
                        initVersion();
                        break;
                    case 3:
                        UIHelper.startHtmlAct();
                        break;
                }
            }
        });
    }

    private void initVersion() {
        Uri uri = Uri.parse("https://www.baidu.com");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);

        /*CloudApi.versionVersionClient()
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        listener.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<BaseResponseBean<DataBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        listener.onAddDisposable(d);
                    }

                    @Override
                    public void onNext(Response<BaseResponseBean<DataBean>> baseResponseBeanResponse) {
                        if (baseResponseBeanResponse.body().code == Code.CODE_SUCCESS){
                            DataBean data = baseResponseBeanResponse.body().data;
                            if (data != null){
                                String appVersionName = AppUtils.getAppVersionName();
                                if (!appVersionName.equals(data.getVersion())){
                                    listener.showVersion(data.getUrl(), data.getVersion());
                                }
                            }
                        }else {
                            ToastUtils.showShort(baseResponseBeanResponse.body().desc);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        listener.hideLoading();
                    }
                });*/
    }
}
