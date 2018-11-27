package com.yc.gtv.utils;

import android.content.Context;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.yc.gtv.R;
import com.yc.gtv.weight.WPopupWindow;

/**
 * 作者：yc on 2018/8/23.
 * 邮箱：501807647@qq.com
 * 版本：v1.0
 */

public class PopupWindowTool {

    public static final int onTrial = 1; //使用六分钟
    public static final int notMember = 2; //不是会员
    public static final int notLogin = 3; //未登录
    public static final int clear_cache = 4; //清除缓存
    public static final int confirm_payment = 5; //是否确定支付


    public static void showDialog(Context act, final int type, final DialogListener listener){
        View wh = LayoutInflater.from(act).inflate(R.layout.p_dialog, null);
        final WPopupWindow popupWindow = new WPopupWindow(wh);
        popupWindow.showAtLocation(wh, Gravity.CENTER, 0, 0);
        TextView tvTitle = wh.findViewById(R.id.tv_title);
        TextView btCancel = wh.findViewById(R.id.bt_cancel);
        TextView btSubmit = wh.findViewById(R.id.bt_submit);
        View view = wh.findViewById(R.id.view);
        switch (type){
            case onTrial:
                tvTitle.setText("6分钟试看已用完\n立即分享获得观影权限吧");
                btCancel.setVisibility(View.GONE);
                view.setVisibility(View.GONE);
                break;
            case notMember:
                tvTitle.setText("您还不是会员，无法下载哦");
                btCancel.setVisibility(View.GONE);
                view.setVisibility(View.GONE);
                break;
            case notLogin:
                String str1 = "您还未登录，" + "<font color='#A9DDC0'> 前往登录>> </font>";
                tvTitle.setText(Html.fromHtml(str1));
                btCancel.setVisibility(View.GONE);
                view.setVisibility(View.GONE);
                break;
            case clear_cache:
                tvTitle.setText("确定清理缓存吗");
                break;
            case confirm_payment:

                break;
        }
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null){
                    listener.onClick();
                }
                popupWindow.dismiss();
            }
        });
    }

    public interface DialogListener{
        void onClick();
    }

    public static void showDialog2(Context act, final DialogListener listener){
        View wh = LayoutInflater.from(act).inflate(R.layout.p_pay, null);
        final WPopupWindow popupWindow = new WPopupWindow(wh);
        popupWindow.showAtLocation(wh, Gravity.CENTER, 0, 0);
        TextView tvTitle = wh.findViewById(R.id.tv_title);
        TextView tvPrice = wh.findViewById(R.id.tv_price);
        TextView btCancel = wh.findViewById(R.id.bt_cancel);
        TextView btSubmit = wh.findViewById(R.id.bt_submit);
        View view = wh.findViewById(R.id.view);
        tvTitle.setText("您将支付 " +
                "59" +
                " 元");
        tvPrice.setText("当前奖励金余额：" +
                "100.85" +
                " 元");
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null){
                    listener.onClick();
                }
                popupWindow.dismiss();
            }
        });
    }

}
