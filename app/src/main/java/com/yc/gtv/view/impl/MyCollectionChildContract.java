package com.yc.gtv.view.impl;

import com.yc.gtv.base.BasePresenter;
import com.yc.gtv.base.IBaseListView;

import java.util.List;

/**
 * Created by edison on 2018/11/21.
 */

public interface MyCollectionChildContract {

    interface View extends IBaseListView {

        void onDelSuccess();
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onRequest(int type, int pagerNumber);

        public abstract void onDel(List<String> listBean);
    }

}
