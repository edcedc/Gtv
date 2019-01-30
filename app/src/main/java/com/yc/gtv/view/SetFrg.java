package com.yc.gtv.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.CleanUtils;
import com.yc.gtv.R;
import com.yc.gtv.adapter.MeAdapter;
import com.yc.gtv.base.BaseFragment;
import com.yc.gtv.base.BasePresenter;
import com.yc.gtv.base.User;
import com.yc.gtv.bean.DataBean;
import com.yc.gtv.bean.SaveSearchListBean;
import com.yc.gtv.callback.Code;
import com.yc.gtv.controller.CloudApi;
import com.yc.gtv.controller.UIHelper;
import com.yc.gtv.databinding.BRecyclerBinding;
import com.yc.gtv.utils.BitmapFixedWidthTransform;
import com.yc.gtv.utils.Constants;
import com.yc.gtv.utils.PopupWindowTool;
import com.yc.gtv.utils.cache.ShareSessionIdCache;
import com.yc.gtv.utils.cache.SharedAccount;

import org.json.JSONObject;
import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by edison on 2018/11/20.
 *  设置
 */

public class SetFrg extends BaseFragment<BasePresenter, BRecyclerBinding>{

    public static SetFrg newInstance() {
        Bundle args = new Bundle();
        SetFrg fragment = new SetFrg();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.b_recycler;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.set));
        String[] str = {act.getString(R.string.account_management), act.getString(R.string.clean_caching), act.getString(R.string.updated_version), act.getString(R.string.user_protocol), getString(R.string.sign_out)};
        List<DataBean> listBean = new ArrayList<>();
        for (int i = 0;i < str.length;i++){
            DataBean bean = new DataBean();
            bean.setName(str[i]);
            listBean.add(bean);
        }
        hideLoading();
        mB.refreshLayout.setPureScrollModeOn();
        setRecyclerViewType(mB.recyclerView);
        MeAdapter meAdapter = new MeAdapter(act, listBean, true);
        mB.recyclerView.setAdapter(meAdapter);
        meAdapter.setClick(new MeAdapter.OnClick() {
            @Override
            public void onClick(int position, String text) {
                switch (position){
                    case 0:
                        UIHelper.startAccountManagementFrg(SetFrg.this);
                        break;
                    case 1:
                        PopupWindowTool.showDialog(act, PopupWindowTool.clear_cache, new PopupWindowTool.DialogListener() {
                            @Override
                            public void onClick() {
                                showLoading();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        LitePal.deleteAll(SaveSearchListBean.class);
                                        CleanUtils.cleanCustomDir(Constants.mainPath);
                                        hideLoading();
                                        showToast("清除缓存成功");
                                    }
                                }, 1000);
                            }
                        });
                        break;
                    case 2:
                        initVersion();
                        break;
                    case 3:
                        UIHelper.startHtmlAct(2);
                        break;
                    case 4:
                        PopupWindowTool.showDialog(act, PopupWindowTool.sign_out, new PopupWindowTool.DialogListener() {
                            @Override
                            public void onClick() {
                                new BitmapFixedWidthTransform();
                                ShareSessionIdCache.getInstance(act).remove();
                                SharedAccount.getInstance(act).remove();
                                User.getInstance().setUserInfo(null);
                                User.getInstance().setUserObj(null);
                                User.getInstance().setLogin(false);
                                UIHelper.startLoginAct();
                                ActivityUtils.finishAllActivities();
                                System.exit(0);
                            }
                        });
                        break;
                }
            }
        });
    }

    private void initVersion() {


        CloudApi.versionVersionClient()
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        if (jsonObject.optInt("code") == Code.CODE_SUCCESS){
                            Uri uri = Uri.parse(jsonObject.optString("data"));
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        hideLoading();

                    }
                });
    }
}
