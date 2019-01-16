package com.yc.gtv.presenter;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.Utils;
import com.lzy.okgo.model.Response;
import com.yc.gtv.R;
import com.yc.gtv.bean.BaseResponseBean;
import com.yc.gtv.callback.Code;
import com.yc.gtv.controller.CloudApi;
import com.yc.gtv.utils.cache.SharedAccount;
import com.yc.gtv.view.impl.ForgetPwdContract;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by edison on 2018/11/17.
 */

public class ForgetPwdPresenter extends ForgetPwdContract.Presenter{
    @Override
    public void onSubmit(final String phone, String code, final String pwd, int type) {
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(code) || StringUtils.isEmpty(pwd)){
            showToast(act.getString(R.string.error_));
            return;
        }
        if (!RegexUtils.isMobileExact(phone)) {
            showToast(act.getString(R.string.error_phone));
            return;
        }
        if (pwd.length() < 6 || pwd.length() > 16){
            showToast(act.getString(R.string.error_pwd_length));
            return;
        }
        CloudApi.authResetPwd(phone, pwd, code)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mView.showLoading();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<BaseResponseBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mView.addDisposable(d);
                    }

                    @Override
                    public void onNext(Response<BaseResponseBean> baseResponseBeanResponse) {
                        if (baseResponseBeanResponse.body().code == Code.CODE_SUCCESS){
                            SharedAccount.getInstance(Utils.getApp()).save(phone, pwd);
                            act.onBackPressed();
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

    @Override
    public void onCode(String phone, String code, int type) {
        if (StringUtils.isEmpty(phone)){
            showToast(act.getString(R.string.please_phone));
            return;
        }
        if (!RegexUtils.isMobileExact(phone)) {
            showToast(act.getString(R.string.error_phone));
            return;
        }
        CloudApi.authGetSms(phone)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mView.showLoading();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<BaseResponseBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mView.addDisposable(d);
                    }

                    @Override
                    public void onNext(Response<BaseResponseBean> baseResponseBeanResponse) {
                        showToast(baseResponseBeanResponse.body().message);
                        mView.onCodeSuccess();
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
