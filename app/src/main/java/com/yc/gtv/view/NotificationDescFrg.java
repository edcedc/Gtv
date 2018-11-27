package com.yc.gtv.view;

import android.os.Bundle;
import android.view.View;

import com.yc.gtv.R;
import com.yc.gtv.base.BaseFragment;
import com.yc.gtv.base.BasePresenter;
import com.yc.gtv.databinding.FMsgDescBinding;

/**
 * Created by edison on 2018/11/21.
 *  系统通知
 */

public class NotificationDescFrg extends BaseFragment<BasePresenter, FMsgDescBinding>{
    @Override
    public void initPresenter() {

    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_msg_desc;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.msg_desc));
        mB.tvTitle.setText("系统公告标题");
        mB.tvContent.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean");
        mB.tvTime.setText("2018-09-06" + "\n视频APP官方");
    }
}
