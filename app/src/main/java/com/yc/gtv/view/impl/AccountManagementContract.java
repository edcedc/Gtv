package com.yc.gtv.view.impl;

import com.yc.gtv.base.BasePresenter;
import com.yc.gtv.base.IBaseView;

/**
 * Created by edison on 2018/11/20.
 */

public interface AccountManagementContract {

    interface View extends IBaseView {

        void updateHeadSuccess(String path);
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onUpdateHead(String path);
    }

}
