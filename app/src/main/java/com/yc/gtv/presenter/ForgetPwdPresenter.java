package com.yc.gtv.presenter;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.yc.gtv.R;
import com.yc.gtv.view.impl.ForgetPwdContract;

/**
 * Created by edison on 2018/11/17.
 */

public class ForgetPwdPresenter extends ForgetPwdContract.Presenter{
    @Override
    public void onSubmit(String phone, String code, String pwd, int type) {
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
    }
}
