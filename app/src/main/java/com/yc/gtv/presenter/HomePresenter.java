package com.yc.gtv.presenter;

import android.os.Handler;

import com.yc.gtv.bean.DataBean;
import com.yc.gtv.view.impl.HomeContract;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * 作者：yc on 2018/8/28.
 * 邮箱：501807647@qq.com
 * 版本：v1.0
 */

public class HomePresenter extends HomeContract.Presenter{

    @Override
    public void onRequest() {
        mView.showLoadDataing();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<DataBean> list = new ArrayList<>();
                for (int i = 0;i < 10;i++){
                    list.add(new DataBean());
                }
                mView.hideLoading();
                mView.setData(list);
            }
        }, 1000);
    }

}
