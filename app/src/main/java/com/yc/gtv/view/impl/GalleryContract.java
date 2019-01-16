package com.yc.gtv.view.impl;

import com.yc.gtv.base.BasePresenter;
import com.yc.gtv.base.IBaseListView;

/**
 * Created by edison on 2018/11/29.
 */

public interface GalleryContract {

    interface View extends IBaseListView {

        void onCollectionSuccess(int position);
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onRequest( int pagerNumber);

        public abstract void onCollection(int position, int type, String id);
    }

}
