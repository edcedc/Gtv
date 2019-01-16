package com.yc.gtv.presenter;

import com.blankj.utilcode.util.StringUtils;
import com.lzy.okgo.model.Response;
import com.yc.gtv.R;
import com.yc.gtv.bean.BaseResponseBean;
import com.yc.gtv.bean.DataBean;
import com.yc.gtv.callback.Code;
import com.yc.gtv.controller.CloudApi;
import com.yc.gtv.view.impl.OpinionContract;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by edison on 2018/11/21.
 */

public class OpinionPresenter extends OpinionContract.Presenter{

    @Override
    public void onLebel() {
        CloudApi.list2(CloudApi.mineReasons)
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
                            if (data != null && data.size() != 0){
                                mView.setLabel(data);
                                mView.hideLoading();
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
    public void onSubmit(String id, String s) {
        if (StringUtils.isEmpty(s) || StringUtils.isEmpty(id)){
            showToast(act.getString(R.string.error_));
            return;
        }
        CloudApi.mineOpinion(id, s)
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
                            act.onBackPressed();
                        }
                        showToast(baseResponseBeanResponse.body().message);
                    }

                    @Override
                    public void onError(Throwable e) {
//                        mView.onError(e);
                        showToast("提交成功");
                        act.onBackPressed();
                    }

                    @Override
                    public void onComplete() {
                        mView.hideLoading();
                    }
                });
    }

}
