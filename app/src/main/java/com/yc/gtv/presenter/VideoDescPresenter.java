package com.yc.gtv.presenter;

import android.os.Handler;

import com.yc.gtv.bean.DataBean;
import com.yc.gtv.view.impl.VideoDescContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edison on 2018/11/18.
 */

public class VideoDescPresenter extends VideoDescContract.Presenter{
    @Override
    public void onLabel() {
        mView.showLoadDataing();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<DataBean> list = new ArrayList<>();
                for (int i =0;i < 10;i++){
                    list.add(new DataBean());
                }
                mView.setLabel(list);
                mView.hideLoading();
            }
        }, 1000);

    }
}
