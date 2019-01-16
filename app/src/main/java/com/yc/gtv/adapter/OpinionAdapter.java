package com.yc.gtv.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.roundview.RoundTextView;
import com.flyco.roundview.RoundViewDelegate;
import com.yc.gtv.R;
import com.yc.gtv.base.BaseRecyclerviewAdapter;
import com.yc.gtv.bean.DataBean;

import java.util.List;

/**
 * Created by edison on 2018/11/21.
 */

public class OpinionAdapter extends BaseRecyclerviewAdapter<DataBean> {

    public OpinionAdapter(Context act, List<DataBean> listBean) {
        super(act, listBean);
    }

    @Override
    protected void onBindViewHolde(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        final DataBean bean = listBean.get(position);
        viewHolder.tvText.setText(bean.getName());
        RoundViewDelegate delegate = viewHolder.tvText.getDelegate();
        if (mPosition == position){
            viewHolder.tvText.setTextColor(act.getResources().getColor(R.color.white));
            delegate.setBackgroundColor(act.getResources().getColor(R.color.reb_FC739D));
        }else {
            viewHolder.tvText.setTextColor(act.getResources().getColor(R.color.black_666666));
            delegate.setBackgroundColor(act.getResources().getColor(R.color.gray_F0F0F0));
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
    public void setOnClickListener(OnClickListener listener){
        this.listener = listener;
    }
    public interface OnClickListener{
        void onClick(int position);
    }


    @Override
    protected RecyclerView.ViewHolder onCreateViewHolde(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.i_opinion, parent, false));
    }

    private int mPosition = -1;

    public void setmPosition(int mPosition) {
        this.mPosition = mPosition;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        RoundTextView tvText;
        public ViewHolder(View itemView) {
            super(itemView);
            tvText = itemView.findViewById(R.id.tv_text);
        }
    }

}
