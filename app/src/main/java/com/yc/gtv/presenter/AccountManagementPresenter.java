package com.yc.gtv.presenter;

import android.text.format.Formatter;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.yc.gtv.base.User;
import com.yc.gtv.bean.BaseResponseBean;
import com.yc.gtv.bean.DataBean;
import com.yc.gtv.callback.Code;
import com.yc.gtv.callback.NewsCallback;
import com.yc.gtv.controller.CloudApi;
import com.yc.gtv.utils.cache.ShareSessionIdCache;
import com.yc.gtv.view.impl.AccountManagementContract;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by edison on 2018/11/20.
 */

public class AccountManagementPresenter extends AccountManagementContract.Presenter {
    @Override
    public void onUpdateHead(final String path) {
        Observable.create(new ObservableOnSubscribe<Progress>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<Progress> e) throws Exception {
                OkGo.<BaseResponseBean<DataBean>>post(CloudApi.userUploadAvatar)
                        .headers("authorization", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                        .params("upload_file", new File(path))
                        .execute(new NewsCallback<BaseResponseBean<DataBean>>() {
                            @Override
                            public void onSuccess(Response<BaseResponseBean<DataBean>> response) {
                                e.onComplete();
                                if (response.body().code == Code.CODE_SUCCESS) {
                                    DataBean data = response.body().data;
                                    if (data != null){
                                        try {
                                            JSONObject userInfo = User.getInstance().getUserInfo();
                                            userInfo.put("headImg", data.getImgUrl());
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        mView.updateHeadSuccess(data.getImgUrl());
                                    }
                                }
                                showToast(response.body().message);
                            }

                            @Override
                            public void uploadProgress(Progress progress) {
                                e.onNext(progress);
                            }

                            @Override
                            public void onError(Response<BaseResponseBean<DataBean>> response) {
                                super.onError(response);
                                e.onError(response.getException());
                            }
                        });

            }
        })
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        LogUtils.e("正在上传中...");
                        mView.showLoading();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())//
                .subscribe(new Observer<Progress>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mView.addDisposable(d);
                    }

                    @Override
                    public void onNext(@NonNull Progress progress) {
                        String downloadLength = Formatter.formatFileSize(act, progress.currentSize);
                        String totalLength = Formatter.formatFileSize(act, progress.totalSize);
                        String speed = Formatter.formatFileSize(act, progress.speed);
                        LogUtils.e(downloadLength + "/" + totalLength, String.format("%s/s", speed));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                        mView.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        mView.hideLoading();
                        LogUtils.e("上传完成");
                    }
                });
    }

    @Override
    public void onSaveName(final String name) {
        CloudApi.userModifyNickname(name)
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
                        if (baseResponseBeanResponse.body().code == Code.CODE_SUCCESS) {
                            try {
                                JSONObject userInfo = User.getInstance().getUserInfo();
                                userInfo.put("nickname", name);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
//                            EventBus.getDefault().post(new LoginInEvent());
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
