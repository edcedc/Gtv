package com.yc.gtv.view.impl;

import com.yc.gtv.base.BasePresenter;
import com.yc.gtv.base.IBaseView;
import com.yc.gtv.bean.DataBean;

import java.util.List;

/**
 * Created by edison on 2018/11/22.
 */

public interface PurchaseMemberContract {

    interface View extends IBaseView {

        void setData(List<DataBean> data);

        void onPaySucees(int finalPayType, String data);
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onRequest();

        public abstract void onPay(String id, int payType);
    }

}
