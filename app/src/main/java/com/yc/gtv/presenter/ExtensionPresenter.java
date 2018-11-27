package com.yc.gtv.presenter;

import com.yc.gtv.view.impl.ExtensionContract;

/**
 * Created by edison on 2018/11/22.
 */

public class ExtensionPresenter extends ExtensionContract.Presenter{
    @Override
    public void onRequest() {
        mView.setData();
    }
}
