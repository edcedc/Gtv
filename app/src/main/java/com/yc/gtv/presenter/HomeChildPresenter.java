package com.yc.gtv.presenter;

import com.lzy.okgo.model.Response;
import com.yc.gtv.bean.BaseListBean;
import com.yc.gtv.bean.BaseResponseBean;
import com.yc.gtv.bean.DataBean;
import com.yc.gtv.callback.Code;
import com.yc.gtv.controller.CloudApi;
import com.yc.gtv.view.impl.HomeChildContract;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * 作者：yc on 2018/8/28.
 * 邮箱：501807647@qq.com
 * 版本：v1.0
 */

public class HomeChildPresenter extends HomeChildContract.Presenter{

    @Override
    public void onRequest(int pagerNumber, String id) {
        CloudApi.homeVediosByClassify(pagerNumber, id)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<BaseResponseBean<BaseListBean<DataBean>>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mView.addDisposable(d);
                    }

                    @Override
                    public void onNext(Response<BaseResponseBean<BaseListBean<DataBean>>> baseResponseBeanResponse) {
                        if (baseResponseBeanResponse.body().code == Code.CODE_SUCCESS){
                            BaseListBean<DataBean> data = baseResponseBeanResponse.body().data;
                            if (data != null){
                                List<DataBean> list = data.getRows();
                                if (list != null){
                                    mView.setData(list);
                                    mView.setRefreshLayoutMode(data.getTotal());
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onError(e, "homeVediosByClassify");
                    }

                    @Override
                    public void onComplete() {
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void onBanner() {
        CloudApi.list2(CloudApi.homeGetBanner)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<BaseResponseBean<List<DataBean>>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mView.addDisposable(d);
                    }

                    @Override
                    public void onNext(Response<BaseResponseBean<List<DataBean>>> baseResponseBeanResponse) {
                        if (baseResponseBeanResponse.body().code == Code.CODE_SUCCESS){
                            List<DataBean> data = baseResponseBeanResponse.body().data;
                            if (data != null){
                                mView.setBannerData(data);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
