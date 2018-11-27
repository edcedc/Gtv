package com.yc.gtv.presenter;

import com.yc.gtv.bean.DataBean;
import com.yc.gtv.view.impl.AllChannelRightContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edison on 2018/11/19.
 */

public class AllChannelRightPresenter extends AllChannelRightContract.Presenter{

    @Override
    public void onRequest() {
        List<DataBean> list = new ArrayList<>();
        for (int i = 0;i < 10;i++){
            list.add(new DataBean());
        }
        mView.setData(list);
    }
}
