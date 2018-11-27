package com.yc.gtv.presenter;

import com.yc.gtv.view.impl.AccountManagementContract;

/**
 * Created by edison on 2018/11/20.
 */

public class AccountManagementPresenter extends AccountManagementContract.Presenter{
    @Override
    public void onUpdateHead(String path) {
        mView.updateHeadSuccess(path);
    }
}
