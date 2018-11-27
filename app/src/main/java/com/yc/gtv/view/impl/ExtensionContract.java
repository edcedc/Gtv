package com.yc.gtv.view.impl;

import com.yc.gtv.base.BasePresenter;
import com.yc.gtv.base.IBaseView;

/**
 * Created by edison on 2018/11/22.
 */

public interface ExtensionContract {

    interface View extends IBaseView {

        void setData();
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onRequest();
    }


}
