package com.yc.gtv.view.impl;

import com.yc.gtv.base.BasePresenter;
import com.yc.gtv.base.IBaseView;
import com.yc.gtv.bean.DataBean;

import java.util.List;

/**
 * Created by edison on 2018/11/18.
 */

public interface VideoDescContract {

    interface View extends IBaseView {

        void setLabel(List<DataBean> list);
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onLabel();
    }

}
