package com.yc.gtv.view.impl;

import com.yc.gtv.base.BasePresenter;
import com.yc.gtv.base.IBaseView;

/**
 * Created by edison on 2018/11/17.
 */

public interface RegisterContract {
    interface View extends IBaseView {

    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onSubmit(String phone, String code, String pwd, String pwd1, String num, boolean checked);

        public abstract void onCode(String phone);
    }

}
