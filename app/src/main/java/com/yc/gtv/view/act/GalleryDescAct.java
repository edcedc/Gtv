package com.yc.gtv.view.act;

import android.os.Bundle;

import com.yc.gtv.R;
import com.yc.gtv.base.BaseActivity;
import com.yc.gtv.view.GalleryDescFrg;

/**
 * Created by edison on 2018/12/28.
 *  图库详情
 */

public class GalleryDescAct extends BaseActivity {

    private String id;
    private int position;

    @Override
    public void initPresenter() {

    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initParms(Bundle bundle) {
        id = bundle.getString("id");
        position = bundle.getInt("position");
    }

    @Override
    protected void initView() {
        setSofia(true);
        if (findFragment(GalleryDescFrg.class) == null) {
            GalleryDescFrg frg = GalleryDescFrg.newInstance();
            Bundle bundle = new Bundle();
            bundle.putString("id", id);
            bundle.putInt("position", position);
            frg.setArguments(bundle);
            loadRootFragment(R.id.fl_container, frg);
        }
    }
}
