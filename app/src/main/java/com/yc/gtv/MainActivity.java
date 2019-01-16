package com.yc.gtv;

import android.os.Bundle;

import com.yc.gtv.base.BaseActivity;
import com.yc.gtv.view.MainFrg;

public class MainActivity extends BaseActivity {

    @Override
    public void initPresenter() {

    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected void initView() {
        setSofia(false);
        if (findFragment(MainFrg.class) == null) {
            loadRootFragment(R.id.fl_container, MainFrg.newInstance());
        }
    }


}
