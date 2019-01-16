package com.yc.gtv.view.impl;

import com.yc.gtv.base.BasePresenter;
import com.yc.gtv.base.IBaseListView;
import com.yc.gtv.bean.DataBean;

import java.util.List;

/**
 * Created by edison on 2018/11/16.
 */

public interface HomeChildContract {

    interface View extends IBaseListView {

        void setBannerData(List<DataBean> list);

    }

    abstract class Presenter extends BasePresenter<HomeChildContract.View> {

        public abstract void onRequest(int pagerNumber, String id);
        public abstract void onBanner();
    }

}
