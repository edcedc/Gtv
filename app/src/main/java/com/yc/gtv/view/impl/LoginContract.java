package com.yc.gtv.view.impl;

import com.yc.gtv.base.BasePresenter;
import com.yc.gtv.base.IBaseView;

/**
 * Created by edison on 2018/11/17.
 */

public interface LoginContract {

    interface View extends IBaseView {

    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onLogin(String phone, String pwd);
    }

}
