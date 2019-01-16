package com.yc.gtv.view.impl;

import com.yc.gtv.base.BasePresenter;
import com.yc.gtv.base.IBaseView;

/**
 * Created by edison on 2018/11/21.
 */

public interface MyCacheChildContract {

    interface View extends IBaseView {

        void setData(Object object);
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onRequest(int type);
    }

}
