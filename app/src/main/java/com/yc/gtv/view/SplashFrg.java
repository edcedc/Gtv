package com.yc.gtv.view;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.StringUtils;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.Setting;
import com.yc.gtv.R;
import com.yc.gtv.base.BaseFragment;
import com.yc.gtv.base.BasePresenter;
import com.yc.gtv.base.User;
import com.yc.gtv.callback.Code;
import com.yc.gtv.controller.CloudApi;
import com.yc.gtv.controller.UIHelper;
import com.yc.gtv.databinding.FSplashBinding;
import com.yc.gtv.event.LoginInEvent;
import com.yc.gtv.utils.cache.ShareSessionIdCache;
import com.yc.gtv.utils.cache.SharedAccount;
import com.yc.gtv.weight.RuntimeRationale;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * 作者：yc on 2018/6/15.
 * 邮箱：501807647@qq.com
 * 版本：v1.0
 */

public class SplashFrg extends BaseFragment<BasePresenter, FSplashBinding> implements BGABanner.Delegate, BGABanner.Adapter<ImageView, Integer> {

    public static SplashFrg newInstance() {
        Bundle args = new Bundle();
        SplashFrg fragment = new SplashFrg();
        fragment.setArguments(args);
        return fragment;
    }

    private List<Integer> listImage = new ArrayList<>();
    private List<String> tips = new ArrayList<String>();

    private final int mHandle_splash = 0;
    private final int mHandle_permission = 1;

    private Activity act;

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_splash;
    }

    @Override
    protected void initView(View view) {
        act = getActivity();
        setSofia(true);
       /* mB.banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == listImage.size() - 1) {
                    handler.sendMessageDelayed(handler.obtainMessage(mHandle_splash), 1500);
                } else {
                    handler.removeMessages(mHandle_splash);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });*/
        handler.sendEmptyMessageDelayed(mHandle_permission, 1000);
    }

    @Override
    public void fillBannerItem(BGABanner banner, ImageView itemView, Integer model, int position) {
        itemView.setBackgroundResource(model);
    }

    @Override
    public void onBannerItemClick(BGABanner banner, View itemView, Object model, int position) {

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case mHandle_splash:
                    startNext();
                    break;
                case mHandle_permission:
                    setHasPermission();
                    break;
            }
        }
    };


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }

    /**
     * 设置权限
     */
    private void setHasPermission() {
        AndPermission.with(SplashFrg.this)
                .runtime()
                .permission(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,//写入外部存储, 允许程序写入外部存储，如SD卡
                        Manifest.permission.CAMERA//拍照权限, 允许访问摄像头进行拍照
                )
                .rationale(new RuntimeRationale())
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        setPermissionOk();
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(@NonNull List<String> permissions) {
                        if (AndPermission.hasAlwaysDeniedPermission(SplashFrg.this, permissions)) {
                            showSettingDialog(act, permissions);
                        } else {
                            setPermissionCancel();
                        }
                    }
                })
                .start();
    }


    /**
     * Display setting dialog.
     */
    public void showSettingDialog(Context context, final List<String> permissions) {
        List<String> permissionNames = Permission.transformText(context, permissions);
        String message = getString(R.string.message_permission_always_failed, TextUtils.join("\n", permissionNames));

        new AlertDialog.Builder(context)
                .setCancelable(false)
                .setTitle(R.string.title_dialog)
                .setMessage(message)
                .setPositiveButton(R.string.setting, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setPermission();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setPermissionCancel();
                    }
                })
                .show();
    }

    /**
     * Set permissions.
     */
    private void setPermission() {
        AndPermission.with(this)
                .runtime()
                .setting()
                .onComeback(new Setting.Action() {
                    @Override
                    public void onAction() {
                        setHasPermission();
//                        ToastUtils.showShort("用户从设置页面返回。");
                    }
                })
                .start();
    }


    /**
     * 权限有任何一个失败都会走的方法
     */
    private void setPermissionCancel() {
        act.finish();
    }

    /**
     * 权限都成功
     */
    private void setPermissionOk() {
        SharedAccount account = SharedAccount.getInstance(act);
        String mobile = account.getMobile();
        String pwd = account.getPwd();
        if (!StringUtils.isEmpty(mobile) && !StringUtils.isEmpty(pwd)){
            CloudApi.authLogin(mobile, pwd)
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(Disposable disposable) throws Exception {
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JSONObject>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onNext(JSONObject jsonObject) {
                            if (jsonObject.optInt("code") == Code.CODE_SUCCESS){
                                JSONObject data = jsonObject.optJSONObject("data");
                                User.getInstance().setUserObj(data);
                                User.getInstance().setLogin(true);
                                EventBus.getDefault().post(new LoginInEvent());
                                ShareSessionIdCache.getInstance(act).save(data.optString("userId") + "_" + data.optString("token"));
                            }else {
                                ShareSessionIdCache.getInstance(act).remove();
                            }
                            startNext();
                        }

                        @Override
                        public void onError(Throwable e) {
                            ShareSessionIdCache.getInstance(act).remove();
                            startNext();
                        }

                        @Override
                        public void onComplete() {
                        }
                    });
        }else {
            startNext();
        }
        String sessionId = ShareSessionIdCache.getInstance(act).getSessionId();

       /* if (ShareIsLoginCache.getInstance(act).getIsLogin()) {
//            ShareSessionIdCache.getInstance(act).save("08f0d4bf806e4a6883d8f0ab935367dd");

        } else {
            startNext();
//            mB.banner.setData(listImage, tips);
//            mB.banner.setAdapter(SplashFrg.this);
        }*/
    }

    private void startNext() {
        UIHelper.startMainAct();
        ActivityUtils.finishAllActivities();
    }
}
