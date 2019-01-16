package com.yc.gtv.view.impl;

import com.yc.gtv.base.BasePresenter;
import com.yc.gtv.base.IBaseListView;

/**
 * Created by edison on 2018/11/30.
 */

public interface NotificationContract {

    interface View extends IBaseListView {

    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onRequest(int pagerNumber);

    }

}
