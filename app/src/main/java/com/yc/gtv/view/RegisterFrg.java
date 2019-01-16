package com.yc.gtv.view;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CompoundButton;

import com.yc.gtv.R;
import com.yc.gtv.base.BaseFragment;
import com.yc.gtv.controller.UIHelper;
import com.yc.gtv.databinding.FRegisterBinding;
import com.yc.gtv.presenter.RegisterPresenter;
import com.yc.gtv.utils.CountDownTimerUtils;
import com.yc.gtv.view.impl.RegisterContract;

/**
 * Created by edison on 2018/11/17.
 *  注册
 */

public class RegisterFrg extends BaseFragment<RegisterPresenter, FRegisterBinding> implements RegisterContract.View, View.OnClickListener{
    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_register;
    }

    @Override
    protected void initView(View view) {
        mB.fyClose.setOnClickListener(this);
        mB.btCode.setOnClickListener(this);
        mB.btSubmit.setOnClickListener(this);
        mB.cbSubmit.setOnClickListener(this);
        mB.tvAgreement.setOnClickListener(this);

        mB.ivPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                int passwordLength = mB.etPwd.getText().length();
                if (b){
                    mB.etPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    mB.etPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                mB.etPwd.setSelection(passwordLength);
            }
        });
        mB.ivPwd1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                int passwordLength = mB.etPwd1.getText().length();
                if (b){
                    mB.etPwd1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    mB.etPwd1.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                mB.etPwd1.setSelection(passwordLength);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fy_close:
                act.onBackPressed();
                break;
            case R.id.bt_code:
                mPresenter.onCode(mB.etPhone.getText().toString());
                break;
            case R.id.bt_submit:
                mPresenter.onSubmit(mB.etPhone.getText().toString(), mB.etCode.getText().toString(), mB.etPwd.getText().toString()
                , mB.etPwd1.getText().toString(), mB.etInvitationCode.getText().toString(), mB.cbSubmit.isChecked());
                break;
            case R.id.tv_agreement:
                UIHelper.startHtmlAct(2);
                break;
        }
    }

    @Override
    public void onCodeSuccess() {
        new CountDownTimerUtils(act, 60000, 1000, mB.btCode).start();
    }
}
