package com.yc.gtv.view.impl;

import com.yc.gtv.base.BasePresenter;
import com.yc.gtv.base.IBaseListView;
import com.yc.gtv.bean.DataBean;

/**
 * Created by edison on 2018/11/19.
 */

public interface ChannelNameContract {

    interface View extends IBaseListView {

        void onChannelGetChannelTagDetailSuccess(DataBean data);
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onRequest(String tagId, int pagerNumber);

        public abstract void onChannelGetChannelTagDetail(String id);
    }

}
