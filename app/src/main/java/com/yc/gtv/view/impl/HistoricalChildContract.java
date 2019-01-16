package com.yc.gtv.view.impl;

import com.yc.gtv.base.BasePresenter;
import com.yc.gtv.base.IBaseListView;

import java.util.List;

/**
 * Created by edison on 2018/11/21.
 */

public interface HistoricalChildContract {

    interface View extends IBaseListView {

        void onDelSuccess();
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onRequest(int pagerNumber, int mType, int type);

        public abstract void onDel(List<String> listStr);
    }

}
