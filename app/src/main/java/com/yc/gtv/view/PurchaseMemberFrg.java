package com.yc.gtv.view;

import android.os.Bundle;
import android.view.View;

import com.yc.gtv.R;
import com.yc.gtv.adapter.PurchaseMemberAdapter;
import com.yc.gtv.base.BaseFragment;
import com.yc.gtv.bean.DataBean;
import com.yc.gtv.databinding.FPurchaseMemberBinding;
import com.yc.gtv.event.PayInEvent;
import com.yc.gtv.presenter.PurchaseMemberPresenter;
import com.yc.gtv.utils.PopupWindowTool;
import com.yc.gtv.view.bottomFrg.PayBottomFrg;
import com.yc.gtv.view.impl.PurchaseMemberContract;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edison on 2018/11/22.
 *  购买会员
 */

public class PurchaseMemberFrg extends BaseFragment<PurchaseMemberPresenter, FPurchaseMemberBinding> implements PurchaseMemberContract.View, View.OnClickListener{

    private List<DataBean> listBean = new ArrayList<>();
    private PurchaseMemberAdapter adapter;
    private double[] strPrice = {9, 59, 159.3, 247.8, 354};
    private int mPosition = -1;
    private double price = -1;
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
//        int[] img = {R.mipmap.card_01, R.mipmap.card_02,R.mipmap.card_03,R.mipmap.card_04,R.mipmap.card_05};
//        String[] strContent = {"（3天）", "", "（3个月）", "", "", ""};
//        for (int i = 0;i < img.length;i++){
//            DataBean bean = new DataBean();
//            bean.setImg(img[i]);
//            bean.setPrice(new BigDecimal(strPrice[i]));
//            bean.setContent(strContent[i]);
//            listBean.add(bean);
//        }

        mB.btSubmit.setOnClickListener(this);
        mB.lyPrice0.setOnClickListener(this);
        mB.lyPrice1.setOnClickListener(this);
        mB.lyPrice2.setOnClickListener(this);
        mB.lyPrice3.setOnClickListener(this);
        mB.lyPrice4.setOnClickListener(this);

        if (payBottomFrg == null) {
            payBottomFrg = new PayBottomFrg();
        }
        payBottomFrg.setOnClickListener(new PayBottomFrg.OnClickListener() {
            @Override
            public void onClick(int payType) {
                PopupWindowTool.showDialog2(act, new PopupWindowTool.DialogListener() {
                    @Override
                    public void onClick() {
                        EventBus.getDefault().post(new PayInEvent());
                        act.onBackPressed();
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_submit:
                if (payBottomFrg != null && !payBottomFrg.isShowing()) {
                    payBottomFrg.show(getChildFragmentManager(), "dialog");
                }
                break;
            case R.id.ly_price0:
                getPrice(0);
                break;
            case R.id.ly_price1:
                getPrice(1);
                break;
            case R.id.ly_price2:
                getPrice(2);
                break;
            case R.id.ly_price3:
                getPrice(3);
                break;
            case R.id.ly_price4:
                getPrice(4);
                break;
        }
    }

    //无奈做法
    private void getPrice(int position){
        mPosition = position;
        price = strPrice[position];
        switch (position){
            case 0:
                mB.lyPrice0.setBackgroundResource(R.mipmap.my_pay_card_bg);
                mB.lyPrice1.setBackgroundResource(R.mipmap.my_pay_card);
                mB.lyPrice2.setBackgroundResource(R.mipmap.my_pay_card);
                mB.lyPrice3.setBackgroundResource(R.mipmap.my_pay_card);
                mB.lyPrice4.setBackgroundResource(R.mipmap.my_pay_card);
                break;
            case 1:
                mB.lyPrice0.setBackgroundResource(R.mipmap.my_pay_card);
                mB.lyPrice1.setBackgroundResource(R.mipmap.my_pay_card_bg);
                mB.lyPrice2.setBackgroundResource(R.mipmap.my_pay_card);
                mB.lyPrice3.setBackgroundResource(R.mipmap.my_pay_card);
                mB.lyPrice4.setBackgroundResource(R.mipmap.my_pay_card);
                break;
            case 2:
                mB.lyPrice0.setBackgroundResource(R.mipmap.my_pay_card);
                mB.lyPrice1.setBackgroundResource(R.mipmap.my_pay_card);
                mB.lyPrice2.setBackgroundResource(R.mipmap.my_pay_card_bg);
                mB.lyPrice3.setBackgroundResource(R.mipmap.my_pay_card);
                mB.lyPrice4.setBackgroundResource(R.mipmap.my_pay_card);
                break;
            case 3:
                mB.lyPrice0.setBackgroundResource(R.mipmap.my_pay_card);
                mB.lyPrice1.setBackgroundResource(R.mipmap.my_pay_card);
                mB.lyPrice2.setBackgroundResource(R.mipmap.my_pay_card);
                mB.lyPrice3.setBackgroundResource(R.mipmap.my_pay_card_bg);
                mB.lyPrice4.setBackgroundResource(R.mipmap.my_pay_card);
                break;
            case 4:
                mB.lyPrice0.setBackgroundResource(R.mipmap.my_pay_card);
                mB.lyPrice1.setBackgroundResource(R.mipmap.my_pay_card);
                mB.lyPrice2.setBackgroundResource(R.mipmap.my_pay_card);
                mB.lyPrice3.setBackgroundResource(R.mipmap.my_pay_card);
                mB.lyPrice4.setBackgroundResource(R.mipmap.my_pay_card_bg);
                break;
        }

    }

}
