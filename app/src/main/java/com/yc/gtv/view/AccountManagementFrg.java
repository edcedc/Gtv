package com.yc.gtv.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.yc.gtv.R;
import com.yc.gtv.base.BaseFragment;
import com.yc.gtv.controller.UIHelper;
import com.yc.gtv.databinding.FAccounManagementBinding;
import com.yc.gtv.event.CameraInEvent;
import com.yc.gtv.presenter.AccountManagementPresenter;
import com.yc.gtv.utils.GlideLoadingUtils;
import com.yc.gtv.view.bottomFrg.CameraBottomFrg;
import com.yc.gtv.view.impl.AccountManagementContract;
import com.yc.gtv.weight.PictureSelectorTool;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edison on 2018/11/20.
 *  账户管理
 */

public class AccountManagementFrg extends BaseFragment<AccountManagementPresenter, FAccounManagementBinding> implements AccountManagementContract.View, View.OnClickListener{

    private CameraBottomFrg cameraBottomFrg;
    private List<LocalMedia> localMediaList = new ArrayList<>();

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_accoun_management;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.account_management));
        mB.btHead.setOnClickListener(this);
        mB.tvPwd.setOnClickListener(this);
        if (cameraBottomFrg == null){
            cameraBottomFrg = new CameraBottomFrg();
        }
        cameraBottomFrg.setCameraListener(new CameraBottomFrg.onCameraListener() {
            @Override
            public void camera() {
                PictureSelectorTool.PictureSelectorImage(act, CameraInEvent.HEAD_CAMEAR, true);
                if (cameraBottomFrg != null && cameraBottomFrg.isShowing())cameraBottomFrg.dismiss();
            }

            @Override
            public void photo() {
                PictureSelectorTool.photo(act, CameraInEvent.HEAD_PHOTO, true);
                if (cameraBottomFrg != null && cameraBottomFrg.isShowing())cameraBottomFrg.dismiss();
            }
        });
        EventBus.getDefault().register(this);
        mB.etName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //判断是否是“完成”键
                if(actionId == EditorInfo.IME_ACTION_SEND){
                    //隐藏软键盘
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                    showToast("保存");
                    return true;
                }
                return false;
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainThreadInEvent(CameraInEvent event) {
        if (cameraBottomFrg != null && cameraBottomFrg.isShowing())cameraBottomFrg.dismiss();
        localMediaList.clear();
        localMediaList.addAll(PictureSelector.obtainMultipleResult((Intent) event.getObject()));
        String path = localMediaList.get(0).getCutPath();
        mPresenter.onUpdateHead(path);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_head:
                cameraBottomFrg.show(getChildFragmentManager(), "dialog");
                break;
            case R.id.tv_pwd:
                UIHelper.startForgetPwdFrg(this, ForgetPwdFrg.UPDATE_PWD);
                break;
        }
    }

    @Override
    public void updateHeadSuccess(String path) {
        GlideLoadingUtils.load(act, path, mB.ivHead);
    }
}
