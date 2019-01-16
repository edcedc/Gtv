package com.yc.gtv.view.impl;

import com.yc.gtv.base.BasePresenter;
import com.yc.gtv.base.IBaseView;
import com.yc.gtv.bean.DataBean;

/**
 * Created by edison on 2018/11/22.
 */

public interface ExtensionContract {

    interface View extends IBaseView {

        void setAgreement(String remark);

        void onUserAnonymousViewTimesAtToday(DataBean data);

        void setData(DataBean data);
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onRequest();

        public abstract void onQueryAPPAgreement();

        public abstract void onUserGetViewTimesAtToday();
    }


}
