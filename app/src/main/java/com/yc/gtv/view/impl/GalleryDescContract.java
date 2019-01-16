package com.yc.gtv.view.impl;

import com.yc.gtv.base.BasePresenter;
import com.yc.gtv.base.IBaseListView;

/**
 * Created by edison on 2018/11/20.
 */

public interface GalleryDescContract {

    interface View extends IBaseListView {

        void onCollectionSuccess(int type);
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onRequest(String id);

        public abstract void onCollection(String id, int type);
    }

}
