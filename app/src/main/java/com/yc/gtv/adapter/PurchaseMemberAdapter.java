package com.yc.gtv.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.blankj.utilcode.util.StringUtils;
import com.yc.gtv.R;
import com.yc.gtv.base.BaseRecyclerviewAdapter;
import com.yc.gtv.bean.DataBean;

import java.util.List;

/**
 * Created by edison on 2018/11/23.
 */

public class PurchaseMemberAdapter extends BaseRecyclerviewAdapter<DataBean>{
    public PurchaseMemberAdapter(Context act, List listBean) {
        super(act, listBean);
    }

    private int mPosition = -1;

    public void setmPosition(int mPosition) {
        this.mPosition = mPosition;
    }

    @Override
    protected void onBindViewHolde(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        DataBean bean = listBean.get(position);
        viewHolder.ivPrice.setBackgroundResource(bean.getImg());
        String content = bean.getContent();
        viewHolder.tvPrice.setText("¥" + bean.getPrice());
        if (!StringUtils.isEmpty(content)){
            viewHolder.tvYouhui.setVisibility(View.VISIBLE);
            viewHolder.tvYouhui.setText(bean.getContent());
        }else {
            viewHolder.tvYouhui.setVisibility(View.GONE);
        }
        if (position == mPosition){
            viewHolder.layout.setBackgroundResource(R.mipmap.my_pay_card_bg);
        }else {
            viewHolder.layout.setBackgroundResource(R.mipmap.my_pay_card);
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null){
                    listener.onClick(position);
                }
            }
        });
    }

    private OnClickListener listener;
    public interface OnClickListener{
        void onClick(int position);
    }
    public void setOnClickListener(OnClickListener listener){
        this.listener = listener;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolde(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.i_purchase, parent, false));
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        View layout;
        ImageView ivPrice;
        AppCompatTextView tvYouhui;
        AppCompatTextView tvPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.layout);
            ivPrice = itemView.findViewById(R.id.iv_price);
            tvYouhui = itemView.findViewById(R.id.tv_youhui);
            tvPrice = itemView.findViewById(R.id.tv_price);
        }
    }

}
