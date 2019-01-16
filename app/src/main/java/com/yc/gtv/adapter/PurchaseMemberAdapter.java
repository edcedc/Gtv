package com.yc.gtv.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yc.gtv.R;
import com.yc.gtv.base.BaseListViewAdapter;
import com.yc.gtv.bean.DataBean;

import java.util.List;

/**
 * Created by edison on 2018/11/23.
 */

public class PurchaseMemberAdapter extends BaseListViewAdapter<DataBean> {
    public PurchaseMemberAdapter(Context act, List listBean) {
        super(act, listBean);
    }

    private int mPosition = -1;

    public void setmPosition(int mPosition) {
        this.mPosition = mPosition;
    }

    private OnClickListener listener;

    @Override
    protected View getCreateVieww(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(act, R.layout.i_purchase, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final DataBean bean = listBean.get(position);
        viewHolder.ivPrice.setBackgroundResource(bean.getImg());
        viewHolder.tvTitle.setText(bean.getName());
        viewHolder.tvYouhui.setText(bean.getInvalidday() + "");
        viewHolder.tvPrice.setText("¥" + bean.getPrice());
        if (position == mPosition){
            viewHolder.layout.setBackgroundResource(R.mipmap.my_pay_card_bg);
        }else {
            viewHolder.layout.setBackgroundResource(R.mipmap.my_pay_card);
        }
        return convertView;
    }

    public interface OnClickListener{
        void onClick(int position);
    }
    public void setOnClickListener(OnClickListener listener){
        this.listener = listener;
    }

    class ViewHolder {

        View layout;
        ImageView ivPrice;
        AppCompatTextView tvYouhui;
        AppCompatTextView tvPrice;
        AppCompatTextView tvTitle;

        public ViewHolder(View itemView) {
            layout = itemView.findViewById(R.id.layout);
            ivPrice = itemView.findViewById(R.id.iv_price);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvYouhui = itemView.findViewById(R.id.tv_youhui);
            tvPrice = itemView.findViewById(R.id.tv_price);
        }
    }

}
