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

        void setData(DataBean data);

        void onCollectionSuccess(boolean isCollection);

        void onHomeGuessLike(List<DataBean> data);

        void setDownloadUrl(String data, int position);
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onVideoDeatil(String id);

        public abstract void onCommonCollect(String id, boolean isCollection);

        public abstract void onHomeGuessLike(String id);

        public abstract void onVideoDownloadUrl(String id, int position);

        public abstract void onVideoDownload(String videoUrl, String name);

        public abstract void onVideoRecordViewTimes();
    }

}
