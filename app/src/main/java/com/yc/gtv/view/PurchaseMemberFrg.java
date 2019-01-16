package com.yc.gtv.view;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.yc.gtv.R;
import com.yc.gtv.adapter.PurchaseMemberAdapter;
import com.yc.gtv.base.BaseFragment;
import com.yc.gtv.base.User;
import com.yc.gtv.bean.DataBean;
import com.yc.gtv.databinding.FPurchaseMemberBinding;
import com.yc.gtv.event.LoginInEvent;
import com.yc.gtv.event.PayInEvent;
import com.yc.gtv.presenter.PurchaseMemberPresenter;
import com.yc.gtv.utils.GlideLoadingUtils;
import com.yc.gtv.utils.PopupWindowTool;
import com.yc.gtv.view.bottomFrg.PayBottomFrg;
import com.yc.gtv.view.impl.PurchaseMemberContract;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by edison on 2018/11/22.
 *  购买会员
 */

public class PurchaseMemberFrg extends BaseFragment<PurchaseMemberPresenter, FPurchaseMemberBinding> implements PurchaseMemberContract.View, View.OnClickListener{

    private List<DataBean> listBean = new ArrayList<>();
    private PurchaseMemberAdapter adapter;
    private int mPosition = -1;
    private PayBottomFrg payBottomFrg;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_purchase_member;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.purchase_member));
        mB.btSubmit.setOnClickListener(this);
        JSONObject userInfo = User.getInstance().getUserInfo();
        if (userInfo != null){
            GlideLoadingUtils.load(act, userInfo.optString("headImg"), mB.ivHead);
            mB.tvName.setText(userInfo.optString("nickname"));
        }

        if (adapter == null){
            adapter = new PurchaseMemberAdapter(act, listBean);
        }
        mB.gridView.setAdapter(adapter);
        mB.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mPosition = i;
                adapter.setmPosition(i);
                adapter.notifyDataSetChanged();
            }
        });
        if (payBottomFrg == null) {
            payBottomFrg = new PayBottomFrg();
        }
        payBottomFrg.setOnClickListener(new PayBottomFrg.OnClickListener() {
            @Override
            public void onClick(final int payType) {
                final DataBean bean = listBean.get(mPosition);
                PopupWindowTool.showDialog2(act, bean.getPrice(), new PopupWindowTool.DialogListener() {
                    @Override
                    public void onClick() {
                        mPresenter.onPay(bean.getId(), payType);
                    }
                });
            }
        });
        mPresenter.onRequest();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onMainPayInEvent(PayInEvent event){
        onPaySucees(-1, null);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_submit:
                if (mPosition == -1 )return;
                if (payBottomFrg != null && !payBottomFrg.isShowing()) {
                    payBottomFrg.show(getChildFragmentManager(), "dialog");
                    DataBean bean = listBean.get(mPosition);
                    BigDecimal price = bean.getPrice();
                    payBottomFrg.setPay(price, bean.getId());
                }
                break;
        }
    }

    @Override
    public void setData(List<DataBean> data) {
        listBean.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onPaySucees(int finalPayType, String data) {
        if (finalPayType == 1){
            try {
                wxPay(new JSONObject(data));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else if (finalPayType == 2){
            pay(data);
        }else {
            try {
                JSONObject userInfo = User.getInstance().getUserInfo();
                userInfo.put("vip", true);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            EventBus.getDefault().post(new LoginInEvent());
            act.onBackPressed();
        }
    }
}
