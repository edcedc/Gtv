package com.yc.gtv.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yc.gtv.R;
import com.yc.gtv.base.BaseRecyclerviewAdapter;
import com.yc.gtv.bean.DataBean;

import java.util.List;

/**
 * Created by edison on 2018/12/8.
 */

public class BalanceDetailsAdapter extends BaseRecyclerviewAdapter<DataBean>{
    public BalanceDetailsAdapter(Context act, List<DataBean> listBean) {
        super(act, listBean);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onBindViewHolde(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        DataBean bean = listBean.get(position);
        viewHolder.tvTitle.setText(bean.getTitle());
        viewHolder.tvTime.setText(bean.getDate());
        if (bean.getCalType() == 1){
            viewHolder.tvPrice.setText("+" + bean.getPrice());
            viewHolder.tvPrice.setTextColor(act.getColor(R.color.reb_D02626));
        }else {
            viewHolder.tvPrice.setText("-" + bean.getPrice());
            viewHolder.tvPrice.setTextColor(act.getColor(R.color.green_1EC75E));
        }
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolde(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.i_balance, parent, false));
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        AppCompatTextView tvTitle;
        AppCompatTextView tvTime;
        AppCompatTextView tvPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvPrice = itemView.findViewById(R.id.tv_price);
        }
    }

}
