package com.yc.gtv.presenter;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.Utils;
import com.yc.gtv.R;
import com.yc.gtv.base.User;
import com.yc.gtv.callback.Code;
import com.yc.gtv.controller.CloudApi;
import com.yc.gtv.controller.UIHelper;
import com.yc.gtv.event.LoginInEvent;
import com.yc.gtv.utils.cache.ShareSessionIdCache;
import com.yc.gtv.utils.cache.SharedAccount;
import com.yc.gtv.view.impl.LoginContract;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by edison on 2018/11/17.
 */

public class LoginPresenter extends LoginContract.Presenter{
    @Override
    public void onLogin(final String phone, final String pwd) {
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(pwd)){
            showToast(act.getString(R.string.error_phone1));
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
        CloudApi.authLogin(phone, pwd)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mView.showLoading();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mView.addDisposable(d);
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        if (jsonObject.optInt("code") == Code.CODE_SUCCESS){
                            JSONObject data = jsonObject.optJSONObject("data");
                            User.getInstance().setUserObj(data);
                            User.getInstance().setLogin(true);
                            SharedAccount.getInstance(Utils.getApp()).save(phone, pwd);
                            ShareSessionIdCache.getInstance(act).save(data.optString("userId") + "_" + data.optString("token"));
                            EventBus.getDefault().post(new LoginInEvent());
                            UIHelper.startMainAct();
                            act.onBackPressed();
                        }else {
                            showToast(jsonObject.optString("message"));
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
