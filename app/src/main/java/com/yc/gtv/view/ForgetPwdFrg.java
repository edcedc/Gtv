package com.yc.gtv.view;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CompoundButton;

import com.yc.gtv.R;
import com.yc.gtv.base.BaseFragment;
import com.yc.gtv.databinding.FForgetPwdBinding;
import com.yc.gtv.presenter.ForgetPwdPresenter;
import com.yc.gtv.view.impl.ForgetPwdContract;

/**
 * Created by edison on 2018/11/17.
 *  忘记密码
 */

public class ForgetPwdFrg extends BaseFragment<ForgetPwdPresenter, FForgetPwdBinding> implements ForgetPwdContract.View, View.OnClickListener{

    public static final int FORGET_PWD = 0;//忘记密码
    public static final int UPDATE_PWD = 1;//重置密码
    private int type;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {
        type = bundle.getInt("type");
    }

    @Override
    protected int bindLayout() {
        return R.layout.f_forget_pwd;
    }

    @Override
    protected void initView(View view) {
        if (type == FORGET_PWD){
            mB.tvLogin.setCompoundDrawablesWithIntrinsicBounds(null,
                    getResources().getDrawable(R.mipmap.reset_password, null), null, null);
            mB.tvLogin.setText(getText(R.string.reset_password));
        }else if (type == UPDATE_PWD){
            mB.tvLogin.setCompoundDrawablesWithIntrinsicBounds(null,
                    getResources().getDrawable(R.mipmap.changepassword, null), null, null);
            mB.tvLogin.setText(getText(R.string.change_password));
        }

        mB.fyClose.setOnClickListener(this);
        mB.btCode.setOnClickListener(this);
        mB.btSubmit.setOnClickListener(this);

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
        setSofia(true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fy_close:
                act.onBackPressed();
                break;
            case R.id.bt_code:
                mPresenter.onCode(mB.etPhone.getText().toString(), mB.etCode.getText().toString(), type);
                break;
            case R.id.bt_submit:
                mPresenter.onSubmit(mB.etPhone.getText().toString(), mB.etCode.getText().toString(), mB.etPwd.getText().toString(), type);
                break;
        }
    }
}
