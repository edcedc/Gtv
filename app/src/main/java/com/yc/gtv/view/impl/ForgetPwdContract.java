package com.yc.gtv.view.impl;

import com.yc.gtv.base.BasePresenter;
import com.yc.gtv.base.IBaseView;

/**
 * Created by edison on 2018/11/17.
 */

public interface ForgetPwdContract {

    interface View extends IBaseView {

        void onCodeSuccess();
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onSubmit(String phone, String code, String pwd, int type);

        public abstract void onCode(String phone, String code, int type);
    }

}
