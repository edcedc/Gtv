package com.yc.gtv.view.impl;

import com.yc.gtv.base.BasePresenter;
import com.yc.gtv.base.IBaseView;
import com.yc.gtv.bean.DataBean;

import java.util.List;

/**
 * Created by edison on 2018/11/21.
 */

public interface OpinionContract {

    interface View extends IBaseView {

        void setLabel(List<DataBean> listStr);
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onLebel();

        public abstract void onSubmit(String id, String s);
    }

}
