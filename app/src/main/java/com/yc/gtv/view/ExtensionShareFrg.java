package com.yc.gtv.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.google.zxing.WriterException;
import com.yc.gtv.R;
import com.yc.gtv.base.BaseFragment;
import com.yc.gtv.base.BasePresenter;
import com.yc.gtv.databinding.FExtensionShareBinding;
import com.yc.gtv.utils.ImageUtils;
import com.yc.gtv.utils.ZXingUtils;

/**
 * Created by edison on 2018/11/22.
 *  推广分享
 */

public class ExtensionShareFrg extends BaseFragment<BasePresenter, FExtensionShareBinding> implements View.OnClickListener{

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_extension_share;
    }

    @Override
    protected void initView(View view) {
        final AppCompatActivity mAppCompatActivity = (AppCompatActivity) act;
        mAppCompatActivity.setSupportActionBar(mB.toolbar);
        mAppCompatActivity.getSupportActionBar().setTitle("");
        mB.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                act.onBackPressed();
            }
        });

        mB.btSave.setOnClickListener(this);
        mB.btCopy.setOnClickListener(this);
        mB.tvName.setText("xxxx");

        mB.ivZking.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mB.ivZking.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                try {
                    Bitmap bitmap = ZXingUtils.creatBarcode("https://www.baidu.com/",mB.ivZking.getWidth());
                    mB.ivZking.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_save:
                mB.toolbar.setNavigationIcon(R.mipmap.back);
                mB.btSave.setVisibility(View.GONE);
                mB.btCopy.setVisibility(View.GONE);

                String s = ImageUtils.viewSaveToImage(act, mB.layout, TimeUtils.getNowString());
                LogUtils.e(s);
                showToast("保存成功");

                mB.toolbar.setNavigationIcon(null);
                mB.btSave.setVisibility(View.VISIBLE);
                mB.btCopy.setVisibility(View.VISIBLE);
                break;
            case R.id.bt_copy:

                break;
        }
    }
}
