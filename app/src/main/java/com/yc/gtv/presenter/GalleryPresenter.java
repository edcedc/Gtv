package com.yc.gtv.presenter;

import com.lzy.okgo.model.Response;
import com.yc.gtv.bean.BaseListBean;
import com.yc.gtv.bean.BaseResponseBean;
import com.yc.gtv.bean.DataBean;
import com.yc.gtv.callback.Code;
import com.yc.gtv.controller.CloudApi;
import com.yc.gtv.view.impl.GalleryContract;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by edison on 2018/11/29.
 */

public class GalleryPresenter extends GalleryContract.Presenter{
    @Override
    public void onRequest(int pagerNumber) {
        CloudApi.galleryGetGalleryList(pagerNumber)
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
                                if (list != null && list.size() != 0){
                                    mView.setData(list);
                                    mView.setRefreshLayoutMode(data.getPageCount());
                                    mView.hideLoading();
                                }else {
                                    mView.showLoadEmpty();
                                }
                            }
                        }else {
                            mView.showLoadEmpty();
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

    @Override
    public void onCollection(final int position, int type, String id) {
        CloudApi.commonCollect(id, type)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mView.showLoading();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<BaseResponseBean<DataBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mView.addDisposable(d);
                    }

                    @Override
                    public void onNext(Response<BaseResponseBean<DataBean>> baseResponseBeanResponse) {
                         if (baseResponseBeanResponse.body().code == Code.CODE_SUCCESS){
                                mView.onCollectionSuccess(position);
                         }
                         showToast(baseResponseBeanResponse.body().message);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        mView.hideLoading();
                    }
                });
    }
}
