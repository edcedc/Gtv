package com.yc.gtv.view.impl;

import android.support.v7.widget.RecyclerView;

import com.yc.gtv.base.BaseFragment;
import com.yc.gtv.base.BasePresenter;
import com.yc.gtv.base.IBaseView;
import com.yc.gtv.weight.WithScrollGridView;

/**
 * Created by edison on 2018/11/17.
 */

public interface MeContract {

    interface View extends IBaseView {

    }

    abstract class Presenter extends BasePresenter<View> {


        public abstract void onInit(WithScrollGridView gridView, RecyclerView recyclerView, BaseFragment root);
    }


}
