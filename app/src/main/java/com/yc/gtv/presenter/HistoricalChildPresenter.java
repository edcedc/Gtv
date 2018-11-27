package com.yc.gtv.presenter;

import android.os.Handler;

import com.yc.gtv.bean.DataBean;
import com.yc.gtv.view.impl.HistoricalChildContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edison on 2018/11/21.
 */

public class HistoricalChildPresenter extends HistoricalChildContract.Presenter{

    @Override
    public void onRequest(int pagerNumber, final int type) {
        mView.showLoadDataing();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<DataBean> list = new ArrayList<>();
                if (type <= 1){
                    for (int i =0;i < 10;i++){
                        list.add(new DataBean());
                    }
                    mView.setData(list);
                    mView.hideLoading();
                }else {
                    mView.showLoadEmpty();
                }
            }
        }, 1000);
    }

}
