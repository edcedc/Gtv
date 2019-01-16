package com.yc.gtv.view;

import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.lzy.okgo.model.Response;
import com.yc.gtv.R;
import com.yc.gtv.base.BaseFragment;
import com.yc.gtv.base.BasePresenter;
import com.yc.gtv.bean.BaseResponseBean;
import com.yc.gtv.bean.DataBean;
import com.yc.gtv.controller.CloudApi;
import com.yc.gtv.databinding.FMsgDescBinding;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by edison on 2018/11/21.
 * 系统通知
 */

public class NotificationDescFrg extends BaseFragment<BasePresenter, FMsgDescBinding> {

    private DataBean bean;

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initParms(Bundle bundle) {
        bean = new Gson().fromJson(bundle.getString("bean"), DataBean.class);
    }

    @Override
    protected int bindLayout() {
        return R.layout.f_msg_desc;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.msg_desc));
        mB.tvTitle.setText(bean.getTitle());
        mB.tvContent.setText(bean.getContext());
        mB.tvTime.setText(bean.getCreateTime() + "\n视频APP官方");

        CloudApi.noticeSetRead(bean.getId())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<BaseResponseBean<DataBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(Response<BaseResponseBean<DataBean>> baseResponseBeanResponse) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        NotificationDescFrg.this.onError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
