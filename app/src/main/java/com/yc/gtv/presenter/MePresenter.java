package com.yc.gtv.presenter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;

import com.yc.gtv.R;
import com.yc.gtv.adapter.LabelAdapter;
import com.yc.gtv.adapter.MeAdapter;
import com.yc.gtv.base.BaseFragment;
import com.yc.gtv.bean.DataBean;
import com.yc.gtv.controller.UIHelper;
import com.yc.gtv.view.impl.MeContract;
import com.yc.gtv.weight.WithScrollGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edison on 2018/11/17.
 */

public class MePresenter extends MeContract.Presenter{

    @Override
    public void onInit(WithScrollGridView gridView, RecyclerView recyclerView, final BaseFragment root) {
        String[] laberStr = {act.getString(R.string.member_center), act.getString(R.string.promote), act.getString(R.string.system_notification), act.getString(R.string.feedback_feedback)};
        int[] laberImg = {R.mipmap.my_con_ic01, R.mipmap.my_con_ic02, R.mipmap.my_con_ic03, R.mipmap.my_con_ic04};
        List<DataBean> listStr = new ArrayList<>();
        for (int i = 0;i < laberStr.length;i++){
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
                switch (i){
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
        for (int i = 0;i < str.length;i++){
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
                switch (position){
                    case 0:
                        UIHelper.startHistoricalRecordsFrg(root);
                        break;
                    case 1:
                        UIHelper.startMyCacheFrg(root);
                        break;
                    case 2:
                        UIHelper.startMyCollectionFrg(root);
                        break;
                }
            }
        });
    }

}
