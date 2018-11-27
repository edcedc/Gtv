package com.yc.gtv.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yc.gtv.R;
import com.yc.gtv.base.BaseListViewAdapter;
import com.yc.gtv.bean.DataBean;

import java.util.List;

/**
 * 作者：yc on 2018/9/26.
 * 邮箱：501807647@qq.com
 * 版本：v1.0
 */

public class PayAdapter extends BaseListViewAdapter<DataBean> {

    public PayAdapter(Context act, List<DataBean> listBean) {
        super(act, listBean);
    }


    @Override
    protected View getCreateVieww(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(act, R.layout.i_pay, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        DataBean bean = listBean.get(position);
        viewHolder.tvText.setText(bean.getName());
        viewHolder.ivImg.setBackgroundResource(bean.getImg());
        if (mPsition == position){
            viewHolder.ivSelect.setBackgroundResource(R.mipmap.pay_way_sel);
        }else {
            viewHolder.ivSelect.setBackgroundResource(R.mipmap.pay_way_nor);
        }
        if (position == 2){
            viewHolder.tvOnus.setVisibility(View.VISIBLE);
            viewHolder.tvOnus.setText("余额：" +
                    "100.85");
        }else {
            viewHolder.tvOnus.setVisibility(View.GONE);
        }
        return convertView;
    }

    private int mPsition = -1;

    public void setmPsition(int mPsition) {
        this.mPsition = mPsition;
    }

    class ViewHolder{

        TextView tvText;
        TextView tvOnus;
        ImageView ivSelect;
        ImageView ivImg;

        public ViewHolder(View convertView) {
            tvText = convertView.findViewById(R.id.tv_text);
            tvOnus = convertView.findViewById(R.id.tv_onus);
            ivSelect = convertView.findViewById(R.id.iv_select);
            ivImg = convertView.findViewById(R.id.iv_img);
        }
    }

}
