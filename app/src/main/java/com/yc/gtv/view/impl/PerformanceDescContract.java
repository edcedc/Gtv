package com.yc.gtv.view.impl;

import com.yc.gtv.base.BasePresenter;
import com.yc.gtv.base.IBaseView;
import com.yc.gtv.bean.DataBean;

/**
 * Created by edison on 2018/11/22.
 */

public interface PerformanceDescContract {

    interface View extends IBaseView {

        void setData(DataBean data);
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onRequest(int pagerNumber);
    }

}
