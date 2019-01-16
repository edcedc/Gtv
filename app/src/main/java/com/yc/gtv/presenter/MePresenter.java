package com.yc.gtv.presenter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;

import com.lzy.okgo.model.Response;
import com.yc.gtv.R;
import com.yc.gtv.adapter.LabelAdapter;
import com.yc.gtv.adapter.MeAdapter;
import com.yc.gtv.base.BaseActivity;
import com.yc.gtv.base.BaseFragment;
import com.yc.gtv.base.User;
import com.yc.gtv.bean.BaseResponseBean;
import com.yc.gtv.bean.DataBean;
import com.yc.gtv.callback.Code;
import com.yc.gtv.controller.CloudApi;
import com.yc.gtv.controller.UIHelper;
import com.yc.gtv.event.MemberSuccessInEvent;
import com.yc.gtv.view.impl.MeContract;
import com.yc.gtv.weight.WithScrollGridView;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by edison on 2018/11/17.
 */

public class MePresenter extends MeContract.Presenter {

    @Override
    public void onInit(WithScrollGridView gridView, RecyclerView recyclerView, final BaseFragment root) {
        String[] laberStr = {act.getString(R.string.member_center), act.getString(R.string.promote), act.getString(R.string.system_notification), act.getString(R.string.feedback_feedback)};
        int[] laberImg = {R.mipmap.my_con_ic01, R.mipmap.my_con_ic02, R.mipmap.my_con_ic03, R.mipmap.my_con_ic04};
        List<DataBean> listStr = new ArrayList<>();
        for (int i = 0; i < laberStr.length; i++) {
            DataBean bean = new DataBean();
            bean.setName(laberStr[i]);
            bean.setImg(laberImg[i]);
            listStr.add(bean);
        }
        LabelAdapter labelAdapter = new LabelAdapter(act, listStr);
        gridView.setAdapter(labelAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (!((BaseActivity)act).isLogin())return;
                switch (i) {
                    case 0:
                        UIHelper.startMemberCenterFrg(root);
                        break;
                    case 1:
                        UIHelper.startExtensionFrg(root);
                        break;
                    case 2:
                        UIHelper.startNotificationFrg(root);
                        break;
                    case 3:
                        UIHelper.startOpinionFrg(root);
                        break;
                }
            }
        });

        String[] str = {act.getString(R.string.historical_records), act.getString(R.string.my_caching), act.getString(R.string.my_collection)};
        int[] img = {R.mipmap.my_con_ic05, R.mipmap.my_con_ic_hc, R.mipmap.my_con_ic07};
        List<DataBean> listBean = new ArrayList<>();
        for (int i = 0; i < str.length; i++) {
            DataBean bean = new DataBean();
            bean.setName(str[i]);
            bean.setImg(img[i]);
            listBean.add(bean);
        }
        MeAdapter meAdapter = new MeAdapter(act, listBean);
        recyclerView.setAdapter(meAdapter);
        meAdapter.setClick(new MeAdapter.OnClick() {
            @Override
            public void onClick(int position, String text) {
                if (!((BaseActivity)act).isLogin())return;
                switch (position) {
                    case 0:
                        UIHelper.startHistoricalRecordsFrg(root);
                        break;
                    case 1:
                        UIHelper.startMyCacheFrg(root);
//                        FileSaveUtils.saveVideo(act, "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4");
                        break;
                    case 2:
                        UIHelper.startMyCollectionFrg(root);
                        break;
                }
            }
        });
    }

    @Override
    public void onRequest() {
        if (User.getInstance().isLogin()) {
            userGetUserInfo();
            userGetViewTimesAtToday();
        } else {
            userAnonymousViewTimesAtToday();
        }
    }

    //10.3、获取用户当天观影次数
    private void userGetViewTimesAtToday() {
        CloudApi.userGetViewTimesAtToday()
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<BaseResponseBean<DataBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mView.addDisposable(d);
                    }

                    @Override
                    public void onNext(Response<BaseResponseBean<DataBean>> baseResponseBeanResponse) {
                        if (baseResponseBeanResponse.body().code == Code.CODE_SUCCESS) {
                            DataBean data = baseResponseBeanResponse.body().data;
                            if (data != null) {
                                mView.onUserAnonymousViewTimesAtToday(data);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //10.1、获取用户信息
    private void userGetUserInfo() {
        CloudApi.userGetUserInfo()
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mView.addDisposable(d);
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        if (jsonObject.optInt("code") == Code.CODE_SUCCESS) {
                            JSONObject data = jsonObject.optJSONObject("data");
                            if (data != null) {
                                User.getInstance().setUserInfo(data);
                                EventBus.getDefault().post(new MemberSuccessInEvent());
                                mView.onUserGetUserInfo(User.getInstance().getUserInfo());
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


    //10.2、获取匿名用户当天观影次数（不需要登陆）
    private void userAnonymousViewTimesAtToday() {
        CloudApi.userAnonymousViewTimesAtToday()
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<BaseResponseBean<DataBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mView.addDisposable(d);
                    }

                    @Override
                    public void onNext(Response<BaseResponseBean<DataBean>> baseResponseBeanResponse) {
                        if (baseResponseBeanResponse.body().code == Code.CODE_SUCCESS) {
                            DataBean data = baseResponseBeanResponse.body().data;
                            if (data != null) {
                                mView.onUserAnonymousViewTimesAtToday(data);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
