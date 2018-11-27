package com.yc.gtv.presenter;

import com.yc.gtv.bean.DataBean;
import com.yc.gtv.view.impl.HomeChildContract;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：yc on 2018/8/28.
 * 邮箱：501807647@qq.com
 * 版本：v1.0
 */

public class HomeChildPresenter extends HomeChildContract.Presenter{

    @Override
    public void onRequest(int pagerNumber) {
        List<DataBean> list = new ArrayList<>();
        for (int i = 0;i < 20;i++){
            list.add(new DataBean());
        }
        mView.setData(list);
    }

    @Override
    public void onBanner() {
        List<DataBean> list = new ArrayList<>();
        for (int i = 0;i < 4;i++){
            list.add(new DataBean());
        }
        mView.setBannerData(list);
    }

}
