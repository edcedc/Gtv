package com.yc.gtv.presenter;

import android.os.Handler;

import com.yc.gtv.bean.DataBean;
import com.yc.gtv.view.impl.MyCollectionChildContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edison on 2018/11/21.
 */

public class MyCollectionChildPresenter extends MyCollectionChildContract.Presenter{

    @Override
    public void onRequest() {
        mView.showLoadDataing();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<DataBean> list = new ArrayList<>();
                for (int i =0;i < 10;i++){
                    list.add(new DataBean());
                }
                mView.setData(list);
                mView.hideLoading();
            }
        }, 1000);
    }

}
