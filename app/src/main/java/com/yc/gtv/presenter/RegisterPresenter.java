package com.yc.gtv.presenter;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.yc.gtv.R;
import com.yc.gtv.view.impl.RegisterContract;

/**
 * Created by edison on 2018/11/17.
 */

public class RegisterPresenter extends RegisterContract.Presenter{
    @Override
    public void onSubmit(String phone, String code, String pwd, String pwd1, String num, boolean checked) {
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(code) || StringUtils.isEmpty(pwd)){
            showToast(act.getString(R.string.error_));
            return;
        }
        if (!RegexUtils.isMobileExact(phone)) {
            showToast(act.getString(R.string.error_phone));
            return;
        }
        if (!pwd.equals(pwd1)){
            showToast(act.getString(R.string.please_pwd2));
            return;
        }
        if (pwd.length() < 6 || pwd.length() > 16){
            showToast(act.getString(R.string.error_pwd_length));
            return;
        }
        if (!checked){
            showToast(act.getString(R.string.error_1));
            return;
        }
    }

    @Override
    public void onCode(String phone) {
        if (StringUtils.isEmpty(phone)){
            showToast(act.getString(R.string.please_phone));
            return;
        }
        if (!RegexUtils.isMobileExact(phone)) {
            showToast(act.getString(R.string.error_phone));
            return;
        }
//        new CountDownTimerUtils(act, 60000, 1000, mB.tvCode).start();

    }

}
