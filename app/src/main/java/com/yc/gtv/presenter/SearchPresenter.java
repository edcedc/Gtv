package com.yc.gtv.presenter;

import android.support.constraint.Group;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.StringUtils;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lzy.okgo.model.Response;
import com.yc.gtv.bean.BaseListBean;
import com.yc.gtv.bean.BaseResponseBean;
import com.yc.gtv.bean.DataBean;
import com.yc.gtv.bean.SaveSearchListBean;
import com.yc.gtv.callback.Code;
import com.yc.gtv.controller.CloudApi;
import com.yc.gtv.view.impl.SearchContract;

import org.litepal.LitePal;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by edison on 2018/11/18.
 */

public class SearchPresenter extends SearchContract.Presenter{

    @Override
    public void onRequest(int pagerNumber) {
       CloudApi.list2(CloudApi.homeKeywords)
               .doOnSubscribe(new Consumer<Disposable>() {
                   @Override
                   public void accept(Disposable disposable) throws Exception {
                   }
               })
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(new Observer<Response<BaseResponseBean<List<DataBean>>>>() {
                   @Override
                   public void onSubscribe(Disposable d) {
                       mView.addDisposable(d);
                   }

                   @Override
                   public void onNext(Response<BaseResponseBean<List<DataBean>>> baseResponseBeanResponse) {
                       if (baseResponseBeanResponse.body().code == Code.CODE_SUCCESS){
                           List<DataBean> data = baseResponseBeanResponse.body().data;
                           if (data != null){
                               mView.setData(data);
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

    public void onSaveHistory(String trim, Group gpList, RecyclerView recyclerView, TwinklingRefreshLayout refreshLayout, int pagerNumber) {
        if (StringUtils.isEmpty(trim))return;
        /*SaveSearchListBean bean1 = new SaveSearchListBean();
        final List<SaveSearchListBean> all = LitePal.findAll(SaveSearchListBean.class);
        if (all != null && all.size() != 0){
            for (SaveSearchListBean bean : all){
                if (bean.getContent().equals(trim)){
                    break;
                }else {
                    bean1.setContent(trim);
                    bean1.save();
                    break;
                }
            }
        }*/
        gpList.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        refreshLayout.startRefresh();
//        onSearch(trim, pagerNumber);
    }

    @Override
    public void onSearch(String trim, int pagerNumber) {
        SaveSearchListBean bean1 = new SaveSearchListBean();
        final List<SaveSearchListBean> all = LitePal.findAll(SaveSearchListBean.class);
        if (all != null){
            List<SaveSearchListBean> saveSearchListBeans = LitePal.where("content = ?" , trim).find(SaveSearchListBean.class);
            if (saveSearchListBeans.size() == 0){
                bean1.setContent(trim);
                bean1.save();
            }
        }
        mView.setSearchData();

        CloudApi.homeSearch(pagerNumber, trim)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<BaseResponseBean<BaseListBean<DataBean>>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mView.addDisposable(d);
                    }

                    @Override
                    public void onNext(Response<BaseResponseBean<BaseListBean<DataBean>>> baseResponseBeanResponse) {
                        if (baseResponseBeanResponse.body().code == Code.CODE_SUCCESS){
                            BaseListBean<DataBean> data = baseResponseBeanResponse.body().data;
                            if (data != null){
                                List<DataBean> rows = data.getRows();
                                if (rows != null){
                                    mView.setSearchList(rows);
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        mView.hideLoading();
                    }
                });
    }

}
