package com.yc.gtv.view.bottomFrg;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yc.gtv.R;
import com.yc.gtv.adapter.PayAdapter;
import com.yc.gtv.base.BaseBottomSheetFrag;
import com.yc.gtv.bean.DataBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：yc on 2018/9/26.
 * 邮箱：501807647@qq.com
 * 版本：v1.0
 *  支付类型
 */

public class PayBottomFrg extends BaseBottomSheetFrag {

    @Override
    public int bindLayout() {
        return R.layout.f_pay;
    }
    private String[] pays= {"支付宝", "微信支付", "奖励金支付"};

    private int payType = 0;
    private BigDecimal payPrice;
    private String payId;

    private PayAdapter adapter;

    private boolean isPayState = false;//页面关闭状态

    public boolean isPayState() {
        return isPayState;
    }

    public void setPayState(boolean payState) {
        isPayState = payState;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        if (!isPayState){
//            UIHelper.startOrderAct(0);
//            ActivityUtils.finishActivity(ConfirmationOrderAct.class);
//        }
    }

    @Override
    public void initView(View view) {
        ListView listView = view.findViewById(R.id.listView);
        List<DataBean> listBean = new ArrayList<>();
        int[] imgs = {R.mipmap.pay_zfb, R.mipmap.pay_wechat, R.mipmap.pay_jlj};
        for (int i = 0;i < pays.length;i++){
            DataBean bean = new DataBean();
            bean.setImg(imgs[i]);
            bean.setName(pays[i]);
            listBean.add(bean);
        }
        if (adapter == null){
            adapter = new PayAdapter(act, listBean);
        }
        listView.setAdapter(adapter);
//        adapter.setmPsition(0);
//        adapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                payType = i;
                switch (i){
                    case 0:
                        break;
                    case 1:
                        break;
                    default:
                        break;

                }
                adapter.setmPsition(i);
                adapter.notifyDataSetChanged();
                if (listener != null){
                    listener.onClick(payType);
                }
                dismiss();
            }
        });

        view.findViewById(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    private OnClickListener listener;

    public void setOnClickListener(OnClickListener listener){
        this.listener = listener;
    }

    public void setPay(BigDecimal price, String id) {
        this.payPrice = price;
        this.payId = id;
    }

    public interface OnClickListener{
        void onClick(int payType);
    }

}
