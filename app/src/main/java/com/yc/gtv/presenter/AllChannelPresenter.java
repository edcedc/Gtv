package com.yc.gtv.presenter;

import com.yc.gtv.bean.DataBean;
import com.yc.gtv.view.impl.AllChannelContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edison on 2018/11/19.
 */

public class AllChannelPresenter extends AllChannelContract.Presenter{
    @Override
    public void onLeftRequest() {
        List<DataBean> list = new ArrayList<>();
        for (int i = 0;i < 4;i++){
            list.add(new DataBean());
        }
        mView.setData(list);
    }
}
