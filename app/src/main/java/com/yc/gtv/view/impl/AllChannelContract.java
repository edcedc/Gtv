package com.yc.gtv.view.impl;

import com.yc.gtv.base.BasePresenter;
import com.yc.gtv.base.IBaseView;
import com.yc.gtv.bean.DataBean;

import java.util.List;

/**
 * Created by edison on 2018/11/19.
 */

public interface AllChannelContract {

    interface View extends IBaseView {

        void setData(List<DataBean> list);
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onLeftRequest();
    }

}
