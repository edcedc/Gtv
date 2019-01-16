package com.yc.gtv.presenter;

import com.lzy.okgo.model.Response;
import com.yc.gtv.bean.BaseListBean;
import com.yc.gtv.bean.BaseResponseBean;
import com.yc.gtv.bean.DataBean;
import com.yc.gtv.callback.Code;
import com.yc.gtv.controller.CloudApi;
import com.yc.gtv.view.impl.ChannelNameContract;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by edison on 2018/11/19.
 */

public class ChannelNamePresenter extends ChannelNameContract.Presenter{

    @Override
    public void onRequest(String tagId, int pagerNumber) {
        List<DataBean> listLabel = new ArrayList<>();
        DataBean bean = new DataBean();
        bean.setTagId(tagId);
        listLabel.add(bean);
        CloudApi.videoGetVideoByTagIds(pagerNumber, listLabel)
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
                                mView.setData(list);
                                if (list != null && list.size() != 0){
                                    mView.setRefreshLayoutMode(data.getTotal());
                                    mView.hideLoading();
                                }
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

    @Override
    public void onChannelGetChannelTagDetail(String id) {
        CloudApi.channelGetChannelTagDetail(id)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mView.showLoadDataing();
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
                            DataBean data = baseResponseBeanResponse.body().data;
                            if (data != null){
                                mView.onChannelGetChannelTagDetailSuccess(data);
                            }
                        }
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
