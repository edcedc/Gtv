package com.yc.gtv.view;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CompoundButton;

import com.blankj.utilcode.util.StringUtils;
import com.yc.gtv.R;
import com.yc.gtv.base.BaseFragment;
import com.yc.gtv.controller.UIHelper;
import com.yc.gtv.databinding.FLoginBinding;
import com.yc.gtv.presenter.LoginPresenter;
import com.yc.gtv.utils.cache.SharedAccount;
import com.yc.gtv.view.impl.LoginContract;

/**
 * Created by edison on 2018/11/17.
 *  登录
 */

public class LoginFrg extends BaseFragment<LoginPresenter, FLoginBinding> implements LoginContract.View, View.OnClickListener{

    public static LoginFrg newInstance() {
        Bundle args = new Bundle();
        LoginFrg fragment = new LoginFrg();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_login;
    }

    @Override
    protected void initView(View view) {
        mB.fyClose.setOnClickListener(this);
        mB.btSubmit.setOnClickListener(this);
        mB.btForgetPwd.setOnClickListener(this);
        mB.btRegister.setOnClickListener(this);

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
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        SharedAccount account = SharedAccount.getInstance(act);
        String mobile = account.getMobile();
        String pwd = account.getPwd();
        if (!StringUtils.isEmpty(mobile) && !StringUtils.isEmpty(pwd)){
            mB.etPhone.setText(mobile);
            mB.etPwd.setText(pwd);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fy_close:
                act.finish();
                break;
            case R.id.bt_submit:
                mPresenter.onLogin(mB.etPhone.getText().toString(), mB.etPwd.getText().toString());
                break;
            case R.id.bt_forget_pwd:
                UIHelper.startForgetPwdFrg(this, ForgetPwdFrg.FORGET_PWD);
                break;
            case R.id.bt_register:
                UIHelper.startRegisterFrg(this);
                break;
        }
    }
}
