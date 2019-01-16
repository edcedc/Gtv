package com.yc.gtv.view;

import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;

import com.blankj.utilcode.util.TimeUtils;
import com.google.zxing.WriterException;
import com.lzy.okgo.model.Response;
import com.yc.gtv.R;
import com.yc.gtv.base.BaseFragment;
import com.yc.gtv.base.BasePresenter;
import com.yc.gtv.bean.BaseResponseBean;
import com.yc.gtv.bean.DataBean;
import com.yc.gtv.callback.Code;
import com.yc.gtv.controller.CloudApi;
import com.yc.gtv.databinding.FExtensionShareBinding;
import com.yc.gtv.utils.ImageUtils;
import com.yc.gtv.utils.ZXingUtils;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by edison on 2018/11/22.
 *  推广分享
 */

public class ExtensionShareFrg extends BaseFragment<BasePresenter, FExtensionShareBinding> implements View.OnClickListener{

    private String downloadUrl;

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




        CloudApi.userGetPromotionInfo()
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<BaseResponseBean<DataBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Response<BaseResponseBean<DataBean>> baseResponseBeanResponse) {
                        if (baseResponseBeanResponse.body().code == Code.CODE_SUCCESS){
                            final DataBean data = baseResponseBeanResponse.body().data;
                            if (data != null){
                                mB.tvName.setText(data.getInvitCode());
                                downloadUrl = data.getDownloadUrl();
                                mB.ivZking.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                                    @Override
                                    public void onGlobalLayout() {
                                        mB.ivZking.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                                        try {
                                            Bitmap bitmap = ZXingUtils.creatBarcode(downloadUrl, mB.ivZking.getWidth());
                                            mB.ivZking.setImageBitmap(bitmap);
                                        } catch (WriterException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_save:
                mB.toolbar.setNavigationIcon(null);
                mB.btSave.setVisibility(View.GONE);
                mB.btCopy.setVisibility(View.GONE);

                showLoading();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ImageUtils.viewSaveToImage(act, mB.layout, TimeUtils.getNowString());
                        mB.toolbar.setNavigationIcon(R.mipmap.back);
                        mB.btSave.setVisibility(View.VISIBLE);
                        mB.btCopy.setVisibility(View.VISIBLE);
                        hideLoading();
                    }
                }, 500);
                break;
            case R.id.bt_copy:
                // 从API11开始android推荐使用android.content.ClipboardManager
                // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
                ClipboardManager cm = (ClipboardManager) act.getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(downloadUrl);
                showToast("复制成功");
                break;
        }
    }
}
