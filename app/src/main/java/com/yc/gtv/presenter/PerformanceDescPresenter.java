package com.yc.gtv.presenter;

import android.os.Handler;

import com.yc.gtv.bean.DataBean;
import com.yc.gtv.view.impl.PerformanceDescContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edison on 2018/11/22.
 */

public class PerformanceDescPresenter extends PerformanceDescContract.Presenter{
    @Override
    public void onRequest(int pagerNumber) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<DataBean> list = new ArrayList<>();
                for (int i =0;i < 20;i++){
                    DataBean bean = new DataBean();
                    List<DataBean> list1 = new ArrayList<>();
                    list1.add(new DataBean());
                    list1.add(new DataBean());
                    list1.add(new DataBean());
                    list1.add(new DataBean());
                    bean.setProd(list1);
                    list.add(bean);
                }
                mView.setData(list);
                mView.hideLoading();
            }
        }, 1000);
    }
}
