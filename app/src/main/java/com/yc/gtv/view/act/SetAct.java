package com.yc.gtv.view.act;

import android.content.Intent;
import android.os.Bundle;

import com.yc.gtv.R;
import com.yc.gtv.base.BaseActivity;
import com.yc.gtv.event.CameraInEvent;
import com.yc.gtv.view.SetFrg;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2018/5/9.
 *  设置
 */

public class SetAct extends BaseActivity {

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
        if (findFragment(SetFrg.class) == null) {
            loadRootFragment(R.id.fl_container, SetFrg.newInstance());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            EventBus.getDefault().post(new CameraInEvent(requestCode, data));
        }
    }

}
