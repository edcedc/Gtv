package com.yc.gtv.view.act;

import android.os.Bundle;

import com.yc.gtv.R;
import com.yc.gtv.base.BaseActivity;
import com.yc.gtv.view.LoginFrg;

/**
 * Created by Administrator on 2018/5/9.
 *  启动页
 */

public class LoginAct extends BaseActivity {

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
        setSofia(true);
        if (findFragment(LoginFrg.class) == null) {
            loadRootFragment(R.id.fl_container, LoginFrg.newInstance());
        }
    }

}
