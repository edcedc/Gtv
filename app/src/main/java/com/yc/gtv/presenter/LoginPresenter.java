package com.yc.gtv.presenter;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.yc.gtv.R;
import com.yc.gtv.view.impl.LoginContract;

/**
 * Created by edison on 2018/11/17.
 */

public class LoginPresenter extends LoginContract.Presenter{
    @Override
    public void onLogin(String phone, String pwd) {
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


        /*CloudApi.userLogin(phone, pwd)
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
                            if (data != null && data.optInt("code") == Code.CODE_SUCCESS){
                                listener.onLoginSuccess();
                                JSONObject obj = data.optJSONObject("data");
                                User.getInstance().setLogin(true);
                                User.getInstance().setUserObj(obj);
                                SharedAccount.getInstance(Utils.getApp()).save(phone, pwd);
                                SharedAccount.getInstance(Utils.getApp()).saveSessionId(obj.optString("sessionId"));
                                ToastUtils.showShort(data.optString("desc"));
                            }else {
                                ToastUtils.showShort(jsonObject.optString("desc"));
                            }
                        }else {
                            ToastUtils.showShort(jsonObject.optString("desc"));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                        mView.hideLoading();
                    }
                });*/
    }
}
