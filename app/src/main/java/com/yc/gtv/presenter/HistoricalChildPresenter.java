package com.yc.gtv.presenter;

import com.blankj.utilcode.util.TimeUtils;
import com.lzy.okgo.model.Response;
import com.yc.gtv.bean.BaseListBean;
import com.yc.gtv.bean.BaseResponseBean;
import com.yc.gtv.bean.DataBean;
import com.yc.gtv.callback.Code;
import com.yc.gtv.controller.CloudApi;
import com.yc.gtv.utils.Constants;
import com.yc.gtv.utils.DateUtils;
import com.yc.gtv.view.impl.HistoricalChildContract;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by edison on 2018/11/21.
 */

public class HistoricalChildPresenter extends HistoricalChildContract.Presenter{

    @Override
    public void onRequest(int pagerNumber, int mType, final int type) {
        String url;
        long startTime = 0;
        long endTime = 0;
       if (type == Constants.HISTORICAL_VIDEO){
           url = CloudApi.historyGetVideoHistoryList;
       }else {
           url = CloudApi.historyGetGalleryHistoryList;
       }
       switch (mType){
           case 0:
               startTime = TimeUtils.string2Millis(DateUtils.zeroTime(TimeUtils.getNowString()));
               endTime = TimeUtils.string2Millis(DateUtils.zeroTime(DateUtils.addTime(TimeUtils.getNowString(), 1)));
               break;
           case 1:
               startTime = TimeUtils.string2Millis(DateUtils.reduceTime(TimeUtils.getNowString(), 7));
               endTime = TimeUtils.getNowMills();
               break;
           case 2:
               startTime = TimeUtils.string2Millis(DateUtils.reduceTime(TimeUtils.getNowString(), 365));
               endTime = TimeUtils.getNowMills();
               break;
       }
       startTime = startTime / 1000;
       endTime = endTime / 1000;
        CloudApi.historyGetGalleryHistoryList(url, pagerNumber, startTime, endTime)
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
                                    mView.setRefreshLayoutMode(data.getTotal());
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
    public void onDel(List<String> listBean) {
        CloudApi.historyDelete(listBean)
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
                            mView.onDelSuccess();
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
